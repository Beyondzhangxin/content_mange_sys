/**
 *该类的主要作用是为Spring Security提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 此类在初始化时，应该取到所有资源及其对应角色的定义。。
 * @author joyu
 *@date 2017/03/23
 */
package cn.com.aiidc.common.security;

import cn.com.aiidc.cms.dao.AuthoritiesResourcesDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CustomInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource,InitializingBean {

	private final static List<ConfigAttribute> NULL_CONFIG_ATTRIBUTE = Collections.emptyList();
	
	private final static String RES_KEY = "resourcePath";
	private final static String AUTH_KEY = "authorityMark";
	
	//权限集合
	private Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;
	
	@Autowired
	private AuthoritiesResourcesDao authoritiesResourcesDao;
	
	/**
	 * 
	 * <p>Description:返回所请求资源所需要的权限 </p>
	 * @author joyu
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        
        //Collection<ConfigAttribute> attrs = NULL_CONFIG_ATTRIBUTE;
		Collection<ConfigAttribute> attrs = new LinkedList<>();
        
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            if (entry.getKey().matches(request)) {
            	attrs =  entry.getValue();
            	break;
            }
        }
        //防止数据库中没有数据，不能进行权限拦截  
        if(attrs.size()<1){  
            ConfigAttribute configAttribute = new SecurityConfig("AUTH_NO_USER");  
            attrs.add(configAttribute);  
        }  
        //logger.info("URL资源："+request.getRequestURI()+ " -> " + attrs);
        return attrs;
	}

	/**
	 * 
	 * <p>Description: </p>
	 * @author joyu
	 * @return
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();

        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	/**
	 * 
	 * <p>Description: 加载所有资源与权限</p>
	 * @author joyu
	 * @return
	 */
	private Map<String,String> loadResuorce(){
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		List<Map<String,String>> list = this.authoritiesResourcesDao.getURLResourceMapping();
		Iterator<Map<String,String>> it = list.iterator();
		while(it.hasNext()){
			Map<String,String> rs = it.next();
			String resourcePath = rs.get(RES_KEY);
			String authorityMark = rs.get(AUTH_KEY);
			
			if(map.containsKey(resourcePath)){
				String mark = map.get(resourcePath);
				map.put(resourcePath, mark+","+authorityMark);
			}else{
				map.put(resourcePath, authorityMark);
			}
		}
		return map;
	}
	
	/**
	 * 
	 * <p>Description: 绑定资源与权限的关系</p>
	 * @author joyu
	 * @return
	 */
	protected Map<RequestMatcher, Collection<ConfigAttribute>> bindRequestMap(){
		Map<RequestMatcher, Collection<ConfigAttribute>> map = 
				new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		
		Map<String,String> resMap = this.loadResuorce();
		for(Map.Entry<String,String> entry:resMap.entrySet()){
			String key = entry.getKey();
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			atts = SecurityConfig.createListFromCommaDelimitedString(entry.getValue());
			map.put(new AntPathRequestMatcher(key), atts);
		}
		return map;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		this.requestMap = this.bindRequestMap();
		//logger.info("资源权限列表"+this.requestMap);
	}
	
	public void refreshResuorceMap(){
		this.requestMap = this.bindRequestMap();
	}

}
