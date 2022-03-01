package com.example.yourcar.adapter;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourcar.R;
import com.example.yourcar.dto.Car;

import java.util.List;

public class AdapterOffresCar extends RecyclerView.Adapter<AdapterOffresCar.MyViewHolder> {
    private List<Car> carList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView marque,model,kilometrage,annee,prix;
        MyViewHolder(View view) {
            super(view);
            prix = view.findViewById(R.id.prix);
            marque = view.findViewById(R.id.offresmarque);
            model = view.findViewById(R.id.offresmodele);
            annee= view.findViewById(R.id.offresannee);
            kilometrage= view.findViewById(R.id.offreskilometrage);
        }
    }
    public AdapterOffresCar(List<Car> carList) {
        this.carList = carList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offres, parent, false);
        return new MyViewHolder(itemView);
    }

   @SuppressLint("SetTextI18n")
   public void onBindViewHolder(MyViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.marque.setText(car.getMarque_car());
        holder.model.setText(car.getModel_car());
        holder.annee.setText(car.getAnnee()+"");
        holder.prix.setText("Au prix de : "+car.getPrix()+"€");
        holder.kilometrage.setText(car.getKilometrage()+"km");
    }

    public int getItemCount() {
        return carList.size();

    }
}