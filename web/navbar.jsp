<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

 <%
    String uid = "";
    String type = "";
    try
    {
        uid = session.getAttribute("uid").toString();
        type = session.getAttribute("type").toString();
    }
    catch(Exception e)
    {}
%>

<table>
    <tr>
        <td>
            <%
            if(type.equals("Admin"))
            {
            %>
            <a href = manageClients.jsp>Manage Clients</a> |
            <a href = getnewpass.jsp>Change Password</a> |
            <a href = logout.jsp>Logout</a>
            <%
            }
            else if(type.equals("Client"))
            {
            %>
            <a href = getShieldingInfo.jsp>Shield</a> |
            <a href = getUnshieldOptions.jsp>UnShield</a> |
            <a href = unshieldShared.jsp>UnShield Sharings</a> |
            <a href = manageShieldings.jsp>Manage Shieldings</a> |
            <a href = getSharingInfo.jsp>Add Sharings</a> |
            <a href = manageSharings.jsp>Manage Sharings</a> |
            <a href = shareWithNonUsers.jsp>Share With NonClients</a> |
            <a href = getUserImage.jsp>User Image</a> |
            <a href = getnewpass.jsp>Change Password</a> |
            <a href = logout.jsp>Logout </a> 
           
            <%
            }
            else
            {
                out.println("&nbsp");
            }
            %>
        </td>
    </tr>
</table>
