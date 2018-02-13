<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<jsp:useBean class="datasentinel.Sharings" id="shr" scope="page" />

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Sharing</title>
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
                    <td colspan="2"> <jsp:include page="titlebar.jsp" />
                </tr>
                <tr align="right" bgcolor = "#9999FF">
                    <td > <jsp:include page="navbar.jsp"/> </td>
                </tr>
                <tr>
                    <td>
                    <%
                        try
                        {
                            String ids[] = request.getParameterValues("chkbx");
                            if(ids != null)
                            {
                                String cid = request.getParameter("client");
                                shr.addSharings( ids, Integer.parseInt(cid));
                                out.println("<h2>Sharing Set</h2>");
                            }
                        }

                        catch(Exception ex)
                        {
                            out.println("<h2>Error Setting Sharing </h2>");
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
