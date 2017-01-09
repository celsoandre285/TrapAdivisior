package view.celso.com.br.tripadvisor;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;


/**
 * Created by celso on 09/01/2017.
 */

public class PermissionUtils {

    public static Boolean validate(Activity activity, int requestCode, String... permissions ){

        ArrayList<String> list = new ArrayList<String>();
        //Checando as permissões
        for(String permission: permissions){
            //Validando permissões
            Boolean ok = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            if(! ok){
                list.add(permission);
            }
        }
        if(list.isEmpty()){
            //estiver tudo ok!
            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        ActivityCompat.requestPermissions(activity, newPermissions, 1);

        return false;

    }
}
