package cn.com.aiidc.cms.dao;

/**
 @author joyu
 @date 2017/03/27
 Description: 完成resource 的增删改查
 **/
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.aiidc.cms.entity.TAuthoritiesResources;
import cn.com.aiidc.cms.entity.TResources;;

@Repository
public class ResourcesDao extends HibernateDao {
	/**
	 * 
	*  <p>Description: 取得所有权限列表</p>
	 * @author joyu
	 * @return List<TResources>
	 */
	
	public List<TResources> getResourcesList(){
		return findAll(TResources.class);
	}
	
	/**
	 * 
	*  <p>Description: 通过权限名称检索权限数据</p>
	 * @author joyu
	 * @param authorityName ：权限名称
	 * @return list
	 */
	
	public List<TResources> getResourcesbySearch(String resourceName){
		DetachedCriteria dc = DetachedCriteria.forClass(TResources.class);
		//通过名字模糊检索TUser
		if(resourceName != null){
			dc.add(Restrictions.like("authorityName",resourceName,MatchMode.ANYWHERE));
		}
		return findByCriteria(dc);
	}
	
	
	/**
	 * 
	*  <p>Description: 返回分页数据</p>
	 * @author joyu
	 * @param page：  页码
	 * @param pageSize：  每页条数
	 * @return
	 */
	
	public List<TResources> getPageResources(int page,int pageSize){
		DetachedCriteria dc = DetachedCriteria.forClass(TResources.class);
		List<TResources> list = findClassByPage(dc,page,pageSize,TResources.class);
		return list;
	}

	/**
	 * 
	 * <p>Description: 保存一个TAuthorities</p>
	 * @author joyu
	 * @param authority
	 */
    @Transactional(readOnly = false)
    public void saveResuorce(TResources authority){
    	save(authority);
    }
	
    /**
     * 
     * <p>Description: 删除一个TAuthorities</p>
     * @author joyu
     * @param authority
     */
    @Transactional(readOnly = false)
	public void deleteResource(TResources authority){
		delete(authority);
	}
	
	/**
	 * 
	 * <p>Description: 通过id从数据库中取得resource 对象</p>
	 * @author joyu
	 * @param authorityId
	 * @return
	 */
	public TResources getResourceById(int resourceId){
		return findByCondition("resourceId", resourceId+"", TResources.class).get(0);
	}
	
	/**
	 * 
	 * <p>Description:通过名称从数据库中取得Resource对象 </p>
	 * @author joyu
	 * @param authorityName
	 * @return
	 */
	public TResources getResourceByName(String authorityName){
		List<TResources> list = findByCondition("authorityName", authorityName, TResources.class);
		return list.get(0);
	}
	}
