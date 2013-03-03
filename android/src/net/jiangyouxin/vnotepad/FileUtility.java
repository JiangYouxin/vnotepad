package net.jiangyouxin.vnotepad;

import java.io.*;

import android.content.*;

public class FileUtility {
    public static byte[] readFile(Context context, String fileName) {
        FileInputStream fin = null;
        try {
            fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            return buffer;
        } catch (Exception e) {
            return new byte[0];
        } finally {
            if (fin != null) try {
                fin.close();
            } catch (IOException e) {
            }
        } 
    }
    public static void writeFile(Context context, String fileName, byte[] content) {
        FileOutputStream fout = null;
        try {
            fout = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fout.write(content);
        } catch (Exception e) {
        } finally {
            if (fout != null) try {
                fout.close();
            } catch (IOException e) {
            }
        }
    }
}
