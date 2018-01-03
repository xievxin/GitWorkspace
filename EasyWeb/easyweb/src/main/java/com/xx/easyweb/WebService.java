package com.xx.easyweb;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.RemoteViews;

import com.xx.model.ServiceBean;
import com.xx.model.WebBeans;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xievxin on 2017/11/8.
 */

public class WebService extends Service {

    public static AtomicInteger activityCount = new AtomicInteger();
    private String webclassName;
    private String intentServiceName;

    /**
     * 支持同时4个UI修改
     */
    Class<? extends ServiceBean>[] contentBeans = new Class[]{
            WebBeans.SetContentServiceBean.class,
            WebBeans.SetContentServiceBean01.class,
            WebBeans.SetContentServiceBean02.class,
            WebBeans.SetContentServiceBean03.class
    };

    @Override
    public IBinder onBind(final Intent intent) {
        return new IWeb.Stub() {

            @Override
            public void loadUrl(String url) throws RemoteException {
                ServiceBean.obtain(WebBeans.LoadUrlServiceBean.class).setObj1(url).postSticky();
                startWebActivity();
            }

            @Override
            public void postUrl(String url, byte[] bytes) throws RemoteException {
                ServiceBean.obtain(WebBeans.PostUrlServiceBean.class).setObj1(url).setObj2(bytes).postSticky();
                startWebActivity();
            }

            @Override
            public void loadData(String data, String mimeType, String encoding) throws RemoteException {
                ContentValues values = new ContentValues();
                values.put("data", data);
                values.put("mimeType", mimeType);
                values.put("encoding", encoding);
                ServiceBean.obtain(WebBeans.LoadDataServiceBean.class).setValues(values).postSticky();

                startWebActivity();
            }

            @Override
            public void closeAll() throws RemoteException {
                ServiceBean.obtain(WebBeans.FinishServiceBean.class).post();
            }

            @Override
            public void setContent(RemoteViews remoteViews) throws RemoteException {
                for(Class cls : contentBeans) {
                    if(EventBus.getDefault().getStickyEvent(cls)==null) {
                        ServiceBean.obtain(cls).setObj1(remoteViews).postSticky();
                        break;
                    }
                }
            }

            @Override
            public void setWebClass(String webclassName) throws RemoteException {
                WebService.this.webclassName = webclassName;
            }

            @Override
            public void setIntentService(String intentServiceName) throws RemoteException {
                WebService.this.intentServiceName = intentServiceName;
            }

            @Override
            public boolean setStaticString(String className, String fieldName, String value) throws RemoteException {
                return setValue(className, fieldName, value);
            }

            @Override
            public boolean setStaticBoolean(String className, String fieldName, boolean value) throws RemoteException {
                return setValue(className, fieldName, value);
            }

            @Override
            public boolean setStaticInteger(String className, String fieldName, int value) throws RemoteException {
                return setValue(className, fieldName, value);
            }

        };
    }

    private void startWebActivity() {
        if(activityCount.get()==0) {
            Intent webIntent = new Intent(WebService.this, WebActivity.class);
            webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            webIntent.putExtra("webview", webclassName);
            webIntent.putExtra("intentService", intentServiceName);
            startActivity(webIntent);
        }
    }

    private boolean setValue(String className, String fieldName, Object value) {
        try {
            Class cls = Class.forName(className);
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(null, value);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
