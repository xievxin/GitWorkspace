/**
     * 获取正在运行桌面包名（注：存在多个桌面时且未指定默认桌面时，该方法返回Null,使用时需处理这个情况）
     */
    public static String getLauncherPackageName(Context context) {
        final Intent intent = new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            return null;
        }
        if (res.activityInfo.packageName.equals( "android" )) {
            // 有多个桌面程序存在，且未指定默认项时；
            return null;
        } else {
            return res.activityInfo.packageName;
        }
    }
    
    private boolean hasShortCut() {
		String launcher = getLauncherPackageName( LoadingActivity.this );
		if (launcher != null) {
			String url = "content://" + launcher + ".settings/favorites?notify=true";
			ContentResolver resolver = getContentResolver();
			Cursor cursor = resolver.query( Uri.parse( url ), null, "title=?", new String[]{optString( R.string.app_name )}, null );
			if (cursor != null && cursor.moveToFirst()) {
				cursor.close();
				return true;
			}
			return false;
		} else {
			return true;
		}
    }

    /**
     * 创建桌面快捷方式
     *
     * @return true if create success
     */
    private void createShortCut() {
        Intent shortcutIntent = new Intent(ACTION_ADD_SHORTCUT);
        shortcutIntent.putExtra("duplicate", false);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, optString(R.string.app_name));
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(this, R.drawable.about_logo));
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(this, LoadingActivity.class));
        sendBroadcast(shortcutIntent);
    }


    /*权限添加
    <uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
    <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />

    <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.android.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.android.launcher2.permission.READ_SETTINGS" />
    <uses-permission android:name="com.android.launcher2.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.android.launcher3.permission.READ_SETTINGS" />
    <uses-permission android:name="com.android.launcher3.permission.WRITE_SETTINGS" />
    <uses-permission android:name="org.adw.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="org.adw.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.htc.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.htc.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.qihoo360.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.qihoo360.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.lge.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.lge.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="net.qihoo.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="net.qihoo.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="org.adwfreak.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="org.adwfreak.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="org.adw.launcher_donut.permission.READ_SETTINGS" />
    <uses-permission android:name="org.adw.launcher_donut.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.huawei.launcher3.permission.READ_SETTINGS" />
    <uses-permission android:name="com.huawei.launcher3.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.fede.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.fede.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.sec.android.app.twlauncher.settings.READ_SETTINGS" />
    <uses-permission android:name="com.sec.android.app.twlauncher.settings.WRITE_SETTINGS" />
    <uses-permission android:name="com.anddoes.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.anddoes.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.tencent.qqlauncher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.tencent.qqlauncher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.huawei.launcher2.permission.READ_SETTINGS" />
    <uses-permission android:name="com.huawei.launcher2.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.android.mylauncher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.android.mylauncher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.ebproductions.android.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.ebproductions.android.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.oppo.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.oppo.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="com.huawei.android.launcher.permission.READ_SETTINGS" />
    <uses-permission android:name="com.huawei.android.launcher.permission.WRITE_SETTINGS" />
    <uses-permission android:name="telecom.mdesk.permission.READ_SETTINGS" />
    <uses-permission android:name="telecom.mdesk.permission.WRITE_SETTINGS" />
    <uses-permission android:name="dianxin.permission.ACCESS_LAUNCHER_DATA" />*/