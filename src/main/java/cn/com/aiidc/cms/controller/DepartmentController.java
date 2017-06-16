package cn.com.aiidc.cms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.type.TrueFalseType;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.dao.DepartmentDao;
import cn.com.aiidc.cms.entity.EasyuiDepartmentTreeNode;
import cn.com.aiidc.cms.entity.TDepartment;
import cn.com.aiidc.common.util.BeanUtils;

@Controller
@RequestMapping("deptmgr")
public class DepartmentController {
	@Resource
	private DepartmentDao departmentDao;

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@RequestMapping("list")
	@Secured("管理员")
	public String list()
	throws Exception
	{	
		System.out.println("============跳转进入部门管理页面=============");
		return "deptmgr/list";
	}
	
	/**
	 * 返回整个部门列表的easyUI树
	 */
	@RequestMapping(value="getDepartmentTree",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getDepartmentTree() 
	throws Exception
	{
		List<EasyuiDepartmentTreeNode> list = new ArrayList<EasyuiDepartmentTreeNode>();
		for (TDepartment temp : departmentDao.getRootList()) {
			list.add(departmentDao.getEasyuiDepartmentTreeNode(temp.getDepId()));
		}
		return BeanUtils.describeToJson(list).toString();

	}

	/**
	 * 返回所有部门的列表信息
	 * 
	 * @return
	 */
	@RequestMapping(value="listdata",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getDepartmentList() 
	throws Exception
	{
		//return departmentDao.doQuery();
		
		return BeanUtils.describeToJson(departmentDao.doQuery()).toString();
	}
	/**
	 * 保存或修改更新部门实体类
	 * @param tDepartment
	 */
	@RequestMapping(value="saveOrUpdate")
	public void saveDepartment(@RequestBody TDepartment tDepartment) 
	{
		departmentDao.saveOrUpdate(tDepartment);
	}
	/**
	 * 输入为部门ID，返回若为1，表明删除成功，为0表示删除失败
	 * @param depId
	 * @return
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public int deleteDepartment(@RequestParam int depId){
		return departmentDao.DeleteById(depId);
	}
}
