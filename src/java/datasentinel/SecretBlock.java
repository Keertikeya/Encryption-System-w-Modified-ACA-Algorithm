package datasentinel;
import java.io.Serializable;
import java.util.Arrays;

public class SecretBlock implements Serializable
{
    int arr[];
    
    SecretBlock()
    {
        arr = null;
    }
    
    SecretBlock(int src[], int len)
    {
        arr = Arrays.copyOf(src, len);
        
    }
    
    int[] getArray()
    {
        return arr;
    }
}
