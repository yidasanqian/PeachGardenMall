package me.zoro.peachgardenmall.datasource;

import android.support.annotation.NonNull;

import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.UserInfo;


/**
 * Main entry point for accessing User Info data.
 * Created by dengfengdecao on 16/10/21.
 */

public interface UserDatasource {

    void fetchCaptcha(String tel, @NonNull GetCaptchaCallback callback);

    void registerNewUser(Map<String, Object> params, @NonNull RegisterUserCallback callback);

    void login(Map<String, String> params, @NonNull LoginCallback callback);

    void getUserInfo(String username, @NonNull GetUserInfoCallback callback);

    void userInfoRevise(Map<String, Object> params, @NonNull UserInfoReviseCallback callback);

    void logout(int userId, @NonNull LogoutCallback callback);

    void forgetPassword(Map<String, Object> params, @NonNull ForgetPasswordCallback callback);

    void changePassword(Map<String, Object> params, @NonNull ChangePasswordCallback callback);

    void changePhone(Map<String, Object> params, @NonNull ChangePhoneCallback callback);

    void changeIdCard(Map<String, Object> params, @NonNull ChangeIdCardCallback callback);

    void fetchUserInfo(int userId, @NonNull GetUserInfoCallback callback);

    interface GetCaptchaCallback {
        /**
         * 成功获取校验码
         *
         * @param captcha 返回的校验码
         */
        void onFetchSuccess(String captcha);

        void onFetchFailure(String errorMsg);
    }

    interface RegisterUserCallback {
        void onRegisterSuccess(String username);

        void onRegisterFailure(String errorMsg);
    }

    interface LoginCallback {
        void onLoginSuccess(UserInfo userInfo, String token);

        void onLoginFailure(String errorMsg);
    }

    interface SaveUserInfoCallback {
        void onUserInfoSaved();

        void onSaveFailure();
    }

    interface GetUserInfoCallback {
        void onUserInfoLoaded(UserInfo userInfo);

        void onDataNotAvailable(String errorMsg);

    }

    interface UserInfoReviseCallback {
        void onUserInfoReviseSuccess();

        void onUserInfoReviseFailure(String errorMsg);
    }

    interface LogoutCallback {

        void onLogout();
    }

    interface ForgetPasswordCallback {
        void onForgetPasswordSuccess();

        void onForgetPasswordFailure(String errorMsg);
    }

    interface ChangePasswordCallback {
        void onChangePasswordSuccess();

        void onChangePasswordFailure(String errorMsg);
    }

    interface ChangePhoneCallback {
        void onChangePhoneSuccess();

        void onChangePhoneFailure(String errorMsg);
    }

    interface ChangeIdCardCallback {
        void onChangeIdCardSuccess();

        void onChangeIdCardFailure(String errorMsg);
    }
}
