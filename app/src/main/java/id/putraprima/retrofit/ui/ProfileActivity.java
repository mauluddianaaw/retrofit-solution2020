package id.putraprima.retrofit.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.UserInfo;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    public Context context;
    TextView propilId, propilName,propilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = getApplicationContext();
        getMe();

        propilId = findViewById(R.id.idText);
        propilName = findViewById(R.id.nameText);
        propilEmail = findViewById(R.id.emailText);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            getMe();
            return;
        }else if (requestCode == 2 && resultCode == 2){
            getMe();
            return;
        }
    }

    private void getMe() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Toast.makeText(context, preference.getString("token",null), Toast.LENGTH_SHORT).show();

        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<UserInfo>> call = service.me("Bearer "+preference.getString("token",null));
        call.enqueue(new Callback<Envelope<UserInfo>>() {
            @Override
            public void onResponse(Call<Envelope<UserInfo>> call, Response<Envelope<UserInfo>> response) {
                //Toast.makeText(ProfileActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()){
                propilId.setText(Integer.toString(response.body().getData().getId()));
                propilName.setText(response.body().getData().getName());
                propilEmail.setText(response.body().getData().getEmail());
                }else{
                    ApiError error = ErrorUtils.parseError(response);
                    if (error.getError() != null){
                        Toast.makeText(ProfileActivity.this, error.getError().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                }

            @Override
            public void onFailure(Call<Envelope<UserInfo>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleUpdateProfile(View view) {
        Intent intent = new Intent(this, UpdateProfilActivity.class);
        startActivityForResult(intent, 1);
    }

    public void handleUpdatePassword(View view) {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        startActivityForResult(intent, 2);
    }
    public void handleRecipe(View view) {
        Intent intent = new Intent(this, RecipeActivity.class);
        startActivity(intent);
    }

    public void handleUpload(View view) {
        Intent intent = new Intent(this, UploadActivity.class);
        startActivity(intent);

    }
}
