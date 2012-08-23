package com.javaguirre.doc_translator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String DEBUG_TAG = "MainActivity";
	public static final String ALBUM_NAME = "Doc Translator";
	public static final String JPEG_FILE_PREFIX = "Picture";
	public static final String JPEG_FILE_SUFFIX = ".jpeg";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Bundle extras = data.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(mImageBitmap);
    }

    public void takePhoto(View view) {
    	Toast.makeText(getApplicationContext(), "This is the Take Photo", Toast.LENGTH_SHORT).show();
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//    	File f = createImageFile();
//    	takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(takePictureIntent, 1);
    }

    public void convertText(View view) {
    	Toast.makeText(getApplicationContext(), "This is the convert text", Toast.LENGTH_SHORT).show();
    }

    public void translateText(View view) {
    	Toast.makeText(getApplicationContext(), "This is the translate text", Toast.LENGTH_SHORT).show();
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private File getStorageDir() {
        File storageDir = new File(
		    Environment.getExternalStoragePublicDirectory(
		        Environment.DIRECTORY_PICTURES
		    ),
		    ALBUM_NAME
		);

		Log.d(DEBUG_TAG, "DIRECTORY STORAGE: " + storageDir);

		return storageDir;
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp =
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File image = null;

        try {
	        image = File.createTempFile(
	            imageFileName,
	            JPEG_FILE_SUFFIX,
	            getStorageDir()
	        );
        }
	    catch (IOException e) {
	    	Log.d(DEBUG_TAG, "There was a failure creating the file");
	    }
//        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
