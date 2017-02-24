/**
 *
 * @Title:fileuploaduil.java
 *
 * @Package:util
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午1:36:22
 *
 * @version V1.0
 *
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class fileuploaduil {
	
	public Map<Object, Object> fileuploaduilfuc(HttpServletRequest request){
	
		  Map<Object, Object> result = new HashMap<Object, Object>();
		  
		  try{
        	
			  DiskFileItemFactory factory = new DiskFileItemFactory();
		          	
			  ServletFileUpload uploadservlet = new ServletFileUpload(factory);
		          	
			  uploadservlet.setHeaderEncoding("UTF-8");
	          
			  if(!ServletFileUpload.isMultipartContent(request)){
	           		
				  result.put("result", 4);
	    
			  }
	           	
			  @SuppressWarnings("unchecked")
		  
			  List<FileItem> list = uploadservlet.parseRequest(request);
	           	
			  if(list.size() != 0){
				  
				  Map<Object, Object> info = canshu(list);
			  	  
			  	  String projectid = (String) info.get("projectid");
			  	
			  	  String filetype = (String) info.get("filetype");
			  	  
			  	  info.put("filetype", Integer.parseInt(filetype));
			  	  
//			  	  String savePath = "D://upload";
			  	  
			  	  String savePath = request.getSession().getServletContext().getRealPath("/upload")+"/"+projectid +"/"+filetype;
			  	  
			  	  mkfiledir(savePath);
			        	
			  	  Map<Object, Object> fileinfo = uploadfile(list, savePath);
			           			
			  	  info.put("filename", (String)fileinfo.get("filename"));
			  	  
			  	  info.put("fileurl", (String)fileinfo.get("fileurl"));
			  	  
			  	  info.put("tail", (String)fileinfo.get("tail"));
			           	  
			      result.put("info", info);
			      
			      result.put("result", 1);
				
			  }else{     	
		 
				  result.put("result", 4);
			      
			  }
		           			
			}catch(Exception e){
				
				e.printStackTrace();
				
				result.put("result", 0);
				
			}
		  
		  return result;

	}

	public Map<Object, Object> canshu(List<FileItem> list){
		
		Map<Object, Object> result = new HashMap<Object, Object>();
	
		for(FileItem item: list){
          		
		  if(item.isFormField()){
     			
			  String name = item.getFieldName();
     			
			  String value = null;
			  try {
				
				  value = item.getString("UTF-8");
				
			  } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
				
			  }
			  
			  result.put(name, value);
     			
		  	}
          
		}
		
		return result;
	  
	}
	
	public Map<Object, Object> uploadfile(List<FileItem> list, String savePath ){
		
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		for(FileItem item2:list){
       		
			if(!item2.isFormField()){
		           			
					  String filename = item2.getName();
					  
					  filename = filename.substring(filename.lastIndexOf("\\")+1);
					  
					  String menufile =  filename.split("\\.")[0]+String.valueOf(System.currentTimeMillis())+"."+ filename.split("\\.")[1];
					  
					  InputStream in;
					
					  try {
						
						in = item2.getInputStream();
		           			
						FileOutputStream out = new FileOutputStream(savePath+"/"+menufile);
		           			
						byte buffer[] = new byte[1024];
		           			
						int len = 0;
		           			
						while((len=in.read(buffer))>0){
		           			
							out.write(buffer,0,len);
		           			
						}
		           			
						in.close();
		           			
						out.close();
		           			
						item2.delete();	
					  
						String fileurl = savePath+"/"+menufile;
						
						result.put("tail", filename.split("\\.")[1]);
						
						result.put("filename", filename.split("\\.")[0]);
					  
						result.put("fileurl", fileurl);
					 
					  } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
					  }
			
			}
			
		}
		return result;
		
	}
	public void mkfiledir(String savePath){
        
		  File file = new File(savePath);
	           	
		  if(!file.exists()&&!file.isDirectory()){
	          
			  file.mkdirs();
	           	
		  }
	}
	
}

