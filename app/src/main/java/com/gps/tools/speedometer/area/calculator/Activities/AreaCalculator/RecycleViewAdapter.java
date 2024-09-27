package com.gps.tools.speedometer.area.calculator.Activities.AreaCalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gps.tools.speedometer.area.calculator.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<Integer> iconList;
    private newInterface listnterface;



    public  class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img;

        public ViewHolder(@Nullable View view) {
            super(view);
            if (view == null) {

            }
            this.img = (ImageView) view.findViewById(R.id.img_icon);
        }

        public final ImageView getImg() {
            return this.img;
        }

        public final void onBinder(@NotNull newInterface newinterface, int i) {

            this.img.setOnClickListener(new RecycleViewAdapter$ViewHolder$onBinder$1(newinterface, i));
        }
    }

    public interface newInterface {
        void button1(int i);

        void button2(int i);

        void button3(int i);

        void button4(int i);
    }

    public RecycleViewAdapter(@NotNull Context context2, @NotNull newInterface newinterface, @NotNull ArrayList<Integer> arrayList) {

        this.context = context2;
        this.listnterface = newinterface;
        this.iconList = arrayList;
    }

    @NotNull
    public final ArrayList<Integer> getIconList() {
        return this.iconList;
    }

    @NotNull
    public final newInterface getListnterface() {
        return this.listnterface;
    }

    public final void setListnterface(@NotNull newInterface newinterface) {

        this.listnterface = newinterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_view_adapter, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        Object obj = this.iconList.get(i);

        ((Number) obj).intValue();
    //    ImageView img = holder.getImg();
        Object obj2 = this.iconList.get(i);
     //   img.setImageResource(((Number) obj2).intValue());
    //    holder.onBinder(this.listnterface, i);


    }

    public int getItemCount() {
        return this.iconList.size();
    }

}
