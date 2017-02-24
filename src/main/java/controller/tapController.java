/**
 *
 * @Title:tapController.java
 *
 * @Package:controller
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年2月6日 下午2:42:10
 *
 * @version V1.0
 *
 */
package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.tapService;
import service.usermanagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller

@RequestMapping("/tapController")

public class tapController {
	
	private static Logger logger = LoggerFactory.getLogger(usermanagerController.class);

	@Autowired
	private tapService tapservice;
	
	@Autowired
	private usermanagerService userservice;
	
@RequestMapping(value = "/modifytaptoproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	
	public @ResponseBody
	
	Map<Object, Object> modifytaptoproject(HttpServletRequest request) throws Exception {
		
		logger.debug("TEST");
		
		String projectid = request.getParameter("projectid"); 

		String thirdtypeid = request.getParameter("thirdtypeid"); 
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
		
		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){

			map = tapservice.modifytaptoproject(projectid, thirdtypeid, userid);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
		
	}

	@RequestMapping(value = "/listtap", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	
	public @ResponseBody
	
	Map<Object, Object> listtap(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.debug("TEST");
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
		
		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = tapservice.listtap();
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
		
	}
	
	@RequestMapping(value = "/addtap", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	
	public @ResponseBody
	
	Map<Object, Object> addtap(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		logger.debug("TEST");
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
		
		String tapname = request.getParameter("tapname");
		
		String TestColor = request.getParameter("TestColor");
		
		String BackgroundColor = request.getParameter("BackgroundColor");
		
		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = tapservice.addtap(tapname, TestColor, BackgroundColor);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
		
	}

}
