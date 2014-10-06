package se.liu.ida.josfa969.testmodule;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Josef on 2014-03-06.
 */
public class ArticleFragment extends Fragment {

    public static int argIndex;

    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        assert getActivity() != null;
        text = ((FragmentCommunicationTest) getActivity()).getTextView();
        updateArticleView(argIndex);
    }

    public void updateArticleView(int index) {
        text.setText("Index: " + index);
    }
}
