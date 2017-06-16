package cn.com.aiidc.cms.dao;

import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TAuthoritiesResources;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * 
 * @author joyu
 * @date 2017年3月28日
 */
@Transactional
@Repository
public class AuthoritiesResourcesDao extends HibernateDao{
	
	/**
	 * <p>Description: 返回全部的AuthoritiesResources</p>
	 * @author joyu
	 * @return list
	 */
	
	public List<TAuthoritiesResources> getAuthoritiesResourcesList(){
		return findAll(TAuthoritiesResources.class);
	}
		
	/**
	 * <p>Description: 返回分页数据</p>
	 * @author joyu
	 * @param page
	 * @param pageSize
	 * @return
	 */
	
	public List<TAuthoritiesResources> getPageAuthoritiesResources(int page,int pageSize){
		DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
		List<TAuthoritiesResources> list = findClassByPage(dc,page,pageSize,TAuthoritiesResources.class);
		return list;
	}
    @Transactional(readOnly = false)
    public void saveAuthoritiesResources(TAuthoritiesResources authoritiesResources){
    	save(authoritiesResources);
    }
    @Transactional(readOnly = false)
	public void deleteAuthoritiesResources(TAuthoritiesResources authoritiesResources){
		delete(authoritiesResources);
	}
	public TAuthoritiesResources getAuthoritiesResourcesById(int id){
		//return getSession().find(TAuthoritiesResources.class, id); 
		return findByCondition("id", id+"", TAuthoritiesResources.class).get(0);
	}
	
	/**
     * <p>Description: 取得所有的资源对应的权限数据</p>
	 * @author joyu
	 * @return List<Map<String,String>>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getURLResourceMapping(){
		String sql = "select t3.resource_path,t2.authority_mark from t_authorities_resources t1 "+
				"join t_authorities t2 on t1.authority_id = t2.authority_id "+
				"join t_resources t3 on t1.resource_id = t3.resource_id and t3.resource_type='url' order by t3.priority DESC";
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		List ls = getResultesBySql(sql, "");
		Iterator<Object[]> it=ls.iterator();
		
		while(it.hasNext()){
			Object[] objects = it.next();
			Map<String,String> map = new HashMap<String,String>();
			map.put("resourcePath", (String)objects[0]);
			map.put("authorityMark", (String)objects[1]);
			list.add(map);
		}
		
		return list;
	}
	
	/**
	 * 
	 * <p>Description: 取得所有方法所对应的权限</p>
	 * @author joyu
	 * @return List<Map<String,String>>
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> getMethodResourceMapping(){
		String sql = "select t3.resource_path,t2.authority_mark from t_authorities_resources t1 "+
				"join t_authorities t2 on t1.authority_id = t2.authority_id "+
				"join t_resources t3 on t1.resource_id = t3.resource_id and t3.resource_type='method' order by t3.priority DESC";
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
/*		Query query = getSession().createNativeQuery(sql);
		Iterator<Object[]> it =  query.getResultList().iterator();*/
		List result = getResultesBySql(sql, "");
		Iterator<Object[]> it=result.iterator();
		while(it.hasNext()){
			Object[] objects = it.next();
			Map<String,String> map = new HashMap<String,String>();
			map.put("resourcePath", (String)objects[0]);
			map.put("authorityMark", (String)objects[1]);
			list.add(map);
		}
		
		return list;
	}
	
	public List<TAuthorities> getAuthoritiesByResourceID(Serializable id){
		String sql = "select distinct t2.authority_id as authorityId,t2.authority_mark as authorityMark"
				+ ",t2.authority_name as authorityName,t2.authority_desc as authorityDesc"
				+ ",t2.enable as enable,t2.issys as isSys,t2.module_id as moduleId from t_authorities_resources t1 "
				+ "join t_authorities t2 on t1.authority_id = t2.authority_id join t_resources t3 on t3.resource_id = t1.resource_id "
				+ "where t3.resource_id=?";
		
		List<TAuthorities> authorities = new ArrayList<TAuthorities>();
		List result = getResultesBySql(sql, id);
		Iterator<Object[]> it=result.iterator();
		while(it.hasNext()){
			Object[] objects = it.next();
			TAuthorities authority = new TAuthorities();
			authority.setAuthorityId((Integer)objects[0]);
			authority.setAuthorityMark( (String)objects[1]);
			authority.setAuthorityName((String)objects[2]);
			authority.setAuthorityDesc((String)objects[3]);
			authority.setEnable((Integer)objects[4]>0? true:false);
			authority.setIsSys((Integer)objects[5]>0? true:false);
			authority.setModuleId((Integer)objects[6]);

			authorities.add(authority);
		}
		return authorities;
	}
	
}
