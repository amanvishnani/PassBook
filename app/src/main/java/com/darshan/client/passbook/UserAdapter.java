package com.darshan.client.passbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DARSHAN on 16-09-2016.
 */
public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList <User> userArrayList;
    public UserAdapter(Context ctx, ArrayList<User> arrayList) {
        context=ctx;
        userArrayList=arrayList;
    }

    @Override
    public int getCount() {
       return userArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        User user=userArrayList.get(i);
        viewGroup= (ViewGroup) LayoutInflater.from(context).inflate(R.layout.activity_data,null);
        TextView tvService= (TextView) viewGroup.findViewById(R.id.tvService);
        TextView tvEmail= (TextView) viewGroup.findViewById(R.id.tvUsername);
        TextView tvPassword= (TextView) viewGroup.findViewById(R.id.tvPassword);

        tvService.setText(user.getServiceProvider());
        tvEmail.setText(user.getEmail());
        tvPassword.setText(user.getPassword());


        return viewGroup;
    }
}
