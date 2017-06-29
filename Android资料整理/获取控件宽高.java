ViewTreeObserver vto2 = imageView.getViewTreeObserver(); 
vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() { 
@Override
public void onGlobalLayout() { 
imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this); 
textView.append("\n\n"+imageView.getHeight()+","+imageView.getWidth()); 
} 
}); 



int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		headerLayout.measure(w, h);
		rowHeight = headerLayout.getMeasuredHeight();