/**
 *
 * @Title:registerMapper.java
 *
 * @Package:mapper
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2016年3月18日 上午9:54:35
 *
 * @version V1.0
 *
 */
package mapper;

import java.util.Map;

import model.usertable;

public interface usermanagerMapper {

	public int checkusername(Map<Object, Object> map);
	
	public void register(Map<Object, Object> map);
	
	public usertable login(Map<Object, Object> map);
	
	public void settime(Map<Object, Object> map);
	
	public usertable finduser(Map<Object, Object> map);
	
	public void changepwd(Map<Object, Object> map);

	public int checktoken(Map<Object, Object> map);
	
	public void changeImage(Map<Object, Object> map);

}
