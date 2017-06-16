package cn.com.aiidc.cms.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TRiview;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ConApprService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;

@Controller
@RequestMapping("conappr")
public class ConApprController {
	@Resource
	private ConApprService conApprService;
	
	public ConApprService getConApprService() {
		return conApprService;
	}

	public void setConApprService(ConApprService conApprService) {
		this.conApprService = conApprService;
	}
	
	@Resource
	private UserService userService;

	private Map<String, Object> map = new HashMap<String, Object>();
	
	@RequestMapping("list")
	public String list()
	throws Exception
	{
		map.put("contentTitle", null);
		map.put("contentCreateUserName", null);
		return "conappr/list";
	}
	
	/**
	 * 内容审核列表：分页
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findContentForConAppr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentForConAppr(Integer page,Integer rows)
	{
		map.put("page", page);
		map.put("pageSize", rows);
		List<TContent> listdatas = conApprService.findContentForAppr(map);
		JSONArray jarr = new JSONArray();
		for(TContent content : listdatas){
			try {
				jarr.put(BeanUtils.describe(content));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = conApprService.getTotalForConAppr(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	
	/**
	 * 添加搜索条件，返回结果集
	 * @param contentTitle
	 * @param contentCreateUserName
	 * @return
	 */
	@RequestMapping(value = "/addQueryForConAppr", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addQueryForConAppr(String contentTitle, String contentCreateUserName){
		map.put("contentTitle", contentTitle);
		map.put("contentCreateUserName", contentCreateUserName);
		int page =1;int rows =10;
		return findContentForConAppr(page,rows);
	}
	
	
	/**
	 * 内容审核
	 * @param contentId
	 * @param reviewStatus
	 * @param apprNoPassReason
	 * @return
	 */
	@RequestMapping(value = "/ApprContent", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String ApprContent(Integer contentId,Integer reviewStatus,String apprNoPassReason){
		TContent content =conApprService.findById(contentId, TContent.class);
		content.setIsReview(reviewStatus);
		TRiview riview = new TRiview();
		riview.setContentId(contentId);
		riview.setReviewStatus(reviewStatus);
		String contentApprTime = DateUtil.getStringByDate(new Date(),"yyyy-MM-dd");
		riview.setContentApprTime(contentApprTime);
		TUser user = userService.getLoginUser();
		riview.setContentApprUserId(user.getUserId());
		riview.setApprNoPassReason(apprNoPassReason);
		//保存审核结果
		Integer riviewId = conApprService.saveRiview(riview, content);
		JSONObject json = new JSONObject();
		if(riviewId<1){
			json.put("success", false);
			json.put("msg", "审核未完成");
			return json.toString();
		}
		json.put("success", true);
		json.put("msg", "审核完成");
		return json.toString();
	}
	
	
}