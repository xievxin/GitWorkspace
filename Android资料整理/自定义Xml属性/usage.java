1、res/values新建attr.xml
2、xmlns:custom="http://schemas.android.com/apk/res/[packageName]"
|| xmlns:custom="http://schemas.android.com/apk/res-auto"
<com.ckjr.ui.VerticalScrollTextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            custom:textColor="#555555"
            custom:textSize="@dimen/largeTextSize"
            >

3、public VerticalScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		if(attrs!=null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.verticalScrollView);
			textSize = ta.getDimension(R.styleable.verticalScrollView_textSize,
					context.getResources().getDimension(R.dimen.normalTextSize)) / AppConfig.sp;
			textColor = ta.getColor(R.styleable.verticalScrollView_textColor, 0xffffffff);
			setTextSize(textSize);
			setTextColor(textColor);
		}
	}

附：自定义属性详解 http://www.cnblogs.com/tiantianbyconan/archive/2012/06/06/2538528.html