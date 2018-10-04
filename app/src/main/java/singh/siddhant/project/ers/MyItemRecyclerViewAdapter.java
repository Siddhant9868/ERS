package singh.siddhant.project.ers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import singh.siddhant.project.ers.dummy.DummyContent;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyContent.DummyItem} and makes a call to the
 * specified {@link PassbookActivity.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.UserViewHolder> {

    private List<Contact> mValues;
    private final PassbookActivity.OnListFragmentInteractionListener mListener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public MyItemRecyclerViewAdapter(List<Contact> contacts, PassbookActivity.OnListFragmentInteractionListener listener) {

        mValues = contacts;
        mListener = listener;
        Log.d("MyItemRecyclerView","I'm IN here");
        //RecyclerView recyclerView = (RecyclerView)
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view_row, parent, false);

        /*Contact temp;
        int j = 0;
        while (j < mValues.size()) {
            for (int i = 0; i < mValues.size() - j - 1; i++) {
                Log.d("qwertyuiopasdfgsdfg - ",mValues.get(i).getDOR());
                if (mValues.get(i).getDOR().compareTo(mValues.get(i + 1).getDOR()) > 0) {
                    temp = mValues.get(i);
                    mValues.set(i, mValues.get(i + 1));
                    mValues.set(i + 1, temp);
                }
            }
            j++;
        }*/

        Log.w("onCreateViewHolder","In here - " + parent);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, int position) {
        //if (holder instanceof UserViewHolder) {
        Log.d("onBind","hello om here - " + position + holder.getAdapterPosition());
        if (holder != null ){

            Contact contact = mValues.get((holder.getAdapterPosition()));
            UserViewHolder userViewHolder = holder;
            Log.d("ViewHolder", "ITEM_ID = " + contact.getITEM_ID());
            Log.d("ViewHolder", "BILL_ID = " + contact.getBILL_ID().toString());
            Log.d("ViewHolder", "QTY = " + contact.getQTY().toString());
            Log.d("ViewHolder", "Timestamp = " + contact.getDOR());
            char []loc = contact.getLocation_circle().toCharArray();
            Log.d("ViewHolder","Location = " + contact.getLocation_circle() + "   " + loc[0]);

            /*char []timestamp = contact.getDOR().toCharArray();
            timestamp[10] = ' ';
            String timestamp_trimed = "2018-00-00 00:00 ";
            for (int i = 0 ; i < timestamp.length - 7; i++){
                timestamp_trimed.toCharArray()[i] = timestamp[i];
            }*/

            String timestamp_trimed = null;
            String timestamp = contact.getFORMATED_DOR();
            SimpleDateFormat date = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",Locale.US);
            Date date1;

               //date1 = date.parse(timestamp);

                String itemName = contact.getITEM_ID();
                String rate = String.valueOf(contact.getRATE());
                userViewHolder.Rate.setText(rate);
                userViewHolder.ItemName.setText(itemName);
                String billid = contact.getBILL_ID().toString();
                userViewHolder.BillId.setText(billid);
                String qty = contact.getQTY().toString();
                userViewHolder.QTY.setText(qty);
                userViewHolder.Timestamp.setText(timestamp);
                userViewHolder.Loc_circle.setText(String.valueOf(loc[0]));


            Log.d("onBindViewHolder", "In here");
        }

         /*else if (holder instanceof LoadingViewHolder) {
            ContactAdapter.LoadingViewHolder loadingViewHolder = (ContactAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }*/

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(mValues.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 1 : mValues.size();
    }

    /*public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Contact contact;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.txt_BillId);
            mContentView = (TextView) view.findViewById(R.id.txt_timestamp);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }*/

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);

        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView ItemName;
        public TextView BillId;
        public TextView QTY;
        public TextView Timestamp;
        public TextView Rate;
        public TextView Loc_circle;

        public UserViewHolder(View view) {
            super(view);
            mView = view;
            ItemName = view.findViewById(R.id.item_name);
            Rate = view.findViewById(R.id.rate);
            BillId = view.findViewById(R.id.BillId);
            QTY = (TextView) view.findViewById(R.id.QTY);
            Timestamp = (TextView) view.findViewById(R.id.timestamp);
            Loc_circle = (TextView) view.findViewById(R.id.location_circle);
        }
    }
}
