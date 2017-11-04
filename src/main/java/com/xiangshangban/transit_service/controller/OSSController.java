package com.xiangshangban.transit_service.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.bean.OSSFile;
import com.xiangshangban.transit_service.service.OSSFileService;
import com.xiangshangban.transit_service.util.OSSFileUtil;


@RestController
@RequestMapping(value = "/oss")
public class OSSController {
	@Autowired
	OSSFileService oSSFileService;
	/**
	 * 上传文件到OSS
	 * @param file
	 * @param ACCESS_TOKEN 
	 * @param funcDirectory 存储模块名称
	 * @return
	 */
	@RequestMapping(value = "/upload",produces = "application/json;charset=UTF-8",method=RequestMethod.POST)
	public String appUpload(@RequestParam(value="file") MultipartFile file,@RequestHeader String ACCESS_TOKEN, 
			@RequestParam(value="funcDirectory") String funcDirectory){ 
		String token = ACCESS_TOKEN;
		String customerId = "C001";//公司编号，此编号实际应用时，应根据token去查询
		funcDirectory = "portrait";//portrait目录存储员工头像
		if(StringUtils.isNotEmpty(token)){
	        if(!file.isEmpty()){		
	        	OSSFile ossFile = oSSFileService.addOSSFile(customerId, funcDirectory, file);
	        	//System.out.println(JSON.toJSONString(ossFile));
	        	return JSON.toJSONString(ossFile);
	        }
		}
        return null;  
    }
	/**
	 * 根据文件名获取全路径
	 * @param key
	 * @param ACCESS_TOKEN
	 * @return
	 */
	@RequestMapping(value = "/path.shtml",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	public String appGetPath(String key,@RequestHeader String ACCESS_TOKEN, @RequestParam String funcDirectory){
		String token = ACCESS_TOKEN;
		String customerId = "C001";//公司编号，此编号实际应用时，应根据token去查询
		return OSSFileUtil.getFilePath(customerId, funcDirectory, key);
	}
} 

