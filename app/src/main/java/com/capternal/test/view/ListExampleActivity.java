package com.capternal.test.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.capternal.test.R;
import com.capternal.test.controller.FoodListAdapter;
import com.capternal.test.model.FoodListModel;
import com.capternal.test.utils.CallWebService;
import com.capternal.test.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListExampleActivity extends AppCompatActivity implements CallWebService.OnGetUrlResponse {

    private CallWebService objApiFoodList = null;
    private RecyclerView objRecyclerView = null;
    private ArrayList<FoodListModel> arrFoodListModel = new ArrayList<FoodListModel>();
    private FoodListAdapter objFoodListAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_example);

        /** INITIALISE VIEW */
        initializeView();

        /** API FOOD LIST */
        objApiFoodList = (CallWebService) new CallWebService(Constants.API_DEMO_LIST, this, this, true, true, "API_DEMO_LIST");
    }

    private void initializeView() {
        objRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_food);

        objFoodListAdapter = new FoodListAdapter(arrFoodListModel, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        objRecyclerView.setLayoutManager(manager);
        objRecyclerView.setItemAnimator(new DefaultItemAnimator());
        objRecyclerView.setAdapter(objFoodListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        objApiFoodList.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        objApiFoodList.cancel(true);
        objApiFoodList = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        objApiFoodList.cancel(true);
    }

    @Override
    public void onGetUrlResponse(String urlId, String strUrl, String strResult) {
        switch (urlId) {
            case "API_DEMO_LIST":
                try {
                    JSONObject objJsonObject = new JSONObject(strResult);
                    if (objJsonObject.getBoolean(Constants.API_RESULT)) {
                        arrFoodListModel.clear();
                        JSONArray objJsonArray = new JSONArray(objJsonObject.getString(Constants.API_RESPONSE));
                        for (int index = 0; index < objJsonArray.length(); index++) {
                            arrFoodListModel.add(new FoodListModel(objJsonArray.getJSONObject(index).getString("name"), objJsonArray.getJSONObject(index).getString("image")));
                        }
                        objFoodListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, objJsonObject.getString(Constants.API_MESSAGE), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onGetUrlCancelled(String urlId, String strUrl, String cancelledResult) {

    }
}
