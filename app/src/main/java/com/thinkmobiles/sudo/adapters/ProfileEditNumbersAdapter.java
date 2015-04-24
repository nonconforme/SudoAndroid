package com.thinkmobiles.sudo.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditNumbersAdapter extends BaseAdapter {

    private Context mContext;
    private List<NumberModel> mListNumbers;
    private LayoutInflater inflater;


    public ProfileEditNumbersAdapter(Context _context, List<NumberModel> _list) {
        mContext = _context;
        mListNumbers = _list;
        inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.phone_number_item_edit, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.etPhoneNumber = (EditText) view.findViewById(R.id.etPhoneNumber_AVC);
            viewHolder.ivDeleteNumber = (ImageView) view.findViewById(R.id.ivRemoveNumber_AVC);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.etPhoneNumber.removeTextChangedListener(viewHolder.textWatcher);
        }

        viewHolder.etPhoneNumber.setText(mListNumbers.get(position).getNumber());
        addBlankNumberView();
        viewHolder.ivDeleteNumber.setOnClickListener(new MyOnClickListener(position));
        viewHolder.textWatcher = new MyTextWatcher(position);
        viewHolder.etPhoneNumber.addTextChangedListener(viewHolder.textWatcher);

        return view;
    }


    private void addBlankNumberView() {
        if (mListNumbers.get(mListNumbers.size() - 1).getNumber() != null && !mListNumbers.get(mListNumbers.size() - 1).getNumber().equalsIgnoreCase("")) {
            NumberModel numberModel = new NumberModel();
            numberModel.setNumber("");
            mListNumbers.add(numberModel);
            notifyDataSetChanged();

        }
    }


    static class ViewHolder {
        public EditText etPhoneNumber;
        public ImageView ivDeleteNumber;
        public TextWatcher textWatcher;
    }

    public List<NumberModel> getNumbersList() {
        return mListNumbers;
    }

    private void showConfirmationDialog(AlertDialogCallback alertDialogCallback) {
        final AlertDialogCallback callback = alertDialogCallback;

        new AlertDialog.Builder(mContext)

                .setTitle(mContext.getString(R.string.alert_delete_pn_title))
                .setMessage(mContext.getString(R.string.alert_delete_pn_message))
                .setPositiveButton(mContext.getString(R.string.alert_delete_pn_ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                callback.confirmDeletePhoneNumber();

                            }

                        })
                .setNegativeButton(mContext.getString(R.string.alert_delete_pn_cancel),
                        null).show();
    }



    private interface AlertDialogCallback {
        public void confirmDeletePhoneNumber();
    }

    private class MyTextWatcher implements TextWatcher {
        private int pos;

        public MyTextWatcher(int pos) {
            this.pos = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mListNumbers.get(pos).setNumber(String.valueOf(charSequence));


        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int pos;

        public MyOnClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View view) {
            AlertDialogCallback callback = new AlertDialogCallback() {
                @Override
                public void confirmDeletePhoneNumber() {
                    mListNumbers.remove(pos);
                    if (mListNumbers.size() == 0) {
                        mListNumbers.add(new NumberModel());

                    }
                    notifyDataSetChanged();
                }
            };
            showConfirmationDialog(callback);

        }
    }


}