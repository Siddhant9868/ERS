package singh.siddhant.project.ers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jauhar xlr on 4/18/2016.
 *  mycreativecodes.in
 */

public class SumsTabFragment extends Fragment {
    public TextView todays_view;
    public TextView looters_view;
    public TextView anc_view;
    public TextView f301_view;
    public TextView fk_view;
    public TextView pitstop_view;
    public TextView this_sem_story;
    public TextView this_mon_story;
    public TextView todays_story;
    public TextView this_sem_anc;
    public TextView this_mon_anc;
    public TextView todays_anc;
    public TextView this_sem_looters;
    public TextView this_mon_looters;
    public TextView todays_looters;
    public TextView this_sem_pitstop;
    public TextView this_mon_pitstop;
    public TextView todays_pitstop;
    public TextView this_sem_301f;
    public TextView this_mon_301f;
    public TextView todays_301f;
    public TextView this_sem_foodking;
    public TextView this_mon_foodking;
    public TextView todays_foodking;

    public ImageView DashboardImage;
    public View view;
    public View rootView;
    public String S_id ;
    public DatabaseReference db;
    public List<Contact> contacts = new ArrayList<>();
    SumUtils sumUtils = new SumUtils();
    SumUtils sumUtils_mon = new SumUtils();
    SumUtils sumUtils_sem = new SumUtils();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.sums_layout, null);
        fk_view = (TextView) rootView.findViewById(R.id.fk_value);
        looters_view = (TextView) rootView.findViewById(R.id.looters_value);
        anc_view = (TextView) rootView.findViewById(R.id.anc_value);
        f301_view = (TextView) rootView.findViewById(R.id.f301_value);
        pitstop_view = (TextView) rootView.findViewById(R.id.pitstop_value);
        todays_view = (TextView) rootView.findViewById(R.id.todays_value);
        DashboardImage = (ImageView) rootView.findViewById(R.id.dashboardimage);

        this_sem_story = rootView.findViewById(R.id.this_sems_story);
        this_mon_story = rootView.findViewById(R.id.this_mons_story);
        todays_story = rootView.findViewById(R.id.todays_story);
        this_sem_anc = rootView.findViewById(R.id.this_sems_anc);
        this_mon_anc = rootView.findViewById(R.id.this_mons_anc);
        todays_anc = rootView.findViewById(R.id.todays_anc);
        this_sem_looters = rootView.findViewById(R.id.this_sems_looters);
        this_mon_looters = rootView.findViewById(R.id.this_mons_looters);
        todays_looters = rootView.findViewById(R.id.todays_looters);
        this_sem_301f = rootView.findViewById(R.id.this_sems_301f);
        this_mon_301f = rootView.findViewById(R.id.this_mons_301f);
        todays_301f = rootView.findViewById(R.id.todays_301f);
        this_sem_foodking = rootView.findViewById(R.id.this_sems_foodking);
        this_mon_foodking = rootView.findViewById(R.id.this_mons_foodking);
        todays_foodking = rootView.findViewById(R.id.todays_foodking);
        this_sem_pitstop = rootView.findViewById(R.id.this_sems_pitstop);
        this_mon_pitstop = rootView.findViewById(R.id.this_mons_pitstop);
        todays_pitstop = rootView.findViewById(R.id.todays_pitsop);

        DashboardImage.setImageResource(R.drawable.dashboard_storyuntilnow);

        todays_story.setVisibility(View.VISIBLE);
        this_mon_story.setVisibility(View.VISIBLE);
        this_sem_story.setVisibility(View.VISIBLE);
        todays_anc.setVisibility(View.INVISIBLE);
        this_mon_anc.setVisibility(View.INVISIBLE);
        this_sem_anc.setVisibility(View.INVISIBLE);
        todays_301f.setVisibility(View.INVISIBLE);
        this_mon_301f.setVisibility(View.INVISIBLE);
        this_sem_301f.setVisibility(View.INVISIBLE);
        todays_foodking.setVisibility(View.INVISIBLE);
        this_mon_foodking.setVisibility(View.INVISIBLE);
        this_sem_foodking.setVisibility(View.INVISIBLE);
        todays_looters.setVisibility(View.INVISIBLE);
        this_mon_looters.setVisibility(View.INVISIBLE);
        this_sem_looters.setVisibility(View.INVISIBLE);
        todays_pitstop.setVisibility(View.INVISIBLE);
        this_mon_pitstop.setVisibility(View.INVISIBLE);
        this_sem_pitstop.setVisibility(View.INVISIBLE);


        final float centreX = DashboardImage.getX() + DashboardImage.getWidth() / 2;

        DashboardImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float centreY = DashboardImage.getY() + DashboardImage.getHeight()/2;
                final int action = motionEvent.getAction();
                final float dX = (int) motionEvent.getX() - centreX;
                final float dY = (int) motionEvent.getY();

                if(dY < 1060 && dY > 800 && dX > 220 && dX < 830) {
                    Log.d("dY", String.valueOf(dY));
                    Log.d("dX", String.valueOf(dX));
                    Log.d("center", String.valueOf(DashboardImage.getY()));
                    //Toast.makeText(getActivity(),"Bata kya karna hai - ",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_storyuntilnow);
                    todays_story.setVisibility(View.VISIBLE);
                    this_mon_story.setVisibility(View.VISIBLE);
                    this_sem_story.setVisibility(View.VISIBLE);
                    todays_anc.setVisibility(View.INVISIBLE);
                    this_mon_anc.setVisibility(View.INVISIBLE);
                    this_sem_anc.setVisibility(View.INVISIBLE);
                    todays_301f.setVisibility(View.INVISIBLE);
                    this_mon_301f.setVisibility(View.INVISIBLE);
                    this_sem_301f.setVisibility(View.INVISIBLE);
                    todays_foodking.setVisibility(View.INVISIBLE);
                    this_mon_foodking.setVisibility(View.INVISIBLE);
                    this_sem_foodking.setVisibility(View.INVISIBLE);
                    todays_looters.setVisibility(View.INVISIBLE);
                    this_mon_looters.setVisibility(View.INVISIBLE);
                    this_sem_looters.setVisibility(View.INVISIBLE);
                    todays_pitstop.setVisibility(View.INVISIBLE);
                    this_mon_pitstop.setVisibility(View.INVISIBLE);
                    this_sem_pitstop.setVisibility(View.INVISIBLE);
                    //DashboardImage.setVisibility(View.INVISIBLE);
                //Story until now
                }

                if(dY < 810 && dY > 440 && dX > 840 && dX < 1030) {
                    //Toast.makeText(getActivity(),"Pitstop",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_pitstop);
                    todays_story.setVisibility(View.INVISIBLE);
                    this_mon_story.setVisibility(View.INVISIBLE);
                    this_sem_story.setVisibility(View.INVISIBLE);
                    todays_anc.setVisibility(View.INVISIBLE);
                    this_mon_anc.setVisibility(View.INVISIBLE);
                    this_sem_anc.setVisibility(View.INVISIBLE);
                    todays_301f.setVisibility(View.INVISIBLE);
                    this_mon_301f.setVisibility(View.INVISIBLE);
                    this_sem_301f.setVisibility(View.INVISIBLE);
                    todays_foodking.setVisibility(View.INVISIBLE);
                    this_mon_foodking.setVisibility(View.INVISIBLE);
                    this_sem_foodking.setVisibility(View.INVISIBLE);
                    todays_looters.setVisibility(View.INVISIBLE);
                    this_mon_looters.setVisibility(View.INVISIBLE);
                    this_sem_looters.setVisibility(View.INVISIBLE);
                    todays_pitstop.setVisibility(View.VISIBLE);
                    this_mon_pitstop.setVisibility(View.VISIBLE);
                    this_sem_pitstop.setVisibility(View.VISIBLE);


                }
                //pitstop

                if(dY < 810 && dY > 440 && dX > 15 && dX < 205) {
                    //Toast.makeText(getActivity(),"-301F",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_301f);
                    todays_story.setVisibility(View.INVISIBLE);
                    this_mon_story.setVisibility(View.INVISIBLE);
                    this_sem_story.setVisibility(View.INVISIBLE);
                    todays_anc.setVisibility(View.INVISIBLE);
                    this_mon_anc.setVisibility(View.INVISIBLE);
                    this_sem_anc.setVisibility(View.INVISIBLE);
                    todays_301f.setVisibility(View.VISIBLE);
                    this_mon_301f.setVisibility(View.VISIBLE);
                    this_sem_301f.setVisibility(View.VISIBLE);
                    todays_foodking.setVisibility(View.INVISIBLE);
                    this_mon_foodking.setVisibility(View.INVISIBLE);
                    this_sem_foodking.setVisibility(View.INVISIBLE);
                    todays_looters.setVisibility(View.INVISIBLE);
                    this_mon_looters.setVisibility(View.INVISIBLE);
                    this_sem_looters.setVisibility(View.INVISIBLE);
                    todays_pitstop.setVisibility(View.INVISIBLE);
                    this_mon_pitstop.setVisibility(View.INVISIBLE);
                    this_sem_pitstop.setVisibility(View.INVISIBLE);

                }
                //301F

                if(dY < 445 && dY > 120 && dX > 50 && dX < 300) {
                    //Toast.makeText(getActivity(),"Foodking",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_fk);
                    todays_story.setVisibility(View.INVISIBLE);
                    this_mon_story.setVisibility(View.INVISIBLE);
                    this_sem_story.setVisibility(View.INVISIBLE);
                    todays_anc.setVisibility(View.INVISIBLE);
                    this_mon_anc.setVisibility(View.INVISIBLE);
                    this_sem_anc.setVisibility(View.INVISIBLE);
                    todays_301f.setVisibility(View.INVISIBLE);
                    this_mon_301f.setVisibility(View.INVISIBLE);
                    this_sem_301f.setVisibility(View.INVISIBLE);
                    todays_foodking.setVisibility(View.VISIBLE);
                    this_mon_foodking.setVisibility(View.VISIBLE);
                    this_sem_foodking.setVisibility(View.VISIBLE);
                    todays_looters.setVisibility(View.INVISIBLE);
                    this_mon_looters.setVisibility(View.INVISIBLE);
                    this_sem_looters.setVisibility(View.INVISIBLE);
                    todays_pitstop.setVisibility(View.INVISIBLE);
                    this_mon_pitstop.setVisibility(View.INVISIBLE);
                    this_sem_pitstop.setVisibility(View.INVISIBLE);

                }
                //Foodking

                if(dY < 445 && dY > 120 && dX > 745 && dX < 1000) {
                    //Toast.makeText(getActivity(),"Looters ",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_looters);
                    todays_story.setVisibility(View.INVISIBLE);
                    this_mon_story.setVisibility(View.INVISIBLE);
                    this_sem_story.setVisibility(View.INVISIBLE);
                    todays_anc.setVisibility(View.INVISIBLE);
                    this_mon_anc.setVisibility(View.INVISIBLE);
                    this_sem_anc.setVisibility(View.INVISIBLE);
                    todays_301f.setVisibility(View.INVISIBLE);
                    this_mon_301f.setVisibility(View.INVISIBLE);
                    this_sem_301f.setVisibility(View.INVISIBLE);
                    todays_foodking.setVisibility(View.INVISIBLE);
                    this_mon_foodking.setVisibility(View.INVISIBLE);
                    this_sem_foodking.setVisibility(View.INVISIBLE);
                    todays_looters.setVisibility(View.VISIBLE);
                    this_mon_looters.setVisibility(View.VISIBLE);
                    this_sem_looters.setVisibility(View.VISIBLE);
                    todays_pitstop.setVisibility(View.INVISIBLE);
                    this_mon_pitstop.setVisibility(View.INVISIBLE);
                    this_sem_pitstop.setVisibility(View.INVISIBLE);

                }
                //Looters

                if(dY < 180 && dY > 10 && dX > 335 && dX < 715) {
                    //Toast.makeText(getActivity(),"ANC ",Toast.LENGTH_SHORT).show();
                    DashboardImage.setImageResource(R.drawable.dashboard_anc);
                    todays_story.setVisibility(View.INVISIBLE);
                    this_mon_story.setVisibility(View.INVISIBLE);
                    this_sem_story.setVisibility(View.INVISIBLE);
                    todays_anc.setVisibility(View.VISIBLE);
                    this_mon_anc.setVisibility(View.VISIBLE);
                    this_sem_anc.setVisibility(View.VISIBLE);
                    todays_301f.setVisibility(View.INVISIBLE);
                    this_mon_301f.setVisibility(View.INVISIBLE);
                    this_sem_301f.setVisibility(View.INVISIBLE);
                    todays_foodking.setVisibility(View.INVISIBLE);
                    this_mon_foodking.setVisibility(View.INVISIBLE);
                    this_sem_foodking.setVisibility(View.INVISIBLE);
                    todays_looters.setVisibility(View.INVISIBLE);
                    this_mon_looters.setVisibility(View.INVISIBLE);
                    this_sem_looters.setVisibility(View.INVISIBLE);
                    todays_pitstop.setVisibility(View.INVISIBLE);
                    this_mon_pitstop.setVisibility(View.INVISIBLE);
                    this_sem_pitstop.setVisibility(View.INVISIBLE);

                }
                //ANC

                return true;
            }
        });

        Log.d("Sumstanfragment","Came here hello");

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

        sumUtils_mon.setSumAt301(0f);
        sumUtils_mon.setSumAtANC(0f);
        sumUtils_mon.setSumAtFK(0f);
        sumUtils_mon.setSumAtLooters(0f);
        sumUtils_mon.setSumAtmess(0f);

        sumUtils_sem.setSumAt301(0f);
        sumUtils_sem.setSumAtANC(0f);
        sumUtils_sem.setSumAtFK(0f);
        sumUtils_sem.setSumAtLooters(0f);
        sumUtils_sem.setSumAtmess(0f);

        retrieve();

        return rootView;
    }

    public void fetchData(DataSnapshot dataSnapshot)
    {

        Log.w("Fetchdata","Inside Fetch data sums");

        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,LOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    final Calendar c = Calendar.getInstance();
                    int mMonth = c.get(Calendar.MONTH) + 1;
                    int mDay = c.get(Calendar.DATE);

                    String current_month = mMonth<10?("0"+String.valueOf(mMonth)):String.valueOf(mMonth);
                    String current_day = mDay<10?("0"+String.valueOf(mDay)):String.valueOf(mDay);

                    Log.d("contact data day " + contact.getDOR().substring(8, 10), "Sysytem date " + String.valueOf(mMonth));

                    contacts.add(contact);

                    if (timestamp.getKey().equals("LOOTERS")) {
                        sumUtils_sem.sumAtLooters += contact.getRATE() * contact.getQTY();
                    } else if (timestamp.getKey().equals("PITSTOP")) {
                        sumUtils_sem.sumAtmess += contact.getRATE() * contact.getQTY();
                    } else if (timestamp.getKey().equals("ANC")) {
                        sumUtils_sem.sumAtANC += contact.getRATE() * contact.getQTY();
                    } else if (timestamp.getKey().equals("FK")) {
                        sumUtils_sem.sumAtFK += contact.getRATE() * contact.getQTY();
                    } else if (timestamp.getKey().equals("301F")) {
                        sumUtils_sem.sumAt301 += contact.getRATE() * contact.getQTY();
                    }

                    Log.d("holaa","inside if " + String.valueOf(sumUtils_sem.getSumAtANC()));

                    if(contact.getDOR().substring(5,7).equals(current_month)) {
                        if (timestamp.getKey().equals("LOOTERS")) {
                            sumUtils_mon.sumAtLooters += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("PITSTOP")) {
                            sumUtils_mon.sumAtmess += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("ANC")) {
                            sumUtils_mon.sumAtANC += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("FK")) {
                            sumUtils_mon.sumAtFK += contact.getRATE() * contact.getQTY();
                        } else if (timestamp.getKey().equals("301F")) {
                            sumUtils_mon.sumAt301 += contact.getRATE() * contact.getQTY();
                        }

                        if (contact.getDOR().substring(8, 10).equals(current_day)) {

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
                            Log.d("todays anc",String.valueOf(sumUtils.getSumAtANC()) + " DOR" + String.valueOf(contact.getDOR()));
                        }
                        Log.d("holaa","inside if " + String.valueOf(sumUtils.getSumAtANC()));
                    }

                    Log.d("holaa  ",String.valueOf(sumUtils_mon.getTot()) + "  " + sumUtils_mon.getSumAtmess() + "  " + sumUtils_mon.getSumAt301() + "  " + sumUtils_mon.getSumAtANC() + "  " + sumUtils_mon.getSumAtLooters() + "  " + sumUtils_mon.getSumAtFK());

                    sumUtils_mon.setTot(sumUtils_mon.getSumAtmess() + sumUtils_mon.getSumAt301() + sumUtils_mon.getSumAtANC() + sumUtils_mon.getSumAtLooters() + sumUtils_mon.getSumAtFK());

                    sumUtils_sem.setTot(sumUtils_sem.getSumAtmess() + sumUtils_sem.getSumAt301() + sumUtils_sem.getSumAtANC() + sumUtils_sem.getSumAtLooters() + sumUtils_sem.getSumAtFK());

                    sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());

                    /*todays_view.setText(String.valueOf(sumUtils.getTot()));
                    looters_view.setText(String.valueOf(sumUtils.getSumAtLooters()));
                    anc_view.setText(String.valueOf(sumUtils.getSumAtANC()));
                    fk_view.setText(String.valueOf(sumUtils.getSumAtFK()));
                    f301_view.setText(String.valueOf(sumUtils.getSumAt301()));
                    pitstop_view.setText(String.valueOf(sumUtils.getSumAtmess()));*/

                    this_sem_story.setText("Rs " + String.valueOf(sumUtils_sem.getTot()));
                    this_mon_story.setText("Rs " + String.valueOf(sumUtils_mon.getTot()));
                    todays_story.setText("Rs " + String .valueOf(sumUtils.getTot()));

                    this_sem_anc.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtANC()));
                    this_mon_anc.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtANC()));
                    todays_anc.setText("Rs " + String .valueOf(sumUtils.getSumAtANC()));

                    this_sem_looters.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtLooters()));
                    this_mon_looters.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtLooters()));
                    todays_looters.setText("Rs " + String .valueOf(sumUtils.getSumAtLooters()));

                    this_sem_301f.setText("Rs " + String.valueOf(sumUtils_sem.getSumAt301()));
                    this_mon_301f.setText("Rs " + String.valueOf(sumUtils_mon.getSumAt301()));
                    todays_301f.setText("Rs " + String .valueOf(sumUtils.getSumAt301()));

                    this_sem_foodking.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtFK()));
                    this_mon_foodking.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtFK()));
                    todays_foodking.setText("Rs " + String .valueOf(sumUtils.getSumAtFK()));

                    this_sem_pitstop.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtmess()));
                    this_mon_pitstop.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtmess()));
                    todays_pitstop.setText("Rs " + String .valueOf(sumUtils.getSumAtmess()));

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
    }

    public void fetchData_change(DataSnapshot dataSnapshot) {

        Log.w("Fetchdata", "Inside Fetch data Remove sums");

        for (DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,LOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    final Calendar c = Calendar.getInstance();
                    int mMonth = c.get(Calendar.MONTH) + 1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    String current_month = mMonth < 10 ? ("0" + String.valueOf(mMonth)) : String.valueOf(mMonth);
                    String current_day = mDay < 10 ? ("0" + String.valueOf(mDay)) : String.valueOf(mDay);

                    Log.d("contact data day " + contact.getDOR().substring(8, 10), "Sysytem date" + String.valueOf(mDay));

                    Log.d("datasnapshot in rm ST", String.valueOf((contacts.size())));
                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()) {
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())) {
                            Log.d("datasnapshot rm af", String.valueOf((contacts.size())));
                            Log.d("datasnapshot rm af TId", String.valueOf(con.getT_ID()));
                            if (timestamp.getKey().equals("LOOTERS")) {
                                sumUtils_sem.sumAtLooters -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("PITSTOP")) {
                                sumUtils_sem.sumAtmess -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("ANC")) {
                                sumUtils_sem.sumAtANC -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("FK")) {
                                sumUtils_sem.sumAtFK -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("301F")) {
                                sumUtils_sem.sumAt301 -= con.getRATE() * con.getQTY();
                            }
                            Log.d("holee", "inside if " + String.valueOf(contact.getDOR()) + " month  " + current_month);

                            if (con.getDOR().substring(5, 7).equals(current_month)) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_mon.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_mon.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_mon.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_mon.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_mon.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                                if (con.getDOR().substring(8, 10).equals(current_day)) {

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
                                }
                                Log.d("holaa", "inside if " + String.valueOf(sumUtils.getSumAtANC()));
                            }

                            iterator.remove();
                        }
                    }

                    /*String str = String.valueOf(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters());
                    todays_view.setText(str);
                    looters_view.setText(String.valueOf(sumUtils.getSumAtLooters()));
                    anc_view.setText(String.valueOf(sumUtils.getSumAtANC()));
                    fk_view.setText(String.valueOf(sumUtils.getSumAtFK()));
                    f301_view.setText(String.valueOf(sumUtils.getSumAt301()));
                    pitstop_view.setText(String.valueOf(sumUtils.getSumAtmess()));*/

                    sumUtils_mon.setTot(sumUtils_mon.getSumAtmess() + sumUtils_mon.getSumAt301() + sumUtils_mon.getSumAtANC() + sumUtils_mon.getSumAtLooters() + sumUtils_mon.getSumAtFK());

                    sumUtils_sem.setTot(sumUtils_sem.getSumAtmess() + sumUtils_sem.getSumAt301() + sumUtils_sem.getSumAtANC() + sumUtils_sem.getSumAtLooters() + sumUtils_sem.getSumAtFK());

                    sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());


                    this_sem_story.setText("Rs " + String.valueOf(sumUtils_sem.getTot()));
                    this_mon_story.setText("Rs " + String.valueOf(sumUtils_mon.getTot()));
                    todays_story.setText("Rs " + String.valueOf(sumUtils.getTot()));

                    this_sem_anc.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtANC()));
                    this_mon_anc.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtANC()));
                    todays_anc.setText("Rs " + String.valueOf(sumUtils.getSumAtANC()));

                    this_sem_looters.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtLooters()));
                    this_mon_looters.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtLooters()));
                    todays_looters.setText("Rs " + String.valueOf(sumUtils.getSumAtLooters()));

                    this_sem_301f.setText("Rs " + String.valueOf(sumUtils_sem.getSumAt301()));
                    this_mon_301f.setText("Rs " + String.valueOf(sumUtils_mon.getSumAt301()));
                    todays_301f.setText("Rs " + String.valueOf(sumUtils.getSumAt301()));

                    this_sem_foodking.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtFK()));
                    this_mon_foodking.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtFK()));
                    todays_foodking.setText("Rs " + String.valueOf(sumUtils.getSumAtFK()));

                    this_sem_pitstop.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtmess()));
                    this_mon_pitstop.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtmess()));
                    todays_pitstop.setText("Rs " + String.valueOf(sumUtils.getSumAtmess()));

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
        Log.d("datasnapshot print",String.valueOf(sumUtils.getTot()));
        fetchData(dataSnapshot);
    }

    public void fetchData_remove(DataSnapshot dataSnapshot) {

        Log.w("Fetchdata", "Inside Fetch data Remove sums");

        for (DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,LOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    final Calendar c = Calendar.getInstance();
                    int mMonth = c.get(Calendar.MONTH) + 1;
                    int mDay = c.get(Calendar.DAY_OF_MONTH);

                    String current_month = mMonth < 10 ? ("0" + String.valueOf(mMonth)) : String.valueOf(mMonth);
                    String current_day = mDay < 10 ? ("0" + String.valueOf(mDay)) : String.valueOf(mDay);

                    Log.d("contact data day " + contact.getDOR().substring(8, 10), "Sysytem date" + String.valueOf(mDay));

                    Log.d("datasnapshot in rm ST", String.valueOf((contacts.size())));
                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()) {
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())) {
                            Log.d("datasnapshot rm af", String.valueOf((contacts.size())));
                            Log.d("datasnapshot rm af TId", String.valueOf(con.getT_ID()));
                            if (timestamp.getKey().equals("LOOTERS")) {
                                sumUtils_sem.sumAtLooters -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("PITSTOP")) {
                                sumUtils_sem.sumAtmess -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("ANC")) {
                                sumUtils_sem.sumAtANC -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("FK")) {
                                sumUtils_sem.sumAtFK -= con.getRATE() * con.getQTY();
                            } else if (timestamp.getKey().equals("301F")) {
                                sumUtils_sem.sumAt301 -= con.getRATE() * con.getQTY();
                            }
                            Log.d("holee", "inside if " + String.valueOf(contact.getDOR()) + " month  " + current_month);

                            if (con.getDOR().substring(5, 7).equals(current_month)) {
                                if (timestamp.getKey().equals("LOOTERS")) {
                                    sumUtils_mon.sumAtLooters -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("PITSTOP")) {
                                    sumUtils_mon.sumAtmess -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("ANC")) {
                                    sumUtils_mon.sumAtANC -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("FK")) {
                                    sumUtils_mon.sumAtFK -= con.getRATE() * con.getQTY();
                                } else if (timestamp.getKey().equals("301F")) {
                                    sumUtils_mon.sumAt301 -= con.getRATE() * con.getQTY();
                                }
                                if (con.getDOR().substring(8, 10).equals(current_day)) {

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
                                }
                                Log.d("holaa", "inside if " + String.valueOf(sumUtils.getSumAtANC()));
                            }

                            iterator.remove();
                        }
                    }

                    /*String str = String.valueOf(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters());
                    todays_view.setText(str);
                    looters_view.setText(String.valueOf(sumUtils.getSumAtLooters()));
                    anc_view.setText(String.valueOf(sumUtils.getSumAtANC()));
                    fk_view.setText(String.valueOf(sumUtils.getSumAtFK()));
                    f301_view.setText(String.valueOf(sumUtils.getSumAt301()));
                    pitstop_view.setText(String.valueOf(sumUtils.getSumAtmess()));*/

                    sumUtils_mon.setTot(sumUtils_mon.getSumAtmess() + sumUtils_mon.getSumAt301() + sumUtils_mon.getSumAtANC() + sumUtils_mon.getSumAtLooters() + sumUtils_mon.getSumAtFK());

                    sumUtils_sem.setTot(sumUtils_sem.getSumAtmess() + sumUtils_sem.getSumAt301() + sumUtils_sem.getSumAtANC() + sumUtils_sem.getSumAtLooters() + sumUtils_sem.getSumAtFK());

                    sumUtils.setTot(sumUtils.getSumAtmess() + sumUtils.getSumAt301() + sumUtils.getSumAtANC() + sumUtils.getSumAtLooters() + sumUtils.getSumAtFK());


                    this_sem_story.setText("Rs " + String.valueOf(sumUtils_sem.getTot()));
                    this_mon_story.setText("Rs " + String.valueOf(sumUtils_mon.getTot()));
                    todays_story.setText("Rs " + String.valueOf(sumUtils.getTot()));

                    this_sem_anc.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtANC()));
                    this_mon_anc.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtANC()));
                    todays_anc.setText("Rs " + String.valueOf(sumUtils.getSumAtANC()));

                    this_sem_looters.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtLooters()));
                    this_mon_looters.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtLooters()));
                    todays_looters.setText("Rs " + String.valueOf(sumUtils.getSumAtLooters()));

                    this_sem_301f.setText("Rs " + String.valueOf(sumUtils_sem.getSumAt301()));
                    this_mon_301f.setText("Rs " + String.valueOf(sumUtils_mon.getSumAt301()));
                    todays_301f.setText("Rs " + String.valueOf(sumUtils.getSumAt301()));

                    this_sem_foodking.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtFK()));
                    this_mon_foodking.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtFK()));
                    todays_foodking.setText("Rs " + String.valueOf(sumUtils.getSumAtFK()));

                    this_sem_pitstop.setText("Rs " + String.valueOf(sumUtils_sem.getSumAtmess()));
                    this_mon_pitstop.setText("Rs " + String.valueOf(sumUtils_mon.getSumAtmess()));
                    todays_pitstop.setText("Rs " + String.valueOf(sumUtils.getSumAtmess()));

                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
    }

    public void retrieve()
    {

        sumUtils.setSumAt301(0f);
        sumUtils.setSumAtANC(0f);
        sumUtils.setSumAtFK(0f);
        sumUtils.setSumAtLooters(0f);
        sumUtils.setSumAtmess(0f);

        sumUtils_mon.setSumAt301(0f);
        sumUtils_mon.setSumAtANC(0f);
        sumUtils_mon.setSumAtFK(0f);
        sumUtils_mon.setSumAtLooters(0f);
        sumUtils_mon.setSumAtmess(0f);

        sumUtils_sem.setSumAt301(0f);
        sumUtils_sem.setSumAtANC(0f);
        sumUtils_sem.setSumAtFK(0f);
        sumUtils_sem.setSumAtLooters(0f);
        sumUtils_sem.setSumAtmess(0f);

        final Context context = getContext();

        Intent intent = new Intent(getActivity(), MainActivity0.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("From", "notifyFrag");
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0, intent,0);

        long[] pattern = {300,300,300,300};

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_512px_indian_rupee_symbolsvg_for_monthlycap)
                .setContentTitle("You just made a transaction!")
                .setContentText("Tap for details.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLights(Color.BLUE,500,500)
                .setVibrate(pattern)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Log.w("Retrieve","Inside Retrieve data");
        Query query = db.orderByChild("timestampCreated/date");
        Log.d("retrieve query - ",query.toString());
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.w("datasnapshot added",dataSnapshot.toString());
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
                /*sumUtils.setSumAt301(0f);
                sumUtils.setSumAtANC(0f);
                sumUtils.setSumAtFK(0f);
                sumUtils.setSumAtLooters(0f);
                sumUtils.setSumAtmess(0f);*/
                Log.d("datasnapshot changed",dataSnapshot.toString());
                /*Handler handler = new Handler();
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {

                    }
                });*/
                // Create an explicit intent for an Activity in your app
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, mBuilder.build());

                fetchData_change(dataSnapshot);

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
// notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, mBuilder.build());

                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();

                //refresh_app();

                //getFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
