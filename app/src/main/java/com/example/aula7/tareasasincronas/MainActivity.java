package com.example.aula7.tareasasincronas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.id_pb_loaddata);
        textView = (TextView) findViewById(R.id.id_tv_data);
    }

    //Metodo para validar estado de la red
    public Boolean isOnLine(){
        // OBTENER el Servicio de la conectividad en android
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // Obtener la informacion del estado de la red
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //Verifica el estado de la red
        if (networkInfo != null )
        {
            return  true;
        } else {
            return  false;
        }
    }

    //Evento para el boton pricipal
    public  void loadData(View view){
        if(isOnLine()){
            MyTask myTask = new MyTask();
            myTask.execute("https://jsonplaceholder.typicode.com/posts");

        }else{
            Toast.makeText(this, "no hay conexcion", Toast.LENGTH_SHORT).show();

        }

    }

    //*****************************************************************


    //Mètodo para procesar los datos
    public void processData(String s){
        textView.setText("Numero:" + s);
        textView.setTextSize(Integer.parseInt(s                                                          ));
    }
    //Clase para crear una tarea asincrona

    public class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 1 ;  i <= 50 ; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                publishProgress(String.valueOf(i));

            }
            return "fin";
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            processData(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
        }


    }


}
