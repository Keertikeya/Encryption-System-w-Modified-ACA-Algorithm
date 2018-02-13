<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


<html>
  <head>
    <link rel="stylesheet" href="fcol.css">
      
    <title>Login</title>
    <script language="javascript">
        function chk()
        {
            var u, p;
            u = document.f.uid.value;
            p = document.f.pass.value;
            
            if(u.length == 0 || p.length == 0)
            {
                alert("uid/pass missing ");
                return false;
            }
            return true;
        }
    </script>

  </head>
    <body >
  <table width = "100%">
    <tr >
        <td colspan="2" > <jsp:include page="titlebar.jsp" />
    </tr>
    <tr bgcolor="#9999FF" align="right">
        <td colspan="2">
            <a href = index.jsp> Home</a>
        </td>
    </tr>
    
    <tr>
        <td colspan="2">
            <h2>Login</h2>
        </td>
    </tr>    
    
    <tr>
        <td width="50%" align="center">
            <img src = "login.jpg" width="425" height="314">
        </td>
        <td width="50%" align="left" valign="top">
            
           <form name="f" action="validate.jsp" method="POST" onsubmit="return chk()">
                <table border="0" width="75%">
                    <tr>
                      <td>User ID</td>
                      <td><input type="text" name="uid" value="" size="23" /></td>
                    </tr>
                    <tr>
                      <td>Password</td>
                      <td><input type="password" name="pass" value="" size="25"/></td>
                    </tr>
           
                    <tr>
                      <td>&nbsp;</td>
                      <td><input type="submit" value="login" name="login" /></td>
                    </tr>
                </table>
            </form>
        </td>
    </tr>
  </table>
  </body>
</html>

