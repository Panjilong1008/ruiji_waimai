package com.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.waimai.common.BaseContext;
import com.waimai.common.R;
import com.waimai.entity.Employee;
import com.waimai.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import java.util.Date;
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;


    /*
    * 商家登录
    * */
    @PostMapping("/login")

    public  R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){//一定要加@RequestBody要不然接收不到数据
        String phone=employee.getPhone();
        String password=employee.getPassword();
        //MD5加密
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        employee.setPassword(password);


        Employee employee1=employeeService.getOneEmployeeByPhoneAndPwd(employee);

        if(employee1==null){

            return R.error("登陆失败");
        }
        request.getSession().setAttribute("employee",employee1.getId());
        return R.success(employee1);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功!");
    }

    @PutMapping("/addMember")
    public R<String> addMember(@RequestBody Employee employee,HttpServletRequest request){
        String password=employee.getIdNumber().substring(employee.getIdNumber().length()-7,employee.getIdNumber().length()-1);
        employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        Date date = new Date();
//        代码在公共字段填充中实现
//        employee.setCreateTime(date);
//        employee.setUpdateTime(date);
//        employee.setCreateUser((Long)request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long)request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("添加成功!");
    }

    @GetMapping("/getMemberList")
    public R<Page> getMemberPage(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize,name);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

//  这里前端直接传来了要修改的值。直接update就行
    @PostMapping("/changeEmployeeInfo")
    public R<String> changeEmployeeInfo(@RequestBody Employee employee,HttpServletRequest request){
        log.info("employee={}",employee.toString());
        Long adminId=(Long)request.getSession().getAttribute("employee");
        Date date=new Date();
//        代码在公共字段填充中实现
//        employee.setUpdateTime(date);
//        employee.setUpdateUser(adminId);
        employeeService.updateById(employee);
        return R.success("修改成功!");
    }
//@PathVariable指明id为路径变量
    @GetMapping("/getEmployeeInfo/{id}")
    public R<Employee> getEmployeeInfo(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        Employee employee=employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }else {
            return R.error("查询失败!");
        }
    }
}
