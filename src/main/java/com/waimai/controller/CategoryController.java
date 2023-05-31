package com.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waimai.common.R;
import com.waimai.entity.Category;
import com.waimai.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PutMapping("/add")
    public R<String> addCategory(@RequestBody Category category){
        log.info("新增category:{}",category.toString());
        categoryService.save(category);
        return R.success("添加成功!");
    }

    @GetMapping("/page")
    public R<Page> getPage(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo=new Page<Category>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>();

        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    @DeleteMapping("/delete")
    public R<String> deleteCategory(Long id){
        log.info("删除category的id:{}",id);
        categoryService.remove(id);
        return R.success("删除成功!");
    }

    @PostMapping("/alter")
    public R<String> alterCategory(@RequestBody Category category){
        log.info("修改category:{}",category.toString());
        categoryService.updateById(category);
        return R.success("修改成功!");
    }

    @GetMapping("/list")
    //在get请求中不能使用@RequestBody，否则会让后台函数接收不到参数！！！！！！！！
    public R<List<Category>> getList(Category category){
        log.info("category:{}",category.getType());
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
