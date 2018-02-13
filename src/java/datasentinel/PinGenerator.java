package datasentinel;

public class PinGenerator
{
    public static final int PINLENGTH = 20;
    
    
    public static String getNextPin()
    {
        long l = System.currentTimeMillis();
        String temp =  String.valueOf(l);
        while(temp.length() < PINLENGTH)
            temp = temp + " ";
        
        return temp;
    }
    
    
}
