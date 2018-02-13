package datasentinel;
import java.io.*;

public class DirManager
{
    private static final String baseFolder ="c:/dataSentinel";
    public DirManager()
    {
        File f = new File(baseFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
    }

    public static String getBaseFolder()
    {
        return  baseFolder;
    }
    
    public String getFilePath(String uid, String fname, int flag)
    {
        String userFolder;
        if(flag == 0)
            userFolder = baseFolder + "/" + uid;
        else
            userFolder = baseFolder + "/" + uid + "/image";
        
        File f = new File(userFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        String fileName = userFolder + "/" + fname;
        return fileName;
    }

    public String getTempFileName(String uid)
    {
        String userFolder;
        userFolder = baseFolder + "/" + uid;
        File f = new File(userFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        String fileName = userFolder + "/temp.txt";
        return fileName;
    }

    public String getAnotherTempFileName(String uid)
    {
        String userFolder;
        userFolder = baseFolder + "/" + uid;
        File f = new File(userFolder);
        if(!f.exists())
        {
            f.mkdirs();
        }
        String fileName = userFolder + "/temp1.txt";
        return fileName;
    }

    
    
    public void clearUploads(String uid)
    {
        try
        {
            String userFolder;
            userFolder = baseFolder + "/" + uid;
            File f = new File(userFolder);
            if(f.exists())
            {
                String contents[] = f.list();
                int i;
                File tempFile;
                for(i =0 ; i< contents.length; i++)
                {
                    tempFile = new File(f, contents[i]);
                    System.out.println("** " + tempFile.getAbsolutePath());
                    if(tempFile.isFile())
                        tempFile.delete();
                }
            }

        }
        catch(Exception ex)
        {}
    }
}
