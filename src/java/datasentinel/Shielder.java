package datasentinel;

public class Shielder
{

    public Shielder()
    {}

    public String shieldFile(String tempFname, String uid)
    {
        String result;
        DirManager dm = new DirManager();
                
        try
        {
            UploadParser uh = new UploadParser(tempFname,uid);
            if(uh.extractFile(0))
            {
                String dataFile, shieldedFile, remark, keyFile;
                String fname, pin;
                remark = uh.fetchRemark();
                fname = uh.getFileName();

                dataFile = dm.getFilePath(uid, fname,0);
                shieldedFile = dm.getFilePath(uid,"s_" + fname,0);
                pin = PinGenerator.getNextPin();
                
                KeyGenerator kgen = new KeyGenerator(uid);
                keyFile = kgen.generateKey(remark);
                if(keyFile == null)
                    throw new Exception ("Key File Generation Failed");
                
                kgen.generateKeyBlocks();
                
                Encryptor en = new Encryptor(kgen, dataFile, shieldedFile , pin);
                en.encrypt();
                System.out.println("shielded file : " + shieldedFile);
 
                Shieldings  sh = new Shieldings();
                if( sh.addShielding(remark, pin,keyFile, uid))
                    return shieldedFile;
                else
                {
                    dm.clearUploads(uid);
                    return "Err : Unable To Register Shielding";
                }
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
        }
        return result;
    }

}
