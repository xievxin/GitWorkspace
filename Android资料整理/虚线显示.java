View hLine = new View(context);
hLine.setBackgroundResource(R.drawable.xvxian);
hLine.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	//关闭硬件加速


xvxian.xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android" >
    <item>
        <shape android:shape="line">
            <stroke
                android:color="@color/grey_light"
                android:width="1dp"
		        android:dashWidth="@dimen/dividerHeight" 
		        android:dashGap="@dimen/dividerHeight" />     
        </shape>
    </item>

</layer-list>

Tip:width虚线高度，dashWidth破折宽度，dashGap间隔宽度
