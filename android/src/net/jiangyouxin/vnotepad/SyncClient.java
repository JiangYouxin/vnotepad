package net.jiangyouxin.vnotepad;

import android.content.*;

public interface SyncClient {
    boolean upload(Context context, String filename);
    boolean download(Context context, String filename); 
}
