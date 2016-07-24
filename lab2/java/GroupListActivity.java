package se.liu.ida.josfa969.lab2;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GroupListActivity extends Activity implements GroupListFragment.OnGroupSelectedListener {

    //private static final String GROUPS_URL = "http://tddd80-afteach.rhcloud.com/api/groups";
    private static final String GROUPS_URL = "http://10.0.2.2:8080/_register_user_/a/b/c/d/e";

    private ArrayList<String> groupNames = new ArrayList<String>();
    private ArrayList<JSONArray> groupsInfo = new ArrayList<JSONArray>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // Creates an ArrayList of the group names
            JSONObject groupJsonObject = new JSONObject(getUrlResponseString(GROUPS_URL));
            JSONArray groupJsonArray = groupJsonObject.getJSONArray("grupper");
            for (int i = 0; i < groupJsonArray.length(); i++) {
                groupNames.add(groupJsonArray.get(i).toString());
            }
            // Gets the JSONArrays of each group's info and
            // adds them to the groupsInfo ArrayList
            for (String group : groupNames) {
                String tempUrl = GROUPS_URL + "/" + group;
                JSONObject infoJsonObject = new JSONObject(getUrlResponseString(tempUrl));
                JSONArray infoJsonArray = infoJsonObject.getJSONArray("medlemmar");
                groupsInfo.add(infoJsonArray);
            }
        } catch (JSONException e) {
            System.out.println("JSON Exception");
        }

        // Creates the groups which are to be displayed
        GroupCreator.makeFacebookGroup();
        GroupCreator.makeTwitterGroup();
        GroupCreator.makeRegisteredGroup();

        System.out.println("groupNames: " + groupNames);
        System.out.println("groupsInfo: " + groupsInfo);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new GroupListFragment(groupNames)).commit();
        }
        setContentView(R.layout.list_activity_layout);
    }

    public String getUrlResponseString(String url) {
        /**
         * Reading URL response
         */
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                System.out.println("Failed to download file");
            }
        } catch (ClientProtocolException e) {
            System.out.print("Client Protocol Exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("IO Exception");
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            System.out.print("Network On GroupListActivity Thread Exception");
            e.printStackTrace();
        }
        /**
         * End reading of URL
         */
        return builder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lab2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(int index) {
        // The user selected the group name from the GroupListFragment

        AdditionalInfoFragment infoFrag = (AdditionalInfoFragment) getFragmentManager().findFragmentById(R.id.additional_info_layout);

        // Gets UI components
        TextView infoTitle = (TextView) findViewById(R.id.info_title);
        TextView infoList = (TextView) findViewById(R.id.info_list);

        // Gets the correct data depending on which option was selected
        String title = groupNames.get(index);
        JSONArray members = groupsInfo.get(index);

        if (infoFrag != null) {
            // If article fragment is available, we're in two-pane layout...

            // Call a method in the AdditionalInfoFragment to update its content
            infoFrag.updateContent(infoTitle, infoList, title, members);
        } else {
            // Otherwise we're in one-pane layout and must swap fragments...
            Intent intent = new Intent(this, AdditionalInfoActivity.class);

            // Create a new fragment
            AdditionalInfoFragment newFragment = new AdditionalInfoFragment(title, members);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            // Replace whatever is in the container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    /*
    * Help functions to access GUI components
    */
    public TextView getInfoTitle() {
        return (TextView) findViewById(R.id.info_title);
    }

    public TextView getInfoList() {
        return (TextView) findViewById(R.id.info_list);
    }
}
