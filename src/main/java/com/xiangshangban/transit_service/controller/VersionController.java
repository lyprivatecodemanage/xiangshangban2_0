package com.xiangshangban.transit_service.controller;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.xiangshangban.transit_service.bean.Version;
import com.xiangshangban.transit_service.util.PropertiesUtils;

@Controller
@RequestMapping(value="/x/version")
public class VersionController {
	private static final Log LOG = LogFactory.getLog(VersionController.class);
	@RequestMapping(value="/getVersion.shtml",produces = "application/json;charset=UTF-8",method=RequestMethod.GET)
	@ResponseBody
	public String getVersion(@RequestParam(value="appType")String appType){	
		Version version = new Version();
		try {
			if("test".equals(PropertiesUtils.ossProperty("ossEnvironment"))){
				appType=appType+".test";
			}
			version.setVersionCode(PropertiesUtils.versionProperty("version."+appType+".code"));
			version.setVersionName(PropertiesUtils.versionProperty("version."+appType+".name"));
			version.setVersionAddress(PropertiesUtils.versionProperty("version."+appType+".address"));
		} catch (IOException e) {
			LOG.info("获取版本号错误");
			e.printStackTrace();
		}
		return JSON.toJSONString(version);
	}
}
