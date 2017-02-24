/**
 *
 * @Title:usermanagerController.java
 *
 * @Package:controller
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2016年3月30日 上午11:04:18
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

import service.usermanagerService;

@Controller

@RequestMapping("/usermanager")

public class usermanagerController {
	private static Logger logger = LoggerFactory.getLogger(usermanagerController.class);
	@Autowired
	private usermanagerService userservice;
	
@RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> register(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String username = request.getParameter("username"); 
		String userpassword = request.getParameter("userpassword"); 
		String uname = request.getParameter("uname"); 
		Map<Object, Object> map = userservice.register(username, userpassword, uname);
		return map;
	}

@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> login(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String username = request.getParameter("username"); 
		String userpassword = request.getParameter("userpassword");
		Map<Object, Object> map = userservice.login(username, userpassword);
		return map;
	}

@RequestMapping(value = "/changepwd", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> changepwd(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String userid = request.getParameter("userid");
		String userpassword = request.getParameter("userpassword");
		String newpassword = request.getParameter("newpassword");
		String token = request.getParameter("token");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = userservice.changepwd(userid, userpassword, newpassword);
		}else{
			map.put("result", 3);
		}
		return map;
	}

@RequestMapping(value = "/checkToken", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> checkToken(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map.put("result", 1);
		}else{
			map.put("result", 3);
		}
		return map;
	}

	@RequestMapping(value = "/changeImage", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public @ResponseBody
	Map<Object, Object> changeImage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("TEST");
		String userid = request.getParameter("userid");
		String token = request.getParameter("token");
		String ProfilePhoto = request.getParameter("ProfilePhoto");
		int check = userservice.checkToken(userid, token);
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(check ==1){
			map = userservice.changeImage(userid, ProfilePhoto);
			return map;
		}
		map.put("result", 3);
		return map;
	}

}
