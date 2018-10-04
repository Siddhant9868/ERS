package singh.siddhant.project.ers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Jauhar xlr on 4/18/2016.
 *  mycreativecodes.in
 */

public class JoinusFragment extends Fragment {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.join_us,null);

        textView1 = rootView.findViewById(R.id.join_us_text1);
        textView2 = rootView.findViewById(R.id.join_us_text2);
        textView3 = rootView.findViewById(R.id.join_us_text3);
        textView4 = rootView.findViewById(R.id.join_us_text4);

        textView1.setText("Have ideas like this and are super-psyched to execute them?");
        textView2.setText("Wondering how the \nfirebase backend is working on this massive data?");
        textView3.setText("Curious about the design thinking process required for appealing UI and smooth UX?");
        textView4.setText("All aside,\nwant to work with people with real ideas?");

        return rootView;

    }
}
