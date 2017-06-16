package cn.com.aiidc.cms.dao;
/**
 * @author 张鑫
 * ======================
 * @edite by joyu  date 2017/03/23
 * 修改方法：doQuery,findRoleByName ，seachRole，addRole，deleteRole
 * 去除方法中已废弃的方法调用
 *  =======================
 *  */

import cn.com.aiidc.cms.entity.TRole;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public class RoleDao extends HibernateDao
{
	/**
	 * find all the Troles
	 *
	 * @return
	 */
	public List<TRole> doQuery()
	{
		return findAll(TRole.class);
	}

	/**
	 * find role by its name
	 *
	 * @param name
	 * @return
	 */
	public List<TRole> findRoleByName(String name)
	{
		List<TRole> list = findByCondition("roleName", name, TRole.class);
		return list;
	}

	public List<TRole> seachRole(String roleName)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(TRole.class);
		//通过名字模糊检索TUser
		if (roleName != null)
		{
			dc.add(Restrictions.like("roleName", roleName, MatchMode.ANYWHERE));
		}
		return findByCriteria(dc);
	}

	/**
	 * add role
	 *
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void addRole(TRole role)
	{
		save(role);
	}

	@Transactional(readOnly = false)
	public void deleteRole(TRole role)
	{
		delete(role);
	}

	@Transactional(readOnly = false)
	public void deleteRole(Serializable id)
	{
		TRole role = findById(id, TRole.class);
		delete(role);
	}
}

