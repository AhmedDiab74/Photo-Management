package com.example.photomanagement;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.os.Environment.MEDIA_MOUNTED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int req = 100;
    RecyclerView recyclerView ;
   public static ImageView iv ,Photo;
    public static ImageView iv2 ;
    public static ArrayList <String> images;
    public static ArrayList <String> vv2;
   public static String photo;
    galaryAdapter GalaryAdapter;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        Photo = (ImageView) findViewById(R.id.photo);
        images = new ArrayList<>();
        vv2 = new ArrayList<>();

        GalaryAdapter = new galaryAdapter(this , images);
        manager = new GridLayoutManager(this,3);
        recyclerView.setAdapter(GalaryAdapter);
        recyclerView.setLayoutManager(manager);
        checkPermission();
    }
    public void checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        if (result== PackageManager.PERMISSION_GRANTED){
                loadimages();
        } else {
            ActivityCompat.requestPermissions(this ,new  String[]{READ_EXTERNAL_STORAGE},req);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 ){
            boolean acc = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (acc){
                loadimages();
            } else {
                Toast.makeText(this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadimages() {
        boolean SDcard = Environment.getExternalStorageState().equals(MEDIA_MOUNTED);
        if (SDcard){
            final  String [] col={MediaStore.Images.Media.DATA,MediaStore.Images.Media._ID};
            final String order =MediaStore.Images.Media.DATE_TAKEN + " DESC";
            Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,col,null ,null , order);
            int count  = cursor.getCount();
            for (int i = 0 ; i < count ; i++){
                cursor.moveToPosition(i);
                int columnIndex =cursor.getColumnIndex((MediaStore.Images.Media.DATA));
                images.add(cursor.getString(columnIndex));
                photo = images.get(columnIndex);
        }
            recyclerView.getAdapter().notifyDataSetChanged();
            cursor.close();
        }
    }
}