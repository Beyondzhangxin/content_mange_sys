package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TColumn;

@Repository
public class ColumnApprDao extends HibernateDao {
	
	/**
	 * 获取需要审核的栏目：分页
	 * @param map
	 * @return
	 */
	public List<TColumn>  getPageDataForColumnAppr(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId != null ){
			dc.add(Restrictions.eq("channelId", channelId));
		}
		String columnName = (String) map.get("columnName");
		if(columnName != null){
			dc.add(Restrictions.ilike("columnName", columnName, MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("columnAppr", 0));//未审核
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("columnId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TColumn.class);
	}
	
	/**
	 * 获取满足未审核的总记录数
	 * @param map
	 * @return
	 */
	public Integer  getTotalForColumnAppr(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId != null ){
			dc.add(Restrictions.eq("channelId", channelId));
		}
		String columnName = (String) map.get("columnName");
		if(columnName != null){
			dc.add(Restrictions.ilike("columnName", columnName, MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("columnAppr", 0));//未审核
		dc.add(Restrictions.eq("isDisplay", 1));
		return queryCountByCriteria(dc);
	}
}
