package cn.com.aiidc.cms.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.TemplateMgrDao;
import cn.com.aiidc.cms.entity.TTemplate;
import cn.com.aiidc.cms.entity.TTemplateClass;

@Service
public class TemplateMgrService {

	@Resource
	private TemplateMgrDao templateMgrDao;

	public TemplateMgrDao getTemplateMgrDao() {
		return templateMgrDao;
	}

	public void setTemplateMgrDao(TemplateMgrDao templateMgrDao) {
		this.templateMgrDao = templateMgrDao;
	}
	
	
	/**
	 * 获取满足条件要求的分页数据
	 * @param map
	 * @return
	 */
	public List<TTemplate> gettemplatesBypage(Map<String,Object> map){
		return templateMgrDao.gettemplatesBypage(map);
	}
	
	/**
	 * 根据id查询clazz
	 * @param id
	 * @param clazz
	 * @return
	 */
	public <T> T findById(Integer id, Class<T> clazz){
		return templateMgrDao.findById(id, clazz);
	}
	
	/**
	 * 获取满足要求的总记录数
	 * @param map
	 * @return
	 */
	public Integer getCountForTemplate(Map<String,Object> map){
		return templateMgrDao.getTemplateCountForPage(map);
	}
	
	/**
	 * 查找所有的模板类型
	 * @return
	 */
	public List<TTemplateClass> findTemplateClass(){
		return templateMgrDao.findAll(TTemplateClass.class);
	}
	
	/**
	 * 校验模板是否已存在
	 * @param temolateName
	 * @return
	 */
	public List<TTemplate> checkTemplate(String templateName,Integer templateId){
		return templateMgrDao.checkTemplate(templateName, templateId);
	}
	
	/**
	 * 保存模板
	 * @param template
	 * @return
	 */
	public Integer saveTemplate(TTemplate template){
		Integer templateId =(Integer) templateMgrDao.save(template);
		return templateId;
	}
	
	/**
	 * 删除模板
	 * @param template
	 */
	public void deleteModById(TTemplate template){
		templateMgrDao.delete(template);
	}

	/**
	 * 根据模板类型查找模板结果集
	 * @param templateClass
	 * @return
	 */
	public List<TTemplate> findModByModClass(Integer templateClass){
		return templateMgrDao.findModByModClass(templateClass);
	}
}










