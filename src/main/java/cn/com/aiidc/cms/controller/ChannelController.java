package cn.com.aiidc.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import cn.com.aiidc.cms.service.ChannelService;

@Controller
@RequestMapping("channel")
public class ChannelController {
	
	
	@Autowired
	private ChannelService channelService;
	
	
	
	public ChannelService getChannelService() {
		return channelService;
	}



	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	
	@RequestMapping("list")
	public String logon(@SessionAttribute("SPRING_SECURITY_CONTEXT") SecurityContext context,ModelMap model)
	{
		UserDetails user = (UserDetails) context.getAuthentication().getPrincipal();
		model.put("username", user.getUsername());
		return "channel/list";
	}


	@RequestMapping(value="listdata",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
/*	public String list()
	throws Exception
	{
		return channelService.getJsonOfAllChannel().toString();
	}*/

	
	
	public String logout(ModelMap model)
	{
		model.remove("username");
		return "";
	}
}