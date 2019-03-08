package com.yun.user.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {

    private int userId;

    private String loginName;

    private String loginPass;

    private String userName;

}
