package cn.com.aiidc.cms.controller;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.com.aiidc.common.aop.CheckAccess;



@Controller
@SessionAttributes({"user","SPRING_SECURITY_CONTEXT"})
@RequestMapping("/login")
public class LoginController {
	
	
	@RequestMapping(value="/success",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String success(@SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext context,ModelMap model)
	{
		JSONObject obj = new JSONObject();
		obj.append("status", 0);
		obj.append("msg", "登录成功");
		return obj.toString();
	}
	
	/*@RequestMapping(value="/failure",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String failure()
	throws Exception
	{

		return "logon";
	}*/
	
	
	
	
}