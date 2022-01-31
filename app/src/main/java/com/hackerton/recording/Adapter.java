package com.hackerton.recording;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hackerton.recording.entity.History;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// universal adapter
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {
    final ArrayList<History> list;
    final View.OnClickListener listener;
    final DateFormat mDateFormat = SimpleDateFormat.getDateTimeInstance();

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
        Date cTime = list.get(position).getmTimestamp();

        holder.getMtextDate().setText(mDateFormat.format(cTime));
//        holder.getMtextHistory().setText(list.get(position).getHistory());
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
