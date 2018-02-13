<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp"%>

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body  >
        <table width = 100%>
        <tr>
            <td colspan="2"> <jsp:include page="titlebar.jsp" /> </td>
        </tr>
        <tr>
            <td colspan="2" bgcolor = "#9999FF" align="right"> <a href = index.jsp> Home</a></td>
        </tr>
            
            <tr>
                <td width = 4% >&nbsp;</td>
                <td>
                    <%
                        session.invalidate();
                    %>
                    <br>
                    <h2>You have successully logged out of the system</h2>
                </td>
            </tr>
        </table>
    </body>
</html>
