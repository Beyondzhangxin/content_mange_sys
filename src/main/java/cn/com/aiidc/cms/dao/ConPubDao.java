package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TContentPublish;

@Repository
public class ConPubDao extends HibernateDao{
	
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
		Integer[] arrInt = new Integer[] {3,1};
		dc.add(Restrictions.in("isReview", arrInt));
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
		Integer[] arrInt = new Integer[] {3,4};
		dc.add(Restrictions.in("isReview", arrInt));
		dc.add(Restrictions.eq("isDisplay", 1));
		return queryCountByCriteria(dc);
	}

	/**
	 * 根据 内容id、站点id、频道id、栏目id查询已发布的内容
	 * @param contentId
	 * @param siteId
	 * @param channelId
	 * @param columnId
	 * @return
	 */
	public List<TContentPublish> getconpub(Integer contentId,Integer siteId,Integer channelId,Integer columnId){
		DetachedCriteria dc = DetachedCriteria.forClass(TContentPublish.class);
		dc.add(Restrictions.eq("contentId", contentId));
		dc.add(Restrictions.eq("siteId", siteId));
		dc.add(Restrictions.eq("channelId", channelId));
		dc.add(Restrictions.eq("columnId", columnId));
		return findByCriteria(dc, TContentPublish.class);
	}
	
	
	/**
	 * 获取需要定时发布的内容
	 * @return
	 */
	public List<TContentPublish> findPlanPubContent(){
		DetachedCriteria dc = DetachedCriteria.forClass(TContentPublish.class);
		dc.add(Restrictions.isNotNull("contentPubPlanTime"));
		return findByCriteria(dc);
	}
}




