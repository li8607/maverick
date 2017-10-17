package com.maverick.presenter;

import android.content.Context;

import com.maverick.presenter.implView.IDetailActivityView;

/**
 * Created by Administrator on 2017/10/17.
 */
public class DetailActivityPresenter extends BasePresenter {

    private IDetailActivityView mView;
    private Context mContext;

    public DetailActivityPresenter(Context context, IDetailActivityView view) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void release() {

    }

    public void loadImage(String imgUrl) {
        boolean isGif = isGif(getFileType(imgUrl));
        if (isGif) {
            mView.onShowGifImageView(imgUrl);
        }else {
            mView.onShowImageView(imgUrl);
        }
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return 文件后缀名
     */
    public static String getFileType(String fileName) {
        if (fileName != null) {
            int typeIndex = fileName.lastIndexOf(".");
            if (typeIndex != -1) {
                String fileType = fileName.substring(typeIndex + 1)
                        .toLowerCase();
                return fileType;
            }
        }
        return "";
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param type
     * @return 是否是图片结果true or false
     */
    public static boolean isImage(String type) {
        if (type != null
                && (type.equals("jpg") || type.equals("gif")
                || type.equals("png") || type.equals("jpeg")
                || type.equals("bmp") || type.equals("wbmp")
                || type.equals("ico") || type.equals("jpe"))) {
            return true;
        }
        return false;
    }

    /**
     * 根据后缀名判断是否是图片文件
     *
     * @param type
     * @return 是否是图片结果true or false
     */
    public static boolean isGif(String type) {
        if (type != null && type.equals("gif")) {
            return true;
        }
        return false;
    }
}
