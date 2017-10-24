package com.capternal.test.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capternal.test.R;
import com.capternal.test.model.FoodListModel;
import com.capternal.test.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by jupitor on 24/10/17.
 */

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.FoodViewHolder> {

    private ArrayList<FoodListModel> arrFoodListModel = new ArrayList<FoodListModel>();
    private Context context = null;

    public FoodListAdapter(ArrayList<FoodListModel> arrFoodListModel, Context context) {
        this.arrFoodListModel = arrFoodListModel;
        this.context = context;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView objTextViewFoodName = null;
        ImageView objImageViewFoodImage = null;

        public FoodViewHolder(View itemView) {
            super(itemView);
            objTextViewFoodName = (TextView) itemView.findViewById(R.id.textView_food_name);
            objImageViewFoodImage = (ImageView) itemView.findViewById(R.id.imageView_food);
        }
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_cell, parent, false);
        return new FoodViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.objTextViewFoodName.setText(arrFoodListModel.get(position).getFoodName());
        ImageLoader.loadWithRectangle(context, arrFoodListModel.get(position).getFoodImage(), holder.objImageViewFoodImage);
    }

    @Override
    public int getItemCount() {
        return arrFoodListModel.size();
    }
}
