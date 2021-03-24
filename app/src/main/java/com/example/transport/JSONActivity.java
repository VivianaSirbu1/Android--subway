package com.example.transport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.HashMap;

public class JSONActivity extends ListActivity {

    private ProgressDialog pDialog;

    JSONArray metrouri = null;
    ArrayList<HashMap<String, String>> subwayList;

    public static final String TAG_SUBWAY = "Metrou";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "denumire";
    public static final String TAG_TRAVELS = "nr calatorii 2019";
    public static final String TAG_PLATFORMS = "peroane in uz";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        subwayList = new ArrayList<HashMap<String, String>>();
        URL url = null;

        try {
            url = new URL("https://jsonkeeper.com/b/DQO1");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        GetSubway m = new GetSubway();
        m.setOnTaskFinishedEvent(new OnTaskExecutionFinished() {
            @Override
            public void onTaskFinishedEvent(String result) {

                if (pDialog.isShowing()) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                }
                ListAdapter adapter = new SimpleAdapter(JSONActivity.this, subwayList, R.layout.list_item,
                        new String[]{TAG_TITLE, TAG_TRAVELS, TAG_PLATFORMS},
                        new int[]{R.id.denumire_metrou, R.id.nrClatorii_metrou, R.id.nrPeron_metrou}) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        HashMap<String, String> currentRow = subwayList.get(position);

                        TextView peroane = view.findViewById(R.id.nrPeron_metrou);
                        int nrper = Integer.parseInt(currentRow.get(TAG_PLATFORMS));
                        if (nrper < 4)
                            peroane.setTextColor(Color.RED);
                        else
                            peroane.setTextColor(Color.BLUE);
                        return view;


                    }
                };
                setListAdapter(adapter);
            }
        });
        m.execute(url);
    }

    public interface OnTaskExecutionFinished {
        void onTaskFinishedEvent(String result);
    }


    public class GetSubway extends AsyncTask<URL, Void, String> {
        private OnTaskExecutionFinished event;

        public void setOnTaskFinishedEvent(OnTaskExecutionFinished event) {
            if (event != null)
                this.event = event;
        }

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) urls[0].openConnection();
                conn.setRequestMethod("GET");
                InputStream ist = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(ist);
                BufferedReader br = new BufferedReader(isr);
                String linie = "";
                String sbuf = "";
                while ((linie = br.readLine()) != null) {
                    sbuf += linie + "\n";
                }

                loadJSONObject(sbuf);

                return sbuf;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (this.event != null)
                this.event.onTaskFinishedEvent(s);
            else
                Log.e("GetSubway", "event is null");
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          pDialog = new ProgressDialog(JSONActivity.this);
           pDialog.setMessage("Please wait...");
          pDialog.setCancelable(false);
           pDialog.show();
        }

        public void loadJSONObject(String jsonStr) {
            if (jsonStr != null) {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    metrouri = jsonObj.getJSONArray(TAG_SUBWAY);
                    for (int i = 0; i < metrouri.length(); i++) {
                        JSONObject ob = metrouri.getJSONObject(i);
                        String id = ob.getString(TAG_ID);
                        String title = ob.getString(TAG_TITLE);
                        String travels = ob.getString(TAG_TRAVELS);
                        String platforms = ob.getString(TAG_PLATFORMS);

                        HashMap<String, String> metrou = new HashMap<>();
                        metrou.put(TAG_ID, id);
                        metrou.put(TAG_TITLE, title);
                        metrou.put(TAG_TRAVELS, travels);
                        metrou.put(TAG_PLATFORMS, platforms);

                        subwayList.add(metrou);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else
                Log.e("loadJSONObject", "obiectul este null");

        }

    }
}