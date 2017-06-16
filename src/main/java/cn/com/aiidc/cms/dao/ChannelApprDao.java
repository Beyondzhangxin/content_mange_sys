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
public class ChannelApprDao extends HibernateDao{
	/**
	 * 	获取满足条件的数据
	 * @param map
	 * @return
	 */
	public List<TChannel> getdataForChannelAppr(Map<String,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		String channelName = (String) map.get("channelName");
		if(channelName != null){
			dc.add(Restrictions.like("channelName", channelName,MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("channelAppr", 0));//未审核的数据
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("channelId"));
		Integer page = (Integer) map.get("page");
		Integer pageSize = (Integer) map.get("pageSize");
		return findClassByPage(dc, page, pageSize, TChannel.class);
	}
	
	/**
	 * 获取满足条件的总的记录数
	 * @param map
	 * @return
	 */
	public Integer getTotalForChannelAppr(Map<String,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TChannel.class);
		Integer siteId = (Integer) map.get("siteId");
		if(siteId != null){
			dc.add(Restrictions.eq("siteId", siteId));
		}
		String channelName = (String) map.get("channelName");
		if(channelName != null){
			dc.add(Restrictions.like("channelName", channelName,MatchMode.ANYWHERE));
		}
		dc.add(Restrictions.eq("channelAppr", 0));//未审核的数据
		dc.add(Restrictions.eq("isDisplay", 1));
		return queryCountByCriteria(dc);
	}
}













