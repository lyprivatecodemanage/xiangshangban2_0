package com.xiangshangban.transit_service.controller;

import com.aliyuncs.exceptions.ClientException;
import com.xiangshangban.transit_service.bean.Company;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.dao.UusersMapper;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.util.PinYin2Abbreviation;
import com.xiangshangban.transit_service.util.RedisUtil;
import com.xiangshangban.transit_service.util.YtxSmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by mian on 2017/10/30.
 */
@RestController
@RequestMapping("/registerController")
public class RegisterController{

    @Autowired
    UusersMapper uusersMapper;

    @Autowired
    CompanyService companyService;

    /***
     * 发送验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "/temporaryPwd",method = RequestMethod.POST,produces = "application/text;charset=utf-8")
    public Map<String,Object> temporaryPwd(String phone){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            //创建初始化短信接口对象
            YtxSmsUtil yts = new YtxSmsUtil("LTAIcRopzlp5cbUd","VnLMEEXQRukZQSP6bXM6hcNWPlphiP");
            //给接受的手机号码发送验证短信 并返回验证码
            String temporaryPwd = yts.sendIdSms(phone);

            //将验证码存入redis缓存中
            RedisUtil redis = RedisUtil.getInstance();
            redis.new Hash().hset("u_users"+phone, "temporaryPwd", temporaryPwd);
            //设置缓存有效时间
            redis.expire("u_users"+phone, 180);

            System.out.println("=========>  已发送验证短信");
            map.put("temporaryPwd",temporaryPwd);
            map.put("returnCode","1000");
            map.put("message","信息发送成功");
            return map;
       }catch(ClientException c){
            c.printStackTrace();
            map.put("returnCode","1001");
            map.put("message","信息发送失败");
            return map;
       }catch(Exception e){
           e.printStackTrace();
            map.put("returnCode","1002");
            map.put("message","错误");
            return map;
        }
    }

    /***
     * 进行用户注册
     * 公司注册
     * @param phone
     * @param userName
     * @param companyName
     * @param type
     * @return
     */
    @RequestMapping(value = "/registerUsers",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String,Object> registerUsers(String phone,String userName,String companyName,String type){
        Map<String,Object> map = new HashMap<String,Object>();
        //全局 公司ID值
        String companyId = "";

        try{
            //根据前台提供注册公司名称查询是否已被注册
            int count = companyService.selectByCompany(companyName);
            if(count>0){
                map.put("returnCode","1022");
                map.put("message","公司名称已被注册");
                return map;
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("returnCode","1021");
            map.put("message","失败");
            return map;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            //生成公司编号
            companyId = UUID.randomUUID().toString();
            //生成公司创建时间
            Date date = new Date(System.currentTimeMillis());
            //创建新增公司对象
            Company company = new Company();
            company.setCompany_id(companyId);
            company.setCompany_name(companyName);
            company.setCompany_creat_time(sdf.format(date));
            company.setUser_name(userName);

            String companyNameLo = "";
            if(companyName.length()>=4){
                //根据公司名称生成前四位字母小写
                companyNameLo = new PinYin2Abbreviation().cn2py(companyName).substring(0,4);
            }else{
                companyNameLo = new PinYin2Abbreviation().cn2py(companyName);
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            String sDate = sdf.format(date);
            //模糊查询今天是否有同音公司名称的注册信息
            int num = companyService.selectCompanyNo(sDate+companyNameLo);
            //将查询出来的条数+1存入  以便区别公司编号
            if(num>9){
                company.setCompany_no(sDate+companyNameLo+"0"+num);
            }else{
                company.setCompany_no(sDate+companyNameLo+"00"+num);
            }
            //对创建公司的信息进行插入操作
            companyService.insertSelective(company);
        }catch(Exception e){
            map.put("returnCode","1023");
            map.put("message","创建公司失败");
            return map;
        }

        try {
            //从redis中获取之前存入的验证码 判断是否还在有效期
            RedisUtil redis = RedisUtil.getInstance();
            String temporaryPwd = redis.new Hash().hget("u_users"+phone, "temporaryPwd");

            if(!temporaryPwd.equals(null)){
                //生成UUID作为用户编号
                String userId = UUID.randomUUID().toString();
                //获取系统时间作为用户创建时间
                Date date = new Date(System.currentTimeMillis());
                //创建新增实体
                Uusers uUsers = new Uusers();
                uUsers.setUserid(userId);
                uUsers.setPhone(phone);
                uUsers.setTemporarypwd(temporaryPwd);
                uUsers.setUsername(userName);
                uUsers.setCreateTime(sdf.format(date));
                //存入之前创建公司的编号
                uUsers.setCompanyId(companyId);
                uUsers.setStatus(Uusers.status_1);

                uusersMapper.insertSelective(uUsers);

                map.put("returnCode","1030");
                map.put("message","注册成功");
                return map;
            }else{
                map.put("returnCode","1032");
                map.put("message","验证码失效");
                return map;
            }

        }catch(Exception e){
            map.put("returnCode","1031");
            map.put("message","注册失败");
            return map;
        }
    }

    /***
     * 查询手机号是否已被注册
     * @param phone
     * @return
     */
    @RequestMapping(value = "/SelectByPhone",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public Map<String,Object> SelectByPhone(String phone){
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            //查询条件为手机号，统计用户表中是否有用户使用了这个手机号
            int count = uusersMapper.SelecByPhone(phone);
            //判断手机号是否被注册
            if(count>0){
                map.put("returnCode","1012");
                map.put("message","手机号已被注册");
                return map;
            }else{
                map.put("returnCode","1010");
                map.put("message","手机号可注册");
                return map;
            }
        }catch(Exception e){
            e.printStackTrace();
            map.put("returnCode","1011");
            map.put("message","错误");
            return map;
        }
    }
}