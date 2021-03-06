package com.socks.okhttp.plus.builder;

import android.text.TextUtils;

import com.socks.okhttp.plus.OkHttpProxy;
import com.socks.okhttp.plus.callback.OkCallback;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zhaokaiqiang on 15/11/24.
 */
public class PostRequestBuilder extends RequestBuilder {

    private Map<String, String> headers;

    public PostRequestBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostRequestBuilder setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public PostRequestBuilder addParams(String key, String value) {
        if (params == null) {
            params = new IdentityHashMap<>();
        }
        params.put(key, value);
        return this;
    }

    public PostRequestBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public PostRequestBuilder addHeader(String key, String values) {
        if (headers == null) {
            headers = new IdentityHashMap<>();
        }
        headers.put(key, values);
        return this;
    }

    public PostRequestBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }


    @Override
    public Call execute(Callback callback) {

        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be null !");
        }

        Request.Builder builder = new Request.Builder().url(url);
        if (tag != null) {
            builder.tag(tag);
        }
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        appendParams(encodingBuilder, params);
        appendHeaders(builder, headers);
        builder.post(encodingBuilder.build());
        Request request = builder.build();

        if (callback instanceof OkCallback) {
            ((OkCallback) callback).onStart();
        }
        Call call = OkHttpProxy.getInstance().newCall(request);
        call.enqueue(callback);
        return call;
    }
}
