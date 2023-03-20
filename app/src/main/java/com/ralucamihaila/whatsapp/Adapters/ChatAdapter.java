package com.ralucamihaila.whatsapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ralucamihaila.whatsapp.Models.MessageModel;
import com.ralucamihaila.whatsapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<MessageModel> messageModels;
    Context context;
    String receiverId;

    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String receiverId) {
        this.messageModels = messageModels;
        this.context = context;
        this.receiverId = receiverId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if ( viewType == SENDER_VIEW_TYPE) {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewVolter(view);

        } else {

            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new ReceiverViewVolter(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance("https://whatsapp-849ed-default-rtdb.europe-west1.firebasedatabase.app/");
                                String senderRoom = FirebaseAuth.getInstance().getUid() + receiverId;
                                database.getReference().child("Chats").child(senderRoom)
                                        .child(messageModel.getMessageId())
                                        .setValue(null);
                            }
                        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

                return false;
            }
        });

        if (holder.getClass() == SenderViewVolter.class) {

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(messageModel.getTimestamp());
            Date d = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(d);

            ((SenderViewVolter)holder).senderMessage.setText(messageModel.getMessage());
            ((SenderViewVolter)holder).senderTime.setText(time);

        } else {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(messageModel.getTimestamp());
            Date d = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(d);
            ((ReceiverViewVolter)holder).receiverMessage.setText(messageModel.getMessage());
            ((ReceiverViewVolter)holder).receiverTime.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewVolter extends RecyclerView.ViewHolder {

        TextView receiverMessage, receiverTime;

        public ReceiverViewVolter(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.reciverText);
            receiverTime = itemView.findViewById(R.id.reciverTime);
        }
    }

    public class SenderViewVolter extends RecyclerView.ViewHolder {

        TextView senderMessage, senderTime;

        public SenderViewVolter(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
