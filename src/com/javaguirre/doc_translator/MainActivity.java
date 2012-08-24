package com.javaguirre.doc_translator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String DEBUG_TAG = "MainActivity";
	public static final String ALBUM_NAME = "Doc Translator";
	public static final String JPEG_FILE_PREFIX = "Picture";
	public static final String JPEG_FILE_SUFFIX = ".jpg";
	public static final int TAKE_PHOTO = 1;
	private static File mActualFile;
	private static String mActualText;

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
    	//TODO Check for activity
    	if(requestCode == TAKE_PHOTO) {
	        if(resultCode != -1) {
	        }
	        else {
	        	Log.d("WRONG!", "Something went really wrong!");
	        }
		    	Log.d("REQUEST CODE", "REQUEST CODE: " + requestCode);
		    	Log.d("RESULT CODE", "RESULT CODE: " + resultCode);
		    	Bundle extras = data.getExtras();
		        Bitmap mImageBitmap = (Bitmap) extras.get("data");
		        ImageView imageView = (ImageView) findViewById(R.id.imageView);
		        imageView.setImageBitmap(mImageBitmap);

		        //Save the file
		        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		        mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

		        //you can create a new file name "test.jpg" in sdcard folder.
		        File f = createImageFile();
		        mActualFile = f;

		        //write the bytes in file
		        FileOutputStream fo;
		        Toast.makeText(getApplicationContext(), f.getAbsolutePath(), Toast.LENGTH_LONG).show();

				try {
					fo = new FileOutputStream(f);
					fo.write(bytes.toByteArray());
					Toast.makeText(getApplicationContext(), "NO FUNCIONA NADA!!!", Toast.LENGTH_LONG).show();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	}
    }

    public void takePhoto(View view) {
    	Toast.makeText(getApplicationContext(), "This is the Take Photo", Toast.LENGTH_SHORT).show();
    	Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(takePictureIntent, TAKE_PHOTO);
    }

    public void convertText(View view) {
    	Toast.makeText(getApplicationContext(), "This is the convert text", Toast.LENGTH_SHORT).show();
    	Intent convertToText = new Intent(this, ConvertTextActivity.class);
    	convertToText.putExtra("image_path", mActualFile.getAbsolutePath());
    	startActivity(convertToText);
    }

    public void translateText(View view) {
    	Toast.makeText(getApplicationContext(), "This is the translate text", Toast.LENGTH_SHORT).show();
    	Intent translateText = new Intent(this, TranslateTextActivity.class);
    	translateText.putExtra("converted_text", mActualText);
    	startActivity(translateText);
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

        boolean success = false;

        if(!storageDir.exists()) {
        	success = storageDir.mkdir();

	        if(!success) {
	        	Log.d("DIRECTORY STORAGE: ", "We couldn't create the dir");
	        }
        }

		Log.d(DEBUG_TAG, "DIRECTORY STORAGE: " + storageDir);

		return storageDir;
    }

    private File createImageFile() {
        // Create an image file name
        String timeStamp =
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File file = null;

        try {
	        file = File.createTempFile(
	            imageFileName,
	            JPEG_FILE_SUFFIX,
	            getStorageDir()
	        );
        }
	    catch (IOException e) {
	    	Log.d(DEBUG_TAG, "There was a failure creating the file");
	    }

        Log.d("IMAGE", "We have an image: " + file.getAbsolutePath());

        return file;
    }
}
