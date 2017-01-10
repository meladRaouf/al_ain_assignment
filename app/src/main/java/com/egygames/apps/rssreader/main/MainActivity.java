package com.egygames.apps.rssreader.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.egygames.apps.rssreader.BaseActivity;
import com.egygames.apps.rssreader.R;
import com.egygames.apps.rssreader.article.ArticleActivity;
import com.egygames.apps.rssreader.model.Article;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener, OnMoreListener {

    private static final int MAXIMUM_PAGES = 10;// Maximum numbers of pages.
    public static final String ARTICLE_EXTRA = "article";// Key constant for passing extras.
    @BindView(R.id.recycler)
    SuperRecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private MainPresenter presenter;
    private int page;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);// BindView annotated fields and methods

        //Create the articles adapter
        adapter = new MainAdapter(this, this);
        // assign the adapter to recycler view and creating the animation.
        recyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
        // assign the recycler view and layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //the load more listener
        recyclerView.setupMoreListener(this, 1);
        //Main presenter
        presenter = new MainPresenterImpl(this);
        //first page.
        page = 0;
        //getting the first page.
        presenter.getData(page);
    }

    /**
     * Show the progress on the center of the screen
     */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * hide the progress.
     */
    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Add the article items to the adapter.
     *
     * @param items the list of retrieved new articles.
     */
    @Override
    public void setItems(List<Article> items) {
        // If this is the first reset the adapter
        if (page == 0) {
            adapter.reset();
        }
        //Add items to adapter
        adapter.addItems(items);
        // refresh  data
        adapter.notifyDataSetChanged();
    }

    /**
     * Show toast messages
     * @param message the message text to show.
     */
    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Show toast messages
     * @param messageRSID the message id to show.
     */
    @Override
    public void showMessage(int messageRSID) {
        Toast.makeText(this, messageRSID, Toast.LENGTH_LONG).show();
    }

    /**
     * gets the activity context
     * @return activity context.
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * On clicking article item
     * @param view recycler view row.
     */
    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();//getting the position of the clicked item.
        Article item = adapter.getItem(position);//Article implements Parcelable so we can pass it on intent.
        Intent intent = new Intent(this, ArticleActivity.class);// Create article activity intent.
        intent.putExtra(ARTICLE_EXTRA, item);//put the the article on the extra.
        startActivity(intent);//start the new activity.

    }

    /**
     * On more artecles requested.
     * @param overallItemsCount current items on adapter.
     * @param itemsBeforeMore items before the loading more
     * @param maxLastVisiblePosition maximum items if set.
     */
    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

        if (page == MAXIMUM_PAGES)//if we are on the last page, do nothing.
            return;
        page++;
        presenter.getData(page);
    }


    @Override
    protected void onStop() {
        presenter.cancelAllRequests();//Stop all requests on activity stop;
        super.onStop();

    }
}
