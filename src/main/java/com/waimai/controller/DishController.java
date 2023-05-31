package com.waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.waimai.common.R;
import com.waimai.dto.DishDto;
import com.waimai.entity.Category;
import com.waimai.entity.Dish;
import com.waimai.entity.DishFlavor;
import com.waimai.service.CategoryService;
import com.waimai.service.DishFlavorService;
import com.waimai.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishFlavorService dishFlavorService;

    @PostMapping("/add")
    public R<String> addDish(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");

    }
    //联表分页查询
    @GetMapping("page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> dishPageInfo=new Page<Dish>(page,pageSize);
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<Dish>();
        queryWrapper.like(name!=null,Dish::getName,name);
        queryWrapper.orderByDesc(Dish::getSort);
        dishService.page(dishPageInfo,queryWrapper);

        LambdaQueryWrapper<DishDto> queryWrapper1=new LambdaQueryWrapper<DishDto>();
        Page<DishDto> dishDtoPageInfo=new Page<DishDto>(page,pageSize);
        //对象拷贝(将父类的值拷贝给子类)
        BeanUtils.copyProperties(dishPageInfo,dishDtoPageInfo,"records");
        List<Dish> records = dishPageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPageInfo.setRecords(list);

        return R.success(dishDtoPageInfo);
    }

    @GetMapping("/{id}")
    public R<DishDto> getDishDtoByID(@PathVariable Long id){
        DishDto dishDto=dishService.getDishDtoByID(id);
        return R.success(dishDto);
    }

    @PutMapping("/updateDishInfo")
    public R<String> updateDishInfo(@RequestBody DishDto dishDto){
        dishService.updateDishDto(dishDto);
        return R.success("修改成功");
    }

    @GetMapping("/getDishListByType")
    public R<List<Dish>> getDishListByType(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        queryWrapper.orderByAsc(Dish::getSort);
        List<Dish> dishList=dishService.list(queryWrapper);
        return R.success(dishList);
    }

    @DeleteMapping("/deleteDishes")
    public R<String> deleteDishes(String ids){
        List<String> idList= Arrays.asList(ids.split(","));
        for(String id:idList){
            Dish dish=dishService.getById(id);
            if(dish.getStatus()==0){
                dishService.removeById(id);
                LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper=new LambdaQueryWrapper<>();
                dishFlavorLambdaQueryWrapper.eq(id!=null,DishFlavor::getDishId,id);
                dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
            }
        }
        return R.success("删除成功!");
    }

    @PutMapping("/changeStatus/0")
    public R<String> changeStatus(@RequestParam List<String> ids){
        for(String id:ids){
            Dish dish=new Dish();
            dish.setId(Long.parseLong(id));
            dish.setStatus(0);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }
    @PutMapping("/changeStatus/1")
    public R<String> changeStatus1(@RequestParam List<String> ids){
        for(String id:ids){
            Dish dish=new Dish();
            dish.setId(Long.parseLong(id));
            dish.setStatus(1);
            dishService.updateById(dish);
        }
        return R.success("修改成功");
    }


}
