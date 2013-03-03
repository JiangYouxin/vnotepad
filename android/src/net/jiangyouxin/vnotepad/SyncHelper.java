package net.jiangyouxin.vnotepad;

public class SyncHelper {
    // trick: use id in strings.xml for result
    public static int SYNC_SUCCESS_NO_CHANGE = R.string.sync_success_no_change;

    public static int SYNC_SUCCESS_DOWNLOAD = R.string.sync_success_download;
    public static int SYNC_SUCCESS_UPLOAD = R.string.sync_success_upload;
    public static int SYNC_SUCCESS_MERGE = R.string.sync_success_merge;

    public static int SYNC_FAILED_NETWORK = R.string.sync_failed_network;
    public static int SYNC_FAILED_CONFLICT = R.string.sync_failed_conflict;

    public static int doSync() {
        return SYNC_SUCCESS_NO_CHANGE;
    }
}
