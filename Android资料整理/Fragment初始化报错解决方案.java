Unable to instantiate fragment make sure class name exists, is public, and has an empty constructor that is public

解决方案：
1、必须有空构造方法
2、Fragment不能作为内部类，即使作为内部类，我们也要加上static关键字（要不然无法反射到）
3、Activity.onCreate中 
	super.onCreate(null); 			//不使用保存状态
||	if(savedInstanceState==null){ }	//为空则addFragment