package uk.ac.kcl.cch.diamm.servlet;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import uk.ac.kcl.cch.diamm.ImageSearch;
import uk.ac.kcl.cch.diamm.hibernate.HibernateUtil;
import uk.ac.kcl.cch.diamm.model.FileComparator;
import uk.ac.kcl.cch.diamm.model.Item;
import uk.ac.kcl.cch.diamm.ui.Constants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 04/09/12
 * Time: 09:38
 * To change this template use File | Settings | File Templates.
 */
public class DataManagerServlet extends HttpServlet {
    /**
     * From Michael C. Daconta
     */
    class StreamGobbler extends Thread {
        InputStream is;
        String type;
        OutputStream os;

        StreamGobbler(InputStream is, String type) {
            this(is, type, null);
        }

        StreamGobbler(InputStream is, String type, OutputStream redirect) {
            this.is = is;
            this.type = type;
            this.os = redirect;
        }

        public void run() {
            try {
                PrintWriter pw = null;
                if (os != null)
                    pw = new PrintWriter(os);

                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (pw != null)
                        pw.println(line);
                    System.out.println(type + ">" + line);
                }
                if (pw != null)
                    pw.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    //Number of SQL dumps to retain on drive
    public static final int DUMPLIMIT = 5;

    public static final int MANAGER = 1;
    public static final int RESETCACHE = 2;
    public static final int DUMPDB = 3;
    public static final int PUSHTOLIVE = 4;
    public static final int UPLOADIMAGE = 5;
    private static String exportPath;
    private String page = "datamanager.jsp";
    private final Runtime rt = Runtime.getRuntime();


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter(Constants.opParameter);
        exportPath = getServletContext().getInitParameter("export_path");
        int operation = 0;
        if (op != null && op.length() > 0) {
            try {
                operation = Integer.parseInt(op);
            } catch (NumberFormatException ne) {

            }
        } else {
            //default
            operation = 1;
        }
        switch (operation) {
            case MANAGER:
                //Display recent backups
                displayManager(request, response);
                break;
            case RESETCACHE:
                resetCache();
                //Force cache rebuild?
                break;
            case DUMPDB:
                //Generate dump
                dumpDB("stg", request, response);
                //If too many others, delete oldest

                displayManager(request, response);
                break;
            case PUSHTOLIVE:
                pushToLive(request, response);
                page="dbmi.jsp";
                //displayManager(request, response);
                break;
            case UPLOADIMAGE:
                //If Post data
                // copy image to temp folder
                //Convert jp2 and store in iip folder
                break;
        }
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter(Constants.jspPRoot) + page);
        reqDisp.include(request, response);

    }

    private void displayManager(HttpServletRequest request, HttpServletResponse response) {
        //Add response variables
        getFiles(request, exportPath);

    }

    private void displayUploadPage(HttpServletRequest request, HttpServletResponse response) {

    }

    private void uploadImage(HttpServletRequest request, HttpServletResponse response) {

    }

    /**
     * Builds a mysqldump statement and executes
     *
     * @param target   stg or live
     * @param request
     * @param response
     */
    private void dumpDB(String target, HttpServletRequest request, HttpServletResponse response) {
        /*String dumpcmd = "/bin/sh -c /usr/bin/mysqldump --defaults-file=" + exportPath;
        if (target.contains("stg")) {
            dumpcmd += "/dump_stg.cnf";
        } else {
            dumpcmd += "/dump_live.cnf";
        }

        dumpcmd += " diamm_ess";*/
        String dumpcmd = "";
        if (target.contains("stg")) {
            dumpcmd = "/bin/sh -c /vol/diamm/webroot/stg/tomcat/webapps/diamm/WEB-INF/bin/dump_stg.sh";
        }else{
            dumpcmd = "/bin/sh -c /vol/diamm/webroot/stg/tomcat/webapps/diamm/WEB-INF/bin/dump_live.sh";
        }
        //File to export to
        String fName = getExportFilename(target);
        try {
            FileOutputStream fos = new FileOutputStream(exportPath + fName);
            //String[] cmd = {"/bin/sh","-c","\""+dumpcmd+"\""};
            System.out.println("Trying:"+dumpcmd);
            Process proc = rt.exec(dumpcmd);
            // any error message?
            StreamGobbler errorGobbler = new
                    StreamGobbler(proc.getErrorStream(), "ERROR");
            InputStreamReader isr = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            PrintWriter pw = new PrintWriter(fos);
            int count=0;
            while ((line = br.readLine()) != null) {
                pw.print(line);
                count+=1;
                if (count % 100==0){
                    System.out.println("At line:"+count);
                }
            }
            System.out.println("Total: "+count);
            System.out.println("Writing to :"+exportPath + fName);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void pushToLive(HttpServletRequest request, HttpServletResponse response) {
        /*String dumpName = request.getParameter("filename");
        if (dumpName != null && dumpName.length() > 0) {*/
            dumpDB("stg", request, response);
            //Backup live
            dumpDB("live", request, response);
            //Push selected dump to live
            String pushcmd = "/bin/sh -c /vol/diamm/webroot/stg/tomcat/webapps/diamm/WEB-INF/bin/push_live.sh";

            //
            try {
                //Windows version, doesn't work yet
                //String[] cmd = {"cmd.exe","/C","mysql.exe","--defaults-file=" + exportPath+ "/push_live.cnf","diamm_ess","<",exportPath +"/"+dumpName};
                //String[] cmd = {"/bin/sh","-c","\"/usr/bin/mysql.exe --defaults-file=" + exportPath+ "/push_live.cnf diamm_ess < "+exportPath +"/"+dumpName+"\""};
                Process proc = rt.exec(pushcmd);
                InputStreamReader isr = new InputStreamReader(proc.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String line="";
                while ((line = br.readLine()) != null) {
                    //Dump any messages to console
                    System.out.println(line);
                }
                request.setAttribute("message","Upload to Live complete.  Load the facet browser to repopulate the cache.");
            } catch (IOException e) {
                request.setAttribute("message","error:"+e.getMessage());
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            //Reset cache
            //updateItemHasImages();
            //resetCache();
       // }
    }

    /**
     * Modified from John Lee's cvma dbmi code by EH
     *
     * @param exportPath
     * @return
     */
    private void getFiles(HttpServletRequest request, String exportPath) {
        File folder = new File(exportPath);
        File[] fileList = folder.listFiles();
        List<String> stgStringList = new ArrayList<String>();
        List<String> liveStringList = new ArrayList<String>();
        if (fileList != null) {
            Arrays.sort(fileList, new FileComparator());
            for (File file : fileList) {
                //Omit files that have not finished exporting
                if (file.length() > 0 && file.getName().contains("sql")) {
                    if (file.getName().contains("stg")) {
                        stgStringList.add(file.getName());
                    } else {
                        liveStringList.add(file.getName());
                    }
                }
            }
        }
        request.setAttribute("stgFileList", stgStringList);
        request.setAttribute("liveFileList", liveStringList);
    }

    private String getExportFilename(String prefix) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String date = sdf.format(cal.getTime());
        return ("DIAMM_" + prefix + "_" + date + ".sql");
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Deletes all items from FacetCriterion to allow rebuilding, and refreshes the Item.hasImage switch
     */
    public static void resetCache() {
        try {
            HibernateUtil.beginTransaction();
            HibernateUtil.getSession().createSQLQuery("DELETE from FacetCriterionEntity").executeUpdate();
            ImageSearch.updateItemHasImages();
        } finally {
            HibernateUtil.getSession().flush();
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
        }
    }


    public static void updateItemHasImages() {
        try {
            ArrayList<Item> items = (ArrayList<Item>) HibernateUtil.getSession().createCriteria(Item.class)
                    .createCriteria("itemimagesByItemkey").createCriteria("imageByImagekey").add(Restrictions.eq("availwebsite", "Y"))
                    .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                    .list();
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                //int num = HibernateUtil.getSession().createSQLQuery("update Item set hasImages=1 where itemKey in (select ItemImage.itemKey from ItemImage,Image where Image.imageKey=ItemImage.imageKey and Image.availWebsite='Y' group by ItemImage.itemKey)").executeUpdate();
                //This is incredibly stupid, but Hibernate update is not working for some reason
                if(item.getHasImages()==0){
                    HibernateUtil.getSession().createSQLQuery("update Item set hasImages=1 where itemKey="+item.getItemkey()).executeUpdate();
                }
                /*if (item.getItemkey()==6221){
                    int stop=0;
                }
                item.setHasImages(1);
                HibernateUtil.getSession().saveOrUpdate(item);*/
            }
        } catch (HibernateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            //HibernateUtil.getSession().flush();
            //HibernateUtil.commitTransaction();
        }
    }

}
