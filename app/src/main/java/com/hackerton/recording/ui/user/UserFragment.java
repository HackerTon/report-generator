package com.hackerton.recording.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hackerton.recording.R;


public class DashboardFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        final TextView textView = view.findViewById(R.id.text_dashboard);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}