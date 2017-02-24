/**
 *
 * @Title:solutionmanagerService.java
 *
 * @Package:service
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午1:43:06
 *
 * @version V1.0
 *
 */
package service;

import java.util.Date;
import java.util.Map;

public interface solutionmanagerService {

	Map<Object, Object> addproject(String projectname, String projectinfo, String userid, int firsttype, int secondtype, String thirdtype, Date projecttime);
	
	Map<Object, Object> modifyproject(String projectid, String projectname, String projectinfo, String userid, Date projecttime);
	
	Map<Object, Object> deleteproject(String projectid, String userid);
	
	Map<Object, Object> listproject(int pagePerNum, int pageNum, int order, String userid, int firsttype, int secondtype);
	
	Map<Object, Object> listuser(int firsttype, int secondtype);
	
	Map<Object, Object> searchproject(int pagePerNum, int pageNum, int order, String userid, String projectname, String uuserid, int firsttype, int secondtype, Date projectMintime, Date projectMaxtime);
	
	Map<Object, Object> addperson(String userid, String projectid, String useridlist);
	
	Map<Object, Object> addpersonlist(String userid, String projectid);
	
	Map<Object, Object> deleteperson(String userid, String projectid, String uuserid);
	
	Map<Object, Object> projectinfo(String userid, String projectid);
	
	Map<Object, Object> findupdateversion(String fileid);

}
