package com.yun.user.service;

import com.yun.user.dao.UserInfoDao;
import com.yun.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    public UserInfo findUserInfoByNameAndPass(String loginName,String loginPass){
        return userInfoDao.findUserInfoByNameAndPass(loginName,loginPass);
    }

    public List<UserInfo> findUserInfoList() {
        return userInfoDao.findUserInfoList();
    }

    public void insertUserInfo(UserInfo userInfo) {
        userInfoDao.insertUserInfo(userInfo);
    }

    public void deleteUserInfo(int userId) {
        userInfoDao.deleteUserInfo(userId);
    }
}
