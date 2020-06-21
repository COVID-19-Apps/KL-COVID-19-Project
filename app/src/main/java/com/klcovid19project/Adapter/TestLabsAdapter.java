package com.klcovid19project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.klcovid19project.Models.Test_Labs;
import com.klcovid19project.R;

import java.util.List;

public class TestLabsAdapter extends RecyclerView.Adapter<TestLabsAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Test_Labs> mTest_Labs;

    public TestLabsAdapter(Context context, List<Test_Labs> list) {
        mContext = context;
        mTest_Labs = list;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_lab_test, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Test_Labs test_labs = mTest_Labs.get(position);

        holder.Lab_Name.setText(test_labs.getName());
        holder.Type.setText(test_labs.getType());
    }

    @Override
    public int getItemCount() {
        return mTest_Labs.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Lab_Name, Location, Type;
        public ImageViewHolder(View itemView) {
            super(itemView);

            Lab_Name = itemView.findViewById(R.id.lab_name);
            Location = itemView.findViewById(R.id.location);
            Type = itemView.findViewById(R.id.type);
        }
    }

}