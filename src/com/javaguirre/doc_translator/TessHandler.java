package com.javaguirre.doc_translator;

import java.io.File;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TessHandler {

	public TessHandler() {}

	//TODO Change lang prefix for traineddata files
	protected String convertToText(String filename, String language) {
//		File myDir = getExternalFilesDir(Environment.MEDIA_MOUNTED);
		String myDir = "file:///android_asset/tess_data";
		File myImage = new File(filename);

		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(myDir, language); // myDir + "/tessdata/eng.traineddata" must be present
		baseApi.setImage(myImage);

		String recognizedText = baseApi.getUTF8Text(); // Log or otherwise display this string...
		baseApi.end();

		return recognizedText;
	}
}
