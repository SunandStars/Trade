package com.example.dawn.realbuyer.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.common.fragment.ReFirstFragment;
import com.example.dawn.realbuyer.fragment.ReBuyBuyFragment;
import com.example.dawn.realbuyer.fragment.ReBuyManageFragment;

/**
 * Created by wanxiao on 2015/11/6.
 */
public class BuyMainAcitivity extends Activity
{
    private ReBuyBuyFragment rebuybuyfrag;
    private ReFirstFragment rebuyfirstfrag;
    private ReBuyManageFragment rebuymanagefrag;
    private FragmentTransaction transaction;
    FragmentManager fManager;

    //设立四个单选按钮，并添加四个fragment
    RadioGroup TabRadioGroup;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_real_buy_main);

        transaction = getFragmentManager().beginTransaction();
        rebuyfirstfrag = new ReFirstFragment();
        transaction.add(R.id.tabcontent, rebuyfirstfrag);
        transaction.commit();
        TabRadioGroup=(RadioGroup)findViewById(R.id.tab_radiogroup);
        TabRadioGroup.setOnCheckedChangeListener(new TableButtonOnChangeCheckedClick());
        //getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new FirstFragment());
        rebuybuyfrag=new ReBuyBuyFragment();
        rebuyfirstfrag=new ReFirstFragment();
        rebuymanagefrag=new ReBuyManageFragment();
        // Maininit();

//        //接收从UserInfActivity中传过来的fragid数据，实现从Activity跳转到Fragment
//        Intent intent=this.getIntent();
////            final Bundle data=intent.getExtras();
//        int id = intent.getIntExtra("fragid",-1);
////            String cellphonenum=data.getString("cellphonemun");
//        if(id==1)
//        {
//
//            replaceFragment(setfrag);
//        }


//        //接收从WriteOrderActivity中传过来的writeorder数据，实现从Activity跳转到Fragment
//        Intent intent1=this.getIntent();
//        int id2 = intent.getIntExtra("writeorder",-1);
//        if(id2==1)
//        {
//            replaceFragment(buyfrag);
//        }
//
//        Intent backIntent =this.getIntent();
//        int id3 = intent.getIntExtra("backwriteorder",-1);
//        if(id3==1)
//        {
//            replaceFragment(buyfrag);
//        }


    }
    public void Maininit()
    {
        TabRadioGroup=(RadioGroup)findViewById(R.id.tab_radiogroup);
        TabRadioGroup.setOnCheckedChangeListener(new TableButtonOnChangeCheckedClick());
        //     fManager =getSupportFragmentManager();
        //getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new FirstFragment());
    }
    public class TableButtonOnChangeCheckedClick extends FragmentActivity implements RadioGroup.OnCheckedChangeListener
    {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {

            //     RadioButton CheckRadioButton=(RadioButton)findViewById(checkedId);
            switch (checkedId)
            {
                case R.id.First_ButtonID:
                    //CheckRadioButton.setHintTextColor();
                    if(rebuyfirstfrag==null) {
                        // getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new FirstFragment());
                        rebuyfirstfrag = new ReFirstFragment();
                        addFragment(rebuyfirstfrag);
                       /* transaction.add(R.id.tabcontent, firstfrag);
                        transaction.show(firstfrag);
                        transaction.*/
                    }
                    else{
                        replaceFragment(rebuyfirstfrag);
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent,new FirstFragment());
                    break;
                case R.id.Buy_ButtonID:
                    if(rebuybuyfrag ==null)
                    {
                        rebuybuyfrag = new ReBuyBuyFragment();
                        addFragment(rebuybuyfrag);
                       /* buyfrag = new BuyFragment();
                        transaction.add(R.id.tabcontent, buyfrag);
                        transaction.show(buyfrag);*/
                    }

                    else{
                        replaceFragment(rebuybuyfrag);
                    }

                    break;

                case R.id.Manage_ButtonID:
                    if (rebuymanagefrag == null){
                        rebuymanagefrag = new ReBuyManageFragment();
                        addFragment(rebuymanagefrag);
                    }
                    //getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new SellFragment());
                    else{
                        replaceFragment(rebuymanagefrag);
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent,new SellFragment());
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
        //transaction.replace(android.R.id.tabcontent, fragment);
        // Commit the transaction
        transaction.commit();
    }



    //提示双击退出app

    private long exitTime = 0;
    /**
     * 捕捉返回事件按钮
     *
     * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent
     * 一般的 Activity 用 onKeyDown 就可以了
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(BuyMainAcitivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
