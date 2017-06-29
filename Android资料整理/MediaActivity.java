package com.example.xin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity implements MediaPlayer.OnCompletionListener,OnClickListener,
		MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,SeekBar.OnSeekBarChangeListener,
		MediaPlayer.OnBufferingUpdateListener{

	public static final String videoPath = "http://vlive.qqvideo.tc.qq.com/w0016xumyqi.mp4?type=mp4&fmt=mp4&vkey=ECE285CF8B4D7C3360D31D9DACC0A892DBC45B3FB6229787DE2F146AF4B4B50B8DFFE54C38D2BF5925867CDEB879BC1E31672557FEBAA0B79D8DEB07200213CB1224C957A7CFE8F6FEDC3B0279307993236C7AEB0F037746";
	private SurfaceView surfaceView;
	private PowerManager powerManager;
	private PowerManager.WakeLock wakeLock;
	private Button btn;
	private Button repayBtn;
	private Button showDlgBtn;
	private SeekBar progressBar;
	private MediaPlayer mediaPlayer;
	private SurfaceHolder surfaceHolder;
	private int currentPosition = -1;

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "mm");

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		progressBar = (SeekBar) findViewById(R.id.progressBar);
		btn = (Button) findViewById(R.id.btn);
		repayBtn = (Button) findViewById(R.id.repayBtn);
		showDlgBtn = (Button) findViewById(R.id.showDlgBtn);
		btn.setOnClickListener(this);
		repayBtn.setOnClickListener(this);
		showDlgBtn.setOnClickListener(this);
		progressBar.setOnSeekBarChangeListener(this);

		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(new SurfaceCallback());
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		if(fromUser && progress>0 && mediaPlayer!=null) {
			mediaPlayer.seekTo(progress);
			if(!mediaPlayer.isPlaying())
				mediaPlayer.start();
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		//缓冲区
		progressBar.setSecondaryProgress((int) (percent/100f*progressBar.getMax()));
	}

	class SurfaceCallback implements SurfaceHolder.Callback {

		@Override
		public void surfaceCreated(final SurfaceHolder holder) {
			playVideo();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
								   int height) {
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}
	}

	private void playVideo() {
		mediaPlayer = new MediaPlayer();
		mediaPlayer.reset();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnErrorListener(this);
		mediaPlayer.setOnBufferingUpdateListener(this);
		try {
			mediaPlayer.setDataSource(this, Uri.parse(videoPath));
			mediaPlayer.prepareAsync();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "加载视频错误！", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
//		mp.release();
//		mp = null;
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		progressBar.setMax(mediaPlayer.getDuration());
		mediaPlayer.start();
		mediaPlayer.setDisplay(surfaceHolder);
		btn.setVisibility(View.VISIBLE);

		handler.post(runnable);
	}

	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
//			if(mediaPlayer.isPlaying()) {
				progressBar.setProgress(mediaPlayer.getCurrentPosition());
				handler.postDelayed(this, 200);
//			}
		}
	};

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		Toast.makeText(this, "you Errors！", Toast.LENGTH_LONG).show();
		return false;
	}

	@Override
	public void onClick(View v) {
		if(v==btn) {
			if(mediaPlayer!=null) {
				if(mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
					currentPosition = mediaPlayer.getCurrentPosition();
					btn.setText("continue");
				}else {
					mediaPlayer.seekTo(currentPosition);
					mediaPlayer.start();
					btn.setText("Pause");
				}
			}
		}else if(v==showDlgBtn) {
			startActivity(new Intent(MainActivity.this, DialogActivity.class));
		}else if(v==repayBtn) {
			currentPosition = 0;
			mediaPlayer.seekTo(currentPosition);
			mediaPlayer.start();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		if(currentPosition>=0 && mediaPlayer!=null) {
			mediaPlayer.seekTo(currentPosition);
			mediaPlayer.start();
			btn.setText("Pause");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		wakeLock.release();
		if(mediaPlayer!=null && mediaPlayer.isPlaying()) {
			currentPosition = mediaPlayer.getCurrentPosition();
			mediaPlayer.pause();
			btn.setText("continue");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mediaPlayer!=null) {
			if(mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
}
