package com.example.td3.presentation.view;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.td3.Constant;
import com.example.td3.Injection;
import com.example.td3.R;
import com.example.td3.presentation.model.Coin;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView txtDetail;
    private TextView txtdescription;
    private TextView txtRank;
    private TextView txtPrice;
    private TextView txtCoinUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtDetail = findViewById(R.id.detail_txt);
        txtdescription = findViewById(R.id.description_txt);
        txtPrice = findViewById(R.id.coinPrice);
        txtRank = findViewById(R.id.coinRank);
        txtCoinUrl = findViewById(R.id.coinUrl);

        Intent intent = getIntent();
        String coinJson = intent.getStringExtra(Constant.KEY_COIN);
        Coin coin = Injection.getGson().fromJson(coinJson, Coin.class);
        ImageView coinLogo = findViewById(R.id.coinLogo);
        //Etant donné que l'API que j'ai choisi ne contient que des logos en format SVG que je n'ai pas réussi à afficher sous android, j'ai contourné le problème en utilisant un autre site et une concaténation.
        String url = "https://raw.githubusercontent.com/spothq/cryptocurrency-icons/master/128/color/" + coin.getSymbol().toLowerCase() + ".png";
        Picasso.with(this).load(url).into(coinLogo);
        showDetail(coin);
    }

    private void showDetail(Coin coin) {
        txtDetail.setText(coin.getName());
        txtdescription.setText(coin.getDescription());
        txtPrice.setText("Un " +coin.getSymbol()+ " vaut: " + coin.getPrice() +" $");
        txtRank.setText("Rang de la cryptomonnaie: " + coin.getRank());
        txtCoinUrl.setText(Html.fromHtml("Site officiel : " + coin.getWebsiteUrl()));

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(Color.parseColor(coin.getColor()));
            getWindow().setStatusBarColor(Color.parseColor(coin.getColor()));
        }
    }
}
