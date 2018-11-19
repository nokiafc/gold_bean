package com.tianmi.goldbean.message;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianmi.goldbean.R;

public class MessageFragment extends Fragment {
    private TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        init(view);
        return view;
    }
    private void init(View view){
        title = (TextView)view.findViewById(R.id.text_title);
        title.setText("消息");
    }
}
