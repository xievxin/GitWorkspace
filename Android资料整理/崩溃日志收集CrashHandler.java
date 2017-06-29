package com.ckjr.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.ckjr.util.DesUtil;
import com.ckjr.util.HttpUtil;

/**
 * 崩溃日志收集
 * @author xin
 * 使用方法：Application中 CrashHandler.getInstance().init(this);
 */
public class CrashHandler implements UncaughtExceptionHandler{
	
	private static CrashHandler Instance = null; 
	private UncaughtExceptionHandler defaultHandler;
	private Context context;
	private String path = Environment.getExternalStorageDirectory() + "/ckjr/log.txt";
	private Handler handler = new Handler();
	
	public static CrashHandler getInstance() {
		if(Instance==null)
			Instance = new CrashHandler();
		return Instance;
	}
	
	public void init(Context context) {
		this.context = context;
		defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(Instance);
	}

	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		if(context==null) {
			defaultHandler.uncaughtException(thread, ex);
			return;
		}
		new UploadErrorThread(thread, ex).start();
	}

	
	/**
	 * 处理日志
	 * @author xin
	 */
	class UploadErrorThread extends Thread {
		
		private Thread thread;
		private Throwable e;

		public UploadErrorThread(Thread thread, Throwable ex) {
			super();
			this.thread = thread;
			this.e = ex;
		}

		@Override
		public void run() {
			File file = null;
			StringBuffer sb = null;
			try {
				String errorMsg = "";
				String historyError = null;
				sb = new StringBuffer("（"+android.os.Build.MODEL+"："+android.os.Build.VERSION.SDK_INT+"）"); 
				for(StackTraceElement ste:e.getStackTrace()) {
					errorMsg = ste.getClassName()+"~"+ste.getMethodName()+"（"+ste.getFileName()+":"+ste.getLineNumber()+"）\n";
					if(errorMsg.contains("com.ckjr"))
						sb.append(errorMsg);
				}
				if(e.getCause()!=null) {
					sb.append(e.getCause().toString());
					for(StackTraceElement ste:e.getCause().getStackTrace()) {
						errorMsg = ste.getClassName()+"~"+ste.getMethodName()+"（"+ste.getFileName()+":"+ste.getLineNumber()+"）\n";
						if(errorMsg.contains("com.ckjr"))
							sb.append(errorMsg);
					}
				}else {
					sb.insert(0, e.getLocalizedMessage());
				}
				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo netInfo = manager.getActiveNetworkInfo();
				file = new File(path);
				//读取历史崩溃信息
				if(file.exists()) {
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
					String str = "";
					while((str=br.readLine())!=null) {
						historyError += str;
					}
					if(historyError.contains(DesUtil._3desEncode(historyError))) {
						sb.delete(0, sb.length());
					}
					sb.append(DesUtil._3desDecode(historyError));
					br.close();
				}else {
					file.createNewFile();
				}
				if(netInfo==null || !netInfo.isAvailable() || !netInfo.isConnected()) {
					writeToLocal(file, sb.toString());
				}else {
					//上传到服务器
					List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
					parameters.add(new BasicNameValuePair("appkey", AppConfig.APPKEY));
					parameters.add(new BasicNameValuePair("signa", AppConfig.SIGNA));
					parameters.add(new BasicNameValuePair("ts", AppConfig.TS));
					parameters.add(new BasicNameValuePair("log", sb.toString()));
					parameters.add(new BasicNameValuePair("type", "android"));
					String json = HttpUtil.post(context, AppConfig.ADDRESS+"mobile/log/logwrite.html", parameters);
					//上传完成后清空本地文件
					file.delete();
				}
			} catch (Exception e) {
				writeToLocal(file, sb.toString());
				e.printStackTrace();
			}
			defaultHandler.uncaughtException(thread, e);
		}
		
	}
	
	/**
	 * 没网存储到本地
	 * @param file
	 * @param str
	 * @throws FileNotFoundException
	 */
	private void writeToLocal(File file, String str) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(file), true);
			pw.print(DesUtil._3desEncode(str));
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
