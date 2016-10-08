package com.bignerdranch.android.fragment_observer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListFragment extends Fragment implements Data.Observer {

    ListView listView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Data.getInstance().attach(this); // 옵저버 붙여줌

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

        CustomAdapter adapter = new CustomAdapter(getActivity());
        listView.setAdapter(adapter);


        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void update() {
        listView.setSelection(Data.getInstance().getPosition());
    }
}

class CustomAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<MusicData> datas;

    public CustomAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        datas = Data.getInstance().getDatas();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = inflater.inflate(R.layout.fragment_list_item, null);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.getInstance().setPosition(position);
            }
        });


        TextView title = (TextView) convertView.findViewById(R.id.list_item_title);
        title.setText(datas.get(position).title);

        TextView artist = (TextView) convertView.findViewById(R.id.list_item_artist);
        artist.setText(datas.get(position).artist);

        return convertView;
    }
}