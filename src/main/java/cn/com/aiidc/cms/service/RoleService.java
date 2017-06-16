package cn.com.aiidc.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.HibernateDao;
import cn.com.aiidc.cms.entity.TRole;
import cn.com.aiidc.cms.entity.TTable;

@Service
public class RoleService {
	@Resource
	private HibernateDao hibernateDao;
	
	public HibernateDao getHibernateDao() 
	{
		return hibernateDao;
	}

	public void setHibernateDao(HibernateDao hibernateDao) 
	{
		this.hibernateDao = hibernateDao;
	}
	
	/**
	 * 查询所有的Role信息
	 * @return
	 */
	public List<TRole> findAllRole(){
		List<TRole> roles = getHibernateDao().findAll(TRole.class);
		return roles;
	}
	
	/**
	 * 查询所有的table
	 * @return
	 */
	public List<TTable> findAllTable(){
		List<TTable> tables = getHibernateDao().findAll(TTable.class);
		return tables;
	}
	
	/**
	 * 根据roleName查询该角色是否已存在
	 * @param roleName
	 * @return
	 */
	public String findByName(String roleName){
		DetachedCriteria dc = DetachedCriteria.forClass(TRole.class);
		dc.add(Restrictions.eq("roleName", roleName));
		List<TRole> role =getHibernateDao().findByCriteria(dc, TRole.class);
		String msg=null;
		if(role.size() != 0){
			msg = "角色名已存在";
		}
		return msg;
	}
	
	
	public void addRole(TRole role){
		 getHibernateDao().save(role);
	}
	
	/**
	 * 根据id查询role
	 * @param roleId
	 * @return
	 */
	public TRole findRoleById(Integer roleId){
		TRole role = getHibernateDao().findById(roleId, TRole.class);
		return role;
	}
	
	
	public void findRoleByWhere(Map<Object,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TRole.class);
		
	}
	public int deleteRole(Integer roleId){
		TRole role=findRoleById(roleId);
		if(role!=null){
			hibernateDao.delete(role);
			return 1;
		}else {
			return 0;
		}
	}
}


















