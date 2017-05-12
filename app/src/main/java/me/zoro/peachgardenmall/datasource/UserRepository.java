package me.zoro.peachgardenmall.datasource;


import android.support.annotation.NonNull;

import java.util.Map;

import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import me.zoro.peachgardenmall.utils.CacheManager;

/**
 * 从数据源加载用户数据到缓存
 * Created by dengfengdecao on 16/11/11.
 */

public class UserRepository implements UserDatasource {

    public static final String TAG = "UserRepository";

    private static UserRepository sRepository;


    private UserDatasource mRemoteDatasource;

    public void setDirty(boolean dirty) {
        mIsDirty = dirty;
    }

    /**
     * true,表示缓存存在过期数据，false，表示缓存数据是最新的
     */
    private boolean mIsDirty = true;

    // 防止被直接实例化
    private UserRepository(UserDatasource remoteDatasource) {
        mRemoteDatasource = remoteDatasource;
    }

    public static UserRepository getInstance(UserDatasource remoteDatasource) {
        if (sRepository == null) {
            sRepository = new UserRepository(remoteDatasource);
        }

        return sRepository;
    }


    public void fetchCaptcha(String tel, @NonNull final GetCaptchaCallback callback) {
        mRemoteDatasource.fetchCaptcha(tel, new GetCaptchaCallback() {
            @Override
            public void onFetchSuccess(String msg) {
                callback.onFetchSuccess(msg);
            }

            @Override
            public void onFetchFailure(String msg) {
                callback.onFetchFailure(msg);
            }
        });
    }

    @Override
    public void registerNewUser(Map<String, Object> params, @NonNull final RegisterUserCallback callback) {
        mRemoteDatasource.registerNewUser(params, new RegisterUserCallback() {
            @Override
            public void onRegisterSuccess(String username) {
                callback.onRegisterSuccess(username);
            }

            @Override
            public void onRegisterFailure(String message) {
                callback.onRegisterFailure(message);
            }
        });
    }

    @Override
    public void login(Map<String, String> params, @NonNull final LoginCallback callback) {
        mRemoteDatasource.login(params, new LoginCallback() {
            @Override
            public void onLoginSuccess(UserInfo userInfo, String token) {
                callback.onLoginSuccess(userInfo, token);
            }

            @Override
            public void onLoginFailure(String errorMsg) {
                callback.onLoginFailure(errorMsg);
            }
        });
    }


    @Override
    public void getUserInfo(String username, @NonNull final GetUserInfoCallback callback) {
        mRemoteDatasource.getUserInfo(username, new GetUserInfoCallback() {
            @Override
            public void onUserInfoLoaded(UserInfo userInfo) {
                callback.onUserInfoLoaded(userInfo);
            }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });
    }

    @Override
    public void userInfoRevise(Map<String, Object> params, @NonNull final UserInfoReviseCallback callback) {
        mRemoteDatasource.userInfoRevise(params, new UserInfoReviseCallback() {
            @Override
            public void onUserInfoReviseSuccess() {
                callback.onUserInfoReviseSuccess();
            }

            @Override
            public void onUserInfoReviseFailure(String errorMsg) {
                callback.onUserInfoReviseFailure(errorMsg);
            }
        });
    }

    @Override
    public void logout(int userId, @NonNull final LogoutCallback callback) {
        mRemoteDatasource.logout(userId, new LogoutCallback() {

            @Override
            public void onLogout() {
                callback.onLogout();
            }
        });
    }

    @Override
    public void forgetPassword(Map<String, Object> params, @NonNull final ForgetPasswordCallback callback) {
        mRemoteDatasource.forgetPassword(params, new ForgetPasswordCallback() {
            @Override
            public void onForgetPasswordSuccess() {
                callback.onForgetPasswordSuccess();
            }

            @Override
            public void onForgetPasswordFailure(String errorMsg) {
                callback.onForgetPasswordFailure(errorMsg);
            }
        });
    }

    @Override
    public void changePassword(Map<String, Object> params, @NonNull final ChangePasswordCallback callback) {
        mRemoteDatasource.changePassword(params, new ChangePasswordCallback() {
            @Override
            public void onChangePasswordSuccess() {
                callback.onChangePasswordSuccess();
            }

            @Override
            public void onChangePasswordFailure(String errorMsg) {
                callback.onChangePasswordFailure(errorMsg);
            }
        });
    }

    @Override
    public void changePhone(Map<String, Object> params, @NonNull final ChangePhoneCallback callback) {
        mRemoteDatasource.changePhone(params, new ChangePhoneCallback() {
            @Override
            public void onChangePhoneSuccess() {
                callback.onChangePhoneSuccess();
            }

            @Override
            public void onChangePhoneFailure(String errorMsg) {
                callback.onChangePhoneFailure(errorMsg);
            }
        });
    }

    @Override
    public void changeIdCard(Map<String, Object> params, @NonNull final ChangeIdCardCallback callback) {
        mRemoteDatasource.changeIdCard(params, new ChangeIdCardCallback() {
            @Override
            public void onChangeIdCardSuccess() {
                callback.onChangeIdCardSuccess();
            }

            @Override
            public void onChangeIdCardFailure(String errorMsg) {
                callback.onChangeIdCardFailure(errorMsg);
            }
        });
    }

    @Override
    public void fetchUserInfo(final int userId, @NonNull final GetUserInfoCallback callback) {
        UserInfo userInfo = (UserInfo) CacheManager.getInstance().get(Const.USER_INFO_CACHE_KEY);
        if (mIsDirty) {
            mRemoteDatasource.fetchUserInfo(userId, new GetUserInfoCallback() {
                @Override
                public void onUserInfoLoaded(UserInfo userInfo) {
                    CacheManager.getInstance().put(Const.USER_INFO_CACHE_KEY, userInfo);
                    mIsDirty = false;
                    callback.onUserInfoLoaded(userInfo);
                }

                @Override
                public void onDataNotAvailable(String errorMsg) {
                    mIsDirty = false;
                    callback.onDataNotAvailable(errorMsg);
                }
            });
        } else {
            callback.onUserInfoLoaded(userInfo);
        }

    }

    @Override
    public void uploadAvatar(Map<String, Object> params, @NonNull final UploadAvatarCallback callback) {
        mRemoteDatasource.uploadAvatar(params, new UploadAvatarCallback() {
            @Override
            public void onUploaded(String avatarUrl) {
                callback.onUploaded(avatarUrl);
            }

            @Override
            public void onUploadFailure() {
                callback.onUploadFailure();
            }
        });
    }


}
