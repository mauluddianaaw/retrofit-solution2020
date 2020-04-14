package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import id.putraprima.retrofit.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class loadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loading);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), RecipeActivity.class));
                finish();
            }
        }, 3000L);
    }
}
