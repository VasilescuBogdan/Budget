package ace.ucv.budget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import ace.ucv.budget.workers.BudgetCalculator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchBudgetSumThread();
    }

    private void launchBudgetSumThread () {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView budgetTextView = findViewById(R.id.budget_text);

        BudgetCalculator budgetCalculator = new BudgetCalculator(db, budgetTextView, this);
        budgetCalculator.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();

        launchBudgetSumThread();
    }

    public void launchExpenseActivity(View view) {
        Intent intent = new Intent(this, ExpenseActivity.class);
        startActivity(intent);
    }

    public void launchEarningActivity(View view) {
        Intent intent = new Intent(this, EarningActivity.class);
        startActivity(intent);
    }

    public void launchItemListActivity(View view) {
        Intent intent = new Intent(this, ItemListActivity.class);
        startActivity(intent);
    }

}