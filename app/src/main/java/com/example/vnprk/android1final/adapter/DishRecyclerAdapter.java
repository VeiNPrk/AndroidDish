package com.example.vnprk.android1final.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.*;
import android.widget.Toast;

import com.example.vnprk.android1final.Presenter.DataPresenter;
import com.example.vnprk.android1final.R;
import com.example.vnprk.android1final.model.Dish;
import com.example.vnprk.android1final.model.KategoriaDish;
import com.example.vnprk.android1final.view.ActivityDish;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VNPrk on 30.11.2016.
 */
public class DishRecyclerAdapter extends RecyclerView.Adapter<DishRecyclerAdapter.DishViewHolder>{

    Context context;
    DataPresenter presenter;
    boolean fromMenu;

    class DishViewHolder extends RecyclerView.ViewHolder {
        CardView cvDish;
        ImageView imDish;
        TextView tvDishName;
        TextView tvDishKategoria;
        TextView tvDishPrice;

        DishViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        void initViews(View itemView) {
            cvDish = (CardView)itemView.findViewById(R.id.cv_dish);
            //cvDish.setOnCreateContextMenuListener(this);
            imDish = (ImageView)itemView.findViewById(R.id.im_dish_photo);
            tvDishName = (TextView)itemView.findViewById(R.id.tv_dish_name);
            tvDishKategoria = (TextView)itemView.findViewById(R.id.tv_dish_kategoria);
            tvDishPrice = (TextView)itemView.findViewById(R.id.tv_dish_price);
        }
    }

    ArrayList<Dish> data;
    ArrayList<KategoriaDish> kategories;

    public DishRecyclerAdapter(Context _context, ArrayList<Dish> data, ArrayList<KategoriaDish> kategoria, boolean _fromMenu) {
        this.data = data;
        kategories=kategoria;
        context=_context;
        fromMenu=_fromMenu;
        presenter = new DataPresenter(context);
    }

        @Override
        public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.maket_rv_dish, parent, false);
            return new DishViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DishViewHolder holder, int i) {
            final Dish selectedDish = data.get(i);
            holder.tvDishName.setText(data.get(i).getName());
            holder.tvDishKategoria.setText(getKategoriaForIndex(data.get(i).getKategoria()));
            holder.tvDishPrice.setText(Integer.toString(data.get(i).getPrice()));
            holder.imDish.setImageResource(context.getResources().getIdentifier(data.get(i).getPhoto(), "drawable", context.getPackageName()));
            holder.cvDish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityDish.openActivity(context, selectedDish, fromMenu);
                }
            });
            if(fromMenu) {
                holder.cvDish.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        createPopupMenu(v, selectedDish);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

    public String getKategoriaForIndex(int i) {
        String nameKat = "";
        for (KategoriaDish kategoria : kategories) {
            if (kategoria.getIdKategoria() == i) {
                nameKat = kategoria.getNameKategoria();
            }
        }
        return nameKat;
    }

    private void createPopupMenu(View v, final Dish _selectedDish){
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.inflate(R.menu.popup_menu_dish);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_item_add_position:
                        presenter.addPositionToOrder(_selectedDish);
                        Toast.makeText(context, context.getString(R.string.tst_add_position),Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu_item_about:
                        ActivityDish.openActivity(context, _selectedDish, fromMenu);
                        return true;
                }
                return false;
            }
        });
        popup.show();
    }
}
