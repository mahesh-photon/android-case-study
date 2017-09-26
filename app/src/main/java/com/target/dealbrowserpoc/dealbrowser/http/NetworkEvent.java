package com.target.dealbrowserpoc.dealbrowser.http;

import android.support.annotation.NonNull;


import com.google.gson.Gson;
import com.target.dealbrowserpoc.dealbrowser.model.Response;

import java.net.HttpURLConnection;

public class NetworkEvent<T> {
    private static final String TAG = NetworkEvent.class.getSimpleName();

    protected Response response;
    protected String responseBody;

    private boolean successful;

    private T responseObject;

    public T getResponseObject() {
        return responseObject;
    }

    public NetworkEvent(@NonNull Response response, Class<T> responseObjectClass) {
        this.response = response;
        final int statusCode = response.getStatusCode();
        responseBody =  response.getResponseBody();
        final String requestString = response.getRequestString();
        this.successful = isSuccessStatus(statusCode);

        if ( responseObjectClass != null) {
            this.responseObject = fromJson(responseBody, responseObjectClass);
        }
    }

    public static <T> T fromJson(String json,  Class<T> classOfT) {
        T retObj;
        final Gson gson = new Gson();

        try {
            retObj = gson.fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();

            retObj = null;
        }
        return retObj;
    }

    public boolean isSuccessful() {
        return successful;
    }


    public boolean isSuccessStatus(int statusCode) {
        return !(statusCode == HttpURLConnection.HTTP_NOT_FOUND || statusCode == -1);
    }

}
