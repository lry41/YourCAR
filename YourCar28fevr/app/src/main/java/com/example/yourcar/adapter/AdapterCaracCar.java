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

public class AdapterCaracCar extends RecyclerView.Adapter<AdapterCaracCar.MyViewHolder> {
    private List<String> caracList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView caracBlockText;
        MyViewHolder(View view) {
            super(view);
            caracBlockText = view.findViewById(R.id.caracBlock);

        }
    }
    public AdapterCaracCar(List<String> caracList) {
        this.caracList = caracList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caracteristique, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String carac = caracList.get(position);

            //ON AURA UN PROBLEME À AMELIORER

            if(position==0)holder.caracBlockText.setText(caracList.get(0) + "€");
            else if (position == 1) holder.caracBlockText.setText(caracList.get(0) + "chv");
            else if (position == 2) holder.caracBlockText.setText(caracList.get(1) + "km");
            else if (position == 3) holder.caracBlockText.setText(caracList.get(2) + "");

        /*switch (position){
            case 0:  holder.caracBlockText.setText(car.getChevaux()+"chv");

            case 1:holder.caracBlockText.setText(car.getKilometrage()+"km");
            break;
            case 2:holder.caracBlockText.setText(car.getCarburant()+"");

            default:holder.caracBlockText.setText("erreur chargement");
            break;
        }*/
    }

    public int getItemCount() {
        return caracList.size();

    }
}