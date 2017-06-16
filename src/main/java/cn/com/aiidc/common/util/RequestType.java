package cn.com.aiidc.common.util;
import javax.servlet.http.HttpServletRequest;

/**
 * @version 1.0
 * @author joyu
 * @date 2017年3月28日
 * @desc:通过request 请求，判断请求类别，现实现一个静态方法
 * 来判断是否为ajax请求
 */

public class RequestType {
	public static boolean isAjaxReques(HttpServletRequest request){
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
		return ajax || ajaxFlag.equalsIgnoreCase("true"); 
	}
}
