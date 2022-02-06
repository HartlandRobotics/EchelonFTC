package org.hartlandrobotics.echelon2.TBA.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.District;

import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictViewHolder> {


    private final LayoutInflater mInflater;
    private List<District> mDistricts; // cached copy of districts

    DistrictListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        return new DistrictViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
        if (mDistricts != null) {
            holder.setDistrict(mDistricts.get(position));
        } else {
            holder.setDisplayText("No District Data Yet...");
        }
    }

    void setDistricts(List<District> districts) {
        mDistricts = districts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDistricts != null) return mDistricts.size();
        return 0;
    }
}
