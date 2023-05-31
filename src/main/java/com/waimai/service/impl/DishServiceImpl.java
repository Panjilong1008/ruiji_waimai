package com.waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waimai.dao.DishMapper;
import com.waimai.dto.DishDto;
import com.waimai.entity.Dish;
import com.waimai.entity.DishFlavor;
import com.waimai.service.DishFlavorService;
import com.waimai.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;

    @Override
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        Long dishID=dishDto.getId();
        for(DishFlavor flavor:dishDto.getFlavors()){
            flavor.setDishId(dishID);
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());
    }

    @Override
    public DishDto getDishDtoByID(Long id) {
        Dish dish=this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(id!=null,DishFlavor::getDishId,id);
        List<DishFlavor> flavorList=dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavorList);
        return dishDto;
    }

    @Override
    public void updateDishDto(DishDto dishDto) {
        //这里口味的修改不仅是内容的修改，还可能新增和删除了一些口味，所以要先删除原来的口味信息，再插入修改后的口味信息。
        this.updateById(dishDto);
        List<DishFlavor> flavorList=dishDto.getFlavors();
        Long dishID=dishDto.getId();
        for(DishFlavor dishFlavor:flavorList){
            dishFlavor.setDishId(dishID);
        }
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(dishDto.getId()!=null,DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        dishFlavorService.saveBatch(flavorList);
    }
}
