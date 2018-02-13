package datasentinel;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.Arrays;

public class KeyGenerator 
{
    public final static int KEYLENGTH = 64;
    public final static int KEYFACTOR = 16;
    
    private String user;
    private final String userfolder;
    
    private int key[];
    private int keyBlock1[] , keyBlock2[], keyBlock3[], keyBlock4[];
    private int keyBlock13[], keyBlock213[], keyBlock4213[];
    
    public KeyGenerator(String user)
    {
        this.user = user;
        userfolder = DirManager.getBaseFolder() ;
    }
    
    public String generateKey(String keyVal) throws Exception
    {
        System.out.println("in generateKey " + keyVal);
        if(keyVal.length() < KEYFACTOR)
            throw new Exception("Key Length Cannot Be Less Than "+ KEYFACTOR +" characters");
       
        UserImageManager uim = new UserImageManager();
        String userImage = uim.getUserImage(user);
        if(userImage == null)
            throw new Exception("User Image Not Found");
        
        File f = new File(userImage);

        if(! f.exists())
            throw new Exception("User Image Not Found");
        
        String keyOfLength = generateKeyOfLength(keyVal);
    
        //generate key from user image 
        
        BufferedImage bImg = ImageIO.read(f);
        Raster imgReader = bImg.getData();
        int w, h, i, j, x, currAscii;
        int bandId;

        key = new int[KEYLENGTH];
        
        bandId = 0;
        w = imgReader.getWidth();
        h = imgReader.getHeight();
        
        for(x = 0; x < KEYLENGTH; x++)
        {
            currAscii = (int) keyOfLength.charAt(x);
            i = (currAscii / w) % h;
            j = currAscii % w;
            key[x] = (byte)imgReader.getSample(i, j, bandId);
                
            if(x > 0 && x % KEYFACTOR == 0)
                bandId = (bandId + 1) % 3;
        }
        
        return writeKey(key);
    }
    
    String writeKey(int key[])
    {
        try
        {
            UserKeyManager ukm = new UserKeyManager();
            String fname = ukm.getNextKeyFileName(user);
            
            SecretBlock sb = new SecretBlock(key, KEYLENGTH);
            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(fname));
            oout.writeObject(sb);
            oout.flush();
            oout.close();
            return fname;
        }
        catch(Exception ex)
        {
            return null;
        }
    }
    
    public int[] fetchKeyFromFile(String pin)
    {
        try
        {
            DB ref = DB.getInstance();
            String fname = ref.getShieldingFilePinwise(pin);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fname));
            SecretBlock sb = (SecretBlock) ois.readObject();
            key = sb.getArray();
            ois.close();
            return key;
        }
        catch(Exception ex)
        {
            return null;
        }
        
    }
    
    String generateKeyOfLength(String keyVal)
    {
        int i = 0;
        String newKey = keyVal;
        
        if(newKey.length() > KEYLENGTH)
            return newKey.substring(0,KEYLENGTH);
        
        while(newKey.length() < KEYLENGTH)
        {
            newKey = newKey + keyVal.charAt(i);
            i = (i + 1) % keyVal.length();
       
        }
        System.out.println("new key : " + newKey);
        return newKey;
    }
  
    
    public void generateKeyBlocks() throws Exception
    {
        int i;
        
        keyBlock1 = Arrays.copyOfRange(key, 0, KEYFACTOR);
        keyBlock2 = Arrays.copyOfRange(key, KEYFACTOR, KEYFACTOR*2);
        keyBlock3 = Arrays.copyOfRange(key, KEYFACTOR*2, KEYFACTOR*3);
        keyBlock4 = Arrays.copyOfRange(key, KEYFACTOR*3, KEYFACTOR *4);
        
        
        keyBlock13  = new int[KEYFACTOR];
        keyBlock213 = new int[KEYFACTOR];
        keyBlock4213 = new int[KEYFACTOR];
        
        for(i =0 ; i< KEYFACTOR; i++)
        {
            keyBlock13[i] =  keyBlock1[i] ^ keyBlock3[i];
            keyBlock213[i] = keyBlock2[i] ^ keyBlock13[i];
            keyBlock4213[i] = keyBlock213[i] ^ keyBlock4[i];
        }
        
    }
    
    public int[] getKey()
    {
        return key;
    }
    
    
    public int[] getKeyBlock1()
    {
        return keyBlock1;
    }
    
    public int[] getKeyBlock2()
    {
        return keyBlock2;
    }
    
    public int[] getKeyBlock3()
    {
        return keyBlock3;
    }
    
    public int[] getKeyBlock4()
    {
        return keyBlock4;
    }
    
    public int[] getKeyBlock13()
    {
        return keyBlock13;
    }
    
    public int[] getKeyBlock213()
    {
        return keyBlock213;
    }
    
    public int[] getKeyBlock4213()
    {
        return keyBlock4213;
    }
    
}
