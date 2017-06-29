package com.example.bluetooth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.adapter.MyAdapter;

public class MainActivity extends Activity {

	/*<uses-permission android:name="android.permission.BLUETOOTH"/>  加入蓝牙权限
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />*/
	
	private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");// 固定的UUID
	
	private boolean hasregister = false;
	private final int REQUEST_CODE_ENABLE = 110;
	private final int REQUEST_CODE_DISCOVERABLE = 111;
	BluetoothAdapter bluetoothAdapter;
	BluetoothDevice blueDevice;
	BluetoothServerSocket serverSocket; 
	BluetoothSocket socket;
	List<BluetoothDevice> listDevice = new ArrayList<BluetoothDevice>();
	Vector<String> v = new Vector<String>();
	
	ListView listview;
	Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initView();
		setBlueTooth();
		
	}
	
	/**
	 * 初始化视图 
	 */
	private void initView() { 
		listview = (ListView) findViewById(R.id.listView);
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		listview.setOnItemClickListener(onItemClickListener);
	}
	
	/**
	 * 蓝牙设置
	 */
	private void setBlueTooth() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter!=null){//设备有蓝牙
			//开启
			if(!bluetoothAdapter.isEnabled()){
				//请求开启蓝牙
				Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(i, REQUEST_CODE_ENABLE);
			}else {
				onRegister();
			}
		}else {
			new AlertDialog.Builder(this).setTitle("提示").setMessage("设备不支持蓝牙")
				.setNegativeButton("关闭", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				})
				.setCancelable(false).show();
		}
	}

	/** 
	 * 获取startActivityForResult()返回结果
	 */ 
	@Override  
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE_ENABLE){
			if(resultCode == RESULT_OK) {  
				//设置可见 
				Intent visiI = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				visiI.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);//300秒可见
				startActivityForResult(visiI, REQUEST_CODE_DISCOVERABLE);
            }else if(resultCode == RESULT_CANCELED) {  
                finish();  
            }
		}
		else if(requestCode==REQUEST_CODE_DISCOVERABLE){
			onRegister();
		}
	}

	private void onRegister() {
		//注册蓝牙接收广播
		if(!hasregister){
			IntentFilter ifStart = new IntentFilter(BluetoothDevice.ACTION_FOUND);
			registerReceiver(receiver, ifStart);
			hasregister = true;
			blueStartDiscovery();
		}
		super.onStart();
	}
	
	/**
	 * 开启蓝牙搜索，20s后停止
	 */
	private void blueStartDiscovery() {
		bluetoothAdapter.startDiscovery();//开始搜索
		new Thread(){//20s后停止搜索

			@Override
			public void run() {
				try {
					Thread.sleep(20000);
					if(bluetoothAdapter!=null){
						bluetoothAdapter.cancelDiscovery();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}.start();
	}
	
	/**
	 * 广播
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(BluetoothDevice.ACTION_FOUND)){
				blueDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				//if(blueDevice.getBondState()!=BluetoothDevice.BOND_BONDED){
					if(v!=null && v.contains(blueDevice.getAddress())){
						
					}else {
						v.add(blueDevice.getAddress());
						listDevice.add(blueDevice);
						listview.setAdapter(new MyAdapter(listDevice, MainActivity.this));
					}
				//}
			}
		}
	};
	
	/**
	 * ListView的item监听事件
	 */
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
			//取消搜索
			bluetoothAdapter.cancelDiscovery();
			//bluetoothAdapter = null;
			
			BluetoothDevice newBlueTooth = listDevice.get(position);
			connect(newBlueTooth);//未配对会自动先配对，直接连接即可
		}
	};
	
	/**
	 * 蓝牙设备连接
	 * @param device
	 * @throws IOException
	 */
	private void connect(BluetoothDevice device) {  
		try {
			socket = device.createRfcommSocketToServiceRecord(uuid);
			socket.connect();
			Toast.makeText(MainActivity.this, "连接成功！", Toast.LENGTH_SHORT).show();
			//进入交流页面
			SecondActivity.socket = socket;
			MainActivity.this.startActivity(new Intent(MainActivity.this, SecondActivity.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}  
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "开启服务端");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case 1:
			bluetoothAdapter.cancelDiscovery();
			try {
				serverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(bluetoothAdapter.getName(), uuid);
				//new AlertDialog.Builder(MainActivity.this).setMessage("正在开启服务器...").setCancelable(false).show();
				new Thread(){

					@Override
					public void run() {
						try {
							socket = serverSocket.accept(10000);
							//进入交流页面
							SecondActivity.socket = socket;
							MainActivity.this.startActivity(new Intent(MainActivity.this, SecondActivity.class));
						} catch (IOException e) {
							if("java.io.IOException: Connection timed out".equals(e.toString())){
								handler.sendEmptyMessage(0);
							}else {
								e.printStackTrace();
							}
						}
					}
					
				}.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		return true; 
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(MainActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};

	
	@Override
	protected void onDestroy() {
		if(socket!=null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		hasregister = false;
		v.clear();
		super.onDestroy();
	}

	

}
