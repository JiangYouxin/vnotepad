package net.jiangyouxin.vnotepad;

import java.io.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
    private static String FILENAME = "vnotepad.txt";
    private static String FILENAME_ORIG = FILENAME + ".orig";
    private static String FILENAME_SERVER = FILENAME + ".server";

    private EditText edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
        edit = (EditText)findViewById(R.id.edit_text);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFile();
    }

    @Override
    public void onPause() {
        saveFile();
        super.onPause();
    }

    public void syncFile(View v) {
        final ProgressDialog dialog = ProgressDialog.show(
                this,
                getString(R.string.sync_progress_title),
                getString(R.string.sync_progress_message),
                true);
        saveFile();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
                SyncTask task = new SyncTask(FILENAME_ORIG, FILENAME, FILENAME_SERVER, getApplicationContext());
                final int sync_result = task.doSync();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                            getString(sync_result),
                            Toast.LENGTH_SHORT).show();
                        loadFile();
                    }
                });
            }
        }.start();
    }

    private void loadFile() {
        byte[] buffer = FileUtility.readFile(this, FILENAME);
        if (buffer.length > 0) {
            try {
                String res = new String(buffer, "utf8");
                edit.setText(res);
            } catch (UnsupportedEncodingException e) {
            }
        } 
    }

    private void saveFile() {
        String res = edit.getText().toString();
        try {
            FileUtility.writeFile(this, FILENAME, res.getBytes("utf8"));
        } catch (UnsupportedEncodingException e) {
        }
    }
}
