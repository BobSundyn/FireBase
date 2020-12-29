package br.com.bobsundyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.bobsundyn.model.Pessoa;

public class MainActivity extends AppCompatActivity {

        Button btnCadastrar;
        ListView listPessoas;
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        PessoaAdapter adapterPessoas;
        FirebaseDatabase database;
        DatabaseReference referencePessoas;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        listPessoas = findViewById(R.id.listPessoas);

        adapterPessoas = new PessoaAdapter(MainActivity.this, pessoas);
        listPessoas.setAdapter(adapterPessoas);

        database = FirebaseDatabase.getInstance();
        referencePessoas = database.getReference("pessoas");
        referencePessoas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pessoas.clear();
                for (DataSnapshot dataPessoa : snapshot.getChildren()){
                    Pessoa pessoa = dataPessoa.getValue(Pessoa.class);
                    pessoa.setKey(dataPessoa.getKey());
                    pessoas.add(pessoa);
                }
                adapterPessoas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Erro ao consultar as pessoas!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intentCadastrarPessoa = new Intent(MainActivity.this, CadastrarPessoaActivity.class);
        startActivity(intentCadastrarPessoa);
        }
        });

    listPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            final Pessoa pessoaSelecionada = pessoas.get(i);
            AlertDialog alerta = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Excluir")
                    .setMessage("Deseja excluir a pessoa " + pessoaSelecionada.nome + "?")
                    .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            referencePessoas.child(pessoaSelecionada.getKey()).removeValue();
                            Toast.makeText(MainActivity.this, "Pessoa removida com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
                        }
                    }).create();
            alerta.show();
            return true;
        }
    });

    listPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Pessoa pessoaSelecionada = pessoas.get(i);
            Intent intentCadastrar = new Intent(MainActivity.this, CadastrarPessoaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("key", pessoaSelecionada.getKey());
            intentCadastrar.putExtras(bundle);
            startActivity(intentCadastrar);
        }
    });
        }
        }