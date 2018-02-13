<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<jsp:useBean class="datasentinel.Validate" id="cpass" scope="page" />
    
<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
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

    %>
        <table width="100%">
            <tr>
                <td> <jsp:include page="titlebar.jsp" /> </td>
            </tr>
            <tr>
                <td align="right" bgcolor = "#9999FF"> <jsp:include page = "navbar.jsp" /></td>
            </tr>

            <tr>    
                               
                <td>
                <%
                    String pass = request.getParameter("currentpass");
                    String newpass = request.getParameter("newpass");
                    if(cpass.chgPass(uid, pass, newpass))
                    {
                        out.println("<h2>Password Changed Successfully</h2>");
                    }
                    else
                    {
                        out.println("<h2>Error Changing Password</h2>");
                    }
                %>
            </td>
        </tr>
    </table>
    <%
    }
    %>
    </body>
</html>
