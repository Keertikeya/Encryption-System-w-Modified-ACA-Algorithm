<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>

<jsp:useBean id="dirMgr" class="datasentinel.DirManager" scope="page"/>

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Image</title>
    </head>
    <body>
    <table width="100%">
        <tr>
            <td colspan="2"> <jsp:include page="titlebar.jsp" />
        </tr>
        <tr align="right" bgcolor = "#9999FF">
            <td > <jsp:include page="navbar.jsp"/> </td>
        </tr>
        <tr>
            <td>
    <%
    String uid = "";
    try
    {
        uid = session.getAttribute("uid").toString();
    }
    catch(Exception e)
    {}
    if(uid.length() == 0)
    {
         response.sendRedirect("login.jsp");
    }
    else
    {
        try
        {
            byte buffer[] = new byte[512];
            int bytesread;
            ServletInputStream sin = request.getInputStream();

            String tempfname = dirMgr.getTempFileName(uid);
            FileOutputStream fout = new FileOutputStream(tempfname);
            while( (bytesread = sin.read(buffer)) != -1)
            {
                fout.write(buffer, 0, bytesread);
            }
            fout.close();

            datasentinel.UserImageManager uim = new datasentinel.UserImageManager();
            if(uim.parseUserImage(tempfname, uid))
            {
                
                out.println("<h2> User Image Uplaoded SUCCESSFULLY </h2>");
            }
            else
             {
                out.println("<h2> User Image Uplaod FAILED </h2>");
            }  
        }
        catch(Exception ex)
        {
            out.println("\n File Upload Failed");
        }
        finally
        {
            dirMgr.clearUploads(uid);
        }
       
    }
    %>
            </td>
        </tr>
    </table>
    </body>
</html>
