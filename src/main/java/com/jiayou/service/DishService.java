package com.jiayou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiayou.Dto.DishDto;
import com.jiayou.entity.Dish;
import org.springframework.stereotype.Service;

public interface DishService extends IService<Dish> {

    public void saveflavor(DishDto dishDto);

    public DishDto getByIdAndFlavor(long id);

    public void updateAndFlavor(DishDto dishDto);
}
