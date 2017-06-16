package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ConPubMgrDao;
import cn.com.aiidc.cms.entity.TContentPublish;



@Service
public class ConPubMgrService {
	
	@Resource
	private ConPubMgrDao conPubMgrDao;

	public ConPubMgrDao getConPubMgrDao() {
		return conPubMgrDao;
	}

	public void setConPubMgrDao(ConPubMgrDao conPubMgrDao) {
		this.conPubMgrDao = conPubMgrDao;
	}
	
	/**
	 * 获取满足条件的记录数：分页
	 * @param map
	 * @return
	 */
	public List<TContentPublish> getContPubForPage(Map<String, Object> map){
		return conPubMgrDao.findContPubByPage(map);
	}

	/**
	 * 返回满足条件的总记录数
	 * @param map
	 * @return
	 */
	public Integer getTotalByMap(Map<String, Object> map){
		return conPubMgrDao.getTotalByMap(map);
	}
	
	/**
	 * 根据id查询实体类
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Serializable id,Class<T> clazz){	
		return conPubMgrDao.findById(id, clazz);
	}
	
	/**
	 * 删除
	 * @param conpub
	 */
	public void deteleContentPublish(TContentPublish conpub){
		conPubMgrDao.delete(conpub);
	}
	
	
}
