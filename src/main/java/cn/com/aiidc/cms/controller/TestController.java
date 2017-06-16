package cn.com.aiidc.cms.controller;

import cn.com.aiidc.cms.dao.AuthoritiesDao;
import cn.com.aiidc.cms.dao.AuthoritiesResourcesDao;
import cn.com.aiidc.cms.dao.ResourcesDao;
import cn.com.aiidc.cms.entity.TAuthorities;
import cn.com.aiidc.cms.entity.TAuthoritiesResources;
import cn.com.aiidc.cms.entity.TResources;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/6 at 14:43.
 */
@Controller
@RequestMapping("test")
public class TestController
{
    @Resource
    private AuthoritiesDao authoritiesDao;
    @Resource
    private AuthoritiesResourcesDao authoritiesResourcesDao;
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

    public AuthoritiesResourcesDao getAuthoritiesResourcesDao()
    {
        return authoritiesResourcesDao;
    }

    public void setAuthoritiesResourcesDao(AuthoritiesResourcesDao authoritiesResourcesDao)
    {
        this.authoritiesResourcesDao = authoritiesResourcesDao;
    }

    public ResourcesDao getResourcesDao()
    {
        return resourcesDao;
    }

    public void setResourcesDao(ResourcesDao resourcesDao)
    {
        this.resourcesDao = resourcesDao;
    }

    @RequestMapping("list")
    @ResponseBody
    public List<TAuthorities> get(@RequestParam int id){
        TResources tResources=resourcesDao.findById(id,TResources.class);
        DetachedCriteria dc=DetachedCriteria.forClass(TAuthoritiesResources.class);
        dc.add(Restrictions.eq("TResources",tResources));
        List<TAuthoritiesResources> list= authoritiesResourcesDao.findByCriteria(dc);
        List<TAuthorities>  authlist=new ArrayList<>();
        for (TAuthoritiesResources authoritiesResources : list)
        {
            authlist.add(authoritiesResources.getTAuthorities());
        }
        return authlist;
    }

    @RequestMapping("list1")
    @ResponseBody
    public List<TAuthorities> getUnselectedAuthByResId(int id)
    {
        List<TAuthorities> listall = authoritiesDao.findAll(TAuthorities.class);
        List<TAuthorities> listin = get(id);
        listall.removeAll(listin);
        return listall;
    }

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


     @RequestMapping("delete")
     @ResponseBody
    public String delete(){
         TResources tResources = resourcesDao.findById(4, TResources.class);
         DetachedCriteria dc = DetachedCriteria.forClass(TAuthoritiesResources.class);
         dc.add(Restrictions.eq("TResources", tResources));
         List<TAuthoritiesResources> list = authoritiesResourcesDao.findByCriteria(dc);
         List<TAuthorities> authli=new ArrayList<>();
         authli.add(authoritiesDao.findById(111,TAuthorities.class));
         authli.add(authoritiesDao.findById(123,TAuthorities.class));
         try
         {
             for (int i = 0; i < 2; i++)
             {
                 save(6,111+i);
             }
             return String.valueOf(authoritiesResourcesDao.deleteAll(list));

         } catch (Exception e)
         {
             e.printStackTrace();
             return "fail";
         }

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

}
