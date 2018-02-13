package datasentinel;

public class Validate 
{
    String uid, pass;

    public Validate() {
    }
    
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public void setPass(String pass)
    {
        this.pass = pass;
    }
    
    public String getPass()
    {
        return pass;
    }
    
    public String isValid() 
    {
        DB ref = DB.getInstance();
        return ref.validateLogin(uid,pass);
    }
    
    public boolean chgPass(String u, String p, String np)
    {
        DB ref = DB.getInstance();
        return ref.chgPass(u,p, np);
    }

}
