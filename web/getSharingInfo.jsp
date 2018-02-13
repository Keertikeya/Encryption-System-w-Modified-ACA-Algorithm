<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>
<%@page import="java.util.*" %>


<jsp:useBean id="shld"  class= "datasentinel.Shieldings" scope="page" />
<jsp:useBean id="clnt"  class= "datasentinel.Client" scope="page" />

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" language="javascript">

            function chk()
            {
                var i;
                if(document.f.chkbx.length == null)
                {
                    if(document.f.chkbx.checked)
                        return true;
                }
                for(i =0 ; i< document.f.chkbx.length; i++)
                {
                    if(document.f.chkbx[i].checked)
                        return true;
                }
                alert("Select Shielding To Share");
                return false;
            }
        </script>
    </head>
    <body >
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

        <table width="100%">
        <tr>
            <td> <jsp:include page="titlebar.jsp" /> </td>
        </tr>
        <tr bgcolor="#9999FF" align="right">
            <td> <jsp:include page = "navbar.jsp" /> </td>
        </tr>
        <tr>
            <td>

            <table width="100%">
            <%
                boolean flag = true;
                LinkedList<LinkedList<Object>> rows, rows1;
                LinkedList<Object> cols, cols1;
                LinkedList<String> fields;

                ListIterator<LinkedList<Object>> ri,ri1;
                ListIterator<Object> ci;
                ListIterator<String> fi;


                fields = shld.getShieldingFields();
                fi = fields.listIterator();

                rows = shld.getShieldingsClientwise(uid);
                ri = rows.listIterator();

                rows1 = clnt.getClientsOtherThanUser(uid);
                ri1 = rows1.listIterator();

                if(rows.size() >0)
                {
                    out.println("<h2>Sharings</h2>");
                    out.println("<form name= f method= \"post\" action= \"addSharing.jsp\" onsubmit= \"return chk()\" >");

                    out.println("<tr>");
                    out.println("<td> Client </td>");
                    out.println("<td>");
                    out.println("<select name = \"client\">");
                    while(ri1.hasNext())
                    {
                        cols1 = ri1.next();
                        if(!uid.equals(cols1.get(1).toString()))
                        {
                            out.println("<option value = "+ cols1.get(0) +"  > "+ cols1.get(1) +" </option>" );
                        }
                    }
                    out.println("</select>");
                    out.println("</td>");
                    out.println("</tr>");

                    out.println("<tr bgcolor=\"#9999FF\">");
                    while(fi.hasNext())
                    {
                        if(flag == true)
                        {
                            out.println("<td width= \"8%\" >Select </td>");
                            flag = false;
                            fi.next();
                        }
                        else
                        {
                            out.println("<td>"+ fi.next()+"</td>");
                            flag = false;
                        }
                    }
                    out.println("</tr>");

                    while(ri.hasNext())
                    {
                        out.println("<tr bgcolor=#E3E3FF>");
                        cols = ri.next();
                        ci = cols.listIterator();
                        flag = true;
                        while(ci.hasNext())
                        {
                            if(flag == true)
                            {
                                out.println("<td>");
                                out.println("<input type = checkbox  value = "+ ci.next() + " name = chkbx>");
                                out.println("</td>");

                                flag = false;
                            }
                            else
                            {

                                out.println("<td>"+ci.next()+"</td>");
                            }
                        }
                        out.println("</tr>");

                    }
                    out.println("<input type = \"hidden\" name = \"action\" value=\"\"  ");
                    out.println("<tr>");
                    out.println("<td>");
                    out.println("<input type = \"submit\" name = \"bttnShare\" value = \"Share\" onClick= \"share()\" >");
                    out.println("</td>");
                    out.println("</tr>");
                    out.println("</form>");

                    rows.clear();
                    fields.clear();
                }
                else
                {
                    out.println("<h2>No Shieldings Done</h2>");
                }
                %>
            </table>

            </td>
        </tr>
        </table>
    </body>
</html>
