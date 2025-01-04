package com.example.myapplicationrv.activitys;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationrv.R;
import com.example.myapplicationrv.adapters.CustomeAdapter;
import com.example.myapplicationrv.classes.myData;
import com.example.myapplicationrv.models.Data;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Data> arr;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CustomeAdapter customeAdapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rvcon);
        searchEditText = findViewById(R.id.searchEditText);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arr = new ArrayList<>();
        for (int i = 0; i < myData.nameArray.length; i++) {
            arr.add(new Data(
                    myData.nameArray[i],
                    myData.versionArray[i],
                    myData.drawableArray[i],
                    myData.id_[i]
            ));
        }

        customeAdapter = new CustomeAdapter(arr, this::showDialog);
        recyclerView.setAdapter(customeAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customeAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void showDialog(Data data) {
        if (data == null) return;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        TextView name = dialog.findViewById(R.id.dialogName);
        TextView description = dialog.findViewById(R.id.dialogDescription);
        ImageView image = dialog.findViewById(R.id.dialogImage);

        if (data.getName() != null) name.setText(data.getName());
        if (data.getVersion() != null) description.setText(data.getVersion());
        if (data.getImage() != 0) image.setImageResource(data.getImage());

        dialog.show();
    }
}
