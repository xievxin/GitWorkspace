Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.about_logo)).getBitmap();
                Bitmap overlay = Bitmap.createBitmap(bmp.getWidth()/3, bmp.getHeight()/3, Bitmap.Config.ARGB_8888);
                Canvas cvs = new Canvas(overlay);
                cvs.scale(1 / 3f, 1 / 3f);	//以左上角为原点，缩小三分之一，刚好完整画到overlay上
                Paint paint = new Paint();
                paint.setFlags(Paint.FILTER_BITMAP_FLAG);
                cvs.drawBitmap(bmp, 0, 0, paint);
                iv.setBackgroundDrawable(new BitmapDrawable(FastBlur.doBlur(overlay, 7, true)));