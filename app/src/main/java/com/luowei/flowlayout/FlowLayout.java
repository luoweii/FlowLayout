package com.luowei.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 骆巍 on 2016/2/16.
 */
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int lineWidth = getPaddingLeft() + getPaddingRight();
        int maxWidth = 0;
        int lineHeight = 0;
        int maxHeight = getPaddingTop() + getPaddingBottom();

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            //判断换行
            if (lineWidth + child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin > widthSize) {
                lineWidth = getPaddingLeft() + getPaddingRight();
                maxHeight += lineHeight;
                lineHeight = 0;
            }
            lineWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            maxWidth = Math.max(maxWidth, lineWidth);
            lineHeight = Math.max(child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin, lineHeight);
        }
        maxHeight += lineHeight;
        maxWidth = Math.min(maxWidth, widthSize);

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxWidth,
                heightMode == MeasureSpec.EXACTLY ? heightSize : maxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //容器宽度
        int width = getWidth();
        int height = getHeight();

        //已经使用了的高度
        int usedHeight = getPaddingTop();
        //子View起始位置
        int left = getPaddingLeft();
        int top = getPaddingTop();

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) continue;
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //如果当前行容不下下一个子View就换行
            if (left + childWidth + lp.leftMargin + lp.rightMargin + getPaddingRight() > width) {
                left = getPaddingLeft();
                top = usedHeight;
            }
            int cl = left + lp.leftMargin;
            int ct = top + lp.topMargin;
            int cr = cl + childWidth;
            int cb = ct + childHeight;
            child.layout(cl, ct, cr, cb);
            left = cr + lp.rightMargin;
            usedHeight = Math.max(cb + lp.bottomMargin, usedHeight);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
