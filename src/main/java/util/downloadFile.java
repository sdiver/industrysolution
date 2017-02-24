/**
 *
 * @Title:downloadFile.java
 *
 * @Package:util
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午5:08:30
 *
 * @version V1.0
 *
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class downloadFile {

	public void downloadii(String url,HttpServletResponse response) throws Exception{
	
		response.setCharacterEncoding("utf-8");
	
		response.setContentType("multipart/form-data");
    
		String filename = url.substring(url.lastIndexOf("/")+1);
    
		response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(filename, "UTF-8"));
    
		try { 
  	  
			File file=new File(url);  
        
			InputStream inputStream=new FileInputStream(file);  
        
			OutputStream os=response.getOutputStream(); 
        
			byte[] b=new byte[1024]; 
        
			int length;
        
			while((length=inputStream.read(b))>0){  
      	  
				os.write(b,0,length);
            
			} 
        
			inputStream.close(); 
        
        
		} catch (FileNotFoundException e) {  
  	  
			e.printStackTrace();  
        
		} catch (IOException e) {  
  	  
			e.printStackTrace();  
		}  
	}
}
