
package com.iset.P_kids;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements
        TextWatcher, CompoundButton.OnCheckedChangeListener {
    Button signin;
    String messageerreur;
    String messagesucces;
    private FirebaseAuth firebaseAuth;
    EditText etemail, etpassword;
    CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_EMAIL = "username";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etemail = findViewById(R.id.email);
        etpassword = findViewById(R.id.motdepasse);
        signin = findViewById(R.id.button1);
        //récupération du message  existe dans le fichier string
        messageerreur = getString(R.string.messageerreur);
        messagesucces = getString(R.string.messagesucces);
        //instance du firebase
        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.motdepasse);
        rem_userpass = (CheckBox) findViewById(R.id.checkBox);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        etemail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
        etpassword.setText(sharedPreferences.getString(KEY_PASS, ""));

        etemail.addTextChangedListener(this);
        etpassword.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);

        //gérer le click sur le button SignIn
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signInWithEmailAndPassword(etemail.getText().toString(), etpassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //si l' utilisateur est existe et l'email et le mot de passe
                                // sont correctes alors  l'nterface de dashboard sera ouverte
                                if (task.isSuccessful()) {
                                    Intent newActivity = new Intent(MainActivity.this, MenuActivity.class);
                                    startActivity(newActivity);
                                    //l'affichage d'un toast indique le succes de connection
                                    Toast.makeText(MainActivity.this, "Authentication suuc.",
                                            Toast.LENGTH_SHORT).show();

                                }
                                //si il y'a une erreur alors un Toast sera etre affiché
                                else {
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });
        //passage a l'interface de création de compte
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Inscrire.class);
                startActivity(intent);
            }
        });

//passage au interface de mot de passe oublier
        findViewById(R.id.oublier).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Forget_password.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            editor.putString(KEY_EMAIL, etemail.getText().toString().trim());
            editor.putString(KEY_PASS, etpassword.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_EMAIL);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }


    }
}


