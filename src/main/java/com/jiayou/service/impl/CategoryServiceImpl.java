package com.jiayou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiayou.common.CustomException;
import com.jiayou.common.R;
import com.jiayou.entity.Category;
import com.jiayou.entity.Dish;
import com.jiayou.entity.Setmeal;
import com.jiayou.mapper.CategoryMapper;
import com.jiayou.service.CategoryService;
import com.jiayou.service.DishService;
import com.jiayou.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;

    @Override
    public void remove(long id) {
        int count1 = 0;
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id);
        count1 = dishService.count(lambdaQueryWrapper);
        if(count1 > 0){
            throw new CustomException("跟菜品关联了");
        }
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1  = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId,id);
        count1 = setmealService.count(lambdaQueryWrapper1);
        if(count1 > 0){
            throw new CustomException("跟套餐关联了");
        }
    }
}
