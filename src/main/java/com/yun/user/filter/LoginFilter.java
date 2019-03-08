package com.yun.user.filter;

import com.yun.common.Constract;
import com.yun.user.model.UserInfo;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    private String[] excludeUrl={"/index.html","/login","/css","/js"};
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        response.setContentType("text/html;charset=utf-8");
        String requestUrl=request.getRequestURI();
        log.debug("当前请求的地址requestUrl："+requestUrl);
        if(!isValidate(requestUrl)) {
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constract.USER_SESSION_KEY);
            if (null == userInfo) {
                response.getWriter().print("<script>alert('没有登录不能访问');window.location='/index.html'</script>");
                //response.sendRedirect("/index.html");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    private boolean isValidate(String requestUrl){
        if(requestUrl.equals("/"))return true;
        for(String url:excludeUrl){
            if(requestUrl.startsWith(url)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
