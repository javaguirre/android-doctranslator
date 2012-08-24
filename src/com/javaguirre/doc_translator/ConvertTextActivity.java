package com.javaguirre.doc_translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertTextActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_text);
        Intent intent = getIntent();
        String image_path = intent.getStringExtra("image_path");

        Log.d("DEBUG", "PATH:   " + image_path);
        TessHandler handler = new TessHandler();
        String resultText = handler.convertToText(getApplicationContext(), image_path, "nld");
        TextView textView = (TextView) findViewById(R.id.textViewConvert);
        textView.setText(resultText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_convert_text, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
