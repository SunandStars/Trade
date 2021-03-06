package com.example.dawn.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dawn.buyer.domain.CharacterParser;
import com.example.dawn.buyer.domain.ClearEditText;
import com.example.dawn.buyer.domain.PinyinComparator;
import com.example.dawn.buyer.domain.SideBar;
import com.example.dawn.buyer.domain.SortAdapter;
import com.example.dawn.buyer.domain.SortModel;
import com.example.dawn.car.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.buyer.domain.SideBar.OnTouchingLetterChangedListener;

/**
 * Created by wanxiao on 2015/10/13.
 */
public class CarModelChooseActivity extends Activity
{
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private  TextView carbrand;
    private  String CarBrand;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_carmodel_choose);
        carbrand =(TextView)findViewById(R.id.car_model_choose_tv);
        //接收从CarModelActivity中传过来的汽车品牌信息，并显示在界面最上端的TextView控件上
        Intent intent =getIntent();
        final Bundle data=intent.getExtras();
        CarBrand =data.getString("carbrand");
        carbrand.setText(CarBrand);
        initViews();
    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                String CarModel = ((SortModel) adapter.getItem(position)).getName();
                String Car = CarBrand + "-" + CarModel;
//                Toast.makeText(getApplication(), Car, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CarModelChooseActivity.this, WriteOrderActivity.class);
                intent.putExtra("car", Car);
                startActivity(intent);
                finish();
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.CarModel));

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 为ListView填充数据
     * @param CarModel
     * @return
     */
    private List<SortModel> filledData(String [] CarModel){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<CarModel.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(CarModel[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(CarModel[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = SourceDateList;
        }else{
            filterDateList.clear();
            for(SortModel sortModel : SourceDateList){
                String name = sortModel.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    //   复写手机退出按钮提示消息
    @Override

    public void onBackPressed() {

        // TODO Auto-generated method stub

        Intent intent = new Intent(CarModelChooseActivity.this, CarModelActivity.class);
        startActivity(intent);
        finish();

        //super.onBackPressed();

    }

}
