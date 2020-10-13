package br.edu.dmos5.github_dmos5.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.model.Repository;

public class Adapter extends RecyclerView.Adapter<Adapter.RepositoryViewHolder>   {

    private List<Repository> repositories;
    private Context mContext;

    public Adapter(@NonNull List<Repository> repositories, Context context) {
        this.repositories = repositories;
        this.mContext = context;
    }

    @NonNull
    @Override
    public Adapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repositorio, parent, false);
        RepositoryViewHolder viewHolder =new RepositoryViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.RepositoryViewHolder holder, int position) {
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

        private final TextView textView_nome_repo;

        public RepositoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nome_repo = itemView.findViewById(R.id.textView_nomeRepo);
        }
    }

    public void update(List<Repository> repositories, ImageView imageView, RecyclerView recyclerView) {
        this.repositories = repositories;
        imageView.setVisibility(View.GONE);

        recyclerView.setVisibility(View.VISIBLE);
        notifyDataSetChanged();
    }

}
