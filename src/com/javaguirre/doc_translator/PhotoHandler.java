package com.javaguirre.doc_translator;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PhotoHandler implements PictureCallback {

	private final static String DEBUG_TAG = "MakePhotoActivity";
	private Context context;
	private String filename;
	private File directory;

	public PhotoHandler(Context context, File directory, String filename) {
		this.context = context;
		this.directory = directory;
		this.filename = filename;

		if(!this.directory.exists() && ! this.directory.mkdirs()) {
			Log.d(DEBUG_TAG, "Can't create directory to save image");
			Toast.makeText(context, "Can't create directory to save image", Toast.LENGTH_LONG).show();

			return;
		}

	}

	public void onPictureTaken(byte[] data, Camera camera) {
		File pictureFile = new File(this.directory + File.separator + this.filename);

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			fos.close();
			Toast.makeText(context, "New Image saved: " + this.filename, Toast.LENGTH_LONG).show();
		} catch(Exception error) {
			Log.d(DEBUG_TAG, "File " + filename + " not saved: " + error.getMessage());
			Toast.makeText(context, "Image could not be saved ", Toast.LENGTH_LONG).show();
		}
	}
}