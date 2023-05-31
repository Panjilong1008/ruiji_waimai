package com.waimai.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.waimai.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBookMapper> {
    int deleteByPrimaryKey(Long id);

    int insert(AddressBook record);

    int insertSelective(AddressBook record);

    AddressBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AddressBook record);

    int updateByPrimaryKey(AddressBook record);
}