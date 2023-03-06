package com.jiayou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiayou.Dto.SetmealDto;
import com.jiayou.common.BaseContext;
import com.jiayou.common.CustomException;
import com.jiayou.entity.Setmeal;
import com.jiayou.entity.SetmealDish;
import com.jiayou.mapper.SetmealMapper;
import com.jiayou.service.SetmealDishService;
import com.jiayou.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    SetmealDishService setmealDishService;

    public void saveAndDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        Long id = setmealDto.getId();
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        for (SetmealDish s : list){
            s.setSetmealId(id);
        }

        setmealDishService.saveBatch(list);
    }

    @Transactional
    public void removeAndDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId,ids);
        lambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int temp = this.count(lambdaQueryWrapper);
        if(temp > 0){
            throw new CustomException("在售的不能删啊，皇上");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(lambdaQueryWrapper1);
    }
}
