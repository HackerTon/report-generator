package com.hackerton.recording;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerton.recording.ui.home.History;

import java.util.ArrayList;

// universal adapter
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    final ArrayList<History> list;
    final View.OnClickListener listener;

    public Adapter(ArrayList<History> list, View.OnClickListener listener) {
        this.list = list;
        this.listener = listener;
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
        final TextView mtextDate;
        final TextView mtextHistory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mtextDate = itemView.findViewById(R.id.textDate);
            mtextHistory = itemView.findViewById(R.id.textHistory);
            itemView.setTag(this);
            itemView.setOnClickListener(listener);
        }

        public TextView getMtextDate() {
            return mtextDate;
        }

        public TextView getMtextHistory() {
            return mtextHistory;
        }
    }
}
