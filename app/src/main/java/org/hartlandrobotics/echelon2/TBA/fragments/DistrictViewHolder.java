package org.hartlandrobotics.echelon2.TBA.fragments;

import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.database.entities.District;

public class DistrictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextInputLayout districtNameLayout;
    private EditText districtName;
    private DistrictListViewModel districtViewModel;

    DistrictViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        districtNameLayout = itemView.findViewById(R.id.list_item_textview);
        districtNameLayout.setEndIconDrawable(R.drawable.baseline_check_box_outline_blank_24);
        //districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_off_background);
        districtName = districtNameLayout.getEditText();

        //districtNameLayout.setEndIconOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
//                    TBAActivity a = (TBAActivity)getActivity();
//                   a.setDistrictKey(district.getDistrictKey());
//
//                   isChecked = !isChecked;
//                  if(isChecked){
//                     districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_on_background);
//
//                   }
//                  else{
//                     districtNameLayout.setEndIconDrawable(R.drawable.baseline_check_box_outline_blank_24);
//                    //districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_off_background);
        //               }
        //          }
        //});
    }

    public void setDistrict(District district) {
        this.districtViewModel = new DistrictListViewModel(district);

        districtName.setText(district.getDisplayName());
        districtNameLayout.setHelperText(district.getDistrictKey());
    }

    public void setDisplayText(String displayText) {
        districtName.setText(displayText);
    }

    @Override
    public void onClick(View view) {
        // find out what the view is and how to communicate this up to the status object

        // handle district selected
    }

}
