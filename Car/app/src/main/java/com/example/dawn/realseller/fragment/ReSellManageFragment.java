package com.example.dawn.realseller.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dawn.car.R;
import com.example.dawn.car.activity.MainActivity;
import com.example.dawn.common.activity.ChangePassword;
import com.example.dawn.common.activity.ChangePhoneNumber1;
import com.example.dawn.realbuyer.activity.ReBuyCheckInfActivity;
import com.example.dawn.realseller.activity.ReSellCheckInfActivity;

/**
 * Created by wanxiao on 2015/11/9.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ReSellManageFragment extends Fragment
{
    SharedPreferences preferencesuserinf;
    TextView buyeraddress;
    TextView buyercellphonenum;
    Button checkinf,changenum,changepassword,exitLogin;
    TextView checkinftv,changenumtv;
    ImageView HeadPic;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_resell_manage,container,false);
        buyeraddress=(TextView)view.findViewById(R.id.manage_user_address);
        buyercellphonenum=(TextView)view.findViewById(R.id.manage_user_cellphonenumber);
        checkinf=(Button)view.findViewById(R.id.manage_user_message_btn);
        changenum=(Button)view.findViewById(R.id.manage_changenum_btn);
        changepassword=(Button)view.findViewById(R.id.manage_changpassword_btn);
        exitLogin=(Button)view.findViewById(R.id.manage_user_quit);
        checkinftv=(TextView)view.findViewById(R.id.manage_user_message_tv);
        changenumtv=(TextView)view.findViewById(R.id.manage_changenum_tv);
        HeadPic=(ImageView)view.findViewById(R.id.manage_user_pic);

        //读取userinf文件中的数据
        preferencesuserinf=getActivity().getSharedPreferences("userinf", Context.MODE_PRIVATE);

        buyeraddress.setText(preferencesuserinf.getString("address", null));
        buyercellphonenum.setText(preferencesuserinf.getString("phoneNumber", null));
        String headpic=preferencesuserinf.getString("lisencepic",null);
        byte[] bitmapArray1= Base64.decode(headpic, Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapArray1, 0,
                bitmapArray1.length);
        HeadPic.setImageBitmap(bitmap1);

        checkinf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReSellCheckInfActivity.class);
                startActivity(intent);

            }

        });
        checkinftv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ReSellCheckInfActivity.class);
                startActivity(intent);


            }
        });
        changenumtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ChangePhoneNumber1.class);
                startActivity(intent);

            }
        });

        changenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ChangePhoneNumber1.class);
                startActivity(intent);

            }
        });

        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ChangePassword.class);
                startActivity(intent);

            }
        });

        exitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences;
                sharedPreferences=getActivity().getSharedPreferences("LoginState",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor=sharedPreferences.edit();
                editor.putInt("State",0);
                editor.apply();

                Intent intent=new Intent(getActivity(), MainActivity.class);
                intent.putExtra("fragid", 1);
                startActivity(intent);

            }
        });




        return view;

    }
}
