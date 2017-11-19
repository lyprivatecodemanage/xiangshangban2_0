package com.xiangshangban.transit_service.filter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;

import com.xiangshangban.transit_service.service.LoginService;
import com.xiangshangban.transit_service.service.UusersService;
=======
>>>>>>> fcbad0c389c8688c29385b35bee09f6a6d077c0a
import com.xiangshangban.transit_service.util.HttpClientUtil;

@WebFilter(filterName="ServletFilter",urlPatterns="/*")
public class ServletFilter implements Filter{

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception { 
        File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
	
	
	public void init(FilterConfig filterConfig) throws ServletException {
		 
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response;
		String uri = req.getRequestURI();
		System.out.println(uri);
<<<<<<< HEAD
		// 这里填写你允许进行跨域的主机ip
=======
		res.setContentType("textml;charset=UTF-8");
		 //这里填写你允许进行跨域的主机ip
>>>>>>> fcbad0c389c8688c29385b35bee09f6a6d077c0a
		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		res.setHeader("Access-Control-Allow-Credentials","true");
		// 允许的访问方法
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
<<<<<<< HEAD
		// Access-Control-Max-Age 用于 CORS 相关配置的缓存
		res.setHeader("Access-Control-Max-Age", "3600");
		// Content-Disposition文件下载时设置文件名
		res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Access-Control-Request-Headers, Content-Type, Accept, Content-Disposition, type");
		// res.setHeader("Content-Disposition",
		// "attachment;filename="+java.net.URLEncoder.encode("报表.xls","UTF-8"));
		res.setHeader("Access-Control-Allow-Methods", req.getMethod());
=======
		/*res.setHeader("Access-Control-Allow-Methods", req.getMethod());*/
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
		res.setHeader("Access-Control-Max-Age", "3600");
		//Content-Disposition文件下载时设置文件名
		res.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, type");
		res.setHeader("XDomainRequestAllowed","1");
		//res.setHeader("Content-Disposition", "attachment;filename="+java.net.URLEncoder.encode("报表.xls","UTF-8"));
		//res.setHeader("Access-Control-Allow-Methods", req.getMethod());
>>>>>>> fcbad0c389c8688c29385b35bee09f6a6d077c0a
		String [] includeMode = HttpClientUtil.getIncludeMode();
		String redirectUrl = "";
		boolean redirect = false;
		for(String mode : includeMode){
			String checkUrl = "/api/"+mode+"/";
			if(uri.contains(checkUrl)){
				String sendurl = URLDecoder.decode(uri.replaceAll(checkUrl, ""),"UTF-8");
				if(uri.contains("/export/")){
					redirectUrl = "/redirectApi/exportRequest?redirectUrl="+sendurl+"&redirectMode="+mode;
				}else{
					redirectUrl = "/redirectApi/sendRequest?redirectUrl="+sendurl+"&redirectMode="+mode;
				}
				
				redirect = true;
			}
		}
		
		if(redirect){
			//System.out.println("sessionId :"+req.getSession().getId());
			//String companyId = req.getParameter("companyId");
			req.getRequestDispatcher(redirectUrl).forward(req, res);
		}else{
			System.out.println("=======================>"+req.getSession().getId());
			chain.doFilter(req, res);
			System.out.println("嘻嘻嘻");
		}
	}

	public void destroy() {
	}

}
