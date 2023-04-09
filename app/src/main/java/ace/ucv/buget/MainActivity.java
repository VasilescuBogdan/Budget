package ace.ucv.buget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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