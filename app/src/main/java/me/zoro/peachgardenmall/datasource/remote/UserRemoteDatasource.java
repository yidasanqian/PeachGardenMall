package me.zoro.peachgardenmall.datasource.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import me.zoro.peachgardenmall.api.ServiceGenerator;
import me.zoro.peachgardenmall.api.UserClient;
import me.zoro.peachgardenmall.common.Const;
import me.zoro.peachgardenmall.datasource.UserDatasource;
import me.zoro.peachgardenmall.datasource.domain.UserInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 从服务器获取数据
 * Created by dengfengdecao on 16/10/21.
 */
public class UserRemoteDatasource implements UserDatasource {

    private static final String TAG = "UserRemoteDatasource";
    private static UserRemoteDatasource sUserRemoteDatasource;

    private UserClient mUserClient;

    private UserRemoteDatasource(Context context) {
        mUserClient = ServiceGenerator.createService(context, UserClient.class);
    }

    public static UserRemoteDatasource getInstance(Context context) {

        if (sUserRemoteDatasource == null) {
            sUserRemoteDatasource = new UserRemoteDatasource(context);
        }
        return sUserRemoteDatasource;
    }


    @Override
    public void fetchCaptcha(final String tel, @NonNull final GetCaptchaCallback callback) {
        Call<JsonObject> call = mUserClient.fetchCaptcha(tel);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {

                    Log.d(TAG, "onResponse: 获取校验码 bodyJson <== " + bodyJson);
                    int code = bodyJson.get("code").getAsInt();
                    if (code == 0) {
                        String captcha = bodyJson.get("result").getAsString();
                        callback.onFetchSuccess(captcha);
                    } else {
                        callback.onFetchFailure(bodyJson.get("message").getAsString());
                    }
                } else {
                    callback.onFetchFailure("服务器异常！");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取校验码异常 t <== ", t);
                callback.onFetchFailure("服务器异常！");
            }
        });

    }

    @Override
    public void registerNewUser(Map<String, Object> params, @NonNull final RegisterUserCallback callback) {
        Call<JsonObject> call = mUserClient.saveUser(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                Log.d(TAG, "onResponse: 注册 bodyJson <== " + bodyJson);
                if (bodyJson != null) {

                    int code = bodyJson.get(Const.CODE).getAsInt();
                    JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        UserInfo userInfo = gson.fromJson(resultJson, UserInfo.class);
                        String username = userInfo.getMobile();
                        callback.onRegisterSuccess(username);
                    } else {
                        callback.onRegisterFailure(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onRegisterFailure("请求失败");
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 注册异常 t <== ", t);
                callback.onRegisterFailure("服务器异常");
            }
        });
    }

    @Override
    public void login(Map<String, String> params, @NonNull final LoginCallback callback) {
        Call<JsonObject> call = mUserClient.login(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        UserInfo userInfo = gson.fromJson(resultJson, UserInfo.class);

                        String token = userInfo.getToken();
                        callback.onLoginSuccess(userInfo, token);
                    } else {
                        callback.onLoginFailure(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onLoginFailure("请求失败");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 登陆异常 t <== ", t);
                callback.onLoginFailure("服务器异常");
            }
        });
    }


    @Override
    public void getUserInfo(final String username, @NonNull final GetUserInfoCallback callback) {
        Call<JsonObject> call = mUserClient.getUserInfo(username);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {

                    //Log.d(TAG, "onResponse: 用户详细信息 bodyJson <== " + bodyJson);
                    int code = bodyJson.get("code").getAsInt();
                    if (code == 0) {
                        JsonObject resultJson = bodyJson.get("result").getAsJsonObject();
                        Gson gson = new GsonBuilder().setLenient().create();
                        UserInfo userInfo = gson.fromJson(resultJson, UserInfo.class);
                        callback.onUserInfoLoaded(userInfo);
                    } else if (code == 40000) {
                        UserInfo userInfo = new UserInfo();

                        callback.onUserInfoLoaded(userInfo);
                        callback.onDataNotAvailable("错误码：40000\n" + bodyJson.get("message").getAsString());
                    } else {
                        callback.onDataNotAvailable(bodyJson.get("message").getAsString());
                    }
                } else {
                    UserInfo userInfo = new UserInfo();

                    callback.onUserInfoLoaded(userInfo);
                    callback.onDataNotAvailable("错误码：40000\n您未登录");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 系统异常，获取用户信息失败！", t);
                callback.onDataNotAvailable("服务器异常");
            }
        });
    }

    @Override
    public void userInfoRevise(Map<String, Object> params, @NonNull final UserInfoReviseCallback callback) {
        Call<JsonObject> call = null;
        MultipartBody.Part part;
        if (params.get("a") != null) {
            part = ((MultipartBody.Part) params.get("a"));
            Map<String, RequestBody> requestBodyMap = new HashMap<>();
            requestBodyMap.put("id", RequestBody.create(MediaType.parse("multipart/form-data"),
                    params.get("id").toString()));
            call = mUserClient.uploadAvatar(requestBodyMap, part);
        } else {
            call = mUserClient.userInfoRevise(params);
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get("code").getAsInt();
                    if (code == 0) {
                        callback.onUserInfoReviseSuccess();
                    } else {
                        callback.onUserInfoReviseFailure((bodyJson.get("message").getAsString()));
                    }
                } else {
                    callback.onUserInfoReviseFailure("服务器异常");

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 信息修改异常 t <== ", t);
                callback.onUserInfoReviseFailure("服务器异常");
            }
        });
    }

    @Override
    public void logout(String sid, @NonNull final LogoutCallback callback) {
        Call<JsonObject> call = mUserClient.logout(sid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                Log.d(TAG, "onResponse: 退出登录 bodyJson <== " + bodyJson);
                int code = bodyJson.get("code").getAsInt();
                if (code != 0) {
                    callback.onLogout();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 登出异常 t <== ", t);
            }
        });
    }

    @Override
    public void forgetPassword(Map<String, Object> params, @NonNull final ForgetPasswordCallback callback) {
        Call<JsonObject> call = mUserClient.forgetPassword(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                Log.d(TAG, "onResponse: 忘记密码 bodyJson <== " + bodyJson);
                int code = bodyJson.get("code").getAsInt();
                if (code == 0) {
                    callback.onForgetPasswordSuccess();
                } else {
                    callback.onForgetPasswordFailure(bodyJson.get("message").getAsString());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 忘记密码异常 t <== ", t);
                callback.onForgetPasswordFailure("服务器错误");
            }
        });
    }

    @Override
    public void changePasswrod(Map<String, Object> params, @NonNull final ChangePasswordCallback callback) {
        Call<JsonObject> call = mUserClient.changePassword(params);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                Log.d(TAG, "onResponse: 更改密码 bodyJson <== " + bodyJson);
                int code = bodyJson.get("code").getAsInt();
                if (code == 0) {
                    callback.onChangePasswordSuccess();
                } else {
                    callback.onChangePasswordFailure(bodyJson.get("message").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 更改密码异常 t <== ", t);
                callback.onChangePasswordFailure("服务器错误");
            }
        });
    }

    @Override
    public void fetchUserInfo(int userId, @NonNull final GetUserInfoCallback callback) {
        Call<JsonObject> call = mUserClient.fetchUserInfo(userId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject bodyJson = response.body();
                if (bodyJson != null) {
                    int code = bodyJson.get(Const.CODE).getAsInt();
                    JsonObject resultJson = bodyJson.get(Const.RESULT).getAsJsonObject();
                    if (code == 0) {
                        Gson gson = new GsonBuilder().setLenient().create();
                        UserInfo userInfo = gson.fromJson(resultJson, UserInfo.class);
                        callback.onUserInfoLoaded(userInfo);
                    } else {
                        callback.onDataNotAvailable(bodyJson.get(Const.MESSAGE).getAsString());
                    }
                } else {
                    callback.onDataNotAvailable("请求失败");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取用户信息异常 t <== ", t);
                callback.onDataNotAvailable("服务器异常");
            }
        });
    }

}
