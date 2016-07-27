package com.example.dawn.realseller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawn.car.R;
import com.example.dawn.common.MapCodeCovert;
import com.example.dawn.manage.activity.HttpUtil;
import com.example.dawn.manage.service.UserService;
import com.example.dawn.manage.service.UserServiceImp;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanxiao on 2015/11/9.
 */
public class ReSellEditInfActivity extends Activity
{
    SharedPreferences preferencesEdit;
    SharedPreferences.Editor editorEidt;


    private String usertype_t;
    private String username_t;
    private String phone_t;
    private String company_t;
    private String legalperson_t;
    private String address_t;
    private String post_t;
    private String zhifubao_t;
    private String wechat_t;
    private String qq_t;
    private String email_t;
    private String iv1_t,iv2_t;
    private UserService userService = new UserServiceImp();

    ImageView iv1;
    ImageView iv2;
    int a;
    String picturePath = null;
    File outFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realseller_manage_edit_inf);

        preferencesEdit=this.getSharedPreferences("userinf", MODE_PRIVATE);
        editorEidt=preferencesEdit.edit();


        final TextView usertype=(TextView)findViewById(R.id.manage_editinf_type);
        final TextView username=(TextView)findViewById(R.id.manage_editinf_id);
        final TextView phone=(TextView)findViewById(R.id.manage_editinf_phone);
        final EditText company=(EditText)findViewById(R.id.manage_editinf_company);
        final EditText legalperson=(EditText)findViewById(R.id.manage_editinf_legalperson);
        final EditText address=(EditText)findViewById(R.id.manage_editinf_address);
        final EditText post=(EditText)findViewById(R.id.manage_editinf_post);
        final EditText zhifubao=(EditText)findViewById(R.id.manage_editinf_zhifubao);
        final EditText wechat=(EditText)findViewById(R.id.manage_editinf_wechat);
        final EditText qq=(EditText)findViewById(R.id.manage_editinf_qq);
        final EditText email=(EditText)findViewById(R.id.manage_editinf_email);
        iv1=(ImageView)findViewById(R.id.manage_editinf_image1);
        iv2=(ImageView)findViewById(R.id.manage_editinf_image2);

        //读取 SharedPreferences中的数据
        usertype.setText(preferencesEdit.getString("usertype", null));
        username.setText(preferencesEdit.getString("username", null));
        phone.setText(preferencesEdit.getString("phoneNumber", null));
        company.setText(preferencesEdit.getString("company", null));
        legalperson.setText(preferencesEdit.getString("legalpersonname", null));
        address.setText(preferencesEdit.getString("address", null));
        post.setText(preferencesEdit.getString("postnum", null));
        zhifubao.setText(preferencesEdit.getString("alipaynum", null));
        wechat.setText(preferencesEdit.getString("weixinnum", null));
        qq.setText(preferencesEdit.getString("qq", null));
        email.setText(preferencesEdit.getString("email", null));
        String pic1=preferencesEdit.getString("lisencepic",null);
        String pic2=preferencesEdit.getString("companypic", null);
        byte[] bitmapArray1= Base64.decode(pic1, Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(bitmapArray1, 0,
                bitmapArray1.length);
        iv1.setImageBitmap(bitmap1);

        byte[] bitmapArray2= Base64.decode(pic2, Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(bitmapArray2, 0,
                bitmapArray2.length);
        iv2.setImageBitmap(bitmap2);


        usertype_t=usertype.getText().toString();
        username_t=username.getText().toString();
        phone_t=phone.getText().toString();
        company_t=company.getText().toString();
        legalperson_t=legalperson.getText().toString();
        address_t=address.getText().toString();
        post_t=post.getText().toString();
        zhifubao_t=zhifubao.getText().toString();
        wechat_t=wechat.getText().toString();
        qq_t=qq.getText().toString();
        email_t=email.getText().toString();
        iv1_t=pic1;
        iv2_t=pic2;





        /////////////////图片的点击更换图片功能
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        Button bn_back = (Button) findViewById(R.id.manage_editinf_bnback);
        bn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button bn_complete=(Button)findViewById(R.id.manage_editinf_bnedit);
        bn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        //ImageView类型的图片转化成Bitmap类型，然后通过编码转化成String类型
                Bitmap bmp1 = ((BitmapDrawable)iv1.getDrawable()).getBitmap();
                Bitmap bmp2 = ((BitmapDrawable)iv2.getDrawable()).getBitmap();
                String pic_temp1 = MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
                String pic_temp2 = MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));

                if( usertype_t.equals(usertype.getText().toString())&&username_t.equals(username.getText().toString())
                        &&phone_t.equals(phone.getText().toString())&&company_t.equals(company.getText().toString())
                        &&legalperson_t.equals(legalperson.getText().toString())&&address_t.equals(address.getText().toString())
                        &&post_t.equals(post.getText().toString())&&zhifubao_t.equals(zhifubao.getText().toString())
                        &&wechat_t.equals(wechat.getText().toString())&&qq_t.equals(qq.getText().toString())
                        &&email_t.equals(email.getText().toString())&&iv1_t.equals(pic_temp1)&&iv2_t.equals(pic_temp2))
                {
                    //没有做修改时
                    finish();
                }
                else {
                    //否则有所修改，判断格式是否正确，修改后内容保存到本地，且上传到服务器

                    if(!userService.isValidEmail(email.getText().toString()))
                    {
                        Toast.makeText(getApplicationContext(),"邮箱格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                        email.setFocusable(true);
                        email.requestFocus();
                    }
                    else
                    {
                        if(!userService.isZipNO(post.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"邮编格式不正确，请重新输入",Toast.LENGTH_LONG).show();
                            post.setFocusable(true);
                            post.requestFocus();
                        }
                        else
                        {
                            if(!"".equals(company.getText().toString())&&!"".equals(post.getText().toString())&&!"".equals(legalperson.getText().toString())
                                    &&!"".equals(address.getText().toString())&& (!"".equals(zhifubao.getText().toString())||!"".equals(wechat.getText().toString())))
                            {
                                if(EditInf())
                                {
                                    editorEidt.putString("username",username_t);
                                    editorEidt.putString("usertype",usertype_t);
                                    editorEidt.putString("phoneNumber",phone_t);
                                    editorEidt.putString("company",company_t);
//                                  editorEidt.putString("point",);//暂时还没有经销商评分的功能
                                    editorEidt.putString("legalpersonname",legalperson_t);
                                    editorEidt.putString("address",address_t);
                                    editorEidt.putString("alipaynum",zhifubao_t);
                                    editorEidt.putString("weixinnum",wechat_t);
                                    editorEidt.putString("email",email_t);
                                    editorEidt.putString("postnum",post_t);
                                    editorEidt.putString("qq",qq_t);
                                    editorEidt.putString("lisencepic",iv1_t);
                                    editorEidt.putString("companypic",iv2_t);
                                    editorEidt.commit();


                                    finish();
                                }
                            }
                            else
                            {
                                Toast.makeText(ReSellEditInfActivity.this,"请将信息填写完整",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                }

            }

        });

    }
    //    private void m_init(){
//        usertype_t=usertype.getText().toString();
//        username_t=username.getText().toString();
//        phone_t=phone.getText().toString();
//        company_t=company.getText().toString();
//        legalperson_t=legalperson.getText().toString();
//        address_t=address.getText().toString();
//        post_t=post.getText().toString();
//        zhifubao_t=zhifubao.getText().toString();
//        wechat_t=wechat.getText().toString();
//        qq_t=qq.getText().toString();
//        email_t=email.getText().toString();
//
//        //ImageView类型的图片转化成Bitmap类型，然后通过编码转化成String类型
//        Bitmap bmp1 = ((BitmapDrawable)iv1.getDrawable()).getBitmap();
//        Bitmap bmp2 = ((BitmapDrawable)iv2.getDrawable()).getBitmap();
//        iv1_t=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
//        iv2_t=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));
//
//   }
    private Boolean  EditInf()
    {
        // 获取用户修改后的信息
        String manageStep="修改信息";

        TextView username=(TextView)findViewById(R.id.manage_editinf_id);
        TextView usertype=(TextView)findViewById(R.id.manage_editinf_type);

        TextView phone=(TextView)findViewById(R.id.manage_editinf_phone);
        EditText company=(EditText)findViewById(R.id.manage_editinf_company);
        EditText legalperson=(EditText)findViewById(R.id.manage_editinf_legalperson);
        EditText address=(EditText)findViewById(R.id.manage_editinf_address);
        EditText post=(EditText)findViewById(R.id.manage_editinf_post);
        EditText zhifubao=(EditText)findViewById(R.id.manage_editinf_zhifubao);
        EditText wechat=(EditText)findViewById(R.id.manage_editinf_wechat);
        EditText qq=(EditText)findViewById(R.id.manage_editinf_qq);
        EditText email=(EditText)findViewById(R.id.manage_editinf_email);
        ImageView iv1=(ImageView)findViewById(R.id.manage_editinf_image1);
        ImageView iv2=(ImageView)findViewById(R.id.manage_editinf_image2);


        usertype_t=usertype.getText().toString();
        username_t=username.getText().toString();
        phone_t=phone.getText().toString();
        company_t=company.getText().toString();
        legalperson_t=legalperson.getText().toString();
        address_t=address.getText().toString();
        post_t=post.getText().toString();
        zhifubao_t=zhifubao.getText().toString();
        wechat_t=wechat.getText().toString();
        qq_t=qq.getText().toString();
        email_t=email.getText().toString();

        //ImageView类型的图片转化成Bitmap类型，然后通过编码转化成String类型
        Bitmap bmp1 = ((BitmapDrawable)iv1.getDrawable()).getBitmap();
        Bitmap bmp2 = ((BitmapDrawable)iv2.getDrawable()).getBitmap();
        iv1_t=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp1));
        iv2_t=MapCodeCovert.encode(MapCodeCovert.BitmaptoBytes(bmp2));

//        m_init();

        JSONObject jsonObj;
        try
        {
            jsonObj = query(manageStep, username_t,company_t,legalperson_t,address_t,post_t,zhifubao_t,wechat_t,qq_t,email_t,iv1_t,iv2_t);
            //Toast.makeText(getApplicationContext(),"555",Toast.LENGTH_SHORT).show();
            //  如果userId 大于0
            Toast.makeText(getApplicationContext(),"收的的回调码："+jsonObj.getString("reback"),Toast.LENGTH_LONG).show();

            if (jsonObj.getString("reback") .equals("修改失败"))
            {
                Toast.makeText(getApplicationContext(),"修改失败请重试",Toast.LENGTH_LONG).show();
                return false;
            }
            else
            {
//                Toast.makeText(getApplicationContext(),"验证码是："+checktemp,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"信息修改成功",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }
    // 对用户输入的用户名、密码进行校验
    // 定义发送请求的方法
    private JSONObject query(String manageStep,String username,String company,String legalperson,
                             String address,String zhifubao,String wechat,String qq,String email,String post,
                             String lisencepic,String companypic)
            throws Exception
    {
        // 使用Map封装请求参数
        Map<String, String> map = new HashMap<>();

        map.put("manageStep", manageStep);
        map.put("username",username);
        map.put("company",company);
        map.put("legalpersonname",legalperson);
        map.put("address",address);
        map.put("alipaynum",zhifubao);
        map.put("weixinnum",wechat);
        map.put("qq",qq);
        map.put("email",email);
        map.put("postnum",post);
        map.put("lisencepic",lisencepic);
        map.put("companypic",companypic);


        // 定义发送请求的URL
        String url = HttpUtil.BASE_URL + "manage.jsp";
        // 发送请求
//        Toast.makeText(getApplicationContext(),HttpUtil.postRequest(url, map),Toast.LENGTH_SHORT).show();
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
                ReSellEditInfActivity.this);
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
                        Toast.makeText( ReSellEditInfActivity.this, "关闭对话框", Toast.LENGTH_SHORT).show();
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
            if (null !=  ReSellEditInfActivity.this) {
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
                        Toast.makeText (ReSellEditInfActivity.this,
                                "图片已保存至:" + outFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
// 展示图片，点击放大
                        String pathname = outFile.getAbsolutePath();// 绝对路径
                        final Bitmap myBitmap = BitmapFactory
                                .decodeFile(pathname);
                        switch (a) {
                            case 1:
                                iv1.setImageBitmap(bitmap);
                                iv1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getBigPicture(myBitmap);// 点击放大
                                    }
                                });
                                break;
                            case 2:
                                iv2.setImageBitmap(bitmap);
                                iv2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getBigPicture(myBitmap);// 点击放大
                                    }
                                });
                                break;
                        }

                    } else {
                        Toast.makeText( ReSellEditInfActivity.this, "图片保存失败!", Toast.LENGTH_SHORT).show();
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
                    Cursor c = ReSellEditInfActivity.this.getContentResolver().query(
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
                            iv1.setImageBitmap(myBitmap);
//                            image1.setOnClickListener(new View.OnClickListener() {        //删除
//                                @Override
//                                public void onClick(View v) {
//                                    getBigPicture(myBitmap);
//                                }
//                            }
//                            );
                            break;
                        case 2:
                            iv2.setImageBitmap(myBitmap);
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
        AlertDialog.Builder builder = new AlertDialog.Builder( ReSellEditInfActivity.this);


        builder.setTitle("请上传包含车牌的清晰照片");
        View imgEntryView = View.inflate( ReSellEditInfActivity.this,
                R.layout.buy_writeorder_photo_magnify, null); // 加载自定义的布局文件
        ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
        if (b != null) {
            Display display =  ReSellEditInfActivity.this.getWindowManager()
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
            Toast.makeText( ReSellEditInfActivity.this, "获取照片失败", Toast.LENGTH_SHORT).show();
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
    public boolean isNetworkConnected( ReSellEditInfActivity context) {
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
            Display display = ReSellEditInfActivity.this.getWindowManager()
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
