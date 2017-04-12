package me.zoro.peachgardenmall.datasource.domain;

import java.io.Serializable;

/**
 * Created by dengfengdecao on 17/4/12.
 */

public class Comment implements Serializable {
    private static final long serialVersionUID = -5714148449617140764L;

    private String content;

    private String time;

    private String avatarUrl;

    private String nickname;

    private String identityUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdentityUrl() {
        return identityUrl;
    }

    public void setIdentityUrl(String identityUrl) {
        this.identityUrl = identityUrl;
    }
}
