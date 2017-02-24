/**
 *
 * @Title:solutionmanagerController.java
 *
 * @Package:controller
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午12:58:04
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
import service.solutionmanagerService;
import service.usermanagerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/solutionmanager")
public class solutionmanagerController {
		private static Logger logger = LoggerFactory.getLogger(usermanagerController.class);
		@Autowired
		private usermanagerService userservice;
		@Autowired
		private solutionmanagerService solutionservice;
	@RequestMapping(value = "/addproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
		public @ResponseBody
		Map<Object, Object> addproject(HttpServletRequest request,HttpServletResponse response) throws Exception {
			logger.debug("TEST");
			String projectname = request.getParameter("projectname"); 
			String projectinfo = request.getParameter("projectinfo"); 
			String userid = request.getParameter("userid");
			String token = request.getParameter("token");
			int firsttype = Integer.parseInt(request.getParameter("firsttype"));
			int secondtype = Integer.parseInt(request.getParameter("secondtype"));
			String thirdtype =  request.getParameter("thirdtype");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			Date projecttime =sdf.parse(request.getParameter("projecttime"));  
			int check = userservice.checkToken(userid, token);
			Map<Object, Object> map = new HashMap<Object, Object>();
			if(check ==1){
				map = solutionservice.addproject(projectname, projectinfo, userid, firsttype, secondtype, thirdtype,projecttime);
				return map;
			}
			map.put("result", 3);
			return map;
		}
	
	@RequestMapping(value = "/modifyproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> modifyproject(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String projectid = request.getParameter("projectid"); 
		String projectname = null;
		String projectinfo = null;
		if(request.getParameter("projectname" ) != null || request.getParameter("projectname") != ""){
			projectname = request.getParameter("projectname"); 
		}
		if(request.getParameter("projectinfo") != null || request.getParameter("projectinfo") != ""){
			projectinfo = request.getParameter("projectinfo");
		}
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date projecttime =sdf.parse(request.getParameter("projecttime"));  
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.modifyproject(projectid, projectname, projectinfo, userid, projecttime);
			return map;
		}
		map.put("result", 3);
		return map;
	}
	
	@RequestMapping(value = "/deleteproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> deleteproject(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String projectid = request.getParameter("projectid"); 
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.deleteproject(projectid, userid);
			return map;
		}
		map.put("result", 3);
		return map;
	}
	
	@RequestMapping(value = "/listproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> listproject(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		int pagePerNum = Integer.parseInt(request.getParameter("pagePerNum"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		int order = Integer.parseInt(request.getParameter("order"));
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		int firsttype = Integer.getInteger(request.getParameter("firsttype"));
		int secondtype = Integer.getInteger(request.getParameter("secondtype"));
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.listproject(pagePerNum,pageNum, order,userid, firsttype, secondtype);
			return map;
		}
		map.put("result", 3);
		return map;
		
	}
	
@RequestMapping(value = "/searchproject", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> searchproject(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		 int pagePerNum = Integer.parseInt(request.getParameter("pagePerNum"));
		 int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		 int order = Integer.parseInt(request.getParameter("order"));
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		String projectname = null;
		String uuserid = null;
		Date projectMintime = null;
		Date projectMaxtime = null;	
		if(request.getParameter("projectname") != null && request.getParameter("projectname") != ""){
			projectname = request.getParameter("projectname");
		}
		if(request.getParameter("uuserid") != null && request.getParameter("uuserid") != ""){
			uuserid = request.getParameter("uuserid");
		}
		if(request.getParameter("projectMintime") != null && request.getParameter("projectMintime") != ""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			projectMintime = sdf.parse(request.getParameter("projectMintime"));
		}
		if(request.getParameter("projectMaxtime") != null && request.getParameter("projectMaxtime") != ""){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			projectMaxtime = sdf.parse(request.getParameter("projectMaxtime"));
		}
		int firsttype = Integer.parseInt(request.getParameter("firsttype"));
		int secondtype = Integer.parseInt(request.getParameter("secondtype"));
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.searchproject(pagePerNum,pageNum, order,userid,projectname, uuserid,firsttype, secondtype, projectMintime, projectMaxtime);
			return map;
		}
		map.put("result", 3);
		return map;
		
	}

	@RequestMapping(value = "/listuser", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> listuser(HttpServletRequest request,HttpServletResponse response) throws Exception {
	logger.debug("TEST");
	String userid = request.getParameter("userid");
	String token = request.getParameter("token");
	int firstType = Integer.parseInt(request.getParameter("firstType"));
	int secondType = Integer.parseInt(request.getParameter("secondType"));
	int check = userservice.checkToken(userid, token);
	Map<Object, Object> map = new HashMap<Object, Object>();
	if(check ==1){
		map = solutionservice.listuser(firstType, secondType);
	}else{
		map.put("result", 3);
	}
	return map;
}
	
	@RequestMapping(value = "/addperson", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> addperson(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		String projectid = request.getParameter("projectid");
		String useridlist = request.getParameter("useridlist");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.addperson(userid, projectid, useridlist);
			return map;
		}
		map.put("result", 3);
		return map;
	}
	
	@RequestMapping(value = "/addpersonlist", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> addpersonlist(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		String projectid = request.getParameter("projectid");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = solutionservice.addpersonlist(userid, projectid);
			return map;
		}
		map.put("result", 3);
		return map;
	}

	@RequestMapping(value = "/deleteperson", method = RequestMethod.POST, produces = "application/json; charset=utf-8")

	public @ResponseBody

	Map<Object, Object> deleteperson(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		logger.debug("TEST");
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
	
		String projectid = request.getParameter("projectid");
		
		String uuserid = request.getParameter("uuserid");
		
		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = solutionservice.deleteperson(userid, projectid, uuserid);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
	
	}

	@RequestMapping(value = "/projectinfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")

	public @ResponseBody//添加用户表、文件列表

	Map<Object, Object> projectinfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		logger.debug("TEST");
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
	
		String projectid = request.getParameter("projectid");

		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = solutionservice.projectinfo(userid, projectid);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
	
	}
	
	@RequestMapping(value = "/findupdateversion", method = RequestMethod.POST, produces = "application/json; charset=utf-8")

	public @ResponseBody//添加用户表、文件列表

	Map<Object, Object> findupdateversion(HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		logger.debug("TEST");
		
		String userid = request.getParameter("userid");
		
		String token = request.getParameter("token");
		
		String fileid = request.getParameter("fileid");

		int check = userservice.checkToken(userid, token);
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		if(check ==1){
	
			map = solutionservice.findupdateversion(fileid);
		
		}else{
			
			map.put("result", 3);
			
		}
		
		return map;
	
	}
	
	
	
}
