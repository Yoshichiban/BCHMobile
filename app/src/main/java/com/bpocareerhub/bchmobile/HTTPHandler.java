package com.bpocareerhub.bchmobile;

import android.provider.Settings;

import java.util.List;

/**
 * Created by BCH_OJT on 6/24/2016.
 */
public class HTTPHandler {
    static String response = null;
    public final static int GET = 1;
    public final static int POST = 2;

    public HTTPHandler(){

    }

    public String makeHttpCall(String url){
        return this.makeHttpCall(url, null);
    }

    public String makeHttpCall(String url, List<Settings.NameValueTable> params){
        return "";
    }
}
