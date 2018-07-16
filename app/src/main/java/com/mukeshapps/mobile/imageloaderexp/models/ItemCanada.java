package com.mukeshapps.mobile.imageloaderexp.models;

/**
 * Created by Mukesh on 7/16/2018.
 * Model class for each item in a row.
 * Create instance for each row
 */

public class ItemCanada {

    //Properties of each item {@link ItemCanada}
    public String title;
    public String description;
    public String imageUrl;

    /**
     * Cunstructor for @link {@link #ItemCanada(String, String, String)}
     * @param title : Title of row
     * @param description : Desc of row
     * @param imageUrl : Image url for row
     */
    public ItemCanada(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
