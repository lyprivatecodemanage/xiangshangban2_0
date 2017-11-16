package com.xiangshangban.transit_service.filter;

import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.web.servlet.AdviceFilter;  
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;  
  
  
  
/**用于跨域共享session,那个应用需要与登录应用共享session，就开放此filter，并且应该是拦截所有请求，至少是拦截所有需要保护的资源 
 * @author Dylan 
 * @mail pickear@gmail.com 
 * @time 2014年4月14日 
 */  
public class ShiroFilter extends AdviceFilter {  
      
    private final static Logger log = Logger.getLogger(ShiroFilter.class);  
      
    private String casServerURL;  //重定向的目标地址，该地址用于获取sessionId,如www.b.com/token  
    private String domain;   //filter应用的域名，如www.a.com  
  
    @Override  
    protected boolean preHandle(ServletRequest request, ServletResponse response)throws Exception {  
    	HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("origin"));
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
        /*boolean hasSyn = (null == ShiroSecurityHelper.getSession().getAttribute("hasSyn") ? false : (Boolean) ShiroSecurityHelper.getSession().getAttribute("hasSyn"));  
        if(ShiroSecurityHelper.hasAuthenticated() || hasSyn){//当用户已经登录或者从session中取得的hasSyn为true，说明已经同步session，不需要再重定向  
            return true;  
        }  
        String jsid = WebUtils.getCleanParam(request, "jsid");  
        HttpServletRequest httpRequest = WebUtils.toHttp(request);  
        String url = httpRequest.getRequestURL().toString();  
        url = StringUtils.remove(url, httpRequest.getContextPath());  
        if(StringUtils.isNotBlank(jsid)){//如果jsid不为空，说明是通过www.b.com重定向回来的，将从b域名拿到的sessionId写回到自己域名下。  
                       //以下两句作用是将jsid,rememberMe写到domain域名下的cookie中，读者可以自己实现。  
            HttpHelper.setCookie(WebUtils.toHttp(httpRequest),WebUtils.toHttp(response), "jsid", jsid,domain,"/");  
            HttpHelper.setCookie(WebUtils.toHttp(httpRequest),WebUtils.toHttp(response), "rememberMe", WebUtils.getCleanParam(request, "rememberMe"),domain,"/");  
            WebUtils.issueRedirect(request, response, url);  
            log.info("redirect : " + url);  
            return false;  
        }  
        String uri = casServerURL + "?service=" + url;   //重写向到www.b.com/token下  
        WebUtils.issueRedirect(request, response, uri);  
        log.info("redirect : " + uri);  
        return false;  */
    }  
  
    @Override  
    protected void postHandle(ServletRequest request, ServletResponse response)throws Exception {  
        super.postHandle(request, response);  
    }  
  
    public void setCasServerURL(String casServerURL) {  
        this.casServerURL = casServerURL;  
    }  
  
    public void setDomain(String domain) {  
        this.domain = domain;  
    }  
      
}  
