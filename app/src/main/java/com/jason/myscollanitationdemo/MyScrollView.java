package com.jason.myscollanitationdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * @Explain：自定义ScrollView，获取滚动监听，根据不同情况进行动画；
 * @Author:LYL
 * @Version:
 * @Time:2017/6/14
 */

public class MyScrollView extends ScrollView {
    private MyLinearLayout mMyLinearLayout;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //获取内部LinearLayout；
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMyLinearLayout = (MyLinearLayout) getChildAt(0);
    }

    //将第一张图片设置成全屏；
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMyLinearLayout.getChildAt(0).getLayoutParams().height = getHeight();
        mMyLinearLayout.getChildAt(0).getLayoutParams().width = getWidth();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int scrollViewHeight = getHeight();
        for (int i = 0; i < mMyLinearLayout.getChildCount(); i++) {
            //如果子控件不是MyFrameLayout则循环下一个子控件；
            View child = mMyLinearLayout.getChildAt(i);
            if (!(child instanceof MyFrameLayout)) {
                continue;
            }
            //以下为执行动画逻辑；
            MyFrameLayoutAnitationCallBack myCallBack = (MyFrameLayoutAnitationCallBack) child;
            //获取子View高度；
            int childHeight = child.getHeight();
            //子控件到父控件的距离；
            int childTop = child.getTop();
            //滚动过程中，子View距离父控件顶部距离；
            int childAbsluteTop = childTop - t;
            //进入了屏幕
            if (childAbsluteTop <= scrollViewHeight) {
                //当前子控件显示出来的高度；
                int childShowHeight = scrollViewHeight - childAbsluteTop;
                float moveRadio = childShowHeight / (float) childHeight;//这里一定要转化成float类型；
                //执行动画；
                myCallBack.excuteAnitation(getMiddleValue(moveRadio, 0, 1));
            } else {
                //没在屏幕内,恢复数据；
                myCallBack.resetViewAnitation();
            }
        }
    }

    /**
     * 求中间大小的值；
     *
     * @param radio
     * @param minValue
     * @param maxValue
     * @return
     */
    private float getMiddleValue(float radio, float minValue, float maxValue) {
        return Math.max(Math.min(maxValue, radio), minValue);
    }
}
