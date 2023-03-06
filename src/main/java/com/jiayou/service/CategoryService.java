package com.jiayou.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jiayou.common.R;
import com.jiayou.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(long id);
}
