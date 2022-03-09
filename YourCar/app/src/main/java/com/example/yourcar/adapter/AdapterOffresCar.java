package com.example.yourcar.adapter;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yourcar.R;
import com.example.yourcar.dto.Car;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class AdapterOffresCar extends RecyclerView.Adapter<AdapterOffresCar.MyViewHolder> {

    private List<Car> carList;

    Context context;
    private RecyclerViewClickListener listener ;


    /*public AdapterOffresCar(Context context, ArrayList<Car> carlist) {
        this.context = context;
        this.carList = carlist;
    }
*/


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView marque,model,kilometrage,annee,prix;
        ImageView imgCar;
        MyViewHolder(View view) {
            super(view);
            prix = view.findViewById(R.id.prix);
            marque = view.findViewById(R.id.offresmarque);
            model = view.findViewById(R.id.offresmodele);
            annee= view.findViewById(R.id.offresannee);
            kilometrage= view.findViewById(R.id.offreskilometrage);
            imgCar= view.findViewById(R.id.imgCar);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnClick(view, getAdapterPosition());
        }
    }
    public AdapterOffresCar(List<Car> carList,RecyclerViewClickListener listener) {
        this.carList = carList;
        this.listener=listener;
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
        holder.prix.setText(car.getPrix()+"€");
        holder.kilometrage.setText(car.getKilometrage()+"km");

       Glide.with(holder.imgCar.getContext()).load(car.getCarburant()).into(holder.imgCar); //url de l'image mais constructeur oblige appeler carburant et non varUrl
    }

    public int getItemCount() {
        return carList.size();

    }

    public interface RecyclerViewClickListener{
        void OnClick(View v, int position);
    }

//SEARCHBAR
   public void filterList(List<Car> filteredList) {
        carList=filteredList;
        notifyDataSetChanged();
    }
}