package com.xiangshangban.transit_service.controller;

import com.xiangshangban.transit_service.bean.CheckPendingJoinCompany;
import com.xiangshangban.transit_service.bean.Company;
import com.xiangshangban.transit_service.bean.UserCompanyDefault;
import com.xiangshangban.transit_service.bean.Uusers;
import com.xiangshangban.transit_service.service.CheckPendingJoinCompanyService;
import com.xiangshangban.transit_service.service.CompanyService;
import com.xiangshangban.transit_service.service.UserCompanyService;
import com.xiangshangban.transit_service.service.UusersService;
import com.xiangshangban.transit_service.util.PinYin2Abbreviation;
import org.jboss.logging.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mian on 2017/10/30.
 */
@RestController
@RequestMapping("/registerController")
public class RegisterController {
    Logger logger = Logger.getLogger(RegisterController.class);
    @Autowired
    UusersService uusersService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CheckPendingJoinCompanyService checkPendingJoinCompanyService;

    @Autowired
    UserCompanyService userCompanyService;

//    /***
//     * 焦振/发送验证码
//     * @param phone
//     * @return
//     */
//    @RequestMapping(value = "/temporaryPwd",method = RequestMethod.POST,produces = "application/text;charset=utf-8")
//    public Map<String,Object> temporaryPwd(String phone){
//        Map<String,Object> map = new HashMap<String,Object>();
//        try {
//            //创建初始化短信接口对象
//            YtxSmsUtil yts = new YtxSmsUtil("LTAIcRopzlp5cbUd","VnLMEEXQRukZQSP6bXM6hcNWPlphiP");
//            //给接受的手机号码发送验证短信 并返回验证码
//            String temporaryPwd = yts.sendIdSms(phone);
//
//            //将验证码存入redis缓存中
//            RedisUtil redis = RedisUtil.getInstance();
//            redis.new Hash().hset("u_users"+phone, "temporaryPwd", temporaryPwd);
//            //设置缓存有效时间
//            redis.expire("u_users"+phone, 180);
//
//            System.out.println("=========>  已发送验证短信");
//            map.put("temporaryPwd",temporaryPwd);
//            map.put("returnCode","1000");
//            map.put("message","信息发送成功");
//            return map;
//       }catch(ClientException c){
//            c.printStackTrace();
//            map.put("returnCode","1001");
//            map.put("message","信息发送失败");
//            return map;
//       }catch(Exception e){
//           e.printStackTrace();
//            map.put("returnCode","1002");
//            map.put("message","错误");
//            return map;
//        }
//    }

    /***
     * 焦振/进行用户注册
     *      公司注册
     * @param phone
     * @param userName
     * @param companyName
     * @param type
     * @return
     */
    @Transactional
    @RequestMapping(value = "/registerUsers", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Map<String, Object> registerUsers(String phone, String userName, String companyName, String type) {

        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //全局 公司ID值
        String companyId = "";
        //用户编号
        String userId = "";
        try {
            //从redis中获取之前存入的验证码 判断是否还在有效期
//            RedisUtil redis = RedisUtil.getInstance();
//            String temporaryPwd = redis.new Hash().hget("smsCode_"+phone, "smsCode");
            String temporaryPwd = "123456";

            if (!temporaryPwd.equals(null)) {
                //生成UUID作为用户编号
                userId = UUID.randomUUID().toString();
                //获取系统时间作为用户创建时间
                Date date = new Date(System.currentTimeMillis());
                //创建新增实体
                Uusers uUsers = new Uusers();
                uUsers.setUserid(userId);
                uUsers.setPhone(phone);
                uUsers.setTemporarypwd(temporaryPwd);
                uUsers.setUsername(userName);
                uUsers.setCreateTime(sdf.format(date));
                uUsers.setStatus(Uusers.status_0);

                uusersService.insertSelective(uUsers);
            } else {
                map.put("returnCode", "4001");
                map.put("message", "验证码失效");
                return map;
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            map.put("returnCode", "3001");
            map.put("message", "服务器错误");
            return map;
        }

        if (type.equals("0")) {
            try {
                //根据前台提供注册公司名称查询是否已被注册
                int count = companyService.selectByCompany(companyName);
                if (count > 0) {
                    map.put("returnCode", "4007");
                    map.put("message", "公司名称已被注册");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e);
                map.put("returnCode", "3001");
                map.put("message", "服务器错误");
                return map;
            }
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
                company.setCompany_approve("0");
                company.setUser_name(userName);
                //注册公司名称首字母缩写
                String companyNameLo = "";
                if (companyName.length() >= 4) {
                    //根据公司名称生成前四位字母小写
                    companyNameLo = new PinYin2Abbreviation().cn2py(companyName).substring(0, 4);
                } else {
                    companyNameLo = new PinYin2Abbreviation().cn2py(companyName);
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
                String sDate = sdf1.format(date);
                //模糊查询今天是否有同音公司名称的注册信息
                int num = companyService.selectCompanyNo(sDate + companyNameLo);
                //将查询出来的条数+1存入  以便区别公司编号
                num += 1;
                if (num > 9) {
                    //公司编号
                    company.setCompany_no(sDate + companyNameLo + "0" + num);
                } else {
                    company.setCompany_no(sDate + companyNameLo + "00" + num);
                }
                //对创建公司的信息进行插入操作
                companyService.insertSelective(company);

                //将新创建的公司编号信息存入用户与公司关联表中
                UserCompanyDefault userCompanyKey = new UserCompanyDefault();
                userCompanyKey.setCompanyId(companyId);
                userCompanyKey.setUserId(userId);
                userCompanyKey.setCurrentOption("1");

                userCompanyService.insertSelective(userCompanyKey);

                map.put("returnCode", "3000");
                map.put("message", "数据请求成功");
                return map;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e);
                map.put("returnCode", "3001");
                map.put("message", "服务器错误");
                return map;
            }
        }

        if (type.equals("1")) {
            try {
                //根据前台提供注册公司名称查询是否存在
                int count = companyService.selectByCompany(companyName);
                if (count > 0) {
                    //根据输入公司名称获的公司的编号
                    Company company = companyService.selectByCompanyName(companyName);
                    //加入公司  新增待审核记录
                    CheckPendingJoinCompany checkPendingJoinCompany = new CheckPendingJoinCompany();
                    checkPendingJoinCompany.setUserid(userId);
                    checkPendingJoinCompany.setCompanyid(company.getCompany_id());
                    checkPendingJoinCompany.setStatus("0");
                    checkPendingJoinCompanyService.insertSelective(checkPendingJoinCompany);

                    //将加入的公司编号信息存入用户与公司关联表中
                    UserCompanyDefault userCompanyKey = new UserCompanyDefault();
                    userCompanyKey.setCompanyId(company.getCompany_id());
                    userCompanyKey.setUserId(userId);
                    userCompanyKey.setCurrentOption("1");
                    //审核
                    map.put("returnCode", "3000");
                    map.put("message", "数据请求成功");
                    return map;
                } else {
                    map.put("returnCode", "4006");
                    map.put("message", "加入公司不存在");
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info(e);
                map.put("returnCode", "3001");
                map.put("message", "服务器错误");
                return map;
            }
        }
        map.put("returnCode", "3001");
        map.put("message", "服务器错误");
        return null;
    }

    /***
     * 焦振/查询手机号是否已被注册
     * @param phone
     * @return
     */
    @RequestMapping(value = "/SelectByPhone")
    public Map<String, Object> SelectByPhone(String phone) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //查询条件为手机号，统计用户表中是否有用户使用了这个手机号
            int count = uusersService.SelectCountByPhone(phone);
            //判断手机号是否被注册
            if (count > 0) {
                map.put("status", "1");
                map.put("returnCode", "4005");
                map.put("message", "手机号已被注册");
                return map;
            } else {
                map.put("status", "0");
                map.put("returnCode", "4004");
                map.put("message", "手机号可注册");
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e);
            map.put("returnCode", "3001");
            map.put("message", "服务器错误");
            return map;
        }
    }
}