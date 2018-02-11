package com.ramki.myexercise.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.ramki.myexercise.R;
import com.ramki.myexercise.data.model.Fact;
import com.ramki.myexercise.data.model.Information;
import com.ramki.myexercise.data.model.Response;
import com.ramki.myexercise.view.adapter.InformationAdapter;
import com.ramki.myexercise.viewmodel.MainActivityViewModule;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private MainActivityViewModule viewModule;

    private RecyclerView factView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private InformationAdapter mAdapter;
    private ArrayList<Information> mInformations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        viewModule = ViewModelProviders.of(this).get(MainActivityViewModule.class);
        initView();
        loadFact();
        setupEvents();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
    }

    private void initView() {
        factView = (RecyclerView) findViewById(R.id.rv_fact);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);

        //RecyclerView setup as ListView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        factView.setLayoutManager(linearLayoutManager);

        //Add row divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(factView.getContext(),
                linearLayoutManager.getOrientation());
        factView.addItemDecoration(dividerItemDecoration);

        mAdapter = new InformationAdapter(this, mInformations);
        factView.setAdapter(mAdapter);
    }

    /**
     * Load the fact information from ViewModule
     */
    private void loadFact() {
        swipeRefreshLayout.setRefreshing(true);
        viewModule.loadFact().observeForever(new Observer<Response>() {
            @Override
            public void onChanged(@Nullable Response response) {
                swipeRefreshLayout.setRefreshing(false);
                updateList(response);
            }
        });

    }

    /**
     * This method used to handle the user intraction events
     */
    private void setupEvents() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModule.refreshFact().observeForever(new Observer<Response>() {
                    @Override
                    public void onChanged(@Nullable Response response) {
                        swipeRefreshLayout.setRefreshing(false);
                        updateList(response);
                    }
                });
            }
        });
    }

    /**
     * This method will update the List from the response of Load and Refresh the information
     */
    private void updateList(Response response) {
        mInformations.clear();
        if (response.status == Response.SUCCESS) {
            Fact fact = (Fact) response.data;
            if (fact != null) {
                actionBar.setTitle(fact.getTitle());
                if (fact.getInformations() != null)
                    mInformations.addAll(removeEmptyInformation(fact.getInformations()));
            }
        } else {
            Toast.makeText(getApplicationContext(), response.error.getMessage(), Toast.LENGTH_SHORT).show();
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * This method helps to remove empty information from the list of information.
     *
     * @param informations
     * @return ArrayList<Information>
     */
    private ArrayList<Information> removeEmptyInformation(ArrayList<Information> informations) {
        ArrayList<Information> list = new ArrayList<>();
        for (Information information : informations) {
            if (!TextUtils.isEmpty(information.getTitle()) || !TextUtils.isEmpty(information.getDescription()) || !TextUtils.isEmpty(information.getImageUrl())) {
                list.add(list.size(), information);
            }
        }
        return list;
    }

}
