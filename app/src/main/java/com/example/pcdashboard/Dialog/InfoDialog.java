package com.example.pcdashboard.Dialog;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.pcdashboard.Model.User;
import com.example.pcdashboard.R;
import com.labo.kaji.swipeawaydialog.SwipeAwayDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class InfoDialog extends SwipeAwayDialogFragment {
    private User user;
    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvId;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvClass;

    public InfoDialog(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_info, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onStart() {
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        super.onStart();
    }

    @Override
    public void onResume() {
        onInit();
        super.onResume();
    }

    private void initialize(View view) {
        ivAvatar=view.findViewById(R.id.iv_avatar_info_dialog);
        tvName=view.findViewById(R.id.tv_name_info_dialog);
        tvId=view.findViewById(R.id.tv_id_info_dialog);
        tvEmail=view.findViewById(R.id.tv_email_info_dialog);
        tvPhone=view.findViewById(R.id.tv_phone_info_dialog);
        tvClass=view.findViewById(R.id.tv_class_info_dialog);
    }
    private void onInit(){
        Glide.with(getContext()).load(Uri.parse(user.getAvatar())).centerCrop().override(160,160).into(ivAvatar);
        tvName.setText(user.getName());
        tvId.setText(user.getId());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        tvClass.setText(user.getClassId());
    }

    @Override
    public boolean onSwipedAway(boolean toRight) {
        dismiss();
        return true;
    }
}
