package com.javaguirre.doc_translator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MakePhotoActivity extends Activity {

	private final static String DEBUG_TAG = "MakePhotoActivity";
	private Camera camera;
	private int cameraId = 0;
	private String filename;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG).show();
		} else {
			cameraId = findFrontFacingCamera();
			camera = Camera.open(cameraId);
			if(cameraId < 0) {
				Toast.makeText(this, "No front facing camera found", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void takePhoto(View view) {
		genFilename();

		PhotoHandler photoHandler = new PhotoHandler(getApplicationContext(), getDir(), filename);
		camera.takePicture(null, null, photoHandler);
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText(this.filename);
//		Bitmap bmp = BitmapFactory.decodeFile(getDir() + File.separator + this.filename);
//		ImageView imageView = (ImageView) findViewById(R.id.imageView);
//	    imageView.setImageBitmap(bmp);
	}

	public void convertToText() {
		TessHandler handler = new TessHandler();
		String result = handler.convertToText(this.filename, "nld");
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}

	public void translateText() {

	}

	private int findFrontFacingCamera() {
		int cameraId = -1;
		int numberOfCameras = Camera.getNumberOfCameras();
		for(int i = 0; i < numberOfCameras; i++) {
			CameraInfo info = new CameraInfo();
			Camera.getCameraInfo(i, info);
			if(info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				Log.d(DEBUG_TAG, "Camera found");
				cameraId = i;
				break;
			}
		}
		return cameraId;
	}

	@Override
	protected void onPause() {
		if(camera!= null) {
			camera.release();
			camera = null;
		}
		super.onPause();
	}

	private void genFilename() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		this.filename = "Picture_" + date + ".jpg";
	}

	private File getDir() {
		File sdDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		return new File(sdDir, "doc_translator");
	}
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    } */
}
