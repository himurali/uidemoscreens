package com.mahakuta.lalbagh.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.mahakuta.lalbagh.R;
import com.mahakuta.lalbagh.model.SliderItem;
import com.mahakuta.lalbagh.model.TreeInfo;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class TreeDescriptionActivity extends AppCompatActivity {

    TreeInfo currenTree;
    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    SliderView sliderView;
    private SliderAdapterExample adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_description);

        currenTree = (TreeInfo) getIntent().getSerializableExtra("CLICKED_TREE");
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);                          //will display the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//will display the back arrow '<-' button
        getSupportActionBar().setTitle(currenTree.getTitle());

        //handle click event when back arrow '<-' is clicked on top
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set the image under the CollapsingToolbarLayout
        ImageView i = (ImageView)findViewById(R.id.place);
        //i.setImageResource(currentPlace.getImageResourceId());
        Glide.with(this).load(currenTree.getTopimage()).into(i);

        //to make status bar transparent i.e. image will cover status bar too
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // set the text content on the ExpandableTextView
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);

        TextView textViewMap = (TextView) findViewById(R.id.getWhereItGrows);
        TextView textViewUses = (TextView) findViewById(R.id.plantUtility);

        textViewMap.setText(currenTree.getWhereItGrows());
        textViewUses.setText(currenTree.getUses());

        // IMPORTANT - call setText on the ExpandableTextView to set the text content to display
        expTv1.setText(currenTree.getDescription());

        //to show the google map in lite mode

        //to display the images in custom slider view
        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);


        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data

        SliderItem sliderItem = new SliderItem();
        sliderItem.setDescription(currenTree.getFlowers());
        sliderItem.setImageUrl(currenTree.getFlowersUrl());
        sliderItemList.add(sliderItem);

        SliderItem sliderItem2 = new SliderItem();
        sliderItem2.setDescription(currenTree.getLeaves());
        sliderItem2.setImageUrl(currenTree.getLeavesUrl());
        sliderItemList.add(sliderItem2);

        SliderItem sliderItem3 = new SliderItem();
        sliderItem3.setDescription(currenTree.getFruits());
        sliderItem3.setImageUrl(currenTree.getFruitsUrl());
        sliderItemList.add(sliderItem3);


        adapter.renewItems(sliderItemList);

        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });

        //set the phone number

    }

    public void renewItems(View view) {

    }


}
