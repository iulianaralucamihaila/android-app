package com.ralucamihaila.whatsapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ralucamihaila.whatsapp.Models.Users;
import com.ralucamihaila.whatsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
    ArrayList<Users> list;
    Context context;

    public StatusAdapter (ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user_status, parent, false);
        return new StatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusAdapter.ViewHolder holder, int position) {
        Users users = list.get(position);

        Picasso.get().load(users.getProfilePicture()).placeholder(R.drawable.ic_user_profile).into(holder.image);
        holder.userName.setText(users.getUsername());
        holder.status.setText(users.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView userName, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userNameList);
            status = itemView.findViewById(R.id.status);
        }
    }
}
