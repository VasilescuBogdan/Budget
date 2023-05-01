package ace.ucv.buget.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.Chronology;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ace.ucv.buget.ItemListActivity;
import ace.ucv.buget.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private static final int VIEW_TYPE_EARNING = 0;
    private static final int VIEW_TYPE_EXPENSE = 1;

    private final List<DocumentSnapshot> documentList;

    @Override
    public int getItemViewType(int position) {
        DocumentSnapshot document = documentList.get(position);
        if (document.getReference().getPath().startsWith("Earning")) {
            return VIEW_TYPE_EARNING;
        } else {
            return VIEW_TYPE_EXPENSE;
        }
    }

    public ItemAdapter(List<DocumentSnapshot> documentList) {
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_EARNING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        }

        return new ItemViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DocumentSnapshot document = documentList.get(position);
        String collectionName = document.getReference().getParent().getId();

        //Bind the data from the document to the ItemViewHolder
        holder.textViewName.setText(document.getString("name"));
        holder.textViewAmount.setText(String.format(Locale.getDefault(), "%.2f", document.getDouble("amount").floatValue()));
        holder.textViewDate.setText(document.getString("date"));
        if(collectionName.equals("Expense")){
            holder.textViewExpenseLocation.setText(document.getString("location"));
            holder.textViewExpenseCategory.setText(document.getString("category"));
            holder.imageViewReceipt.setImageBitmap(decodeImage(document.getString("image")));
        }
    }

    public Bitmap decodeImage(String imageURI) {
        byte[] decodedBytes = Base64.decode(imageURI, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
