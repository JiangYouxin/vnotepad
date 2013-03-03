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
                final int sync_result = SyncHelper.doSync();
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

    private boolean loadFile() {
        String res = null;
        FileInputStream fin = null;
        try {
            fin = openFileInput(FILENAME);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = new String(buffer, "utf8");
        } catch (Exception e) {
        } finally {
            if (fin != null) try {
                fin.close();
            } catch (IOException e) {
            }
        } 
        if (res != null) {
            edit.setText(res);
            return true;
        } else {
            return false;
        }
    }

    private boolean saveFile() {
        String res = edit.getText().toString();
        FileOutputStream fout = null;
        try {
            fout = openFileOutput(FILENAME, MODE_PRIVATE);
            fout.write(res.getBytes("utf8"));
        } catch (Exception e) {
        } finally {
            if (fout != null) try {
                fout.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
}
