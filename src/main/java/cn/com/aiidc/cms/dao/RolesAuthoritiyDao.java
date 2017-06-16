package cn.com.aiidc.cms.dao;

/**
@author joyu
@date 2017/03/27
@Description: 完成AuthoritiesResources 的增删改查
**/

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.aiidc.cms.entity.TResources;
import cn.com.aiidc.cms.entity.TRolesAuthorities;

@Repository
public class RolesAuthoritiyDao extends HibernateDao{
	
	/**
 	 * <p>Description: 返回全部的RoleAuthorities</p>
	 * @author joyu
	 * @return list
	 */
	
	public List<TRolesAuthorities> getRolesAuthoritiesList(){
		return findAll(TRolesAuthorities.class);
	}
		
	/**
	 * 
	 * <p>Description: 返回分页数据</p>
	 * @author joyu
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<TRolesAuthorities> getPageRolesAuthorities(int page,int pageSize){
		DetachedCriteria dc = DetachedCriteria.forClass(TRolesAuthorities.class);
		List<TRolesAuthorities> list = findClassByPage(dc,page,pageSize,TRolesAuthorities.class);
		return list;
	}

	/**
	 * 
	 * <p>Description: 保存TRolesAuthorities</p>
	 * @author joyu
	 * @param authoritiesResources
	 */
    @Transactional(readOnly = false)
    public void saveRolesAuthorities(TRolesAuthorities authoritiesResources){
    	save(authoritiesResources);
    }
	
    /**
     * 
     * <p>Description:删除TRolesAuthorities </p>
     * @author joyu
     * @param authoritiesResources
     */
    @Transactional(readOnly = false)
	public void deleteRolesAuthorities(TRolesAuthorities authoritiesResources){
		delete(authoritiesResources);
	}
	
	/**
	 * 
	 * <p>Description: 通过id从数据库中取得TRolesAuthorities</p>
	 * @author joyu
	 * @param id
	 * @return
	 */
	public TRolesAuthorities getRolesAuthoritiesById(int id){
		return findByCondition("id", id+"", TRolesAuthorities.class).get(0);
	}

}
