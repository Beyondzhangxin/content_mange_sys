package cn.com.aiidc.cms.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.aiidc.cms.dao.HibernateDao;


@Service
public class ChannelService {

	@Resource
	private HibernateDao hibernateDao;

	public HibernateDao getHibernateDao() 
	{
		return hibernateDao;
	}

	public void setHibernateDao(HibernateDao hibernateDao) 
	{
		this.hibernateDao = hibernateDao;
	}

	

	
	

}
