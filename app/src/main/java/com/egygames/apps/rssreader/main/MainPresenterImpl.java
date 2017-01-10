
package com.egygames.apps.rssreader.main;

import com.egygames.apps.rssreader.R;
import com.egygames.apps.rssreader.model.DataModel;
import com.egygames.apps.rssreader.model.DataResponse;
import com.egygames.apps.rssreader.model.Article;
import com.koushikdutta.async.future.FutureCallback;

public class MainPresenterImpl implements MainPresenter, FutureCallback<DataResponse> {

    private MainView mainView;

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

    /**
     * onCompleted is called by the Future with the result or exception of the asynchronous operation.
     * @param e Exception encountered by the operation
     * @param result Result returned from the operation
     */
    @Override
    public void onCompleted(Exception e, DataResponse result) {
        mainView.hideProgress();// hide te progress.
        if (e != null) {//if exception  show error message.
            mainView.showMessage(R.string.err_connecting);
            return;

        }
        if (result.getSuccess()) {// if success notifiy mainview with the new data.
            mainView.setItems(result.getData());
        } else {
            mainView.showMessage(R.string.err_unknown);

        }

    }

    /**
     * Cancel all network requests on close of activity
     */
    @Override
    public void cancelAllRequests() {
        DataModel.getInstance().cancelAllRequests(mainView.getContext());
    }
}
