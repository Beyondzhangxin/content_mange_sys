package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TContentPublish;

@Repository
public class ConPubMgrDao extends HibernateDao{

	/**
	 * 分页查询：获取满足条件的集合
	 * @param map
	 * @return
	 */
	public List<TContentPublish> findContPubByPage(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TContentPublish.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId != null ){
			dc.add(Restrictions.eq("channelId", channelId));
		}
		Integer columnId = (Integer) map.get("columnId");
		if(columnId != null){
			dc.add(Restrictions.eq("columnId", columnId));
		}
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null ){
			dc.add(Restrictions.ilike("contentTitle", contentTitle, MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.isNull("contentPubPlanTime"));
		dc.addOrder(Order.desc("contentPubId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TContentPublish.class);
	}
	
	/**
	 * 获取满足条件的记录总数
	 * @param map
	 * @return
	 */
	public Integer getTotalByMap(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TContentPublish.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId != null ){
			dc.add(Restrictions.eq("channelId", channelId));
		}
		Integer columnId = (Integer) map.get("columnId");
		if(columnId != null){
			dc.add(Restrictions.eq("columnId", columnId));
		}
		String contentTitle = (String) map.get("contentTitle");
		if(contentTitle != null ){
			dc.add(Restrictions.ilike("contentTitle", contentTitle, MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.isNull("contentPubPlanTime"));
		return queryCountByCriteria(dc);
	}
	
}
