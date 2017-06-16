package cn.com.aiidc.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import cn.com.aiidc.cms.service.LogonService;

@Controller
@SessionAttributes({"username","SPRING_SECURITY_CONTEXT"})
@RequestMapping("logon")
public class LogonController {
	
	@Autowired
	private LogonService logonService;
	
	
	
	public void setLogonService(LogonService logonService) {
		this.logonService = logonService;
	}

	@RequestMapping("prepare")
	public String logon(@SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext context,ModelMap model)
	throws Exception
	{
		UserDetails user = (UserDetails) context.getAuthentication().getPrincipal();
		model.put("username", user.getUsername());
		return "logon";
	}
	
	@RequestMapping(value="menu",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String menu(@RequestParam String username)
	throws Exception
	{
		return logonService.getMenuTreeForJson(username).toString();
		/*JSONArray menuTree = new JSONArray();
		JSONObject func   = new JSONObject();
		func.put("id", "1");
		func.put("text", "内容管理");
		func.put("state", "opened");
		
		JSONArray subTree = new JSONArray();
		JSONObject subFunc   = new JSONObject();
		subFunc.put("id", "1001");
		subFunc.put("text", "栏目管理");
		subFunc.put("state", "opened");
		subFunc.put("url", "channel/list.do");
		subTree.put(subFunc);
		subFunc   = new JSONObject();
		subFunc.put("id", "1003");
		subFunc.put("text", "模板管理");
		subFunc.put("state", "opened");
		subTree.put(subFunc);
		subFunc   = new JSONObject();
		subFunc.put("id", "1005");
		subFunc.put("text", "内容发布");
		subFunc.put("state", "opened");
		subTree.put(subFunc);
		func.put("children", subTree);
		menuTree.put(func);
		func   = new JSONObject();
		func.put("id", "3");
		func.put("text", "系统管理");
		func.put("state", "opened");
		subTree = new JSONArray();
		subFunc   = new JSONObject();
		subFunc.put("id", "3001");
		subFunc.put("text", "机构管理");
		subFunc.put("state", "opened");
		subTree.put(subFunc);
		subFunc   = new JSONObject();
		subFunc.put("id", "3003");
		subFunc.put("text", "用户管理");
		subFunc.put("state", "opened");
		subTree.put(subFunc);
		subFunc   = new JSONObject();
		subFunc.put("id", "3005");
		subFunc.put("text", "权限管理");
		subFunc.put("state", "opened");
		subTree.put(subFunc);
		func.put("children", subTree);
		menuTree.put(func);
		
		return menuTree.toString();*/
	}

	
	
	public String logout(ModelMap model)
	{
		model.remove("username");
		return "";
	}
}