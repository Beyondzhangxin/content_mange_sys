package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.ResourcesDao;
import cn.com.aiidc.cms.entity.TResources;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Zhangx on 2017/4/1 at 18:06.
 */
@Service
public class ResService
{   @Resource
    private ResourcesDao resourcesDao;

    public ResourcesDao getResourcesDao()
    {
        return resourcesDao;
    }

    public void setResourcesDao(ResourcesDao resourcesDao)
    {
        this.resourcesDao = resourcesDao;
    }

    public  void  resSave(TResources tResources){
        resourcesDao.saveOrUpdate(tResources);
    }
    public List<TResources> getPagelist(int page, int rows){
        return resourcesDao.getPageResources(page,rows);
    }
    public int getSize(){
        DetachedCriteria dc=DetachedCriteria.forClass(TResources.class);
        return resourcesDao.queryCountByCriteria(dc);
    }
    public void delete(int id){
        TResources tResources=resourcesDao.findById(id,TResources.class);
        resourcesDao.delete(tResources);
    }
    public List<TResources> getall(){
        return resourcesDao.findAll(TResources.class);
}
   public TResources getById(int id){
            return resourcesDao.findById(id,TResources.class);
   }
}
