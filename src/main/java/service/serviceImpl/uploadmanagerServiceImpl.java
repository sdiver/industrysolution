/**
 *
 * @Title:uploadmanagerServiceImpl.java
 *
 * @Package:service.serviceImpl
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午4:19:59
 *
 * @version V1.0
 *
 */
package service.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mapper.solutionmanagerMapper;
import mapper.uploadmanagerMapper;
import model.fileinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.uploadmanagerService;
import util.deleteFile;
import util.encryption;
import util.fileuploaduil;

@Service("uploadmanagerServiceImpl")
public class uploadmanagerServiceImpl implements uploadmanagerService{
	/* (non-Javadoc)
	 * @see service.uploadmanagerService#uploadfile(javax.servlet.http.HttpServletRequest)
	 */	
	@Autowired
	solutionmanagerMapper solutionmanagermapper;	
	@Autowired
	uploadmanagerMapper uploadmanagermapper;	
	encryption encrypt = new encryption();	
	fileuploaduil uploadinfo = new fileuploaduil();	
	deleteFile deletefile = new deleteFile();	
	public Map<Object, Object> uploadfile(HttpServletRequest request) {
		// TODO Auto-generated method stub		
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request);		  
		  Map<Object, Object> result = new HashMap<Object, Object>();		  
		  if((Integer)uploadinfomation.get("result")==1){			  
			  @SuppressWarnings("unchecked")			  
			  Map<Object, Object>info = (Map<Object, Object>) uploadinfomation.get("info");			  
			  info.put("fileid", encrypt.uuidfactory());			  
			  info.put("updateid", 1);			  
			  uploadmanagermapper.uploadfile(info);			  
			  result.put("result", 1);			  
		  }else{			  
			  result = uploadinfomation;			  
		  }
		return result;
	}

	/* (non-Javadoc)
	 * @see service.uploadmanagerService#deletefile(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> deletefile(String projectid, String userid,
			String fileid) {
		// TODO Auto-generated method stub		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		Map<Object, Object> map = new HashMap<Object, Object>();		
		map.put("projectid", projectid);		
		map.put("userid", userid);		
		int puser = uploadmanagermapper.personlist(map);		
		if(puser == 1){		
			fileinfo fileinfomation = uploadmanagermapper.findbyfileid(fileid);		
			if(fileinfomation != null){			
				deletefile.deleteFiles(fileinfomation.getFileurl());				
				uploadmanagermapper.deletefile(fileinfomation.getFileid());			
				result.put("result", 1);			
			}else{		
				result.put("result", 0);
			}
		}else{
			result.put("result", 2);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see service.uploadmanagerService#updatefile(javax.servlet.http.HttpServletRequest)
	 */
	public Map<Object, Object> updatefile(HttpServletRequest request) {
		// TODO Auto-generated method stub
		Map<Object, Object> uploadinfomation = uploadinfo.fileuploaduilfuc(request);
		  Map<Object, Object> result = new HashMap<Object, Object>();
		  if((Integer)uploadinfomation.get("result")==1){
			  @SuppressWarnings("unchecked")
			  Map<Object, Object> info = (Map<Object, Object>) uploadinfomation.get("info");
			  String fileid = (String) info.get("fileid");
			  info.put("fileid", encrypt.uuidfactory());
			  fileinfo fileinfomation = uploadmanagermapper.getfilebyid(fileid);
			  if(fileinfomation != null){
				  int ii = fileinfomation.getUpdateid()+1;
				  info.put("listid", fileinfomation.getListid());
				  info.put("updateid", ii);
				  uploadmanagermapper.updatefile(info);
				  result.put("result", 1);
			  }else{
				  info.put("updateid", 1);
				  uploadmanagermapper.uploadfile(info);
				  result.put("result", 1);		  
			  }
		  }else{
			  result = uploadinfomation;
		  }
		return result;
	}
}
