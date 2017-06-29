public class XxPopupWindow extends PopupWindow {

	/*可用于背景置灰
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
	*/

    public XxPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        contentView.setPadding(contentView.getLeft(), contentView.getPaddingTop(),
                contentView.getPaddingRight(), StyleUtil.getNavigationBarHeight(contentView.getContext())); //不与底部NavigationBar重叠
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));    //返回键消失
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP_MR1)     //sdk 22以上，decorView fitSystemwindow true
            setAttachedInDecor(false);
    }

}
