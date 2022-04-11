package com.ualr.recyclerviewassignment.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 04. Define the layout of each item in the list
// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list


public class RecyclerAdapter extends RecyclerView.Adapter {
    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private List<Inbox> kItems;
    private Context kContext;
    private OnItemClickListener kOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox obj, int position);

        void onIconClick(View view, Inbox obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener kInboxClickListener) {
        this.kOnItemClickListener = kInboxClickListener;
    }

    public RecyclerAdapter(Context context, List<Inbox> items) {
        this.kItems = items;
        this.kContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View inboxView = LayoutInflater.from(parent.getContext()).inflate(R.layout.email_item, parent, false);
        vh = new InboxViewHolder(inboxView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        InboxViewHolder viewHolder = (InboxViewHolder) holder;
        final Inbox inbox = kItems.get(position);

        // Color resources
        int selectedColor = kContext.getResources().getColor(R.color.grey_20);
        int selectedIconColor = kContext.getResources().getColor(R.color.colorAccent);
        int defaultIconColor = kContext.getResources().getColor(R.color.colorPrimary);

        // Drawable resources and initializations
        Drawable defaultCircle = kContext.getDrawable(R.drawable.shape_circle);
        Drawable selectedIndicator = kContext.getDrawable(R.drawable.ic_delete_24px);
        Drawable selectedCircle = kContext.getDrawable(R.drawable.shape_circle);
        defaultCircle.mutate().setColorFilter(defaultIconColor, PorterDuff.Mode.SRC_IN);
        selectedCircle.mutate().setColorFilter(selectedIconColor, PorterDuff.Mode.SRC_IN);
        selectedCircle.setBounds(0, 0, 24, 24);

        // Set standard content
        viewHolder.Icon.setText(inbox.getFrom().substring(0, 1));
        viewHolder.Name.setText(inbox.getFrom());
        viewHolder.Email.setText(inbox.getEmail());
        viewHolder.Preview.setText(R.string.lorem_ipsum);
        viewHolder.Timestamp.setText(inbox.getDate());

        if (inbox.isSelected()) {
            viewHolder.layoutParent.setBackgroundColor(selectedColor);
            viewHolder.Icon.setBackground(selectedCircle);
            viewHolder.Icon.setCompoundDrawablesWithIntrinsicBounds(selectedIndicator, null,null,null);

        }
        else {
            viewHolder.layoutParent.setBackgroundColor(Color.TRANSPARENT);
            viewHolder.Icon.setBackground(defaultCircle);
            viewHolder.Icon.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }

    }

    @Override
    public int getItemCount() {
        return this.kItems.size();
    }

    public void addEmail(int position, Inbox email) {
        kItems.add(position, email);
        notifyItemInserted(position);
    }

    public void deleteEmail(int position) {
        kItems.remove(position);
        notifyItemRemoved(position);
    }

    public void clearAllSelections() {
        for (Inbox mItem : kItems) {
            mItem.setSelected(false);
        }
        notifyDataSetChanged();
    }


    private class InboxViewHolder extends RecyclerView.ViewHolder {
        public TextView Icon;
        public TextView Name;
        public TextView Email;
        public TextView Preview;
        public TextView Timestamp;
        public View layoutParent;

        public InboxViewHolder(View v) {
            super(v);
            Icon = v.findViewById(R.id.icon);
            Name = v.findViewById(R.id.name);
            Email = v.findViewById(R.id.email);
            Preview = v.findViewById(R.id.email_preview);
            Timestamp = v.findViewById(R.id.timestamp);
            layoutParent = v.findViewById(R.id.layout_parent);

            Icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kOnItemClickListener.onIconClick(view, kItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });
            layoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kOnItemClickListener.onItemClick(view, kItems.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }
}