package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String IMAGE = "image";
    private static final String NAME = "name";
    private static final String DETAIL="detail";

    // TODO: Rename and change types of parameters
    private Integer mImage;
    private String mName;
    private String mDetail;

    public NewFragment() {
        // Required empty public constructor
    }

    TextView name;
    TextView detail;
    ImageView image;


    public static NewFragment newInstance(Integer mImage, String mName,String mDetail) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putInt(IMAGE, mImage);
        args.putString(NAME, mName);
        args.putString(DETAIL,mDetail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImage = getArguments().getInt(IMAGE);
            mName = getArguments().getString(NAME);
            mDetail=getArguments().getString(DETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_new, container, false);
        image=view.findViewById(R.id.image);
        name=view.findViewById(R.id.name);
        detail=view.findViewById(R.id.detail);

        name.setText(mName);
        detail.setText(mDetail);
        image.setImageResource(mImage);
        return view;
    }
}