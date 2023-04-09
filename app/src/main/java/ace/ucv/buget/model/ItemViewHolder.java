package ace.ucv.buget.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ace.ucv.buget.R;

public class ItemViewHolder extends RecyclerView.ViewHolder{
    TextView textViewName;
    TextView textViewAmount;
    TextView textViewDate;
    TextView textViewExpenseLocation;
    TextView textViewExpenseCategory;
    //ImageView imageViewReceipt; TODO IMPLEMENT CAMERA


    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewName = itemView.findViewById(R.id.textViewItemName);
        textViewAmount = itemView.findViewById(R.id.textViewItemAmount);
        textViewDate = itemView.findViewById(R.id.textViewItemDate);
        textViewExpenseLocation = itemView.findViewById(R.id.textViewExpenseLocation);
        textViewExpenseCategory = itemView.findViewById(R.id.textViewExpenseCategory);
    }
}
