package cn.com.aiidc.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.SiteMgrDao;
import cn.com.aiidc.cms.entity.TSite;

@Service
public class SiteMgrService {

	@Resource
	private SiteMgrDao siteMgrDao;
	
	public SiteMgrDao getSiteMgrDao() {
		return siteMgrDao;
	}

	public void setSiteMgrDao(SiteMgrDao siteMgrDao) {
		this.siteMgrDao = siteMgrDao;
	}

	/**
	 * 查询站点名称是否已存在
	 * @param siteName
	 * @param siteId
	 * @return
	 */
	public List<TSite> findSiteByName(String siteName,Integer siteId){
		return siteMgrDao.findSiteByName(siteName, siteId);
	}
	
	/**
	 * 查询url是否存在
	 * @param siteUrl
	 * @param siteId
	 * @return
	 */
	public List<TSite> findSiteByUrl(String siteUrl,Integer siteId){
		return siteMgrDao.findSiteByUrl(siteUrl, siteId);
	}

	/**
	 * 保存站点
	 * @param site
	 * @return
	 */
	public Integer addSite(TSite site){
		Integer siteId = (Integer) siteMgrDao.save(site);
		return siteId;
	}
	
	/**
	 * 根据SiteId查询站点
	 * @param siteId
	 * @return
	 */
	public TSite findSiteBySiteId(Integer siteId){
		return siteMgrDao.findById(siteId, TSite.class);
	}
	
	/**
	 * 编辑站点
	 * @param site
	 */
	public void editSite(TSite site){
		siteMgrDao.saveOrUpdate(site);
	}
	
	/**
	 * 伪删除
	 * @param site
	 */
	public void deleteByUpdateIsDisplay(TSite site){
		site.setIsDisplay(0);//1表示显示  0 表示伪删除
		siteMgrDao.saveOrUpdate(site);
	}
	
	/**
	 * 删除站点
	 * @param siteId
	 * @return
	 */
	public void deleteSite(TSite site){
		siteMgrDao.delete(site);
	}

	/**
	 * 分页查询：获取满足条件的记录数
	 * @param map
	 * @return
	 */
	public List<TSite> findAllSiteByPage(Map<String,Object> map){
		return siteMgrDao.findAllSiteByPage(map);
	}

	/**
	 * 获取满足条件的站点的总记录条数
	 * @param map
	 * @return
	 */
	public int queryCount(Map<String,Object> map){
		return siteMgrDao.queryCount(map);
	}
	
	/**
	 * 查询所有审核通过后的站点
	 * @return
	 */
	public List<TSite> findAllSiteBySiteAppr(){
		return siteMgrDao.findAllSiteBySiteAppr();
	}
}
