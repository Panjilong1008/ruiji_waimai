package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    Employee selectByPhoneAndPwd(Employee record);

    Employee selectSelective(Employee record);

//    一对一通过嵌套查询方式
    Employee selectIdCardByPhone(String phone);
//    一对一通过嵌套结果方式
    Employee selectIdCardByPhone1(String phone);
}