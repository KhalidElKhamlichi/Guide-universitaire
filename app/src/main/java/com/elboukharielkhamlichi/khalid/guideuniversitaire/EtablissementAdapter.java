package com.elboukharielkhamlichi.khalid.guideuniversitaire;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elboukharielkhamlichi.khalid.guideuniversitaire.entity.Etablissement;

import java.util.Collections;
import java.util.List;

/**
 * Created by Khalid on 12/29/2017.
 */

public class EtablissementAdapter extends RecyclerView.Adapter<EtablissementAdapter.ViewHolder> {

    private List<Etablissement> mData = Collections.emptyList();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public EtablissementAdapter(Context context, List<Etablissement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cardview_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String nom = mData.get(position).getNom();
        String ville = mData.get(position).getVille();
        String uri = mData.get(position).getImageUri();
        holder.etaNom.setText(nom);
        holder.etaVille.setText(ville);

        if(uri != null) {
            holder.etaImage.setImageURI(Uri.parse(uri));
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView etaNom;
        public TextView etaVille;
        public ImageView etaImage;

        public ViewHolder(View itemView) {
            super(itemView);
            etaNom = (TextView) itemView.findViewById(R.id.uniNom);
            etaVille = (TextView) itemView.findViewById(R.id.uniVille);
            etaImage = (ImageView) itemView.findViewById(R.id.uniImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Etablissement getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
