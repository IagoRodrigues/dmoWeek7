package br.edu.dmos5.github_dmos5.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.model.Repository;


public class Adapter extends RecyclerView.Adapter<Adapter.RepositoryViewHolder>   {

    //A fonte de dados está em nossa implementação
    private List<Repository> repositories;

    //Oficial
    public Adapter(@NonNull List<Repository> repositorios) {
        this.repositories = repositorios;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repositorio, parent, false);
        RepositoryViewHolder viewHolder = new RepositoryViewHolder(view);

        //this.repositories = new ArrayList<>();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.RepositoryViewHolder holder, int position) {
        //Como só terei o atributo nome, uso somente um holder
        holder.textView_nome_repo.setText(repositories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (repositories != null){
            return repositories.size();
        }
        return 0;
    }


    class RepositoryViewHolder extends RecyclerView.ViewHolder {
        //Só possui campo nome
        private final TextView textView_nome_repo;

        public RepositoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nome_repo = itemView.findViewById(R.id.textView_nomeRepo);
        }
    }


    public void update(List<Repository> repositories, ImageView imageView, RecyclerView recyclerView) {

        //Tiro a imagem da frente
        imageView.setVisibility(View.GONE);

        //Coloco o recyclerView
        recyclerView.setVisibility(View.VISIBLE);

        //Adiciono os itens trazidos

        //if(repositories != null) {
            //Nessa linha obtenho null pointer exception
            //this.repositories.addAll(repositories);
        //}

        this.repositories = repositories;
        //Notifico a mudança na lista
        notifyDataSetChanged();
    }
}
