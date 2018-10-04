package singh.siddhant.project.ers;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Activity activity;
    private List<Contact> contacts;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public ContactAdapter(RecyclerView recyclerView, List<Contact> contacts, Activity activity) {
        this.contacts = contacts;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_row, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            Contact contact = contacts.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            Log.d("ViewHolder","ITEM_ID = " + contact.getITEM_ID().toString());
            Log.d("ViewHolder","BILL_ID = " + contact.getBILL_ID().toString());
            userViewHolder.ItemName.setText(contact.getITEM_ID().toString());
            userViewHolder.BillId.setText(contact.getBILL_ID().toString());
            userViewHolder.QTY.setText(contact.getQTY().toString());
            userViewHolder.Timestamp.setText(contact.getDOR());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView ItemName;
        public TextView BillId;
        public TextView QTY;
        public TextView Timestamp;

        public UserViewHolder(View view) {
            super(view);
            ItemName = (TextView) view.findViewById(R.id.item_name);
            BillId = (TextView) view.findViewById(R.id.BillId);
            QTY = (TextView) view.findViewById(R.id.QTY);
            Timestamp = (TextView) view.findViewById(R.id.timestamp);
        }
    }
}
