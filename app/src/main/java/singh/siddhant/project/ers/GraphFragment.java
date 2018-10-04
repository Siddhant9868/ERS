package singh.siddhant.project.ers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Siddhant Singh on 1/19/2018.
 */

public class GraphFragment extends Fragment {
    public String S_id ;
    public DatabaseReference db;
    SumUtils sumUtils = new SumUtils();
    SumUtils sumUtils_oct = new SumUtils();
    SumUtils sumUtils_nov = new SumUtils();
    SumUtils sumUtils_dec = new SumUtils();
    GraphViewXML graph;
    private List<Contact> contacts = new ArrayList<>();
    int flag = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graph,
                container, false);
        //populateGraphView(view);

        Intent intent = getActivity().getIntent();
        String UserEmail = intent.getStringExtra("Email");
        if ((int) UserEmail.charAt(4) >= 55) {
            S_id = UserEmail.substring(0,1).toUpperCase() + UserEmail.substring(1,9) + "P";
            Log.d("CharaAt4",S_id + "  " + UserEmail.charAt(4));
        }
        else
            S_id = UserEmail.substring(0,1).toUpperCase() + UserEmail.substring(1,5) + "0" + UserEmail.substring(5,8) + "P";

        Log.d("TAG","S_ID = " + S_id);

        db = FirebaseDatabase.getInstance().getReference().child(S_id);

        //set dummy data
        //Contact contact = new Contact();
        sumUtils.setSumAt301(0f);
        sumUtils.setSumAtANC(0f);
        sumUtils.setSumAtFK(0f);
        sumUtils.setSumAtLooters(0f);
        sumUtils.setSumAtmess(0f);
        sumUtils.setTot(0f);

        sumUtils_oct.setSumAt301(0f);
        sumUtils_oct.setSumAtANC(0f);
        sumUtils_oct.setSumAtFK(0f);
        sumUtils_oct.setSumAtLooters(0f);
        sumUtils_oct.setSumAtmess(0f);
        sumUtils_oct.setTot(0f);


        sumUtils_nov.setSumAt301(0f);
        sumUtils_nov.setSumAtANC(0f);
        sumUtils_nov.setSumAtFK(0f);
        sumUtils_nov.setSumAtLooters(0f);
        sumUtils_nov.setSumAtmess(0f);
        sumUtils_nov.setTot(0f);


        sumUtils_dec.setSumAt301(0f);
        sumUtils_dec.setSumAtANC(0f);
        sumUtils_dec.setSumAtFK(0f);
        sumUtils_dec.setSumAtLooters(0f);
        sumUtils_dec.setSumAtmess(0f);
        sumUtils_dec.setTot(0f);


        retrieve();



        graph = (GraphViewXML) view.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 0),
                new DataPoint(1, 0),
                new DataPoint(2, 0),
                new DataPoint(3, 0),
                new DataPoint(4, 0)
                //new DataPoint(5,0)
        });
        graph.addSeries(series);
        graph.animate();

        graph.setTitle("Hang in there we are fetching data...");

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() + 8 *255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });


        series.setSpacing(30);
// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.parseColor("#003444"));
        series.setValuesOnTopSize(50);

        return view;
    }

    public void fetchData(DataSnapshot dataSnapshot) {

        if(flag == 1){
            flag = 0;
        }
        Log.w("Fetchdata","Inside Fetch data");

        float tot_mar = 0f;
        float tot_apr = 0f;

        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    Log.d("hola  ",contact.getDOR().substring(5,7));

                    contacts.add(contact);

                    if(contact.getDOR().substring(5,7).equals("09")) {
                        if (timestamp.getKey().equals("LOOTERS")) {
                            sumUtils.sumAtLooters += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("PITSTOP")) {
                            sumUtils.sumAtmess += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("ANC")) {
                            sumUtils.sumAtANC += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("FK")) {
                            sumUtils.sumAtFK += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("301F")) {
                            sumUtils.sumAt301 += contact.getRATE() * contact.getQTY();
                        }
                        Log.d("holaa","inside if " + String.valueOf(sumUtils.getSumAtANC()));
                    }

                    Log.d("holaa  ",String.valueOf(sumUtils.getTot()) + "  " + sumUtils.getSumAtmess() + "  " + sumUtils.getSumAt301() + "  " + sumUtils.getSumAtANC() + "  " + sumUtils.getSumAtLooters() + "  " + sumUtils.getSumAtFK());


                    sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());

                    if(contact.getDOR().substring(5,7).equals("10")){
                        if (timestamp.getKey().equals("LOOTERS")) {
                            sumUtils_oct.sumAtLooters += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("PITSTOP")) {
                            sumUtils_oct.sumAtmess += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("ANC")) {
                            sumUtils_oct.sumAtANC += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("FK")) {
                            sumUtils_oct.sumAtFK += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("301F")) {
                            sumUtils_oct.sumAt301 += contact.getRATE() * contact.getQTY();
                        }
                    }

                    sumUtils_oct.setTot(sumUtils_oct.getSumAtmess() + sumUtils_oct.getSumAt301() + sumUtils_oct.getSumAtANC() + sumUtils_oct.getSumAtLooters() + sumUtils_oct.getSumAtFK());

                    if(contact.getDOR().substring(5,7).equals("11")){
                        if (timestamp.getKey().equals("LOOTERS")) {
                            sumUtils_nov.sumAtLooters += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("PITSTOP")) {
                            sumUtils_nov.sumAtmess += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("ANC")) {
                            sumUtils_nov.sumAtANC += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("FK")) {
                            sumUtils_nov.sumAtFK += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("301F")) {
                            sumUtils_nov.sumAt301 += contact.getRATE() * contact.getQTY();
                        }
                    }

                    sumUtils_nov.setTot(sumUtils_nov.getSumAtmess() + sumUtils_nov.getSumAt301() + sumUtils_nov.getSumAtANC() + sumUtils_nov.getSumAtLooters() + sumUtils_nov.getSumAtFK());

                    if(contact.getDOR().substring(5,7).equals("12")){
                        if (timestamp.getKey().equals("LOOTERS")) {
                            sumUtils_dec.sumAtLooters += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("PITSTOP")) {
                            sumUtils_dec.sumAtmess += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("ANC")) {
                            sumUtils_dec.sumAtANC += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("FK")) {
                            sumUtils_dec.sumAtFK += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("301F")) {
                            sumUtils_dec.sumAt301 += contact.getRATE() * contact.getQTY();
                        }
                    }

                    sumUtils_dec.setTot(sumUtils_dec.getSumAtmess() + sumUtils_dec.getSumAt301() + sumUtils_dec.getSumAtANC() + sumUtils_dec.getSumAtLooters() + sumUtils_dec.getSumAtFK());

                    //mTextView.setText(str);

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }

        final Calendar c = Calendar.getInstance();
        final int mMonth = c.get(Calendar.MONTH) + 1;


        SharedPreferences check_reading = getActivity().getPreferences(Context.MODE_PRIVATE);
        final int check_read = check_reading.getInt(getString(R.string.monthlyCap),1);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(1, sumUtils.getTot()),
                new DataPoint(2, sumUtils_oct.getTot()),
                new DataPoint(3, sumUtils_nov.getTot()),
                new DataPoint(4, sumUtils_dec.getTot())
                //new DataPoint(5,0)
        });

        graph.removeAllSeries();
        graph.addSeries(series);
        graph.animate();

        graph.setTitle("Try tapping the bar!");
                /*graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graph.getLegendRenderer().setTextColor(Color.WHITE);
                graph.getLegendRenderer().setTextSize(50f);*/

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY() > check_read && data.getX() + 8 == mMonth){
                    graph.setTitle("Your Expenditure Cap crossed for this month");
                    return Color.RED;
                }
                return Color.parseColor("#1976d2");
            }
        });


        final Toast toast1 = Toast.makeText(getActivity(), "         SEPT         \nLooters - " + sumUtils.getSumAtLooters() + "\nANC - " + sumUtils.getSumAtANC() + "\n-301F - " + sumUtils.getSumAt301() + "\nFoodking - " + sumUtils.getSumAtFK() + "\nPitstop - " + sumUtils.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast2 = Toast.makeText(getActivity(), "         OCT         \nLooters - " + sumUtils_oct.getSumAtLooters() + "\nANC - " + sumUtils_oct.getSumAtANC() + "\n-301F - " + sumUtils_oct.getSumAt301() + "\nFoodking - " + sumUtils_oct.getSumAtFK() + "\nPitstop - " + sumUtils_oct.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast3 = Toast.makeText(getActivity(), "         NOV         \nLooters - " + sumUtils_nov.getSumAtLooters() + "\nANC - " + sumUtils_nov.getSumAtANC() + "\n-301F - " + sumUtils_nov.getSumAt301() + "\nFoodking - " + sumUtils_nov.getSumAtFK() + "\nPitstop - " + sumUtils_nov.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast4 = Toast.makeText(getActivity(), "         DEC         \nLooters - " + sumUtils_dec.getSumAtLooters() + "\nANC - " + sumUtils_dec.getSumAtANC() + "\n-301F - " + sumUtils_dec.getSumAt301() + "\nFoodking - " + sumUtils_dec.getSumAtFK() + "\nPitstop - " + sumUtils_dec.getSumAtmess(), Toast.LENGTH_SHORT);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {

            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                if(dataPoint.getX() == 1) {
                    toast2.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast1.show();
                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast1.cancel();
                        }
                    }, 20000);*/
                }
                if(dataPoint.getX() == 2) {
                    toast1.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast2.show();
                }
                if(dataPoint.getX() == 3) {
                    toast1.cancel();
                    toast2.cancel();
                    toast4.cancel();
                    toast3.show();
                }
                if(dataPoint.getX() == 4) {
                    toast1.cancel();
                    toast2.cancel();
                    toast3.cancel();
                    toast4.show();
                }

                //toast.setGravity(Gravity.TOP| Gravity.START, 0, 0);
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 6000);*/

            }
        });

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","SEPT", "OCT", "NOV","DEC"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        series.setSpacing(40);
// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.parseColor("#003444"));
        series.setValuesOnTopSize(45);
    }

    public void fetchData_change(DataSnapshot dataSnapshot) {

        if(flag == 1){
            flag = 0;
        }
        Log.w("Fetchdata","Inside Fetch data");

        float tot_mar = 0f;
        float tot_apr = 0f;

        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    Log.d("hola  ",contact.getDOR().substring(5,7));

                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()) {
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())) {

                            Log.d("removing...after...", String.valueOf((contacts.size())));

                            if (con.getDOR().substring(5, 7).equals("09")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                                Log.d("holaa", "inside if " + String.valueOf(sumUtils.getSumAtANC()));
                            }

                            Log.d("holaa  ", String.valueOf(sumUtils.getTot()) + "  " + sumUtils.getSumAtmess() + "  " + sumUtils.getSumAt301() + "  " + sumUtils.getSumAtANC() + "  " + sumUtils.getSumAtLooters() + "  " + sumUtils.getSumAtFK());


                            sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("10")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_oct.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_oct.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_oct.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_oct.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_oct.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_oct.setTot(sumUtils_oct.getSumAtmess() + sumUtils_oct.getSumAt301() + sumUtils_oct.getSumAtANC() + sumUtils_oct.getSumAtLooters() + sumUtils_oct.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("11")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_nov.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_nov.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_nov.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_nov.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_nov.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_nov.setTot(sumUtils_nov.getSumAtmess() + sumUtils_nov.getSumAt301() + sumUtils_nov.getSumAtANC() + sumUtils_nov.getSumAtLooters() + sumUtils_nov.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("12")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_dec.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_dec.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_dec.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_dec.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_dec.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_dec.setTot(sumUtils_dec.getSumAtmess() + sumUtils_dec.getSumAt301() + sumUtils_dec.getSumAtANC() + sumUtils_dec.getSumAtLooters() + sumUtils_dec.getSumAtFK());

                            iterator.remove();
                        }
                    }

                    //mTextView.setText(str);

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(1, sumUtils.getTot()),
                new DataPoint(2, sumUtils_oct.getTot()),
                new DataPoint(3, sumUtils_nov.getTot()),
                new DataPoint(4, sumUtils_dec.getTot())
                //new DataPoint(5,0)
        });
        graph.removeAllSeries();
        graph.addSeries(series);
        graph.animate();

        final Calendar c = Calendar.getInstance();
        final int mMonth = c.get(Calendar.MONTH) + 1;

        SharedPreferences check_reading = getActivity().getPreferences(Context.MODE_PRIVATE);
        final int check_read = check_reading.getInt(getString(R.string.monthlyCap),1);

        graph.setTitle("Try tapping the bar!");
                /*graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graph.getLegendRenderer().setTextColor(Color.WHITE);
                graph.getLegendRenderer().setTextSize(50f);*/

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY() > check_read && data.getX() + 8 == mMonth){
                    graph.setTitle("Your Expenditure Cap crossed for this month");
                    return Color.RED;
                }
                return Color.parseColor("#1976d2");
            }
        });


        final Toast toast1 = Toast.makeText(getActivity(), "         SEPT         \nLooters - " + sumUtils.getSumAtLooters() + "\nANC - " + sumUtils.getSumAtANC() + "\n-301F - " + sumUtils.getSumAt301() + "\nFoodking - " + sumUtils.getSumAtFK() + "\nPitstop - " + sumUtils.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast2 = Toast.makeText(getActivity(), "         OCT         \nLooters - " + sumUtils_oct.getSumAtLooters() + "\nANC - " + sumUtils_oct.getSumAtANC() + "\n-301F - " + sumUtils_oct.getSumAt301() + "\nFoodking - " + sumUtils_oct.getSumAtFK() + "\nPitstop - " + sumUtils_oct.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast3 = Toast.makeText(getActivity(), "         NOV         \nLooters - " + sumUtils_nov.getSumAtLooters() + "\nANC - " + sumUtils_nov.getSumAtANC() + "\n-301F - " + sumUtils_nov.getSumAt301() + "\nFoodking - " + sumUtils_nov.getSumAtFK() + "\nPitstop - " + sumUtils_nov.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast4 = Toast.makeText(getActivity(), "         DEC         \nLooters - " + sumUtils_dec.getSumAtLooters() + "\nANC - " + sumUtils_dec.getSumAtANC() + "\n-301F - " + sumUtils_dec.getSumAt301() + "\nFoodking - " + sumUtils_dec.getSumAtFK() + "\nPitstop - " + sumUtils_dec.getSumAtmess(), Toast.LENGTH_SHORT);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {

            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                if(dataPoint.getX() == 1) {
                    toast2.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast1.show();
                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast1.cancel();
                        }
                    }, 20000);*/
                }
                if(dataPoint.getX() == 2) {
                    toast1.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast2.show();
                }
                if(dataPoint.getX() == 3) {
                    toast1.cancel();
                    toast2.cancel();
                    toast4.cancel();
                    toast3.show();
                }
                if(dataPoint.getX() == 4) {
                    toast1.cancel();
                    toast2.cancel();
                    toast3.cancel();
                    toast4.show();
                }

                //toast.setGravity(Gravity.TOP| Gravity.START, 0, 0);
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 6000);*/

            }
        });
        series.setSpacing(40);
// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.parseColor("#003444"));
        series.setValuesOnTopSize(45);


        fetchData(dataSnapshot);
    }

    public void fetchData_remove(DataSnapshot dataSnapshot) {

        if(flag == 1){
            flag = 0;
        }
        Log.w("Fetchdata","Inside Fetch data");

        float tot_mar = 0f;
        float tot_apr = 0f;

        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    Log.d("hola  ",contact.getDOR().substring(5,7));

                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()) {
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())) {

                            Log.d("removing...after...", String.valueOf((contacts.size())));

                            if (con.getDOR().substring(5, 7).equals("09")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                                Log.d("holaa", "inside if " + String.valueOf(sumUtils.getSumAtANC()));
                            }

                            Log.d("holaa  ", String.valueOf(sumUtils.getTot()) + "  " + sumUtils.getSumAtmess() + "  " + sumUtils.getSumAt301() + "  " + sumUtils.getSumAtANC() + "  " + sumUtils.getSumAtLooters() + "  " + sumUtils.getSumAtFK());


                            sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("10")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_oct.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_oct.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_oct.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_oct.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_oct.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_oct.setTot(sumUtils_oct.getSumAtmess() + sumUtils_oct.getSumAt301() + sumUtils_oct.getSumAtANC() + sumUtils_oct.getSumAtLooters() + sumUtils_oct.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("11")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_nov.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_nov.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_nov.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_nov.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_nov.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_nov.setTot(sumUtils_nov.getSumAtmess() + sumUtils_nov.getSumAt301() + sumUtils_nov.getSumAtANC() + sumUtils_nov.getSumAtLooters() + sumUtils_nov.getSumAtFK());

                            if (con.getDOR().substring(5, 7).equals("12")) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_dec.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_dec.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_dec.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_dec.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_dec.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                            }

                            sumUtils_dec.setTot(sumUtils_dec.getSumAtmess() + sumUtils_dec.getSumAt301() + sumUtils_dec.getSumAtANC() + sumUtils_dec.getSumAtLooters() + sumUtils_dec.getSumAtFK());

                            iterator.remove();
                        }
                    }

                    //mTextView.setText(str);

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(1, sumUtils.getTot()),
                new DataPoint(2, sumUtils_oct.getTot()),
                new DataPoint(3, sumUtils_nov.getTot()),
                new DataPoint(4, sumUtils_dec.getTot())
                //new DataPoint(5,0)
        });
        graph.removeAllSeries();
        graph.addSeries(series);
        graph.animate();

        final Calendar c = Calendar.getInstance();
        final int mMonth = c.get(Calendar.MONTH) + 1;

        SharedPreferences check_reading = getActivity().getPreferences(Context.MODE_PRIVATE);
        final int check_read = check_reading.getInt(getString(R.string.monthlyCap),1);

        graph.setTitle("Try tapping the bar!");
                /*graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graph.getLegendRenderer().setTextColor(Color.WHITE);
                graph.getLegendRenderer().setTextSize(50f);*/

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                if(data.getY() > check_read && data.getX() + 8 == mMonth){
                    graph.setTitle("Your Expenditure Cap crossed for this month");
                    return Color.RED;
                }
                return Color.parseColor("#1976d2");
            }
        });


        final Toast toast1 = Toast.makeText(getActivity(), "         SEPT         \nLooters - " + sumUtils.getSumAtLooters() + "\nANC - " + sumUtils.getSumAtANC() + "\n-301F - " + sumUtils.getSumAt301() + "\nFoodking - " + sumUtils.getSumAtFK() + "\nPitstop - " + sumUtils.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast2 = Toast.makeText(getActivity(), "         OCT         \nLooters - " + sumUtils_oct.getSumAtLooters() + "\nANC - " + sumUtils_oct.getSumAtANC() + "\n-301F - " + sumUtils_oct.getSumAt301() + "\nFoodking - " + sumUtils_oct.getSumAtFK() + "\nPitstop - " + sumUtils_oct.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast3 = Toast.makeText(getActivity(), "         NOV         \nLooters - " + sumUtils_nov.getSumAtLooters() + "\nANC - " + sumUtils_nov.getSumAtANC() + "\n-301F - " + sumUtils_nov.getSumAt301() + "\nFoodking - " + sumUtils_nov.getSumAtFK() + "\nPitstop - " + sumUtils_nov.getSumAtmess(), Toast.LENGTH_SHORT);
        final Toast toast4 = Toast.makeText(getActivity(), "         DEC         \nLooters - " + sumUtils_dec.getSumAtLooters() + "\nANC - " + sumUtils_dec.getSumAtANC() + "\n-301F - " + sumUtils_dec.getSumAt301() + "\nFoodking - " + sumUtils_dec.getSumAtFK() + "\nPitstop - " + sumUtils_dec.getSumAtmess(), Toast.LENGTH_SHORT);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {

            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                if(dataPoint.getX() == 1) {
                    toast2.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast1.show();
                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast1.cancel();
                        }
                    }, 20000);*/
                }
                if(dataPoint.getX() == 2) {
                    toast1.cancel();
                    toast3.cancel();
                    toast4.cancel();
                    toast2.show();
                }
                if(dataPoint.getX() == 3) {
                    toast1.cancel();
                    toast2.cancel();
                    toast4.cancel();
                    toast3.show();
                }
                if(dataPoint.getX() == 4) {
                    toast1.cancel();
                    toast2.cancel();
                    toast3.cancel();
                    toast4.show();
                }

                //toast.setGravity(Gravity.TOP| Gravity.START, 0, 0);
                /*Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 6000);*/

            }
        });
        series.setSpacing(40);
// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.parseColor("#003444"));
        series.setValuesOnTopSize(45);

    }

    public void retrieve()
    {
        Log.w("Retrieve","Inside Retrieve data");
        Query query = db.orderByChild("timestampCreated/date");
        Log.d("retrieve query - ",query.toString());
        db.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.w("Retrieve onchiladded","Inside Retrieve data");
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                /*sumUtils.setSumAt301(0f);
                sumUtils.setSumAtANC(0f);
                sumUtils.setSumAtFK(0f);
                sumUtils.setSumAtLooters(0f);
                sumUtils.setSumAtmess(0f);
                sumUtils.setTot(0f);*/
                //fetchData_remove(dataSnapshot);
                //fetchData(dataSnapshot);
                fetchData_change(dataSnapshot);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();
                fetchData_remove(dataSnapshot);
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    /*
    private void populateGraphView(View view) {
        // init example series data
        GraphView exampleSeries = new GraphView(new GraphViewData[] {
                new GraphViewData(1, 2.0d)
                , new GraphViewData(2, 1.5d)
                , new GraphViewData(3, 2.5d)
                , new GraphViewData(4, 1.0d)
        });

        LineGraphView graphView = new LineGraphView(
                getActivity() // context
                , "GraphViewDemo" // heading
        );
        graphView.addSeries(exampleSeries); // data

        try {
            LinearLayout layout = (LinearLayout) view.findViewById(R.id.graph1);
            layout.addView(graphView);
        } catch (NullPointerException e) {
            // something to handle the NPE.
        }
    }*/
}