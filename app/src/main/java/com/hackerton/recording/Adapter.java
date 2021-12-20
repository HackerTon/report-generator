package com.hackerton.recording;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerton.recording.ui.home.History;

import java.util.ArrayList;

// universal adapter
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    final ArrayList<History> list;

    public Adapter(ArrayList<History> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getMtextDate().setText(list.get(position).getDate());
        holder.getMtextHistory().setText(list.get(position).getHistory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView getMtextDate() {
            return mtextDate;
        }

        public TextView getMtextHistory() {
            return mtextHistory;
        }

        final TextView mtextDate;
        final TextView mtextHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mtextDate = itemView.findViewById(R.id.textDate);
            mtextHistory = itemView.findViewById(R.id.textHistory);
        }
    }
}
