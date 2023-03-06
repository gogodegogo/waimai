package com.jiayou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiayou.common.R;
import com.jiayou.entity.User;
import com.jiayou.service.UserService;
import com.jiayou.utils.SMSUtils;
import com.jiayou.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(@RequestBody Map map, HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        log.info("phone="+phone+"    code="+code);
        String attribute = (String) session.getAttribute(phone);
        log.info("拿到的验证码"+attribute);
        if(!code.equals(attribute)){
            return R.error("验证码失效");
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,phone);
        User user = userService.getOne(lambdaQueryWrapper);
        if(user == null){
            user = new User();
            user.setPhone(phone);
            userService.save(user);
        }
        session.setAttribute("user",user.getId());
        return R.success("登入成功");
    }

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            log.info("发信息了");
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            SMSUtils.sendMessage("外卖外卖","SMS_270215279",phone,code);
            log.info(code);
            session.setAttribute("phone",phone);
            session.setAttribute(phone,code);
            R.success("验证码发送成功");
        }
        return R.error("验证码发送失败");
    }
}
