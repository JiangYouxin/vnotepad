package net.jiangyouxin.vnotepad;

public class SimpleSyncClient implements SyncClient {
    @Override
    public boolean upload(String filename) {
        return false;
    }
    @Override
    public boolean download(String filename) {
        return false;
    }
}
