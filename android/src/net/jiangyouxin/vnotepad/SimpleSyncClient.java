package net.jiangyouxin.vnotepad;

import android.content.*;

import java.io.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;

public class SimpleSyncClient implements SyncClient {
    @Override
    public boolean upload(Context context, String filename) {
        HttpPost request = new HttpPost(Config.URL + "action=upload");
        HttpClient client = new DefaultHttpClient();
        byte[] content = FileUtility.readFile(context, filename);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("vnotepad", new String(content, "utf-8")));
            HttpEntity entity = new UrlEncodedFormEntity(params, "utf-8");
            request.setEntity(entity);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                return true;
        } catch (Exception e) {
        }
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
