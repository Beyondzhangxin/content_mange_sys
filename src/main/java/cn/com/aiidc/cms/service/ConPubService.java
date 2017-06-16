package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ConPubDao;
import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TContentPublish;

@Service
public class ConPubService {

	
	@Resource
	private ConPubDao conPubDao;
	
	public ConPubDao getConPubDao() {
		return conPubDao;
	}


	public void setConPubDao(ConPubDao conPubDao) {
		this.conPubDao = conPubDao;
	}


	/**
	 * 获取满足条件的记录：分页
	 * @param map
	 * @return
	 */
	public List<TContent> findContentForPublic(Map<String, Object> map) 
	{
		return conPubDao.getContentsByPage(map);
	}
	
	/**
	 * 获取需要发布的内容的总和
	 * @param map
	 * @return
	 */
	public Integer getTotalForConPub(Map<String, Object> map)
	{
		return conPubDao.getTotalForContent(map);
	} 
	
	
	/**
	 * 根据id查询实体类
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Serializable id,Class<T> clazz){	
		return conPubDao.findById(id, clazz);
	}
	
	
	/**
	 * 保存
	 * @param contentPub
	 * @return
	 */
	public Integer saveContentPub(TContentPublish contentPub){
		Integer contentPubId = (Integer) conPubDao.save(contentPub);
		return contentPubId;
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
		return conPubDao.getconpub(contentId, siteId, channelId, columnId);
	}
	
	/**
	 * 获取定时需要定时发布的内容
	 * @return
	 */
	public List<TContentPublish> findPlanPubContent(){
		return conPubDao.findPlanPubContent();
	}
	
	/**
	 * 更新发布内容
	 * @param contentPub
	 */
	public void update(TContentPublish contentPub){
		conPubDao.saveOrUpdate(contentPub);
	}
}
