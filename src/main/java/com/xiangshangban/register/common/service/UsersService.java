package com.xiangshangban.register.common.service;

import com.xiangshangban.register.common.bean.Users;

public interface UsersService {
    int deleteByPrimaryKey(String userid);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Users login(String phone,String temporarypwd);
}