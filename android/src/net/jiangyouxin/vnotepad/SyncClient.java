package net.jiangyouxin.vnotepad;

public interface SyncClient {
    boolean upload(String filename);
    boolean download(String filename); 
}
