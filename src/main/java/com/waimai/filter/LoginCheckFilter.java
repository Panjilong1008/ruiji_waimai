package com.waimai.filter;

import com.alibaba.fastjson.JSON;
import com.waimai.common.BaseContext;
import com.waimai.common.R;
import jdk.nashorn.internal.runtime.JSONFunctions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 使用过滤器技术，用于检查用户是否登录.
* 要使用过滤器得在启动类上加入注解
* */

//filterName设置过滤器名称，urlPatterns指定要拦截的url
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符（通配符就例如**代表所有的意思）
    public static final AntPathMatcher PATH_MATCHER=new  AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //STEP1：获得本次请求的url
        String requestURL=request.getRequestURI().replace("/waimai","");
        //STEP2：判断本次请求是否需要处理
        //定义不需要处理的url
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/webresources/**",
                "/front/**",
                "/mail/**"
        };



        boolean is_legal=url_check(requestURL,urls);
        //STEP3：若不需要处理，不进行过滤，直接放行
        if (is_legal){
            filterChain.doFilter(request,response);
            return;
        }
        //STEP4：若需要处理，在需要判断是否已经登陆。若登陆了则放行，否则返回未登陆提示，通过输出流的方式向客户端页面响应数据
        if (request.getSession().getAttribute("employee")!=null){
            Long id=(Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request,response);
            return;
        }else {
            log.info("拦截到未登录请求:{}",request.getRequestURI());
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        }



    }


    public boolean url_check(String requestUrl,String[] urls){
        for(String url : urls){
            boolean is_match=PATH_MATCHER.match(url,requestUrl);
            if (is_match){
                return true;
            }
        }
        return false;



    }
}
