package com.elenaneacsu.faza1;

import android.os.AsyncTask;
import android.util.Log;

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

public class GetJSONResources extends AsyncTask<String, Void, List<SharedResources>> {

    private List<SharedResources> resources;
    private ArrayList<Question> questionsList;
    private List<SharedResources> previews;

    private static final String TAG = "GetJSONResources";

    @Override
    protected List<SharedResources> doInBackground(String... strings) {
        StringBuilder builder = new StringBuilder();
        resources = new ArrayList<>();

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

            JSONObject mainObject = new JSONObject(builder.toString());
            JSONArray arrayResource = mainObject.getJSONArray("resource");

            for (int i = 0; i < arrayResource.length(); i++) {
                JSONObject objectResource = arrayResource.getJSONObject(i);
                String author = objectResource.getString("author");
                String subject = objectResource.getString("subject");
                String chapter = objectResource.getString("chapter");

                JSONObject objectContent = objectResource.getJSONObject("content");
                String time = objectContent.getString("time");
                String quizType = objectContent.getString("quiz type");
                JSONArray questionsArray = objectContent.getJSONArray("questions");

                //previews.add(new SharedResources(author, subject, chapter, time, quizType));

                questionsList = new ArrayList<>();
                for (int j = 0; j < questionsArray.length(); j++) {
                    JSONObject questionObject = questionsArray.getJSONObject(j);
                    String questionTime = questionObject.getString("time (mins)");
                    String text = questionObject.getString("text");
                    JSONArray response = questionObject.getJSONArray("response");

                    List<String> resp = new ArrayList<>();
                    for (int k = 0; k < response.length(); k++) {
                        resp.add(response.getString(k));
                    }
                    questionsList.add(new Question(questionTime, text, resp));
                }
                SharedResources sharedResources = new SharedResources(author, subject, chapter, time, quizType, questionsList);
                resources.add(sharedResources);
                Log.d(TAG, "hani bani: " + sharedResources.toString());
                Log.d(TAG, "doInBackground: downloading " + author + " " + subject + " " + quizType);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resources;
    }
}
