package com.target.dealbrowserpoc.dealbrowser.http;

import android.content.Context;
import android.os.AsyncTask;

import com.target.dealbrowserpoc.dealbrowser.model.Response;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 * Created by mcnaik on 9/24/17.
 */

public class NetController extends AsyncTask<String, Void, Response> {
    private static final int DEFAULT_SOCKET_TIMEOUT = 60;
    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    public static final int DEAL_LIST_REQUEST = 0;
    private static final int BUFFER_SIZE = 1024;
    private final OnTaskCompleted mController;
    private String mRequestUrl;
    private Call call;
    private int reqType;

    public NetController(OnTaskCompleted controller) {
        this.mController = controller;
        this.call = null;
    }

    @Override
    protected Response doInBackground(String... params) {


        final Response response = new Response();
        try {
            Request httpRequest = null;
            response.setRequestTypes(reqType);
            response.setStatusCode(-1);
            final OkHttpClient client = getHttpClientBuilder(true).build();
            mRequestUrl = mRequestUrl.replaceAll(" ", "%20");

            httpRequest = buildBaseRequestBuilder(mRequestUrl, null).build();

            call = client.newCall(httpRequest);
            final okhttp3.Response httpResponse = call.execute();


            if (httpResponse != null) {
                final int status = httpResponse.code();
                final String responseString = getResponseBodyAndClose(httpResponse);

                final String requestString = httpRequest != null ? httpRequest.toString() : null;

                response.setRequestTypes(reqType);
                response.setStatusCode(status);
                response.setResponseBody(responseString);
                response.setRequestString(requestString);
                response.setRequestUrl(mRequestUrl);
                response.setOptionalMsg(httpResponse.message());
                response.setHeaders(httpResponse.headers().toMultimap());
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        if (mController != null) {
            mController.onTaskCompleted(response);
        }
        super.onPostExecute(response);
    }


    private static synchronized OkHttpClient.Builder getHttpClientBuilder(final boolean shouldFollowRedirects) {
        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.readTimeout(DEFAULT_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        httpBuilder.writeTimeout(DEFAULT_SOCKET_TIMEOUT, TimeUnit.SECONDS);
        final List<Protocol> protocols = new ArrayList<>();
        protocols.add(Protocol.HTTP_1_1);
        httpBuilder.protocols(protocols);
        httpBuilder.followRedirects(shouldFollowRedirects);
        return httpBuilder;
    }

    static Request.Builder buildBaseRequestBuilder(String url, Map<String, String> headerMap) {
        final Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (headerMap != null && !headerMap.isEmpty()) {
            builder.headers(Headers.of(headerMap));
        }
        return builder;
    }

    public String getUrl() {
        return mRequestUrl;
    }

    public void setUrl(String url) {
        this.mRequestUrl = url;
    }

    public void setReqType(int reqType) {
        this.reqType = reqType;
    }

    public void cancelActive() {
        if (call != null) {
            call.cancel();
        }
        cancel(true);
    }

    public static String getResponseBodyAndClose(okhttp3.Response response) {
        String responseString = null;
        if (response != null) {
            final ResponseBody body = response.body();
            try {
                responseString = getStringFromInputStream(response.body().byteStream());
            } catch (Exception e) {
                responseString = null;
            } finally {
                body.close();
            }
        }
        return responseString;
    }


    private static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader br = null;
        final StringBuilder sb = new StringBuilder();
        int charsRead;
        final char[] buffer = new char[BUFFER_SIZE];
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((charsRead = br.read(buffer, 0, BUFFER_SIZE)) != -1) {
                sb.append(buffer, 0, charsRead);
            }
        } catch (EOFException eof) {
            eof.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
