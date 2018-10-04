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

public class DeveloperFragment extends Fragment {
    TextView dev1;
    TextView dev2;
    TextView dev3;
    TextView dev4;
    TextView dev5;
    TextView dev1_description;
    TextView dev2_description;
    TextView dev3_description;
    TextView dev4_description;
    TextView dev5_description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.developer_layout,null);
        dev1 = rootView.findViewById(R.id.dev1);
        dev2 = rootView.findViewById(R.id.dev2);
        dev3 = rootView.findViewById(R.id.dev3);
        dev4 = rootView.findViewById(R.id.dev4);
        dev5 = rootView.findViewById(R.id.dev5);
        dev1_description = rootView.findViewById(R.id.dev1_description);
        dev2_description = rootView.findViewById(R.id.dev2_description);
        dev3_description = rootView.findViewById(R.id.dev3_description);
        dev4_description = rootView.findViewById(R.id.dev4_description);
        dev5_description = rootView.findViewById(R.id.dev5_description);

        dev1.setText("\nAkash Singh");
        dev1_description.setText("Operations &\nExecution");

        dev2.setText("\nSiddhant Singh");
        dev2_description.setText("Full Stack Dev.\nDatabase Mgmt.");

        dev3.setText("\nSiddharth Mehta");
        dev3_description.setText("Ideation &\nProduct Design");

        dev4.setText("\nPalash Dhakar");
        dev4_description.setText("Design &\nCreativity");

        dev5.setText("\nKanishk Rajvanshi");
        dev5_description.setText("Backend Assistance");

        return rootView;
    }

}
