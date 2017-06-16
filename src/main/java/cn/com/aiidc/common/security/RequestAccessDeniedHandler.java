/**
 *没有权限访问或通过ajax调用的访问，都会转到这个类来处理
 * @author joyu
 *@date 2017/03/23
 */
package cn.com.aiidc.common.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import cn.com.aiidc.common.util.RequestType;

public class RequestAccessDeniedHandler implements AccessDeniedHandler {


    protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);


    private String errorPage;

    /**
     * 无权限处理，通过response返回出错页（errorPage），及错误信息到浏览器
     */
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
    	logger.info("没有权限访问："+request.getRequestURI());
        if(RequestType.isAjaxReques(request)){
        	 response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        }else if (!response.isCommitted()) {
            if (errorPage != null) {
                // Put exception into request scope (perhaps of use to a view)
                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                // Set the 403 status code.
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                // forward to error page.
                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                dispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
            }
        }
    }

    /**
     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
     *
     * @param errorPage the dispatcher path to display
     *
     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
     */
    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

}
