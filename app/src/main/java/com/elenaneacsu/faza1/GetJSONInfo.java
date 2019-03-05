package com.elenaneacsu.faza1;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetJSONInfo extends AsyncTask<String, Void, List<Information>> {
    private List<Information> infoList;
    @Override
    protected List<Information> doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();
        infoList = new ArrayList<>();

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject object = new JSONObject(builder.toString());
            JSONArray array = object.getJSONArray("info details");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonInfo = array.getJSONObject(i);
                String title = jsonInfo.getString("title");
                String fact = jsonInfo.getString("fact");
                infoList.add(new Information(title, fact));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return infoList;
    }

}
