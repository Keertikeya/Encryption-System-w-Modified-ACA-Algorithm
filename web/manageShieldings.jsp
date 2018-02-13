<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page errorPage="err.jsp" %>
<%@page import="java.util.*" %>


<jsp:useBean id="shld"  class= "datasentinel.Shieldings" scope="page" />

<html>
    <head>
        <link rel="stylesheet" href="fcol.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" language="javascript">

            function selAll()
            {
                var selection;
                var i;
                if(document.f.chkbx.length == null)
                {
                    selection = document.f.chkbx.checked;
                    selection = !selection;
                    if(selection == true)
                    {
                        document.f.bttnSelect.value = "UnSelect All";
                    }
                    else
                    {
                        document.f.bttnSelect.value = "Select All";
                    }
                    document.f.chkbx.checked = selection;
                }
                else
                {
                    selection = document.f.chkbx[0].checked;
                    selection = !selection;
                    if(selection == true)
                    {
                        document.f.bttnSelect.value = "UnSelect All";
                    }
                    else
                    {
                        document.f.bttnSelect.value = "Select All";
                    }

                    for(i =0 ; i< document.f.chkbx.length; i++)
                    {
                        document.f.chkbx[i].checked = selection;
                    }
                }
            }

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
                alert("Select Shielding To Delete");
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
            try
            {
                String ids[] = request.getParameterValues("chkbx");
                if(ids != null)
                {
                    shld.delShieldings(ids);
                }
            }
            catch(Exception ex)
            {}
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
                LinkedList<LinkedList<Object>> rows ;
                LinkedList<Object> cols;
                LinkedList<String> fields;

                ListIterator<LinkedList<Object>> ri ;
                ListIterator<Object> ci ;
                ListIterator<String> fi ;


                fields = shld.getShieldingFields();
                fi = fields.listIterator();

                rows = shld.getShieldingsClientwise(uid);
                ri = rows.listIterator();


                if(rows.size() >0)
                {
                    out.println("<h2>Shieldings</h2>");
                    out.println("<form name= f method= \"post\" action= \"manageShieldings.jsp\" onsubmit= \"return chk()\" >");
                    out.println("<tr bgcolor=\"#9999FF\">");
                    while(fi.hasNext())
                    {
                        if(flag == true)
                        {
                            out.println("<td width= \"10%\" >Select </td>");
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
                    out.println("<tr>");
                    out.println("<td >");
                    out.println("<input type = \"button\" name = \"bttnSelect\" value = \"Select All\" onClick = \"selAll()\">");
                    out.println("</td>");
                    out.println("<td >");
                    out.println("<input type = \"submit\" name = \"bttnDelete\" value = \"Delete\">");
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
