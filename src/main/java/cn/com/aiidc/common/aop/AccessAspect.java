package cn.com.aiidc.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccessAspect 
{
	@Around(value="@annotation(CheckAccess)",argNames="CheckAccess")
	public Object around(ProceedingJoinPoint pjp,CheckAccess access){
		System.out.println("================响应请求前进行权限检查====================");
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("msg", "检查未通过");
		return json.toString();
	}
	
}
