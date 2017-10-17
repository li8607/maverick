package com.maverick.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.maverick.R;
import com.maverick.base.BaseDialogFragment;
import com.maverick.presenter.BasePresenter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by limingfei on 2017/6/12.
 */
public class MultifunctionalDialog extends BaseDialogFragment implements View.OnClickListener {

    private View img_save;
    private View share;
    private View cancel;
    private String mImgUrl;

    public static MultifunctionalDialog newInstance(String url) {
        MultifunctionalDialog mMultifunctionalDialog = new MultifunctionalDialog();

        Bundle bundle = new Bundle();
        bundle.putString("DATA", url);
        mMultifunctionalDialog.setArguments(bundle);

        return mMultifunctionalDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_multifunctional, container);
        Window window = getDialog().getWindow();
        window.getAttributes().windowAnimations = R.style.dialogAnim;
        window.getAttributes().gravity = Gravity.BOTTOM;

        img_save = view.findViewById(R.id.img_save);
        share = view.findViewById(R.id.share);
        cancel = view.findViewById(R.id.cancel);

        img_save.setOnClickListener(this);
        share.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return view;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int getRootViewId() {
        return 0;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitData(Bundle savedInstanceState) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
            return;
        }
        mImgUrl = getArguments().getString("DATA");
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    private boolean isCheckPermissions() {
        //检测是否有写的权限
        int permission = ActivityCompat.checkSelfPermission(getActivity(),
                "android.permission.WRITE_EXTERNAL_STORAGE");
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            onDownLoad(mImgUrl);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0xff000000));
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.img_save:
                if (!isCheckPermissions()) {
                    // 没有写的权限，去申请写的权限，会弹出对话框
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                } else {
                    onDownLoad(mImgUrl);
                }
                break;
            case R.id.share:
                break;
            case R.id.cancel:
                this.dismiss();
                break;
        }

    }

    public class DownLoadImageService implements Runnable {


        private String url;
        private Context context;
        private ImageDownLoadCallBack callBack;
        private File currentFile;

        public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
            this.url = url;
            this.callBack = callBack;
            this.context = context;
        }

        @Override
        public void run() {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(context)
                        .load(url)
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                if (bitmap != null) {
                    // 在这里执行图片保存方法
                    saveImageToGallery(context, bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bitmap != null && currentFile.exists()) {
                    callBack.onDownLoadSuccess(bitmap);
                } else {
                    callBack.onDownLoadFailed();
                }
            }
        }

        public void saveImageToGallery(Context context, Bitmap bmp) {
            // 首先保存图片
            String filePath = Environment.getExternalStorageDirectory() + "/xiaoyixiao/";
            File localFile = new File(filePath);
            if (!localFile.exists()) {
                localFile.mkdir();
            }

            String local_file = localFile.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
            currentFile = new File(local_file);
            if (currentFile.exists()) {
                currentFile.delete();
            }

            if (!currentFile.getParentFile().exists()) {
                if (!currentFile.getParentFile().mkdirs()) {
                }
            }

            try {
                currentFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(currentFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(currentFile.getPath()))));
        }

    }

    public interface ImageDownLoadCallBack {

        void onDownLoadSuccess(Bitmap bitmap);

        void onDownLoadFailed();
    }

    private static final int MSG_VISIBLE = 1;
    private static final int MSG_ERROR = -1;
    private static final int delayTime = 500;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_VISIBLE:
                    Toast.makeText(getActivity(), "保存图片成功", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ERROR:
                    Toast.makeText(getActivity(), "保存图片失败", Toast.LENGTH_SHORT).show();
                    break;
            }

            super.handleMessage(msg);
        }
    };

    /**
     * 启动图片下载线程
     */
    private void onDownLoad(String url) {
        DownLoadImageService service = new DownLoadImageService(getActivity(),
                url,
                new ImageDownLoadCallBack() {

                    @Override
                    public void onDownLoadSuccess(Bitmap bitmap) {
                        // 在这里执行图片保存方法
                        Message message = new Message();
                        message.what = MSG_VISIBLE;
                        handler.sendMessageDelayed(message, delayTime);
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        Message message = new Message();
                        message.what = MSG_ERROR;
                        handler.sendMessageDelayed(message, delayTime);
                    }
                });
        //启动图片下载线程
        new Thread(service).start();
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
