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
import com.hackerton.recording.Adapter;
import com.hackerton.recording.Callback;
import com.hackerton.recording.R;
import com.hackerton.recording.entity.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, Callback {
    final private SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
    private ArrayList<History> historyList;
    private Adapter adapter;
    private SwipeRefreshLayout swipeView;
    private HomePresenter mPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final FloatingActionButton actionButton = view.findViewById(R.id.fab);
        ObjectAnimator dropBounce = ObjectAnimator.ofFloat(actionButton, "Y", 1000);
        dropBounce.setDuration(700);
        dropBounce.setInterpolator(new BounceInterpolator());

        dropBounce.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                NavController navController = Navigation.
                        findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

                mPresenter.testWriteDummyHistory();
                navController.navigate(R.id.action_navigation_home_to_navigation_create);
            }
        });

        actionButton.setOnClickListener(thisview -> {
            dropBounce.start();
        });

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
        adapter = new Adapter(historyList, this);
        recyclerView.setAdapter(adapter);

        mPresenter = new HomePresenter(this);
        mPresenter.fetchHistory();
        return view;
    }

    @Override
    public void receive(Object response) {
        List<History> histories = (List<History>) response;
        historyList.clear();
        historyList.addAll(histories);
        adapter.notifyDataSetChanged();
        swipeView.setRefreshing(false);
    }

    @Override
    public void error(Object response) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        mPresenter.fetchHistory();
    }

    @Override
    public void onClick(View view) {
//        Adapter.ViewHolder viewHolder = (Adapter.ViewHolder) view.getTag();
//        History thisHist = historyList.get(viewHolder.getAdapterPosition());
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setMessage("Your message");
//        builder.setCancelable(true);
//        builder.setPositiveButton("YES", (dialogInterface, i) ->
//                FirebaseFirestore.getInstance().collection("history").document(thisHist.getId()).delete().addOnCompleteListener(runnable -> {
//            if (runnable.isSuccessful()) {
//                Toast.makeText(requireContext(), thisHist.getId(), Toast.LENGTH_SHORT).show();
//                dialogInterface.cancel();
//            }
//        }));
//        builder.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.cancel());
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }


}