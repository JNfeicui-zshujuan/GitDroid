package com.feicuiedu.gitdroid.hotrepositor;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/7.
 * 序列化language
 */
public class Language implements Serializable {
    private String path;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    private String name;
    private static List<Language> DEFAULT_LANGS;
    public static List<Language> getDefaultLangs(Context context){
        if (DEFAULT_LANGS!=null) return DEFAULT_LANGS;
        try {
            InputStream inputStream=context.getAssets().open("langs.json");
            //使用第三方的io(apache)(commons-io:commons-io'2.4'),返回一json字符串
            String content= IOUtils.toString(inputStream);
            Gson gson=new Gson();
            DEFAULT_LANGS= gson.fromJson(content, new TypeToken<List<Language>>() {
            }.getType());
            return DEFAULT_LANGS;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
