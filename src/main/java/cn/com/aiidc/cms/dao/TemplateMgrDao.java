package cn.com.aiidc.cms.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.TTemplate;

@Repository
public class TemplateMgrDao extends HibernateDao{
	
	/**
	 *	模板分页查询
	 * @param map
	 * @return
	 */
	public List<TTemplate> gettemplatesBypage(Map<String,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TTemplate.class);
		String templateName = (String) map.get("templateName");
		if(templateName != null){
			dc.add(Restrictions.like("templateName",templateName,MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.desc("templateId"));
		Integer page =(Integer) map.get("page");
		Integer pageSize =(Integer) map.get("pageSize");
		return findClassByPage(dc,page,pageSize,TTemplate.class);
	}
	
	/**
	 * 获取满足条件的记录总数
	 * @param map
	 * @return
	 */
	public Integer getTemplateCountForPage(Map<String,Object> map){
		DetachedCriteria dc = DetachedCriteria.forClass(TTemplate.class);
		String templateName = (String) map.get("templateName");
		if(templateName != null){
			dc.add(Restrictions.like("templateName",templateName,MatchMode.ANYWHERE));
		}
		return queryCountByCriteria(dc);
	}
	
	/**
	 * 校验模板是否已存在
	 * @param temolateName
	 * @return
	 */
	public List<TTemplate> checkTemplate(String templateName,Integer templateId){
		DetachedCriteria dc = DetachedCriteria.forClass(TTemplate.class);
		if(templateId != -1){
			dc.add(Restrictions.ne("templateId", templateId));
		}
		dc.add(Restrictions.eq("templateName", templateName));
		return findByCriteria(dc, TTemplate.class);
	}
	
	/**
	 * 根据模板类型查找模板
	 * @param templateClass
	 * @return
	 */
	public List<TTemplate> findModByModClass(Integer templateClass){
		DetachedCriteria dc = DetachedCriteria.forClass(TTemplate.class);
		dc.add(Restrictions.eq("templateClass", templateClass));
		return findByCriteria(dc, TTemplate.class);
	}
}
