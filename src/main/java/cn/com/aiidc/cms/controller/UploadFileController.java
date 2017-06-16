package cn.com.aiidc.cms.controller;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes({"user","SPRING_SECURITY_CONTEXT"})
@RequestMapping("/upload")
public class UploadFileController {
	
	
	@RequestMapping(value="/image",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String uploadImage(ModelMap model,HttpServletResponse response)
	{
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=656692895,2997804639&fm=23&gp=0.jpg");
		return obj.toString();
		
	}
	
	
}