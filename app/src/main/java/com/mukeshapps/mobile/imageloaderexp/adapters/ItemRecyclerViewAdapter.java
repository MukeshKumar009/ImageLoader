package com.mukeshapps.mobile.imageloaderexp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mukeshapps.mobile.imageloaderexp.R;
import com.mukeshapps.mobile.imageloaderexp.models.ItemCanada;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mukesh on 7/16/2018.
 * Used to display each item in a row, set data for each view in {@link #onBindViewHolder(MyViewHolder, int)}
 * Receive data {@link #listItemCanada} in constructor {@link #ItemRecyclerViewAdapter(Context, ArrayList)}
 */

public class ItemRecyclerViewAdapter  extends RecyclerView.Adapter<MyViewHolder>{

    Context mContext;
    ArrayList<ItemCanada> listItemCanada;

    public ItemRecyclerViewAdapter(Context context, ArrayList<ItemCanada> listItemCanada){
        //Assign values to list and context
        this.mContext = context;
        this.listItemCanada = listItemCanada;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //set layout for each rows here
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        //check if title is not "null". I have checked string "null", because webservice is returning String value "null" not null
        if (!listItemCanada.get(position).title.equals("null")) {
            holder.mTextTitle.setText(listItemCanada.get(position).title);
        }else {
            holder.mTextTitle.setText("Title not available");
        }
        //check if Description is not "null". I have check string "null", because webservice is returning String not null
        if (!listItemCanada.get(position).description.equals("null")){
            holder.mTextDesc.setText(listItemCanada.get(position).description);
        }else {
            holder.mTextDesc.setText("Description not available");

        }

        //Set image by using Picasso library
        if (!listItemCanada.get(position).imageUrl.equals("null")) {
            // I have used Picasso library to load images from URL. Picasso also caches data in device and only loads its
            //if not available in cache.
            Picasso.with(mContext)
                    .load(listItemCanada.get(position).imageUrl)
                    .placeholder( R.drawable.progress_animation )
                    .into(holder.mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onError() {
                            //Image failed to load or not found, set default image
                            holder.mImage.setImageResource(R.drawable.image_default);
                        }

                    });
        }else {
            //Image Preview not available, set default image
            holder.mImage.setImageResource(R.drawable.image_default);
        }
    }

    @Override
    public int getItemCount() {
        return listItemCanada.size();
    }
}

/**
 * This class will define the Views of each items in a row.
 * Initialize view here for each View holder
 */
class MyViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final TextView mTextTitle;
    public final TextView mTextDesc;
    public final ImageView mImage;

    public MyViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mTextTitle = (TextView) itemView.findViewById(R.id.textView_title);
        mTextDesc = (TextView) itemView.findViewById(R.id.textView_desc);
        mImage = (ImageView) itemView.findViewById(R.id.imageview);
    }
}
