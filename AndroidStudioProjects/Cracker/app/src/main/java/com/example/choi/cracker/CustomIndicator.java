package com.example.choi.cracker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by choi on 2017. 7. 17..
 */

public class CustomIndicator extends LinearLayout {
    private Context context;
    private int margin = 25;
    private int selectdot = R.drawable.dot_select;
    private int deselectdot = R.drawable.dot_deselect;
    private ImageView[] Dot;

    public CustomIndicator(Context context) {
        super(context);
        this.context = context;
    }

    public CustomIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void creatIndicator(int count, int indicator_size) {
        int i;
        Dot = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER | Gravity.BOTTOM;
        params.topMargin = margin;
        params.bottomMargin = 28;
        params.leftMargin = margin;
        params.rightMargin = margin;
        params.width = indicator_size;
        params.height = indicator_size;
        for (i = 0; i < count; i++) {
            Dot[i] = new ImageView(context);
            Dot[i].setLayoutParams(params);
            Dot[i].setImageResource(deselectdot);
            this.addView(Dot[i]);
        }
    }

    public void select(int position) {
        int i;
        for (i = 0; i < Dot.length; i++) {
            if (i == position) {
                Dot[i].setImageResource(selectdot);
                Dot[i].animate().scaleY(1.4f).scaleX(1.4f).start();
            } else {
                Dot[i].setImageResource(deselectdot);
                Dot[i].animate().scaleX(1f).scaleY(1f).start();
            }
        }
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public void setSelectdot(int selectdot) {
        this.selectdot = selectdot;
    }

    public void setDeselectdot(int deselectdot) {
        this.deselectdot = deselectdot;
    }
}