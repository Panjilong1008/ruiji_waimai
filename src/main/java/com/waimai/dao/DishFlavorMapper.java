package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
    int deleteByPrimaryKey(Long id);

    int insert(DishFlavor record);

    int insertSelective(DishFlavor record);

    DishFlavor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DishFlavor record);

    int updateByPrimaryKey(DishFlavor record);
}