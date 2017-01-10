

package com.egygames.apps.rssreader.main;

import android.content.Context;

import com.egygames.apps.rssreader.model.Article;

import java.util.List;

public interface MainView {
    /**
     * Show the progress on the center of the screen
     */
    void showProgress();

    /**
     * hide the progress.
     */
    void hideProgress();
    /**
     * Add the article items to the adapter.
     *
     * @param items the list of retrieved new articles.
     */    void setItems(List<Article> items);
    /**
     * Show toast messages
     * @param message the message text to show.
     */
    void showMessage(String message);
    /**
     * Show toast messages
     * @param messageRSID the message id to show.
     */
    void showMessage(int messageRSID);
    /**
     * gets the activity context
     * @return activity context.
     */
    Context getContext();

    /**
     * set the view offline because we loaded local data.
     */
    void setOffline();
}
