package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.DishFlavor;
import com.waimai.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
    int deleteByPrimaryKey(Long id);

    int insert(Setmeal record);

    int insertSelective(Setmeal record);

    Setmeal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Setmeal record);

    int updateByPrimaryKey(Setmeal record);
}