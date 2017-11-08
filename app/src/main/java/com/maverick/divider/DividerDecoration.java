package com.maverick.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.maverick.adapter.PearBottomFragmentAdapter;
import com.maverick.type.PearItemType;

/**
 * Created by Administrator on 2017/11/6.
 */

public class DividerDecoration extends RecyclerView.ItemDecoration {


    private float mDividerHeight;
    private Paint mPaint;

    public DividerDecoration(int height, Context ctx) {
        this(height, Color.GRAY, ctx);
    }

    public DividerDecoration(int height, @ColorInt int color, Context ctx) {
        this.mDividerHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, ctx.getResources().getDisplayMetrics());
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if (((GridLayoutManager) layoutManager).getOrientation() == GridLayoutManager.HORIZONTAL) {
                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
                    outRect.set(0, 0, (int) mDividerHeight, 0);
            } else {

                PearBottomFragmentAdapter adapter = (PearBottomFragmentAdapter) parent.getAdapter();

                if(adapter.getItemViewType(parent.getChildAdapterPosition(view)) == PearItemType.ITEM) {
                    outRect.set(0, 0, 0, 0);
                }else {
                    outRect.set(0, 0, 0, (int) mDividerHeight);
                }


//                if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1 || parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 2)
//                    outRect.set(0, 0, 0, (int) mDividerHeight);
//                else
//                    outRect.set(0, 0, 0, 0);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            if (((GridLayoutManager) manager).getOrientation() == GridLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }
    }

    /**
     * 画divider (orientation为vertical)
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        // recyclerView是否设置了paddingLeft和paddingRight
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            // divider的top 应该是 item的bottom 加上 marginBottom 再加上 Y方向上的位移

                final int top = child.getBottom();
                // divider的bottom就是top加上divider的高度了
                final int bottom = (int) (top + mDividerHeight);
                c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * 画divider (当orientation为horizontal)
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        // 和drawVertical差不多 left right 与 top和bottom对调一下
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child));
            final int right = (int) (left + mDividerHeight);
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }
}
