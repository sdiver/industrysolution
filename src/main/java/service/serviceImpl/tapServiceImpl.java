/**
 *
 * @Title:tapServiceImpl.java
 *
 * @Package:service.serviceImpl
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年2月6日 下午3:57:36
 *
 * @version V1.0
 *
 */
package service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mapper.solutionmanagerMapper;
import mapper.tapMapper;
import model.projectinfo;
import model.tapinfo;
import service.tapService;

@Service("tapServiceImpl")
public class tapServiceImpl implements tapService {	
	@Autowired
	solutionmanagerMapper solutionmanagermapper;	
	@Autowired
	tapMapper tapmapper;	
	/* (non-Javadoc)
	 * @see service.tapService#modifytaptoproject(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> modifytaptoproject(String projectid,
			String thirdtypeid, String userid) {
		// TODO Auto-generated method stub		
		Map<Object, Object> map = new HashMap<Object, Object>();		
		map.put("projectid", projectid);		
		map.put("thirdtypeid", thirdtypeid);		
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(count.getProjectuserid().equals(userid)){		
			tapmapper.modifytaptoproject(map);			
			result.put("result", 1);			
		}else{			
			result.put("result", 2);			
		}		
		return result;		
	}
	/* (non-Javadoc)
	 * @see service.tapService#listtap()
	 */
	public Map<Object, Object> listtap() {
		// TODO Auto-generated method stub		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		List<tapinfo> tapp = tapmapper.listtap();		
		result.put("result", tapp);		
		return result;		
	}
	/* (non-Javadoc)
	 * @see service.tapService#addtap(java.lang.String)
	 */
	public Map<Object, Object> addtap(String tapname, String TestColor, String BackgroundColor) {
		// TODO Auto-generated method stub		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		tapmapper.addtap(tapname);		
		tapmapper.addtap(TestColor);		
		tapmapper.addtap(BackgroundColor);		
		result.put("result", 1);		
		return result;
	}
}
