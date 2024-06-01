package ace.ucv.budget.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;

public class MonthlySumCalculator extends AsyncTask<Void, Void, Float> {

    private final FirebaseFirestore db;
    private final WeakReference<Context> contextRef;


    public MonthlySumCalculator(FirebaseFirestore db, Context context) {
        this.db = db;
        this.contextRef = new WeakReference<>(context);
    }

    private Float getSumFromCollection (String collectionName) {
        float sum = 0;

        // Get the current month
        Calendar calendar = Calendar.getInstance();
        String currentMonth = String.format(Locale.US, "%04d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);

        try {
            Query query = db.collection(collectionName).whereGreaterThanOrEqualTo("date", currentMonth).whereLessThan("date", currentMonth + "-31");
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
        if(sum < 0) {
            sendNotification();
        }
    }

    private void sendNotification() {
        Context context = contextRef.get();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel("my_channel_id", "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "my_channel_id")
                .setSmallIcon(com.google.firebase.firestore.R.drawable.common_google_signin_btn_icon_dark)
                .setContentTitle("Monthly expenses exceeded!")
                .setContentText("Try to recover your loses and reduce your expenses next month. OK?")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());

    }
}
