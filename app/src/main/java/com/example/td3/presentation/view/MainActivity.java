package com.example.td3.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.td3.R;
import com.example.td3.data.cryptoAPI;
import com.example.td3.presentation.model.Coin;
import com.example.td3.presentation.model.CryptoApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://api.coinranking.com/";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    makeApiCall();
    }

    private void showList(List<Coin> coinList) {
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        // define an adapter
        mAdapter = new ListAdapter(coinList);
        recyclerView.setAdapter(mAdapter);

    }


    private void makeApiCall(){


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        cryptoAPI cryptoAPI = retrofit.create(cryptoAPI.class);

        Call<CryptoApiResponse> call = cryptoAPI.getCoinResponse();
        call.enqueue(new Callback<CryptoApiResponse>() {
            @Override
            public void onResponse(Call<CryptoApiResponse> call, Response<CryptoApiResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Coin> coinList = response.body().getData().getCoins();
                    showList(coinList);
                }
                else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<CryptoApiResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "Erreur de l'API", Toast.LENGTH_SHORT).show();
    }
}