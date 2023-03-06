package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiayou.common.BaseContext;
import com.jiayou.common.R;
import com.jiayou.entity.Dish;
import com.jiayou.entity.SetmealDish;
import com.jiayou.entity.ShoppingCart;
import com.jiayou.service.DishService;
import com.jiayou.service.SetmealDishService;
import com.jiayou.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/shoppingCart")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private DishService dishService;

    @PostMapping("/add")
    public R addshopping(HttpSession session, @RequestBody ShoppingCart shoppingCart){
        shoppingCart.setUserId(BaseContext.getid());

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (shoppingCart.getDishId() != null){
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        List<ShoppingCart> ss = shoppingCartService.list(lambdaQueryWrapper);
        if(ss.size() != 0){
            for(ShoppingCart s : ss){
                if(s.equals(shoppingCart) || shoppingCart.getDishId() == null){
                    s.setNumber(s.getNumber()+1);
                    shoppingCartService.updateById(s);
                    return R.success("下单成功");
                }
            }
        }
        shoppingCartService.save(shoppingCart);
        return R.success(ss);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getid());
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getid());
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("全部删了");
    }
}
