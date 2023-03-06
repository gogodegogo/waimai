package com.jiayou;

import com.jiayou.common.R;
import com.jiayou.entity.TestEntity;
import com.jiayou.entity.User;
import com.jiayou.service.CategoryService;
import com.jiayou.service.TestService;
import com.jiayou.utils.SMSUtils;
import com.jiayou.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = llApplication.class)//作用：声明当前类是springboot的测试类并且获取入口类上的相关信息 NtWebdcApplication是入口类类名
@Controller
public class Test {
    @Resource
    private TestService testService;


    @Autowired
    private CategoryService service;

    @org.junit.Test
    public void aa() {
        TestEntity testEntity = new TestEntity();
        testEntity.setNoid(1);
        testService.save(testEntity);
    }


    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            log.info("发信息了");
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            SMSUtils.sendMessage("外卖外卖","",phone,code);
            log.info(code);
            session.setAttribute(phone,code);
            R.success("验证码发送成功");
        }
        return R.error("验证码发送失败");
    }
}