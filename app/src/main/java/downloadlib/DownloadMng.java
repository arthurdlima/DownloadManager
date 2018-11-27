package downloadlib;

import android.content.Context;
import android.view.View;

import java.io.File;

public abstract class DownloadMng {

    //TEMPLATE METHOD DESIGN PATTERN

    public abstract boolean downloadRequest(String url);
    public abstract void downloadStartThread(String url);
    public abstract File createFile(String urlForParse, String name);
}
