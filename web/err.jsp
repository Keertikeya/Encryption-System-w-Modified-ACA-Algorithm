<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page isErrorPage="true"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
    <table width = 100%>
        <tr>
            <td> <jsp:include page="titlebar.jsp"/> </td>
        </tr>
        <tr>
           
            <td> 
                
                <%
                    
                    out.println("Error : " + pageContext.getException().toString());
                %>
            </td>
        </tr>
    </table>
    </body>
</html>
