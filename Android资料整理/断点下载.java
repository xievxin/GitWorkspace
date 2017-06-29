public static void pointDownload(Context context, String url, File pointFile, BufferUpdateListener bufferUpdateListener) throws Exception{
		init();
		String key = MD5.bytesToString(url.getBytes());
		long currentLength = pointFile.length();

		URL u = new URL(url);
		URLConnection con = u.openConnection();
		int fileLength = con.getContentLength();	
		URLConnection conn = u.openConnection();//链接打开，无法setRequestProperty，需要重新open
		if(listJo!=null && listJo.size()>0) {
			JsonObject jo = listJo.get(0);
			if(currentLength==currentLength) {
				if(bufferUpdateListener!=null)			//已经下载完成
					bufferUpdateListener.onBufferUpdate(100);
				return;
			}else if(currentLength>fileLength) {        //比下载的大，明显不对，直接删掉
				pointFile.delete();
				currentLength = 0;
			}
		}
		conn.setRequestProperty("Range", "bytes=" + currentLength + "-" + fileLength);
		InputStream is = conn.getInputStream();
		RandomAccessFile fos = new RandomAccessFile(pointFile, "rw");
		fos.seek(currentLength);	//从currentLength开始写入
		int len;
		byte buffer[] = new byte[1024];
		//downloadable本用于停止下载
		while(/*downloadable &&*/ (len=is.read(buffer))!=-1) {
			fos.write(buffer, 0, len);
			currentLength += len;
			if(bufferUpdateListener!=null)
				bufferUpdateListener.onBufferUpdate((int) (1f*currentLength/fileLength*100));
//			Log.e(TAG, 1f*currentLength/fileLength*100 + "%");
		}
		buffer = null;
		is.close();
		fos.close();
	}

	public interface BufferUpdateListener {
		public void onBufferUpdate(int percent);
	}