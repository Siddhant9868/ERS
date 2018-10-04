package singh.siddhant.project.ers;/*package com.example.project.tabbednavigation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.tabbednavigation.dummy.DummyContent;
import com.example.project.tabbednavigation.dummy.DummyContent.DummyItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
/*public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Contact> contacts;

    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;
    public String S_id = "F20160144P";
    public DatabaseReference db;
    public int flag = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    /*public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;

        contacts = new ArrayList<>();

        db = FirebaseDatabase.getInstance().getReference().child("mess").child(S_id);

        //set dummy data
        Contact contact = new Contact();
        retrieve();
        Log.d("AfterRetrieve","I'm here");
        //find view by id and attaching adapter for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(contacts, mListener);
        recyclerView.setAdapter(myItemRecyclerViewAdapter);

        //set load more listener for the RecyclerView adapter
        recyclerView.post(new Runnable() {
            public void run() {
                myItemRecyclerViewAdapter.notifyItemInserted(contacts.size() - 1);
            }
        });



        // Set the adapter
        /*if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(Contact, mListener));
        }*/
        
        
        /*return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction();
    }

    
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    public void fetchData(DataSnapshot dataSnapshot)
    {

        if(flag == 1) {
            contacts.clear();
            flag = 0;
        }
        Log.w("Fetchdata","Inside Fetch data");
        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            DataSnapshot ds1 = ds.child("data");
            Log.w("Fetchdata","Contact1 = " + ds.getValue().toString());
            Contact contact = ds.getValue(Contact.class);
            Log.w("Fetchdata","Contact = " + contact.toString());
            contacts.add(contact);
           
            myItemRecyclerViewAdapter.notifyDataSetChanged();

        }
    }
    //RETRIEVE
    public List<Contact> retrieve()
    {
        Log.w("Retrieve","Inside Retrieve data");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.w("Retrieve onchiladded","Inside Retrieve data");
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
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
}*/
