package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.UpdatePasswordRequest;
import id.putraprima.retrofit.api.models.UpdatePasswordResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText mPasswordPasswordUpdateText;
    private EditText mEmailPasswordUpdateText;

    private UpdatePasswordRequest updatePasswordRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        mPasswordPasswordUpdateText = findViewById(R.id.passwordPasswordUpdateText);
        mEmailPasswordUpdateText = findViewById(R.id.emailPasswordUpdateText);
    }

    public void doUpdatePassword(){
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class, "Bearer " + preference.getString("token", null));

        Call<Envelope<UpdatePasswordResponse>> call = service.doUpdatePassword(updatePasswordRequest);
        call.enqueue(new Callback<Envelope<UpdatePasswordResponse>>() {

            @Override
            public void onResponse(retrofit2.Call<Envelope<UpdatePasswordResponse>> call, Response<Envelope<UpdatePasswordResponse>> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
                }
                else{
                    ApiError error = ErrorUtils.parseError(response);
                    if(error.getError().getPassword()!=null && error.getError().getConfirmPassword()!=null){
                        Toast.makeText(UpdatePasswordActivity.this, error.getError().getPassword().get(0) + " and " + error.getError().getConfirmPassword().get(0), Toast.LENGTH_SHORT).show();
                    }
                    else if(error.getError().getPassword()!=null){
                        for (int i=0; i<error.getError().getPassword().size(); i++){
                            Toast.makeText(UpdatePasswordActivity.this, error.getError().getPassword().get(i), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if(error.getError().getConfirmPassword()!=null){
                        Toast.makeText(UpdatePasswordActivity.this, error.getError().getConfirmPassword().get(0), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Envelope<UpdatePasswordResponse>> call, Throwable t) {
                Toast.makeText(UpdatePasswordActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handleUpdatePassword(View view) {
        String password = mPasswordPasswordUpdateText.getText().toString();
        String password_confirm = mPasswordPasswordUpdateText.getText().toString();
        updatePasswordRequest = new UpdatePasswordRequest(password, password_confirm);

            doUpdatePassword();
    }
}
