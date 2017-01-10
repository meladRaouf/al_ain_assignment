package com.egygames.apps.rssreader.model;

import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.egygames.apps.rssreader.R;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.concurrent.FutureCallback;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

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
    private AsyncHttpClient client;
    private Gson gson;

    public static DataModel getInstance() {
        return ourInstance;
    }

    private DataModel() {
        client = new AsyncHttpClient();
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create();// create gson serilzer.
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
            RequestParams params = new RequestParams();
            params.put(TOKEN_PARAMETER, tokenResponse.getToken());
            params.put(PAGE_PARAMETER, String.valueOf(page));
            client.get(URL, params, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, String res) {
                    try {
                        DataResponse response = gson.fromJson(res, DataResponse.class);
                        if (page != 0 && response.getData().size() > 0) {// remove the first duplicated item if  not the fist page.
                            response.getData().remove(0);
                        }
                        if (page == 0) {
                            saveLocally(response.getData());// if the first page cache it for no network situations.
                        }
                        callback.completed(response);
                    } catch (JsonSyntaxException jsonSyntaxException) {
                        callback.failed(jsonSyntaxException);

                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                    callback.failed(new Exception());
                }
            });
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
        RequestParams params = new RequestParams();
        params.put(APP_KEY_PARAMETER, "demo");
        params.put(APP_SECRET_PARAMETER, "12345678");
        client.post(URL, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                try {
                    TokenResponse response = gson.fromJson(res, TokenResponse.class);
                    DataModel.this.tokenResponse = response;
                    DataModel.this.tokenExpirationTime = new Date().getTime() + response.getTtl();
                    getData(context, page, callback);
                } catch (JsonSyntaxException jsonSyntaxException) {
                    callback.failed(jsonSyntaxException);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                callback.failed(new Exception());
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


    private void saveLocally(List<Article> data) {
        //Delete old cached data.
        new Delete().from(Article.class).execute();

        //Start db transaction.
        ActiveAndroid.beginTransaction();
        for (Article article : data) {
            article.save();       //Save the article locally
        }
        ActiveAndroid.setTransactionSuccessful();
        ActiveAndroid.endTransaction();//end the transaction.


    }


    public void loadLocalData(final FutureCallback<DataResponse> callback) {

        //Load saved items.
        List<Article> localItems = new Select()
                .from(Article.class)
                .orderBy("ID asc")
                .execute();

        //construct data response object.
        DataResponse response = new DataResponse();
        response.setData(localItems);
        response.setSuccess(true);
        callback.completed(response);


    }
}
