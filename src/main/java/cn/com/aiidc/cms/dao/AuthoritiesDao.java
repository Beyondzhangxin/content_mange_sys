package cn.com.aiidc.cms.dao;

/**
 * @author joyu
 * @date 2017/03/27
 * Description: 完成authority 的增删改查
 **/

import cn.com.aiidc.cms.entity.TAuthorities;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class AuthoritiesDao extends HibernateDao
{

    /**
     * <p>Description: 取得所有权限列表</p>
     *
     * @return List
     * @author joyu
     */
    public List<TAuthorities> getAuthoritiesList()
    {
        return findAll(TAuthorities.class);
    }

    /**
     * <p>Description: 通过权限名称检索权限数据</p>
     *
     * @param authorityName ：权限名称
     * @return list
     * @author joyu
     */
    public List<TAuthorities> getTAuthoritieslistbySearch(String authorityName)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthorities.class);
        //通过名字模糊检索TUser
        if (authorityName != null)
        {
            dc.add(Restrictions.like("authorityName", authorityName, MatchMode.ANYWHERE));
        }
        return findByCriteria(dc);
    }


    /**
     * <p>Description: 返回分页数据</p>
     *
     * @param page：     页码
     * @param pageSize： 每页条数
     * @return
     * @author joyu
     */
    public List<TAuthorities> getPageAuthorities(int page, int pageSize)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthorities.class);
        List<TAuthorities> list = findClassByPage(dc, page, pageSize, TAuthorities.class);
        return list;
    }

    /**
     * <p>Description: 保存一个TAuthorities</p>
     *
     * @param authority
     * @author joyu
     */
    
    @Transactional(readOnly = false)
    public void saveAuthorities(TAuthorities authority)
    {
        save(authority);
    }

    /**
     * <p>Description: 删除一个TAuthorities</p>
     *
     * @param authority
     * @author joyu
     */
    @Transactional(readOnly = false)
    public void deleteAuthorities(TAuthorities authority)
    {
        delete(authority);
    }



    /**
     * <p>Description: 通过authorityName 从数据库中取得TAuthorities对象</p>
     *
     * @param authorityName
     * @return
     * @author joyu
     */
    public TAuthorities getByAuthoritiesName(String authorityName)
    {
        List<TAuthorities> list = findByCondition("authorityName", authorityName, TAuthorities.class);
        return list.get(0);
    }

}
