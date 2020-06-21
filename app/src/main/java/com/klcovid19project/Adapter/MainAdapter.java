package com.klcovid19project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.klcovid19project.CounsellingActivity;
import com.klcovid19project.EpassActivity;
import com.klcovid19project.HealthCareListActivity;
import com.klcovid19project.MedicalStoresActivity;
import com.klcovid19project.OrphanageSupport.FreeFoodActivity;
import com.klcovid19project.TollNumbersActivity;
import com.klcovid19project.VideosActivity;
import com.bumptech.glide.Glide;

import com.klcovid19project.CoronaActivity;
import com.klcovid19project.FAQsActivity;
import com.klcovid19project.HomeTreamentActivity;
import com.klcovid19project.HospitalAdmissionActivity;
import com.klcovid19project.Models.Jsons;
import com.klcovid19project.MyHealthStatusActivity;
import com.klcovid19project.OnlineDoctorsActivity;
import com.klcovid19project.OnlineEducationActivity;
import com.klcovid19project.OrphanageSupportActivity;
import com.klcovid19project.R;
import com.klcovid19project.TestLabsActivity;
import com.klcovid19project.TweetsActivity;
import com.klcovid19project.VolunteersActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ImageViewHolder> {

    private Context mContext;
    private int[] Image;
    private String[] Title;
    private DatabaseReference mJsonDatabase;

    public MainAdapter(Context context, String[] titles, int[] images) {
        mContext = context;
        Image = images;
        Title = titles;
        mJsonDatabase = FirebaseDatabase.getInstance().getReference().child("Jsons");
        mJsonDatabase.keepSynced(true);
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_grid_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        holder.Title.setText(Title[position]);
        Glide.with(mContext)
                .load(Image[position])
                .into(holder.Image);
        mJsonDatabase = FirebaseDatabase.getInstance().getReference().child("Jsons");
        mJsonDatabase.keepSynced(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJsonDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final Jsons jsons = dataSnapshot.getValue(Jsons.class);
                        switch (holder.getAdapterPosition()) {
                            case 0:
                                Intent coronaintent = new Intent(mContext, CoronaActivity.class);
                                coronaintent.putExtra("url", jsons.getCorona());
                                mContext.startActivity(coronaintent);
                                break;
                            case 1:
                                Intent hometreatmentintent = new Intent(mContext, HomeTreamentActivity.class);
                                hometreatmentintent.putExtra("image_url", jsons.getHome_treatment_images());
                                hometreatmentintent.putExtra("link_url", jsons.getHome_treatment_links());
                                mContext.startActivity(hometreatmentintent);
                                break;

                            case 2:
                                Intent tollnumbersintent = new Intent(mContext, TollNumbersActivity.class);
                                tollnumbersintent.putExtra("url", jsons.getToll_numbers());
                                mContext.startActivity(tollnumbersintent);
                                break;

                            case 3:
                                Intent myhealthstatusintent = new Intent(mContext, MyHealthStatusActivity.class);
                                mContext.startActivity(myhealthstatusintent);
                                break;

                            case 4:
                                Intent healthcareintent = new Intent(mContext, HealthCareListActivity.class);
                                mContext.startActivity(healthcareintent);
                                break;

                            case 5:
                                Intent medicalstoresintent = new Intent(mContext, MedicalStoresActivity.class);
                                mContext.startActivity(medicalstoresintent);
                                break;
                            case 6:
                                Intent onlinedoctorintent = new Intent(mContext, OnlineDoctorsActivity.class);
                                mContext.startActivity(onlinedoctorintent);
                                break;
                            case 7:
                                Intent hospitaladmissionintent = new Intent(mContext, HospitalAdmissionActivity.class);
                                mContext.startActivity(hospitaladmissionintent);
                                break;

                            case 8:
                                Intent volunteersintent = new Intent(mContext, VolunteersActivity.class);
                                mContext.startActivity(volunteersintent);
                                break;
                            case 9:
                                Intent freefoodintent = new Intent(mContext, FreeFoodActivity.class);
                                mContext.startActivity(freefoodintent);
                                break;

                            case 10:
                                Intent testlabsintent = new Intent(mContext, TestLabsActivity.class);
                                testlabsintent.putExtra("url", jsons.getLab_test());
                                mContext.startActivity(testlabsintent);
                                break;

                            case 11:
                                Intent orphanagesupportintent = new Intent(mContext, OrphanageSupportActivity.class);
                                mContext.startActivity(orphanagesupportintent);
                                break;

                            case 12:
                                Intent epassintent = new Intent(mContext, EpassActivity.class);
                                epassintent.putExtra("url", jsons.getEpass());
                                mContext.startActivity(epassintent);
                                break;

                            case 13:
                                Intent donateintent = new Intent(Intent.ACTION_VIEW);
                                donateintent.setData(Uri.parse(jsons.getDonate()));
                                mContext.startActivity(donateintent);
                                break;

                            case 14:
                                Intent applicationtrackerintent = new Intent(Intent.ACTION_VIEW);
                                applicationtrackerintent.setData(Uri.parse(jsons.getTracker()));
                                mContext.startActivity(applicationtrackerintent);
                                break;

                            case 15:
                                Intent counsellingintent = new Intent(mContext, CounsellingActivity.class);
                                counsellingintent.setData(Uri.parse(jsons.getCounselling()));
                                mContext.startActivity(counsellingintent);
                                break;

                            case 16:
                                Intent educationintent = new Intent(mContext, OnlineEducationActivity.class);
                                educationintent.putExtra("sb", jsons.getKl());
                                educationintent.putExtra("cbse",jsons.getCbse());
                                educationintent.putExtra("vc",jsons.getVocational_education());
                                mContext.startActivity(educationintent);
                                break;

                            case 17:
                                Intent intent20 = new Intent(Intent.ACTION_VIEW);
                                intent20.setData(Uri.parse(jsons.getGo()));
                                mContext.startActivity(intent20);
                                break;

                            case 18:
                                Intent tweetintent = new Intent(mContext, TweetsActivity.class);
                                tweetintent.putExtra("url", jsons.getTweets());
                                mContext.startActivity(tweetintent);
                                break;
                            case 19:
                                Intent videointent = new Intent(mContext, VideosActivity.class);
                                videointent.putExtra("url", jsons.getVideos());
                                mContext.startActivity(videointent);
                                break;
                            case 20:
                                Intent faqintent = new Intent(mContext, FAQsActivity.class);
                                faqintent.putExtra("url", jsons.getFaq());
                                mContext.startActivity(faqintent);
                                break;

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return Title.length;
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView Title;
        public ImageView Image;
        public ImageViewHolder(View itemView) {
            super(itemView);

            Title = itemView.findViewById(R.id.label);
            Image = itemView.findViewById(R.id.image);
        }
    }
}