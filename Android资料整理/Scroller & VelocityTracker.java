防止水平方向正在滑动时竖直滑动，水平方向停留在中间状态；
mScroller = new Scroller(context);

case ACTION_DOWN:
	if(!mScroller.isFinished()) {
		mScroller.abortAnimation();	//中止滑动
		intercept = true;
	}
	break;


mScroller.startScroll(getScrollX(), 0, (int) (childIndex * mWidth - getScrollX()), 0, 500);
配合
@Override
	public void computeScroll() {
		if(mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
使用


VelocityTracker计算手势速度：

mVelocityTracker.computeCurrentVelocity(1000);	//1s
float xVelocity = mVelocityTracker.getXVelocity();	//x轴速度
	if(Math.abs(xVelocity)>50) {
		childIndex = xVelocity>0?childIndex-1:childIndex+1;
	}else {
		childIndex = (int) ((getScrollX()+mWidth/2) / mWidth);
	}
	childIndex = childIndex<0?0:childIndex;
	childIndex = childIndex>=mList.size()?mList.size()-1:childIndex;
	mScroller.startScroll(getScrollX(), 0, (int) (childIndex * mWidth - getScrollX()), 0, 500);
	invalidate();
mVelocityTracker.clear();	//清空