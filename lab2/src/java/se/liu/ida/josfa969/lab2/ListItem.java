package se.liu.ida.josfa969.lab2;

/**
 * Created by Josef on 2014-03-02.
 */
public class ListItem {

    private String title;
    private String[] keyArray;
    private Object[] valueArray;

    protected ListItem(String title, String[] keyArray, Object[] valueArray) {
        this.title = title;
        this.keyArray = keyArray;
        this.valueArray = valueArray;
    }

    public String getTitle() {
        return title;
    }

    public String[] getKeyArray() {
        return keyArray;
    }

    public Object[] getValueArray() {
        return valueArray;
    }
}
