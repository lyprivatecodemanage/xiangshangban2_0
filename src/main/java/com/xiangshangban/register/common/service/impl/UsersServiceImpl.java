package com.xiangshangban.register.common.service.impl;

import com.xiangshangban.register.common.bean.Users;
import com.xiangshangban.register.common.dao.UsersMapper;
import com.xiangshangban.register.common.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mian on 2017/10/23.
 */
@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    UsersMapper usersMapper;

    @Override
    public int deleteByPrimaryKey(String userid) {
        return usersMapper.deleteByPrimaryKey(userid);
    }

    @Override
    public int insert(Users record) {
        return usersMapper.insert(record);
    }

    @Override
    public int insertSelective(Users record) {
        return usersMapper.insertSelective(record);
    }

    @Override
    public Users selectByPrimaryKey(String userid) {
        return usersMapper.selectByPrimaryKey(userid);
    }

    @Override
    public int updateByPrimaryKeySelective(Users record) {
        return usersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Users record) {
        return usersMapper.updateByPrimaryKey(record);
    }

    @Override
    public Users login(String phone, String temporarypwd) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("phone",phone);
        map.put("temporarypwd",temporarypwd);
        Users users = usersMapper.login(map);
        return users;
    }
}
