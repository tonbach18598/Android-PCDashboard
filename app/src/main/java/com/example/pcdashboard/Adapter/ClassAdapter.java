package com.example.pcdashboard.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pcdashboard.Model.ClassPost;
import com.example.pcdashboard.R;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ClassPost> classPosts;
    private OnItemClickListener listener;

    public ClassAdapter(Context context, ArrayList<ClassPost> classPosts,OnItemClickListener listener) {
        this.context = context;
        this.classPosts = classPosts;
        this.listener=listener;
    }

    public interface OnItemClickListener {
        void onClick(ClassPost classPost);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_class_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ClassPost classPost = classPosts.get(position);
        holder.tvName.setText(classPost.getUserName());
        holder.tvTime.setText(classPost.getTime());
        holder.tvContent.setText(classPost.getContent());
        Glide.with(context).load(Uri.parse(classPost.getUserAvatar())).centerCrop().override(40,40).into(holder.ivAvatar);
        Glide.with(context).load(Uri.parse(classPost.getImage())).into(holder.ivImage);
        holder.tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(classPost);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classPosts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime, tvContent, tvComment;
        ImageView ivAvatar, ivImage;
        ImageButton ibMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_class_item);
            tvTime = itemView.findViewById(R.id.tv_time_class_item);
            tvContent = itemView.findViewById(R.id.tv_content_class_item);
            tvComment = itemView.findViewById(R.id.tv_comment_class_item);
            ivAvatar = itemView.findViewById(R.id.iv_avatar_class_item);
            ivImage = itemView.findViewById(R.id.iv_image_class_item);
            ibMore = itemView.findViewById(R.id.ib_more_class_item);
        }
    }
}