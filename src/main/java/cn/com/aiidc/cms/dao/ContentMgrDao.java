package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TTemplate;

@Repository
public class ContentMgrDao extends HibernateDao{
	
	/**
	 * 根据模板的类型查询
	 * @param templateClass
	 * @return
	 */
	public List<TTemplate> findModByTemplateClass(Integer templateClass){
		DetachedCriteria dc = DetachedCriteria.forClass(TTemplate.class);
		dc.add(Restrictions.eq("templateClass", templateClass));
		return findByCriteria(dc, TTemplate.class);
	}
	

	/**
	 * 分页查询：获取满足条件的记录
	 * @param map
	 * @return
	 */
	public List<TContent> getContentsByPage(Map<String, Object> map) 
	{
		DetachedCriteria dc = DetachedCriteria.forClass(TContent.class);
		Integer isReview = (Integer) map.get("isReview");
		if(isReview != null){
			dc.add(Restrictions.eq("isReview",isReview ));
		}
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null){
			dc.add(Restrictions.like("contentTitle", contentTitle,MatchMode.ANYWHERE));
		}
		String contentCreateUserName = (String) map.get("contentCreateUserName");
		if(contentCreateUserName !=null ){
			dc.add(Restrictions.like("contentCreateUserName", contentCreateUserName,MatchMode.ANYWHERE));
		}
		Integer isDisplay = (Integer) map.get("isDisplay");
		dc.add(Restrictions.eq("isDisplay", isDisplay));
		dc.addOrder(Order.desc("contentId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TContent.class);
	}
	
	/**
	 * 获取满足条件的记录总数
	 * @param map
	 * @return
	 */
	public Integer getTotalForContent(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TContent.class);
		Integer isReview = (Integer) map.get("isReview");
		if(isReview != null){
			dc.add(Restrictions.eq("isReview",isReview ));
		}
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null){
			dc.add(Restrictions.like("contentTitle", contentTitle,MatchMode.ANYWHERE));
		}
		String contentCreateUserName = (String) map.get("contentCreateUserName");
		if(contentCreateUserName !=null ){
			dc.add(Restrictions.like("contentCreateUserName", contentCreateUserName,MatchMode.ANYWHERE));
		}
		Integer isDisplay = (Integer) map.get("isDisplay");
		dc.add(Restrictions.eq("isDisplay", isDisplay));
		return queryCountByCriteria(dc);
	}
	
	/**
	 * 根据内容标题和内容id查询
	 * @param contentTitle
	 * @param contentId
	 * @return
	 */
	public List<TContent> findContentByContentTitle(String contentTitle,Integer contentId){
		DetachedCriteria dc = DetachedCriteria.forClass(TContent.class);
		dc.add(Restrictions.eq("contentTitle",contentTitle ));
		if(contentId !=null){
			dc.add(Restrictions.ne("contentId",contentId ));
		}
		return findByCriteria(dc);
	}
	
}
