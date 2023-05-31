package com.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waimai.entity.Employee;
import com.waimai.dao.EmployeeMapper;
import com.waimai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    @Override
    public Employee getOneEmployeeByPhoneAndPwd(Employee employee) {

        return employeeMapper.selectSelective(employee);
    }

    @Override
    public int addMember(Employee employee) {
        return employeeMapper.insertSelective(employee);
    }
}
