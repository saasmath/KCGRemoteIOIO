package com.robotside;

import ioio.lib.util.android.IOIOActivity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends IOIOActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		StremingThread sThread = new StremingThread(this.getApplicationContext());
		sThread.start();
		
	//	enableUi(false);
	}


//	@Override
//	protected IOIOLooper createIOIOLooper() {
//		return new Looper();
//	}
//	class Looper extends BaseIOIOLooper {
//		private PwmOutput panPwmOutput;
//		private PwmOutput tiltPwmOutput;
//
//		
//		
//		public void setup() throws ConnectionLostException {
//		try {
////				
//				panPwmOutput = ioio_.openPwmOutput(PAN_PIN, PWM_FREQ);
////				//tiltPwmOutput = ioio_.openPwmOutput(TILT_PIN, PWM_FREQ);
//			enableUi(true);
//			} catch (ConnectionLostException e) {
//				enableUi(false);
//			throw e;
//			}
//		}
//
//		
//		public void loop() throws ConnectionLostException {
//			try {
//				
//				panPwmOutput.setPulseWidth(500+ TempPwm);
//				TempPwm++;
//				if (TempPwm == 100)
//					TempPwm=0;
////				tiltPwmOutput.setPulseWidth(500 + mTiltSeekBar.getProgress() * 2);
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				ioio_.disconnect();
//			} catch (ConnectionLostException e) {
//				enableUi(false);
//				throw e;
//			}
//		}
//	}

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
