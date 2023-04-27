package com.example.photomanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class glary extends AppCompatActivity  {
    CardView card ;
    public static ImageView Photo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photogalary);
        card = (CardView) findViewById(R.id.card);
        Photo = (ImageView) findViewById(R.id.photo);
    }
}
