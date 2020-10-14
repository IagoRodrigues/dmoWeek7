package br.edu.dmos5.github_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.github_dmos5.R;
import br.edu.dmos5.github_dmos5.api.RetrofitService;
import br.edu.dmos5.github_dmos5.model.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.edu.dmos5.github_dmos5.contants.Constants.URL_GITHUB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener   {

    //Referência para o elemento de RecyclerView
    private RecyclerView recyclerView;

    //Fonte de dados, essa lista possue os dados que são apresentados
    //na tela dos dispositivo.
    private List<Repository> mRepositorioist;

    private Adapter adapter;

    //Campos usados na view
    private EditText editText_username;
    private Button button_buscar;
    private ImageView imageView_vazio;

    private static final int REQUEST_PERMISSION = 64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recupera a referência do elemento no layout
        recyclerView = findViewById(R.id.recyclerView);

        //Recupera a referência do elemento no layout
        editText_username   = findViewById(R.id.editText_username);
        button_buscar     = findViewById(R.id.button_buscar);
        imageView_vazio    = findViewById(R.id.imageView_vazio);

        //Para o recycler parecer um ListView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new Adapter(mRepositorioist);
        recyclerView.setAdapter(adapter);

        mRepositorioist = new ArrayList();

        button_buscar.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView_vazio.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {

        String username;

        if(v == button_buscar){
            if ( permissionVerify() ) {
                username = editText_username.getText().toString(); // get username
                if (!username.isEmpty()) {
                    searchRepositorio(username);
                }
                else {
                   imageView_vazio.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                requestPermission();
            }
        }

    }

    private boolean permissionVerify() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void searchRepositorio(String username) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL_GITHUB).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<List<Repository>> repos = service.getRepository(username);

        repos.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful()) {

                    //Recebo os repositórios da API
                    List<Repository> repos = response.body();

                    //Consigo escrevê-los no prompt
                    for(Repository rep : repos){
                        System.out.println(rep.getName());
                    }

                    //Passao para o adapter fazer as alterações
                    adapter.update(repos, imageView_vazio, recyclerView);


                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.request_falied, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermission() {
        final Activity activity = this;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(getApplicationContext())
                    .setMessage(R.string.permission_allow)
                    .setPositiveButton(R.string.permission_allow, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{
                                            Manifest.permission.INTERNET
                                    },
                                    REQUEST_PERMISSION
                            );
                        }
                    })
                    .setNegativeButton(R.string.permission_deny, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{
                            Manifest.permission.INTERNET
                    },
                    REQUEST_PERMISSION
            );
        }
    }
}