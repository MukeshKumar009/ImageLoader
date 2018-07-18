package com.mukeshapps.mobile.imageloaderexp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mukeshapps.mobile.imageloaderexp.R;
import com.mukeshapps.mobile.imageloaderexp.SetView;
import com.mukeshapps.mobile.imageloaderexp.adapters.ItemRecyclerViewAdapter;
import com.mukeshapps.mobile.imageloaderexp.models.ItemCanada;
import com.mukeshapps.mobile.imageloaderexp.network.GetJsonDataClass;
import com.mukeshapps.mobile.imageloaderexp.network.NetworkStatusClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static com.mukeshapps.mobile.imageloaderexp.utilities.ConstantValues.DESC;
import static com.mukeshapps.mobile.imageloaderexp.utilities.ConstantValues.IMAGE_HREF;
import static com.mukeshapps.mobile.imageloaderexp.utilities.ConstantValues.ROWS;
import static com.mukeshapps.mobile.imageloaderexp.utilities.ConstantValues.TITLE;
/**
 * Created by Mukesh on 7/16/2018.
 *
 * This is Main screen to display 'About Canada in list'
 * I have used RecyclerView {@link ItemRecyclerViewAdapter}to show items in horizontal rows.
 * Details of Item is defined in model {@link ItemCanada}
 */
public class MainActivity extends AppCompatActivity implements SetView{

    private ArrayList<ItemCanada> mListItemCanada = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ItemRecyclerViewAdapter mAdapter;
    public static ActionBar actionBar;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get Actionbar instance, set blank until load title from Json
        actionBar = getSupportActionBar();
        actionBar.setTitle("");

        //Configure RecyclerView
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mAdapter = new ItemRecyclerViewAdapter(this,mListItemCanada);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        /**
         * Load complete json data at a time , once json is loaded then we can set images lazily
         * in adapter.
         */
        loadJsonData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Perform refresh action
        if (id == R.id.action_refresh) {
            //clear old data
            mListItemCanada.clear();
            mAdapter.notifyDataSetChanged();
            //load new data
            loadJsonData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This will block our UI only until json is loaded. This is required becase our Image Urls are in
     * json. Once we have image urls we can load images using Picasso library to set in adapter lazily.
     */
    private void loadJsonData(){
        //Only continue if network is connected
        if(NetworkStatusClass.isNetworkConnected(this)) {
            //Perform the doInBackground method, passing in our url
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage(getString(R.string.text_getting_json));
            dialog.setCancelable(false);
            dialog.show();
            GetJsonDataClass.requestJsonFeed(this,getString(R.string.webservice_url));
        }else {
            //Prompt user to connect to network
            Toast.makeText(this,getString(R.string.toast_network_error), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void setListData(String jsonString) {
        //Don't parse json if webservice returns null value
        if (jsonString == null){
            Toast.makeText(this,"Error in loading json data !!",Toast.LENGTH_SHORT).show();
            return;
        }
        //clear old values
        mListItemCanada.clear();
        //Parse json data
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            //Get Title
            String titlemain = jsonObject.getString(TITLE);
            actionBar.setTitle(titlemain);
            //Iterate array to load data for each objects
            JSONArray jsonArray = jsonObject.getJSONArray(ROWS);
            for (int i =0; i<jsonArray.length(); i++){
                JSONObject jsonObjectRow = jsonArray.getJSONObject(i);
                String title = jsonObjectRow.getString(TITLE);
                String desc = jsonObjectRow.getString(DESC);
                String imageUrl = jsonObjectRow.getString(IMAGE_HREF);
                //Create new instance for each row
                ItemCanada itemCanada = new ItemCanada(title, desc, imageUrl);
                //Save instance in list
                mListItemCanada.add(itemCanada);
            }

            //Dismiss progress dialog
            if (dialog!=null && dialog.isShowing()){
                dialog.dismiss();
            }
            //Update list data
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
