package se.liu.ida.josfa969.lab2;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Josef on 2014-03-06.
 */
public class GroupListFragment extends ListFragment {
    OnGroupSelectedListener mCallback;

    private ArrayList<String> groupNames;

    public GroupListFragment() {
    }

    public GroupListFragment(ArrayList<String> groupNames) {
        this.groupNames = groupNames;
    }

    // Container Activity must implement this interface
    public interface OnGroupSelectedListener {
        public void onItemSelected(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not it throws an exception
        try{
            mCallback = (OnGroupSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnGroupSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getActivity() != null;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                groupNames
        );
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onItemSelected((int) id);
    }

}
