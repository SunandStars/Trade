package com.example.dawn.seller.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dawn.buyer.fragment.CheckedFragment;
import com.example.dawn.buyer.fragment.EnquiryFragment;
import com.example.dawn.buyer.fragment.RejectedFragment;
import com.example.dawn.buyer.fragment.TradingFragment;
import com.example.dawn.car.R;
import com.example.dawn.seller.fragment.NewOrderFragment;
import com.example.dawn.seller.fragment.PricedFragment;
import com.example.dawn.seller.fragment.SellCheckedFragment;
import com.example.dawn.seller.fragment.SellTradingFragment;

/**
 * Created by wanxiao on 2015/10/30.
 */
public class SellGoodsInfActivity extends Activity
{
    private NewOrderFragment neworderfrag;
    private PricedFragment pricedfrag;
    private SellTradingFragment selltradingfrag;
    private SellCheckedFragment sellcheckedfrag;

    private FragmentTransaction transaction;
    FragmentManager fManager;


    RadioGroup TabRadioGroup;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sell_goods_inf);


        TabRadioGroup=(RadioGroup)findViewById(R.id.tab_radiogroup);
        TabRadioGroup.setOnCheckedChangeListener(new TableButtonOnChangeCheckedClick());
        neworderfrag=new NewOrderFragment();
        pricedfrag=new PricedFragment();
        selltradingfrag=new SellTradingFragment();
        sellcheckedfrag=new SellCheckedFragment();




        Intent intent=this.getIntent();
        int id = intent.getIntExtra("fragid",-1);
        if(id==1)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_UnderProcess);
            indealbn.setChecked(true);
            replaceFragment(neworderfrag);

        }
        if(id==2)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_InDeal);
            indealbn.setChecked(true);
            replaceFragment(pricedfrag);

        }
        if(id==3)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_Checked);
            indealbn.setChecked(true);
            replaceFragment(selltradingfrag);
        }
        if(id==4)
        {
            TabRadioGroup.clearCheck();
            RadioButton indealbn=(RadioButton)findViewById(R.id.OtherPages_Rejected);
            indealbn.setChecked(true);
            replaceFragment(sellcheckedfrag);

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

                    if(neworderfrag==null) {

                        neworderfrag = new NewOrderFragment();
                        addFragment(neworderfrag);


                    }
                    else{
                        replaceFragment(neworderfrag);
                    }

                    break;
                case R.id.OtherPages_InDeal:
                    if(pricedfrag ==null)
                    {
                        pricedfrag = new PricedFragment();
                        addFragment(pricedfrag);

                    }

                    else{
                        replaceFragment(pricedfrag);
                    }

                    break;
                case R.id.OtherPages_Checked:
                    if (selltradingfrag== null){
                        selltradingfrag = new SellTradingFragment();
                        addFragment(selltradingfrag);
                    }

                    else{
                        replaceFragment(selltradingfrag);
                    }

                    break;
                case R.id.OtherPages_Rejected:
                    if (sellcheckedfrag == null){
                        sellcheckedfrag = new SellCheckedFragment();
                        addFragment(sellcheckedfrag);
                    }

                    else{
                        replaceFragment(sellcheckedfrag);
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
