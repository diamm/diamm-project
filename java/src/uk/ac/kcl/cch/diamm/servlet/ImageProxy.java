package uk.ac.kcl.cch.diamm.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Elliot
 * Date: 01/02/11
 * Time: 18:28
 * To change this template use File | Settings | File Templates.
 */
public class ImageProxy extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    public static boolean testURL(String testurl){
        try {
            URL url = new URL(testurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream test=con.getInputStream();
            if (test!=null&&test.available()>0){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
       return false;
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            //String reqUrl = request.getQueryString();    request.getRequestURI()
            String newURL = "http://images.cch.kcl.ac.uk/diamm/liv/images/" + request.getRequestURI().replaceAll("^.*ImageProxy\\/", "");
            URL url = new URL(newURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod(request.getMethod());
            int clength = request.getContentLength();
            if (clength > 0) {
                con.setDoInput(true);
                byte[] idata = new byte[clength];
                request.getInputStream().read(idata, 0, clength);
                con.getOutputStream().write(idata, 0, clength);
            }
            response.setContentType(con.getContentType());

            if (con.getContentType().indexOf("/xml") > -1) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                   response.getWriter().println(line);
                }
                rd.close();
            } else {
                BufferedInputStream clientToProxyBuf = new BufferedInputStream(con.getInputStream());
                BufferedOutputStream proxyToWebBuf     = new BufferedOutputStream(response.getOutputStream());
                int oneByte;
                while ((oneByte = clientToProxyBuf.read()) != -1){
                     proxyToWebBuf.write(oneByte);
                }
                 proxyToWebBuf.flush();
                 proxyToWebBuf.close();
                 clientToProxyBuf.close();
               /* byte[] readArray = new byte[2048];
                int bytes = 2048;
                while (bytes == 2048) {
                    bytes = rd.read(readArray);
                    response.getWriter().write(readArray);
                }     */
            }


            /* RequestDispatcher reqDisp = request.getRequestDispatcher(newURL);
            reqDisp.forward(request, response);*/

        } catch (Exception e) {
            response.setStatus(500);
        }
    }
}
