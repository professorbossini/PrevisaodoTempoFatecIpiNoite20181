package br.com.bossini.previsaodotempofatecipinoite20181;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText locationEditText;
    private ImageView conditionImageView;
    private TextView dayTextView,
            lowTextView, highTextView,humidityTextView;
    private ListView weatherListView;
    private List <Previsao> previsoes;
    private ArrayAdapter <Previsao> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        locationEditText = findViewById(R.id.locationEditText);
        conditionImageView = findViewById(R.id.conditionImageView);
        dayTextView = findViewById(R.id.dayTextView);
        lowTextView = findViewById(R.id.lowTextView);
        highTextView = findViewById(R.id.highTextView);
        humidityTextView = findViewById(R.id.humidityTextView);

        weatherListView = findViewById(R.id.weatherListView);
        previsoes = new LinkedList<>();
        adapter = new ArrayAdapter<Previsao>(this, -1, previsoes){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater)
                            MainActivity
                                    .this.
                                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate
                        (R.layout.list_item, parent, false);
                return null;
            }
        };
        weatherListView.setAdapter(adapter);


        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cidade = locationEditText.
                        getEditableText().toString();

                StringBuilder sb = new StringBuilder ("");
                sb.append(getString(R.string.web_service_url));
                sb.append(cidade);
                sb.append("&appid=");
                sb.append(getString(R.string.api_key));
                sb.append("&units=metric");
                String url = sb.toString();
                new ConsomeWSPrevisaoDoTempo().execute(url);

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request =
                                new Request.Builder()
                                        .url(url)
                                        .build();
                        Response response = null;
                        try {
                            response = client.newCall(request).execute();
                            final String resultado = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,
                                            resultado,
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }).start();*/
            }

        });
    }

    private class ConsomeWSPrevisaoDoTempo extends
                                    AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(url[0])
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                String resultado = response.body().string();
                return resultado;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resultado) {
            try {
                JSONObject previsao = new JSONObject(resultado);
                JSONArray list = previsao.getJSONArray("list");
                for (int i = 0; i < list.length(); i++){
                    JSONObject dia = list.getJSONObject(i);
                    long dt = dia.getLong("dt");
                    JSONObject temp = dia.getJSONObject("temp");
                    double min = temp.getDouble("min");
                    double max = temp.getDouble("max");
                    int humidity = dia.getInt("humidity");
                    JSONArray weather = dia.getJSONArray("weather");
                    JSONObject detalhes = weather.getJSONObject(0);
                    String description = detalhes.getString("description");
                    String icon = detalhes.getString("icon");
                    Previsao p =
                            new Previsao (dt, min, max, humidity,
                                        description, icon);
                    previsoes.add(p);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
