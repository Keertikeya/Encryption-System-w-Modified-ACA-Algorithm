package datasentinel;
import java.util.*;

public class Sharings
{
    public void addSharing(int sid, int cid)
    {
        DB ref = DB.getInstance();
        ref.addSharing(sid, cid);
    }

    public void addSharings(String sid, int cid)
    {
        DB ref = DB.getInstance();
        ref.addSharing(Integer.parseInt(sid), cid);
        
    }
    
    public void addSharings(String sids[], int cid)
    {
        DB ref = DB.getInstance();
        for (String sid : sids) 
        {
            ref.addSharing(Integer.parseInt(sid), cid);
        }
    }
    
    public boolean addNonClientSharing(int sid, String phoneNumber, String ncSharingPin)
    {
        DB ref = DB.getInstance();
        SMSWriter obj = new SMSWriter();
        obj.writeSMS(phoneNumber, ncSharingPin);
        return ref.addNonClientSharing(sid, phoneNumber, ncSharingPin);
    }
    
    
    public boolean removeNonClientSharing(String pin, String phoneNumber)
    {
        DB ref = DB.getInstance();
        return ref.removeNonClientSharing(pin);
    }
    
    public boolean validateNonClientSharing(String pin)
    {
        DB ref = DB.getInstance();
        return ref.validateNonClientSharing(pin);
    }
    
    public boolean removeSharing(int sid)
    {
        DB ref = DB.getInstance();
        return ref.removeSharing(sid);
    }

    public void removeSharings(String sids[])
    {
        int i;
        for(i =0 ; i< sids.length; i++)
        {
            removeSharing(Integer.parseInt(sids[i]));
        }
    }

    public LinkedList<LinkedList<Object>> getSharingsClientwise(String uid)
    {
        DB ref = DB.getInstance();
        return ref.getSharingsClientwise(uid);
    }

    public LinkedList<LinkedList<Object>> getSharings(String uid)
    {
        DB ref = DB.getInstance();
        return ref.getSharings(uid);
    }


    public LinkedList<String> getSharingFields()
    {
        LinkedList<String> l = new LinkedList<String>();
        l.add("ID");
        l.add("Remark");
        l.add("Shared With");
        
        return l;
    }

    public LinkedList<String> getSharingFields1()
    {
        LinkedList<String> l = new LinkedList<String>();
        l.add("ID");
        l.add("Remark");
        l.add("Shared By");
        
        return l;
    }
    
    
}
