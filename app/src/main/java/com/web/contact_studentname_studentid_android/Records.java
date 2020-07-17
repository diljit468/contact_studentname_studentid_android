package com.web.contact_studentname_studentid_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.web.contact_studentname_studentid_android.Utiles.ClickInterface;
import com.web.contact_studentname_studentid_android.Utiles.SQLHelperClass;

import java.util.ArrayList;

public class Records extends AppCompatActivity {

    private static final String TAG = "Records";
    RecyclerView recordRecycler;
    ArrayList<StudentModel> studentModels=new ArrayList<>();
    RecordAdapter adapter;
    ClickInterface clickInterface;
    ImageView back,cancel;
    EditText search;
    ArrayList<StudentModel> searchRecord=new ArrayList<>();
    int searchCheck=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        recordRecycler=findViewById(R.id.recordRecycler);
        back=findViewById(R.id.back);
        cancel=findViewById(R.id.cancel);
        search=findViewById(R.id.search);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recordRecycler.setLayoutManager(manager);

        clickInterface=new ClickInterface() {
            @Override
            public void onClick(String id,int position,int type) {
                if(type==2){
                    if(searchCheck==0){
                    Intent intent=new Intent(Records.this,EditRecord.class);
                    intent.putExtra("fName",studentModels.get(position).getfName());
                    intent.putExtra("lName",studentModels.get(position).getlName());
                    intent.putExtra("mail",studentModels.get(position).getEmail());
                    intent.putExtra("phone",studentModels.get(position).getPhone());
                    intent.putExtra("address",studentModels.get(position).getAddress());
                    intent.putExtra("id",studentModels.get(position).getId());
                    startActivity(intent);
                    }else{
                        Intent intent=new Intent(Records.this,EditRecord.class);
                        intent.putExtra("fName",searchRecord.get(position).getfName());
                        intent.putExtra("lName",searchRecord.get(position).getlName());
                        intent.putExtra("mail",searchRecord.get(position).getEmail());
                        intent.putExtra("phone",searchRecord.get(position).getPhone());
                        intent.putExtra("address",searchRecord.get(position).getAddress());
                        intent.putExtra("id",searchRecord.get(position).getId());
                        startActivity(intent);
                    }
                }else{
                SQLHelperClass myDb = new SQLHelperClass(Records.this);
                Integer deleteRow = myDb.deleteTitle(id);
                if (deleteRow > 0) {
                    Toast.makeText(Records.this, "Row deleted successfully", Toast.LENGTH_SHORT).show();
                    getRecords();
                } else {
                    Toast.makeText(Records.this, "Row not found", Toast.LENGTH_SHORT).show();
                }
            }
            }
        };

        click();
    }

    private void click() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecords();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.length() == 0) {
                    getRecords();
                } else {
                    searchCheck=1;
                    searchRecord.clear();
                    int searchListLength = studentModels.size();
                    for (int a = 0; a < searchListLength; a++) {
                        if (studentModels.get(a).getfName().contains(search.getText().toString().trim())) {
                          //  Toast.makeText(Records.this, studentModels.get(a).getEmail(), Toast.LENGTH_SHORT).show();
                            StudentModel model=new StudentModel();
                            model.setId(studentModels.get(a).getId());
                            model.setfName(studentModels.get(a).getfName());
                            model.setlName(studentModels.get(a).getlName());
                            model.setEmail(studentModels.get(a).getEmail());
                            model.setPhone(studentModels.get(a).getPhone());
                            model.setAddress(studentModels.get(a).getAddress());
                            searchRecord.add(model);
                        }
                    }
                    if(searchRecord.size()==0){
                        Toast.makeText(Records.this, "No Record found", Toast.LENGTH_SHORT).show();

                    }else{

                        adapter=new RecordAdapter(Records.this,searchRecord,clickInterface);
                        recordRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getRecords();
    }

    private void getRecords() {
        searchCheck=0;
        search.setText("");
        adapter=new RecordAdapter(this,studentModels,clickInterface);
        recordRecycler.setAdapter(adapter);
        studentModels.clear();
        SQLHelperClass myDb = new SQLHelperClass(this);
        Cursor res = myDb.getAlldata();
        if (res.getCount() == 0) {
            //show massage
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();

            return;
        } else {
            while (res.moveToNext()) {
                String id = res.getString(0);
                String fName = res.getString(1);
                String lName = res.getString(2);
                String email = res.getString(3);
                String phone = res.getString(4);
                String adress = res.getString(5);

                StudentModel model=new StudentModel();
                model.setId(id);
                model.setfName(fName);
                model.setlName(lName);
                model.setEmail(email);
                model.setPhone(phone);
                model.setAddress(adress);
                studentModels.add(model);
                Log.e(TAG, "getRecords: "+fName );
            }
            adapter.notifyDataSetChanged();
        }
    }
}
