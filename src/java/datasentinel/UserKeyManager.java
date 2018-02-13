package datasentinel;

import java.io.File;

public class UserKeyManager 
{
 
    public String getUserKeyFolder(String uid)
    {
        String userKeyFolder;
        userKeyFolder = DirManager.getBaseFolder()+ "/" + uid + "/keys";
        File f = new File(userKeyFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        
        return userKeyFolder;
    }

    public String getNextKeyFileName(String uid) throws Exception
    {
        String userKeyFolder = getUserKeyFolder(uid);
        String keyFileName = "";
        boolean flag = false;
        
        int i;
        for(i =0; i<100000;i++)
        {
            keyFileName = userKeyFolder + "/key" +i;
            if(!new File(keyFileName).exists())
            {
                flag = true;
                break;
            }
        }
        
        if(flag == true)
            return keyFileName;
        else
            throw new Exception("Encryption Quota Exhausted, Delete Some Encryptions");
    }
    
    public boolean deleteKeyFile(String fname)
    {
        return new File(fname).delete();
    }
       
}
