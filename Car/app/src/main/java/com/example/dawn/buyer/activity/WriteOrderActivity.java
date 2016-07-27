package com.example.dawn.buyer.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.car.activity.MainActivity;
import com.example.dawn.car.domain.MyOpenHelper;
import com.example.dawn.common.MapCodeCovert;
import com.example.dawn.manage.activity.HttpUtil;
import com.example.dawn.manage.bean.User;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;
import com.example.dawn.realbuyer.activity.BuyMainAcitivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by WANXIAO on 2015/9/13.
 */
public class WriteOrderActivity extends Activity {
    SharedPreferences preferencesuserinf;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    int a;
    String picturePath = null;
    File outFile = null;

    String UserName;
    String Company;
    String Address;
    String Model;
    String ModelYear;
    String ReceiverName;
    String ReceiverAddress;
    String ReceiverCellphone;
    String Post;
    String Demand;
    String iv1;
    String iv2;
    String iv3;
    String Lispic;
    String Compic1;
    String Compic2;
    String Good_total;
    private Button btnAdd = null;
    private Button btshow = null;
    private int number_row = 0;
    private EditText nameET = null;
    private TableLayout table = null;
    String[] strings_all={};
    private String[] strings1;
    private String[] strings2;
    private String[] strings3;
    TextView companyEt;
    TextView addressEt;
    TextView modelTv;
    EditText modelYearEt;
    EditText receivernameEt;
    EditText receiveraddressEt;
    EditText receivercellphoneEt;
    EditText postEt;
    EditText demandEt;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;

    private UserService userService = new UserServiceImp();

    final Map<String, String> params_image = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.buy_writeorder);

        preferencesuserinf = this.getSharedPreferences("userinf", MODE_PRIVATE);
        preferences = this.getSharedPreferences("writeorder_temp", MODE_PRIVATE);
        editor = preferences.edit();


        Button bt_submit = (Button) findViewById(R.id.buy_bt);
        Button btback = (Button) findViewById(R.id.bn_writeorder_return);


        //获取文本框和图片框
        companyEt = (TextView) findViewById(R.id.company_et);
        addressEt = (TextView) findViewById(R.id.address_et);
        modelTv = (TextView) findViewById(R.id.model_et);
        modelYearEt = (EditText) findViewById(R.id.model_time_et);
        receivernameEt = (EditText) findViewById(R.id.model_check_name2);
        receiveraddressEt = (EditText) findViewById(R.id.model_check_adderss2);
        receivercellphoneEt = (EditText) findViewById(R.id.model_check_cellphone2);
        postEt = (EditText) findViewById(R.id.model_check_post2);
        demandEt = (EditText) findViewById(R.id.demand_et);
        imageView1 = (ImageView) findViewById(R.id.buy_iv1);
        imageView2 = (ImageView) findViewById(R.id.buy_iv2);
        imageView3=(ImageView) findViewById(R.id.buy_iv3);
        table = (TableLayout) findViewById(R.id.dictTable);


        //读取userinf文件中的汽修厂名称和地址信息和用户名
        companyEt.setText(preferencesuserinf.getString("company", null));
        addressEt.setText(preferencesuserinf.getString("address", null));




        //读取 SharedPreferences中的数据


        modelTv.setText(preferences.getString("model", null));
        modelYearEt.setText(preferences.getString("modelyear", null));
        receivernameEt.setText(preferences.getString("receivername", null));
        receiveraddressEt.setText(preferences.getString("receiveraddress", null));
        receivercellphoneEt.setText(preferences.getString("receivercellphone", null));
        postEt.setText(preferences.getString("postnum", null));
        demandEt.setText(preferences.getString("demand", null));
        Compic1=preferences.getString("companypic1", null);
        Compic2=preferences.getString("companypic2", null);
        Lispic=preferences.getString("lisencepic",null);

        if(Compic1!=null)
        {

            //将被转换成String类型的图片文件解析成以前的ImageView类型
            byte[] bitmapArray1= Base64.decode(Compic1, Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapArray1, 0,
                    bitmapArray1.length);
            imageView1.setImageBitmap(bitmap1);
//

        }

         if(Compic2!=null)
        {

            //将被转换成String类型的图片文件解析成以前的ImageView类型
            byte[] bitmapArray2= Base64.decode(Compic2, Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(bitmapArray2, 0,
                    bitmapArray2.length);
            imageView2.setImageBitmap(bitmap2);

        }
         if(Lispic!=null)
        {


            //将被转换成String类型的图片文件解析成以前的ImageView类型
            byte[] bitmapArray3= Base64.decode(Lispic, Base64.DEFAULT);
            Bitmap bitmap3 = BitmapFactory.decodeByteArray(bitmapArray3, 0,
                    bitmapArray3.length);
            imageView3.setImageBitmap(bitmap3);

        }


//        if(preferences.contains("Good_total"))
//        {
//            Good_total=preferences.getString("Good_total",null);
//            Toast.makeText(WriteOrderActivity.this,"-------"+Good_total,Toast.LENGTH_LONG).show();
//            strings_all=Good_total.split("/");
//            int num=strings_all.length;
//            strings1=new String[num];
//            strings2=new String[num];
//            strings3=new String[num];
//            for(int i=0;i<num;i++)
//            {
//                String[] a=strings_all[i].split("<");
//                String[] b=a[1].split(">");
//
//                strings1[i]=a[0];
//                strings2[i]=b[0];
//                strings3[i]=b[1];
//            }
//        }

        initItemTable();




        //接收从CarModelChooseActivity中传输过来的汽车品牌+汽车型号的数据
        Intent intent = getIntent();
        final Bundle data = intent.getExtras();
        if (data != null) {
//            Toast.makeText(this, "wanxiao", Toast.LENGTH_SHORT).show();//测试中使用的代码
//

            modelTv.setText(data.getString("car"));
        }


        //设置返回按钮
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            Toast.makeText(WriteOrderActivity.this,Address,Toast.LENGTH_LONG).show();
//
                //获取EditText里面的内容
                Model = modelTv.getText().toString();
                ModelYear = modelYearEt.getText().toString();
                ReceiverName = receivernameEt.getText().toString();
                ReceiverAddress = receiveraddressEt.getText().toString();
                ReceiverCellphone = receivercellphoneEt.getText().toString();
                Post = postEt.getText().toString();
                Demand = demandEt.getText().toString();
                Good_total = "";
                for (int i = 0; i < table.getChildCount(); i++) {
                    // 得到TableRow对象
                    // Toast.makeText(getApplicationContext(),"--------"+Show,Toast.LENGTH_LONG).show();
                    TableRow row = (TableRow) table.getChildAt(i);
                    String dictName = ((TextView) row.getChildAt(0))
                            .getText().toString();
                    String dictNum = ((EditText) row.getChildAt(1))
                            .getText().toString();
                    String dictRemark = ((EditText) row.getChildAt(2))
                            .getText().toString();
                    Good_total = Good_total + dictName +"<"+ dictNum +">"+ dictRemark+"/";
                }
                if(imageView1.getDrawable()!=null)
                {
                    Bitmap bmp1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
                    iv1=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
                }
                if(imageView2.getDrawable()!=null)
                {
                    Bitmap bmp2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
                    iv2=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));
                }
                 if(imageView3.getDrawable()!=null)
                {
                    Bitmap bmp3 = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
                    iv3=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp3));
                }

//
                editor.putString("company", Company);
                editor.putString("address", Address);
                editor.putString("model", Model);
                editor.putString("modelyear", ModelYear);
                editor.putString("receivername", ReceiverName);
                editor.putString("receiveraddress", ReceiverAddress);
                editor.putString("receivercellphone", ReceiverCellphone);
                editor.putString("post", Post);
                editor.putString("Good_total",Good_total);
                editor.putString("companypic1",iv1 );
                editor.putString("companypic2",iv2 );
                editor.putString("lisencepic",iv3);
                editor.putString("demand", Demand);


                editor.apply();
                Toast.makeText(WriteOrderActivity.this,Good_total,Toast.LENGTH_LONG).show();

                finish();

            }
        });
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                Company = companyEt.getText().toString();
                Address = addressEt.getText().toString();
                Model = modelTv.getText().toString();
                ModelYear = modelYearEt.getText().toString();
                ReceiverName = receivernameEt.getText().toString();
                ReceiverAddress = receiveraddressEt.getText().toString();
                ReceiverCellphone = receivercellphoneEt.getText().toString();
                Post = postEt.getText().toString();
                Good_total = "";
                        for (int i = 0; i < table.getChildCount(); i++) {
                            // 得到TableRow对象
                            // Toast.makeText(getApplicationContext(),"--------"+Show,Toast.LENGTH_LONG).show();
                            TableRow row = (TableRow) table.getChildAt(i);
                            String dictName = ((TextView) row.getChildAt(0))
                                    .getText().toString();
                            String dictNum = ((EditText) row.getChildAt(1))
                                    .getText().toString();
                            String dictRemark = ((EditText) row.getChildAt(2))
                                    .getText().toString();
                            Good_total = Good_total + dictName + "<"+dictNum+">" + dictRemark+"/";
                        }
                Demand = demandEt.getText().toString();
                if(imageView1.getDrawable()!=null)
                {
                    Bitmap bmp1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
                    iv1=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
                }
                else if(imageView2.getDrawable()!=null)
                {
                    Bitmap bmp2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
                    iv2=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));
                }
                else if(imageView3.getDrawable()!=null)
                {
                    Bitmap bmp3 = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
                    iv3=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp3));
                }

                if (!userService.checkmobnum(ReceiverCellphone))
                {
                    Toast.makeText(getApplicationContext(), "电话号码格式不正确，请重新输入", Toast.LENGTH_LONG).show();
                }
                else if (!"".equals(Model)  && !"".equals(Good_total) && !"".equals(Post) && !"".equals(ReceiverAddress)
                        && !"".equals(ReceiverName) && !"".equals(ReceiverCellphone) && !"".equals(ModelYear)
                        &&imageView1.getDrawable()!=null &&imageView2.getDrawable()!=null&&imageView3.getDrawable()!=null )
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WriteOrderActivity.this);

                    builder.setIcon(android.R.drawable.ic_btn_speak_now);
                    builder.setTitle("购物愉快");
                    builder.setMessage("请仔细检查收货人信息");


                    builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //上传提交
                            if (OrderDetail()) {
                                Toast.makeText(WriteOrderActivity.this, "订单提交成功", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(WriteOrderActivity.this, BuyMainAcitivity.class);
                                startActivity(intent);
                                editor.clear();
                                editor.apply();
                                finish();
                            }


                        }
                    });
                    //取消按钮
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(WriteOrderActivity.this, "订单未提交", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //使用创建器，生成对话框对象
                    AlertDialog submitting = builder.create();
                    submitting.show();

                }
                else
                {
                    Toast.makeText(WriteOrderActivity.this, "请将订单填写完整", Toast.LENGTH_SHORT).show();
                }
            }
        });


        modelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取EditText里面的内容
                Company = companyEt.getText().toString();
                Address = addressEt.getText().toString();
                Model = modelTv.getText().toString();
                ModelYear = modelYearEt.getText().toString();
                ReceiverName = receivernameEt.getText().toString();
                ReceiverAddress = receiveraddressEt.getText().toString();
                ReceiverCellphone = receivercellphoneEt.getText().toString();
                Post = postEt.getText().toString();
                Good_total = "";
                for (int i = 0; i < table.getChildCount(); i++) {
                    // 得到TableRow对象
                    // Toast.makeText(getApplicationContext(),"--------"+Show,Toast.LENGTH_LONG).show();
                    TableRow row = (TableRow) table.getChildAt(i);
                    String dictName = ((TextView) row.getChildAt(0))
                            .getText().toString();
                    String dictNum = ((EditText) row.getChildAt(1))
                            .getText().toString();
                    String dictRemark = ((EditText) row.getChildAt(2))
                            .getText().toString();
                    Good_total = Good_total + dictName + "<"+dictNum+">" + dictRemark+"/";
                }
                Demand = demandEt.getText().toString();
                if(imageView1.getDrawable()!=null)
                {
                    Bitmap bmp1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
                    iv1=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
                }
                else if(imageView2.getDrawable()!=null)
                {
                    Bitmap bmp2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
                    iv2=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));
                }
                else if(imageView3.getDrawable()!=null)
                {
                    Bitmap bmp3 = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
                    iv3=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp3));
                }

//
                editor.putString("company", Company);
                editor.putString("address", Address);
                editor.putString("model", Model);
                editor.putString("modelyear", ModelYear);
                editor.putString("receivername", ReceiverName);
                editor.putString("receiveraddress", ReceiverAddress);
                editor.putString("receivercellphone", ReceiverCellphone);
                editor.putString("post", Post);
                editor.putString("Good_total", Good_total);
                editor.putString("companypic1",iv1 );
                editor.putString("companypic2",iv2 );
                editor.putString("lisencepic",iv3);
                editor.putString("demand", Demand);

                editor.apply();

                Intent intent = new Intent(WriteOrderActivity.this, CarModelActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNewRow(table);
            }
        });

        //  initDictItemTable();



    }
    private void appendNewRow(final TableLayout table) {
        TableRow row = new TableRow(this);
        row.setId(number_row);//         1，行数的id

        // orders++;
        EditText dictName = new EditText(this);
        dictName.setText("");
        dictName.setHint("配件名称");
        dictName.setTextSize(14);
//      dictName.setHorizontallyScrolling(true);
        dictName.setInputType(InputType.TYPE_CLASS_TEXT);
        dictName.setWidth(390);
        dictName.setGravity(Gravity.CENTER);

        EditText dictNum = new EditText(this);
        dictNum.setText("");
        dictNum.setHint("数量");
        dictNum.setTextSize(14);
//        dictNum.setHorizontallyScrolling(true);
        dictNum.setInputType(InputType.TYPE_CLASS_NUMBER);
        dictNum.setWidth(160);
        dictNum.setGravity(Gravity.CENTER);

        EditText dictRemark = new EditText(this);
        dictRemark.setText("");
        dictRemark.setHint("备注");
        dictRemark.setTextSize(14);
//        dictRemark.setHorizontallyScrolling(true);
        dictRemark.setInputType(InputType.TYPE_CLASS_TEXT);
        dictRemark.setWidth(390);
        dictRemark.setGravity(Gravity.CENTER);

        Button oper = new Button(this);
        oper.setId(number_row + 1000);//         2，按键的id，id加1000为了和行数的id分开，当点添加按钮的时候，每个删除按键也就被赋上了不同的id
        //      当number_row变化，不同的删除按键已经被赋上唯一的id，所以在点击不同的删除按键时，可以识别是哪一个按键
//        oper.setText("删除");
//        oper.setTextSize(12);
//       oper.setBackgroundColor(0);
//        oper.setHeight(40);
//        oper.setWidth(40);
        oper.setGravity(Gravity.CENTER);
//        oper.setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_ic_close_pressed_holo_light));
        oper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(v.getId());
                table.removeView(table.findViewById(v.getId() - 1000));//      3，v.getId()得到的是button的id，此处设置的id是与button关
                // 联的id，减1000是为了与行数的id保持吻合，删除的即为本行
                // 可以把内容删除
            }
        });
        number_row++;
//        Toast.makeText(getApplicationContext(),"第几次"+number_row,Toast.LENGTH_SHORT).show();
        row.addView(dictName);
        row.addView(dictNum);
        row.addView(dictRemark);
        row.addView(oper);
        table.addView(row);
    }

    public void initItemTable() {

//        String[] strings1={"11","21","31","41","51","61"};
//        String[] strings2={"12","22","32","42","52","62"};

        for(number_row=0;number_row<strings_all.length;number_row++){
            TableRow row= new TableRow(this);
            row.setId(number_row);


            EditText good_name = new EditText(this);
            good_name.setText(strings1[number_row]);
            good_name.setPadding(3, 3, 3, 3);
            good_name.setWidth(390);
            good_name.setGravity(Gravity.CENTER);

            EditText good_number = new EditText(this);
            good_number.setText(strings2[number_row]);
            good_number.setPadding(3, 3, 3, 3);
            good_number.setWidth(160);
            good_number.setGravity(Gravity.CENTER);

            EditText good_describe = new EditText(this);
            good_describe.setText(strings3[number_row]);
            good_describe.setPadding(3, 3, 3, 3);
            good_describe.setWidth(360);
            good_describe.setGravity(Gravity.CENTER);

            Button oper = new Button(this);
            oper.setId(number_row + 1000); // 设置这个Id,用于计算得到TableRow的Id.
            oper.setText(" 删除 ");
            oper.setPadding(3, 3, 3, 3);
            oper.setWidth(60);
            oper.setGravity(Gravity.CENTER);
            oper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(v.getId());
                    table.removeView(table.findViewById(v.getId() - 1000));
                }
            });

            row.addView(good_name);
            row.addView(good_number);
            row.addView(good_describe);
            row.addView(oper);

            table.addView(row, new TableLayout.LayoutParams());
        }
    }









//


    private Boolean  OrderDetail()
    {
        UserName=preferencesuserinf.getString("username", null);
        Model = modelTv.getText().toString();
        ModelYear = modelYearEt.getText().toString();
        ReceiverName = receivernameEt.getText().toString();
        ReceiverAddress = receiveraddressEt.getText().toString();
        ReceiverCellphone = receivercellphoneEt.getText().toString();
        Post = postEt.getText().toString();
        Good_total = "";
        for (int i = 0; i < table.getChildCount(); i++) {
            // 得到TableRow对象
            // Toast.makeText(getApplicationContext(),"--------"+Show,Toast.LENGTH_LONG).show();
            TableRow row = (TableRow) table.getChildAt(i);
            String dictName = ((TextView) row.getChildAt(0))
                    .getText().toString();
            String dictNum = ((EditText) row.getChildAt(1))
                    .getText().toString();
            String dictRemark = ((EditText) row.getChildAt(2))
                    .getText().toString();
            Good_total = Good_total + dictName + "<"+dictNum+">" + dictRemark+"/";
        }
        Demand = demandEt.getText().toString();
        if(imageView1.getDrawable()!=null)
        {
            Bitmap bmp1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();
            iv1=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
        }
         if(imageView2.getDrawable()!=null)
        {
            Bitmap bmp2 = ((BitmapDrawable)imageView2.getDrawable()).getBitmap();
            iv2=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));
        }
        if(imageView3.getDrawable()!=null)
        {
            Bitmap bmp3 = ((BitmapDrawable)imageView3.getDrawable()).getBitmap();
            iv3=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp3));
        }




        JSONObject jsonObj;
        try
        {
            jsonObj = query(UserName,Model,ModelYear,ReceiverName,ReceiverAddress,ReceiverCellphone,Post,Good_total,Demand,iv1,iv2,iv3);

            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_SHORT).show();

            if (jsonObj.getString("reback") .equals("添加成功"))
            {
                Toast.makeText(getApplicationContext(),"订单提交成功",Toast.LENGTH_SHORT).show();

                return true;
            }
            else if(jsonObj.getString("reback") .equals("添加失败"))
            {
                Toast.makeText(getApplicationContext(),"订单提交失败",Toast.LENGTH_SHORT).show();

                return false;
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject query(String UserName,String Model,String ModelYear,String ReceiverName,String ReceiverAddress,
                             String ReceiverCellphone,String Post,String Good_total ,String Demand,String iv1,String iv2,String iv3)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("username",UserName);
        map.put("cartype",Model );
        map.put("address",ReceiverAddress );
        map.put("caryear",ModelYear);
        map.put("recperson",ReceiverName );
        map.put("recpersonnum",ReceiverCellphone );
        map.put("postnum",Post );
        map.put("orderdetail",Good_total );
        map.put("demand",Demand );
        map.put("normalPicNum","2");
        map.put("carDisPic",iv3);
        map.put("normalpic0",iv1);
        map.put("normalpic1",iv2);

        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "addorder.jsp";
        // 发送请求

        return new JSONObject(HttpUtil.postRequest(url, map));
    }

    /**
     * 拍照按钮，点击事件
     *
     */
    public void btn_onclick_writeorder_photo1(View v) {

        a=1;
        setView();


    }
    public void btn_onclick_writeorder_photo2(View v) {

        a=2;
        setView();


    }
    public void btn_onclick_writeorder_photo3(View v) {

        a=3;
        setView();

    }


    /**
     * 获取原图片存储路径
     * @return
     */
    private String getPhotopath() {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory()+"/mymyphone/";
        String imageName = "imageTest.jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }
    /**
     * 拍照或从相册拿照片
     */
    public void setView() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                WriteOrderActivity.this);
        String[] strs = { "拍照上传", "相册选取", "取消" };
        builder.setItems(strs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intentPhote = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intentPhote.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        File out = new File(getPhotopath());
                        Uri uri = Uri.fromFile(out);
                        // 获取拍照后未压缩的原图片，并保存在uri路径中
                        intentPhote.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                intentPhote.putExtra(MediaStore.Images.Media.ORIENTATION, 180);
                        startActivityForResult(intentPhote, 1);
                        break;
                    case 1:
                        Intent picture = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 2);
                        break;
                    case 2:
                        Toast.makeText(WriteOrderActivity.this, "关闭对话框", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//
////
//        Bitmap bitmap ;
        File outDir = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
// 这个路径，在手机内存下创建一个pictures的文件夹，把图片存在其中。
            outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            if (null != WriteOrderActivity.this) {
                outDir = this.getFilesDir();
            }
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        if(requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final   Bitmap camerabitmap = getBitmapFromUrl(getPhotopath(), 313.5, 462.0);//压缩
            saveScalePhoto(camerabitmap);
            {
                switch(a) {
                    case 1:
                        imageView1.setImageBitmap(camerabitmap);
//                        imageView1.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getBigPicture(camerabitmap);
//                            }
//                        });
                        break;
                    case 2:
                        imageView2.setImageBitmap(camerabitmap);
//                        imageView2.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getBigPicture(camerabitmap);
//                            }
//                        });
                        break;
                    case 3:
                        imageView3.setImageBitmap(camerabitmap);
//                        imageView3.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                getBigPicture(camerabitmap);
//                            }
//                        });
                        break;
                }
            }
        }
        else {
            if(requestCode == 2 && data != null
                    && resultCode == Activity.RESULT_OK)
            {

                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c = WriteOrderActivity.this.getContentResolver().query(
                            selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    picturePath = c.getString(columnIndex);// 取出图片路径
                    Log.e("1", "图片路径" + picturePath);
                    Toast.makeText(getApplicationContext(),"图片路径"+picturePath,Toast.LENGTH_LONG).show();
                    c.close();
                    outFile = new File(outDir, System.currentTimeMillis() +
                            ".png");

                    // 展示图片并点击放大

                    // 调用压缩方法压缩图片
//                    final   Bitmap camerabitmap = getBitmapFromUrl(getPhotopath(), 313.5, 462.0);//压缩
//                    saveScalePhoto(camerabitmap);
                    final Bitmap bitmap = createThumbnail(picturePath, 313.5,462.0);
                    saveScalePhoto2(bitmap);
                    {
                        switch(a) {
                            case 1:
                                imageView1.setImageBitmap(bitmap);
//                                imageView1.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        getBigPicture(bitmap);
//                                    }
//                                });
                                break;
                            case 2:
                                imageView2.setImageBitmap(bitmap);
//                                imageView2.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        getBigPicture(bitmap);
//                                    }
//                                });
                                break;
                            case 3:
                                imageView3.setImageBitmap(bitmap);
//                                imageView3.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        getBigPicture(bitmap);
//                                    }
//                                });
                                break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    /**
     * 根据路径获取图片资源（已缩放）
     * @param url 图片存储路径
     * @param width 缩放的宽度
     * @param height 缩放的高度
     * @return
     */
    //拍摄的照片进行压缩
    private Bitmap getBitmapFromUrl(String url, double width, double height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;

        if(mWidth <= mHeight) {
            scaleWidth = (float) (width/mWidth);
            scaleHeight = (float) (height/mHeight);
        } else {
            scaleWidth = (float) (height/mWidth);
            scaleHeight = (float) (width/mHeight);
        }

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);

        bitmap.recycle();
        return newBitmap;
    }

    /**
     * 存储缩放的图片
     // * @param data 图片数据
     */
    private void saveScalePhoto(Bitmap bitmap) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory().getPath()+"/mymyphone/";
        Toast.makeText(getApplicationContext(),pathUrl,Toast.LENGTH_LONG).show();
        String imageName = "imageScale.jpg";
        FileOutputStream fos = null;
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 存储缩放的图片
     // * @param data 图片数据
     */
    private void saveScalePhoto2(Bitmap bitmap) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory().getPath()+"/exist_photo_path/";
        Toast.makeText(getApplicationContext(),pathUrl,Toast.LENGTH_LONG).show();
        String imageName = "imageScale.jpg";
        FileOutputStream fos = null;
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        try {
            fos = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
//    /**
//     * 点击图片放大查看
//     *
//     */
//    private void getBigPicture(Bitmap b) {
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
//        final AlertDialog dialog = new AlertDialog.Builder(this).create();
//        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
//        if (b != null) {
//            Display display = WriteOrderActivity.this.getWindowManager()
//                    .getDefaultDisplay();
//            int scaleWidth = display.getWidth();
//            int height = b.getHeight();// 图片的真实高度
//            int width = b.getWidth();// 图片的真实宽度
//            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
//            lp.width = scaleWidth;// 调整宽度
//            lp.height = (height * scaleWidth) / width;// 调整高度
//            img.setLayoutParams(lp);
//            img.setImageBitmap(b);
//            dialog.setView(imgEntryView); // 自定义dialog
//            dialog.show();
//        }
//    }
    /**
     * 压缩图片
     *
     *
     */
    //从相册中取出的图片进行压缩
    private Bitmap createThumbnail(String filepath, double width,double height) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = i;
//        return BitmapFactory.decodeFile(filepath, options);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;

        if(mWidth <= mHeight) {
            scaleWidth = (float) (width/mWidth);
            scaleHeight = (float) (height/mHeight);
        } else {
            scaleWidth = (float) (height/mWidth);
            scaleHeight = (float) (width/mHeight);
        }

        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);

        bitmap.recycle();
        return newBitmap;

    }




//   复写手机退出按钮提示消息
//@Override
//public void onBackPressed() {
//
//    // TODO Auto-generated method stub
//    setContentView(R.layout.buy_writeorder);
//    //获取文本框和图片框
//    final EditText addressEt = (EditText) findViewById(R.id.address_et);
//    final TextView modelTv = (TextView) findViewById(R.id.model_et);
//    final EditText companyEt = (EditText) findViewById(R.id.company_et);
//    final EditText accessoryEt1 = (EditText) findViewById(R.id.table1_2);
//    final EditText accessoryEt2 = (EditText) findViewById(R.id.table2_2);
//    final EditText accessoryEt3 = (EditText) findViewById(R.id.table3_2);
//    final EditText accessoryEt4 = (EditText) findViewById(R.id.table4_2);
//    final EditText accessoryNum1 = (EditText) findViewById(R.id.table1_3);
//    final EditText accessoryNum2 = (EditText) findViewById(R.id.table2_3);
//    final EditText accessoryNum3 = (EditText) findViewById(R.id.table3_3);
//    final EditText accessoryNum4 = (EditText) findViewById(R.id.table4_3);
//    final EditText accessoryRemark1 = (EditText) findViewById(R.id.table1_4);
//    final EditText accessoryRemark2= (EditText) findViewById(R.id.table2_4);
//    final EditText accessoryRemark3 = (EditText) findViewById(R.id.table3_4);
//    final EditText accessoryRemark4 = (EditText) findViewById(R.id.table4_4);
//    final EditText earnestEt = (EditText) findViewById(R.id.orderMoney_et);
//    final EditText demandEt = (EditText) findViewById(R.id.demand_et);
//    final EditText modelYearEt = (EditText) findViewById(R.id.model_time_et);
//
//
//    //获取EditText里面的内容
//    Address = addressEt.getText().toString();
//    Model = modelTv.getText().toString();
//    Company = companyEt.getText().toString();
//    Accessory1 = accessoryEt1.getText().toString();
//    Accessory2 = accessoryEt2.getText().toString();
//    Accessory3 = accessoryEt3.getText().toString();
//    Accessory4= accessoryEt4.getText().toString();
//    AccessoryNum1= accessoryNum1.getText().toString();
//    AccessoryNum2= accessoryNum2.getText().toString();
//    AccessoryNum3= accessoryNum3.getText().toString();
//    AccessoryNum4= accessoryNum4.getText().toString();
//    AccessoryRemark1= accessoryRemark1.getText().toString();
//    AccessoryRemark2= accessoryRemark2.getText().toString();
//    AccessoryRemark3= accessoryRemark3.getText().toString();
//    AccessoryRemark4= accessoryRemark4.getText().toString();
//
//    Demand = demandEt.getText().toString();
//    ModelYear =modelYearEt.getText().toString();
////
//
//    editor.putString("address", Address);
//    editor.putString("model", Model);
//    editor.putString("company", Company);
//    editor.putString("accessory1", Accessory1);
//    editor.putString("accessory2",Accessory2);
//    editor.putString("accessory3",Accessory3);
//    editor.putString("accessory4",Accessory4);
//    editor.putString("accessorynum1",AccessoryNum1);
//    editor.putString("accessorynum2",AccessoryNum2);
//    editor.putString("accessorynum3",AccessoryNum3);
//    editor.putString("accessorynum4",AccessoryNum4);
//    editor.putString("accessoryremark1",AccessoryRemark1);
//    editor.putString("accessoryremark2",AccessoryRemark2);
//    editor.putString("accessoryremark3",AccessoryRemark3);
//    editor.putString("accessoryremark4",AccessoryRemark4);
//    editor.putString("earnest",Earnest);
//    editor.putString("demand", Demand);
//    editor.putString("modelyear", ModelYear);
//    editor.commit();
//
//
//    finish();
//

    //super.onBackPressed();

//}












}
