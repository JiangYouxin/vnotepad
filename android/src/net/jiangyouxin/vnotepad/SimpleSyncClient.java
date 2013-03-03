package net.jiangyouxin.vnotepad;

import android.content.*;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

public class SimpleSyncClient implements SyncClient {
    @Override
    public boolean upload(Context context, String filename) {
        return false;
    }
    @Override
    public boolean download(Context context, String filename) {
        HttpGet request = new HttpGet(Config.URL + "action=download");
        HttpClient client = new DefaultHttpClient();
        FileOutputStream fout = null;
        try {
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                fout = context.openFileOutput(filename, Context.MODE_PRIVATE);
                response.getEntity().writeTo(fout);
                return true;
            }
        } catch (Exception e) {
        } finally {
            try {
                if (fout != null)
                    fout.close();
            } catch (Exception e) {
            }
        }
        return false;
    }
}
