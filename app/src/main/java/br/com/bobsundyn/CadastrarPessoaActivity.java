package br.com.bobsundyn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.bobsundyn.model.Pessoa;

public class CadastrarPessoaActivity extends AppCompatActivity {

    EditText textNome, textImagem, textIdade;
    Button btnCadastrar;
    FirebaseDatabase database;
    DatabaseReference referencePessoas;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pessoa);

        textNome = findViewById(R.id.textNome);
        textImagem = findViewById(R.id.textImagem);
        textIdade = findViewById(R.id.textIdade);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        database = FirebaseDatabase.getInstance();
        referencePessoas = database.getReference("pessoas");

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            key = "";
            getSupportActionBar().setTitle("Cadastrar Pessoa");
        } else {
            key = bundle.getString("key");
            getSupportActionBar().setTitle("Alterar Pessoa");
            btnCadastrar.setText("Alterar");
            referencePessoas.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Pessoa pessoa = snapshot.getValue(Pessoa.class);
                    textNome.setText(pessoa.nome);
                    textImagem.setText(pessoa.imagem);
                    textIdade.setText(pessoa.idade+"");
                    getSupportActionBar().setSubtitle(pessoa.nome);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CadastrarPessoaActivity.this, "Erro ao consultar a pessoa", Toast.LENGTH_SHORT).show();
                }
            });
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeDigitado = textNome.getText().toString();
                String imagemDigitada = textImagem.getText().toString();
                String idadeDigitada = textIdade.getText().toString();
                int idade = Integer.parseInt(idadeDigitada);
                Pessoa pessoa = new Pessoa(nomeDigitado, imagemDigitada, idade);
                if (key.equals("")) {
                    referencePessoas.push().setValue(pessoa);
                    Toast.makeText(CadastrarPessoaActivity.this, "Pessoa cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
                }
                else {
                    referencePessoas.child(key).setValue(pessoa);
                    Toast.makeText(CadastrarPessoaActivity.this, "Pessoa alterada com sucesso!", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}