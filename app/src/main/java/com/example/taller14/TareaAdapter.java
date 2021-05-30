package com.example.taller14;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class TareaAdapter extends BaseAdapter {

    private ArrayList<Tarea> tareas;
    private Boolean pressed = false;

    public TareaAdapter() {
        tareas = new ArrayList<>();
    }

    public void clear() {
        tareas.clear();
        notifyDataSetChanged();
    }

    public void addTareas(Tarea tarea) {
        tareas.add(tarea);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tareas.size();
    }

    @Override
    public Object getItem(int position) {
        return tareas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int pos, View renglon, ViewGroup lista) {
        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View renglonView = inflater.inflate(R.layout.row, null);

        Tarea tarea = tareas.get(pos);

        Button delete = renglonView.findViewById(R.id.deleteBtn);
        Button check = renglonView.findViewById(R.id.checkBtn);
        TextView fecha = renglonView.findViewById(R.id.fechaRow);
        TextView status = renglonView.findViewById(R.id.statusRow);
        TextView nombreTarea = renglonView.findViewById(R.id.nombreRow);
        TextView descripcion = renglonView.findViewById(R.id.descripcionRow);

        fecha.setText(tarea.getFecha());
        status.setText(tarea.getStatus());
        nombreTarea.setText(tarea.getNombre());
        descripcion.setText(tarea.getDescripcion());



       delete.setOnClickListener((v) -> {
            String id = tarea.getId();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("semana14").child("tareas").child(tarea.getUsuario()).child(id);
            reference.setValue(null);
        });

        check.setOnClickListener((v) -> {

            String id = tarea.getId();

            Log.e(">>>", "status: "+ tarea.getStatus());

            if (tarea.getStatus() == "pending") {
                String stat = "completed";

                Tarea toDo = new Tarea(id, tarea.getDescripcion(), tarea.getNombre(), stat, tarea.getFecha(), tarea.getUsuario());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("semana14").child("tareas").child(tarea.getUsuario()).child(id);
                reference.setValue(toDo);



            } else {
                String stat = "pending";

                Tarea toDo = new Tarea(id, tarea.getDescripcion(), tarea.getNombre(), stat, tarea.getFecha(), tarea.getUsuario());
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("semana14").child("tareas").child(tarea.getUsuario()).child(id);
                reference.setValue(toDo);


            }
        });

        if (tarea.getStatus()=="pending"){
            check.setBackgroundResource(R.drawable.uncheck);
        } else {
            check.setBackgroundResource(R.drawable.check);
        }


        return renglonView;
    }
}
