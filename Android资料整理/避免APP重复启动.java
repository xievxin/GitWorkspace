在启动路口onCreate方法中配置如下：

super.onCreate(savedInstanceState);
		if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){	//避免重复启动
			finish();
			return;
		}