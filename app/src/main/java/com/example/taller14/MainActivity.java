package com.example.taller14;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyDisplayInfo;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private Button ingresarBtn;
    private FirebaseDatabase db;
    private TextView users;
    private ArrayList<User> usuariosCreados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameET);
        ingresarBtn = findViewById(R.id.ingresarBtn);
        users = findViewById(R.id.usuarios);

        usuariosCreados = new ArrayList<>();

        db = FirebaseDatabase.getInstance();

        ingresarBtn.setOnClickListener(this);

        loadFirebase();

    }

    private void loadFirebase() {
        db.getReference().child("semana14").child("usuarios").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {

                        for (DataSnapshot child : data.getChildren()) {
                            User user = child.getValue(User.class);
                            usuariosCreados.add(user);

                        }
                        String usuarios = "";
                        for (int i = 0; i < usuariosCreados.size(); i++) {
                            if (i + 1 < usuariosCreados.size()) {
                                usuarios += usuariosCreados.get(i).getName() + "\n";
                            } else {
                                usuarios += usuariosCreados.get(i).getName();
                            }

                        }
                        users.setText("Los usuarios existentes son:" + "\n" +
                                usuarios);
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
            case R.id.ingresarBtn:

                String username = name.getText().toString();

                db.getReference().child("semana14").child("usuarios").orderByChild("name").equalTo(username).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot data) {
                                Boolean existe = false;
                                for (DataSnapshot child : data.getChildren()) {
                                    User user = child.getValue(User.class);
                                    existe = true;
                                    Intent i = new Intent(name.getContext(),Listas.class);
                                    i.putExtra("id",user.getId());
                                    name.setText("");
                                    usuariosCreados.clear();
                                    startActivity(i);
                                }
                                if (existe == false) {
                                    if (username.isEmpty() || username.contains(" ")) {
                                        Toast.makeText(name.getContext(), "No ha digitado un usuario, o bien, tiene espacios", Toast.LENGTH_LONG).show();
                                    } else {
                                        String key = db.getReference().child("semana14").child("usuarios").push().getKey();

                                        User user = new User(key, username);

                                        db.getReference().child("semana14").child("usuarios").child(key).setValue(user);

                                        Intent i = new Intent(name.getContext(),Listas.class);
                                        i.putExtra("id",user.getId());
                                        name.setText("");
                                        usuariosCreados.clear();
                                        startActivity(i);
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );

                break;
        }
    }
}