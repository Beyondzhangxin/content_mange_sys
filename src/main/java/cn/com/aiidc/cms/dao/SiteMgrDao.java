package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TSite;

@Repository
public class SiteMgrDao extends HibernateDao{
	
	/**
	 * 根据站点名称和站点id查询站点信息
	 * @param siteName
	 * @param siteId
	 * @return
	 */
	public List<TSite> findSiteByName(String siteName,Integer siteId){
		DetachedCriteria dc = DetachedCriteria.forClass(TSite.class);
		dc.add(Restrictions.eq("siteName", siteName));
		if(siteId != -1 ){
			dc.add(Restrictions.ne("siteId", siteId));
		}
		return findByCriteria(dc, TSite.class);
	}
	
	/**
	 * 查询url是否存在
	 * @param siteUrl
	 * @param siteId
	 * @return
	 */
	public List<TSite> findSiteByUrl(String siteUrl,Integer siteId){
		DetachedCriteria dc = DetachedCriteria.forClass(TSite.class);
		dc.add(Restrictions.eq("siteUrl", siteUrl));
		if(siteId != -1 ){
			dc.add(Restrictions.ne("siteId", siteId));
		}
		return findByCriteria(dc, TSite.class);
	}
	
	
	/**
	 * 分页查询：获取满足条件的记录数
	 * 条件有： 
	 * 			page  当前页
	 * 			pageSize  每页显示的记录数
	 * 			siteAppr  审核状态
	 * 			siteName  站点名称  模糊查询
	 * 			isDisplay  是否存在
	 * @param map
	 * @return
	 */
	public List<TSite> findAllSiteByPage(Map<String,Object> map){
		//创建搜索条件
		DetachedCriteria dc = DetachedCriteria.forClass(TSite.class);
		String siteName= (String) map.get("siteName");
		//模糊过滤站点名称
		if(siteName != null){
			dc.add(Restrictions.like("siteName",siteName,MatchMode.ANYWHERE));
		}
		//过滤审核状态
		Integer siteAppr = (Integer) map.get("siteAppr");
		dc.add(Restrictions.eq("siteAppr",siteAppr ));
		//检查站点是否存在
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("siteId"));
		//获取满足条件的站点集合
		return findClassByPage(dc,(Integer) map.get("page"),(Integer) map.get("pageSize"),TSite.class);
	}
	
	/**
	 * 获取满足条件的站点的总记录条数：
	 * 条件有： 
	 * 			page  当前页
	 * 			pageSize  每页显示的记录数
	 * 			siteAppr  审核状态
	 * 			siteName  站点名称  模糊查询
	 * 			isDisplay  是否存在
	 * @param map
	 * @return
	 */
	public int queryCount(Map<String,Object> map){
		//创建搜索条件
		DetachedCriteria dc = DetachedCriteria.forClass(TSite.class);
		String siteName= (String) map.get("siteName");
		//模糊过滤站点名称
		if(siteName != null){
			dc.add(Restrictions.like("siteName",siteName,MatchMode.ANYWHERE));
		}
		//过滤审核状态
		Integer siteAppr = (Integer) map.get("siteAppr");
		dc.add(Restrictions.eq("siteAppr",siteAppr ));
		//检查站点是否存在
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("siteId"));
		//获取满足条件的记录数
		int count = queryCountByCriteria(dc);
		return count;
	}
	
	/**
	 * 查询所有审核通过后的站点
	 * @return
	 */
	public List<TSite> findAllSiteBySiteAppr(){
		DetachedCriteria dc = DetachedCriteria.forClass(TSite.class);
		dc.add(Restrictions.eq("isDisplay", 1));
		dc.add(Restrictions.eq("siteAppr",1));//0 未审核  1 审核通过  2 审核未通过
		return findByCriteria(dc, TSite.class);
	}
	
}
