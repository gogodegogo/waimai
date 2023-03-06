package com.jiayou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiayou.Dto.SetmealDto;
import com.jiayou.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveAndDish(SetmealDto setmealDto);

    public void removeAndDish(List<Long> ids);
}
