package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TContent;

@Repository
public class ConApprDao extends HibernateDao{
	
	/**
	 * 分页查询：获取满足条件的记录
	 * @param map
	 * @return
	 */
	public List<TContent> getContentsByPage(Map<String, Object> map) 
	{
		DetachedCriteria dc = DetachedCriteria.forClass(TContent.class);
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null){
			dc.add(Restrictions.like("contentTitle", contentTitle,MatchMode.ANYWHERE));
		}
		String contentCreateUserName = (String) map.get("contentCreateUserName");
		if(contentCreateUserName !=null ){
			dc.add(Restrictions.like("contentCreateUserName", contentCreateUserName,MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("isReview",2));
		dc.add(Restrictions.eq("isDisplay", 1));
		dc.addOrder(Order.desc("contentId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TContent.class);
	}
	
	/**
	 * 获取满足条件的总条数
	 * @param map
	 * @return
	 */
	public Integer getTotalForContent(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TContent.class);
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null){
			dc.add(Restrictions.like("contentTitle", contentTitle,MatchMode.ANYWHERE));
		}
		String contentCreateUserName = (String) map.get("contentCreateUserName");
		if(contentCreateUserName !=null ){
			dc.add(Restrictions.like("contentCreateUserName", contentCreateUserName,MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("isReview",2));
		dc.add(Restrictions.eq("isDisplay", 1));
		return queryCountByCriteria(dc);
	}

}
