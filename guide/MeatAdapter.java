package com.youhone.yjsboilingmachine.guide;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.youhone.yjsboilingmachine.R;

import java.util.List;

/**
 * Created by yarolegovich on 08.03.2017.
 */

public class MeatAdapter extends RecyclerView.Adapter<MeatAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private List<Meattype> data;
    private Context ctx;


    public MeatAdapter(List<Meattype> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_guide_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //int iconTint = ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary);
        Meattype meat = data.get(position);
        Glide.with(holder.itemView.getContext())
                .load(meat.getMeat_icon())
                .fitCenter()
                //.listener(new TintOnLoad(holder.imageView, iconTint))
                .into(holder.imageView);
        holder.textView.setText(meat.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        private FrameLayout info;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.meat_image);
            textView = (TextView) itemView.findViewById(R.id.meat_name);
            info = itemView.findViewById(R.id.container);
            itemView.findViewById(R.id.container).setOnClickListener(this);
        }

        public void showText(int position) {
            Meattype meat = data.get(position);
            imageView.setImageResource(meat.getMeat_icon_active());
            info.setBackgroundColor(Color.parseColor("#f3be3d"));
            //int parentHeight = ((View) imageView.getParent()).getHeight();
            /*float scale = (parentHeight - textView.getHeight()) / (float) imageView.getHeight();
            imageView.setPivotX(imageView.getWidth() * 0.5f);
            imageView.setPivotY(0);
            imageView.animate().scaleX(scale)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            //textView.setVisibility(View.VISIBLE);
                            //imageView.setColorFilter(Color.BLACK);
                        }
                    })
                    .scaleY(scale).setDuration(200)
                    .start();*/


        }

        public void hideText(int position) {
            Meattype meat = data.get(position);
            imageView.setImageResource(meat.getMeat_icon());
            info.setBackgroundColor(Color.parseColor("#a1a3a5"));
            //imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.progress_color));
            //textView.setVisibility(View.INVISIBLE);
            /*imageView.animate().scaleX(1f).scaleY(1f)
                    .setDuration(200)
                    .start();*/
        }

        @Override
        public void onClick(View v) {
            parentRecycler.smoothScrollToPosition(getAdapterPosition());
        }
    }

    private static class TintOnLoad implements RequestListener<Integer, GlideDrawable> {

        private ImageView imageView;
        private int tintColor;

        public TintOnLoad(ImageView view, int tintColor) {
            this.imageView = view;
            this.tintColor = tintColor;
        }

        @Override
        public boolean onException(Exception e, Integer model, Target<GlideDrawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(GlideDrawable resource, Integer model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
            imageView.setColorFilter(tintColor);
            return false;
        }
    }
}
