package com.hackerton.recording.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

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
import java.util.Map;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    final private SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy");
    private ArrayList<History> historyList;
    private Adapter adapter;
    private SwipeRefreshLayout swipeView;

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
        refreshHistory(false);
        adapter = new Adapter(historyList, this);
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        refreshHistory(true);
    }

    private String tohistString(ArrayList<Map<String, Object>> records) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i != records.size(); i++) {
            Map<String, Object> record = records.get(i);

            String studentName = record.get("studentName").toString();
            String studentLevel = record.get("level").toString();
            String modelName = record.get("modelName").toString();
            String studentProgress = record.get("progress").toString();

            builder.append(i);
            builder.append(". ");
            builder.append(studentName);
            builder.append(", Level ");
            builder.append(studentLevel);
            builder.append(" ");
            builder.append(modelName);
            builder.append(" ");
            builder.append(studentProgress);
            builder.append(" ");

            builder.append('\n');
        }

        return builder.toString();
    }


    public void refreshHistory(boolean useswipeView) {
        historyList.clear();
        FirebaseFirestore.getInstance().collection("history").get().addOnCompleteListener(runnable -> {
            if (runnable.isSuccessful()) {
                for (QueryDocumentSnapshot document : runnable.getResult()) {
                    final Timestamp timestamp = (Timestamp) document.getData().get("timecode");
                    ArrayList<Map<String, Object>> records = (ArrayList<Map<String, Object>>) document.getData().get("record");
                    historyList.add(new History(document.getId(), format.format(timestamp.toDate()), tohistString(records)));
                }

                if (useswipeView) {
                    swipeView.setRefreshing(false);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Adapter.ViewHolder viewHolder = (Adapter.ViewHolder) view.getTag();
        History thisHist = historyList.get(viewHolder.getAdapterPosition());

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Your message");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseFirestore.getInstance().collection("history").document(thisHist.getId()).delete().addOnCompleteListener(runnable -> {
                    if (runnable.isSuccessful()) {
                        Toast.makeText(requireContext(), thisHist.getId(), Toast.LENGTH_SHORT).show();
                        dialogInterface.cancel();
                    }
                });
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}