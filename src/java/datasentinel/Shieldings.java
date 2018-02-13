package datasentinel;
import java.util.*;

public class Shieldings
{
    public boolean addShielding(String remark,  String pin, String fname, String uid)
    {
        DB ref = DB.getInstance();
        int cid = ref.getId(uid);
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        return ref.addShielding(remark,pin,fname, date, cid);
    }

    public boolean delShielding(int sid)
    {
        String fname;
        DB ref = DB.getInstance();
        UserKeyManager ukm = new UserKeyManager();
        
        fname = ref.getShieldingFileIdwise(sid);
        ukm.deleteKeyFile(fname);
        return ref.delShielding(sid);
    }

    public void delShieldings(String sids[])
    {
        int i;
        for(i =0 ; i< sids.length; i++)
        {
            delShielding(Integer.parseInt(sids[i]));
        }
    }

    public LinkedList<LinkedList<Object>> getShieldingsClientwise(String uid)
    {
        DB ref = DB.getInstance();
        return ref.getShieldingsClientwise(uid);
    }

    public LinkedList<String> getShieldingFields()
    {
        LinkedList<String> l = new LinkedList<String>();
        l.add("ID");
        l.add("Remark");
        l.add("Pin");
        l.add("Date");

        return l;
    }

}
