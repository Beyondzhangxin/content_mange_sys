package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.dao.DepartmentDao;
import cn.com.aiidc.cms.dao.RoleDao;
import cn.com.aiidc.cms.dao.Userdao;
import cn.com.aiidc.cms.entity.TDepartment;
import cn.com.aiidc.cms.entity.TRole;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.MD5Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Controller
@RequestMapping("usermgr")
public class UserMgrController {
	@Resource
	private Userdao userDao;
	@Resource
	private DepartmentDao deptDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private UserService userService;

	@RequestMapping("list")
	public String list() throws Exception {
		System.out.println("============跳转进入用户管理页面=============");
		return "usermgr/list";
	}

	/**
	 * return the userlist according to the department ID
	 * 
	 * @param depId
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "depUsers")
	@ResponseBody
	public List<?> getDepUsers(@RequestParam int depId) throws Exception {
		TDepartment tDepartment = deptDao.findById(depId,TDepartment.class);
		List<TUser> list = userDao.getUserListByDepId(tDepartment);
		return list;
	}

	/**
	 * save or update user information
	 * 
	 * @param user
	 */
	@RequestMapping(value = "saveUpdateUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String saveOrUpdateUser(@ModelAttribute TUser user, HttpServletRequest sr) {
		if(sr.getParameter("roleId")!=""){
			Integer roleId = Integer.valueOf(sr.getParameter("roleId"));
			user.setTRole(roleDao.findById(roleId, TRole.class));
		}
		if(sr.getParameter("depId")!=""){
			Integer depId = Integer.valueOf(sr.getParameter("depId"));
			user.setTDepartment(deptDao.getById(depId));
		}
		if(sr.getParameter("password")!=""){
			try {
				Md5PasswordEncoder encoder=new Md5PasswordEncoder();
				encoder.setEncodeHashAsBase64(true);
				user.setPassword(encoder.encodePassword(sr.getParameter("password"),null));

				user.setPassword(MD5Util.md5_SystemWideSaltSource(user.getUsername(),sr.getParameter("password")));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			userDao.saveOrUpdate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "添加成功";

	}

	@RequestMapping("updateUser")
	@ResponseBody
	public String updateUser(@ModelAttribute TUser user, HttpServletRequest sr) {
		if (sr.getParameter("depId") != "") {
			Integer depId = Integer.valueOf(sr.getParameter("depId"));
			user.setTDepartment(deptDao.getById(depId));
		}
		if(sr.getParameter("depId")!=""){
			Integer depId = Integer.valueOf(sr.getParameter("depId"));
			user.setTDepartment(deptDao.getById(depId));
		}
		if(sr.getParameter("password")!=""){
			try {
				Md5PasswordEncoder encoder=new Md5PasswordEncoder();
				encoder.setEncodeHashAsBase64(true);
				user.setPassword(encoder.encodePassword(sr.getParameter("password"),null));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			userDao.saveOrUpdate(user);
		} catch (Exception e) {
			System.out.println("shibai");
			e.printStackTrace();
		}
		return "修改成功";

	}

	/**
	 * 按页显示用户列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "listdata", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String  getPageUser(@RequestParam int page, @RequestParam int rows) {
		JSONObject jo = new JSONObject();

		List<TUser> list=userDao.getPageUser(page,rows);
		jo.put("total", userService.getListNum());
		jo.put("rows", new JSONArray(list));
		return jo.toString();

	}

	/**
	 * 返回所有用户的列表信息
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = "listall", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody

	public List<TUser> getAllUser()
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, JsonProcessingException {
		List<TUser> userList = userDao.getUserList();
		List<TUser> list = userList;
		/*
		 * JSONArray jrr=new JSONArray(); for(TUser temp: list){
		 * jrr.put(org.apache.commons.beanutils.BeanUtils.describe(temp)); }
		 */
		return list;

	}

	@RequestMapping("searchList")
	@ResponseBody
	public List<TUser> searchByUsername(@RequestParam String username) {

		return userDao.getUserlistbySearch(username);

	}

	@RequestMapping(value = "deleteUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteUser(@RequestParam int[] userId) {
		String result = "删除成功！";
		for (int i : userId) {
			TUser user = userDao.getUserById(i);
			userDao.deleteUser(user);
			if (user == null) {
				result = "删除失败！";
				break;
			}
		}
		return result;
	}

	/*
	 * @RequestMapping("delete") public void delete(){ int userId=5; TUser
	 * user=userDao.getUserById(userId); userDao .deleteUser(user);
	 * System.out.println(user.getUserName()); }
	 */

}