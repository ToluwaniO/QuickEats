package toluog.quickeats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.header_photo)
    ImageView header;
    @BindView(R.id.email)
    TextView emailView;
    @BindView(R.id.password)
    TextView passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void logIn(View view) {
        if(validate()) {
            openMain();
        }
    }

    public void signUp(View view) {
        openMain();
    }

    private void openMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        boolean isValid = true;
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if(TextUtils.isEmpty(email)) {
            emailView.setError("Field cannot be empty");
            isValid = false;
        }
        if(TextUtils.isEmpty(password)) {
            passwordView.setError("Field cannot be empty");
            isValid = false;
        }
        return isValid;
    }
}
