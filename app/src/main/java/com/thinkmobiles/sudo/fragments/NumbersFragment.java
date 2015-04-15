package com.thinkmobiles.sudo.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.NumbersAdapter;
import com.thinkmobiles.sudo.models.Numbers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersFragment extends Fragment implements View.OnClickListener{

    private View mView;
    private Activity mActivity;
    private NumbersAdapter mAdapter;
    private List<Numbers> mList;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_number, container, false);
        mListView = (ListView) mView.findViewById(R.id.lvNumbers_FN);
        mList = new ArrayList<Numbers>();
        mList.add(new Numbers("Thailand", "12CRED"));
        mList.add(new Numbers("Turkey", "12CRED"));
        mList.add(new Numbers("Ukraine", "12CRED"));
        mAdapter = new NumbersAdapter(mActivity, mList);
        mListView.setAdapter(mAdapter);
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onClick(View v) {

    }
}
