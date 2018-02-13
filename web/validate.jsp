<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<jsp:useBean id="lgn"  class= "datasentinel.Validate" scope="page" />
<jsp:setProperty name="lgn" property="*" />   


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="fcol.css"> 
    </head>
    <body>
        <table width = "100%" border = 0>
            <tr>
                <td> <jsp:include page="titlebar.jsp" />
            </tr>
            
            <tr bgcolor="#9999FF" align="right">
                <td >
                    &nbsp;
                </td>
            </tr>
            
            <%
            String userType = lgn.isValid();
            if(userType != null)
            {
                session.setAttribute("uid", lgn.getUid());
                session.setAttribute("type", userType);
                response.sendRedirect("userhome.jsp");
            }
            %>
            <tr>
                <td>
                    <h2>Login Failed</h2>
                    <a href="login.jsp">Click</a> here to Retry.
                </td>
            </tr>
        </table>
    </body>
</html>
