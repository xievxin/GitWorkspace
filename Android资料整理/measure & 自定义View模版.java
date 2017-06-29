package com.example.xin.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xin on 2016/3/17.
 */
public class ViewEx extends View {

	private int defaultSize = 200;
	private int mWidth;
	private int mHeight;
	private int mColor;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	public ViewEx(Context context) {
		this(context, null);
	}

	public ViewEx(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//xmlns:app="http://schemas.android.com/apk/res-auto"自动找styleable
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewEx);
		mColor = a.getColor(R.styleable.ViewEx_viewex_color, Color.RED);
		a.recycle();

		init();
	}

	private void init() {
		mPaint.setColor(mColor);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int paddingTop = getPaddingTop();
		int paddingLeft = getPaddingLeft();
		int paddingBottom = getPaddingBottom();
		int paddingRight = getPaddingRight();
		mWidth = getWidth() - paddingLeft - paddingRight;
		mHeight = getHeight() - paddingTop - paddingBottom;
		if(mWidth>0 && mHeight>0) {
			int radius = Math.min(mWidth, mHeight) / 2;
			canvas.drawCircle(paddingLeft+mWidth/2, paddingTop+mHeight/2, radius, mPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/*
		 * MeasureSpec.AT_MOST	 	 wrap_content，不超过parent
		 * MeasureSpec.EXACTLY 		 match_parent
		 * MeasureSpec.UNSPECIFIED	 要多大给多大，即实际大小
		 */
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getMode(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if(widthMode==MeasureSpec.AT_MOST && heightMode==MeasureSpec.AT_MOST) {
			setMeasuredDimension(defaultSize, defaultSize);
		}else if(widthMode==MeasureSpec.AT_MOST) {
			setMeasuredDimension(defaultSize, heightSize);
		}else if(heightMode==MeasureSpec.AT_MOST) {
			setMeasuredDimension(widthSpec, defaultSize);
		}
	}
}
