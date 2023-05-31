package com.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waimai.entity.Employee;

public interface EmployeeService extends IService<Employee> {
    Employee getOneEmployeeByPhoneAndPwd(Employee employee);
    int addMember(Employee employee);
}

