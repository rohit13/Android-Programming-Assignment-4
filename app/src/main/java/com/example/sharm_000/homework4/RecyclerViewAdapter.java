package com.example.sharm_000.homework4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by sharm_000 on 2/15/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    OnItemClickListener mItemClickListener;
    private List<Map<String, ?>> mData;
    private Context context;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onItemCheckBoxSelect(int position, boolean isChecked);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public RecyclerViewAdapter(Context myContext, List<Map<String, ?>> moviesData){
        mData=moviesData;
        context=myContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newView;
        switch (viewType) {
            case 1:
                newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view, parent, false);
                break;
            case 2:
                newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view_1, parent, false);
                break;
            default:
                newView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_view, parent, false);
                break;
        }
        ViewHolder vh = new ViewHolder(newView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String,?> movie = mData.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemViewType(int position) {
        Map<String,?> movie = mData.get(position);
        Double rating = (Double) movie.get("rating");
        Double threshold=8.0;
        if(rating>=threshold)
            return 1;
        else
            return 2;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView movieImage;
        public TextView movieName;
        public TextView movieDesc;
        public CheckBox isSelected;
        public TextView rating;
        //public RelativeLayout myRow;

        public ViewHolder(View itemView) {
            super(itemView);
            movieImage=(ImageView)itemView.findViewById(R.id.movieImage);
            movieName=(TextView)itemView.findViewById(R.id.movieName);
            movieDesc= (TextView) itemView.findViewById(R.id.movieDesc);
            isSelected = (CheckBox) itemView.findViewById(R.id.chechBox);
            rating = (TextView) itemView.findViewById(R.id.rating);
            //myRow = (RelativeLayout) itemView.findViewById(R.id.myRow);

            isSelected.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mItemClickListener!=null)
                        mItemClickListener.onItemCheckBoxSelect(getPosition(),isChecked);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(mItemClickListener!=null)
                        mItemClickListener.onItemClick(v,getPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    if(mItemClickListener!=null)
                        mItemClickListener.onItemLongClick(v,getPosition());
                    return true;
                }
            });
        }

        public void bindMovieData(Map<String,?> movie){
            movieName.setText((String) movie.get("name"));
            movieImage.setImageResource((Integer) movie.get("image"));
            movieDesc.setText((String) movie.get("description"));
            isSelected.setChecked((Boolean) movie.get("isSelected"));
            rating.setText( movie.get("rating")+"/10");
        }
    }
}

