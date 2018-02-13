package datasentinel;
import java.util.Arrays;
import java.io.*;

public class Decryptor 
{
    KeyGenerator keygen;
    int deCipherBlockX[], deCipherBlock1[], deCipherBlock2[], deCipherBlock3[], deCipherBlock4[];
    int dataBlock[];
    File encryptedFile;
    File originalFile;
    
    String pin, remark, uid;
    
    public Decryptor(String uid, String efname, String ofname) throws Exception
    {
        this.uid = uid;
        encryptedFile = new File(efname);
        if(! encryptedFile.exists())
            throw new Exception("File "+ efname + " doesnot exist");

        originalFile = new File(ofname);

        pin = fetchPin();
        remark = fetchRemark(pin);
        
        System.out.println("pin in file : " + pin);
        System.out.println("remark : " + remark);
        
        
        keygen  = new KeyGenerator(uid);
        keygen.fetchKeyFromFile(pin);
        keygen.generateKeyBlocks();
              
    }
    
    String getPin()
    {
        return pin;
    }
    
    
    public String fetchPin()throws Exception
    {
        String strPin="";
    
        FileInputStream fin = new FileInputStream(encryptedFile);
        int x;
        byte data[] = new byte[PinGenerator.PINLENGTH];
        x = fin.read(data);
        fin.close();
        if(x == PinGenerator.PINLENGTH)
        {
            strPin = new String(data, 0, x);
        }
        else
            throw new Exception("ERROR FETCHING PIN FROM " + encryptedFile);
        return strPin;
    }

    
    String fetchRemark(String pin)
    {
        DB ref = DB.getInstance();
        return ref.getShieldingKeyPinwise(pin);
                
    }
    
    public void processFile(String fname) throws Exception
    {
        DirManager dm = new DirManager();
        
        String temp = dm.getTempFileName(uid);
        
        FileInputStream fin = new FileInputStream(fname);
        FileOutputStream fout = new FileOutputStream(temp);
        
        fin.skip(PinGenerator.PINLENGTH);
        int x;
        while( (x = fin.read())!= -1)
        {
            fout.write(x);
        }
        fout.flush();
        fout.close();
        fin.close();
        
        new File(fname).delete();
        new File(temp).renameTo(new File(fname));
              
    }
    
    public void decrypt() throws Exception
    {
        dataBlock = new int[KeyGenerator.KEYFACTOR];
        deCipherBlock1 = new int[KeyGenerator.KEYFACTOR];
        deCipherBlock2 = new int[KeyGenerator.KEYFACTOR];
        deCipherBlock3  = new int[KeyGenerator.KEYFACTOR];
        deCipherBlock4 = new int[KeyGenerator.KEYFACTOR];
        
        int i;
        processFile(encryptedFile.getAbsolutePath());
        ObjectInputStream oin  = new ObjectInputStream(new FileInputStream(encryptedFile));
        FileOutputStream fout = new FileOutputStream(originalFile);
        
        deCipherBlockX = Arrays.copyOf(keygen.getKeyBlock4213(), KeyGenerator.KEYFACTOR);
        
        SecretBlock sb;
        boolean flag = true;
        
        while( flag == true )
        {
            try
            {
                sb = (SecretBlock) oin.readObject();
            }
            catch(EOFException e)
            {
                flag = false;
                break;
            }
            
            dataBlock = sb.getArray();
            
            for(i =0; i< dataBlock.length; i++)
            {
                //xor
                deCipherBlock3[i] =  dataBlock[i] ^ keygen.getKeyBlock4()[i];
        
                //xor
                deCipherBlock2[i] = deCipherBlock3[i] ^ keygen.getKeyBlock2()[i];
                
                //left circular shift
                deCipherBlock1[i] = Integer.rotateLeft(deCipherBlock2[i] , 3);
                
                //xor
                deCipherBlock4[i] =  deCipherBlock1[i] ^ deCipherBlockX[i];
                
                //write in file
                fout.write(deCipherBlock4[i]);
                
                deCipherBlockX[i] = (int) dataBlock[i];
            }
            
        }//while
        
        oin.close();
        fout.close();
    }//decrypt
    
    
}//Decryptor

