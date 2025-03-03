package mx.tecnm.chi2.almacenamientointerno;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText editText_producto;
    EditText editText_listado;
    Button button_guardar;
    Button button_verListado;
    Button button_borrarListado;

    //Objetos para gestion de archivos
    FileOutputStream fileOutputStream;
    FileInputStream fileInputStream;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editText_producto = findViewById(R.id.editText_producto);
        editText_listado = findViewById(R.id.editText_listado);
        button_guardar = findViewById(R.id.button_guardar);
        button_verListado = findViewById(R.id.button_verListado);
        button_borrarListado = findViewById(R.id.button_borrarListado);

        //Configuracion del boton guardar
        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String producto = editText_producto.getText().toString() + "\n";

                try {
                    fileOutputStream = openFileOutput("productos",
                            Context.MODE_APPEND);
                    //guardar el producto
                    fileOutputStream.write(producto.getBytes());
                    //cerrar archivo
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                editText_producto.setText("");
                actualizarListado();

            }
        }); //botton de guardar

        button_borrarListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borrar productos
                try {
                    fileOutputStream = openFileOutput("productos",Context.MODE_PRIVATE);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                actualizarListado();
            }
        });
    }

    public void actualizarListado(){
        //abrir archivo
        try {
            fileInputStream = openFileInput("productos");
            StringBuffer stringBuffer = new StringBuffer();
            int i;
            while((i = fileInputStream.read()) != -1){
                stringBuffer.append((char) i);
            }
            fileInputStream.close();

            //mostrar productos
            editText_listado.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}