final Button btn = new Button(this);
		btn.setText("~1~");
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btn.setText("~Clicked~");
			}
		});
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.width = params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSPARENT;
		params.gravity = Gravity.CENTER;
		params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
		params.flags = LayoutParams.FLAG_NOT_FOCUSABLE /*| LayoutParams.FLAG_SHOW_WHEN_LOCKED*/;
		manager.addView(btn, params);