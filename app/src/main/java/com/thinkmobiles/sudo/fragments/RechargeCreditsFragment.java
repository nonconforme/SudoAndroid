package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.MainToolbarManager;
import com.thinkmobiles.sudo.adapters.RechargeCreditsAdapter;
import com.thinkmobiles.sudo.models.Credits;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 16.04.2015.
 */
public class RechargeCreditsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View mView;
    private Activity mActivity;
    private ListView mListView;
    private RechargeCreditsAdapter mAdapter;
    private List<Credits> mListCredit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_recharge_credits, container, false);
        initComponent();
        setListener();
        MainToolbarManager.getMainInstance(mActivity).changeToolbarTitleAndImage(R.string.credits, 0);
        return mView;
    }

    private void initComponent(){
        mListView = (ListView) mView.findViewById(R.id.lvCredits_FRC);

        mListCredit = new ArrayList<Credits>();
        mListCredit.add(new Credits("10credits = 10$", "BUY"));
        mListCredit.add(new Credits("20credits = 20$", "BUY"));
        mListCredit.add(new Credits("30credits = 30$", "BUY"));
        mListCredit.add(new Credits("40credits = 40$", "BUY"));

        mAdapter = new RechargeCreditsAdapter(mActivity, mListCredit);
        mListView.setAdapter(mAdapter);
    }

    private void setListener(){
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity, "Click: pos: " + position + " "
                + mAdapter.getItem(position).getCredits(), Toast.LENGTH_SHORT).show();
    }
}
