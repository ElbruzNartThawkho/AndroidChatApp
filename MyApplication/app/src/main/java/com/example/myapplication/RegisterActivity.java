package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText eposta,sifre;
    private Button kayitol;
    private FirebaseAuth kontrol;
    public void dahil(){
        kontrol=FirebaseAuth.getInstance();
        eposta=(EditText)findViewById(R.id.emailregister);
        sifre=(EditText)findViewById(R.id.passwordregister);
        kayitol=(Button)findViewById(R.id.sign_up);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dahil();
        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hesapolustur();
            }
        });
    }

    private void hesapolustur() {
        String posta = eposta.getText().toString();
        String password = sifre.getText().toString();
        if(TextUtils.isEmpty(posta)){
            Toast.makeText(RegisterActivity.this,"E-posta kısmı boş bırakılamaz!",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this,"Şifre kısmı boş bırakılamaz!",Toast.LENGTH_LONG).show();
        }
        else{
            kontrol.createUserWithEmailAndPassword(posta,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Hesabınız Oluşturuldu.",Toast.LENGTH_LONG).show();
                        Intent giris=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(giris);
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Bir hata oluştu !",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

}