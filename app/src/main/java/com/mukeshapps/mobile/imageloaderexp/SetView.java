package com.mukeshapps.mobile.imageloaderexp;

/**
 * This interface {@link SetView} will keep all the methods to be used to update UI
 * Should be implemented by Presenter
 */
public interface SetView {
    //Call this method to update UI
    public void setListData(String jsonString);

}
