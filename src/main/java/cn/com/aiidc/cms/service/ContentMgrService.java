package cn.com.aiidc.cms.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.ContentMgrDao;
import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TTemplate;

@Service
public class ContentMgrService {

	@Resource
	private ContentMgrDao contentMgrDao;

	public ContentMgrDao getContentMgrDao() {
		return contentMgrDao;
	}

	public void setContentMgrDao(ContentMgrDao contentMgrDao) {
		this.contentMgrDao = contentMgrDao;
	}
	
	/**
	 * 获取内容模板
	 * @param templateClass
	 * @return
	 */
	public List<TTemplate> findConMod(Integer templateClass){
		return contentMgrDao.findModByTemplateClass(templateClass);
	}
	
	/**
	 * 根据id查询clazz
	 * @param id
	 * @param clazz
	 * @return
	 */
    public <T> T findById(Serializable id, Class<T> clazz)
    {
        return contentMgrDao.findById(id, clazz);
    }
    
	/**
	 * 添加内容
	 * @param content
	 * @return
	 */
	public Integer addContent(TContent content){
		Integer contentId = -1;
		contentId = (Integer) contentMgrDao.save(content);
		return contentId;
	}
	

	/**
	 *	分页查询：获取满足添加的记录数
	 * @param map
	 * @return
	 */
	public List<TContent>  getContentsByPage(Map<String, Object> map) 
	{
		return  contentMgrDao.getContentsByPage(map);
	}
	
	/**
	 * 获取满足条件的总的记录数
	 * @param map
	 * @return
	 */
	public Integer getTotalForContent(Map<String, Object> map){
		return contentMgrDao.getTotalForContent(map);
	}
	
	/**
	 * 批量更改
	 * @param content
	 */
	public void updateAll(List<Integer> contentIds) {
		for(Integer contentId : contentIds){
			TContent content = contentMgrDao.findById(contentId, TContent.class);
			content.setIsDisplay(2);//2  回收站
			contentMgrDao.saveOrUpdate(content);
		}
	}


	/**
	 * 批量更新：从回收站中原路返回
	 * @param contentIds
	 * @throws Exception
	 */
	public void returnForContList(List<Integer> contentIds){
		for (Integer contentId : contentIds) {
			TContent content = contentMgrDao.findById(contentId, TContent.class);
			content.setIsDisplay(1);
			contentMgrDao.saveOrUpdate(content);
		}
	}
	
	/**
	 * 删除文章记录
	 * @param content
	 */
	public void deteleContent(TContent content){
		contentMgrDao.delete(content);
	}
	
	/**
	 * 更新文章内容
	 * @param content
	 */
	public void updateContent(TContent content){
		contentMgrDao.saveOrUpdate(content);
	}

	/**
	 * 查询内容标题是否存在
	 * @param contentTitle
	 * @param contentId
	 * @return
	 */
	public Integer findContentByContentTitle(String contentTitle,Integer contentId){
		return contentMgrDao.findContentByContentTitle(contentTitle, contentId).size();
	}
}
