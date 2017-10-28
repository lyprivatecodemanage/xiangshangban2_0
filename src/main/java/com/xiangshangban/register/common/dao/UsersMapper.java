package com.xiangshangban.register.common.dao;

import com.xiangshangban.register.common.bean.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UsersMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);
    //登录
    Users login(Map<String,Object> map);
    //注册
    Users register(String phone);
    //更换手机号码
    int updateByPhone(@Param("userid") String userid,@Param("phone") String phone);
}