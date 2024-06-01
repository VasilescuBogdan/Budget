package ace.ucv.budget.model;

import ace.ucv.budget.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView textViewName;
    TextView textViewAmount;
    TextView textViewDate;
    TextView textViewExpenseLocation;
    TextView textViewExpenseCategory;
    ImageView imageViewReceipt;


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewItemName);
        textViewAmount = itemView.findViewById(R.id.textViewItemAmount);
        textViewDate = itemView.findViewById(R.id.textViewItemDate);
        textViewExpenseLocation = itemView.findViewById(R.id.textViewExpenseLocation);
        textViewExpenseCategory = itemView.findViewById(R.id.textViewExpenseCategory);
        imageViewReceipt = itemView.findViewById(R.id.imageViewReceipt);
    }
}
