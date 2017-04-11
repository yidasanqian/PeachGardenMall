package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;

/**
 * 用户信息
 * Created by dengfengdecao on 17/4/7.
 */

public class UserInfo implements Serializable {

    private String username;
    private String password;

    private String nickname;
    private String avatarUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
