package com.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waimai.dto.SetmealDto;
import com.waimai.entity.Setmeal;

public interface SetMealService extends IService<Setmeal> {
    void saveSetmealDto(SetmealDto setmealDto);
}
