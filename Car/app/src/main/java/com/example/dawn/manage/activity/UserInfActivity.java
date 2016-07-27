package com.example.dawn.manage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.common.MapCodeCovert;
import com.example.dawn.manage.bean.User;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;
import com.example.dawn.buyer.activity.WriteOrderActivity;
import com.example.dawn.car.R;
import com.example.dawn.car.activity.MainActivity;
import com.example.dawn.car.fragment.SetFragment;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by wanxiao on 2015/10/29.
 */
public class UserInfActivity extends Activity
{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RadioGroup rg;
    EditText password;
    EditText passwordcheck;
    EditText  username;
    TextView cellphone;
    EditText  storename;
    EditText legalperson;
    EditText  useraddress;
    EditText  post;
    EditText  zhifubao;
    EditText  wechat;
    EditText  qq;
    EditText  email;
    ImageView image1;
    ImageView image2;
    ImageView imagebit1;
    ImageView imagebit2;
    Byte byteimage1;
    Byte byteimage2;
    String tip;
    private int a;
    String picturePath = null;
    File outFile = null;

    private UserService userService = new UserServiceImp();

    //    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_user_inf);

//        preferences=this.getSharedPreferences("uesrinf", MODE_PRIVATE);
//        editor=preferences.edit();

        rg=(RadioGroup)findViewById(R.id.radiogroup);
        username=(EditText)findViewById(R.id.manage_user_inf_username);
        cellphone=(TextView)findViewById(R.id.manage_user_inf_cellphone);
        password=(EditText)findViewById(R.id.manage_user_inf_password);
        passwordcheck=(EditText)findViewById(R.id.manage_user_inf_passwordcheck);
        storename=(EditText)findViewById(R.id.manage_user_inf_storename);
        legalperson=(EditText)findViewById(R.id.manage_user_inf_legalperson);
        useraddress=(EditText)findViewById(R.id.manage_user_inf_address);
        post=(EditText)findViewById(R.id.manage_user_inf_post);
        zhifubao=(EditText)findViewById(R.id.manage_user_inf_zhifubao);
        wechat=(EditText)findViewById(R.id.manage_user_inf_wechat);
        qq=(EditText)findViewById(R.id.manage_user_inf_qq);
        email=(EditText)findViewById(R.id.manage_user_inf_email);
        image1=(ImageView)findViewById(R.id.manage_user_inf_image1);
        image2=(ImageView)findViewById(R.id.manage_user_inf_image2);


//        //读取 SharedPreferences中的数据
//        // name.setText(preferences.getString("name", null));
//        cellphone.setText(preferences.getString("cellphone", null));
//        password.setText(preferences.getString("password",null));
//        passwordcheck.setText(preferences.getString("passwordcheck",null));
//        storename.setText(preferences.getString("storename", null));
//        legalperson.setText(preferences.getString("legalperson",null));
//        useraddress.setText(preferences.getString("useraddress", null));
//        post.setText(preferences.getString("post", null));
//        zhifubao.setText(preferences.getString("zhifubao", null));
//        wechat.setText(preferences.getString("wechat", null));
//        qq.setText(preferences.getString("qq", null));
//        email.setText(preferences.getString("email", null));

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                tip = checkedId == R.id.radioButton1 ?  "汽修厂": "经销商";

            }

        });

        Intent intent1=getIntent();
        final Bundle data=intent1.getExtras();
        cellphone.setText(data.getString("cellphonenum"));





//        // String Name=name.getText().toString();
//        String CellPhone=cellphone.getText().toString();
//        String Password=password.getText().toString();
//        String PasswordCheck=passwordcheck.getText().toString();
//        String StoreName=storename.getText().toString();
//        String LegalPerson=legalperson.getText().toString();
//        String UserAddress=useraddress.getText().toString();
//        String Post=post.getText().toString();
//        String ZhiFUBao=zhifubao.getText().toString();
//        String WeChat=wechat.getText().toString();
//        String QQ=qq.getText().toString();
//        String Email=email.getText().toString();

//        //返回键
//        Button back =(Button)findViewById(R.id.manage_user_inf_bt);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // String Name=name.getText().toString();
//                String CellPhone=cellphone.getText().toString();
//                String Password=password.getText().toString();
//                String PasswordCheck=passwordcheck.getText().toString();
//                String StoreName=storename.getText().toString();
//                String LegalPerson=legalperson.getText().toString();
//                String UserAddress=useraddress.getText().toString();
//                String Post=post.getText().toString();
//                String ZhiFUBao=zhifubao.getText().toString();
//                String WeChat=wechat.getText().toString();
//                String QQ=qq.getText().toString();
//                String Email=email.getText().toString();
//
//
//
//                // editor.putString("name", Name);
//                editor.putString("cellphone",CellPhone );
//                editor.putString("password",Password);
//                editor.putString("passwordcheck",PasswordCheck);
//                editor.putString("storename",StoreName );
//                editor.putString("legalperson",LegalPerson);
//                editor.putString("address",UserAddress );
//                editor.putString("post", Post);
//                editor.putString("zhifubao", ZhiFUBao);
//                editor.putString("wechat", WeChat);
//                editor.putString("qq", QQ);
//                editor.putString("email", Email);
//                editor.commit();
//
//
//
//
//                finish();
//
//            }
//        });



        Button finishbt=(Button) findViewById(R.id.manager_user_finish_button);
        finishbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String Name=name.getText().toString();

//                Intent intent1=getIntent();
//                final Bundle data=intent1.getExtras();
//                cellphone.setText(data.getString("cellphonenum"));

                String CellPhone=cellphone.getText().toString();
                Toast.makeText(getApplicationContext(),"电话号码："+CellPhone,Toast.LENGTH_LONG).show();
                String UserName=username.getText().toString();
                String Password=password.getText().toString();
                String PasswordCheck=passwordcheck.getText().toString();
                String StoreName=storename.getText().toString();
                String LegalPerson=legalperson.getText().toString();
                String UserAddress=useraddress.getText().toString();
                String Post=post.getText().toString();
                String ZhiFUBao=zhifubao.getText().toString();
                String WeChat=wechat.getText().toString();
                String QQ=qq.getText().toString();
                String Email=email.getText().toString();
                if(Password.equals(PasswordCheck))
                {
                    if(!userService.isValidEmail(Email))
                    {
                        Toast.makeText(getApplicationContext(),"邮箱格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(!userService.isZipNO(Post))
                        {
                            Toast.makeText(getApplicationContext(),"邮编格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            if(!userService.checkusername(UserName))
                            {
                                Toast.makeText(getApplicationContext(),"用户名格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                if(!"".equals(tip)&&!"".equals(UserName)&&!"".equals(CellPhone)&& !"".equals(Password)&& !"".equals(PasswordCheck)&&
                                        !"".equals(StoreName)&& !"".equals(LegalPerson)&& !"".equals(UserAddress)&& !"".equals(Post)&&
                                        (!"".equals(ZhiFUBao)||!"".equals(WeChat)))
                                {
                                    if(finishPro())
                                    {
                                        Intent intent =new Intent(UserInfActivity.this,MainActivity.class);
                                        intent.putExtra("fragid",1);
                                        startActivity(intent);
                                        finish();
                                    }


                                }
                                else
                                {
                                    Toast.makeText(UserInfActivity.this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }

                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"两次输入密码不一致，请重新输入密码！",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private Boolean  finishPro()
    {
        // 获取用户输入的用户名、密码
        String UserName=username.getText().toString();
        String CellPhone=cellphone.getText().toString();
        String Password=password.getText().toString();
        String PasswordCheck=passwordcheck.getText().toString();
        String StoreName=storename.getText().toString();
        String LegalPerson=legalperson.getText().toString();
        String UserAddress=useraddress.getText().toString();
        String Post=post.getText().toString();
        String ZhiFUBao=zhifubao.getText().toString();
        String WeChat=wechat.getText().toString();
        String QQ=qq.getText().toString();
        String Email=email.getText().toString();
        String registerStep="用户信息";

        ImageView image1=(ImageView)findViewById(R.id.manage_user_inf_image1);
        Bitmap bmp = ((BitmapDrawable)image1.getDrawable()).getBitmap();
        ImageView image2=(ImageView)findViewById(R.id.manage_user_inf_image2);
        Bitmap bmp1 = ((BitmapDrawable)image2.getDrawable()).getBitmap();


        String Byteimage1= MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp));
        String Byteimage2=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));


        JSONObject jsonObj;
        try
        {
            jsonObj = query(registerStep,tip,CellPhone,UserName,Password,StoreName,LegalPerson
                    ,UserAddress,Post,ZhiFUBao,WeChat,QQ,Email,Byteimage1,Byteimage2);

            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_SHORT).show();

            if (jsonObj.getString("reback") .equals("成功"))
            {
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();

                return true;
            }
            else if(jsonObj.getString("reback") .equals("失败"))
            {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();

                return false;
            }
            else if(jsonObj.getString("reback") .equals("用户名已存在"))
            {
                Toast.makeText(getApplicationContext(),"用户名已存在，请重新填写用户名",Toast.LENGTH_SHORT).show();

                return false;
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    private JSONObject query(String registerStep,String tip, String CellPhone,String UserName ,String Password,String StoreName,String LegalPerson,
                             String UserAddress,String Post,String ZhiFUBao,String WeChat, String QQ,  String Email ,
                             String Byteimage1,String Byteimage2)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();
        map.put("registerStep", registerStep);
        map.put("usertype", tip);
        map.put("phoneNumber",CellPhone);
        map.put("username",UserName);
        map.put("pass", Password);
        map.put("company", StoreName);
        map.put("legalpersonname",LegalPerson );
        map.put("address",UserAddress );
        map.put("postnum", Post);
        map.put("alipaynum",ZhiFUBao);
        map.put("weixinnum",WeChat );
        map.put("qq",QQ );
        map.put("email",Email );
        map.put("lisencepic",Byteimage1);
        map.put("companypic",Byteimage2);

        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "register.jsp";
        // 发送请求

        return new JSONObject(HttpUtil.postRequest(url, map));
    }




    /**
     * 拍照按钮，点击事件
     *
     */
    public void btn_onclick_manage_user_image1(View v) {
        setView();
        a=1;

    }
    public void btn_onclick_manage_user_image2(View v) {
        setView();
        a=2;
    }


    /**
     * 拍照或从相册拿照片
     */
    public void setView() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                UserInfActivity.this);
        String[] strs = { "拍照上传", "相册选取", "取消" };
        builder.setItems(strs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent camera = new Intent(
                                "android.media.action.IMAGE_CAPTURE");
                        startActivityForResult(camera, 1);
                        break;
                    case 1:
                        Intent picture = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 2);
                        break;
                    case 2:
                        Toast.makeText(UserInfActivity.this, "关闭对话框", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    /**
     * 1取出拍照的结果存储到手机内存则pictures文件夹， 再从文件加下取出展示到界面，并且点击放大
     * 2从相册中取出图片，压缩，放到dialog中，然后去出展示到界面 点击放大。
     */


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        File outDir = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
// 这个路径，在手机内存下创建一个pictures的文件夹，把图片存在其中。
            outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            if (null != UserInfActivity.this) {
                outDir = this.getFilesDir();
            }
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
// 直接获取照片，data是系统默认的（在系统中已经压缩过图片取出来就行了）；
                bitmap = (Bitmap) data.getExtras().get("data");
//                if (bitmap != null) {                                   //删除
//                    showImageDialog(bitmap, "1", "");                          //删除
//                } else {                                                     //删除
//                    Toast.makeText(UserInfActivity.this, "图片获取失败，请重新选择", Toast.LENGTH_SHORT)  //删除
//                            .show();                                          //删除
//                }                                                          //删除
// 保存图片
                try {
                    outFile = new File(outDir, System.currentTimeMillis()
                            + ".png");
                    FileOutputStream fos = new FileOutputStream(outFile);
                    assert bitmap != null;
                    boolean flag = bitmap.compress(Bitmap.CompressFormat.JPEG,
                            100, fos);// 把数据写入文件
                    Log.i("1", "flag=" + flag);
                    if (flag) {
                        Toast.makeText(UserInfActivity.this,
                                "图片已保存至:" + outFile.getAbsolutePath(), Toast.LENGTH_SHORT)
                                .show();
// 展示图片，点击放大
                        String pathname = outFile.getAbsolutePath();// 绝对路径
                        final Bitmap myBitmap = BitmapFactory
                                .decodeFile(pathname);
                        switch (a) {
                            case 1:
                                image1.setImageBitmap(bitmap);
                                image1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getBigPicture(myBitmap);// 点击放大
                                    }
                                });
                                break;
                            case 2:
                                image2.setImageBitmap(bitmap);
                                image2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getBigPicture(myBitmap);// 点击放大
                                    }
                                });
                                break;
                        }

                    } else {
                        Toast.makeText(UserInfActivity.this, "图片保存失败!", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }// 相册显示界面
        else {
            if (requestCode == 2 && data != null
                    && resultCode == Activity.RESULT_OK) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumns = {MediaStore.Images.Media.DATA};
                    Cursor c =UserInfActivity.this.getContentResolver().query(
                            selectedImage, filePathColumns, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    picturePath = c.getString(columnIndex);// 取出图片路径
                    Log.e("1", "图片路径" + picturePath);
                    c.close();
// 调用压缩方法压缩图片
                    bitmap = createThumbnail(picturePath, 4);
// 保存图片到pictures文件夹下,上传的时候还要上传outFile
                    outFile = new File(outDir, System.currentTimeMillis() +
                            ".png");
                    FileOutputStream fos = new FileOutputStream(outFile);                 //删除
                    boolean flag = bitmap.compress(Bitmap.CompressFormat.JPEG,               // 删除
                            100, fos);// 把数据写入文件                                //删除
                    // 选择图片后显示在对话框内
                    showImageDialog(bitmap, "1", "");                                      //删除
                    // 展示图片并点击放大
                    String pathname = outFile.getAbsolutePath();//绝对路径
                    final Bitmap myBitmap = BitmapFactory.decodeFile(pathname);

                    switch(a)
                    {
                        case 1:
                            image1.setImageBitmap(myBitmap);
//                            image1.setOnClickListener(new View.OnClickListener() {        //删除
//                                @Override
//                                public void onClick(View v) {
//                                    getBigPicture(myBitmap);
//                                }
//                            }
//                            );
                            break;
                        case 2:
                            image2.setImageBitmap(myBitmap);
//                            image2.setOnClickListener(new View.OnClickListener() {      //删除
//                                @Override
//                                public void onClick(View v) {
//                                    getBigPicture(myBitmap);
//                                }
//                            });
                            break;
                    }

////自己添加
//                imageView2.setImageBitmap(myBitmap);
//                imageView2.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getBigPicture(myBitmap);
//                    }
//                });
//
////自己添加
//                imageView3.setImageBitmap(myBitmap);
//                imageView3.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getBigPicture(myBitmap);
//                    }
//                });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    /**
     *
     * 展示图片的dialog
     */
    private void showImageDialog(Bitmap b, final String orderNo,
                                 final String chepaihao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserInfActivity.this);


        builder.setTitle("请上传包含车牌的清晰照片");
        View imgEntryView = View.inflate(UserInfActivity.this,
                R.layout.buy_writeorder_photo_magnify, null); // 加载自定义的布局文件
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        if (b != null) {
            Display display = UserInfActivity.this.getWindowManager()
                    .getDefaultDisplay();
            int scaleWidth = display.getWidth();
            int height = b.getHeight();// 图片的真实高度
            int width = b.getWidth();// 图片的真实宽度
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
            lp.width = scaleWidth;// 调整宽度
            lp.height = (height * scaleWidth) / width;// 调整高度
            img.setLayoutParams(lp);
            img.setImageBitmap(b);
        } else {
            Toast.makeText(UserInfActivity.this, "获取照片失败", Toast.LENGTH_SHORT).show();
            img.setVisibility(View.GONE);
        }
        builder.setView(imgEntryView); }
    // 自定义dialog
    // builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (MainActivity.this != null) {
//// 判断网络
//                    if (isNetworkConnected(MainActivity.this)) {
//                        try {
///**
// * 上传照片，没有正确的参数不能正确上传
// *
// */
//                            String str_uploadImage = UploadFileUtil
//                                    .uploadImage(
//                                            MainActivity.this,
//                                            RequestUrl.upload,
//                                            params_image,
//                                            outFile);
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } else {
//                        Toast.makeText(MainActivity.this, "网络异常，请检查网络!", Toast.LENGTH_SHORT)
//                                .show();
//                    }
//                }
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.create().show();
//    }


    /**
     * 压缩图片
     *
     *
     */
    private Bitmap createThumbnail(String filepath, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        return BitmapFactory.decodeFile(filepath, options);
    }


    /**
     * 判断网络是否正常
     *
     *
     * @param context
     */
    public boolean isNetworkConnected(UserInfActivity context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 点击图片放大查看
     *
     */
    private void getBigPicture(Bitmap b) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View imgEntryView = inflater.inflate(R.layout.buy_writeorder_photo_magnify, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        if (b != null) {
            Display display = UserInfActivity.this.getWindowManager()
                    .getDefaultDisplay();
            int scaleWidth = display.getWidth();
            int height = b.getHeight();// 图片的真实高度
            int width = b.getWidth();// 图片的真实宽度
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) img.getLayoutParams();
            lp.width = scaleWidth;// 调整宽度
            lp.height = (height * scaleWidth) / width;// 调整高度
            img.setLayoutParams(lp);
            img.setImageBitmap(b);
            dialog.setView(imgEntryView); // 自定义dialog
            dialog.show();
        }
// 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View paramView) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        });
    }
}
