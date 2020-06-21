package com.klcovid19project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klcovid19project.Models.FAQ;
import com.klcovid19project.Models.Test_Labs;
import com.klcovid19project.R;

import java.util.List;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.ImageViewHolder> {

    private Context mContext;
    private List<FAQ> mFAQ;

    public FAQAdapter(Context context, List<FAQ> list) {
        mContext = context;
        mFAQ = list;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_faq, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        final FAQ faq = mFAQ.get(position);

        holder.Question.setText(faq.getQuestion());
        holder.Answer.setText(faq.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mFAQ.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Question, Answer;
        public ImageViewHolder(View itemView) {
            super(itemView);

            Question = itemView.findViewById(R.id.question);
            Answer = itemView.findViewById(R.id.answer);
        }
    }

}