package com.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.waimai.entity.Category;

public interface CategoryService extends IService<Category> {

    public void remove(Long id);

}
