package com.robotside;

import com.robotside.R;
import net.majorkernelpanic.streaming.SessionBuilder;
import net.majorkernelpanic.streaming.gl.SurfaceView;
import net.majorkernelpanic.streaming.rtsp.RtspServer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class StremingThread extends Thread {
	private SurfaceView mSurfaceView;
	Context _context;

	public StremingThread(Context context) {

		if (_context != null) {
			_context = context;
			
			
			//mSurfaceView = (SurfaceView) _context.findViewById(R.id.surface);
			Log.d("Streaming", "Streaming thread created");
			// Sets the port of the RTSP server to 1234
			Editor editor = PreferenceManager.getDefaultSharedPreferences(_context)
					.edit();
			editor.putString(RtspServer.KEY_PORT, String.valueOf(1234));
			editor.commit();

			// Configures the SessionBuilder
			SessionBuilder.getInstance() //.setSurfaceView(mSurfaceView)
					.setPreviewOrientation(90)
					.setContext(_context)
					.setAudioEncoder(SessionBuilder.AUDIO_NONE)
					.setVideoEncoder(SessionBuilder.VIDEO_H264);
		}

	}

	public void StartService() {
		// Starts the RTSP server
		if (_context != null)
			_context.startService(new Intent(_context, RtspServer.class));
		else
			Log.e("Streaming",
					"Cannot start streaming service the activity referenced equals null");
	}
}
