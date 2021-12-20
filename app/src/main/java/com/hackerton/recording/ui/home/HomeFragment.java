package com.hackerton.recording.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.hackerton.recording.Adapter;
import com.hackerton.recording.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ArrayList<History> historyList;
    private Adapter adapter;
    private SwipeRefreshLayout swipeView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final FloatingActionButton actionButton = view.findViewById(R.id.fab);
        ObjectAnimator dropBounce = ObjectAnimator.ofFloat(actionButton, "Y", 1500);
        dropBounce.setDuration(700);
        dropBounce.setInterpolator(new BounceInterpolator());

        dropBounce.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                NavController navController = Navigation.
                        findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_home_to_navigation_create);
            }
        });

        actionButton.setOnClickListener(thisview -> dropBounce.start());

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        swipeView = view.findViewById(R.id.swipeContainer);
        swipeView.setOnRefreshListener(this);
        swipeView.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 32;
                outRect.bottom = 32;
            }
        });

        historyList = new ArrayList<>();
        refreshHistory();
        adapter = new Adapter(historyList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        FirebaseFirestore.getInstance().collection("history").get().addOnCompleteListener(runnable -> {
            if (runnable.isSuccessful()) {
                historyList.clear();
                for (QueryDocumentSnapshot document : runnable.getResult()) {
                    Log.i("DATA", document.getData().get("timecode").toString());

                    final Timestamp timestamp = (Timestamp) document.getData().get("timecode");
                    SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
                    historyList.add(new History(format.format(timestamp.toDate()), "DATE"));
                }
                adapter.notifyDataSetChanged();
                swipeView.setRefreshing(false);
            }
        });
    }

    public void refreshHistory() {
        FirebaseFirestore.getInstance().collection("history").get().addOnCompleteListener(runnable -> {
            if (runnable.isSuccessful()) {
                for (QueryDocumentSnapshot document : runnable.getResult()) {
                    Log.i("DATA", document.getData().get("timecode").toString());

                    final Timestamp timestamp = (Timestamp) document.getData().get("timecode");
                    SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
                    historyList.add(new History(format.format(timestamp.toDate()), "DATE"));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}