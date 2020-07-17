package com.web.contact_studentname_studentid_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.web.contact_studentname_studentid_android.Utiles.ClickInterface;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    Records records;
    ArrayList<StudentModel> studentModels;
    ClickInterface clickInterface;

    public RecordAdapter(Records records, ArrayList<StudentModel> studentModels, ClickInterface clickInterface) {
        this.records = records;
        this.studentModels = studentModels;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_student_record, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv_name.setText(studentModels.get(position).getfName()+" "+studentModels.get(position).getlName());
        holder.tv_address.setText(studentModels.get(position).getAddress());
        holder.tv_mail.setText(studentModels.get(position).getEmail());
        holder.tv_phone.setText(studentModels.get(position).getPhone());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog(studentModels.get(position).getId(),position);
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickInterface.onClick(studentModels.get(position).getId(),position,2);

            }
        });
    }


    @Override
    public int getItemCount() {
        return studentModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_address,tv_phone,tv_mail,tv_name;
        ImageView delete,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_mail=itemView.findViewById(R.id.tv_mail);
            tv_phone=itemView.findViewById(R.id.tv_phone);
            tv_address=itemView.findViewById(R.id.tv_address);
            delete=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }


    private void deleteDialog(final String id, final int position) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(records);
        alertDialogBuilder.setMessage("Are you sure you want to delete?");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        clickInterface.onClick(id,position,1);
                        arg0.dismiss();
                    }
                });

        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
