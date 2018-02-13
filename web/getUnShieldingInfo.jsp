<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get UnShielding Information</title>
        <script language="javascript">

        function chk()
        {
            var a;

            a = document.f.fileUnShield.value;

            if(a.length === 0 )
            {
                alert("File Not Selected");
                return false;
            }
            return true;
        }


        </script>
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
        <td colspan="2"> <jsp:include page="titlebar.jsp" /> </td>
    </tr>
    <tr>
        <td align="right" colspan="2" bgcolor = "#9999FF"> <jsp:include page = "navbar.jsp" /></td>
    </tr>
    <tr>
        <td colspan="2" > <h2>UnShield</h2></td>
    </tr>

    <tr>
        <td align="center"><img src = register.jpg width="250" height="150"></td>

        <td width="50%" align = "center">

            <form name = f action="unshieldIT.jsp"  method = post enctype="multipart/form-data" onsubmit="return chk()">
                <table border="0" width="100%">
                    <tr>
                        <%
                            int sid = Integer.parseInt(request.getParameter("chkbx"));
                            datasentinel.DB ref = datasentinel.DB.getInstance();
                            String pin = ref.getShieldingPin(sid);
                            out.println("<input type = \"hidden\" name = \"spin\" value = \""+ pin +"\" >" );
                        %>
                        <td>Select File To UnShield</td>
                        <td><input type="file" name="dataFile" value="" /></td>
                    </tr>
                   
                    <tr>
                        <td >&nbsp;</td>
                        <td><input type = "submit" value = "UnShield" name = "bttnSubmit" /></td>
                    </tr>
                </table>

                

            </form>
            </td>
        </tr>
    </table>
    </body>
</html>
