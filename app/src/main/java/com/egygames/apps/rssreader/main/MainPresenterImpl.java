
package com.egygames.apps.rssreader.main;

import com.egygames.apps.rssreader.R;
import com.egygames.apps.rssreader.model.DataModel;
import com.egygames.apps.rssreader.model.DataResponse;

import cz.msebera.android.httpclient.concurrent.FutureCallback;


public class MainPresenterImpl implements MainPresenter, FutureCallback<DataResponse> {

    private MainView mainView;
    private int currentPage;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
    }

    /**
     * get new data from server
     *
     * @param page the page to retrieve.
     */
    @Override
    public void getData(int page) {
        currentPage = page;
        if (mainView == null) { // if mainview is null return.
            return;
        }
        if (page == 0) //only shows the center progress if getting the first page.
            mainView.showProgress();
        DataModel.getInstance().getData(mainView.getContext(), page, this);

    }


    public MainView getMainView() {
        return mainView;
    }


    @Override
    public void completed(DataResponse result) {
        mainView.hideProgress();// hide te progress.
        if (result.getSuccess()) {// if success notify mainview with the new data.
            mainView.setItems(result.getData());
        } else {
            mainView.showMessage(R.string.err_unknown);

        }

    }

    @Override
    public void failed(Exception ex) {
        mainView.showMessage(R.string.err_connecting);
        mainView.setOffline();

        if (currentPage == 0) //if the first page Load from local db
        {
            DataModel.getInstance().loadLocalData(this);
        }

    }

    @Override
    public void cancelled() {

    }
}
