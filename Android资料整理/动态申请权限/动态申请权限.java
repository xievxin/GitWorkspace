1、Androidmanifest.xml配置
	<uses-permission android:name="android.permission.CAMERA"/>
2、build.gradle
	compileSdkVersion 23
    buildToolsVersion "23.0.2"

    defaultConfig {
        minSdkVersion 14
        targetSdkVersion 23
    }
3、
	String camerPermission = "android.permission.CAMERA";
        if(PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(this, camerPermission)) {
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
        }else {
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this, camerPermission)) {
        	//拒绝后，下一次就走这里
//                Log.e("~", "");
//            }else {
                ActivityCompat.requestPermissions(this, new String[]{camerPermission}, 0);
//            }
        }


        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                	//授权成功
                    SurfaceHolder surfaceHolder = surfaceView.getHolder();
                    surfaceHolder.addCallback(this);
                }
                break;
            default:
                break;
        }
    }