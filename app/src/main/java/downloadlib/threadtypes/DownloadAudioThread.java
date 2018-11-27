package downloadlib.threadtypes;

import downloadlib.AudioDownload;

public class DownloadAudioThread implements Runnable {

    private String url;

    public DownloadAudioThread(String url){
        this.url = url;
    }

    @Override
    public void run() {
        AudioDownload audiodownload = new AudioDownload();
        audiodownload.downloadRequest(url);
    }
}
