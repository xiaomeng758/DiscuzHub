package com.kidozh.discuzhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kidozh.discuzhub.databinding.ItemNetworkIndicatorFailedBinding;
import com.kidozh.discuzhub.databinding.ItemNetworkIndicatorLoadAllBinding;
import com.kidozh.discuzhub.databinding.ItemNetworkIndicatorLoadingBinding;
import com.kidozh.discuzhub.entities.ErrorMessage;
import com.kidozh.discuzhub.utilities.ConstUtils;

public class NetworkIndicatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int loadingStatus = 0;
    private Context context;
    private ErrorMessage errorMessage;

    public void setLoadingStatus(int loadingStatus) {
        this.loadingStatus = loadingStatus;
        notifyDataSetChanged();
    }

    public void setErrorStatus(@NonNull ErrorMessage errorMessage) {
        this.loadingStatus = ConstUtils.NETWORK_STATUS_FAILED;
        this.errorMessage = errorMessage;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (loadingStatus){
            case ConstUtils.NETWORK_STATUS_LOADING:{
                ItemNetworkIndicatorLoadingBinding binding = ItemNetworkIndicatorLoadingBinding.inflate(layoutInflater,parent,false);
                return new NetworkIndicatorLoadingViewHolder(binding);
            }
            case ConstUtils.NETWORK_STATUS_LOADED_ALL:{
                ItemNetworkIndicatorLoadAllBinding binding = ItemNetworkIndicatorLoadAllBinding.inflate(layoutInflater,parent,false);
                return new NetworkIndicatorLoadAllViewHolder(binding);
            }
            case ConstUtils.NETWORK_STATUS_FAILED:{
                ItemNetworkIndicatorFailedBinding binding = ItemNetworkIndicatorFailedBinding.inflate(layoutInflater,parent,false);
                return new NetworkIndicatorLoadFailedViewHolder(binding);
            }
        }
        ItemNetworkIndicatorLoadingBinding binding = ItemNetworkIndicatorLoadingBinding.inflate(layoutInflater,parent,false);
        return new NetworkIndicatorLoadingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NetworkIndicatorLoadFailedViewHolder && loadingStatus == ConstUtils.NETWORK_STATUS_FAILED){
            ((NetworkIndicatorLoadFailedViewHolder) holder).binding.errorValue.setText(errorMessage.key);
            ((NetworkIndicatorLoadFailedViewHolder) holder).binding.errorContent.setText(errorMessage.content);
            if(errorMessage.errorIconResource != 0){
                ((NetworkIndicatorLoadFailedViewHolder) holder).binding.errorIcon.setImageResource(errorMessage.errorIconResource);
            }
            else {
                ((NetworkIndicatorLoadFailedViewHolder) holder).binding.errorIcon.setImageResource(ErrorMessage.getDefaultErrorIconResource());
            }

        }
    }

    @Override
    public int getItemCount() {
        switch (loadingStatus){
            case ConstUtils.NETWORK_STATUS_LOADING:{
                return 1;
            }
            case ConstUtils.NETWORK_STATUS_LOADED_ALL:{
                return 1;
            }
            case ConstUtils.NETWORK_STATUS_FAILED:{
                return 1;
            }
            case ConstUtils.NETWORK_STATUS_SUCCESSFULLY:{
                return 0;
            }
        }
        return 0;
    }

    public static class NetworkIndicatorLoadingViewHolder extends RecyclerView.ViewHolder{
        @NonNull
        ItemNetworkIndicatorLoadingBinding binding;
        NetworkIndicatorLoadingViewHolder(@NonNull ItemNetworkIndicatorLoadingBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class NetworkIndicatorLoadAllViewHolder extends RecyclerView.ViewHolder{
        @NonNull
        ItemNetworkIndicatorLoadAllBinding binding;
        NetworkIndicatorLoadAllViewHolder(@NonNull ItemNetworkIndicatorLoadAllBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class NetworkIndicatorLoadFailedViewHolder extends RecyclerView.ViewHolder{
        @NonNull
        ItemNetworkIndicatorFailedBinding binding;
        NetworkIndicatorLoadFailedViewHolder(@NonNull ItemNetworkIndicatorFailedBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
