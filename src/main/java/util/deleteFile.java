package util;
import java.io.File;

public class deleteFile{
	    
	    public  void deleteFiles(String path){
	       
	    	File file = new File(path);
	    	
	       if(!file.isDirectory()){
	    	   
	           file.delete();
	           
	       }else if(file.isDirectory()){
	           //2級文件列表
	           String []filelist = file.list();
	           //獲取新的二級路徑
	           for(int j=0;j<filelist.length;j++){
	               File filessFile= new File(path+"\\"+filelist[j]);
	               if(!filessFile.isDirectory()){
	                   filessFile.delete();
	               }else if(filessFile.isDirectory()){
	                   //遞歸調用
	                   deleteFiles(path+"\\"+filelist[j]);
	               }
	           }
	           file.delete();
	       }
	    }


	}

