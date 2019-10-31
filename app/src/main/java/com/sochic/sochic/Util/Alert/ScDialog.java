package com.sochic.sochic.Util.Alert;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sochic.sochic.R;

public class ScDialog extends LinearLayout {

    LinearLayout moreLayer;
    TextView moreShareBtn;
    TextView moreFollowBtn;
    View moreLineView;

    LinearLayout leavePreLayer;
    Button lPreCancelBtn;
    Button lPreOkBtn;

    LinearLayout leaveComLayer;
    Button lComOkBtn;


    public ScDialog(Context context) {
        super(context);
    }

    public ScDialog(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScDialog(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void LayoutSet(OnClickListener listener) {

        moreLineView = (View)findViewById(R.id.moreLines);
        moreLayer = (LinearLayout) findViewById(R.id.moreLayer);
        moreShareBtn = (TextView) findViewById(R.id.moreShareBtn);
        moreFollowBtn = (TextView) findViewById(R.id.moreFollowBtn);

        leavePreLayer = (LinearLayout) findViewById(R.id.leavePreLayer);
        lPreCancelBtn = (Button) findViewById(R.id.lPreCancelBtn);
        lPreOkBtn = (Button) findViewById(R.id.lPreOkBtn);

        leaveComLayer = (LinearLayout) findViewById(R.id.leaveComLayer);
        lComOkBtn = (Button) findViewById(R.id.lComBtn);

    }

    public ScDialog moreAlert(OnClickListener listener, boolean eqaul, boolean follow) {
        LayoutSet(listener);
        moreLayer.setVisibility(VISIBLE);
        if(eqaul) {
            moreLineView.setVisibility(GONE);
            moreFollowBtn.setVisibility(GONE);
        }else {
            moreLineView.setVisibility(VISIBLE);
            moreFollowBtn.setVisibility(VISIBLE);
            if(follow) {
                moreFollowBtn.setText("언팔");
            }else {
                moreFollowBtn.setText("팔로우");
            }
        }

        moreShareBtn.setOnClickListener(listener);
        moreFollowBtn.setOnClickListener(listener);
        return this;
    }

    public ScDialog leavePreAlert(OnClickListener listener) {
        LayoutSet(listener);
        leavePreLayer.setVisibility(VISIBLE);
        lPreCancelBtn.setOnClickListener(listener);
        lPreOkBtn.setOnClickListener(listener);

        return this;
    }

    public ScDialog leaveComAlert(OnClickListener listener) {
        LayoutSet(listener);
        leaveComLayer.setVisibility(VISIBLE);
        lComOkBtn.setOnClickListener(listener);
        return this;
    }
}
