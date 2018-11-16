package zsp.erasmus.Fragments;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import zsp.erasmus.R;

/**
 * Created by julupukki on 11.12.17.
 */

public class AdapterCrdv extends RecyclerView.Adapter<AdapterCrdv.MyViewHolder> {

private static ArrayList<Member_datamodel> dataSet;



    public static class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textViewName;
    TextView textViewSurname;
    ImageView imageViewCountry;
    CardView mCardViewBottom;
    public MyViewHolder(View itemView) {
        super(itemView);
        this.imageViewCountry= (ImageView) itemView.findViewById(R.id.imageView2);
        this.textViewName = (TextView) itemView.findViewById(R.id.crd_v_name);
        this.textViewSurname = (TextView) itemView.findViewById(R.id.crd_v_team);
        this.mCardViewBottom = (CardView) itemView.findViewById(R.id.card_view);
    }
}


    public AdapterCrdv(ArrayList<Member_datamodel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mem_list_crdviw, parent, false);

        view.setOnClickListener(Member.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewSurname = holder.textViewSurname;
        ImageView imageViewCountry =holder.imageViewCountry;
        textViewName.setText(dataSet.get(listPosition).getName()+" "+dataSet.get(listPosition).getSurname());
        textViewSurname.setText(dataSet.get(listPosition).getTeam());
        Log.i("Admin:",dataSet.get(listPosition).getAdmin());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(dataSet.get(listPosition).getTeach(), "1")){

                holder.mCardViewBottom.setCardBackgroundColor(Color.rgb(255, 204, 204));
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(dataSet.get(listPosition).getAdmin(), "1")){
                holder.mCardViewBottom.setCardBackgroundColor(Color.rgb(102, 185, 255));
            }
        }
        switch(dataSet.get(listPosition).getCountry()){
            case "1":
                imageViewCountry.setImageResource(R.drawable.pl);
                break;
            case "2":
                imageViewCountry.setImageResource(R.drawable.fr);
                break;
            case "3":
                imageViewCountry.setImageResource(R.drawable.cz);
                break;
            case "4":
                imageViewCountry.setImageResource(R.drawable.sp);
                break;
            case "5":
                imageViewCountry.setImageResource(R.drawable.ge);
                break;
            default:
                imageViewCountry.setImageResource(R.drawable.eu);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}