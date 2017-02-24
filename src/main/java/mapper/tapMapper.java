/**
 *
 * @Title:tapMapper.java
 *
 * @Package:mapper
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年2月6日 下午4:22:25
 *
 * @version V1.0
 *
 */
package mapper;

import java.util.List;
import java.util.Map;

import model.tapinfo;

public interface tapMapper {

	public void modifytaptoproject(Map<Object, Object> map);
	
	public List<tapinfo> listtap();
	
	public void addtap(String tapname);
	
}
