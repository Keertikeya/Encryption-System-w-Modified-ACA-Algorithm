package datasentinel;
import java.io.*;

public class UploadParser
{
    String tempFname;
    String uid;
    
    public UploadParser(String tempFname, String uid)
    {
        this.tempFname = tempFname;
        this.uid = uid;
    }

    String fetchRemark()
    {
        String remark = "";
        try
        {
            FileReader fr = new FileReader(tempFname);
            BufferedReader br = new BufferedReader(fr);

            br.readLine();
            br.readLine();
            br.readLine();

            remark = br.readLine();
            remark = remark.trim();
            br.close();
        }
        catch(Exception ex)
        {
            System.out.println("Error in fetchRemark " + ex);
        }
        return remark;
    }
    
    public boolean extractFile(int flag)
    {
        try
        {
            String fileField = getFieldSeparator();
            DirManager dm = new DirManager();
            
            String trgtFname = dm.getFilePath(uid, getFileName(), flag);
            
            int startLineNo = getStartLineNoForFile(fileField);
            
            //extraction begins here
            int x = 0;
            int lineNo= 0;
            byte arr[] = new byte[512];
            
            String fieldSeparator = getFieldSeparator();
            
            MyFileInputStream fin = new MyFileInputStream(tempFname);
            FileOutputStream fout = new FileOutputStream(trgtFname);
            String temp;
            
            while((x = fin.readLine(arr)) != -1)
            {
                lineNo++;
                if(lineNo >= startLineNo)
                {
                    temp = new String(arr,0,x);
                    if(!temp.startsWith(fieldSeparator))
                    {
                        fout.write(arr, 0, x);
                    }
                    else
                    {
                        break;
                    }
                }
            }
            
            fout.flush();
            fout.close();
            fin.close();
            
            removeLast2Bytes(uid, trgtFname);
            return true;
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }
    
    void removeLast2Bytes(String uid, String src) throws Exception
    {
        DirManager dm = new DirManager();
        String tempFile = dm.getAnotherTempFileName(uid);
        
        File f = new File(src);
        FileInputStream fin = new FileInputStream(f);
        FileOutputStream fout = new FileOutputStream(tempFile);
        long len = f.length()-2;
        long i;
        for(i=0; i<len ; i++)
        {
            fout.write(fin.read());
        }
        fin.close();
        fout.flush();
        fout.close();
        f.delete();
        new File(tempFile).renameTo(new File(src));
    }
    
    String getFileName()throws Exception
    {
        FileReader fr = new FileReader(tempFname);
        BufferedReader br = new BufferedReader(fr);
        
        String line, fname;
        String chk = "Content-Disposition: form-data; name=\"dataFile\";";  

        int x;
        while( (line= br.readLine()) != null)
        {
            if(line.startsWith(chk))
            {
                //found the header that has the reqd fileField
                //extract name from it
                x = line.lastIndexOf("=");
                if(x == -1)
                   throw new Exception("FILE NAME NOT FOUND IN HEADER");
                
                fname = line.substring(x+1);//"A.java"
                //remove starting double quotes
                fname = fname.substring(1);//A.java"
                //remove ending double quotes
                fname =fname.substring(0,fname.length()-1);//A.java
                br.close();
                return fname;
            }//if
        }//while
        
        br.close();
        throw new Exception("FILE NAME NOT FOUND IN HEADER");
    }

    int getStartLineNoForFile(String fileField)throws Exception
    {
        MyFileInputStream fin = new MyFileInputStream(tempFname);
        
        int x;
        byte arr[] = new byte[512];
        
        String line;
        String chk = "Content-Disposition: form-data; name=\"dataFile\";";  
        int n= 0;
        while((x= fin.readLine(arr)) != -1)
        {
            n++;
            line = new String(arr, 0, x);
            if(line.startsWith(chk))
            {
                fin.close();
                return n+3;
            }//if
        }//while
        
        fin.close();
        throw new Exception("FILE NAME NOT FOUND IN HEADER");
    }    
 
    String getFieldSeparator()throws Exception
    {
        FileReader fr = new FileReader(tempFname);
        BufferedReader br = new BufferedReader(fr);
        String data = br.readLine();
        br.close();
        return data;
    }
    
    
    class MyFileInputStream//wrapper class
    {
        FileInputStream fin;//binary reading
                
        MyFileInputStream(String fname)throws Exception
        {
            fin = new FileInputStream(fname);
        }
        
        int read()throws Exception
        {
            return fin.read();
        }
        
        int readLine(byte arr[])throws Exception
        {
            int x = 0;
            int data, prevData= 0;
           
            do
            {
                data = fin.read();//fetch a byte
                arr[x] = (byte)data;
                x++;

                if(prevData == 13 && data == 10)//stop as a new line is found
                    return x;
                if(x == arr.length)//buffer is full
                    return x;
                
                prevData = data;
            }while (data != -1);//end of file
            return data;
        }
       
        void close()throws Exception
        {
            fin.close();
        }
        
    }//MyFileInputStream
    
}