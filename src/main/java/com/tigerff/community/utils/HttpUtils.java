package com.tigerff.community.utils;

import com.alibaba.fastjson.JSON;
import com.tigerff.community.dto.AccessToken;
import com.tigerff.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

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
        String jsonString = JSON.toJSONString(accessToken);
        RequestBody body = RequestBody.create(mediaType,jsonString );
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
        client.connectTimeoutMillis();
        Request request = new Request.Builder()
                .header("Authorization","token "+accessToken)
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //fastjson 工具中自带开启了 驼峰命名法
            GithubUser githubUser = JSON.parseObject(response.body().string(), GithubUser.class);
            if(githubUser.getName()==null)
            {
                //有的人没有设置 name 那就将登录 login 自段的值赋值给 name
                Map<String,String> temp = JSON.parseObject(response.body().toString(), Map.class);
                githubUser.setName(temp.get("login"));
            }
            return githubUser;
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
