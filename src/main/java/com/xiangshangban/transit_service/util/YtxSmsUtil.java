package com.xiangshangban.transit_service.util;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class YtxSmsUtil {
	private static final Log LOG = LogFactory.getLog(YtxSmsUtil.class);  
	//产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private String accessKeyId;
    private String accessKeySecret;
    
    //验证码
    private String verifyCode;
    
    //构造器
    public YtxSmsUtil(String accessId,String accessKey){
    	
    	this.accessKeyId = accessId;
    	this.accessKeySecret = accessKey;
    	
    }

    public String sendIdSms(String phone) throws ClientException {
    	String ossEnvironment = "test";
    	try {
			ossEnvironment =PropertiesUtils.ossProperty("ossEnvironment");
		} catch (IOException e) {
			LOG.info("获取环境属性错误");
			LOG.info(e);
		}
    	if("test".equals(ossEnvironment)){
    		return "6666";
    	}
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);//13636412139
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("享上班");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_78285013");
        //产生验证码
        Random random=new Random();
		verifyCode =  String.valueOf(random.nextInt(9000)+1000);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\""+verifyCode+"\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

//      return sendSmsResponse;
        return verifyCode;
    }
}
