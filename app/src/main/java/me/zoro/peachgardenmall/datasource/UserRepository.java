package me.zoro.peachgardenmall.datasource;


import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.util.Map;

import me.zoro.peachgardenmall.datasource.domain.UserInfo;

/**
 * 从数据源加载用户数据到缓存
 * Created by dengfengdecao on 16/11/11.
 */

public class UserRepository implements UserDatasource {

    public static final String TAG = "UserRepository";

    private static final String USER_INFO_KEY = "user";

    private static UserRepository sRepository;

    private LruCache<String, Object> mMemoryCache;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     */
    private boolean mCacheIsDirty = false;

    private UserDatasource mRemoteDatasource;

    // 防止被直接实例化
    private UserRepository(UserDatasource remoteDatasource) {
        mRemoteDatasource = remoteDatasource;
        // 获取系统分配给每个应用程序的最大内存，每个应用系统分配32M
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int maxSize = maxMemory / 8;
        mMemoryCache = new LruCache<>(maxSize);

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
            public void onFetchSuccess(String captcha) {
                callback.onFetchSuccess(captcha);
            }

            @Override
            public void onFetchFailure(String errorMsg) {
                callback.onFetchFailure(errorMsg);
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
       /* UserInfo userInfo = (UserInfo) mMemoryCache.get(USER_INFO_KEY);
        if (userInfo != null) {
            Log.d(TAG, "getUserInfo: 内存中存在用户信息");
        } else {
            mRemoteDatasource.getUserInfo(username, new GetUserInfoCallback() {
                @Override
                public void onUserInfoLoaded(UserInfo userInfo) {
                    callback.onUserInfoLoaded(userInfo);
                }

            @Override
            public void onDataNotAvailable(String errorMsg) {
                callback.onDataNotAvailable(errorMsg);
            }
        });*/

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
    public void logout(String sid, @NonNull final LogoutCallback callback) {
        mRemoteDatasource.logout(sid, new LogoutCallback() {

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
    public void changePasswrod(Map<String, Object> params, @NonNull final ChangePasswordCallback callback) {
        mRemoteDatasource.changePasswrod(params, new ChangePasswordCallback() {
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
    public void fetchUserInfo(int userId, @NonNull final GetUserInfoCallback callback) {
        mRemoteDatasource.fetchUserInfo(userId, new GetUserInfoCallback() {
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


}
