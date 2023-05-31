package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.Dish;
import com.waimai.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    int deleteByPrimaryKey(Long id);

    int insert(Dish record);

    int insertSelective(Dish record);

    Dish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKey(Dish record);
}