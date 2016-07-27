package com.example.dawn.car.activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.dawn.car.fragment.BuyFragment;
import com.example.dawn.car.fragment.FirstFragment;
import com.example.dawn.car.R;
import com.example.dawn.car.fragment.SellFragment;
import com.example.dawn.car.fragment.SetFragment;
import com.example.dawn.realbuyer.activity.BuyMainAcitivity;
import com.example.dawn.realseller.activity.SellMainActivity;


public class MainActivity extends Activity
{
    private BuyFragment buyfrag;
    private SellFragment sellfrag;
    private FirstFragment firstfrag;
    private SetFragment setfrag;
    private FragmentTransaction transaction;
    FragmentManager fManager;

    //设立四个单选按钮，并添加四个fragment
    RadioGroup TabRadioGroup;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences;
        sharedPreferences=getSharedPreferences("LoginState",MODE_PRIVATE);
        int login_state= sharedPreferences.getInt("State", 0);
        switch (login_state){
            case 1:{
                Intent intent=new Intent(MainActivity.this,BuyMainAcitivity.class);
                startActivity(intent);
                finish();
            }break;
            case 2:{
                Intent intent=new Intent(MainActivity.this,SellMainActivity.class);
                startActivity(intent);
                finish();
            }break;
            default:
                break;
        }

       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        transaction = getFragmentManager().beginTransaction();
        firstfrag = new FirstFragment();
        transaction.add(R.id.tabcontent, firstfrag);
        transaction.commit();
        TabRadioGroup=(RadioGroup)findViewById(R.id.tab_radiogroup);
        TabRadioGroup.setOnCheckedChangeListener(new TableButtonOnChangeCheckedClick());
        //getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new FirstFragment());
        buyfrag = new BuyFragment();
        firstfrag = new FirstFragment();
        setfrag = new SetFragment();
        sellfrag = new SellFragment();
       // Maininit();

        //接收从UserInfActivity或者是ReBuymanageFragment中传过来的fragid数据，实现从Activity或者是Fragment跳转到Fragment
            Intent intent=this.getIntent();
//            final Bundle data=intent.getExtras();
            int id = intent.getIntExtra("fragid",-1);
//            String cellphonenum=data.getString("cellphonemun");
            if(id==1)
            {

                replaceFragment(setfrag);
            }


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
                    if(firstfrag==null) {
                       // getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new FirstFragment());
                        firstfrag = new FirstFragment();
                        addFragment(firstfrag);
                       /* transaction.add(R.id.tabcontent, firstfrag);
                        transaction.show(firstfrag);
                        transaction.*/
                    }
                    else{
                        replaceFragment(firstfrag);
                    }
                        //getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent,new FirstFragment());
                    break;
                case R.id.Buy_ButtonID:
                    if(buyfrag ==null)
                    {
                        buyfrag = new BuyFragment();
                        addFragment(buyfrag);
                       /* buyfrag = new BuyFragment();
                        transaction.add(R.id.tabcontent, buyfrag);
                        transaction.show(buyfrag);*/
                    }

                    else{
                        replaceFragment(buyfrag);
                    }

                    break;
                case R.id.Sell_ButtonID:
                    if (sellfrag == null){
                        sellfrag = new SellFragment();
                        addFragment(sellfrag);
                    }
                       // getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new SellFragment());
                    else{
                        replaceFragment(sellfrag);
                    }
                      //  getSupportFragmentManager().beginTransaction().replace(R.id.tabcontent,new SellFragment());
                    break;
                case R.id.Set_ButtonID:
                    if (setfrag == null){
                        setfrag = new SetFragment();
                        addFragment(setfrag);
                    }
                        //getSupportFragmentManager().beginTransaction().add(R.id.tabcontent, new SellFragment());
                    else{
                        replaceFragment(setfrag);
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
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


}
