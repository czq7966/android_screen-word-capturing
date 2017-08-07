package com.nd.screen_word_capturing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.androidyuan.lib.screenshot.ScreenShotActivity;

import java.nio.ByteBuffer;


public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;

    private Button bt_jietu;
    private ImageView imageView;
    private MediaProjection mediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionManager mediaProjectionManager;
    private static final int REQUESTRESULT = 0x100;
    private int mWidth;
    private int mHeight;
    private int mScreenDensity;
    private ImageReader mImageReader;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //General.test(MainActivity.this);
                //new Thread(networkTask).start();
                //NotificationIntentService.startActionFoo(MainActivity.this, "1", "2");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //sendBroadcast(new Intent("com.nd.screen_word_capturing.start"));
                //startActivity(new Intent("com.androidyuan.lib.screenshot.ScreenShotActivity"));
                //startActivity(new Intent(this, ScreenShotActivity.class));
                //startActivity(new Intent(this, ScreenShotActivity.class));
                //onClickShot(view);
                getImage();
            }
        });
        initData();
        initView();
        if (canDrawOverlays())
            showView();

    }

    public void onClickShot(View view)
    {

        startActivity(new Intent(this, ScreenShotActivity.class));
    }

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            // 在这里进行 http request.网络请求相关操作
            //General.test(MainActivity.this);
            //Message msg = new Message();
            //Bundle data = new Bundle();
            //data.putString("value", "请求结果");
            //msg.setData(data);
//            handler.sendMessage(msg);
            Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            General.test(image, mLayout);
        }
    };

    private void initData() {
        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);

        mWidth = size.x;// display.getRealSize(); 1080;//display.getWidth();
        mHeight = size.y;//1920;//display.getHeight();
        DisplayMetrics outMetric = new DisplayMetrics();
        display.getMetrics(outMetric);
        mScreenDensity = (int) outMetric.density;
        Intent intent = new Intent(mediaProjectionManager.createScreenCaptureIntent());
        startActivityForResult(intent,REQUESTRESULT);
    }

    private void initView() {
        imageView = (ImageView) this.findViewById(R.id.imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mImageReader = ImageReader.newInstance(mWidth,mHeight, PixelFormat.RGBA_8888, 2);
            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode,data);
            mVirtualDisplay = mediaProjection.createVirtualDisplay("mediaprojection",mWidth,mHeight,
                    mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,mImageReader.getSurface(),null,null);

        }
    }

    public void getImage(){
        Image image = mImageReader.acquireLatestImage();
        int width = image.getWidth();
        int height = image.getHeight();
        final Image.Plane[] planes = image.getPlanes();
        final ByteBuffer buffer = planes[0].getBuffer();
        int pixelStride = planes[0].getPixelStride();
        int rowStride = planes[0].getRowStride();
        int rowPadding = rowStride - pixelStride * width;
        Bitmap bitmap = Bitmap.createBitmap(width+rowPadding/pixelStride, height,
                Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(buffer);
//        bitmap = Bitmap.createBitmap(bitmap, 0, 0,width, height);
        image.close();
        imageView.setImageBitmap(bitmap);
        new Thread(networkTask).start();

    }

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams param;
    private FloatView mLayout;

    private void showView(){
        mLayout=new FloatView(getApplicationContext());
        mLayout.setBackgroundResource(R.drawable.board);
        //获取WindowManager
        mWindowManager=(WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        param = ((MyApplication)getApplication()).getMywmParams();

        param.type=WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;     // 系统提示类型,重要
        param.format=1;
        param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        param.flags = param.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制

        param.alpha = 1.0f;

        param.gravity= Gravity.LEFT|Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        param.x=0;
        param.y=0;

        //设置悬浮窗口长宽数据
        param.width=200;
        param.height=100;

        //显示myFloatView图像
        mWindowManager.addView(mLayout, param);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        //在程序退出(Activity销毁）时销毁悬浮窗口
        mWindowManager.removeView(mLayout);
    }


    public boolean canDrawOverlays() {

        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }
}
