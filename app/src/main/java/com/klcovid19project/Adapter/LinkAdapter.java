package com.klcovid19project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klcovid19project.Models.Link;
import com.klcovid19project.R;
import com.klcovid19project.Services.WebViewActivity;

import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Link> mLink;

    public LinkAdapter(Context context, List<Link> list) {
        mContext = context;
        mLink = list;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_link, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final Link link = mLink.get(position);

        holder.Link.setText(link.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!link.getLink().isEmpty()) {
                    Intent intent = new Intent(mContext, WebViewActivity.class);
                    intent.putExtra("link", link.getLink());
                    mContext.startActivity(intent);
                }else{
                    Toast.makeText(mContext, "Loading..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLink.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Link;
        public ImageViewHolder(View itemView) {
            super(itemView);

            Link = itemView.findViewById(R.id.link);

        }
    }

}