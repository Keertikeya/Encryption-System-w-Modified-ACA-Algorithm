package datasentinel;

import java.sql.*;
import java.util.*;

public class DB 
{
    private PreparedStatement pstmtSetLogin, pstmtChgPass, pstmtLogin, pstmtDelLogin, pstmtGetId;
    private PreparedStatement pstmtAddClient, pstmtDeleteClient, pstmtViewAllClients, pstmtClientDetails, pstmtClientsOtherThanUser;
    private PreparedStatement pstmtAddShielding, pstmtgetShieldingsClientwise, pstmtDeleteShielding, pstmtGetKeyShieldingwise, pstmtGetPinShieldingwise, pstmtGetPinNonClientSharingwise;
    private PreparedStatement pstmtGetShieldingFilePinwise, pstmtGetShieldingFileIdwise, pstmtGetKeyPinwise;
    private PreparedStatement pstmtAddSharing, pstmtgetSharingsClientwise, pstmtRemoveSharing;
    private PreparedStatement pstmtGetSharings, pstmtMatchUserNPin,  pstmtDeleteSharingShieldingwise, pstmtDeleteSharingShieldingwise1;
    private PreparedStatement pstmtAddNonClientSharing, pstmtDeleteNonClientSharing, pstmtValidateNonClientSharing, pstmtDeleteNonClientSharingShieldingwise, pstmtDeleteNonClientSharingNCPinwise;
    
    private Connection conn;
    private static DB ref= null;

    private DB() throws Exception
    {
        String driver, url, uid, pass;
        driver = "oracle.jdbc.OracleDriver";
        url = "jdbc:oracle:thin:@//localhost:1521/xe";
        uid = "datasentinel";
        pass = "data99";
  
        //load the database drivers in memory
        Class.forName(driver);
        //create the connection
        conn = DriverManager.getConnection(url, uid, pass);
        
        
        pstmtLogin = conn.prepareStatement("select usertype from login where username = ? and userpass = ?");
        pstmtSetLogin = conn.prepareStatement("insert into login values(?,?,?,?)");
        pstmtDelLogin = conn.prepareStatement("delete from login where cid = ? and usertype = ?");
        pstmtChgPass = conn.prepareStatement("update login set userpass = ? where username = ? and userpass = ?");
        pstmtGetId= conn.prepareStatement("select cid from login where username = ?");
        
        pstmtAddClient = conn.prepareStatement("insert into client values (?,?,?,?,?,?)");
        pstmtDeleteClient = conn.prepareStatement("delete from client where cid = ?");
        pstmtViewAllClients = conn.prepareStatement("select * from client");
        pstmtClientDetails = conn.prepareStatement("select * from client where cid = ?");
        pstmtClientsOtherThanUser = conn.prepareStatement("select cid, cname from client where cid not in (select cid from login where username = ?)");

        pstmtAddShielding = conn.prepareStatement("insert into shielding values (?,?,?,?,?,?)");
        pstmtgetShieldingsClientwise = conn.prepareStatement("select shieldingid, shieldingremark, shieldingpin, shieldingdate from shielding where cid = (select cid from login where username = ?)");
        pstmtDeleteShielding = conn.prepareStatement("delete from shielding where shieldingid = ?");

        pstmtGetShieldingFileIdwise= conn.prepareStatement("select shieldingKeyFile from shielding where shieldingid = ?");
        pstmtGetShieldingFilePinwise = conn.prepareStatement("select shieldingKeyFile from shielding where shieldingpin = ?");
        
        pstmtGetKeyShieldingwise = conn.prepareStatement("select shieldingremark from shielding where shieldingid = ?");
        pstmtGetPinShieldingwise= conn.prepareStatement("select shieldingpin from shielding where shieldingid = ?");
        pstmtGetKeyPinwise = conn.prepareStatement("select shieldingremark from shielding where shieldingpin = ?");
        pstmtGetPinNonClientSharingwise = conn.prepareStatement("select shieldingpin from shielding where shieldingid = (select shieldingid from nonclientsharing where ncsharingpin = ?)");
        
        pstmtAddSharing = conn.prepareStatement("insert into sharing values (?,?,?)");
        pstmtgetSharingsClientwise = conn.prepareStatement("select sharingid, shieldingremark, cname from sharing inner join shielding on sharing.shieldingid = shielding.shieldingid inner join client on sharing.sharedWith = client.cid where shielding.cid = (select cid from login where username =?)");
        pstmtRemoveSharing =conn.prepareStatement("delete from sharing where sharingid = ?");

        pstmtGetSharings = conn.prepareStatement("select shielding.shieldingid, shieldingremark, cname from sharing inner join shielding on sharing.shieldingid = shielding.shieldingid inner join client on shielding.cid = client.cid where sharedWith = (select cid from login where username =?)");

        pstmtMatchUserNPin = conn.prepareStatement("select * from shielding where shieldingpin = ? and cid in( select cid from login where username = ?)");
        
        pstmtDeleteSharingShieldingwise = conn.prepareStatement("delete from sharing where shieldingid = ?");
        pstmtDeleteSharingShieldingwise1= conn.prepareStatement("delete from sharing where shieldingid = (select shieldingid from shielding where trim(shieldingpin) = ?) and sharedWith = ?");
    
        pstmtAddNonClientSharing = conn.prepareStatement("insert into nonclientsharing values (?,?,?,?,?)");
        pstmtDeleteNonClientSharing = conn.prepareStatement("delete from nonclientsharing where ncsharingpin = ?");
        pstmtValidateNonClientSharing= conn.prepareStatement("select ncsharingtime from nonclientsharing where ncsharingpin = ?");
        pstmtDeleteNonClientSharingShieldingwise = conn.prepareStatement("delete from nonclientsharing where shieldingid = ?");
        pstmtDeleteNonClientSharingNCPinwise = conn.prepareStatement("delete from nonclientsharing where ncsharingpin = ?");
                
    }

    public static DB getInstance()
    {
        if(ref == null)
        {
            try
            {
                ref = new DB();
            }
            catch(Exception e)
            {
                ref = null;
            }
        }
        return ref;
    }

    private int getNextId(String col, String table) throws SQLException
    {
        int id = 1;
        Statement stmt = conn.createStatement();	
        String qry = "select max(" + col + ") from " + table;
        ResultSet rs = stmt.executeQuery(qry);
        if(rs.next())
        {
            id = rs.getInt(1);
            id++;
        }
        return id;
    } 

    public boolean chgPass(String uid, String pass, String newpass)
    {
        try
        {
            pstmtChgPass.setString(1, newpass);
            pstmtChgPass.setString(2, uid);
            pstmtChgPass.setString(3, pass);
            
            int res = pstmtChgPass.executeUpdate();
            return res > 0;
        }
        catch(SQLException e)
        {
            System.out.println("Error in chgPass");
            return false;
        }
    }
    
    public String  validateLogin(String uid , String pass)
    {
        try
        {
            String type = null;
            pstmtLogin.setString(1, uid);
            pstmtLogin.setString(2, pass);
            ResultSet rs = pstmtLogin.executeQuery();
            if(rs.next())
            {
                type = rs.getString(1);
            }
            rs.close();
            return type;
                
        }
        catch(SQLException e)
        {
            System.out.println("Error in validateLogin " + e);
            return null;
        }
    }
    
    public boolean delLogin(int id , String type)
    {
        try
        {
            pstmtDelLogin.setInt(1, id);
            pstmtDelLogin.setString(2, type);
            pstmtDelLogin.executeUpdate();
            return true;
        }
        catch(SQLException e)
        {
            System.out.print("Error in del Login " + e);    
            return false;
        }
    }

    public int getId(String uid)
    {
        int id = -1;
        try
        {
            pstmtGetId.setString(1, uid);
            ResultSet rs = pstmtGetId.executeQuery();
            if(rs.next())
            {
                id = rs.getInt(1);
            }
            rs.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Error in getId " + ex);
        }
        return id;
    }
    
    boolean addClient(String name, String phone, String address, String email, String imgLoc, String uid, String pass)
    {
        try
        {
            int id = getNextId("cid", "client");
            
            pstmtSetLogin.setString(1, uid);
            pstmtSetLogin.setString(2, pass);
            pstmtSetLogin.setString(3, "Client");
            pstmtSetLogin.setInt(4, id);
            pstmtSetLogin.executeUpdate();

            pstmtAddClient.setInt(1, id);
            pstmtAddClient.setString(2, name);
            pstmtAddClient.setString(3, phone);
            pstmtAddClient.setString(4, address);
            pstmtAddClient.setString(5, email);
            pstmtAddClient.setString(6, imgLoc);

            pstmtAddClient.executeUpdate();
            
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in addClient : " + e);
            return false;
        }
    }    
    
    boolean delClient(int cid)
    {
      try
      {
        delLogin(cid, "Client");
        
	pstmtDeleteClient.setInt(1, cid);
	pstmtDeleteClient.executeUpdate();
        
	return true;
      }
      catch(SQLException e)
      {
        System.out.println("Error in delClient : " + e);
	return false;
      }
    }
    
    LinkedList<LinkedList<Object>> getAllClients()
    {
        LinkedList<LinkedList<Object>> rows = new LinkedList<LinkedList<Object>>();
        try
        {
            ResultSet rs = pstmtViewAllClients.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();
            
            int i;
            LinkedList<Object> cols;

            while(rs.next())
            {
                cols = new LinkedList<Object>();
                for(i = 1; i<= colcount; i++)
                {
                    cols.add( rs.getObject(i));
                }
                rows.add(cols);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getAllClients : " + e);
        }
        return rows;
    }

    LinkedList<LinkedList<Object>> getClientsOtherThanUser(String uid)
    {
        LinkedList<LinkedList<Object>> rows = new LinkedList<LinkedList<Object>>();
        try
        {
            pstmtClientsOtherThanUser.setString(1, uid);
            ResultSet rs = pstmtClientsOtherThanUser.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();

            int i;
            LinkedList<Object> cols;

            while(rs.next())
            {
                cols = new LinkedList<Object>();
                for(i = 1; i<= colcount; i++)
                {
                    cols.add( rs.getObject(i));
                }
                rows.add(cols);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getClientsOtherThanUser : " + e);
        }
        return rows;
    }
    
    LinkedList<Object> getClientDetails(int cid)
    {
        LinkedList<Object> details = new LinkedList<Object>();
        try
        {
            pstmtClientDetails.setInt(1, cid);
            ResultSet rs = pstmtClientDetails.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();
            
            int i;
            if(rs.next())
            {
                for(i = 1; i<= colcount; i++)
                {
                    details.add( rs.getObject(i));
                }
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getClientDetails : " + e);
        }
        return details;
    }

    public boolean addShielding(String remark, String pin, String keyFile, java.util.Date date, int cid)
    {
        try
        {
            int id = getNextId("shieldingid", "shielding");

            java.sql.Date dt = new java.sql.Date(date.getTime());
            pstmtAddShielding.setInt(1, id);
            pstmtAddShielding.setString(2, remark);
            pstmtAddShielding.setString(3, pin);
            pstmtAddShielding.setString(4, keyFile);
            pstmtAddShielding.setDate(5, dt);
            pstmtAddShielding.setInt(6, cid);

            pstmtAddShielding.executeUpdate();

            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in addShielding : " + e);
            return false;
        }
    }


    LinkedList<LinkedList<Object>> getShieldingsClientwise(String uid)
    {
        LinkedList<LinkedList<Object>> rows = new LinkedList<LinkedList<Object>>();
        try
        {
            pstmtgetShieldingsClientwise.setString(1, uid);
            ResultSet rs = pstmtgetShieldingsClientwise.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();

            int i;
            LinkedList<Object> cols;

            while(rs.next())
            {
                cols = new LinkedList<Object>();
                for(i = 1; i<= colcount; i++)
                {
                    cols.add( rs.getObject(i));
                }
                rows.add(cols);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingsClientwise : " + e);
        }
        return rows;
    }


    boolean delShielding(int sid)
    {
      try
      {

	pstmtDeleteShielding.setInt(1, sid);
	pstmtDeleteShielding.executeUpdate();

        pstmtDeleteSharingShieldingwise.setInt(1, sid);
        pstmtDeleteSharingShieldingwise.executeUpdate();
        
        pstmtDeleteNonClientSharingShieldingwise.setInt(1, sid);
        pstmtDeleteNonClientSharingShieldingwise.executeUpdate();
        
	return true;
      }
      catch(SQLException e)
      {
        System.out.println("Error in delShielding : " + e);
	return false;
      }
    }
    
    String getShieldingFilePinwise(String pin)
    {
        String file="";
        try
        {
            pstmtGetShieldingFilePinwise.setString(1, pin);
            ResultSet rs = pstmtGetShieldingFilePinwise.executeQuery();

            if(rs.next())
            {
                file = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingFilePinwise : " + e);
        }
        return file;
    }

    String getShieldingFileIdwise(int sid)
    {
        String file="";
        try
        {
            pstmtGetShieldingFileIdwise.setInt(1, sid);
            ResultSet rs = pstmtGetShieldingFileIdwise.executeQuery();

            if(rs.next())
            {
                file = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingFileIdwise : " + e);
        }
        return file;
    }

    
    String getShieldingKey(int sid)
    {
        String key="";
        try
        {
            pstmtGetKeyShieldingwise.setInt(1, sid);
            ResultSet rs = pstmtGetKeyShieldingwise.executeQuery();
            if(rs.next())
            {
                key = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingKey : " + e);
        }
        return key;
    }

    public String getShieldingPin(int sid)
    {
        String pin="";
        try
        {
            pstmtGetPinShieldingwise.setInt(1, sid);
            ResultSet rs = pstmtGetPinShieldingwise.executeQuery();

            if(rs.next())
            {
                pin = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingPin : " + e);
        }
        return pin;
    }

    String getShieldingKeyPinwise(String pin)
    {
        String key="";
        try
        {
            pstmtGetKeyPinwise.setString(1, pin);
            ResultSet rs = pstmtGetKeyPinwise.executeQuery();

            if(rs.next())
            {
                key = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingKeyPinwise : " + e);
        }
        return key;
    }

    public String getShieldingPinNonClientSharingwise(String ncPin)
    {
        String pin="";
        try
        {
            pstmtGetPinNonClientSharingwise.setString(1, ncPin);
            ResultSet rs = pstmtGetPinNonClientSharingwise.executeQuery();

            if(rs.next())
            {
                pin = rs.getString(1);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getShieldingPinNonClientSharingwise : " + e);
        }
        return pin;
    }

    
    boolean addSharing(int sid, int cid)
    {
        try
        {
            int id = getNextId("sharingid", "sharing");

            pstmtAddSharing.setInt(1, id);
            pstmtAddSharing.setInt(2, sid);
            pstmtAddSharing.setInt(3, cid);
            
            pstmtAddSharing.executeUpdate();

            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in addSharing : " + e);
            return false;
        }
    }


    LinkedList<LinkedList<Object>> getSharingsClientwise(String uid)
    {
        LinkedList<LinkedList<Object>> rows = new LinkedList<LinkedList<Object>>();
        try
        {
            pstmtgetSharingsClientwise.setString(1, uid);
            ResultSet rs = pstmtgetSharingsClientwise.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();

            int i;
            LinkedList<Object> cols;

            while(rs.next())
            {
                cols = new LinkedList<Object>();
                for(i = 1; i<= colcount; i++)
                {
                    cols.add( rs.getObject(i));
                }
                rows.add(cols);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getSharingsClientwise : " + e);
        }
        return rows;
    }


    boolean removeSharing(int shid)
    {
      try
      {

	pstmtRemoveSharing.setInt(1, shid);
	pstmtRemoveSharing.executeUpdate();

	return true;
      }
      catch(SQLException e)
      {
        System.out.println("Error in removeSharing : " + e);
	return false;
      }
    }

    LinkedList<LinkedList<Object>> getSharings(String uid)
    {
        LinkedList<LinkedList<Object>> rows = new LinkedList<LinkedList<Object>>();
        try
        {
            pstmtGetSharings.setString(1, uid);
            ResultSet rs = pstmtGetSharings.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int colcount = rsmd.getColumnCount();

            int i;
            LinkedList<Object> cols;

            while(rs.next())
            {
                cols = new LinkedList<Object>();
                for(i = 1; i<= colcount; i++)
                {
                    cols.add( rs.getObject(i));
                }
                rows.add(cols);
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in getSharings : " + e);
        }
        return rows;
    }

    public void manageGuestSharingOption(String ncpin)
    {
        try
        {
            pstmtDeleteNonClientSharingNCPinwise.setString(1, ncpin);
            pstmtDeleteNonClientSharingNCPinwise.executeUpdate();
                
            
        }
        catch(SQLException ex)
        {
            System.out.println("err "+ ex);
        }
    }

            
    public void manageSharingOption(String uid, String pin)
    {
        try
        {
            pstmtMatchUserNPin.setString(1, pin);
            pstmtMatchUserNPin.setString(2, uid);

            ResultSet rs = pstmtMatchUserNPin.executeQuery();
            if(!rs.next())
            {

                int id = getId(uid);
                
                pstmtDeleteSharingShieldingwise1.setString(1, pin);
                pstmtDeleteSharingShieldingwise1.setInt(2, id);
                pstmtDeleteSharingShieldingwise1.executeUpdate();
                
            }
            rs.close();

        }
        catch(SQLException ex)
        {
            System.out.println("err "+ ex);
        }
    }

 
    public boolean addNonClientSharing(int shieldingid, String pin, String phoneNum)
    {
        try
        {
            int id = getNextId("ncsharingid", "nonclientsharing");

            pstmtAddNonClientSharing.setInt(1, id);
            pstmtAddNonClientSharing.setInt(2, shieldingid);
            pstmtAddNonClientSharing.setString(3, phoneNum);
            pstmtAddNonClientSharing.setString(4, pin);
            
            java.sql.Date dt = new java.sql.Date(System.currentTimeMillis());
            pstmtAddNonClientSharing.setDate(5, dt);
            pstmtAddNonClientSharing.executeUpdate();
            
            return true;
        }
        catch(SQLException e)
        {
            System.out.println("Error in add NonClient Sharing : " + e);
            return false;
        }
    }

    boolean removeNonClientSharing(String pin)
    {
      try
      {

	pstmtDeleteNonClientSharing.setString(1, pin);
        
	pstmtDeleteNonClientSharing.executeUpdate();

	return true;
      }
      catch(SQLException e)
      {
        System.out.println("Error in removeNonClientSharing : " + e);
	return false;
      }
    }

    public boolean validateNonClientSharing(String pin)
    {
        boolean flag = false;
        try
        {
            pstmtValidateNonClientSharing.setString(1, pin);
            
            ResultSet rs = pstmtValidateNonClientSharing.executeQuery();

            if(rs.next())
            {
                long l1 = rs.getDate(1).getTime();
                long l2 = System.currentTimeMillis();
                flag  = (l2 - l1) < (24*60*60*1000);
                if(flag == false)
                    removeNonClientSharing(pin);
            }
            
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Error in validateNonClientSharings : " + e);
        }
        return flag;
    }

}