<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.util.zip.*" %>

<jsp:useBean id="dirMgr" class="datasentinel.DirManager" scope="page"/>
<jsp:useBean id="ushld" class="datasentinel.UnShielder" scope="page" />


<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shield</title>
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
         uid = "guest";
    }
    
        try
        {
            byte buffer[] = new byte[512];
            int bytesread;
            ServletInputStream sin = request.getInputStream();

            String tempFname = dirMgr.getTempFileName(uid);
            FileOutputStream fout = new FileOutputStream(tempFname);
            while( (bytesread = sin.read(buffer)) != -1)
            {
                fout.write(buffer, 0, bytesread);
            }
            fout.close();

            String result = ushld.unShieldFile(tempFname, uid);

            File f = new File(result);
            if(f.exists())
            {
                response.setContentType("application/octet-stream");
                response.setHeader( "Content-Disposition", "attachment;filename="+ f.getName() );

                ServletOutputStream sout = response.getOutputStream();
                FileInputStream fin = new FileInputStream(f);
                int data;
                while((data = fin.read()) != -1)
                {
                   sout.write(data);
                }
                fin.close();
                sout.close();
                
                dirMgr.clearUploads(uid);
            }
            else
            {
                out.println("<h2>UnShielding Failed : "+ result + " </h2>");
            }
            
        }
        catch(Exception ex)
        {
            out.println("<h2>Err :" + ex.getMessage() + " </h2>");
            ex.printStackTrace();
            dirMgr.clearUploads(uid);
        }
    
    %>
        </td>
        </tr>
    </table>
    </body>
</html>
