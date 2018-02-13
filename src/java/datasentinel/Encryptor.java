package datasentinel;
import java.util.Arrays;
import java.io.*;

public class Encryptor 
{
    KeyGenerator keygen;
    int cipherBlockX[], cipherBlock1[], cipherBlock2[], cipherBlock3[], cipherBlock4[];
    byte dataBlock[];
    File fileToEncrypt;
    File targetFile;
    
    
    public Encryptor(KeyGenerator keygen, String sfname, String tfname, String pin) throws Exception
    {
        this.keygen = keygen;
        fileToEncrypt = new File(sfname);
        if(! fileToEncrypt.exists())
            throw new Exception("File "+ sfname + " doesnot exist");
        
        targetFile = new File(tfname);
        writePin(pin);
    }
    
    public void writePin(String pin)throws Exception
    {
        System.out.println("writing pin : " + pin);
        FileOutputStream fout = new FileOutputStream(targetFile);
        fout.write(pin.getBytes());
        fout.flush();
        fout.close();
        
    }

    
    public void encrypt() throws Exception
    {
        dataBlock = new byte[KeyGenerator.KEYFACTOR];
        cipherBlock1 = new int[KeyGenerator.KEYFACTOR];
        cipherBlock2 = new int[KeyGenerator.KEYFACTOR];
        cipherBlock3  = new int[KeyGenerator.KEYFACTOR];
        cipherBlock4  = new int[KeyGenerator.KEYFACTOR];
        
        int x, i;
        
        FileInputStream fin  = new FileInputStream(fileToEncrypt);
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(targetFile, true));
        
        cipherBlockX = Arrays.copyOf(keygen.getKeyBlock4213(), KeyGenerator.KEYFACTOR);
    
        while( (x = fin.read(dataBlock)) != -1)
        {
            for(i =0; i< x; i++)
            {
                //xor
                cipherBlock1[i] = cipherBlockX[i] ^ (int)dataBlock[i];
                
                //right circular shift
                cipherBlock2[i] = Integer.rotateRight(cipherBlock1[i], 3);
                
                //xor
                cipherBlock3[i] = cipherBlock2[i] ^ keygen.getKeyBlock2()[i];
                            
                //xor
                cipherBlock4[i] = cipherBlock3[i] ^ keygen.getKeyBlock4()[i];
            }
            
            //writeback
            SecretBlock sb = new SecretBlock(cipherBlock4,x );
            oout.writeObject(sb);
            
            cipherBlockX = Arrays.copyOf(cipherBlock4, cipherBlock4.length);
        
        }//while
        
        oout.flush();
        fin.close();
        oout.close();
        
    }//encrypt
        
}//Encryptor
