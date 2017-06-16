package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TChannel;

@Repository
public class ChannelMgrDao extends HibernateDao{

	/**
	 * 查询某个站点下的频道
	 * @param siteId
	 * @param channelName
	 * @param channelId
	 * @return
	 */
	public List<TChannel> findBySiteIdAndChannelName(Integer siteId,String channelName,Integer channelId){
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		dc.add(Restrictions.eq("siteId", siteId));
		dc.add(Restrictions.eq("channelName", channelName));
		if(channelId != -1){
			dc.add(Restrictions.ne("channelId", channelId));
		}
		return findByCriteria(dc, TChannel.class);
	}
	
	
	/**
	 * 分页查询：满足条件
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<TChannel> findAllChannelBypage(Map<String,Object> map)
	{	
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		String channelName = (String) map.get("channelName");//频道名称
		if(channelName != null){
			dc.add(Restrictions.like("channelName",channelName,MatchMode.ANYWHERE));
		}
		Integer siteId = (Integer) map.get("siteId");//站点
		if(siteId != null ){
			dc.add(Restrictions.eq("siteId",siteId));
		}
		Integer channelAppr = (Integer) map.get("channelAppr");//审核状态
		dc.add(Restrictions.eq("channelAppr",channelAppr));
		dc.add(Restrictions.eq("isDisplay",1)).addOrder(Order.desc("channelId"));
		//获取数据集合
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		List<TChannel> channels =findClassByPage(dc, page, pageSize, TChannel.class);
		return channels;
	}
	
	/**
	 * 获取满足条件的记录总数
	 * @param map
	 * @return
	 */
	public int gettotalForPage(Map<String,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		String channelName = (String) map.get("channelName");//频道名称
		if(channelName != null){
			dc.add(Restrictions.like("channelName",channelName,MatchMode.ANYWHERE));
		}
		Integer siteId = (Integer) map.get("siteId");//站点
		if(siteId != null ){
			dc.add(Restrictions.eq("siteId",siteId));
		}
		Integer channelAppr = (Integer) map.get("channelAppr");//审核状态
		dc.add(Restrictions.eq("channelAppr",channelAppr));
		dc.add(Restrictions.eq("isDisplay",1)).addOrder(Order.desc("channelId"));
		return queryCountByCriteria(dc);
	}
	
	/**
	 * 获取通过某站点下，通过审核的频道
	 * 注：当频道id为null时，返回所有通过审核的频道
	 * @param siteId
	 * @return
	 */
	public List<TChannel> getChannelByApprPass(Integer siteId){
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		if(siteId != null ){
			dc.add(Restrictions.eq("siteId",siteId));
		}
		dc.add(Restrictions.eq("channelAppr",1));
		dc.add(Restrictions.eq("isDisplay",1));
		return findByCriteria(dc, TChannel.class);
	}
}
