package view.celso.com.br.tripadvisor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
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

        String[] test = {"test01", "test02"};
        listaTrips = (ListView) findViewById(R.id.list_trip);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
        listaTrips.setAdapter(adapter);

        listaTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));

            }
        });


    }
}
