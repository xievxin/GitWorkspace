//通过XML存储数据
SharedPreferences sp = getSharedPreferences("mySp", 
							Context.MODE_PRIVATE);//如果允许其他应用访问，则应该为Context.MODE_WORLD_READABLE
Editor editor = sp.edit();
editor.putString("name", "xievxin");
editor.putBoolean("flag", true);
editor.putInt("id", 1);
editor.commit();
Log.e("值", sp.getString("name", "1"));//1为默认值



Context context = createPackageContext("com.example.othersp", Context.CONTEXT_IGNORE_SECURITY);
SharedPreferences sp = context.getSharedPreferences("mySp", Context.MODE_WORLD_READABLE);
Log.e("值", sp.getString("name", "detaultValue"));