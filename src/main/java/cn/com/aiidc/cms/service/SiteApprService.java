package cn.com.aiidc.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.aiidc.cms.dao.SiteApprDao;
import cn.com.aiidc.cms.entity.TSite;


@Service
public class SiteApprService {

	@Resource
	private SiteApprDao siteApprDao;

	public SiteApprDao getSiteApprDao() {
		return siteApprDao;
	}

	public void setSiteApprDao(SiteApprDao siteApprDao) {
		this.siteApprDao = siteApprDao;
	}
	
	/**
	 * 满足条件需要审核的记录集合
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
		dc.add(Restrictions.eq("siteAppr",0));
		//检查站点是否存在
		dc.add(Restrictions.eq("isDisplay", 1)).addOrder(Order.desc("siteId"));
		//获取满足条件的站点集合
		List<TSite> list = siteApprDao.findClassByPage(dc,(Integer) map.get("page"),(Integer) map.get("pageSize"),TSite.class);
		return list;
	}
	

	/**
	 * 满足条件的需要审核的记录总数
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
		dc.add(Restrictions.eq("siteAppr",0));
		//检查站点是否存在
		dc.add(Restrictions.eq("isDisplay", 1));
		//获取满足条件的记录数
		int count = siteApprDao.queryCountByCriteria(dc);
		return count;
	}
	
	/**
	 * 根据站点id查询站点信息
	 * @param siteId
	 * @return
	 */
	public TSite findSiteBySiteId(Integer siteId){
		return siteApprDao.findById(siteId, TSite.class);
	}
	
	/**
	 * 保存站点审核结果
	 * @param site
	 */
	@Transactional(readOnly=false)
	public void saveSiteApprResult(TSite site){
		siteApprDao.saveOrUpdate(site);
	}
}
