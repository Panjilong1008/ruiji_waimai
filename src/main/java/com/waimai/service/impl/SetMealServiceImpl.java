package com.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.waimai.dao.SetmealMapper;
import com.waimai.dto.SetmealDto;
import com.waimai.entity.Setmeal;
import com.waimai.entity.SetmealDish;
import com.waimai.service.SetMealService;
import com.waimai.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SetMealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetMealService {
    @Autowired
    SetmealDishService setmealDishService;
    @Override
    public void saveSetmealDto(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes=setmealDto.getSetmealDishes();
        for(SetmealDish setmealDish:setmealDishes){
            setmealDish.setSetmealId(setmealDto.getId());
            setmealDish.setSort(0);
        }
        setmealDishService.saveBatch(setmealDishes);

    }
}
