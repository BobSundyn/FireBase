package br.com.bobsundyn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.bobsundyn.model.Pessoa;


public class PessoaAdapter extends BaseAdapter {

    Context context;
    ArrayList<Pessoa> pessoas;

    public PessoaAdapter(Context context, ArrayList<Pessoa> pessoas) {
        this.context = context;
        this.pessoas = pessoas;
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Object getItem(int i) {
        return pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.adapter_pessoa, viewGroup, false);
        }

        Pessoa pessoa = pessoas.get(i);

        TextView textNome = view.findViewById(R.id.textNome);
        TextView textIdade = view.findViewById(R.id.textIdade);
        ImageView imagePessoa = view.findViewById(R.id.imagePessoa);

        textNome.setText(pessoa.nome);
        textIdade.setText(pessoa.idade+"");
        Picasso.get().load(pessoa.imagem).into(imagePessoa);

        return view;
    }
}

