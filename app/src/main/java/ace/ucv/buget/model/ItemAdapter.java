package ace.ucv.buget.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.chrono.Chronology;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import ace.ucv.buget.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private List<DocumentSnapshot> documentList;

    public ItemAdapter(List<DocumentSnapshot> documentList) {
        this.documentList = documentList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DocumentSnapshot document = documentList.get(position);
        String collectionName = document.getReference().getParent().getId();

        //Bind the data from the document to the ItemViewHolder
        holder.textViewName.setText(document.getString("name"));
        holder.textViewAmount.setText(String.valueOf(document.getLong("amount")));
        holder.textViewDate.setText(document.getString("date"));
        if(collectionName.equals("Expense")){
            holder.textViewExpenseLocation.setText(document.getString("location"));
            holder.textViewExpenseCategory.setText(document.getString("category"));
        }
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }
}
