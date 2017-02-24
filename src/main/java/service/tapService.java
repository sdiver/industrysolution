/**
 *
 * @Title:tapService.java
 *
 * @Package:service
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年2月6日 下午2:49:38
 *
 * @version V1.0
 *
 */
package service;

import java.util.Map;

public interface tapService {
	
	Map<Object, Object> modifytaptoproject(String projectid, String thirdtypeid, String userid);
	
	Map<Object, Object> listtap();
	
	Map<Object, Object> addtap(String tapname, String TestColor, String BackgroundColor);
	
}
