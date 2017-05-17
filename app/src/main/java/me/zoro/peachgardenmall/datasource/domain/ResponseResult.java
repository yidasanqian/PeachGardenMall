package me.zoro.peachgardenmall.datasource.domain;

/**
 * 服务器返回的数据结构:
 * {
 * code：0,
 * message: "success",
 * result: { key1: value1, key2: value2, ... },
 * count:120
 * }
 * Created by dengfengdecao on 17/5/17.
 */

public class ResponseResult<T> {

    public int code;
    public String message;
    public T data;
    public int count;

    @Override
    public String toString() {
        return "ResponseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", count=" + count +
                '}';
    }
}
