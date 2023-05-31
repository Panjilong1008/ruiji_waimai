package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
    int deleteByPrimaryKey(Long id);

    int insert(SetmealDish record);

    int insertSelective(SetmealDish record);

    SetmealDish selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SetmealDish record);

    int updateByPrimaryKey(SetmealDish record);
}