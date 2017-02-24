/**
 *
 * @Title:usermanagerServiceImpl.java
 *
 * @Package:serviceImpl
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2016年3月18日 上午9:35:43
 *
 * @version V1.0
 *
 */
package service.serviceImpl;

import mapper.usermanagerMapper;
import model.usertable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.usermanagerService;
import util.ImageUpload;
import util.encryption;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("usermanagerServiceImpl")
public class usermanagerServiceImpl implements usermanagerService{
	@Autowired
	private usermanagerMapper usermanagermapper;
	encryption encrypt = new encryption();
	/* (non-Javadoc)
	 * @see service.usermanagerService#register(int, int, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<Object, Object> register(String username,
			String userpassword, String uname) throws SQLException{
		// TODO Auto-generated method stub
		String userid = encrypt.uuidfactory();
		String password = encrypt.MD5(userpassword);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userid", userid);
		map.put("username", username);
		map.put("userpassword", password);
		map.put("uname", uname);
		int usernum = usermanagermapper.checkusername(map);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(usernum == 0){
			usermanagermapper.register(map);
			result.put("result", 1);
		}else{
			result.put("result", 0);
		}
		return result;
	}
	/* (non-Javadoc)
	 * @see service.usermanagerService#login(int, java.lang.String, java.lang.String)
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<Object, Object> login(String username, String userpassword) {
		// TODO Auto-generated method stub
		String password = encrypt.MD5(userpassword);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("username", username);
		map.put("userpassword", password);
		usertable userinfo = usermanagermapper.login(map);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(userinfo == null){
			result.put("result", 0);
		}else{
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=format.format(date);
			String token = encrypt.MD5(userinfo.getUserid() + username + time);
			Map<Object, Object> mapcode = new HashMap<Object, Object>();
			mapcode.put("userid", userinfo.getUserid());
			mapcode.put("token", token);
			usermanagermapper.settime(mapcode);
			userinfo.setToken(token);
			result.put("result", userinfo);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see service.usermanagerService#changepwd(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	
	@Transactional(rollbackFor=Exception.class)
	public Map<Object, Object> changepwd(String userid,
			String userpassword, String newpassword) {
		// TODO Auto-generated method stub
		String password = encrypt.MD5(userpassword);
		String npassword = encrypt.MD5(newpassword);
		Map<Object, Object> log = new HashMap<Object, Object>();
		log.put("userid", userid);
		log.put("userpassword", password);
		usertable userinfo = usermanagermapper.finduser(log);
		Map<Object, Object> result = new HashMap<Object, Object>();
		if(userinfo == null){
			result.put("result", 0);
		}else{
			if(newpassword != null && newpassword != ""){
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("userid", userid);
				map.put("newpassword", npassword);
				usermanagermapper.changepwd(map);
				result.put("result", 1);
			}else{
				result.put("result", 2);
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see service.usermanagerService#checkToken(int, java.lang.String, java.lang.String)
	 */
	public int checkToken(String userid, String token) {
		// TODO Auto-generated method stub
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("userid", userid);
		map.put("token", token);
		int num = usermanagermapper.checktoken(map);
		return num;
	}
	/* (non-Javadoc)
	 * @see service.usermanagerService#changeImage(java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> changeImage(String userid, String ProfilePhoto) {
		// TODO Auto-generated method stub
		ImageUpload imageupload = new ImageUpload();
		Map<Object,Object> result = new HashMap<Object,Object>();
		Map<String,Object> image = new HashMap<String,Object>();
		Map<Object, Object> info = new HashMap<Object, Object>();
		try {
			image = imageupload.upload(ProfilePhoto, userid);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("result", 2);
			return result;
		}
		String MSG = (String) image.get("MSG");
		if(MSG.equals("SUCCESS")){
			String photominiurl = (String) image.get("miniURL");
			info.put("userid", userid);
			info.put("picminiUrl", photominiurl);
			usermanagermapper.changeImage(info);
			result.put("result", 1);
			return result;
		}
		result.put("result", 4);
		return result;
	}

}
