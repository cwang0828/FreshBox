package com.example.mac.mykitchenapp1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;
import org.json.JSONArray;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by mac on 6/24/16.
 */
public class Inventory extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);
        int id = getIntent().getExtras().getInt("user_id");
        new FetchFoods().execute(id);
    }

    public class FetchFoods extends AsyncTask<Integer, Boolean, ArrayList<String>> {
        private InputStream is = null;
        private BufferedReader br = null;
        private JSONArray jObj = null;
        private ProgressDialog dialog;
        //static final String DB_URL = "http://repos.insttech.washington.edu/~dejarc/user_foods.php";
        static final String DB_URL = "http://students.washington.edu/yxw1990/freshboxapp/freshbox_all.php";
        public FetchFoods() {

        }
        protected void onPreExecute() {
            dialog = new ProgressDialog(Inventory.this);
            dialog.setMessage("finding your foods...");
            dialog.show();

        }
        protected ArrayList<String> doInBackground(Integer...strings) {

            ArrayList<String> myItems = new ArrayList<String>();
            try {
                ArrayList<String> myKeys = new ArrayList<String>();
                myKeys.add("user_id");
                String myQuery = "";
                boolean first = true;
                for(int i = 0; i <strings.length; i++) {
                    if(!first) {
                        myQuery += "&";
                    }
                    myQuery += URLEncoder.encode(myKeys.get(i), "UTF-8") + "=" + URLEncoder.encode("" + strings[0], "UTF-8");
                    first = false;
                }
                URL url = new URL(DB_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                OutputStreamWriter user_input = new OutputStreamWriter(conn.getOutputStream());
                user_input.write(myQuery);
                user_input.flush();
                user_input.close();
                int code = conn.getResponseCode();
                String msg = "Connection Code: " + code;
                Log.e("MYSQL", msg);
                is = conn.getInputStream();
                //conn.connect();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                try {

                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    jObj = new JSONArray(sb.toString());
                    for(int i = 0; i < jObj.length(); i++){
                        JSONObject objectInArray = jObj.getJSONObject(i);
                        myItems.add(objectInArray.getString("food_name") + "                                  " + objectInArray.getInt("quantity"));
                    }

                    // change the style for food name



                    // change the style for the quantity
                    //jObj.getString("food_name");


                    //for(int i = 0; i < jArr.length(); i++) {
                    //myItems.add(jObj.getString("food_name") + " " + jObj.getInt("quantity"));
                    //}


                    is.close();

                } catch (Exception e) {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                }
            }  catch (IOException e) {
                Log.e("MYSQL", "Couldn't connect!");
                //e.printStackTrace();
            }


            return myItems;
        }
        protected void onProgressUpdate(Boolean...progress) {
            if(progress[0]) {
                dialog.setMessage("Login Successful!");
            } else {
                dialog.setMessage("Login Failed");
            }

        }

        protected void onPostExecute(ArrayList<String> activities) {
            String[] activityItems = new String[activities.size()];
            Resources res = getResources();
            //String new_title = res.getString(R.string.activities_title);
            //setTitle(new_title);
            setContentView(R.layout.display);
            if(!activities.isEmpty()) {
                for (int i = 0; i < activities.size(); i++) {
                    activityItems[i] = activities.get(i);
                }
            }
            ListView temp = (ListView) findViewById(R.id.left_drawer);
            temp.setAdapter(new ArrayAdapter<String>(Inventory.this, R.layout.drawer_list_item, activityItems));
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

}
