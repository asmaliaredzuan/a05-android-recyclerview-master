package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.RecyclerAdapter;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int DEFAULT_POS = 0;

    private RecyclerAdapter kAdapter;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();
    }

    private void initComponent() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        List<Inbox> inboxList = DataGenerator.getInboxData(this);
        inboxList.addAll(DataGenerator.getInboxData(this));

        // TODO 03. Do the setup of a new RecyclerView instance to display the item list properly
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        kAdapter = new RecyclerAdapter(this, inboxList);
        kAdapter.setOnItemClickListener(this);
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kAdapter);

        // TODO 09. Create a new instance of the created Adapter class and bind it to the RecyclerView instance created in step 03
        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 10. Invoke the method created to a new item to the top of the list so it's
                //  triggered when the user taps the Floating Action Button
                Inbox newItem = DataGenerator.getRandomInboxItem(getApplicationContext());
                kAdapter.addEmail(DEFAULT_POS, newItem);
                recyclerView.scrollToPosition(DEFAULT_POS);
            }
        });
    }

    @Override
    public void onItemClick(View view, Inbox obj, int position) {
        kAdapter.clearAllSelections();
        obj.toggleSelection();
        kAdapter.notifyItemChanged(position);
    }

    @Override
    public void onIconClick(View view, Inbox obj, int position) {
        if (obj.isSelected()) {
            kAdapter.deleteEmail(position);
        }
    }

}