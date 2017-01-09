package view.celso.com.br.tripadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TelaLugar extends AppCompatActivity {
    private String nomeDetalhe;

    private TextView txDetalhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_lugar);
        txDetalhe = (TextView) findViewById(R.id.idDetalhes);
        Intent intent = getIntent();
        //nomeDetalhe = intent.getStringExtra("meuLugar");
        txDetalhe.setText(intent.getStringExtra("meuLugar"));


    }
}
