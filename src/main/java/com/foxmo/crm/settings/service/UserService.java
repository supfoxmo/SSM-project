package com.foxmo.crm.settings.service;

import com.foxmo.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public User queryUserByLoginActAndPwd(Map<String,Object> map);

    public List<User> queryAllUser();
}
