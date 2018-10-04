package singh.siddhant.project.ers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Siddhant Singh on 1/11/2018.
 */

public class PassbookActivity extends Fragment {

    // TODO: Customize parameter argument names
    public SumsTabFragment sumsTabFragment = new SumsTabFragment();
    private static final String ARG_COLUMN_COUNT = "column-count";
    private List<Contact> contacts = new ArrayList<>();
    private ContactAdapter contactAdapter;
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    public String S_id ;
    public DatabaseReference db;
    public int flag = 1;
    public int flag_fetch = 1;
    DrawerLayout myDrawerLayout;
    NavigationView myNavigationView;
    private OnListFragmentInteractionListener mListener;
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    SumUtils sumUtils = new SumUtils();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PassbookActivity() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PassbookActivity newInstance(int columnCount) {
        PassbookActivity fragment = new PassbookActivity();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*Contact cont = new Contact();
        cont.setDOR("Hang in there, we are still fetching");
        Long num = 1234L;
        cont.setITEM_ID(num);
        contacts.add(cont);*/

        Log.d("AfterRetrieve", "I'm here");
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.passbook, container, false);
        //if (view instanceof RecyclerView) {
            //set load more listener for the RecyclerView adapter
        contacts.clear();

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
        retrieve();

        Context context = view.getContext();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setVerticalScrollBarEnabled(true);

        //find view by id and attaching adapter for the RecyclerView

        recyclerView.post(new Runnable() {
            public void run() {
                if (contacts.size() > 0) {

                    myItemRecyclerViewAdapter.notifyItemInserted(contacts.size() - 1);
                }
            }
        });

        LinearLayoutManager recyclerLayoutManager =
                new LinearLayoutManager(context);
        recyclerLayoutManager.setReverseLayout(true);
        recyclerLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(recyclerLayoutManager);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(contacts, mListener);
        recyclerView.setAdapter(myItemRecyclerViewAdapter);

        return view;// super.onCreateView(inflater, container, savedInstanceState);
    }

    float sumAtLooters = 0;
    float sumAtANC = 0;
    float sumAt301 = 0;
    float sumAtFK = 0;
    float sumAtmess = 0;
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    public void fetchData(DataSnapshot dataSnapshot)
    {
        Log.w("Fetchdata","Inside Fetch data");
        if(flag == 1) {
            contacts.clear();
            flag = 0;
        }

        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    String str = String.valueOf(sumUtils.getSumAtmess());
                    contact.setLocation_circle(timestamp.getKey());
                    contact.setFORMATED_DOR(dataSnapshot.getKey());

                    contacts.add(contact);
                    myItemRecyclerViewAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(contacts.size()-1);
                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
    }

    public void fetchData_remove(DataSnapshot dataSnapshot)
    {
        Log.w("Fetchdata","Inside Fetch data");
        if(flag == 1) {
            contacts.clear();
            flag = 0;
        }
        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    String str = String.valueOf(sumUtils.getSumAtmess());
                    contact.setLocation_circle(timestamp.getKey());
                    contact.setFORMATED_DOR(dataSnapshot.getKey());
                    Log.d("removing... ", String.valueOf((contacts.size())));
                    int i = 0;
                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()){
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())){
                            iterator.remove();
                            Log.d("removing...after...",String.valueOf((contacts.size())));
                            myItemRecyclerViewAdapter.notifyItemRemoved(i);
                    }
                    i++;
                    }
                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
    }

    public void fetchData_change(DataSnapshot dataSnapshot)
    {
        Log.w("Fetchdata","Inside Fetch data");
        if(flag == 1) {
            contacts.clear();
            flag = 0;
        }
        boolean flag_changed = true;
        for(DataSnapshot timestamp : dataSnapshot.getChildren()) {
            for (DataSnapshot ds_location : timestamp.getChildren()) {
                for (DataSnapshot ds : ds_location.getChildren()) {
                    DataSnapshot ds1 = ds.child("data");
                    Log.w("FetchData - ", ds.getKey() + " ,Value - " + ds.getValue() + " ,lOCATION - " + dataSnapshot.getValue());
                    Log.w("Fetchdata", "Contact1 = " + ds1.getValue().toString());
                    Contact contact = ds1.getValue(Contact.class);
                    Log.w("Fetchdata", "Contact = " + contact.toString());

                    String str = String.valueOf(sumUtils.getSumAtmess());
                    contact.setLocation_circle(timestamp.getKey());
                    contact.setDOR(dataSnapshot.getKey());
                    Log.d("removing... ", String.valueOf((contacts.size())));
                    int i = 0;
                    Iterator<Contact> iterator = contacts.iterator();
                    while (iterator.hasNext()){
                        Contact con = iterator.next();
                        if (con.getDOR().equals(contact.getDOR())) {
                            if (con.getITEM_ID().compareTo(contact.getITEM_ID()) != 0) {
                                iterator.remove();
                                Log.d("removing...after...", String.valueOf((contacts.size())));
                                myItemRecyclerViewAdapter.notifyItemRemoved(i);
                                flag_changed = false;
                            }
                        }
                        i++;
                    }
                    if(flag_changed) {
                        contacts.add(contact);
                        myItemRecyclerViewAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(contacts.size() - 1);
                    }
                    //sum += contact.getRATE()*contact.getQTY() + contact.getTAX();
                }
            }
        }
    }

    //RETRIEVE
    public List<Contact> retrieve()
    {
        Log.w("Retrieve","Inside Retrieve data");
        Query query = db.orderByChild("timestampCreated/date");
        Log.d("retrieve query - ",query.toString());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
                /*if(flag_fetch == 1) {
                    fetchData(dataSnapshot);
                    flag_fetch = 0;
                }
                else{
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            fetchData(dataSnapshot);
                        }
                    }, 1000);
                }*/
                Log.w("Retrieve onchiladded","Inside Retrieve data onChildAdded  " + dataSnapshot.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.w("Retrieve onchiladded","Inside Retrieve data onChildChanged before  " + dataSnapshot.toString());
                //contacts.clear();
                fetchData_remove(dataSnapshot);
                fetchData(dataSnapshot);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new HomeFragment()).commit();
                Log.w("Retrieve onchiladded","Inside Retrieve data onChildChanged");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.w("Retrieve onchilremove b",dataSnapshot.toString());
                fetchData_remove(dataSnapshot);
                Log.w("Retrieve onchilremove",dataSnapshot.toString());
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return contacts;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PassbookActivity.OnListFragmentInteractionListener) {
            mListener = (PassbookActivity.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Contact item);
    }

}













