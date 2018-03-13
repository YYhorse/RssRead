package com.rssread.www.Util;

import android.animation.Animator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by yy on 2018/2/26.
 */
public class ViewAnimationUtils {

    public static void StartAnimation(final View view){
        view.post(new Runnable() {
            @Override
            public void run() {
                // 圆形动画的x坐标  位于View的中心
                int cx = (view.getLeft() + view.getRight()) / 2;
                //圆形动画的y坐标  位于View的中心
                int cy = (view.getTop() + view.getBottom()) / 2;
                //起始大小半径
                float startX=0f;
                //结束大小半径 大小为图片对角线的一半
                float startY= (float) Math.sqrt(cx*cx+cy*cy);
                Animator animator= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    animator = android.view.ViewAnimationUtils.createCircularReveal(view, cx, cy, startX, startY);
                }
                //在动画开始的地方速率改变比较慢,然后开始加速
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(400);
                animator.start();
                view.bringToFront();
            }
        });
    }
}
