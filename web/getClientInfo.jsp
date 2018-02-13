<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get Client Information</title>
        <script language="javascript">
        function chk()
        {
            var a,b,c,d,e,f,g,h;
            
            a = document.f.clientName.value;
            b = document.f.clientPhone.value;
            c = document.f.clientAddress.value;
            d = document.f.clientEmail.value;
            e = document.f.clientImage.value;
                
            f = document.f.uid.value;
            g = document.f.pass.value;
            h = document.f.repass.value;
            
            if(a.length === 0 || b.length === 0 || c.length === 0 || d.length === 0 || e.length === 0 || f.length === 0 || g.length === 0 || h.length === 0)
            {
                alert("Data missing ");
                return false;
            }
            else if(g.length < 6)
            {
                alert("Password should be min 6 characters in length");
                return false;
            }
            else if(g !== h)
            {
                alert("Password and ReEntered Password Mismatchshould be min 6 characters in length");
                return false;
            }
            else if(b.length !== 10)
            {
                alert("Phone Number Should be 10 Digits in Length");
                return false;
            }
            else
            {
                return true;
            }
        }
        
            
        </script>
    </head>
    <body>
  <table width = 100%>
    <tr>
        <td colspan="2"> <jsp:include page="titlebar.jsp" /> </td>
    </tr>
    <tr>
        <td align="right" colspan="2" bgcolor = "#9999FF"> <a href ="index.jsp" > Home </a> </td>
    </tr>
    <tr>
        <td colspan="2" > <h2>Register Client</h2></td>
    </tr>

    <tr>
        <td align="center"><img src = register.jpg width="250" height="150"></td>
        
        <td width="50%" align = "center"> 
            
            <form name = f action="registerClient.jsp"  method = "post"  onsubmit="return chk()">
                <table border="0" width="100%">
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="clientName" value="" /></td>
                    </tr>
                    <tr>
                        <td>Phone</td>
                        <td><input type="text" name="clientPhone" value="" /></td>
                    </tr>                    
                    <tr>
                        <td>Address</td>
                        <td><input type="text" name="clientAddress" value="" /></td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td><input type="email" name="clientEmail" value="" /></td>
                    </tr>

                    <tr>
                        <td>Client Image</td>
                        <td><input type="file" name="clientImage" value="" /></td>
                    </tr>

                    <tr>
                        <td>User Name</td>
                        <td><input type="text" name="uid" value=""/></td>
                    </tr>            
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="pass" value=""/></td>
                    </tr>
                    <tr>
                        <td>ReEnter Password</td>
                        <td><input type="password" name="repass" value=""/></td>
                    </tr>
                    
                    <tr>
                        <td >&nbsp;</td>
                        <td><input type = "submit" value = "Register" name = "register" /></td>
                    </tr>
                </table>

            </form>
            </td>
        </tr>
    </table>
    </body>
</html>
