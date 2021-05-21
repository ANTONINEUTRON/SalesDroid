package com.neutron.salesdroid.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.neutron.salesdroid.R;
import com.neutron.salesdroid.data.model.Stock;
import com.neutron.salesdroid.ui.dialogs.ViewStockDetails;
import com.neutron.salesdroid.utils.DeleteStock;

import java.util.List;

public class StocksRVAdapter extends RecyclerView.Adapter<StocksRVAdapter.ViewHolder> {

    private final List<Stock> mValues;
    private Context context;

    public StocksRVAdapter(List<Stock> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stocks, parent, false);
        context = view.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Stock stockPos = mValues.get(position);
        holder.stockName.setText(stockPos.getName());
        holder.availQty.setText(stockPos.getAvailableQuantity()+" "+stockPos.getUnit());
        holder.priceDetails.setText(stockPos.getUnitPrice()+" per "+stockPos.getUnit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                ViewStockDetails viewStockDetails = new ViewStockDetails(stockPos);
                viewStockDetails.show(activity.getSupportFragmentManager(),"stock dialog");
            }
        });
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.menu);
                popupMenu.inflate(R.menu.rv_popup_menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete:
                                DeleteStock deleteStock = new DeleteStock(context);
                                deleteStock.delete(stockPos);
                                holder.itemView.setVisibility(View.GONE);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView stockName;
        private final TextView availQty;
        private final TextView priceDetails;
        private final ImageButton menu;

        public ViewHolder(View view) {
            super(view);
            stockName = (TextView) view.findViewById(R.id.s_name);
            availQty = (TextView) view.findViewById(R.id.avail_qty_details);
            priceDetails = view.findViewById(R.id.price_details);
            menu = view.findViewById(R.id.menu_btn);
        }
    }
}