package com.leicx.util;

import com.leicx.constant.PayuStringConstant;
import com.leicx.payment.entity.PackageResponseEntity;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Random;

/**
 * PayU请求发送httpClient工具类
 *
 * @author daxiong
 * @date 2019-11-21 14:42
 */
public class HttpClientUtil {

    /**
     * 发送get请求，获取返回响应
     *
     * @param url get请求的地址
     * @return com.leicx.payment.entity.PackageResponseEntity
     * @author daxiong
     * @date 2019-11-22 10:42
     */
    public static PackageResponseEntity doGet(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        setHeader(httpGet, false);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 发送DELETE请求，获取返回响应
     *
     * @param url 请求的地址
     * @author daxiong
     * @return com.leicx.payment.entity.PackageResponseEntity
     * @date 2019-11-22 10:42
     */
    public static PackageResponseEntity doDelete(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete httpDelete = new HttpDelete(url);
        setHeader(httpDelete, false);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 发送post请求，获取返回
     *
     * @param url        请求的url地址
     * @param jsonString post请求的请求体
     * @param isToken    是否是token请求
     * @return com.leicx.payment.entity.PackageResponseEntity
     * @author daxiong
     * @date 2019-11-22 10:41
     */
    public static PackageResponseEntity doPost(String url, String jsonString, boolean isToken) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        setHeader(httpPost, isToken);

        // post请求可能没有请求体
        if (jsonString != null) {
            StringEntity stringEntity = new StringEntity(jsonString, PayuStringConstant.CHAR_SET);
            httpPost.setEntity(stringEntity);
        }
//        Optional.ofNullable(jsonString).ifPresent(m -> httpPost.setEntity(new StringEntity(jsonString, PayuStringConstant.CHAR_SET)));

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static PackageResponseEntity doPost(String url, String jsonString) {
        return doPost(url, jsonString, false);
    }

    public static PackageResponseEntity doTokenPost(String url, String jsonString) {
        return doPost(url, jsonString, true);
    }

    /**
     * 打印响应的response，封装好返回
     *
     * @param response 响应
     * @return com.leicx.payment.entity.PackageResponseEntity
     * @author daxiong
     * @date 2019-11-22 13:20
     */
    private static PackageResponseEntity handleResponse(CloseableHttpResponse response) throws IOException {
        HttpEntity responseEntity = response.getEntity();

        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        System.out.println("响应状态为:" + statusLine);
        String responseStr = "";
        if (responseEntity != null) {
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            responseStr = EntityUtils.toString(responseEntity);
            System.out.println("响应内容为:" + responseStr);
        }
        return new PackageResponseEntity(statusCode, responseStr);
    }

    /**
     * 设置请求的header
     *
     * @param httpRequest httpPost或者httpGet对象
     * @param isToken     是否是token请求
     * @return void
     * @author daxiong
     * @date 2019-11-22 10:27
     */
    public static void setHeader(HttpRequestBase httpRequest, boolean isToken) {
        httpRequest.setHeader(PayuStringConstant.APP_ID_KEY, PayuStringConstant.APP_ID_VALUE);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setHeader(PayuStringConstant.API_VERSION_KEY, PayuStringConstant.API_VERSION_VALUE);
        httpRequest.setHeader(PayuStringConstant.X_PAYMENTS_OS_ENV_KEY, PayuStringConstant.X_PAYMENTS_OS_ENV_VALUE);
        if (isToken) {
            httpRequest.setHeader(PayuStringConstant.PUBLIC_KEY_KEY, PayuStringConstant.PUBLIC_KEY_VALUE);
        } else {
            httpRequest.setHeader(PayuStringConstant.PRIVATE_KEY_KEY, PayuStringConstant.PRIVATE_KEY_VALUE);
            httpRequest.setHeader(PayuStringConstant.IDEMPOTENCY_KEY_KEY, String.valueOf(new Random().nextInt()));
        }
    }


}
