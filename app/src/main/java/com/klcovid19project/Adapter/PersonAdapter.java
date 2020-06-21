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

import com.klcovid19project.Models.Persons;
import com.klcovid19project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ImageViewHolder> implements Filterable {


    private Context mContext;
    private List<Persons> mPersonList;
    private FirebaseAuth mAuth;
    private List<Persons> mDefaultPersonList;
    private DatabaseReference mPersonsDatabase, mUsersDatabase;
    private SearchAdapterListener listener;
    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    public PersonAdapter(Context context, List<Persons> mPrescriptionList, SearchAdapterListener listener) {
        mContext = context;
        this.mPersonList = mPrescriptionList;
        this.mDefaultPersonList = mPrescriptionList;
        this.listener = listener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_layout_persons_details, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();
        mPersonsDatabase = FirebaseDatabase.getInstance().getReference().child("Patients");
        mPersonsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        final Persons persons = mPersonList.get(position);

        holder.Name.setText(persons.getName());
        holder.Phone.setText(persons.getPhone_number());
        holder.Email.setText(persons.getEmail_id());
        holder.Desc.setText("Description : "+persons.getDesc());
        holder.Type.setText(persons.getType());
        holder.Location.setText("Address : "+persons.getAddress());

    }

    @Override
    public int getItemCount() {
        return mPersonList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mPersonList = mDefaultPersonList;
                } else {
                    List<Persons> filteredList = new ArrayList<>();
                    for (Persons row : mDefaultPersonList) {

                        if (row.getDesc().toLowerCase().contains(charString) ||
                                row.getAddress().toLowerCase().contains(charString) ||
                                row.getCity().toLowerCase().contains(charString) ||
                                row.getEmail_id().toLowerCase().contains(charString) ||
                                row.getName().toLowerCase().contains(charString) ||
                                row.getType().toLowerCase().contains(charSequence)
                        ) {
                            filteredList.add(row);
                        }
                    }

                    mPersonList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPersonList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPersonList = (ArrayList<Persons>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private TextView Name, Email, Phone, Location, Type, Desc;

        public ImageViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            Email = itemView.findViewById(R.id.email);
            Location = itemView.findViewById(R.id.location);
            Type = itemView.findViewById(R.id.type);
            Desc = itemView.findViewById(R.id.desc);
            Phone = itemView.findViewById(R.id.phone);


        }
    }

    public interface SearchAdapterListener {
        void onSearchSelected(Persons persons);
    }
}