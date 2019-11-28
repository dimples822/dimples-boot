package com.dimples.biz.system.service.impl;

import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public void insertSelective(User user) {
        user.setCreateTime(new Date());
        //然后直接调用存储方法
    }
}













