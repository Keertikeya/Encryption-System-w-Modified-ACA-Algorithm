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
                            String pins[] = request.getParameterValues("chkbx");
                            if(pins != null)
                            {
                                String phoneNo = request.getParameter("txtMobile");
                                datasentinel.SMSWriter msgSndr = new datasentinel.SMSWriter();
                                int i;
                                datasentinel.DB ref = datasentinel.DB.getInstance();
                                String shieldingId, ncPin;
                                for(i= 0; i< pins.length; i++)
                                {
                                    shieldingId = pins[i].trim();
                                    ncPin = datasentinel.PinGenerator.getNextPin().trim();
                                    if(ref.addNonClientSharing(Integer.parseInt(shieldingId), ncPin, phoneNo))
                                    {
                                        msgSndr.writeSMS(phoneNo, "24 Hour Valid Unshielding pin : " + ncPin);
                                    }
                                }
                                out.println("<h2>Sharing Pin(s) Sent To " + phoneNo +" </h2>");
                            }
                        }

                        catch(Exception ex)
                        {
                            ex.printStackTrace();
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
