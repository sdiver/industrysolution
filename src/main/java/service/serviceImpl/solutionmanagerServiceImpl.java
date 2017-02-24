/**
 *
 * @Title:solutionmanagerServiceImpl.java
 *
 * @Package:service.serviceImpl
 *
 * @Description:TODO
 *
 * @author shi sdiver
 *
 * @date 2017年1月12日 上午1:52:11
 *
 * @version V1.0
 *
 */
package service.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mapper.solutionmanagerMapper;
import model.fileinfo;
import model.projectinfo;
import model.projectuser;
import model.tapinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import service.solutionmanagerService;
import util.encryption;

@Service("solutionmanagerServiceImpl")
public class solutionmanagerServiceImpl implements solutionmanagerService{
	@Autowired
	solutionmanagerMapper solutionmanagermapper;	
	encryption encrypt = new encryption();	
	/* (non-Javadoc)
	 * @see service.solutionmanagerService#addproject(java.lang.String, java.lang.String, java.lang.String)
	 */
	
	@Transactional(rollbackFor=Exception.class)
	public Map<Object, Object> addproject(String projectname,
			String projectinfo, String userid,int firsttype, int secondtype, String thirdtype, Date projecttime) {
		// TODO Auto-generated method stub
		Map<Object, Object> map = new HashMap<Object, Object>();		
		String projectid = encrypt.uuidfactory();		
		map.put("projectid", projectid);	
		map.put("projectname", projectname);		
		map.put("projectinfo", projectinfo);		
		map.put("projectuserid", userid);		
		map.put("userid", userid);		
		map.put("firsttype", firsttype);		
		map.put("secondtype", secondtype);		
		map.put("thirdtype", thirdtype);
		map.put("projecttime", projecttime);
		int count = solutionmanagermapper.check(projectname);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(count == 0){			
			solutionmanagermapper.addproject(map);			
			String personlinkid = encrypt.uuidfactory();			
			map.put("personlinkid", personlinkid);			
			solutionmanagermapper.addperson(map);			
			result.put("result", 1);			
		}else{			
			result.put("result", 0);			
		}		
		return result;		
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#modifyproject(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> modifyproject(String projectid,
			String projectname, String projectinfo, String userid, Date projecttime) {
		// TODO Auto-generated method stub		
		Map<Object, Object> map = new HashMap<Object, Object>();		
		map.put("projectid", projectid);		
		map.put("projectname", projectname);		
		map.put("projectinfo", projectinfo);		
		map.put("projectuserid", userid);	
		map.put("projecttime", projecttime);
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		int countnum = solutionmanagermapper.check(projectname);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(countnum == 0 || count.getProjectname().equals(projectname)){		
			if(count.getProjectuserid().equals(userid)){			
				solutionmanagermapper.modifyproject(map);				
				projectinfo info = solutionmanagermapper.findprojectid(projectid);			
				result.put("projectinfo", info);				
			}else{				
				result.put("result", 2);				
			}		
		}else{			
			result.put("result", 4);			
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#deleteproject(java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> deleteproject(String projectid, String userid) {
		// TODO Auto-generated method stub		
		Map<Object, Object> map = new HashMap<Object, Object>();		
		map.put("projectid", projectid);		
		map.put("projectuserid", userid);		
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(count.getProjectuserid().equals(userid)){		
			solutionmanagermapper.deleteproject(map);			
			solutionmanagermapper.deletegperson(map);			
			solutionmanagermapper.deletefile(map);			
			result.put("result", 1);			
		}else{			
			result.put("result", 2);			
		}		
		return result;
	}
		
	/* (non-Javadoc)
	 * @see service.solutionmanagerService#listproject()
	 */
	public Map<Object, Object> listproject(int pagePerNum, int pageNum, int order,String userid, int firsttype, int secondtype) {		
		// TODO Auto-generated method stub		
		Map<Object, Object> map = new HashMap<Object, Object>();		
		int min = pagePerNum * pageNum - pagePerNum;		 
		map.put("userid", userid);		
		map.put("min",min );		
		map.put("pagePerNum",pagePerNum );		
		map.put("order", order);		
		map.put("firsttype", firsttype);		
		map.put("secondtype", secondtype);		
		List<projectinfo> listprojectinfo = solutionmanagermapper.listproject(map);		
		List<projectinfo> resultlist = new ArrayList<projectinfo>();		
		for(int i = 0; i < listprojectinfo.size(); i ++){			
			projectinfo info = listprojectinfo.get(i);			
			if(info.getFirsttype() == 1 && info.getSecondtype()==2){				
				String[] thirdlist = info.getThirdtype().split(",");				
				List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();				
				for(int j = 0; j < thirdlist.length; j++){					
					int thirdid = Integer.parseInt(thirdlist[j]);					
					tapinfo thirdtypename =solutionmanagermapper.findtapname(thirdid);					
					thirdtypesuffer.add(thirdtypename);					
				}				
				info.setThirdrealtype(thirdtypesuffer);				
			}else if(info.getFirsttype() == 2){				
				tapinfo ThirdTypeNameVo = null;				
				String ThirdTypeName = null;				
				List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();				
				if(info.getThirdtype().equals("1")){					
					ThirdTypeName = "主业";					
					ThirdTypeNameVo = new tapinfo(ThirdTypeName);					
					thirdtypesuffer.add(ThirdTypeNameVo);					
					info.setThirdrealtype(thirdtypesuffer);					
				}else{					
					ThirdTypeName = "集成";				
					ThirdTypeNameVo = new tapinfo(ThirdTypeName);					
					thirdtypesuffer.add(ThirdTypeNameVo);					
					info.setThirdrealtype(thirdtypesuffer);					
				}				
			}				
			resultlist.add(info);			
		}		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		result.put("projectinfo", resultlist);		
		return result;		
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#addperson(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> addperson(String userid, String projectid,
			String useridlist) {
		// TODO Auto-generated method stub		
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(count.getProjectuserid().equals(userid)){			
			String[] useridll = useridlist.split(",");			
			for(int i =0; i < useridll.length; i++){
				String aa = useridll[i];				
				Map<Object, Object> info = new HashMap<Object, Object>();				
				String personlinkid = encrypt.uuidfactory();				
				info.put("personlinkid", personlinkid);
				info.put("userid", aa);
				info.put("projectid", projectid);
				int usercount = solutionmanagermapper.usercount(info);
				if(usercount == 0){
					solutionmanagermapper.addperson(info);
				}
			}
			result.put("result", 1);
		}else{
			result.put("result", 2);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#addpersonlist(java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> addpersonlist(String userid, String projectid) {
		// TODO Auto-generated method stub		
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(count.getProjectuserid().equals(userid)){		
			Map<Object, Object> map = new HashMap<Object, Object>();			
			map.put("projectid", projectid);			
			map.put("userid", userid);			
			List<projectuser> puser = solutionmanagermapper.addpersonlist(map);			
			result.put("usercheck", puser);		
		}else{
			result.put("result", 2);
		}		
		return result;
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#deleteperson(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> deleteperson(String userid, String projectid,
			String uuserid) {
		// TODO Auto-generated method stub		
		projectinfo count = solutionmanagermapper.findprojectid(projectid);		
		Map<Object, Object> result = new HashMap<Object, Object>();		
		if(userid.equals(uuserid)){		
			result.put("result", 4);			
		}else{			
			if(count.getProjectuserid().equals(userid)){				
				Map<Object, Object> info = new HashMap<Object, Object>();					
				info.put("userid", uuserid);					
				info.put("projectid", projectid);					
				solutionmanagermapper.deleteperson(info);					
				result.put("result", 1);
			}else{				
				result.put("result", 2);				
			}			
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#projectinfo(java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> projectinfo(String userid, String projectid) {
		// TODO Auto-generated method stub		
		projectinfo projectinfomation = solutionmanagermapper.projectinfo(projectid);		
		List<projectuser> projectuserinfo = solutionmanagermapper.personinfo(projectid);		
		List<fileinfo> fileinfomation = solutionmanagermapper.fileinfo(projectid);		
		projectinfo info = projectinfomation;		
		if(info.getFirsttype() == 1 && info.getSecondtype()==2){			
			String[] thirdlist = info.getThirdtype().split(",");			
			List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();
			for(int j = 0; j < thirdlist.length; j++){				
				int thirdid = Integer.parseInt(thirdlist[j]);				
				tapinfo thirdtypename =solutionmanagermapper.findtapname(thirdid);				
				thirdtypesuffer.add(thirdtypename);				
			}			
			info.setThirdrealtype(thirdtypesuffer);			
		}else if(info.getFirsttype() == 2){			
			tapinfo ThirdTypeNameVo = null;			
			String ThirdTypeName = null;			
			List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();			
			if(info.getThirdtype().equals("1")){				
				ThirdTypeName = "主业";				
				ThirdTypeNameVo = new tapinfo(ThirdTypeName);		
				thirdtypesuffer.add(ThirdTypeNameVo);
				info.setThirdrealtype(thirdtypesuffer);				
			}else{			
				ThirdTypeName = "集成";			
				ThirdTypeNameVo = new tapinfo(ThirdTypeName);					
				thirdtypesuffer.add(ThirdTypeNameVo);
				info.setThirdrealtype(thirdtypesuffer);				
			}			
		}	
		Map<Object, Object> result = new HashMap<Object, Object>();	
		result.put("projectinfo", info);		
		result.put("projectuserinfo", projectuserinfo);		
		result.put("fileinfomation", fileinfomation);		
		return result;
	}

	/* (non-Javadoc)
	 * @see service.solutionmanagerService#searchproject(int, int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<Object, Object> searchproject(int pagePerNum, int pageNum,
	    int order, String userid, String projectname, String uuserid, int firsttype, int secondtype, Date projectMintime, Date projectMaxtime) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		int min = pagePerNum * pageNum - pagePerNum;
		map.put("userid", userid);
		map.put("min",min );
		map.put("pagePerNum",pagePerNum );
		map.put("order", order);
		map.put("projectname", projectname);
		map.put("uuserid", uuserid);
		map.put("firsttype", firsttype);
		map.put("secondtype", secondtype);
		map.put("projectMintime", projectMintime);
		map.put("projectMaxtime", projectMaxtime);
		List<projectinfo> listprojectinfo = solutionmanagermapper.searchproject(map);
		List<Object> resultlist = new ArrayList<Object>();
		for(int i = 0; i < listprojectinfo.size(); i ++){
			projectinfo info = listprojectinfo.get(i);
			if(info.getFirsttype() == 1 && info.getSecondtype()==2){
				String[] thirdlist = info.getThirdtype().split(",");
				List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();
				for(int j = 0; j < thirdlist.length; j++){
					int thirdid = Integer.parseInt(thirdlist[j]);
					tapinfo thirdtypename =solutionmanagermapper.findtapname(thirdid);
					thirdtypesuffer.add(thirdtypename);
				}
				info.setThirdrealtype(thirdtypesuffer);
			}else if(info.getFirsttype() == 2){
				tapinfo ThirdTypeNameVo = null;
				String ThirdTypeName = null;
				List<tapinfo> thirdtypesuffer = new ArrayList<tapinfo>();
				if(info.getThirdtype().equals("1")){
					ThirdTypeName = "主业";
					ThirdTypeNameVo = new tapinfo(ThirdTypeName);		
					thirdtypesuffer.add(ThirdTypeNameVo);
					info.setThirdrealtype(thirdtypesuffer);
				}else{
					
					ThirdTypeName = "集成";
					ThirdTypeNameVo = new tapinfo(ThirdTypeName);		
					thirdtypesuffer.add(ThirdTypeNameVo);
					info.setThirdrealtype(thirdtypesuffer);
				}
			}
			resultlist.add(info);
		}
		int countproject = solutionmanagermapper.countproject(map);
		Map<Object, Object> result = new HashMap<Object, Object>();
		result.put("projectinfo", resultlist);
		result.put("projectnum", countproject);
		return result;
	}
	
	public Map<Object, Object> listuser(int firstType, int secondType) {		
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("firstType", firstType);
		map.put("secondType", secondType);
		List<projectuser> user = solutionmanagermapper.listuser(map);		
		result.put("projectuser", user);		
		return result;		
	}
	
	public Map<Object, Object> findupdateversion(String fileid){	
		Map<Object, Object> result = new HashMap<Object, Object>();	
		fileinfo info = solutionmanagermapper.findfilebyfileid(fileid);		
		Map<Object, Object> idlist = new HashMap<Object, Object>();		
		idlist.put("projectid", info.getProjectid());		
		idlist.put("listid", info.getListid());		
		idlist.put("fileid", fileid);		
		List<fileinfo> infomation = solutionmanagermapper.listold(idlist);		
		result.put("oldfile", infomation);		
		return result;
	}

}
