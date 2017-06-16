package cn.com.aiidc.cms.controller;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TTemplate;
import cn.com.aiidc.cms.entity.TUser;
import cn.com.aiidc.cms.service.ContentMgrService;
import cn.com.aiidc.cms.service.UserService;
import cn.com.aiidc.common.util.BeanUtils;
import cn.com.aiidc.common.util.DateUtil;
import cn.com.aiidc.common.util.FileUtil;
import cn.com.aiidc.common.util.PathUtil;

@Controller
@RequestMapping("conmgr")
public class ContentMgrController {

	@Resource
	private ContentMgrService contMgrService;

	public ContentMgrService getContMgrService() {
		return contMgrService;
	}

	public void setContMgrService(ContentMgrService contMgrService) {
		this.contMgrService = contMgrService;
	}
	
	@Resource
	private UserService userService;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	

	@RequestMapping("list")
	public String list() throws Exception {
		map.put("isReview", null);
		map.put("contentTitle", null);
		map.put("contentCreateUserName", null);
		map.put("isDisplay", 1);//默认情况是1-内容列表
		return "conmgr/list";
	}
	
	/**
	 * 获取内容模板
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getContentModForContent",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getContentModForContent() throws Exception{
		Integer templateClass =1;
		List<TTemplate> templates = contMgrService.findConMod(templateClass);
		JSONArray jarr = new JSONArray();
		for(TTemplate template :templates){
			jarr.put(BeanUtils.describe(template));
		}
		return jarr.toString();
	}
	
	/**
	 *  获取模板的内容
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="getModContent",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getModContent(Integer templateId) throws Exception{
		TTemplate template = contMgrService.findById(templateId, TTemplate.class);
		String templateFilePath = template.getTemplateFilePath();
		String str = FileUtil.ReadFileByBufferedReader(templateFilePath);
		JSONObject json = new JSONObject();
		json.put("ModName",template.getTemplateName() );
		json.put("ModContent",str);
		return json.toString();
	}
	
	/**
	 * 添加文章内容
	 * @param content
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/addContent",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addContent(@ModelAttribute TContent content,HttpServletRequest req)
	{
		JSONObject json = new JSONObject();//创建结果返回集
		if(content.getContentTitle() == null){
			json.put("success", false);
			json.put("msg","文章的标题不能为空！");
			return json.toString();
		}
		if(contMgrService.findContentByContentTitle(content.getContentTitle(), content.getContentId()) !=0){
			json.put("success", false);
			json.put("msg","该内容标题已存在，请重新输入！");
			return json.toString();
		}
		TUser user = userService.getLoginUser();
		content.setContentCreateUserName(user.getUsername());
		content.setContentCreateTime(new Date());
		//创建文件名
		String contentFileName = ""+user.getUserId()+DateUtil.getStringByDate(new Date(), "HHmmss")+".html";
		content.setContentFileName(contentFileName);
		//内容保存路径：
		String servicePath =PathUtil.getServicePath();
		String contFileName ="createContentFile";//存放内容的文件夹名称
		Calendar cal=Calendar.getInstance();
		String savePath = PathUtil.buildFilePathByDate(servicePath+File.separator+contFileName, cal);
		String contentFilePath = PathUtil.getFilePathForFile(contentFileName, savePath);
		content.setContentFilePath(contentFilePath);
		//判断文本域中的内容是否为空
		if(content.getContentFileUrl() == null){
			json.put("success", false);
			json.put("msg","请输入文章内容！");
			return json.toString();
		}else{
			//将字符串写入指定的html文件中
			FileUtil.WriteFileByBufferedWriter(content.getContentFileUrl(), contentFilePath);
		}
		//创建访问路径：
		String serverUrl = PathUtil.getServerUrl();
		String contentFileUrl= serverUrl+contentFilePath.substring(PathUtil.getServicePath().length());
		content.setContentFileUrl(contentFileUrl);
		//设置内容的显示状态：1 显示内容列表  2  回收站
		content.setIsDisplay(1);
		//添加content
		Integer contentId =contMgrService.addContent(content);
		if(contentId ==-1){
			json.put("success", false);
			json.put("msg","添加内容失败！");
			return json.toString();
		}else{
			json.put("success", true);
			json.put("msg","添加内容成功！");
			return json.toString();
		}
	}
	
	/**
	 * 分页查询——内容列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findContentForlistdata", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentForlistdata(int page, int rows){
		map.put("page", page);
		map.put("pageSize", rows);
		List<TContent> listdatas = contMgrService.getContentsByPage(map);
		JSONArray jarr = new JSONArray();
		for(TContent content : listdatas){
			Map<String, Object> conmap;
			try {
				conmap = BeanUtils.describe(content);
				//1 不要审核 	2 需要审核（未审核） 3 审核通过  4 审核未通过
				if(content.getIsReview() ==1){
					conmap.put("review","不要审核");
				}
				if(content.getIsReview() ==2){
					conmap.put("review","未审核");
				}
				if(content.getIsReview() ==3){
					conmap.put("review","审核通过");
				}
				if(content.getIsReview() ==4){
					conmap.put("review","审核未通过");
				}
				jarr.put(conmap);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Integer total = contMgrService.getTotalForContent(map);
		JSONObject json = new JSONObject();
		json.put("total", total);
		json.put("rows", jarr);
		return json.toString();
	}
	
	/**
	 * 为【内容列表】绑定单击事件
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findContentForlistdata2", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentForlistdata2(int page, int rows)
	{
		map.put("isReview", null);
		map.put("contentTitle", null);
		map.put("contentCreateUserName", null);
		map.put("isDisplay", 1);//默认情况是1-内容列表
		return findContentForlistdata(page,rows);
	}

	/**
	 * 添加搜索条件
	 * @param isReview
	 * @param contentTitle
	 * @param contentCreateUserName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addQueryCriteria", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String addQueryCriteria(Integer isReview ,String contentTitle, String contentCreateUserName) 
	throws Exception 
	{
		map.put("isReview", isReview);
		map.put("contentTitle", contentTitle);
		map.put("contentCreateUserName", contentCreateUserName);
		Integer page =1;Integer rows =10;
		return findContentForlistdata(page,rows);
	}
	
	/**
	 * 将需要删除的内容批量移动到回收站
	 * @param contentIds
	 * @return
	 */
	@RequestMapping(value = "/moveToRecycle",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String moveToRecycle(@RequestBody List<Integer> contentIds) 
	{
		contMgrService.updateAll(contentIds);
		JSONObject  json = new JSONObject();
		json.put("success", true);
		json.put("message", "删除成功");
		return json.toString();
	}
	
	/** 
	 * 
	 * 获取回收站数据列表：分页
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findContentForRecycle", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentForRecycle(int page, int rows)  {
		map.put("isReview", null);
		map.put("contentTitle", null);
		map.put("contentCreateUserName", null);
		map.put("isDisplay", 2);//默认情况是	1-内容列表  2 回收站
		return findContentForlistdata(page,rows);
	}
	
	/**
	 * 将回收站中不需要删除的文章，恢复
	 * @param contentIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/return2ContentList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String return2ContentList(@RequestBody List<Integer> contentIds) throws Exception {
		contMgrService.returnForContList(contentIds);
		JSONObject  json = new JSONObject();
		json.put("success", true);
		json.put("message", "恢复文章操作成功！");
		return json.toString();
	}

	/**
	 * 彻底删除文章：回收站
	 * @param contentId
	 * @return
	 */
	@RequestMapping(value = "/deleteContent",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String deleteContent(Integer contentId){
		TContent content =contMgrService.findById(contentId, TContent.class);
		String contentFilePath = content.getContentFilePath();
		boolean ok = FileUtil.deleteFile(contentFilePath);
		if(ok){
			contMgrService.deteleContent(content);
			JSONObject  json = new JSONObject();
			json.put("message", "成功从回收站删除文章！");
			return json.toString();
		}else{
			JSONObject  json = new JSONObject();
			json.put("message", "从回收站删除文章失败！");
			return json.toString();
		}
	}
	

	/**
	 * 编辑文章内容（特别注意：编辑时，需要传递内容的id）
	 * @param content
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/editContent",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String editContent(@ModelAttribute TContent content,HttpServletRequest req)
	{
		JSONObject json = new JSONObject();//创建结果返回集
		if(content.getContentId() ==null ){
			json.put("success", false);
			json.put("msg","编辑内容的操作失败！");
			return json.toString();
		}
		if(content.getContentTitle() == null){
			json.put("success", false);
			json.put("msg","文章的标题不能为空！");
			return json.toString();
		}
		if(contMgrService.findContentByContentTitle(content.getContentTitle(), content.getContentId()) !=0){
			json.put("success", false);
			json.put("msg","该内容标题已存在，请重新输入！");
			return json.toString();
		}
		TUser user = userService.getLoginUser();
		content.setContentCreateUserName(user.getUsername());
		content.setContentCreateTime(new Date());
		//创建文件名
		String contentFileName = ""+user.getUserId()+DateUtil.getStringByDate(new Date(), "HHmmss")+".html";
		content.setContentFileName(contentFileName);
		//内容原有的保存路径：
		String contentFilePath = content.getContentFilePath();
		//根据原有的文件存储路径删除原有的文件
		if(!FileUtil.deleteFile(contentFilePath)){
			json.put("success", false);
			json.put("msg","编辑内容的操作失败！");
			return json.toString();
		}
		//创建内容编辑后的保存路径
		String servicePath =PathUtil.getServicePath();
		String contFileName ="createContentFile";//存放内容的文件夹名称
		Calendar cal=Calendar.getInstance();
		String savePath = PathUtil.buildFilePathByDate(servicePath+File.separator+contFileName, cal);
		contentFilePath = PathUtil.getFilePathForFile(contentFileName, savePath);
		content.setContentFilePath(contentFilePath);
		//判断文本域中的内容是否为空
		if(content.getContentFileUrl() == null){
			json.put("success", false);
			json.put("msg","请输入文章内容！");
			return json.toString();
		}else{
			//将字符串写入指定的html文件中
			FileUtil.WriteFileByBufferedWriter(content.getContentFileUrl(), contentFilePath);
		}
		//创建访问路径：
		String serverUrl = PathUtil.getServerUrl();
		String contentFileUrl= serverUrl+contentFilePath.substring(PathUtil.getServicePath().length());
		content.setContentFileUrl(contentFileUrl);
		//设置内容的显示状态：1 显示内容列表  2  回收站
		content.setIsDisplay(1);
		//编辑content
		contMgrService.updateContent(content);
		json.put("success", true);
		json.put("msg","编辑文章内容成功！");
		return json.toString();
	}
	
	/**
	 * 根据contentId查询文章的内容
	 * @param contentId
	 * @return
	 */
	@RequestMapping(value="/findContentById",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String findContentById(Integer contentId)
	{
		TContent content = contMgrService.findById(contentId,TContent.class);
		String htmlPath=content.getContentFileUrl();
		String contentStr = FileUtil.ReadFileByBufferedReader(htmlPath);
		content.setContentFileUrl(contentStr);
		JSONObject json = new JSONObject(content);
		return json.toString();
	}

}



























