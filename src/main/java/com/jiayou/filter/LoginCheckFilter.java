package com.jiayou.filter;

import com.alibaba.fastjson.JSON;
import com.jiayou.common.BaseContext;
import com.jiayou.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        if(check(requestURI,urls)){
            filterChain.doFilter(request,response);
            return;
        }
        //判断客户端是否有登入
        if(request.getSession().getAttribute("employee") != null){
            long id = (long) request.getSession().getAttribute("employee");
            BaseContext.setid(id);
            log.info("有登入，放行了");
            filterChain.doFilter(request,response);
            return;
        }

        //判断移动端是否有登入
        if(request.getSession().getAttribute("user") != null){
            long id = (Long) request.getSession().getAttribute("user");
            BaseContext.setid(id);
            log.info("有登入，放行了");
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截{}",request.getRequestURI());
    }

    //检查是不是在urls数组里面的地址
    public boolean check(String s,String[] arr){
        for(String url : arr){
            boolean match = PATH_MATCHER.match(url, s);
            if(match){
                return true;
            }
        }
        return false;
    }

}
