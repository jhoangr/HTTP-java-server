import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AsciiInputStream extends FilterInputStream
{

	protected AsciiInputStream(InputStream in) 
	{
		super(in);
		// TODO Auto-generated constructor stub
	}



public int read(boolean filtre) throws IOException 
{
	
    int b = in.read();  
    /*while lectura*/
    
    if (filtre&&b==60)
    {
    	while(b!=62)
    	{
    		b=in.read();
    	}
    	
    	b=in.read();
    }
  
   
    
 
    
    
    return b;
   
}

public int read(byte[] b) throws IOException {
    int len;
    len = in.read(b, 0, b.length);
    if (len != -1) {
        
    }
    return len;
}

public int read(byte[] b, int off, int len) throws IOException {
    len = in.read(b, off, len);
    if (len != -1) {
        
    }
    return len;
}
}
