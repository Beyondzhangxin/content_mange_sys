package cn.com.aiidc.cms.dao;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
public class HibernateDao
{


    @Autowired
    private HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
    {
        this.hibernateTemplate = hibernateTemplate;
    }


    public <T> T findById(Serializable id, Class<T> clazz)
    {

        return hibernateTemplate.get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findAll(Class<T> clazz)
    {
        return (List<T>) hibernateTemplate.findByCriteria(DetachedCriteria.forClass(clazz));
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria dc, Class<T> clazz)
    {
        return (List<T>) hibernateTemplate.findByCriteria(dc);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria dc)
    {
        return (List<T>) hibernateTemplate.findByCriteria(dc);
    }

    public int queryCountByCriteria(DetachedCriteria dc)
    {
        dc.setProjection(Projections.rowCount());
        Long count = (Long) hibernateTemplate.findByCriteria(dc).get(0);
        Integer max = new Integer(String.valueOf(count));
        return max;
    }

    public <T> List<T> findByConditionMap(Map<String, Serializable> m, Class<T> clazz)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.allEq(m));
        return findByCriteria(dc, clazz);
    }

    public <T> List<T> findByCondition(String col, Object value, Class<T> clazz)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        dc.add(Restrictions.eq(col, value));
        return findByCriteria(dc, clazz);
    }

    @Transactional(readOnly = false)
    public void delete(Object obj)
    {
        hibernateTemplate.delete(obj);
    }

    @Transactional(readOnly = false)
    public int deleteAll(Collection<?> c)
    {

        hibernateTemplate.deleteAll(c);
        return c.size();
    }

    @Transactional(readOnly = false)
    public Serializable save(Object obj)
    {
        return hibernateTemplate.save(obj);
    }

    @Transactional(readOnly = false)
    public void saveOrUpdate(Object obj)
    {
        hibernateTemplate.saveOrUpdate(obj);
    }
    

    /**
     * 分页查询
     *
     * @param dc       查询条件
     * @param page     当前页
     * @param pageSize 每页显示的记录数
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> findClassByPage(DetachedCriteria dc, int page, int pageSize, Class<T> clazz)
    {
        int startIndex = (page - 1) * pageSize;
        return (List<T>) hibernateTemplate.findByCriteria(dc, startIndex, pageSize);
    }

    /**
     * <p>Description: </p>
     *
     * @param sql
     * @param para
     * @param clazz
     * @return
     * @author joyu
     */
    public <T> List<T> getResultesBySql(final String sql, final Serializable para, Class<T> clazz)
    {

/*		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createNativeQuery(sql);
        if(!para.equals("") && para!=null){
			query.setParameter(1, para);
		}
		List<T> list = query.getResultList();
		return list;*/


        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) hibernateTemplate.execute(new HibernateCallback<Object>()
        {
            @Override
            public List<T> doInHibernate(org.hibernate.Session arg0) throws HibernateException
            {
                Query query = arg0.createNativeQuery(sql);
                if (!para.equals("") && para != null)
                {
                    query.setParameter(1, para);
                }
                return query.getResultList();
            }
        });
        return (List<T>)list;
    }

    /**
     * <p>Description: </p>
     *
     * @param sql
     * @param para
     * @return
     * @author joyu
     */
    public <T> List<T> getResultesBySql(final String sql, final Serializable para)
    {
        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) hibernateTemplate.execute(new HibernateCallback<Object>()
        {
            @Override
            public List<T> doInHibernate(org.hibernate.Session arg0) throws HibernateException
            {
                Query query = arg0.createNativeQuery(sql);
                if (!para.equals("") && para != null)
                {
                    query.setParameter(1, para);
                }
                return query.getResultList();
            }
        });
        return list;
    }
}
