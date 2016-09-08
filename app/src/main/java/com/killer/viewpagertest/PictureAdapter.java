package com.killer.viewpagertest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    private int[] res;

    private Context context;

    public PictureAdapter(int[] res,Context context) {
        this.res = res;
        this.context = context;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout, null);

        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder holder, int position) {
        holder.imageView.setImageResource(res[position%res.length]);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        if(mOnItemClickLitener != null){
           holder.imageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = holder.getLayoutPosition();
                   mOnItemClickLitener.onItemClick(holder.imageView,pos);

               }
           });



        }
    }



    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}
class PictureViewHolder extends RecyclerView.ViewHolder {
     ImageView imageView;

    public PictureViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);

    }


}
