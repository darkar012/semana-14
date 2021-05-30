package com.example.taller14;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Listas extends AppCompatActivity implements View.OnClickListener {

    private EditText nombreTarea, descripcionTarea;
    private Button agregarBtn;
    private ListView doList;
    private TareaAdapter adapter;
    private String id;

    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        nombreTarea = findViewById(R.id.nombreTarea);
        descripcionTarea = findViewById(R.id.descrip);
        agregarBtn = findViewById(R.id.agregarBtn);
        doList = findViewById(R.id.listTareas);

        adapter = new TareaAdapter();

        doList.setAdapter(adapter);

        id = getIntent().getExtras().getString("id");

        db = FirebaseDatabase.getInstance();

        agregarBtn.setOnClickListener(this);

        loadDatabase();
    }

    private void loadDatabase() {
        DatabaseReference ref = db.getReference().child("semana14").child("tareas").child(id);
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {
                        adapter.clear();
                        for (DataSnapshot child : data.getChildren()){
                            Tarea tarea = child.getValue(Tarea.class);
                            adapter.addTareas(tarea);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agregarBtn:
                String descrip = descripcionTarea.getText().toString();
                String nTarea = nombreTarea.getText().toString();

                if (descrip.isEmpty()) {
                    Toast.makeText(this, "la tarea debe tener un nombre", Toast.LENGTH_LONG).show();
                } else if (descrip.isEmpty()) {
                    Toast.makeText(this, "la tarea debe tener una descripcion", Toast.LENGTH_LONG).show();
                } else {
                    DatabaseReference reference = db.getReference().child("semana14").child("tareas").child(id);
                    String key = reference.push().getKey();
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    Tarea tarea = new Tarea(key, descrip, nTarea, "pending",formattedDate, id);
                    reference.child(key).setValue(tarea);
                    descripcionTarea.setText("");
                    nombreTarea.setText("");
                }
        }
    }
}