package se.liu.ida.josfa969.lab2;

import java.util.ArrayList;

/**
 * Created by Josef on 2014-03-02.
 */
public class GroupCreator {

    private static ArrayList<ListItem> groupList = new ArrayList<ListItem>();

    public static void makeFacebookGroup() {
        String title = "Facebook";
        String[] keyArray = {"Members", "Likes"};
        Object[] valueArray = {5, 7};
        groupList.add(new ListItem(title, keyArray, valueArray));
    }

    public static void makeTwitterGroup() {
        String title = "Twitter";
        String[] keyArray = {"Followers", "Tweets", "Re-Tweets"};
        Object[] valueArray = {10, 17, 3};
        groupList.add(new ListItem(title, keyArray, valueArray));
    }

    public static void makeRegisteredGroup() {
        String title = "Registered";
        String[] keyArray = {"Members"};
        Object[] valueArray = {10};
        groupList.add(new ListItem(title, keyArray, valueArray));
    }

    // Creates and returns a list of all titles
    public static ArrayList<String> getTitlesList() {
        ArrayList<String> titlesList = new ArrayList<String>();
        for (ListItem item : groupList) {
            titlesList.add(item.getTitle());
        }
        return titlesList;
    }

    // Creates and returns a list of all key arrays
    public static ArrayList<String[]> getKeysList() {
        ArrayList<String[]> keysList = new ArrayList<String[]>();
        for (ListItem item : groupList) {
            keysList.add(item.getKeyArray());
        }
        return keysList;
    }

    // Creates and returns a list of all value arrays
    public static ArrayList<Object[]> getValuesList() {
        ArrayList<Object[]> valuesList = new ArrayList<Object[]>();
        for (ListItem item : groupList) {
            valuesList.add(item.getValueArray());
        }
        return valuesList;
    }

}
