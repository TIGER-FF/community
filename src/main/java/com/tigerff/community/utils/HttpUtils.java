package com.tigerff.community.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tigerff.community.dto.AccessToken;
import com.tigerff.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author tigerff
 * @version 1.0
 * @date 2021/2/14 18:08
 */
@Component
public class HttpUtils {
    /**
     * 发送 post 请求到 OAuth 获取到 accessToken
     * @param accessToken
     * @return
     */
    public String getAccessToken(AccessToken accessToken)
    {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessToken));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送 get 请求带上 access_token 获取到个人信息
     *
     *  @param url  https://api.github.com/user+access_token
     * @return
     */
    public GithubUser getPersonInfo(String url, String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization","token "+accessToken)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return JSON.parseObject(response.body().string(), GithubUser.class);
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
