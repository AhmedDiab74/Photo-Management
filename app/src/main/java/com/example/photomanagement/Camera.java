package com.example.photomanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.logging.LoggingMXBean;

public class Camera extends AppCompatActivity {
    private static final int PERMISSION_CODE = 1234;
    private static final int CAPTURE_CODE = 1001;
    Button capture_pic;
    ImageView img ;
    Uri img_Uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        capture_pic = (Button) findViewById(R.id.capture_pic);
        img = (ImageView) findViewById(R.id.img);
        capture_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                                    PackageManager.PERMISSION_DENIED){
                        String [] permission = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else {
                         OpenCamera();
                    }
                } else {
                    OpenCamera();
                }
            }
        });
    }

    private void OpenCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new image");
        values.put(MediaStore.Images.Media.TITLE,"From the Camera");
        img_Uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, img_Uri);
        startActivityForResult(CamIntent,CAPTURE_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if (grantResults.length>0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
                    OpenCamera();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==RESULT_OK){
            img.setImageURI(img_Uri);
        }
    }
    public void open(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

    }
}