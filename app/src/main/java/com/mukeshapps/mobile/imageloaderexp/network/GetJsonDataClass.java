package com.mukeshapps.mobile.imageloaderexp.network;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mukeshapps.mobile.imageloaderexp.SetView;
import com.mukeshapps.mobile.imageloaderexp.activities.MainActivity;
import com.mukeshapps.mobile.imageloaderexp.models.ItemCanada;


/**
 * Created by Mukesh on 7/19/2018.
 * Load json data create values for {@link ItemCanada}, once data is loaded update adapter
 * user volley network library for this operation
 */

public class GetJsonDataClass {
    private static final String TAG = GetJsonDataClass.class.getName();

    public static void requestJsonFeed(final Context context, String url) {

        //RequestQueue initialized
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //String Request initialized
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Call method in Presenter to update UI
                SetView setView = (MainActivity)context;
                setView.setListData(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }
}