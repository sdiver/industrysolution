/**
 *
 * @Title:uploadmanagerMapper.java
 *
 * @Package:mapper
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月14日 下午7:37:25
 *
 * @version V1.0
 *
 */
package mapper;

import java.util.Map;

import model.fileinfo;

public interface uploadmanagerMapper {
	
	public String getfilebylistid(Map<Object, Object> map);
	
	public void uploadfile(Map<Object, Object> map);
	
	public void updatefile(Map<Object, Object> map);
	
	public int personlist(Map<Object, Object> map);
	
	public fileinfo findbyfileid(String fileid);
	
	public void deletefile(String fileid);
	
	public  fileinfo getfilebyid(String fileid);
	
}
