/**
 *
 * @Title:usermanager.java
 *
 * @Package:service
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2016年3月15日 上午9:09:51
 *
 * @version V1.0
 *
 */
package service;

import java.sql.SQLException;
import java.util.Map;

public interface usermanagerService {

	public Map<Object, Object> register(String username, String userpassword, String uname) throws SQLException;
	
	public Map<Object, Object> login(String username, String userpassword);
	
	public Map<Object, Object> changepwd(String userid, String userpassword, String newpassword);
	
	public int checkToken(String userid, String token);
	
	public Map<Object, Object> changeImage(String userid, String ProfilePhoto);
}
