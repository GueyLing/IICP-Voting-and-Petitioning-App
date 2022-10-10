package com.example.adminiicp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewStatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewStatisticsFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<EventTitle> list;
    MyAdapter.RecyclerViewClickListener listener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewStatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewStatistics.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewStatisticsFragment newInstance(String param1, String param2) {
        ViewStatisticsFragment fragment = new ViewStatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_statistics, container, false);
        recyclerView = rootView.findViewById(R.id.eventList);
        database = FirebaseDatabase.getInstance().getReference("events");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        setOnClickListener();
        list = new ArrayList<>();
        //this should put line90
        myAdapter = new MyAdapter(getActivity(),list, listener);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    EventTitle event = dataSnapshot.getValue(EventTitle.class);
                    list.add(event);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }

    private void setOnClickListener() {

        listener = new MyAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("petition_event").child(list.get(position).getId());
                if(list.get(position).getType().equals("petition")){
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String petitionTitle= snapshot.child("petitionTitle").getValue().toString();
                            String description= snapshot.child("description").getValue().toString();
                            String petition_no= snapshot.child("petition_no").getValue().toString();
                            methodToProcess(petitionTitle, description, petition_no);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
//                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
//                    intent.putExtra("title", list.get(position).getId());
//
//                    intent.putExtra("title", group);
//                    startActivity(intent);
                }

            }
        };
    }

    private void methodToProcess(String petitionTitle, String description, String petition_no) {
        Intent intent = new Intent(getActivity(), ResultPetitionActivity.class);
//                    intent.putExtra("title", list.get(position).getId());

        intent.putExtra("title", petitionTitle);
        intent.putExtra("description", description);
        intent.putExtra("count", petition_no);

        startActivity(intent);
    }
}