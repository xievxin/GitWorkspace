1、AndroidManifest.xml中配置：

<manifest 
    android:installLocation="internalOnly"  //装载SD卡里接收不到启动广播，装在内存中
    >


<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<receiver android:name="com.ckjr.receiver.BootReceiver">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
                <category android:name="android.intent.category.HOME" />
            </intent-filter>
            /*<intent-filter >
                <action android:name="android.intent.action.MEDIA_MOUNTED"/>
                <action android:name="android.intent.action.MEDIA_UNMOUNTED"/>
                <data android:scheme="file"></data>
            </intent-filter>*/
        </receiver>

2、实现广播
public class BootReceiver extends BroadcastReceiver {

	/*开机、挂载SD卡开机时都会有广播，最好只接收BOOT_COMPLETED，不确定插SD卡是否会发送挂载广播*/
	public static final String BootAction = "android.intent.action.BOOT_COMPLETED";		//开机广播
	public static final String SdMOUNTED_Action = "android.intent.action.MEDIA_MOUNTED";//SD卡挂载广播

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.e("BootReceiver", "onReceive " + action);
		CommonUtil.toastLong(context.getApplicationContext(), action);
	}
}

3、可能还没接收到的原因如下：
打开自启动，允许APP自启动；
需要手动启动一次；
查看一下系统中是否有一类似360管家的软件、他们会默认将一些开机广播给屏蔽掉、加快开机速度、只需将其打开即可。