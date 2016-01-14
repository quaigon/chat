package com.example.kamil.chat;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;

public class PostAsyncTask extends RoboAsyncTask<Boolean> {

    private String nickName;
    private String message;
    private boolean newUser;
    public static final String SERVERURL = "http://192.168.1.117:8080/EJBChatServlet/ServletMessage";
    Gson gson;
    public PostAsyncTask (Context context, String nickName, String message, boolean newUser) {
        super(context);
        this.nickName = nickName;
        this.message = message;
        this.newUser = newUser;
        this.gson = new Gson();
    }



    @Override
    public Boolean call() throws Exception {
        return doPost(nickName, message, newUser);
    }

    @Override
    protected void onSuccess(Boolean aBoolean) throws Exception {
        super.onSuccess(aBoolean);
    }

    private boolean doPost(String nickname, String message, boolean newUser)  throws IOException {
        URL url = new URL (SERVERURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("nickname", nickname)
                .appendQueryParameter("message", message)
                .appendQueryParameter("newUser", String.valueOf(newUser));

        String query = builder.build().getEncodedQuery();
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        connection.getResponseCode();
        String res = readInputStreamToString(connection);
        Response response = gson.fromJson(res,Response.class);
        Ln.v(response.isSuccess());
        return response.isSuccess();

    }


    private String readInputStreamToString(HttpURLConnection connection) {
        String result = null;
        StringBuffer sb = new StringBuffer();
        InputStream is = null;

        try {
            is = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            result = sb.toString();
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }

        return result;
    }
}


