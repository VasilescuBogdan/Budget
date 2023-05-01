package ace.ucv.buget.workers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;

import ace.ucv.buget.R;

public class BudgetCalculator extends AsyncTask<Void, Void, Float> {

    private final FirebaseFirestore db;
    private final WeakReference<TextView> sumTextViewRef;
    private final WeakReference<Context> contextRef;


    public BudgetCalculator(FirebaseFirestore db, TextView sumTextView, Context context) {
        this.db = db;
        this.sumTextViewRef = new WeakReference<>(sumTextView);
        this.contextRef = new WeakReference<>(context);
    }

    private Float getSumFromCollection (String collectionName) {
        float sum = 0;
        try {
            Query query = db.collection(collectionName);
            QuerySnapshot querySnapshot = Tasks.await(query.get());
            for (DocumentSnapshot documentSnapshot: querySnapshot.getDocuments()) {
                sum += documentSnapshot.getDouble("amount").floatValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    @Override
    protected Float doInBackground(Void... voids) {
        return getSumFromCollection("Earning") - getSumFromCollection("Expense");
    }

    @Override
    protected void onPostExecute(Float sum) {
        Context context = contextRef.get();
        TextView sumTextView = sumTextViewRef.get();
        String sumText = context.getString(R.string.budget_text, sum);
        if(sumTextView != null) {
            sumTextView.post(new Runnable() {
                @Override
                public void run() {
                    sumTextView.setText(sumText);
                }
            });
        }
    }
}
