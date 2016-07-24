package se.liu.ida.josfa969.lab2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Josef on 2014-03-06.
 */
public class AdditionalInfoFragment extends Fragment {

    private TextView infoTitle;
    private TextView infoList;
    private String title;
    private JSONArray members;

    public AdditionalInfoFragment() {
    }

    public AdditionalInfoFragment(String title, JSONArray members) {
        System.out.println("Additional Info Constructor");

        this.title = title;
        this.members = members;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.additional_info_layout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On Resume");

        this.infoTitle = ((GroupListActivity) getActivity()).getInfoTitle();
        this.infoList = ((GroupListActivity) getActivity()).getInfoList();
        updateContent(infoTitle, infoList, title, members);
    }

    // Changes the content of the AdditionalInfoFragment fragment
    public void updateContent(TextView infoTitle, TextView infoList, String title, JSONArray members) {
        infoTitle.setText("");
        infoList.setText("");
        infoTitle.setText(title);
        for (int i = 0; i < members.length(); i++) {
            try {
                JSONObject temp = (JSONObject) members.get(i);
                infoList.append(temp.get("namn").toString() + "\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
