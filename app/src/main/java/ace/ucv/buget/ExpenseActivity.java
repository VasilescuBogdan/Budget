package ace.ucv.buget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;

import ace.ucv.buget.model.Expense;

public class ExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        String[] items = {"Bill", "Shopping", "Personal", "Imprumut", "Gift"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        Spinner spinner = findViewById(R.id.category_dropdown);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Category"); // Set the prompt text
    }


    public void saveExpense(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Expense expense = new Expense();

        EditText nameText = findViewById(R.id.name_expense_plainText);
        EditText amountNumber = findViewById(R.id.amount_expense_number_decimal);
        EditText locationText = findViewById(R.id.location_plainText);
        Spinner categoryDropdown = findViewById(R.id.category_dropdown);

        expense.setName(nameText.getText().toString());
        expense.setAmount(Float.parseFloat(amountNumber.getText().toString()));
        expense.setDate(LocalDateTime.now());
        expense.setLocation(locationText.getText().toString());
        expense.setCategory(categoryDropdown.getSelectedItem().toString());

        db.collection("Expense")
                .add(expense)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SUCCESS", "Document successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FAILURE", "Document was not written");
                    }
                });

        Intent intent = new Intent(this ,MainActivity.class);
        startActivity(intent);
    }

    public void addPhoto(View view) {
        // TODO use camera to create photo
    }
}