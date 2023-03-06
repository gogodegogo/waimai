package com.jiayou.Dto;

import com.jiayou.entity.Setmeal;
import com.jiayou.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
