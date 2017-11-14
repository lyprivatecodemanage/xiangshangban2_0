package com.xiangshangban.transit_service.service;

import com.xiangshangban.transit_service.bean.Company;
import com.xiangshangban.transit_service.dao.CompanyMapper;
import com.xiangshangban.transit_service.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mian on 2017/10/31.
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyMapper companyMapper;

    @Override
    public int deleteByPrimaryKey(String company_id) {
        return companyMapper.deleteByPrimaryKey(company_id);
    }

    @Override
    public int insert(Company record) {
        return companyMapper.insert(record);
    }

    @Override
    public Company selectByPrimaryKey(String company_id) {
        return companyMapper.selectByPrimaryKey(company_id);
    }

    @Override
    public int updateByPrimaryKeySelective(Company record) {
        return companyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Company record) {
        return companyMapper.updateByPrimaryKey(record);
    }

    @Override
    public int selectByCompany(String company_no) {
        return companyMapper.selectByCompany(company_no);
    }

    @Override
    public Company selectByCompanyName(String company_no) {
        return companyMapper.selectByCompanyName(company_no);
    }

    @Override
    public int insertSelective(Company record) {
        return companyMapper.insertSelective(record);
    }

    @Override
    public int selectCompanyNo(String companyNo) {
        return companyMapper.selectCompanyNo(companyNo);
    }
}