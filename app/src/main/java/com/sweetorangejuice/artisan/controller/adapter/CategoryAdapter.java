package com.sweetorangejuice.artisan.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweetorangejuice.artisan.R;
import com.sweetorangejuice.artisan.model.Category;
import com.sweetorangejuice.artisan.view.Activity.SubPlazaActivity;

import java.util.List;

/**
 * Created by as on 2017/5/12.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mCategories;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;
        ImageView mImageView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            mImageView=(ImageView) itemView.findViewById(R.id.category_image_view);
            mTextView=(TextView) itemView.findViewById(R.id.category_text_view);
        }
    }

    public CategoryAdapter(List<Category> categories){
        mCategories=categories;
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category=mCategories.get(position);
        holder.mImageView.setImageResource(category.getImageResId());
        holder.mTextView.setText(category.getNameResId());
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO:跳转到指定分类下页面
                SubPlazaActivity.CategoryCode categoryCode;
                int position=holder.getAdapterPosition();
                Category category=mCategories.get(position);
                categoryCode=category.getCategoryCode();
                SubPlazaActivity.actionStart(parent.getContext(),categoryCode);
            }
        });

        return holder;
    }
}
