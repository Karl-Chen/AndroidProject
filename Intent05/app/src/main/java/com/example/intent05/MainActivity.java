package com.example.intent05;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCallerLauncher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

//    public static final int GET_CONTACT = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button btnBrowserSearch = findViewById(R.id.btn_search_browser);
        Button btnCommunitaionSearch = findViewById(R.id.btn_search_communication);

        btnBrowserSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = findViewById(R.id.edit_keyword);
                Intent i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY, editSearch.getText().toString());
                startActivity(i);
            }
        });
        btnCommunitaionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearch = findViewById(R.id.edit_keyword);
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(ContactsContract.Contacts.CONTENT_TYPE);
                //startActivityForResult(i, GET_CONTACT);
                launcher.launch(i);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == GET_CONTACT) {
//            if (resultCode == RESULT_OK) {
//                String strConact = data.getData().toString();
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(strConact));
//                Log.d("E", strConact);
//                Toast.makeText(MainActivity.this, strConact, Toast.LENGTH_LONG).show();
//                startActivity(i);
//            }
//        }
//    }

    ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == RESULT_OK) {
                        String strContact = o.getData().getData().toString();   //第一個getData是取得Intent，第二個getData才是真的從Intent取得Data
                        Toast.makeText(MainActivity.this, strContact, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(strContact));
                        startActivity(i);

                    }
                }
            });
}