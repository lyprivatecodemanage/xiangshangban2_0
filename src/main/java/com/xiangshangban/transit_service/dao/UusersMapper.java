package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.PersonalInformationVerification;
import com.xiangshangban.transit_service.bean.Uusers;
@Mapper
public interface UusersMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Uusers record);

    Uusers selectByPrimaryKey(String userId);
    
    Uusers selectByPhone(String phone);
    
    List<String> selectRoles(String phone);
    
    Uusers selectByAccount(String account);
    
    Uusers selectApprovalPerson(String companyId);
    
    int selectIdentityAuthentication(@Param("phone") String phone,@Param("userName") String userName,@Param("companyName") String companyName);
    
    List<PersonalInformationVerification> selectPersonalInformationVerification(@Param("phone")String phone,@Param("userName")String userName,@Param("postName")String postName);
    
    int updateByPrimaryKey(Uusers record);
    
    int updateSmsCode(@Param("phone")String phone,@Param("temporarypwd")String smsCode);

    List<Uusers> selectListsByPhone(String phone);
    
    Uusers selectCompanyByToken(String token);

    Uusers selectCompanyBySessionId(String sessionId);

    //注册时检查手机号是否已被注册
    int SelectCountByPhone(String phone);

    //修改用户状态(当注册为加入公司情况时，待审核加入情况用户为不可用，加入后需对用户账号状态进行修改)
    int updateByPrimaryKeySelective(Uusers record);

    //注册用户（生成UUID为主键、存入手机号、姓名、盐值、以及默认为‘不可用’的初始状态）
    int insertSelective(Uusers record);
}