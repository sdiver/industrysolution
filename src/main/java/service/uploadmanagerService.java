/**
 *
 * @Title:uploadmanagerService.java
 *
 * @Package:service
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午4:16:03
 *
 * @version V1.0
 *
 */
package service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface uploadmanagerService {

	public Map<Object, Object> uploadfile(HttpServletRequest request);
	
	public Map<Object, Object>deletefile(String projectid, String userid, String fileid);
	
	public Map<Object, Object> updatefile(HttpServletRequest request);
	
}
