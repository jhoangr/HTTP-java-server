import java.net.*;
import java.util.Arrays;
import java.io.*;



public class WServer 
{

	public static void main(String[]args)
	{
		ServerSocket serverSocket = null;
		try
		{
		serverSocket=new ServerSocket(8410);
		}catch(IOException e1)
		{
			e1.printStackTrace();
			
		}
			
			while(true)
			{
				try
				{
		         Socket incoming;
			     incoming=serverSocket.accept();
		         Thread t = new Descarga(incoming);
		         t.start();
		        
			    }catch(IOException e)
				{
				 e.printStackTrace();
			    }
				
				
			}
		
			
		
	}
}


