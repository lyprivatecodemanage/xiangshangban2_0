package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.Company;

import java.util.List;

/**
 * Created by mian on 2017/10/31.
 */
public interface CompanyService {
    int deleteByPrimaryKey(String company_id);

    int insert(Company record);

    Company selectByPrimaryKey(String company_id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    //判断公司名是否重复(是否有这个公司)
    int selectByCompany(String companyName);

    //创建公司(填写公司名称，存入员工表编号关联)
    int insertSelective(Company record);

    //模糊查询公司编号前缀是否有一致的
    int selectCompanyNo(String companyNo);
}
