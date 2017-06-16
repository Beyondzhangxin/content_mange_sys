package cn.com.aiidc.cms.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import cn.com.aiidc.cms.entity.EasyuiDepartmentTreeNode;
import cn.com.aiidc.cms.entity.TDepartment;

@Repository
public class DepartmentDao extends HibernateDao{
	@Resource
	private SessionFactory sessionfactory;

	public SessionFactory getSessionfactory() {
		return sessionfactory;
	}

	public void setSessionfactory(SessionFactory sessionfactory) {
		this.sessionfactory = sessionfactory;
	}
	public Session getSession(){
		return this.sessionfactory.getCurrentSession();
	}
	/**
	 * query all the department,return the list
	 * @return
	 */
	public List<TDepartment> doQuery(){
		String hql="from TDepartment tDepartment";
		return getSession().createQuery(hql).list();
	}
	/**
	 * save the entity if not exists, or update the entity if it exists,
	 * return null
	 * @param entityObject
	 */
	public void saveOrUpdate(Object entityObject) {
		this.getSession().saveOrUpdate(entityObject);
	}
	/**
	 * delete the Department entity by id
	 * @param id
	 * @return
	 */
	public int DeleteById(int id){
		String hql="delete from TDepartment where depId ="+id;
		Session session=getSessionfactory().openSession();
		Transaction tx=session.beginTransaction();
		int result=session.createQuery(hql).executeUpdate();
		tx.commit();
		session.close();
		return result;
	}
	/**
	 * 返回所有的父级部门列表的ID和名称，以MAP的形式返回
	 * 
	 */
	public Map<Integer, String> getParentDepartmentList(){
		Map<Integer, String> parentDepartmentList = new HashMap<Integer, String>();
		List<TDepartment> list = doQuery();
		for (TDepartment temp : list) {
			parentDepartmentList.put(temp.getDepId(), temp.getDepName());
		}
		return parentDepartmentList;
	}
	/**
	 * 返回部门列表的根节点
	 * @return
	 */
	public List<TDepartment> getRootList(){
		 String hql="From TDepartment tDepartment where tDepartment.parentId is null";
		 Query query=getSession().createQuery(hql);
		 return query.list();
	}
	/**
	 * 根据部门ID获取下一级的部门列表
	 * @param id
	 * @return
	 */
	public List<TDepartment> getChildrenListById(Serializable id){
		 String hql="From TDepartment tDepartment where tDepartment.parentId="+id;
		 Query query=getSession().createQuery(hql);
		 return query.list();
	}
	/**
	 * 返回一个部门根节点下的所有子节点
	 * @param rootId
	 * @return
	 */
	public EasyuiDepartmentTreeNode getEasyuiDepartmentTreeNode(int rootId) {
		EasyuiDepartmentTreeNode node = new EasyuiDepartmentTreeNode();
		node.setId(rootId);
		node.setText(getById(rootId).getDepName());
		for (TDepartment temp : getChildrenListById(rootId)) {
			EasyuiDepartmentTreeNode nodeTemp = getEasyuiDepartmentTreeNode(temp.getDepId());
			node.getChildren().add(nodeTemp);
		}
		return node;

	}
	/**
	 * 根据部门ID获取对应的部门类
	 * @param id
	 * @return
	 */
	public TDepartment getById(Integer id) {
		return this.getSession().load(TDepartment.class, id);
	}
	
}
