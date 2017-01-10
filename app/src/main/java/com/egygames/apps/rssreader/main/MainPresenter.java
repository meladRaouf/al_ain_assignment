
package com.egygames.apps.rssreader.main;



/**
 * Listens to user actions from the UI
 *
 */
public interface MainPresenter {
    /**
     * get new data from server
     * @param page the page to retrieve.
     */
    void getData(int page);

    /**
     * Cancel all network requests on close of activity
     */
    void cancelAllRequests() ;
}
