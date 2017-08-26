package com.gaohui.parallaxeffect.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class MyListView extends ListView {

	private ImageView mImage;
	private int mOriginalHeight;
	private int drawableHeight;
	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
								   int scrollY, int scrollRangeX, int scrollRangeY,
								   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		Log.d("TAG", "deltaY: " +deltaY + " scrollY: " + scrollY + " scrollRangeY: " + scrollRangeY
				+ " maxOverScrollY: " + maxOverScrollY + " isTouchEvent: " + isTouchEvent);

		// 手指拉动 并且 是下拉
		if(isTouchEvent && deltaY < 0) {
			// 把拉动的瞬时变化量的绝对值交给Header, 就可以实现放大效果
			if(mImage.getHeight() <= drawableHeight ) {
				int newHeight = mImage.getHeight() + Math.abs(deltaY);

				mImage.getLayoutParams().height = newHeight;
				mImage.requestLayout();
			}

		}

		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
			case MotionEvent.ACTION_UP:
				//执行回弹动画  方式一：属性动画
				// 从当前高度mImage.getHeight(), 执行动画到原始高度mOriginalHeight
				final int startHeight = mImage.getHeight();
				final int endHeight = mOriginalHeight;
				Log.d("TAG","onTouchEvent ACTION_UP");
				valueAnimator(startHeight, endHeight);
				break;
		}
		return super.onTouchEvent(ev);
	}

	@SuppressLint("NewApi")
	private void valueAnimator(final int startHeight, final int endHeight) {
		ValueAnimator valueAnimator = ValueAnimator.ofInt(1);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float fraction = animation.getAnimatedFraction();
				Integer newHeight = evaluate(fraction,startHeight,endHeight);
				Log.d("TAG","evaluate newHeight: " + newHeight);
				mImage.getLayoutParams().height = newHeight;
				mImage.requestLayout();
			}
		});
		//TODO 插值器
		valueAnimator.setInterpolator(new OvershootInterpolator());
		valueAnimator.setDuration(500);
		valueAnimator.start();
	}

	protected Integer evaluate(float fraction, int startValue, int endValue) {
		int startInt = startValue;
		return (int)(startInt + fraction * (endValue - startInt));
	}

	public void setParallaxImage(ImageView mImage) {
		this.mImage = mImage;
		mOriginalHeight = mImage.getHeight(); //180
		drawableHeight = mImage.getDrawable().getIntrinsicHeight();  //488
		Log.d("TAG", "height: " + mOriginalHeight + " drawableHeight: " + drawableHeight);
	}
}
