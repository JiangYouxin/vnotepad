package net.jiangyouxin.vnotepad;

public interface SyncClient {
    void uploadFile(String filename);
    void downloadFile(String filename); 
}
