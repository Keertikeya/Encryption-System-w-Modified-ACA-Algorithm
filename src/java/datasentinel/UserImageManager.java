package datasentinel;

import java.io.File;

public class UserImageManager 
{
    public String getUserImage(String uid)
    {
        String userFolder;
        userFolder = DirManager.getBaseFolder() + "/" + uid + "/image";
        File f = new File(userFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        String contents[] = f.list();
        String userImage = null;
        int i;
        
        for(i =0; i< contents.length; i++)
        {
            if(new File(f, contents[i]).isFile())
            {
                userImage = userFolder + "/" + contents[i];
                break;
            }
        }
        return userImage;
    }
    
    public boolean deleteUserImage(String img)
    {
        return new File(img).delete();
    }
    
    public boolean parseUserImage(String tempFile, String uid)
    {
        String fname = getUserImage(uid);
        UploadParser up = new UploadParser(tempFile, uid);
        if(up.extractFile(1))
        {
            if(fname != null)
                new File(fname).delete();
            new File(tempFile).delete();
            return true;
        }
        new File(tempFile).delete();
        return false;
    
    }
    
}
