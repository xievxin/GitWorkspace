package com.ckjr.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.ckjr.context.R;

/**
 * 不报tring to use a recyled bitmap
 * Created by xin on 2016/6/15.
 */
public class UnRecyledErrorView extends ImageView {

	private Drawable defaultRrawable;
	private Context context;

	public UnRecyledErrorView(Context context) {
		this(context, null);
	}

	public UnRecyledErrorView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UnRecyledErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		if(attrs!=null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.unRecyledErrorView);
			defaultRrawable = ta.getDrawable(R.styleable.unRecyledErrorView_defaultSrc);		//默认资源
			ta.recycle();
		}
	}

	/**
	 * 设置默认背景资源图片
	 * @param defaultResId
	 */
	public void setDefaultResId(int defaultResId) {
		this.defaultRrawable = new BitmapDrawable(BitmapFactory.decodeResource(context.getResources(), defaultResId));
	}

	@Override
	public void draw(Canvas canvas) {
		try {
			super.draw(canvas);
		}catch (RuntimeException re) {	//catch RuntimeException:trying to using a recyled bitmap
			if(defaultRrawable!=null)
				setBackgroundDrawable(defaultRrawable);
			Log.e("UnRecyledErrorView", re.toString());
			re.printStackTrace();
		}catch (Exception e) {
			Log.e("UnRecyledErrorView", e.toString());
			e.printStackTrace();
		}
	}
}
