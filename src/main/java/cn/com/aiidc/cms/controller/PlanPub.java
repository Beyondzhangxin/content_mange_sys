package cn.com.aiidc.cms.controller;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import cn.com.aiidc.cms.entity.TColumn;
import cn.com.aiidc.cms.entity.TContent;
import cn.com.aiidc.cms.entity.TContentPublish;
import cn.com.aiidc.cms.service.ConPubService;
import cn.com.aiidc.common.util.DateUtil;
import cn.com.aiidc.common.util.FileUtil;
import cn.com.aiidc.common.util.PathUtil;

public class PlanPub {
	@Resource
	private ConPubService conPubService;
	
	/**
	 * 执行定时任务的方法
	 * @throws ParseException 
	 */
	public void execute() throws ParseException{
		Date date = new Date();
		List<TContentPublish> list = new	ArrayList<TContentPublish>();
		List<TContentPublish> contentPubs = conPubService.findPlanPubContent();
//		System.out.println("需要定时发布的个数："+contentPubs.size());
		for(TContentPublish contentPub : contentPubs){
			Date nowTime =date;
			String contentPubPlanTime = contentPub.getContentPubPlanTime();
			Date planTime = DateUtil.getDateByString(contentPubPlanTime, "yyyy-MM-dd HH:mm:ss");
			int min = (int) (Math.abs(nowTime.getTime()-planTime.getTime())/1000/60);
			if(min <=5){
				contentPub.setContentPubTime(DateUtil.getStringByDate(planTime, "yyyy-MM-dd"));
				list.add(contentPub);
			}
		}
//		System.out.println("即将发布的个数："+list.size());
		if(list.size() >0){
			ergodicList(list);
		}
	}
	
	/**
	 * 遍历集合：发布到时间的文章
	 * @param contentPubs
	 */
	public void ergodicList(List<TContentPublish> contentPubs){
		for(TContentPublish contentPublish : contentPubs){
			System.out.println("***************************************************************************");
			String serverUrl= contentPublish.getContentPubUrl();
			String serverPath = contentPublish.getContentPubPath();
			//获取栏目的保存路径：
			TColumn column = conPubService.findById(contentPublish.getColumnId(), TColumn.class);
			String columnpath = column.getColumnFilePath();
			//在栏目路径下创建年月日目录
			Calendar cal=Calendar.getInstance();
			String savecontentpubpath = PathUtil.buildFilePathByDate(columnpath, cal);
			//将html文件复制到指定的目录下：
			TContent content = conPubService.findById(contentPublish.getContentId(), TContent.class);
			String contentPath = content.getContentFilePath();//目标文件的路径
			String contentPubPath = FileUtil.copyFile(contentPath, savecontentpubpath);
			contentPublish.setContentPubPath(contentPubPath);
			//设置文件的访问路径
			contentPublish.setContentPubUrl(serverUrl+File.separator+contentPubPath.substring(serverPath.length()+1));
			//设置文章标题
			contentPublish.setContentTitle(content.getContentTitle());
			contentPublish.setContentPubPlanTime(null);
			//保存结果
			conPubService.update(contentPublish);
		}
	}
}
