package org.hartlandrobotics.echelon2.matchScouting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import org.hartlandrobotics.echelon2.R;

import java.util.List;

public class RobotImageAdapter extends RecyclerView.Adapter<RobotImageAdapter.RobotImageViewHolder>  {
    private List<String> imageFileNames;

    RobotImageAdapter( List<String> imageFileNames ){
        this.imageFileNames = imageFileNames;
    }

    @Override
    public RobotImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType ){
        return new RobotImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_robot_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RobotImageViewHolder holder, int position){
        holder.setImage(imageFileNames.get(position));
    }

    @Override
    public int getItemCount() {
        return imageFileNames.size();
    }

    class RobotImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView robotImageView;

        RobotImageViewHolder( @NonNull View itemView){
            super(itemView);
            robotImageView = itemView.findViewById(R.id.robotImageView);
        }

        void setImage(String imageFileName){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFileName);
            robotImageView.setImageBitmap(bitmap);
        }
    }

}
