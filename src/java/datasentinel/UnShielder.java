package datasentinel;

public class UnShielder
{
    public UnShielder()
    {}

    public void manageSharingOption(String uid, String pin)
    {
        DB ref = DB.getInstance();
        ref.manageSharingOption(uid, pin);
    }
    
    public void manageGuestSharingOption(String ncpin)
    {
        DB ref = DB.getInstance();
        ref.manageGuestSharingOption(ncpin);
    }

    public String unShieldFile(String tempFname, String uid)
    {
        String result;
        DirManager dm = new DirManager();
                
        try
        {
            UploadParser uh = new UploadParser(tempFname,uid);
            String selectedPin = uh.fetchRemark().trim();
            String selectedPinCopy = selectedPin; 
                    
            if(uid.equalsIgnoreCase("guest"))
            {
                selectedPin = datasentinel.DB.getInstance().getShieldingPinNonClientSharingwise(selectedPin).trim();
                if(selectedPin.length() == 0)
                    return "INVALID/EXPIRED SHARING";
            
                System.out.println("selected pin : " + selectedPinCopy);

                if(!new Sharings().validateNonClientSharing(selectedPinCopy))
                    return "INVALID/EXPIRED SHARING";
            }
            
            if(uh.extractFile(0))
            {
                String dataFile, shieldedFile, remark;
                String fname;
                fname = uh.getFileName();

                shieldedFile = dm.getFilePath(uid, fname,0);
                fname = fname.substring(2);//removed prefixed s_
                dataFile = dm.getFilePath(uid, fname,0);
                
                
                Decryptor de = new Decryptor(uid, shieldedFile, dataFile);
                String pin = de.getPin().trim();
                
                if(!selectedPin.endsWith(pin))
                    return "UPLOADED FILE DOESNT MATCH WITH SHIELDING ";
                
                de.decrypt();
            
                if(uid.equalsIgnoreCase("guest"))
                {
                    manageGuestSharingOption(selectedPinCopy);
                }
                else
                {
                    manageSharingOption(uid, pin);
                }
                return dataFile;
            }
            else
            {
                dm.clearUploads(uid);
                result = "Err : Parsing Failed For Uploaded File";
            }
        }
        catch(Exception ex)
        {
            dm.clearUploads(uid);
            result = "Err :" + ex.getMessage();
            ex.printStackTrace();
        }
        return result;
    }
    
    /*
    public String unShieldFileForNonClient(String tempFname, String uid, String pin, String phoneNumber)
    {
        String result;
        DirManager dm = new DirManager();
                
        try
        {
            UploadParser uh = new UploadParser(tempFname,uid);
            if(uh.extractFile(0))
            {
                DB ref = DB.getInstance();
                String dataFile="", shieldedFile="";
                String fname="";
                
                  
                fname = uh.getFileName();

                shieldedFile = dm.getFilePath(uid, fname,0);
                fname = fname.substring(2);//removed prefixed s_
                dataFile = dm.getFilePath(uid, fname,0);

                Decryptor de = new Decryptor(uid, shieldedFile, dataFile);
                String filepin = de.getPin();
                if(filepin.equals(pin))
                {
                    de.decrypt();
                    System.out.println("original file : " + dataFile);

                    ref.removeNonClientSharing(pin);
                }
                else
                    return "Err : Pin Mismatch";
                return dataFile;
            }
            else
            {
                dm.clearUploads(uid);
                result = "Err : Parsing Failed For Uploaded File";
            }
        }
        catch(Exception ex)
        {
            dm.clearUploads(uid);
            result = "Err :" + ex.getMessage();
//            ex.printStackTrace();
        }
        return result;
    }
    */
}
