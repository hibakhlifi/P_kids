package com.iset.P_kids;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iset.P_kids.Retro.ApiUtil;
import com.iset.P_kids.Retro.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inscrire extends AppCompatActivity {
EditText Nom,Prénom,Email,Mot_de_passe,mail,pass,nom,prenom;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    EditText login;
    EditText  motdepasse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscrire);
        //récupération des données saisi dans les champs
        Nom = findViewById(R.id.Nom);
        Prénom = findViewById(R.id.Prénom);
        Email = findViewById(R.id.Email);
        Mot_de_passe = findViewById(R.id.Mot_de_passe);
        findViewById(R.id.Inscrire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = Nom.getText().toString();
                String prenom = Prénom.getText().toString();
                String email = Email.getText().toString();
                String motdepasse = Mot_de_passe.getText().toString();


                User user = new User();
                user.setNom(nom);
                user.setPrénom(prenom);
                user.setEmail(email);
                //instance de classe ApiUtil pour créer un nouveau utilisateur
                ApiUtil.getServiceClass().addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User userResponse = response.body();
                        Toast.makeText(Inscrire.this, "User id: " + userResponse.getId() +
                                        "\nUser name: " + userResponse.getNom() + " " + userResponse.getPrénom() +
                                        "\nUser mail: " + userResponse.getEmail(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Inscrire.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        //Instance de firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
//récupération des informations saisie dans les champs
         mail = findViewById(R.id.Email);
         pass = findViewById(R.id.Mot_de_passe);

        findViewById(R.id.Inscrire).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();

            }
        });



    }
    private void updateUI(FirebaseUser user)
    {
        if (user != null)
        {
            Toast.makeText(Inscrire.this, "ajout avec succés", Toast.LENGTH_LONG).show();
        }
    }

    public void test()
    //un test sur la création d'un nouveau utilisateur dans la base de données
    {
        String login = mail.getText().toString();
        String motdepasse=pass.getText().toString();


        mAuth.createUserWithEmailAndPassword(login, motdepasse)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Inscrire", "createUserWithEmail:success");
                            Toast.makeText(Inscrire.this, "Authentication succ.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Sign_up", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Inscrire.this, "Authentication craché.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

}
