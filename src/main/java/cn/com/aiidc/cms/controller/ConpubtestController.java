package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TContentPublish;
import cn.com.aiidc.cms.service.ConPubMgrService;
import cn.com.aiidc.common.util.BeanUtils;

@Controller
@RequestMapping("conpubtest")
public class ConpubtestController {
	
	@Resource
	private ConPubMgrService conPubMgrService;

	public ConPubMgrService getConPubMgrService() {
		return conPubMgrService;
	}

	public void setConPubMgrService(ConPubMgrService conPubMgrService) {
		this.conPubMgrService = conPubMgrService;
	}

	@RequestMapping("list")
	public String list(){
		return "conpubtest/list";
	}
	
	/**
	 * 注意：由于用于测试，最多显示10条
	 * @param siteId
	 * @param channelId
	 * @param columnId
	 * @return
	 */
	@RequestMapping(value="/getcontentForConpubtestController",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getcontentForConpubtestController(Integer siteId,Integer channelId,Integer columnId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", 1);
		map.put("pageSize", 10);
		map.put("siteId", siteId);
		map.put("channelId", channelId);
		map.put("columnId", columnId);
		List<TContentPublish> conpubs = conPubMgrService.getContPubForPage(map);
		JSONArray jarr = new JSONArray();
		for(TContentPublish conpub : conpubs){
			try {
				jarr.put(BeanUtils.describe(conpub));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return jarr.toString();
	}
}
