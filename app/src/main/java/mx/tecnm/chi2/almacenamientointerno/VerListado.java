package mx.tecnm.chi2.almacenamientointerno;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VerListado extends AppCompatActivity {

    ListView listView_listado;
    String[] productos;
    FileInputStream fileInputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_listado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //vincular objetos
        listView_listado = findViewById(R.id.listView_listado);
        obtenerProductos();
        //mostrar productos en elementos listView
        //guardar arreglo de productos en una lista java
        final List<String> lista_productos  = new ArrayList<String>(Arrays.asList(productos));

        //crear adaptador de datos
        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista_productos);

        //vincular adaptador de datos con listView
        listView_listado.setAdapter(adaptador);
    }//onCreate

    public void obtenerProductos(){
        try {
            fileInputStream = openFileInput("productos");
            StringBuffer stringBuffer = new StringBuffer();
            int i;
            while((i = fileInputStream.read()) != -1){
                stringBuffer.append((char) i);
            }
            fileInputStream.close();

            productos = stringBuffer.toString().split("\n");


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}//verListado