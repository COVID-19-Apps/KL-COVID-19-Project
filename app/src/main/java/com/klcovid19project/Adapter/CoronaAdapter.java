package com.klcovid19project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klcovid19project.Models.Corona;
import com.klcovid19project.Models.FAQ;
import com.klcovid19project.R;

import java.util.ArrayList;
import java.util.List;

public class CoronaAdapter extends RecyclerView.Adapter<CoronaAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Corona> mCorona;

    public CoronaAdapter(Context context, List<Corona> list) {
        mContext = context;
        mCorona = list;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_corona, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Corona corona = mCorona.get(position);

        holder.District.setText(corona.getDistrict());
        holder.Confirmed.setText(corona.getConfirmed());
    }

    @Override
    public int getItemCount() {
        return mCorona.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView District, Confirmed;
        public ImageViewHolder(View itemView) {
            super(itemView);

            District = itemView.findViewById(R.id.district);
            Confirmed = itemView.findViewById(R.id.confirmed);
        }
    }
}