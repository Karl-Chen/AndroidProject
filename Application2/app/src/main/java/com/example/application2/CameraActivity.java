package com.example.application2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultCallerLauncher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CameraActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == RESULT_OK)
                    {
                        Intent i = o.getData();
                        Bundle b = i.getExtras();
                        if (b != null) {
                            Bitmap bitmap = (Bitmap)b.get("data");
                            imgPhoto.setImageBitmap(bitmap);
                        }

                    }
                }
            });
    private static int REQUEST_IMAGE = 1003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_camera);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        imgPhoto = findViewById(R.id.img_photo);
        Button btnStarCamera = findViewById(R.id.button_start_camera);
        btnStarCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(i, REQUEST_IMAGE);
                launcher.launch(i);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK)
//        {
//            Bitmap bitmapImage = (Bitmap) data.getExtras().get("data");
//            imgPhoto.setImageBitmap(bitmapImage);
//        }
//    }
}