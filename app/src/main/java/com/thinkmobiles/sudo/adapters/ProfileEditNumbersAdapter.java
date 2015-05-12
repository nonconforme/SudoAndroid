package com.thinkmobiles.sudo.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditNumbersAdapter extends BaseAdapter implements View.OnFocusChangeListener, View.OnClickListener {

    private Context mContext;
    private List<NumberModel> mListNumbers;
    private LayoutInflater inflater;
    private boolean errorsInNumbers [];


    public ProfileEditNumbersAdapter(Context _context) {
        mContext = _context;
        mListNumbers = new ArrayList<>();
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mListNumbers.size();
    }

    @Override
    public NumberModel getItem(int position) {
        return mListNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_phone_number_edit, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber_AVC);
            viewHolder.ivDeleteNumber = (ImageView) view.findViewById(R.id.ivRemoveNumber_AVC);
            viewHolder.etPhoneNumber.setOnFocusChangeListener(this);
            viewHolder.ivDeleteNumber.setTag(position);
            viewHolder.ivDeleteNumber.setOnClickListener(this);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.etPhoneNumber.setText(mListNumbers.get(position).getNumber());
        viewHolder.ivDeleteNumber.setTag(position);
        viewHolder.etPhoneNumber.setTag(position);
        if(errorsInNumbers != null){
            if (errorsInNumbers[position])
                viewHolder.etPhoneNumber.setBackgroundResource(android.R.color.holo_red_dark);
        }else
            viewHolder.etPhoneNumber.setBackgroundResource(android.R.color.transparent);


        return view;
    }

    public void reloadList(List<NumberModel> mListNumbers) {
        this.mListNumbers = mListNumbers;
        errorsInNumbers = null;
        notifyDataSetChanged();
    }

    public void showErrorsInNumbers(boolean [] errorsInNumbers){
        this.errorsInNumbers = errorsInNumbers;
        notifyDataSetChanged();
    }

    public void addBlankNumberView() {
        if (mListNumbers.size() < 1 || (mListNumbers.get(mListNumbers.size() - 1).getNumber() != null && !mListNumbers.get(mListNumbers.size() - 1).getNumber().equalsIgnoreCase(""))) {
            NumberModel numberModel = new NumberModel();
            numberModel.setNumber("");
            mListNumbers.add(numberModel);
            notifyDataSetChanged();

        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            int pos = (int) view.getTag();
            mListNumbers.get(pos).setNumber(((EditText) view).getText().toString());
            view.setBackgroundResource(android.R.color.transparent);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.ivRemoveNumber_AVC) {
           final int pos = (int) view.getTag();
            AlertDialogCallback callback = new AlertDialogCallback() {
                @Override
                public void confirmDeletePhoneNumber() {
/*
                    Toast.makeText(mContext, String.valueOf(pos),Toast.LENGTH_SHORT).show();
*/
                    mListNumbers.remove(pos);
                    if (mListNumbers.size() == 0) {
                        mListNumbers.add(mListNumbers.size(), new NumberModel());

                    }
                    notifyDataSetChanged();
                }
            };
            showConfirmationDialog(callback);
        }
    }
    static class ViewHolder {
        public EditText etPhoneNumber;
        public ImageView ivDeleteNumber;

    }

    public List<NumberModel> getNumbersList() {
        if (mListNumbers != null && mListNumbers.size() > 0) return mListNumbers;
        else return mListNumbers = new ArrayList<>();
    }

    private void showConfirmationDialog(AlertDialogCallback alertDialogCallback) {
        final AlertDialogCallback callback = alertDialogCallback;

        new AlertDialog.Builder(mContext)

                .setTitle(mContext.getString(R.string.alert_delete_pn_title)).setMessage(mContext.getString(R.string.alert_delete_pn_message)).setPositiveButton(mContext.getString(R.string.alert_delete_pn_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callback.confirmDeletePhoneNumber();

            }

        }).setNegativeButton(mContext.getString(R.string.alert_delete_pn_cancel), null).show();
    }


    private interface AlertDialogCallback {
        void confirmDeletePhoneNumber();
    }







}