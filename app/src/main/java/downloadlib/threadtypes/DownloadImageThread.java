package downloadlib.threadtypes;

import android.util.Log;

import downloadlib.ImageDownload;

public class DownloadImageThread implements Runnable {

    private String url;

    public DownloadImageThread(String url){
        this.url = url;
    }

    @Override
    public void run() {
        ImageDownload imagedownload = new ImageDownload();
        imagedownload.downloadRequest(url);
    }
}
