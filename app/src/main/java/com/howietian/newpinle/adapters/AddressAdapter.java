package com.howietian.newpinle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.howietian.newpinle.R;
import com.howietian.newpinle.entities.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 83624 on 2018/2/16 0016.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private Context context;
    private List<Address> addressList = new ArrayList<>();
    private onEditClickListener onEditClicklistener;
    private onDeleteClickListener onDeleteClickListener;

    public interface onEditClickListener {
        void onClick(int position);
    }

    public interface onDeleteClickListener {
        void onClick(int position);
    }

    public void setOnEditClickListener(onEditClickListener listener) {
        this.onEditClicklistener = listener;
    }

    public void setOnDeleteClickListener(onDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    public AddressAdapter(Context context, List<Address> addresses) {
        this.context = context;
        this.addressList = addresses;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddressViewHolder holder = null;
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        holder = new AddressViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, final int position) {
        final Address address = addressList.get(position);
        holder.tvName.setText(address.getPeople());
        holder.tvPhone.setText(address.getPhone());
        holder.tvAddress.setText(address.getAddress());


        if (onEditClicklistener != null) {
            holder.llEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEditClicklistener.onClick(position);
                }
            });
        }

        if (onDeleteClickListener != null) {
            holder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDeleteClickListener.onClick(position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_phone)
        TextView tvPhone;
        @Bind(R.id.ll_eidt)
        LinearLayout llEdit;
        @Bind(R.id.ll_delete)
        LinearLayout llDelete;
        @Bind(R.id.tv_address)
        TextView tvAddress;

        public AddressViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
