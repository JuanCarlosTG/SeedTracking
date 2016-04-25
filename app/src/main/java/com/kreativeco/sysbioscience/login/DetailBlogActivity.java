package com.kreativeco.sysbioscience.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreativeco.sysbioscience.R;
import com.kreativeco.sysbioscience.SectionActivity;
import com.kreativeco.sysbioscience.utils.Constants;

public class DetailBlogActivity extends SectionActivity{

    private ImageView iv_description_blog;
    private TextView tv_txt_description_video;
    private ImageButton btnBackHeader;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_blog);
        setStatusBarColor(SectionActivity.STATUS_BAR_COLOR);
        overridePendingTransition(R.anim.slide_left_from, R.anim.slide_left);

        iv_description_blog = (ImageView)findViewById(R.id.iv_description_blog);
        tv_txt_description_video = (TextView) findViewById(R.id.tv_txt_description_video);
        txtTitle = (TextView) findViewById(R.id.txt_title);

        btnBackHeader = (ImageButton) findViewById(R.id.i_btn_header);
        btnBackHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                DetailBlogActivity.this.overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
            }
        });

        Glide.with(this).load(Constants.getSpotFullImage()).into(iv_description_blog);
        tv_txt_description_video.setText(Constants.getSpotDescription());
        txtTitle.setText(Constants.getSpotTitle());
    }

    public void clickBack(View v) {
        finish();
        overridePendingTransition(R.anim.slide_right_from, R.anim.slide_right);
    }

    @Override
    public void onBackPressed() {
        clickBack(null);
    }

}
