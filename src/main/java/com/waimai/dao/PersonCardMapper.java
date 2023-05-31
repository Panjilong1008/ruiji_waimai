package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.DishFlavor;
import com.waimai.entity.PersonCard;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PersonCardMapper extends BaseMapper<PersonCard> {
    int deleteByPrimaryKey(String phone);

    int insert(PersonCard record);

    int insertSelective(PersonCard record);

    PersonCard selectByPrimaryKey(String phone);

    int updateByPrimaryKeySelective(PersonCard record);

    int updateByPrimaryKey(PersonCard record);
}