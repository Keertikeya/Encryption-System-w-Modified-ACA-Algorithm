<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
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
    %>

    <table width = 100%>
    <tr>
        <td> <jsp:include page="titlebar.jsp"/> </td>
    </tr>
    <tr bgcolor="#9999FF">
        <td align="right" colspan="2" > <jsp:include page="navbar.jsp" /> </td>
    </tr>
    
    <tr> <td colspan=2>
       <h3>Welcome <%=uid%></h3></td> </tr>
<tr> <td> </td>
<br><br><br><br><td align=right>
<img src=features.bmp align=center></td> </tr>
</table>
    </body>
</html>
