package com.egygames.apps.rssreader.model;

import android.content.Context;
import android.util.Log;

import com.egygames.apps.rssreader.R;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Date;

/**
 * Main Data Model that retrieves from network.
 * This class is singleton to be used cross the application.
 */
public class DataModel {
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static final String TOKEN_PARAMETER = "token";
    private static final String PAGE_PARAMETER = "page";

    private static final String APP_KEY_PARAMETER = "app_key";
    private static final String APP_SECRET_PARAMETER = "app_secret";


    private static final String URL = "http://lowcost-env.pmtiunacvu.us-east-1.elasticbeanstalk.com";

    private TokenResponse tokenResponse;
    private long tokenExpirationTime;
    private static DataModel ourInstance = new DataModel();

    public static DataModel getInstance() {
        return ourInstance;
    }

    private DataModel() {
    }

    /**
     * Getting the next page from the server
     *
     * @param context  context object needed by ION
     * @param page     the page to retrieve.
     * @param callback Callback to be called after finishing the request
     */
    public void getData(final Context context, final int page, final FutureCallback<DataResponse> callback) {
        if (isTokenValid()) {// check if the current token is valid.
            // starts a new request.
            Ion.with(context)
                    .load(METHOD_GET, URL)
                    .addQuery(TOKEN_PARAMETER, tokenResponse.getToken())
                    .addQuery(PAGE_PARAMETER, String.valueOf(page))
                    .as(DataResponse.class)
                    .setCallback(callback);
        } else {
            //get new token if token is not valid.
            getToken(context, page, callback);
        }


    }

    /**
     * Get a new token from the server.
     *
     * @param context  context object needed by ION
     * @param page     the page to retrieve.
     * @param callback Callback to be called after finishing the getting data request
     */
    private void getToken(final Context context, final int page, final FutureCallback<DataResponse> callback) {
        Ion.with(context)
                .load(METHOD_POST, URL)
                .setBodyParameter(APP_KEY_PARAMETER, "demo")
                .setBodyParameter(APP_SECRET_PARAMETER, "12345678")
                .as(TokenResponse.class)
                .setCallback(new FutureCallback<TokenResponse>() {
                    @Override
                    public void onCompleted(Exception e, TokenResponse result) {
                        if (e != null || result == null) {
                            callback.onCompleted(new Exception(), null);
                            return;
                        }
                        DataModel.this.tokenResponse = result;
                        DataModel.this.tokenExpirationTime = new Date().getTime() + result.getTtl();
                        getData(context, page, callback);
                    }
                });

    }

    /**
     * check the current token
     *
     * @return true if the token exist and valid, false otherwise.
     */
    private boolean isTokenValid() {

        return tokenResponse != null && (tokenExpirationTime > new Date().getTime());
    }

}
