① ：灰色保活，启动前台进程，不显示推送
public class NotificationService extends Service {
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(LOGTAG, "onStart()...");
        LogSaver.save("onStart()...");

        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return START_STICKY;
    }

}

public class GrayInnerService extends Service {

    public final static int GRAY_SERVICE_ID = 1001; //9371?

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GRAY_SERVICE_ID, new Notification());
        stopForeground(true);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

}
