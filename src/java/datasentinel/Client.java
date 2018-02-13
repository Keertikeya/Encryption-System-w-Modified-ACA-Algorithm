package datasentinel;

import java.util.*;

public class Client
{
    private String clientName, clientPhone, clientAddress, clientEmail, clientImage, uid, pass;
 
    public Client() 
    {}
    
    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone)
    {
        this.clientPhone = clientPhone;
    }
            
    public void setClientAddress(String clientAddress)
    {
        this.clientAddress = clientAddress;
    }            
            
    public void setClientEmail(String clientEmail)
    {
        this.clientEmail = clientEmail;
    }
 
    public void setClientImage(String clientImage)
    {
        this.clientImage = clientImage;
    }
            
    public void setUid(String uid) 
    {
        this.uid = uid;
    }
            
    public void setPass(String pass) 
    {
        this.pass = pass;
    }

    public String getClientName()
    {
        return clientName;
    }
    
    public String getClientPhone()
    {
        return clientPhone;
    }

    public String getClientAddress()
    {
        return clientAddress;
    }
    
    
    public String getClientEmail()
    {
        return clientEmail;
    }

    
    public boolean addClient()
    {
        DB ref = DB.getInstance();
        return ref.addClient(clientName, clientPhone, clientAddress, clientEmail, clientImage, uid, pass);
    }
    
    public boolean delClient(int vid)
    {
        DB ref = DB.getInstance();
        return ref.delClient(vid);
    }
    public void delClients(String vids[])
    {
        int i;
        for(i =0 ; i< vids.length; i++)
        {
            delClient(Integer.parseInt(vids[i]));
        }
    }

    public LinkedList<LinkedList<Object>> getAllClientsInfo()
    {
        DB ref = DB.getInstance();
        return ref.getAllClients();
    }

    public LinkedList<LinkedList<Object>> getClientsOtherThanUser(String uid)
    {
        DB ref = DB.getInstance();
        return ref.getClientsOtherThanUser(uid);
    }


    public LinkedList<Object> getClientDetails(int vid)
    {
        DB ref = DB.getInstance();
        return ref.getClientDetails(vid);
    }
    
    public LinkedList<String> getClientFields()
    {
        LinkedList<String> l = new LinkedList<String>();
        l.add("ID");
        l.add("Name");
        l.add("Phone");
        l.add("Address");
        l.add("Email");
        
        return l;
    }
    
}
