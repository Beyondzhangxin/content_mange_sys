package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.dao.RoleDao;
import cn.com.aiidc.cms.entity.TRole;
import org.jboss.logging.Param;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("rolemgr")
public class RoleMgrController {

    @Resource
	private RoleDao roleDao;

    public RoleDao getRoleDao()
    {
        return roleDao;
    }

    public void setRoleDao(RoleDao roleDao)
    {
        this.roleDao = roleDao;
    }
	@RequestMapping("list")
	public String list()
	throws Exception
	{	
		System.out.println("============跳转进入角色管理页面=============");
		return "rolemgr/list";
	}
	

	@RequestMapping(value = "listdata", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String listdata() throws Exception {
		List<TRole> roles = roleDao.doQuery();
		JSONArray jarr = new JSONArray();
		for (TRole role : roles) {
			JSONObject temp = new JSONObject();
			temp.put("roleId", role.getRoleId());
			temp.put("roleName", role.getRoleName());
			//temp.put("unitcost", "<a href='#'>[编辑]</a>" + "<a href='#'>[删除]</a>");
			jarr.put(temp);
		}
		return jarr.toString();
	}

	@RequestMapping(value = "saveOrUpdateRole", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addRole(@ModelAttribute TRole role) throws Exception {
		try
		{
			roleDao.saveOrUpdate(role);
		} catch (Exception e)
		{
			e.printStackTrace();
			return "失败！";
		}
		return "成功！";

	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public String deleteRole(@RequestParam int[] roleId) {
		try {
			for(int i:roleId){
                TRole role = roleDao.findById(i,TRole.class);
                roleDao.delete(role);
			}
			return "sucess";
		} catch (Exception e) {
		   return "fail";
		}
		// return JSON.toJSONString(result);

	}
	@RequestMapping("searchRole")
	@ResponseBody
    public List<TRole> getSearchList(@Param String roleName){
    	return roleDao.seachRole(roleName);
    }
    
	@RequestMapping("findById")
	@ResponseBody
	public String findRoleById(Integer roleId) {
		TRole role = roleDao.findById(roleId,TRole.class);
		JSONObject jobj = new JSONObject(role);
		return jobj.toString();
	}
    @RequestMapping("test")
	public void test(){
        TRole role=roleDao.findById(213,TRole.class);
        roleDao.delete(role);
    }


}