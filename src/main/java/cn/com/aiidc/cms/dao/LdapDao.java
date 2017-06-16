package cn.com.aiidc.cms.dao;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.Name;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;


public class LdapDao {
	
	private LdapTemplate ldapTemplate;

	@Autowired
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
	
	
	
	
	public <T>  T findById(String idName,String idValue,Class<T> clazz)
	{
		LdapQuery query =query().where(idName).is(idValue);
		return ldapTemplate.findOne(query, clazz);
	}
	
	public <T> List<T> findByProperties(Properties p,Class<T> clazz)
	{
		
		ContainerCriteria query = null;
		Set<String> keys = p.stringPropertyNames();
		for (String key :keys) 
		{
			if(query==null){
				query = query().where(key).is(p.getProperty(key));
			}else{
				query.and(key).is(p.getProperty(key));
			}
		}
		
		return ldapTemplate.find(query, clazz);
	}
	
	public <T> T findOneByProperties(Properties p,Class<T> clazz)
	{
		
		ContainerCriteria query = null;
		Set<String> keys = p.stringPropertyNames();
		for (String key :keys) {
			if(query==null){
				query = query().where(key).is(p.getProperty(key));
			}else{
				query.and(key).is(p.getProperty(key));
			}
		}
		
		return ldapTemplate.findOne(query, clazz);
	}
	
	public <T> T findByDn(Name dn,Class<T> clazz)
	{
		return ldapTemplate.findByDn(dn, clazz);
	}
	
	public <T> T findByDn(String dnStr,Class<T> clazz)
	{
		Name dn = LdapNameBuilder.newInstance(dnStr).build();
		return findByDn(dn, clazz);
	}
	
	public boolean authenticate(String userid,String password)
	throws Exception
	{
		LdapQuery query =query().where("uid").is(userid);
		ldapTemplate.authenticate(query, password);
		
		return true;
	}
	
	
	public void update(Object obj)
	{
		ldapTemplate.update(obj);
	}

	
}

