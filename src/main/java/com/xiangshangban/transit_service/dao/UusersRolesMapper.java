package com.xiangshangban.transit_service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.xiangshangban.transit_service.bean.Upermission;
import com.xiangshangban.transit_service.bean.UusersRolesKey;
@Mapper
public interface UusersRolesMapper {
    int deleteByPrimaryKey(UusersRolesKey key);

    int insert(UusersRolesKey record);

    int insertSelective(UusersRolesKey record);
    
	// 查看当前管理员及历史管理员
	UusersRolesKey SelectAdministrator(@Param("companyId") String companyId);
    
	// 修改管理员
    int updateAdministrator(@Param("userId")String userId,@Param("newUserId")String newUserId,@Param("companyId")String companyId,@Param("historyUserIds")String historyUserIds);

	// 根据用户ID查询权限url地址
    List<Upermission> SelectUserIdByPermission(@Param("userId")String userId,@Param("companyId")String companyId);
    
}