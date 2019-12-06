package com.iset.P_kids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button signin ;
    String messageerreur;
    String messagesucces;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
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


        email = findViewById(R.id.email);
        password = findViewById(R.id.motdepasse);

        signin = findViewById(R.id.button1);
        //récupération du message  existe dans le fichier string
        messageerreur = getString(R.string.messageerreur);
        messagesucces = getString(R.string.messagesucces);
        //instance du firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //gérer le click sur le button SignIn
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
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


    }

}