package com.xievxin.gtapps;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.xievxin.gtapps.AppDetailActivity.staticSvcName;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = "MainActivity";

    ListView listView;
    Button reloadBtn;
    List<String> appList;
    String[] reqestPerArr;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        listView = findViewById(R.id.listview);
        reloadBtn = findViewById(R.id.reloadBtn);
        listView.setOnItemClickListener(this);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadBtn.setVisibility(View.GONE);
                applyPermission();
            }
        });

        applyPermission();
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 5_000);*/
        test();
    }

    void test() {
        /*Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        mIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(mIntent);*/
//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName("com.example.xievxin.companytest01",
//                "com.example.xievxin.companytest01.TestService");
//        intent.setComponent(componentName);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
////            startActivity(intent);
//            bindService(intent, conn, Service.BIND_AUTO_CREATE);
//        }else {
//            startService(intent);
//        }


//        BufferedReader reader = null;
//        String content = "";
//        try {
//            Process process = Runtime.getRuntime().exec("ps | grep sina | awk '{print $2}' | xargs adb shell kill -9");
//            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            StringBuffer output = new StringBuffer();
//            int read;
//            char[] buffer = new char[4096];
//            while ((read = reader.read(buffer)) > 0) {
//                output.append(buffer, 0, read);
//            }
//            reader.close();
//            content = output.toString();
//            Log.i(TAG, content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//
//        try {
//            Method method = manager.getClass().getDeclaredMethod("getService");
//            method.setAccessible(true);
//            Object amsProxy = method.invoke(manager);
//
//            Method[] mh01 = amsProxy.getClass().getDeclaredMethods();
//            Field[] fields = amsProxy.getClass().getDeclaredFields();
//
//            // 获取ActiveServices mServices;
//            Field field=amsProxy.getClass().getDeclaredField("mServices");
//            Object activeServices = field.get(amsProxy);
//            // 调用ActiveServices.getRunningServiceInfoLocked(int maxNum, int flags,
//            //        int callingUid, boolean allowed, boolean canInteractAcrossUsers) {
//            Method getSvcMethod = activeServices.getClass().getDeclaredMethod("getRunningServiceInfoLocked", int.class,
//                    int.class, int.class, boolean.class, boolean.class);
//            getSvcMethod.setAccessible(true);
//            Object servicesList = getSvcMethod.invoke(activeServices, Integer.MAX_VALUE, 0, Binder.getCallingUid(), true, false);
//            Log.i(TAG, "test: succ!!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    void applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String needPerStr = "";
            for(String per:AppFinder.needsPermissions) {
                if(checkSelfPermission(per) != PackageManager.PERMISSION_GRANTED) {
                    needPerStr += "," + per;
                }
            }
            if(TextUtils.isEmpty(needPerStr)) {
                load();
                return;
            }

            reqestPerArr = needPerStr.replaceFirst(",", "").split(",");
            requestPermissions(reqestPerArr, 0);
        }else {
            load();
        }
    }

    void load() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appList = AppFinder.scanGTApps(MainActivity.this);

                if(appList!=null && appList.size()>0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(adapter);
                        }
                    });
                }else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.removeAllViews();
                            reloadBtn.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "未找到接入个推应用", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.i(TAG, "run: appList is null");
                }
            }
        }).start();
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return appList==null?0:appList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null) {
                holder = new ViewHolder(MainActivity.this);
                convertView = holder.vg;
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                final String pkgname = appList.get(position);
                ApplicationInfo info = getPackageManager().getApplicationInfo(pkgname, 0);

                holder.img.setBackgroundDrawable(getPackageManager().getApplicationIcon(pkgname));
                holder.label.setText(getPackageManager().getApplicationLabel(info));
                holder.packageName.setText("包名: " + pkgname);

                final ViewHolder tempHolder = holder;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        boolean flag = AppFinder.isServiceWorked(MainActivity.this, pkgname, AppFinder.getDynamicSvcName(pkgname))
                                || AppFinder.isServiceWorked(MainActivity.this, pkgname, staticSvcName);
                        if(flag) {
                            tempHolder.state.setText("服务正在运行");
                            tempHolder.state.setTextColor(Color.parseColor("#1fbf00"));
                        }else {
                            tempHolder.state.setText("暂未启动");
                            tempHolder.state.setTextColor(Color.parseColor("#bf4f00"));
                        }
                    }
                });
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String pkgName = appList.get(position);
        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra("name", pkgName);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions.length<=0) {
            return;
        }

        boolean allPassed = true;
        for(int resCode:grantResults) {
            if(resCode!=PackageManager.PERMISSION_GRANTED) {
                allPassed = false;
                break;
            }
        }
        if(allPassed) {
            load();
        }else {
            reloadBtn.setVisibility(View.VISIBLE);
            Toast.makeText(this, "权限被拒绝，请前往设置页面开启", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        unbindService(conn);
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    class ViewHolder {

        ViewGroup vg;
        ImageView img;
        TextView label;
        TextView packageName;
        TextView state;

        public ViewHolder(Context context) {
            vg = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pkg_list_item, null);
            img = vg.findViewById(R.id.img);
            label = vg.findViewById(R.id.label);
            packageName = vg.findViewById(R.id.packageName);
            state = vg.findViewById(R.id.state);
        }
    }
}
