package com.javaguirre.doc_translator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TranslatorHandler {

	public TranslatorHandler() {}
	
	protected void translateText(String language_in, String language_out, String text) {
		//FIXME Provisional
		final String TRANSLATOR_KEY = "1111111";
		try {
			/*URL url = new URL("https://www.googleapis.com/language/translate/v2" 
							  + "?key=" + TRANSLATOR_KEY
							  + "&source=" + language_in
							  + "&target=" + language_out
							  + "&q=" + text
							 );*/
			URL url = new URL("http://javaguirre.net");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			readStream(con.getInputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void readStream(InputStream in) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

//	public boolean isNetworkAvailable() {
//		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//		
//		if(networkInfo != null && networkInfo.isConnected()) {
//			return true;
//		}
//		
//		return false;
//	}
}
