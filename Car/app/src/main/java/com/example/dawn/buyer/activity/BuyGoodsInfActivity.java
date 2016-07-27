package com.example.dawn.buyer.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dawn.buyer.fragment.CheckedFragment;
import com.example.dawn.buyer.fragment.EnquiryFragment;
import com.example.dawn.buyer.fragment.RejectedFragment;
import com.example.dawn.buyer.fragment.TradingFragment;
import com.example.dawn.car.R;
import com.example.dawn.car.fragment.BuyFragment;
import com.example.dawn.car.fragment.FirstFragment;
import com.example.dawn.car.fragment.SellFragment;
import com.example.dawn.car.fragment.SetFragment;

/**
 * Created by wanxiao on 2015/10/29.
 */
public class BuyGoodsInfActivity extends Activity
{
    private EnquiryFragment enquiryfrag;
    private TradingFragment tradingfrag;
    private CheckedFragment checkedfrag;
    private RejectedFragment rejectedfrag;
    private FragmentTransaction transaction;
    FragmentManager fManager;


    RadioGroup TabRadioGroup;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.buy_goods_inf);


        TabRadioGroup=(RadioGroup)findViewById(R.id.tab_radiogroup);
        TabRadioGroup.setOnCheckedChangeListener(new TableButtonOnChangeCheckedClick());
        enquiryfrag=new EnquiryFragment();
        tradingfrag = new TradingFragment();
        checkedfrag = new CheckedFragment();
        rejectedfrag = new RejectedFragment();



//        Button enquiry= (Button)findViewById(R.id.OtherPages_UnderProcess);
//        Button trading=(Button)findViewById(R.id.OtherPages_InDeal);
//        Button checked=(Button)findViewById(R.id.OtherPages_Checked);
//        Button rejected=(Button)findViewById(R.id.OtherPages_Rejected);
//
//        enquiry.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(enquiryfrag==null) {
//
//                    enquiryfrag = new EnquiryFragment();
//                    addFragment(enquiryfrag);
//
//                }
//                else{
//                    replaceFragment(enquiryfrag);
//                }
//
//            }
//        });
//
//
//       trading.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               if (tradingfrag == null) {
//
//                   tradingfrag = new TradingFragment();
//                   addFragment(tradingfrag);
//
//               } else {
//                   replaceFragment(tradingfrag);
//               }
//
//           }
//       });
//
//
//
//
//
//        checked.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkedfrag == null) {
//
//                    checkedfrag = new CheckedFragment();
//                    addFragment(checkedfrag);
//
//                } else {
//                    replaceFragment(checkedfrag);
//                }
//
//            }
//        });
//
//
//
//
//       rejected.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//               if (rejectedfrag == null) {
//
//                   rejectedfrag = new RejectedFragment();
//                   addFragment(rejectedfrag);
//
//               } else {
//                   replaceFragment(rejectedfrag);
//               }
//
//           }
//       });



        Intent intent=this.getIntent();
        int id = intent.getIntExtra("fragid",-1);
        if(id==1)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_UnderProcess);
            indealbn.setChecked(true);
            replaceFragment(enquiryfrag);

        }
        if(id==2)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_InDeal);
            indealbn.setChecked(true);
            replaceFragment(tradingfrag);

        }
        if(id==3)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_Checked);
            indealbn.setChecked(true);
            replaceFragment(checkedfrag);
        }
        if(id==4)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_Rejected);
            indealbn.setChecked(true);
            replaceFragment(rejectedfrag);

        }
    }





    public class TableButtonOnChangeCheckedClick extends FragmentActivity implements RadioGroup.OnCheckedChangeListener
    {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {


            switch (checkedId)
            {
                case R.id.OtherPages_UnderProcess:

                    if(enquiryfrag==null) {

                        enquiryfrag = new EnquiryFragment();
                        addFragment(enquiryfrag);


                    }
                    else{
                        replaceFragment(enquiryfrag);
                    }

                    break;
                case R.id.OtherPages_InDeal:
                    if(tradingfrag ==null)
                    {
                        tradingfrag = new TradingFragment();
                        addFragment(tradingfrag);

                    }

                    else{
                        replaceFragment(tradingfrag);
                    }

                    break;
                case R.id.OtherPages_Checked:
                    if (checkedfrag== null){
                        checkedfrag = new CheckedFragment();
                        addFragment(checkedfrag);
                    }

                    else{
                        replaceFragment(checkedfrag);
                    }

                    break;
                case R.id.OtherPages_Rejected:
                    if (rejectedfrag == null){
                        rejectedfrag = new RejectedFragment();
                        addFragment(rejectedfrag);
                    }

                    else{
                        replaceFragment(rejectedfrag);
                    }

                    break;
                default:
                    break;

            }
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addFragment(Fragment fragment){
        if(!fragment.isAdded()) {
            FragmentTransaction transaction;
            transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.tabcontent, fragment);
            transaction.commit();
        }
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction;
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.tabcontent,fragment);

        transaction.commit();
    }
}
