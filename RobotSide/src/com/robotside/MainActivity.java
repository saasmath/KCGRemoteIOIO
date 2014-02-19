package com.robotside;


import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import net.majorkernelpanic.example1.R;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.SeekBar;

public class MainActivity extends IOIOActivity {
	

	private SurfaceView mSurfaceView;

	
	private final int PAN_PIN = 3;
	private final int TILT_PIN = 6;
	private  int TempPwm ;
	private final int PWM_FREQ = 100;
	
	private SeekBar mPanSeekBar;
	private SeekBar mTiltSeekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSurfaceView = (SurfaceView) findViewById(R.id.surface);
		
		// Sets the port of the RTSP server to 1234
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
		editor.commit();

		// Configures the SessionBuilder
		SessionBuilder.getInstance()
		.setSurfaceView(mSurfaceView)
		.setPreviewOrientation(90)
		.setContext(getApplicationContext())
		.setAudioEncoder(SessionBuilder.AUDIO_NONE)
		.setVideoEncoder(SessionBuilder.VIDEO_H264);
		
		// Starts the RTSP server
		this.startService(new Intent(this,RtspServer.class));
		
		enableUi(false);
	}


	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}
	class Looper extends BaseIOIOLooper {
		private PwmOutput panPwmOutput;
		private PwmOutput tiltPwmOutput;

		
		
		public void setup() throws ConnectionLostException {
		try {
//				
				panPwmOutput = ioio_.openPwmOutput(PAN_PIN, PWM_FREQ);
//				//tiltPwmOutput = ioio_.openPwmOutput(TILT_PIN, PWM_FREQ);
			enableUi(true);
			} catch (ConnectionLostException e) {
				enableUi(false);
			throw e;
			}
		}

		
		public void loop() throws ConnectionLostException {
			try {
				
				panPwmOutput.setPulseWidth(500+ TempPwm);
				TempPwm++;
				if (TempPwm == 100)
					TempPwm=0;
//				tiltPwmOutput.setPulseWidth(500 + mTiltSeekBar.getProgress() * 2);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				ioio_.disconnect();
			} catch (ConnectionLostException e) {
				enableUi(false);
				throw e;
			}
		}
	}

	private void enableUi(final boolean enable) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//mPanSeekBar.setEnabled(enable);
				//mTiltSeekBar.setEnabled(enable);
			}
		});
	}

    
}
