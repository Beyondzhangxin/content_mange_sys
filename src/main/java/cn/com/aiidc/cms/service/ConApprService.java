package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.aiidc.cms.dao.ConApprDao;
import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TRiview;

@Service
public class ConApprService {

	@Resource
	private ConApprDao  conApprDao;

	public ConApprDao getConApprDao() {
		return conApprDao;
	}

	public void setConApprDao(ConApprDao conApprDao) {
		this.conApprDao = conApprDao;
	}
	
	/**
	 * 内容审核分页管理：
	 * 只有未审核的内容才被审核
	 * @param map
	 * @return
	 */
	public List<TContent> findContentForAppr(Map<String, Object> map) 
	{
		return conApprDao.getContentsByPage(map);
	}
	
	/**
	 * 获取需要发布的内容的总和
	 * @param map
	 * @return
	 */
	public Integer getTotalForConAppr(Map<String, Object> map){
		return conApprDao.getTotalForContent(map);
	} 
	
	
	/**
	 * 根据id查询实体类
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Serializable id,Class<T> clazz){	
		return conApprDao.findById(id, clazz);
	}
	
	/**
	 * 保存审核内容
	 * @param riview
	 * @return
	 */
	@Transactional
	public Integer saveRiview(TRiview riview,TContent content){
		conApprDao.saveOrUpdate(content);
		Integer id = (Integer) conApprDao.save(riview);
		return id ;
	}
}
