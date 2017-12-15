package com.xiangshangban.transit_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiangshangban.transit_service.bean.Department;
import com.xiangshangban.transit_service.dao.DepartmentMapper;



@Service("departmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	DepartmentMapper departmentMapper;
	
	@Override
	public int insertDepartment(Department department) {
		// TODO Auto-generated method stub
		return departmentMapper.insertDepartment(department);
	}
  
}