/**
 *
 * @Title:solutionmanagerMapper.java
 *
 * @Package:mapper
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午9:05:01
 *
 * @version V1.0
 *
 */
package mapper;

import model.fileinfo;
import model.projectinfo;
import model.projectuser;
import model.tapinfo;

import java.util.List;
import java.util.Map;

public interface solutionmanagerMapper {
	
	public int check(String projectname);
	public void addproject(Map<Object, Object> map);
	public projectinfo findprojectid(String projectid);
	public void addperson(Map<Object, Object> map);
	public void modifyproject(Map<Object, Object> map);
	public void deleteproject(Map<Object, Object> map);
	public void deletegperson(Map<Object, Object> map);
	public void deleteperson(Map<Object, Object> map);
	public void deletefile(Map<Object, Object> map);
	public List<projectinfo> listproject(Map<Object, Object> map);
	public List<projectuser> listuser(Map<Object, Object> map);
	public List<projectinfo> searchproject(Map<Object, Object> map);
	public int countproject(Map<Object, Object> map);
	public List<projectuser> addpersonlist(Map<Object, Object> map);
	public projectinfo projectinfo(String projectid);
	public List<projectuser> personinfo(String projectid);
	public List<fileinfo> fileinfo(String projectid);
	public int usercount(Map<Object, Object> map);
	public fileinfo findfilebyfileid(String fileid);
	public List<fileinfo> listold(Map<Object, Object> map);
	public tapinfo findtapname(int thirdid);
}
