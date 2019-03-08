package com.yun.user.dao;

import com.yun.user.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoDao {

    @Results(id = "userInfo",value = {
            @Result(column = "user_id",property = "userId"),
            @Result(column = "login_name",property = "loginName"),
            @Result(column = "login_pass",property = "loginPass"),
            @Result(column = "user_name",property = "userName")
    })
    @Select("select * from user_info")
    List<UserInfo> findUserInfoList();

    @Select({"select * from user_info where login_name=#{loginName} and login_pass=#{loginPass}"})
    @ResultMap("userInfo")
    UserInfo findUserInfoByNameAndPass(@Param("loginName") String loginName, @Param("loginPass") String loginPass);

    @Insert("insert into user_info(login_name,login_pass,user_name) values(#{loginName},#{loginPass},#{userName})")
    void insertUserInfo(UserInfo userInfo);

    @Delete("delete from user_info where user_id=#{userId}")
    void deleteUserInfo(int userId);
}
