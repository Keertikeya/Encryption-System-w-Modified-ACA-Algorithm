<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>
    
<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Get New Password</title>
        <script language="javascript">
        function chk()
        {
            var a,b;
            
            a = document.f.currentpass.value;
            b = document.f.newpass.value;
            c = document.f.repass.value;
            
            if(a.length == 0 || b.length == 0  || c.length== 0)
            {
                alert("Data missing ");
            }
            else if(b != c)
            {
                alert("Password and Reentered password mismatch");
            }
            else if(b.length < 6)
            {
                alert("Password must be min 6 characters in length ");
            }
            else
            {
                document.f.submit();
            }
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
    else
    {

    %>
        <table width="100%">
            <tr>
                <td colspan="2" > <jsp:include page="titlebar.jsp" /> </td>
            </tr>
            <tr>
                <td align="right" colspan="2" bgcolor = "#9999FF"> <jsp:include page = "navbar.jsp" /> </td>
            </tr>
            <tr>    
                <td align="center">
                    <h2>Change Password</h2>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        
                    <img src="chpass.jpg" width="250" height="250"> 
                    
                </td>
                
                <td>
                
                <form name="f" method="post" action="chgpass.jsp">
                <table width="100%">
                    <tr>
                        <td>Enter Current Password</td>
                        <td><input type="password" name = "currentpass"></td>
                    </tr>
                    <tr>
                        <td>Enter New Password</td>
                        <td><input type="password" name = "newpass"></td>
                    </tr>
                    <tr>
                        <td>ReEnter New Password</td>
                        <td><input type="password" name = "repass"></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="button" name = "bttnSubmit" value="Change Password" onclick="chk()" ></td>
                    </tr>                    
                </table>
                </form>
            </td>
        </tr>
    </table>
    <%
    }
    %>
    </body>
</html>
