/**
 *
 * @Title:uploadmanagerController.java
 *
 * @Package:controller
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午1:36:36
 *
 * @version V1.0
 *
 */
package controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.uploadmanagerService;
import service.usermanagerService;
import util.downloadFile;

@Controller

@RequestMapping("/uploadmanager")
public class uploadmanagerController {

	private static Logger logger = LoggerFactory.getLogger(usermanagerController.class);

	@Autowired
	
	private usermanagerService userservice;

	@Autowired
	private uploadmanagerService uploadservice;
	
	downloadFile download = new downloadFile();
	
	
	  @RequestMapping(value={"/uploadfile"}, method={RequestMethod.POST})
	  @ResponseBody
	  public Map<Object, Object> uploadfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		  logger.debug("test");

		  Map<Object, Object> map = uploadservice.uploadfile(request);
		  
		  return map;  
		        
	  }
	
	
	
@RequestMapping(value = "/deletefile", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	
	public @ResponseBody
	
	Map<Object, Object> deletefile(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.debug("TEST");
		
		String projectid = request.getParameter("projectid"); 
	
		String fileid = request.getParameter("fileid"); 
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
		
		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = uploadservice.deletefile(projectid,userid,fileid);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
		
	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.POST, produces = "application/json; charset=utf-8")

	public @ResponseBody void downloadfile(HttpServletRequest request,
			
		  HttpServletResponse response) throws Exception {
		
		String url = request.getParameter("url");
		
		download.downloadii(url, response);
		
	}
	
	 @RequestMapping(value={"/updatefile"}, method={RequestMethod.POST})
	  @ResponseBody
	  public Map<Object, Object> updatefile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		  logger.debug("test");

		  Map<Object, Object> map = uploadservice.updatefile(request);
		  
		  return map;  
		        
	  }
	
}
