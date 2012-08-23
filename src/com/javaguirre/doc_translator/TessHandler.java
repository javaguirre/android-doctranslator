package com.javaguirre.doc_translator;

import java.io.File;

import android.content.Context;

import com.googlecode.tesseract.android.TessBaseAPI;

public class TessHandler {

	public TessHandler() {}

	//TODO Change lang prefix for traineddata files
	protected String convertToText(Context context, String filename, String language) {
		//FIXME Provisional path
		final String TESSBASE_PATH = "/mnt/sdcard/tesseract";

		File myImage = new File(filename);

		TessBaseAPI baseApi = new TessBaseAPI();
		baseApi.init(TESSBASE_PATH, language); // myDir + "/tessdata/eng.traineddata" must be present
		baseApi.setImage(myImage);

		String recognizedText = baseApi.getUTF8Text(); // Log or otherwise display this string...
		baseApi.end();

		return recognizedText;
	}
}
