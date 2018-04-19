package com.example.a10011878.orientationproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> headlines;
    String json;
    String apiReq;
    JSONObject root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView = (ListView) findViewById(R.id.ID_NewsList);
        headlines = new ArrayList<>();
        NewsAdapter adapter = new NewsAdapter(this,R.layout.list2_layout,headlines);
        listView.setAdapter(adapter);
        AsyncThread1 thread2 = new AsyncThread1();
        thread2.execute();
    }
    public class NewsAdapter extends ArrayAdapter<String>{
        Context context;
        List list;

        public NewsAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            this.context = context;
            list = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View layoutView = layoutInflater.inflate(R.layout.list2_layout,null);
            TextView titles = (TextView) layoutView.findViewById(R.id.newstitle);
            titles.setText(headlines.get(position));
            return layoutView;
        }
    }
    public class AsyncThread1 extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                apiReq = "https://newsapi.org/v2/top-headlines?q=cryptocurrency&apiKey=af6052d688d64bf3a2bf476b21e39450";
                Log.d("TAG",apiReq);
                URL url = new URL(apiReq);
                URLConnection urlConnection = url.openConnection();
                InputStream stream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                json = "";
                while ((line = reader.readLine()) != null) {
                    json +=line;
                }
                Log.d("TAG",json+"front");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                root = new JSONObject(json);
                JSONArray articles = root.getJSONArray("articles");
                for(int i=0;i<articles.length();i++){
                    JSONObject indiv = articles.getJSONObject(i);
                    String title = (String) indiv.get("title");
                    headlines.add(title);
                    Log.d("TAG",title);
                }
             listView.setAdapter(new NewsAdapter(Main2Activity.this,R.layout.list2_layout,headlines));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(aVoid);
        }
    }
}
