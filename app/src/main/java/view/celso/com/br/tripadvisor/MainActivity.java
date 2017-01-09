package view.celso.com.br.tripadvisor;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> lugares;
    static ArrayList<LatLng> localizacoes;
    static ArrayAdapter arrayAdapter;


    private ListView listaTrips;
    private String[] permissoes = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Verificando e pegando as permisões necessarias
        PermissionUtils.validate(this, 0, permissoes);



        lugares = new ArrayList<>();
        lugares.add("Selecione seu local favorito");

        localizacoes = new ArrayList<>();
        localizacoes.add(new LatLng(0, 0));

        //Vinculando componentes
        listaTrips = (ListView) findViewById(R.id.list_trip);
        //Criando adapter padrão android
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lugares);
        //setando adapter
        listaTrips.setAdapter(arrayAdapter);

        //Evento de clique em um item da lista
        listaTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent itMap = new Intent(MainActivity.this, MapaActivity.class);
                itMap.putExtra("infoLocalizacao",position );
                startActivity(itMap);

            }
        });
        //Fim do Oncreate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int resultado : grantResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertAndFinish();
            }

        }
        // Se chegou aqui, esta OK
    }
    // Mensagem soliciando
    private void alertAndFinish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name)
            .setMessage("Para utilizar este aplicativo, é necessario aceitar as permissões");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Verificando e pegando as permisões necessarias
                PermissionUtils.validate(MainActivity.this, 0, permissoes);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
