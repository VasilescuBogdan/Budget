package ace.ucv.budget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ace.ucv.budget.model.Earning;

public class EarningActivity extends AppCompatActivity {

    private static final String TAG = EarningActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
    }

    public void saveEarning(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //get values from fields
        EditText nameText = findViewById(R.id.name_earning_plainText);
        EditText amountNumber = findViewById(R.id.amount_number_decimal);

        Earning earning = new Earning();
        earning.setName(nameText.getText().toString());
        earning.setAmount(Float.parseFloat(amountNumber.getText().toString()));
        earning.setDate(LocalDateTime.now().format(formatter));

        db.collection("Earning")
                .add(earning)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Document successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Document was not written");
                    }
                });

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}