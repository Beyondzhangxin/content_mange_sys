package cn.com.aiidc.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("pubplan")
public class PubSchedSetController {
	
	
	@RequestMapping("list")
	public String list()
	throws Exception
	{
		System.out.println("============跳转进入定时发布管理页面=============");
		return "pubplan/list";
	}
	
}