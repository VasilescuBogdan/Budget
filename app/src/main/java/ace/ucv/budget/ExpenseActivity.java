package ace.ucv.budget;

import ace.ucv.budget.model.Expense;
import ace.ucv.budget.workers.MonthlySumCalculator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExpenseActivity extends AppCompatActivity {

    private static final String TAG = ExpenseActivity.class.getSimpleName();
    private final ActivityResultLauncher<Intent> captureImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ImageView imageView = findViewById(R.id.expense_receipt_image);
                    imageView.setImageBitmap(imageBitmap);
                }
            });

    public void saveExpense(View view) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Expense expense = new Expense();

        EditText nameText = findViewById(R.id.name_expense_plainText);
        EditText amountNumber = findViewById(R.id.amount_expense_number_decimal);
        EditText locationText = findViewById(R.id.location_plainText);
        Spinner categoryDropdown = findViewById(R.id.category_dropdown);
        ImageView imageView = findViewById(R.id.expense_receipt_image);

        expense.setName(nameText.getText()
                                .toString());
        expense.setAmount(Float.parseFloat(amountNumber.getText()
                                                       .toString()));
        expense.setDate(LocalDateTime.now()
                                     .format(formatter));
        expense.setLocation(locationText.getText()
                                        .toString());
        expense.setCategory(categoryDropdown.getSelectedItem()
                                            .toString());

        Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        expense.setImage(encodeImage(imageBitmap));


        db.collection("Expense")
          .add(expense)
          .addOnSuccessListener(documentReference -> Log.d(TAG, "Document successfully written!"))
          .addOnFailureListener(e -> Log.d(TAG, "Document was not written"));

        MonthlySumCalculator monthlySumCalculator = new MonthlySumCalculator(db, this);
        monthlySumCalculator.execute();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            captureImageLauncher.launch(intent);
        }
    }

    public String encodeImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        String[] items = {"Bill", "Shopping", "Personal", "Imprumut", "Gift"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        Spinner spinner = findViewById(R.id.category_dropdown);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Category");
        // Set the prompt text
    }

}