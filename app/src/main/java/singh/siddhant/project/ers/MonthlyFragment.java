package singh.siddhant.project.ers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jauhar xlr on 4/18/2016.
 *  mycreativecodes.in
 */

public class MonthlyFragment extends Fragment {
    public EditText dev1;
    TextView set_text;
    Button change_cap;
    ImageView rupee;
    final String PREF_FILE_NAME = "SettingsActivity";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.monthly_layout,null);
        dev1 = rootView.findViewById(R.id.editText);
        change_cap = rootView.findViewById(R.id.change_monthly_cap);
        set_text = rootView.findViewById(R.id.set_text);
        rupee = rootView.findViewById(R.id.rupee);

        final Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String month = String.valueOf(date);

        dev1.setSelected(true);

        SharedPreferences check_reading = getActivity().getPreferences(Context.MODE_PRIVATE);
        int check_read = check_reading.getInt(getString(R.string.monthlyCap),1);

        if(check_read>1){
            dev1.setText(String.valueOf(check_read));
            dev1.setEnabled(false);
            set_text.setText("Your Expenditure Cap\n for " + month.substring(4,7) + " is set to -");
            rupee.setImageResource(R.drawable.ic_512px_indian_rupee_symbolsvg_for_monthlycap_grey);
            Log.e("Hello from the other si", String.valueOf(check_read));
        }

        else {
            set_text.setText("Your Expenditure Cap\n for " + month.substring(4,7) + " is set to -");
            dev1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        // TODO do something
                        int cap = Integer.valueOf(dev1.getText().toString());
                        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.monthlyCap), cap);
                        editor.apply();

                        SharedPreferences reading = getActivity().getPreferences(Context.MODE_PRIVATE);
                        int read = reading.getInt(getString(R.string.monthlyCap), 1);

                        Log.e("Hello from the other si", String.valueOf(read));
                        dev1.setEnabled(false);
                        handled = true;
                    }
                    return handled;
                }
            });
        }

        change_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dev1.setEnabled(true);
                rupee.setImageResource(R.drawable.ic_512px_indian_rupee_symbolsvg_for_monthlycap);
                dev1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // TODO do something
                            int cap = Integer.valueOf(dev1.getText().toString());
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putInt(getString(R.string.monthlyCap), cap);
                            editor.apply();

                            SharedPreferences reading = getActivity().getPreferences(Context.MODE_PRIVATE);
                            int read = reading.getInt(getString(R.string.monthlyCap), 1);

                            Log.e("Hello from the other si", String.valueOf(read));

                            dev1.setEnabled(false);
                            rupee.setImageResource(R.drawable.ic_512px_indian_rupee_symbolsvg_for_monthlycap_grey);
                            handled = true;
                        }
                        return handled;
                    }
                });
            }
        });

        return rootView;
    }

}
