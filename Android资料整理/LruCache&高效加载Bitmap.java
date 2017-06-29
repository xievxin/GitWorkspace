private ImageLoader() {  
        // 获取应用程序最大可用内存  
        int maxMemory = (int) Runtime.getRuntime().maxMemory();  
        int cacheSize = maxMemory / 8;  
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {  
            @Override  
            protected int sizeOf(String key, Bitmap bitmap) {  
                return bitmap.getByteCount();  	//默认返回1，需重写此方法
            }  
        };  
    }  


//BitmapFactory.Options参数，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存
public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,  
        int reqWidth, int reqHeight) {  
    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
    final BitmapFactory.Options options = new BitmapFactory.Options();  
    options.inJustDecodeBounds = true;  
    BitmapFactory.decodeResource(res, resId, options);  
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
    options.inJustDecodeBounds = false;  
    return BitmapFactory.decodeResource(res, resId, options);  
}  

public static int calculateInSampleSize(BitmapFactory.Options options,  
        int reqWidth, int reqHeight) {  
    // 源图片的高度和宽度  
    final int height = options.outHeight;  
    final int width = options.outWidth;  
    int inSampleSize = 1;  
    if (height > reqHeight || width > reqWidth) {  
        final int heightRatio = Math.round((float) height / (float) reqHeight);  
        final int widthRatio = Math.round((float) width / (float) reqWidth);  
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
    }  
    return inSampleSize;  
}  


