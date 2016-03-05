package com.example.kamil.chat;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionService extends Service{

    int mStartMode;
    public static final String SERVERURL = "http://localhost:9000/EJBChatServlet/ServletMessage";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GetMessegesTask getMessegesTask = new GetMessegesTask();
        getMessegesTask.execute();

        return mStartMode;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    private class GetMessegesTask extends AsyncTask <Void, String, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            while (true ) {
                String result = getOutputFromUrl(SERVERURL);
                publishProgress(result);
            }
        }


        @Override
        protected void onProgressUpdate(String... values) {
            Intent intent = new Intent("MessageSent");
            intent.putExtra("message", values[0]);
            LocalBroadcastManager.getInstance(ConnectionService.this).sendBroadcast(intent);
            super.onProgressUpdate(values);
        }

        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnction = (HttpURLConnection) connection;
                httpConnction.setRequestMethod("GET");
                httpConnction.connect();
                ;
                if (httpConnction.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnction.getInputStream();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }


        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");

            try {
                InputStream stream = getHttpConnection(url);
                if (null != stream) {
                BufferedReader buffer = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                String s = "";

                while ( (s = buffer.readLine()) != null) {
                    output.append(s);

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return output.toString();
        }
    }







}
