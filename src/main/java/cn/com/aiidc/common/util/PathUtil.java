package cn.com.aiidc.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PathUtil {

	/**
	 * 获取webapps的位置
	 * 如：E:\gkr\apache-tomcat-9.0.0.M9\webapps
	 * @return
	 */
	public static String getServicePath(){
		//获取request
		ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		//获取当前应用在服务器的目录
		String path = request.getSession().getServletContext().getRealPath(File.separator);
		//获取应用
		String servicePath = path.substring(0,path.lastIndexOf(File.separator));
		return servicePath;
	}
	
	/**
	 * 获取ServerUrl：http://ip号/端口号
	 * @return
	 */
	public static String getServerUrl(){
		//获取request
		ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String ServerUrl=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		return ServerUrl;
	}

	/**
	 * 创建文件夹：
	 * @param fileName  文件夹名
	 * @param path  目录的路径
	 * @return	返回文件夹的保存路径
	 */
	public static String getFilePathForDirectory(String fileName,String path){
		String savepath = path+File.separator+fileName;
		File file = new File(savepath);
		//判断File目录是否存在  且 file是否是一个目录
		if(!file.exists()  &&  !file.isDirectory()){
			file.mkdirs();
		}
		return savepath;
	}
	
	/**
	 * 在指定的文件夹目录下:创建年、月、日目录
	 * @param path
	 * @param cal
	 * @return
	 */
	public static String buildFilePathByDate(String path,Calendar cal){
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;//由月份从0开始计算，所以需要+1
		String monStr ="";
		if(month<10){
			monStr+="0"+month;
		}else{
			monStr+=month;
		}
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String dayStr ="";
		if(day<10){
			dayStr+="0"+day;
		}else{
			dayStr+=day;
		}
		String savepath = path+File.separator+year+File.separator+monStr+File.separator+dayStr;
		File file = new File(savepath);
		if(!file.exists()  &&  !file.isDirectory()){
			file.mkdirs();
		}
		return savepath;
	}
	
	/**
	 * 创建文件：
	 * @param fileName 文件名
	 * @param path 保存路径
	 * @return	返回文件的保存路径
	 */
	public static String getFilePathForFile(String fileName,String path){
		String savepath = path+File.separator+fileName;
		File file = new File(savepath);
		//判断File目录是否存在  且 file是否是一个目录
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return savepath;
	}
	
}
