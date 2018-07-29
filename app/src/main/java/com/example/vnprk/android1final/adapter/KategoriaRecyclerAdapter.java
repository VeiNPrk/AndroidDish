package com.example.vnprk.android1final.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.KategoriaDish;
import com.example.vnprk.android1final.view.ActivityMenuDish;

import java.util.ArrayList;

/**
 * Created by VNPrk on 30.11.2016.
 */
public class KategoriaRecyclerAdapter extends RecyclerView.Adapter<KategoriaRecyclerAdapter.KategoriaViewHolder>{

    Context context;
    class KategoriaViewHolder extends RecyclerView.ViewHolder {
        //TextView tvKategoria;
        Button tvKategoria;
        View katView;
        KategoriaViewHolder(View itemView) {
            super(itemView);
            katView=itemView;
            initViews(itemView);
        }

        void initViews(View itemView) {
            tvKategoria = (Button)itemView.findViewById(R.id.tv_kategoria);
            //tvKategoria = (TextView)itemView.findViewById(R.id.tv_kategoria);
        }
    }
    ArrayList<KategoriaDish> data;

    public KategoriaRecyclerAdapter(Context _context, ArrayList<KategoriaDish> data) {
        context=_context;
        this.data = data;
    }

    @Override
    public KategoriaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.maket_rv_kategories, parent, false);

        return new KategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KategoriaViewHolder holder, final int i) {
        final int selectIdKategoria = data.get(i).getIdKategoria();
        final String selectNameKategoria = data.get(i).getNameKategoria();
        holder.tvKategoria.setText(data.get(i).getNameKategoria());

        //holder.katView.setOnClickListener(new View.OnClickListener() {
        holder.tvKategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityMenuDish.openActivity(context, selectIdKategoria, selectNameKategoria);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
