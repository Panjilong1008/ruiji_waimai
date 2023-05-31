package com.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waimai.dto.DishDto;
import com.waimai.entity.Dish;

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getDishDtoByID(Long id);
    void updateDishDto(DishDto dishDto);
}
