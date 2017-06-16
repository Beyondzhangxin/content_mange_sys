package cn.com.aiidc.cms.service;

import cn.com.aiidc.cms.dao.AuthoritiesDao;
import cn.com.aiidc.cms.entity.TAuthorities;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 57332 on 2017/3/28.
 */

@Service
public class AuthService
{
    @Resource
    private AuthoritiesDao authoritiesDao;

    public AuthoritiesDao getAuthoritiesDao()
    {
        return authoritiesDao;
    }

    public void setAuthoritiesDao(AuthoritiesDao authoritiesDao)
    {
        this.authoritiesDao = authoritiesDao;
    }

    public List<TAuthorities> getList()
    {

        return authoritiesDao.getAuthoritiesList();
    }

    public List<TAuthorities> getIndexList(int page, int rows)
    {
        return authoritiesDao.getPageAuthorities(page,rows);
    }

    public List<TAuthorities> test(int page, int rows){
        return authoritiesDao.getPageAuthorities(page, rows);
    }
    /*save TAuthorities*/
    public void saveAuthority(TAuthorities authorities){
        authoritiesDao.saveOrUpdate(authorities);
        
    }

    public  int getListSize(){
        return authoritiesDao.queryCountByCriteria(DetachedCriteria.forClass(TAuthorities.class));
    }
    public void deleteAuth(TAuthorities authorities){
        authoritiesDao.delete(authorities);
    }
    public TAuthorities getAuthById(int authId){
        return authoritiesDao.findById(authId,TAuthorities.class);
    }





}
