package com.example.androidsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final int port = 8000;
	private String IP = "127.0.0.1";
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	Socket socket;
	PrintWriter pw;
	BufferedReader br;
	
	EditText ipAddress, editText, getMsgText;
	Button sendBtn, testBtn;
	String getMsg = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ipAddress = (EditText) findViewById(R.id.ipAddress);//IP地址
		getMsgText = (EditText) findViewById(R.id.showMsgText);//信息显示区
		editText = (EditText) findViewById(R.id.editText1);//消息编辑区
		testBtn = (Button) findViewById(R.id.testBtn);//测试连接
		sendBtn = (Button) findViewById(R.id.sendBtn);//发送按钮
		
		testBtn.setOnClickListener(onClickListener);
		sendBtn.setOnClickListener(onClickListener);
		editText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if(keyCode==66 && event.getAction()==KeyEvent.ACTION_DOWN){
					sendMessageOut();
				}
				return false;
			}
		});
	} 
	

	private void initIO(){
		try {
			pw = new PrintWriter(socket.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			while((getMsg=br.readLine())!=null){
				handler.sendEmptyMessage(0);
			}
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}
	}
	
	/**
	 * 异步处理网络
	 */
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				getMsgText.setText(getMsg+"\n");
				break;
			case 1:
				makeText("连接成功");
				setViewVisible(View.VISIBLE);//显示消息区域
				new Thread(){

					@Override
					public void run() {
						initIO(); 
					}
					
				}.start();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	}; 
	
	/**
	 * Button点击事件
	 */
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v==sendBtn){//发送
				sendMessageOut();
			}else if(v==testBtn){//连接*********一切都是从这里开始！！***************
				IP = ipAddress.getText().toString();
				new Thread(){
					
					@Override
					public void run() {
						String judge = SUCCESS;//判断连接是否成功
							try {
								socket = new Socket(IP, port);
							} catch (IOException e) {
								e.printStackTrace();
								Log.e("连接异常", e.toString());
								judge = FAIL;
								setViewVisible(View.INVISIBLE);//隐藏消息区域	
								//忽略超时异常，继续操作
								String errorMsg = "libcore.io.ErrnoException: connect failed: ETIMEDOUT (Connection timed out)";
								if(errorMsg.equals(e.getCause().toString())){
									return;
								}
								makeText("连接异常");
							}
						if(judge.equals(FAIL)){ 
							return;
						}
						handler.sendEmptyMessage(1);
					}
				}.start();
			}
			
		}
	};
	
	//消息通知
	private void makeText(String text) {
		Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
	}
	
	/**
	*发送消息
	*/
	private void sendMessageOut() {
		try {
			String messageOut = editText.getText().toString();
			if("\n".equals(messageOut)){
				return;
			}
			if(messageOut==null || "".equals(messageOut)){
				makeText("输入框不能为空！");
				return;
			}
			editText.setText("");
			pw.println(messageOut);
		} catch (Exception e) {
			Log.e("printlnException", e.toString()); 
		}
	}
	
	/**
	 * 隐藏或显示消息交流区域
	 * @param flag
	 */
	private void setViewVisible(int visibility) {
		getMsgText.setVisibility(visibility);
		editText.setVisibility(visibility);
		sendBtn.setVisibility(visibility);
	}
	
	/**
	 * 关闭流
	 */
	@Override
	protected void onDestroy() {
		try {
			if(socket!=null) {
				socket.close();
			}
			if(pw!=null) {
				pw.close();
			}
			if(br!=null) {
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onDestroy();
	}

	
}
