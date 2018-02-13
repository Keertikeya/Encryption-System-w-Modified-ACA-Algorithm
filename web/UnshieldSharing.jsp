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
            var a,b;

            a = document.f.dataFile.value;
            b = document.f.spin.value;
	    if(a.length === 0)
            {
                alert("File Not Selected");
                return false;
            }
            else if(b.length === 0)
            {
                alert("Pin Not Entered");
                return false;
            }

            return true;
        }


        </script>
    </head>
    <body>

  <table width = 100%>
    <tr>
        <td colspan="2"> <jsp:include page="titlebar.jsp" /> </td>
    </tr>
    <tr>
        <td align="right" colspan="2" bgcolor = "#9999FF"> <a href ="index.jsp">Home</a> </td>
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
                        <td>
                           Enter Pin
                        </td>
                        <td>
                           <input type = "text" name = "spin" >
                        </td>
                    </tr>

                    <tr>
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
