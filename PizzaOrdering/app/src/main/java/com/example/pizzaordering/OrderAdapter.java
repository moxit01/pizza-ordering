package com.example.pizzaordering;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzaordering.OrderHistory;
import com.example.pizzaordering.R;
import com.example.pizzaordering.OrderContract;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    private Cursor mCursor;
    private Context mContext;
    OrderHistory obj;

    public OrderAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        obj= (OrderHistory) context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.order_list, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        int total = mCursor.getInt(mCursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_TOTAL));
        String datetime = mCursor.getString(mCursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_DATE_TIME));
        final long id = mCursor.getLong(mCursor.getColumnIndexOrThrow(OrderContract.OrderEntry._ID));


        holder.total_tv.setText("Total: $"+total);
        holder.datetime_tv.setText(""+datetime);
        holder.orderid_tv.setText("ORDER ID: "+id);
        holder.itemView.setTag(id);


        //Delete order.
        holder.delimv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                obj.delete(id);
            }
        });

        holder.toppings.setText(obj.getToppings(id));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();

        }
    }


    public void setmCursor(Cursor mCursor) {

        this.mCursor = mCursor;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView total_tv;
        // Will display the party size number
        TextView datetime_tv;

        TextView orderid_tv,toppings;

        ImageView delimv;


        public OrderViewHolder(View itemView) {
            super(itemView);
            total_tv = (TextView) itemView.findViewById(R.id.total_tv);
            datetime_tv = (TextView) itemView.findViewById(R.id.datetime_tv);
            orderid_tv = (TextView) itemView.findViewById(R.id.orderid_tv);
            toppings = (TextView) itemView.findViewById(R.id.toppings);
            delimv =itemView.findViewById(R.id.delimv);
        }

    }
}