package uk.ac.kcl.cch.diamm.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 29/08/12
 * Time: 12:02
 * <p/>
 * Uses ApacheCommon fule upload, checked out at rev  r1082083
 */
public class UploadServlet extends HttpServlet {
    public static final String page = "imageupload.jsp";
    public static final int maxSize = 200000000;
    private final Runtime rt = Runtime.getRuntime();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /*int op = 1;
        if (request.getParameter("op") != null) {
            op = Integer.parseInt(request.getParameter("op"));
        }*/
        String tempImagePath = getServletContext().getInitParameter("tempImagePath");
        String jp2Path = getServletContext().getInitParameter("jp2Path");
        //Upload Image
        //Parse form
        // Check that we have a file upload request
        boolean isMultipart = org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {

            try {
                File tempRepo=new File(tempImagePath);
                // Create a factory for disk-based file items
                org.apache.commons.fileupload.disk.DiskFileItemFactory factory = new org.apache.commons.fileupload.disk.DiskFileItemFactory(maxSize,tempRepo);
                // Set factory constraints
                //factory.setSizeThreshold(maxSize);
                //factory.setRepository(tempImagePath);

                // Create a new file upload handler
                org.apache.commons.fileupload.servlet.ServletFileUpload upload = new org.apache.commons.fileupload.servlet.ServletFileUpload(factory);

                // Parse the request
                List<org.apache.commons.fileupload.FileItem> items = upload.parseRequest(request);
                for (int i = 0; i < items.size(); i++) {
                    org.apache.commons.fileupload.FileItem fileItem = items.get(i);
                    if (fileItem.isFormField()){
                        //todo will needmore data here?
                    } else{
                        String fileName = fileItem.getName();
                        String contentType = fileItem.getContentType();
                        //Copy image to temp folder
                        File uploadedFile = new File(tempImagePath + "/" + fileName);
                        fileItem.write(uploadedFile);
                        //system call to kdu to convert to jp2
                        String kducmd="kdu_compress -i "+tempImagePath + "/" + fileName+" -o "+jp2Path+"/"+fileName.replaceAll("\\.tif","")+
                                 ".jp2 -rate -,4,2.34,1.36,0.797,0.466,0.272,0.159,0.0929,0.0543,0.0317,0.0185 Creversible=no Clevels=5 Stiles={1024,1024} Cblk={64,64} Corder=RPCL Cmodes=BYPASS";
                        try {
                            Process proc = rt.exec(kducmd);
                        } catch (IOException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    }
                }
            } catch (org.apache.commons.fileupload.FileUploadException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }

        RequestDispatcher reqDisp = request.getRequestDispatcher(getServletContext().getInitParameter("jsp_protected_root") + page);
        reqDisp.forward(request, response);
    }
}