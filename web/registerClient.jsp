<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<jsp:useBean class="datasentinel.Client" id="clnt" scope="page" />
<jsp:setProperty name="clnt" property="*" />
   

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Client Registration</title>

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
                    if(clnt.addClient())
                    {
                        out.println("<h2>Client Registered Successfully</h2>");
                        out.println("<h2><a href = login.jsp>login</a></h2>");
                    }
                    else
                    {
                        out.println("<h2>Client Registration Failed</h2>");
                    }
                %>

            </td>
        </tr>
    </table>
    </body>
</html>
