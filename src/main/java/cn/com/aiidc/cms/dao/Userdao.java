package cn.com.aiidc.cms.dao;

/**
 * @author 张鑫
 * ======================
 * @edite by joyu  date 2017/03/23
 * 修改方法：saveUser ，deleteUser，getUserlistbySearch，getPageUser
 * 去除方法中已废弃的方法调用
 * 增加方法：getAuthoritiesByUsername，loadUserAuthorities
 * =======================
 *  */
import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TDepartment;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.common.security.SecurityContextUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class Userdao extends HibernateDao {
	
	/**
	 * return the user list by department id
	 * @param dept
	 * @return
	 */

	public List<TUser> getUserListByDepId(TDepartment dept){
		return findByCondition("TDepartment", dept, TUser.class);
	
	}
	/**
	 * return the all the user information list
	 * @return
	 */
	public List<TUser> getUserList(){
		return findAll(TUser.class);
	}

	/**
	 * 
	 * <p>Description: 通过用户名条件从数据库中检索TUser对象列表</p>
	 * @author joyu
	 * @param userName
	 * @return
	 */
	public List<TUser> getUserlistbySearch(String userName){
		DetachedCriteria dc = DetachedCriteria.forClass(TUser.class);
		//通过名字模糊检索TUser
		if(userName != null){
			dc.add(Restrictions.like("username",userName,MatchMode.ANYWHERE));
		}
		return findByCriteria(dc);
	}
	
	/**
	 * 
	 * <p>Description:取得TUser的分页数据 </p>
	 * @author joyu
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<TUser> getPageUser(int page,int pageSize){
		DetachedCriteria dc = DetachedCriteria.forClass(TUser.class);
		List<TUser> list = findClassByPage(dc,page,pageSize,TUser.class);
		return list;
	}

	/**
	 * 
	 * <p>Description: 把用户对象TUser 存储到数据库</p>
	 * @author joyu
	 * @param user
	 */
    @Transactional(readOnly = false)
    public void saveUser(TUser user){
    	save(user);
    }
	
    /**
     * 
     * <p>Description: 从数据库中删除一个TUser</p>
     * @author joyu
     * @param user
     */
    @Transactional(readOnly = false)
	public void deleteUser(TUser user){
		delete(user);
	}
	
	/**
	 * 
	 * <p>Description: 通过userId 从数据库检索出一个TUser</p>
	 * @author joyu
	 * @param userId
	 * @return
	 */
	public TUser getUserById(Integer userId){
		return findById(userId, TUser.class);
	}
	
	/**
	 * 
	 * <p>Description: 通过当前用户名从数据库中取得当前用户对象</p>
	 * @author joyu
	 * @return
	 */
	public TUser getCurrentUser(){
		String currentUserName=SecurityContextUtil.getCurrentUserName();
		List<TUser> list = findByCondition("username", currentUserName, TUser.class);
		return list.get(0);
	}
	
	/**
	 * 
	 * <p>Description: 通过用户名从数据库中检索TUser对象</p>
	 * @author joyu
	 * @param username
	 * @return
	 */
	public TUser getByUsername(String username){
/*		String hql="from TUser t where t.userName="+username;
		List<TUser> list=(List<TUser>) getSession().createQuery(hql);
		return list.get(0);*/
		List<TUser> list = findByCondition("username", username, TUser.class);
		return list.get(0);
	}
	
	/**
	 * 根据用户名获取到用户的权限并封装成GrantedAuthority集合
	 * @param username
	 */
	public List<GrantedAuthority> loadUserAuthorities(String username){
/*		List<TAuthorities> list = this.getAuthoritiesByUsername(username);
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		
		for (TAuthorities authority : list) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthorityMark());
			auths.add(grantedAuthority);
		}
		return auths;
*/
		
		List  list = this.getAuthoritiesByUsername(username);
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Iterator<Object[]> it=list.iterator();
		
		while(it.hasNext()){
			Object[] objects = it.next();
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority((String)objects[1]);
			auths.add(grantedAuthority);
		}
		return auths;
	}
	
	/**
	 * 先根据用户名获取到TAuthorities集合
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<TAuthorities> getAuthoritiesByUsername(String username){
		String sql = "select * from t_authorities where authority_id in( "+
				"select distinct authority_id from t_roles_authorities  t1 "+
				"join t_user_role t2 on t1.role_id = t2.role_id "+
				"join t_user t3 on t3.user_id = t2.user_id and t3.user_name=?)";
		
		List<TAuthorities> list=getResultesBySql(sql, username);
		return list;
	}
	/**
	 * 
	 * <p>Description: 根据用户名起得当前用户权限范围内的菜单列表</p>
	 * @author joyu
	 * @param username
	 * @return
	 */
	public List getUserHasAuthorityMenu(String username){
		String sql = "select distinct t2.resource_path from t_authorities_resources t1 "
				+ " join t_resources t2 on t1.resource_id = t2.resource_id join t_authorities t3 "
				+ " on t1.authority_id = t3.authority_id join t_roles_authorities t4  on "
				+ " t3.authority_id = t4.authority_id join t_user_role t5 on t4.role_id = t5.role_id "
				+ " join t_user t6 on t6.user_id = t5.user_id and t2.resource_type='menu' "
				+ " and t6.user_name=? ";
		return getResultesBySql(sql, username);
	}
}
