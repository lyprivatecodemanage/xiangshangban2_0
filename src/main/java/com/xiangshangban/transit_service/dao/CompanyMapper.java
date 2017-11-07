package com.xiangshangban.transit_service.dao;

import com.xiangshangban.transit_service.bean.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(String company_id);

    int insert(Company record);

    Company selectByPrimaryKey(String company_id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    //判断公司名是否重复(是否有这个公司)
    int selectByCompany(String companyName);

    //根据公司名称获得公司信息
    Company selectByCompanyName(String companyName);

    //创建公司(填写公司名称，存入员工表编号关联)
    int insertSelective(Company record);

    //模糊查询公司编号前缀是否有一致的
    int selectCompanyNo(@Param("company_no") String company_no);
}