package view.celso.com.br.tripadvisor;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.getIntent;

/**
 * Created by celso on 09/01/2017.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, LocationListener {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private String provider;
    private int localizacao = -1;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //provider = locationManager.getBestProvider(new Criteria(), false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);


        localizacao = getActivity().getIntent().getExtras().getInt("infoLocalizacao", -1);
        //Log.i("LogX", Integer.toString(intent.getIntExtra("infoLocalizacao", -1)));
        //localizacao = intent.getIntExtra("infoLocalizacao", -1);

        if (localizacao != -1 && localizacao != 0) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.removeUpdates(this);
            //Adicionando marcador
            mMap.addMarker(new MarkerOptions()
                    .position(MainActivity.localizacoes.get(localizacao))
                    .title(MainActivity.lugares.get(localizacao))
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MainActivity.localizacoes.get(localizacao), 10));
        }else{
            // fazendo request da localização a cada 400miliSegundos
            locationManager.requestLocationUpdates(provider, 400, 1 , this);
        }

        // evento de click no title do marker
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //Encaminha para tela de Detalhes
                Intent itDetalhes = new Intent(getContext(), TelaLugar.class);
                //enviar o nome via intent
                itDetalhes.putExtra("meuLugar", marker.getTitle());
                startActivity(itDetalhes);
            }
        });



    }

    @Override
    public void onMapLongClick(LatLng point) {
        //para pegar Latitude e Longitude

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        String marcador = new Date().toString();
        try{
            List<Address> listaLocais = geocoder.getFromLocation(point.latitude,point.longitude , 1);
            if (listaLocais != null && listaLocais.size() > 0){
                marcador = listaLocais.get(0).getAddressLine(0);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }

        MainActivity.localizacoes.add(point);
        MainActivity.lugares.add(marcador);
        MainActivity.arrayAdapter.notifyDataSetChanged();
        //Adicionando marcador
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .title(marcador)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        );

    }



    @Override
    public void onLocationChanged(Location localizacaoUsuario) {
        //Pega a atual posição do usuario e suas Configurações
        if(localizacao ==1 || localizacao ==0) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(localizacaoUsuario.getLatitude(), localizacaoUsuario.getLongitude()))
                    .title("Estou Aqui!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            );

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(localizacaoUsuario.getLatitude(), localizacaoUsuario.getLongitude()), 13));
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
