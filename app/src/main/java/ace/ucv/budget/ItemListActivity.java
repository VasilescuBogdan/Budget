package ace.ucv.budget;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ace.ucv.budget.model.ItemAdapter;
import ace.ucv.budget.workers.BudgetCalculator;

public class ItemListActivity extends AppCompatActivity {

    public static final String TAG = ItemListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        List<DocumentSnapshot> mergedList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView budgetTextView = findViewById(R.id.budget_text);

        BudgetCalculator budgetCalculator = new BudgetCalculator(db, budgetTextView, this);
        budgetCalculator.execute();

        db.collection("Expense")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    mergedList.addAll(querySnapshot.getDocuments());

                    db.collection("Earning")
                            .get()
                            .addOnSuccessListener(querySnapshot1 -> {
                                mergedList.addAll(querySnapshot1.getDocuments());

                                RecyclerView recyclerView = findViewById(R.id.earning_expense_list);
                                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                                ItemAdapter adapter = new ItemAdapter(mergedList);
                                recyclerView.setAdapter(adapter);
                            })
                            .addOnFailureListener(e -> Log.d(TAG, "Error getting documents from Earning"));
                })
                .addOnFailureListener(e -> Log.d(TAG, "Error getting documents from Expense"));
    }
}