package com.example.main;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;

public class HttpUtils {
	
	static{
		if(android.os.Build.VERSION.SDK_INT>9) {
			ThreadPolicy tp = new ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(tp);
		}
	}

	/**
	 * POST提交
	 * @return
	 * @throws Exception
	 */
	public static String post(String url) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("userName", "xievxin"));
		parameters.add(new BasicNameValuePair("password", "123456"));
		HttpEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
		post.setEntity(entity);
		HttpResponse response = httpclient.execute(post);
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}
	
	/**
	 * GET提交
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String get(String url) throws ParseException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = httpClient.execute(get);
		String result = EntityUtils.toString(response.getEntity());
		return result;
	}
	
	/**
	 * 读取数据流图片
	 * @throws Exception
	 */
	public static Bitmap loadImg(String url) throws Exception {
		URL u = new URL(url);
		HttpURLConnection con = (HttpURLConnection) u.openConnection(); 
		InputStream is = con.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int index = 0;
		while((index=is.read(data))!=-1) {
			out.write(data, 0, index);
		}
		Bitmap map = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.toByteArray().length);
		is.close();
		out.close();
		return map;
	}
	
}
