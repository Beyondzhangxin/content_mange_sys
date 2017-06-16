package cn.com.aiidc.cms.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TColumn;

@Repository
public class ColumnMgrDao extends HibernateDao{
	
	/**
	 * 获取频道下，审核通过的栏目
	 * 注意：如果频道id为null,则获取全部通过审核的栏目数据
	 * @param channelId
	 * @return
	 */
	public List<TColumn> getColumnByApprPass(Integer channelId){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		if(channelId != null){
			dc.add(Restrictions.eq("channelId", channelId));
		}
		dc.add(Restrictions.eq("columnAppr", 1));
		dc.add(Restrictions.eq("isDisplay", 1));
		return findByCriteria(dc, TColumn.class);
	}
	
	/**
	 *	根据频道id、栏目名称 和栏目id查询
	 * @param columnId
	 * @param channelId
	 * @param columnName
	 * @return
	 */
	public List<TColumn> findByColumnIdChannelIdColumnName(Integer columnId,Integer channelId,String columnName)
	{
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		if(columnId != null){
			dc.add(Restrictions.ne("columnId", columnId));
		}
		dc.add(Restrictions.eq("channelId", channelId));
		dc.add(Restrictions.eq("columnName", columnName));
		return findByCriteria(dc, TColumn.class);
	}
	
	/**
	 * 	获取该栏目下的子栏目
	 * @param pid
	 * @return
	 */
	public List<TColumn> findChildColumnByPid(Serializable pid){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		dc.add(Restrictions.eq("isDisplay", 1));
		dc.add(Restrictions.eq("parentId", pid));
		return findByCriteria(dc, TColumn.class);
	}
	
	/**
	 * 获取栏目的栏目树结构数据
	 * @param map
	 * @return
	 */
	public List<TColumn> getColumnTree(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId !=null){//站点
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId !=null ){//频道
			dc.add(Restrictions.eq("channelId", channelId));
		}
		Integer columnAppr = (Integer) map.get("columnAppr");//审核状态
		dc.add(Restrictions.eq("columnAppr", columnAppr));
		dc.add(Restrictions.eq("parentId", 0));
		dc.add(Restrictions.eq("isDisplay", 1));
		return findByCriteria(dc, TColumn.class);
	}
	
	
	/**
	 * 	获取满足条件的栏目数据：分页
	 * @param map
	 * @return
	 */
	public List<TColumn> getColumnsForColumnList(Map<String, Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TColumn.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId !=null){//站点
			dc.add(Restrictions.eq("siteId", siteId));
		}
		Integer channelId = (Integer) map.get("channelId");
		if(channelId !=null ){//频道
			dc.add(Restrictions.eq("channelId", channelId));
		}
		Integer columnAppr = (Integer) map.get("columnAppr");//审核状态
		dc.add(Restrictions.eq("columnAppr", columnAppr));
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("columnId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TColumn.class);
	}
	
	/**
	 * 获取需要审核的栏目：分页
	 * @param map
	 * @return
	 */
	public List<TColumn>  getPageDataForColumnMgr(Map<String, Object> map){
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
		Integer columnAppr = (Integer) map.get("columnAppr");
		dc.add(Restrictions.eq("columnAppr", columnAppr));
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
	public Integer  getTotalForColumnMgr(Map<String, Object> map){
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
		Integer columnAppr = (Integer) map.get("columnAppr");
		dc.add(Restrictions.eq("columnAppr", columnAppr));
		dc.add(Restrictions.eq("isDisplay", 1));
		return queryCountByCriteria(dc);
	}
}
