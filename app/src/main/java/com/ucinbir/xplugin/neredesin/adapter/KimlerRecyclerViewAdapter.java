package com.ucinbir.xplugin.neredesin.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.database.Profil;

import java.util.List;

public class KimlerRecyclerViewAdapter extends RecyclerView.Adapter<KimlerRecyclerViewAdapter.ProfilViewHolder> {

    private List<Profil> arkadasList;
    private KimlerItemClickListener itemClickListener;

    public KimlerRecyclerViewAdapter(List<Profil> arkadasList, KimlerItemClickListener itemClickListener) {
        this.arkadasList = arkadasList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ProfilViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kimler_item, parent, false);
        return new ProfilViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfilViewHolder holder, int position) {
        Profil profil = arkadasList.get(position);
        holder.kimlerListKullaniciAdiTextView.setText(profil.getKullaniciAdi());
        holder.kimlerListAdSoyadTextView.setText(profil.getAd() + " " + profil.getSoyad());
    }

    @Override
    public int getItemCount() {
        return arkadasList.size();
    }

    public class ProfilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView kimlerListKullaniciAdiTextView;
        TextView kimlerListAdSoyadTextView;

        public ProfilViewHolder(View itemView) {
            super(itemView);
            kimlerListKullaniciAdiTextView = (TextView) itemView.findViewById(R.id.kimlerListKullaniciAdiTextView);
            kimlerListAdSoyadTextView = (TextView) itemView.findViewById(R.id.kimlerListAdSoyadTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(kimlerListKullaniciAdiTextView.getText().toString());
        }
    }

    public interface KimlerItemClickListener {
        void onItemClick(String kullaniciAdi);
    }

}
