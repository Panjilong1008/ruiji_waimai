package com.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waimai.common.R;
import com.waimai.dto.DishDto;
import com.waimai.dto.SetmealDto;
import com.waimai.entity.Category;
import com.waimai.entity.Setmeal;
import com.waimai.entity.SetmealDish;
import com.waimai.service.CategoryService;
import com.waimai.service.SetMealService;
import com.waimai.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    SetMealService setMealService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SetmealDishService setmealDishService;

    @PostMapping("/addSetmeal")
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto){
        setMealService.saveSetmealDto(setmealDto);
        return R.success("添加成功");
    }
    @GetMapping("/getSetmealPage")
    public R<Page> page(int page,int pageSize,String name){
        log.info("套餐分页，page:{},PageSize:{}",page,pageSize);
        Page<Setmeal> setmealPage=new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name!=null,Setmeal::getName,name);
        setMealService.page(setmealPage,setmealLambdaQueryWrapper);
        Page<SetmealDto> setmealDtoPage=new Page<>(page,pageSize);
        BeanUtils.copyProperties(setmealPage,setmealDtoPage,"records");
        List<Setmeal> setmealList=setmealPage.getRecords();
        List<SetmealDto> setmealDtoList= setmealList.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();

            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtoList);


        return R.success(setmealDtoPage);
    }

    @DeleteMapping("/deleteSetmeals")
    public R<String> deleteSetmeals( String ids){
        List<String> idList= Arrays.asList(ids.split(","));
        for(String id:idList){
            Setmeal setmeal=setMealService.getById(id);
            if(setmeal.getStatus()==0){
                setMealService.removeById(id);
                LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper=new LambdaQueryWrapper<>();
                setmealDishLambdaQueryWrapper.eq(id!=null,SetmealDish::getSetmealId,id);
                setmealDishService.remove(setmealDishLambdaQueryWrapper);
            }else continue;
        }
        return R.success("删除成功");
    }

    @PutMapping("/changeStatus/0")
    public R<String> changeStatus(@RequestParam List<String> ids){
        for(String id:ids){
            Setmeal setmeal=new Setmeal();
            setmeal.setId(Long.parseLong(id));
            setmeal.setStatus(0);
            setMealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }
    @PutMapping("/changeStatus/1")
    public R<String> changeStatus1(@RequestParam List<String> ids){
        for(String id:ids){
            Setmeal setmeal=new Setmeal();
            setmeal.setId(Long.parseLong(id));
            setmeal.setStatus(1);
            setMealService.updateById(setmeal);
        }
        return R.success("修改成功");
    }
}
