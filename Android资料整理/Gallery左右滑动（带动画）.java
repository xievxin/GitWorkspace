dvGallery.onScroll(null, null, 1, 0);
advGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);	//向右滑动
advGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);		//向左滑动




//一次性只滑动一页
@Override
public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
	int keyCode;
	if(e2.getX()>e1.getX()) {
		keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
	}else {
		keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
	}
	onKeyDown(keyCode, null);
	return true;
}