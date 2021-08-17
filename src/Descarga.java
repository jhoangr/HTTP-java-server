import java.net.*;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.*;

public class Descarga extends Thread 
{
	/*private ServerSocket serverSocket;*/
	private Socket socket2;
	
	
	
	public Descarga(Socket socket)
	{
		this.socket2=socket;
		System.out.print("Thread"+socket.getInetAddress()+"\n");
		
	}
	
	
	public void run()
	{
		
		
		
		
		try
		{
		 InputStream is;
		 OutputStream os;
		 is=socket2.getInputStream();
		 os=socket2.getOutputStream();
		 BufferedReader in  = new BufferedReader(new InputStreamReader(is));
			
			
			
			
		  while(true)
		   {
			
			//SEPARADORS//
			String separadores = "[\\ \\?]";
			//LLEGIM PETICIÓ
			String px=in.readLine();
			String p[]=px.split(separadores);
			//IMPRIMIM PETICIÓ
			System.out.println(Arrays.toString(p));
			//DECLAREM LA CAPCELERA
			String capcelera;
			
			
			//INICIALITZEM RUTA DE ARXIU A LLEGIR//
			File af = new File("");
			
			
					
			if(p.length>1)
			{
			//directori on trobem els arxius -A MODIFICAR-//
				
				af = new File("/home/jhoan/eclipse-workspace/WServer/files"+p[1]);
			}
			
            // COMPROVEM SI EL FITXER EXISTEIX//		
			if(af.exists())
			{
				
				boolean html=false;
				boolean zip=false;
				boolean gzip=false;
				
				int r=0;
				
				//ESTABLIM LA CONEXIÓ ENTRE EL ARXIU I LA SORTIDA
				FileInputStream fis=new FileInputStream(af);
				AsciiInputStream fx=new AsciiInputStream(fis);
				

			   
			    
				
				if(px.contains("gzip=true&zip=true")||px.contains("zip=true&gzip=true"))
				{
					gzip=true;
					zip=true;
					
				}else if(px.contains("gzip=true"))
					
				{					
					
				gzip=true;	
				
				}
				
				if(px.contains("zip=true")&&!px.contains("gzip=true"))
					
				{					
					
				zip=true;	
				
				}
                
				
				if(p[1].contains("html"))
				{
					html=true;
					
				}
				 //SI VOLEM CONVERSIÓ HTML Y FITXER ES HTML//
	            boolean filtre=false;
			    if(html&&p[2].contains("asc=true"))
			       {
			    	   filtre=true;
			    	   System.out.print("asc=true\n");
			       }
				
				//TRACTCAMENT DE CAPCELERES//
				if(html&&!zip&&!gzip)	
				{
					capcelera="HTTP/1.1 200 OK\n Content-Type: text/html\n\n";
					
				}
				else if(p[1].contains("text")&&!zip&&!gzip)
				{
				  capcelera="HTTP/1.1 200 OK\n Content-Type: text/plain\n\n";
				}else if(zip&&!gzip)
				{
				capcelera="HTTP/1.1 200 OK\nContent-Type: application/zip\nContent-Disposition: filename=\""+p[1].replace("./"," ")+".zip\"\n\n";
				}else if(gzip&&!zip)
				{
					
					capcelera="HTTP/1.1 200 OK\nContent-Type: application/x-gzip\nContent-Disposition: filename=\""+p[1].replace("./," ," ")+".gz\n\n";
				}else if(gzip&&zip&&filtre)
				{
					
					capcelera="HTTP/1.1 200 OK\nContent-Type: application/x-gzip\nContent-Disposition: filename=\""+p[1].replace("./"," ")+ ".asc.zip.gz\"\n\n";
					
				}else if(gzip&&zip)
				{
					
					capcelera="HTTP/1.1 200 OK\nContent-Type: application/x-gzip\nContent-Disposition: filename=\""+p[1].replace("./"," ")+ ".zip.gz\"\n\n";
				}
				else  if((p[1].contains("jpeg")||p[1].contains("png")||p[1].contains("gif")))
				{
					
					capcelera="HTTP/1.1 200 OK\n Content-Type: image"+p[1].replace("/.","")+"\n\n";
					
				}
				else if((p[2].contains("xml")||p[1].contains("xml")))
				{
					capcelera="HTTP/1.1 200 OK\nContent-Type: application/xml\n Content-Disposition: filename=\"HelloWorld.html.xml\"\n\n";
					
				}else
				{
					capcelera="HTTP/1.1 200 OK\nContent-Type: application/octet-stream\n Content-Disposition: filename=\"HelloWorld.html.xml\"\n\n";
					
				}
					 
			System.out.print(capcelera);
			
            //ESCRIBIM LA CAPCELERA DEL ARXIU	
            os.write(capcelera.getBytes());
			
			
			
		    
		
		    
		    if(gzip)
		    {
		      os=new GZIPOutputStream(os);
		  
		    }
		    
		    
		    ZipEntry zpe=new ZipEntry(af.getName());
		    if(zip)
		     {
		    	
		    	os=new ZipOutputStream(os);
		    	//casteo
		    	((ZipOutputStream)os).putNextEntry(zpe);
		    
		       

		     }
		    
		   
		    
		    	 
		    
			//LLEGIM MENTRE QUE NO FINALITZI EL FITXER
			while((r=fx.read(filtre))!=-1)
					{   
				      
				      os.write(r);
				     }
					
			fis.close();
			fx.close();
			}
			else 
			{
				System.out.print("HTTP/1.1 404 Not Found\n\n");
				
			}
			os.close();
			
			
		}
	}catch(IOException ioe)
	  {
		System.out.print("Error.");
		ioe.printStackTrace();
	  }
		
	}

}
