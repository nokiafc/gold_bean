package com.tianmi.goldbean.net;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;

/**
 * Created by hdb on 2016/3/2.
 */
public abstract  class JsonCallback<T> {
    public abstract void onError(Request request, String e);
    public abstract void onResponse(T t, String message) throws IOException;

    Type getType(){
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return  type;
//        if(type instanceof Class){
//            return type;
//        }else{
//
//            return new TypeToken<T>(){}.getType();
//        }

    }

    }
