package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.AuthoritiesDao;
import cn.com.aiidc.cms.dao.AuthoritiesResourcesDao;
import cn.com.aiidc.cms.dao.ResourcesDao;
import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TAuthoritiesResources;
import cn.com.aiidc.cms.entity.TResources;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/6 at 11:02.
 */
@Service
public class ResToAuthService
{
    @Resource
    private AuthoritiesResourcesDao authoritiesResourcesDao;
    @Resource
    private AuthoritiesDao authoritiesDao;
    @Resource
    private ResourcesDao resourcesDao;

    public AuthoritiesDao getAuthoritiesDao()
    {
        return authoritiesDao;
    }

    public void setAuthoritiesDao(AuthoritiesDao authoritiesDao)
    {
        this.authoritiesDao = authoritiesDao;
    }


    public ResourcesDao getResourcesDao()
    {
        return resourcesDao;
    }

    public void setResourcesDao(ResourcesDao resourcesDao)
    {
        this.resourcesDao = resourcesDao;
    }


    public AuthoritiesResourcesDao getAuthoritiesResourcesDao()
    {
        return authoritiesResourcesDao;
    }

    public void setAuthoritiesResourcesDao(AuthoritiesResourcesDao authoritiesResourcesDao)
    {
        this.authoritiesResourcesDao = authoritiesResourcesDao;
    }

    /**
     * 通过资源的ID来获取该资源ID所对应的权限
     *
     * @param id
     * @return
     */
    public List<TAuthorities> getauthByRes(int id)
    {
        TResources tResources = resourcesDao.findById(id, TResources.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
        dc.add(Restrictions.eq("TResources", tResources));
        List<TAuthoritiesResources> list = authoritiesResourcesDao.findByCriteria(dc);
        List<TAuthorities> authlist = new ArrayList<>();
        for (TAuthoritiesResources authoritiesResources : list)
        {
            authlist.add(authoritiesResources.getTAuthorities());
        }
        return authlist;
    }

    /*根据权限的ID来获取该权限下所有的资源*/
    public List<TResources> getResByAuthId(int id)
    {
        TAuthorities tAuthorities = authoritiesDao.findById(id, TAuthorities.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
        dc.add(Restrictions.eq("TAuthorities", tAuthorities));
        List<TAuthoritiesResources> list = authoritiesResourcesDao.findByCriteria(dc);
        List<TResources> resList = new ArrayList<>();
        for (TAuthoritiesResources authoritiesResources : list)
        {
            resList.add(authoritiesResources.getTResources());
        }
        return resList;
    }

    public List<TAuthoritiesResources> getrestoauthlistByRes(int id)
    {
        TResources tResources = resourcesDao.findById(id, TResources.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
        dc.add(Restrictions.eq("TResources", tResources));
        List<TAuthoritiesResources> list = authoritiesResourcesDao.findByCriteria(dc);
        return list;
    }

    public List<TAuthoritiesResources> getRestoAuthlistByAuthId(int id)
    {
        TAuthorities tAuthorities = authoritiesDao.findById(id, TAuthorities.class);
        DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
        dc.add(Restrictions.eq("TAuthorities", tAuthorities));
        List<TAuthoritiesResources> list = authoritiesResourcesDao.findByCriteria(dc);
        return list;
    }

    public List<TAuthorities> getUnselectedAuthByResId(int id)
    {
        List<TAuthorities> listall = authoritiesDao.findAll(TAuthorities.class);
        List<TAuthorities> listin = getauthByRes(id);
        listall.removeAll(listin);
        return listall;
    }

    public List<TResources> getUnselectedResByAuthId(int id)
    {
        List<TResources> listall=resourcesDao.findAll(TResources.class);
        List<TResources> listin=getResByAuthId(id);
        listall.removeAll(listin);
        return listall;
    }

    public void save(int resId, int authId)
    {
        TResources tResources = resourcesDao.findById(resId, TResources.class);
        TAuthorities tAuthorities = authoritiesDao.findById(authId, TAuthorities.class);
        TAuthoritiesResources tAuthoritiesResources = new TAuthoritiesResources();
        tAuthoritiesResources.setTAuthorities(tAuthorities);
        tAuthoritiesResources.setTResources(tResources);
        authoritiesResourcesDao.saveOrUpdate(tAuthoritiesResources);

    }

    public int deleteAll(List<?> list)
    {
        return authoritiesResourcesDao.deleteAll(list);
    }


}
