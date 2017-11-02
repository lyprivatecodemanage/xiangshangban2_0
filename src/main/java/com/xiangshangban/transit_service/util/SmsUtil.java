package com.xiangshangban.transit_service.util;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.ctc.smscloud.json.JSONHttpClient;

public class SmsUtil {
	
	private static final Log LOG = LogFactory.getLog(SmsUtil.class);
	private static final String SMS_SERVER = "wt.3tong.net";//服务器
	private static final String SMS_ID_CONTENT = "(酬勤登录验证码)此验证码只用于登录你的客户端程序,验证码提供给他人将导致被盗。请勿转发。";							// 短信内容
	private static final String SMS_SIGN = "【酬勤信息】"; // 短信签名
	private static final String SMS_SUBCODE = "85281234"; // 子号码
	
	private  String accessId;
	private  String accessKey;
	private  JSONHttpClient client;
	
	/**
	 * 构造函数
	 * @param accessId
	 * @param accessKey
	 */
	public SmsUtil(String accessId,String accessKey){
		this.accessId = accessId;
		this.accessKey = accessKey;
	}

	/**
	 * 初始化
	 */
	public void initialize(){
		client = JSONHttpClient.getInstance(SMS_SERVER);
	}

	/**
	 * 发送短信
	 * @param String account
	 * @param String password
	 * @param String phone
	 * @return String
	 */
	public String sendSms(String phone,String content) {
		initialize();	
		String result = JSON.parseObject(client.sendSms(accessId, accessKey, phone,content, SMS_SIGN, SMS_SUBCODE)).getString("result");
		if(result.equals("0"))
			return result;
		return null;
	}
	
	/**
	 * 发送验证码短信
	 * @param phone
	 * @return
	 */
	public String sendIdSms(String phone) {
		String sms =  createIDCode();
		String content = sms+SMS_ID_CONTENT;
		if(sendSms(phone,content).equals("0"))
			return sms;
		return null;
	}
	/**
	 * 发送链接到手机
	 * @param phone 手机号
	 * @param linkUrl  链接地址
	 * @return
	 */
	public boolean sendLink(String phone, String linkUrl) {
		String content = "请打开访客预约链接"+linkUrl+"，填写预约信息。";
		if("0".equals(sendSms(phone,content)))
			return true;
		return false;
	}
	/**
	 * 产生验证码
	 * @return
	 */
	private static String createIDCode(){
		Random rand=new Random();
		return String.valueOf(rand.nextInt(9000)+1000);
	}
}
