package com.example.commonutils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * @author shengtao
 */

public class CustomChooseDialog extends Dialog {
    private int centerbtn;
    private int visibile;
    private String negetive;
    private String positive;
    private String title;
    private CharSequence message;
    private Context context = null;
    private View customView;
    private TextView cancelButton;
    private TextView confirmButton;
    private CustomedDialogClickListener mCustomedDialogClickListener;
    private TextView mTvcenterPositive;
    private TextView mTvmessage;
    private TextView mTvtitle;
    private LinearLayout mBottombtn;
    private ImageView bottomLine;
    //盛放所有按钮的布局
    private RelativeLayout linear_dialogBtns;
    private int cancelAction;
    private int confirmAction;
    private int centerAction;
    private CustomChooseDialog mCustomChooseDialog;


    public void setCustomedDialogClickListener(CustomedDialogClickListener clickListener) {
        mCustomedDialogClickListener = clickListener;
    }


    public CustomChooseDialog(Context context) {
        super(context);
        this.context = context;
    }

    //1.来自.2.主题样式.3.是否可以取消.4.标题.5.content.6.左边按钮文字.7.右边按钮文字.8.双按钮.9.单按钮.10.左边动作.11.右边动作
    public CustomChooseDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.commen_dialog, null);
        this.setContentView(customView);


    }

    public CustomChooseDialog(Context context, int theme, boolean isCancelable, String title, CharSequence message, String leftButtonText, String rightButtonText, int visibile, int centerbtn) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.commen_dialog, null);
        this.setContentView(customView);
//        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCancelable(isCancelable);
        this.title = title;
        this.message = message;
        this.positive = leftButtonText;
        this.negetive = rightButtonText;
        this.visibile = visibile;
        this.centerbtn = centerbtn;
        showAllBtn = visibile;
        showOnlyOneBtn = centerbtn;
        confirmText = leftButtonText;
        cancelText = rightButtonText;
    }

    public static CustomChooseDialog createDialog(Context context, int theme) {
        CustomChooseDialog mCustomChooseDialog = new CustomChooseDialog(context, theme);

//        this.getWindow().getAttributes().gravity = Gravity.CENTER;

        return mCustomChooseDialog;
    }

    public CustomChooseDialog initView(CustomChooseDialog mCustomChooseDialog, boolean isCancelable, String title, CharSequence message,
                                       String leftButtonText, String rightButtonText, int visibile, int centerbtn, int mConfirmAction, int mCancelAction, int mCenterAction){
        this.mCustomChooseDialog = mCustomChooseDialog;
        mCustomChooseDialog.setCancelable(isCancelable);
        mCustomChooseDialog.title = title;
        mCustomChooseDialog.message = message;
        mCustomChooseDialog.positive = leftButtonText;
        mCustomChooseDialog.negetive = rightButtonText;
        mCustomChooseDialog.visibile = visibile;
        mCustomChooseDialog.centerbtn = centerbtn;
        mCustomChooseDialog.cancelAction = mCancelAction;
        mCustomChooseDialog.confirmAction = mConfirmAction;
        mCustomChooseDialog.centerAction = mCenterAction;
        mCustomChooseDialog.cancelAction = mCancelAction;
        mCustomChooseDialog.confirmAction = mConfirmAction;
        mCustomChooseDialog.centerAction = mCenterAction;
        return mCustomChooseDialog;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        this.setContentView(customView);
        linear_dialogBtns = (RelativeLayout) customView.findViewById(R.id.linear_dialogBtns);
        cancelButton = (TextView) customView.findViewById(R.id.btn_cancel);
        mTvmessage = (TextView) customView.findViewById(R.id.tv_message);
        mTvtitle = (TextView) customView.findViewById(R.id.tv_title);
        confirmButton = (TextView) customView.findViewById(R.id.btn_positive);
        mTvcenterPositive = (TextView) customView.findViewById(R.id.tv_center_positive);
        mBottombtn = (LinearLayout) customView.findViewById(R.id.ll_bottom_button);
        bottomLine = (ImageView) customView.findViewById(R.id.img_dialogLine);

        if (null == title) {
            mTvtitle.setVisibility(View.GONE);
        } else {
            mTvtitle.setText(title);
        }
        mTvmessage.setText(message);
        if (View.GONE == visibile && View.GONE == centerbtn) {
            bottomLine.setVisibility(View.GONE);
        } else {
            bottomLine.setVisibility(View.VISIBLE);
        }
        if (View.GONE == visibile) {
            mBottombtn.setVisibility(visibile);
        } else {
            confirmButton.setText(positive);
            cancelButton.setText(negetive);
            mBottombtn.setVisibility(visibile);
        }
        if (View.GONE == centerbtn) {
            mTvcenterPositive.setVisibility(centerbtn);
        } else {
            mTvcenterPositive.setText(positive);
            mTvcenterPositive.setVisibility(centerbtn);
        }
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomedDialogClickListener != null) {

                    mCustomedDialogClickListener.onCancel(cancelAction);
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomedDialogClickListener != null) {
                    mCustomedDialogClickListener.onConfirm(confirmAction);
                }
            }
        });
        mTvcenterPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomedDialogClickListener != null) {
                    mCustomedDialogClickListener.onCenterConfirm(centerAction);
                }
            }
        });
        LogUtil.e("create结束");
    }

    /**
     * @description: 设置对话框提示标题
     * @author: 袁东华
     * created at 2016/11/9 14:19
     */
    public CustomChooseDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @description: 设置对话框提示消息
     * @author: 袁东华
     * created at 2016/11/9 14:19
     */
    public CustomChooseDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    //确认按钮的文字
    private String confirmText;

    /**
     * @description: 设置对话框确认按钮文字
     * @author: 袁东华
     * created at 2016/11/9 14:20
     */
    public CustomChooseDialog setConfirmText(String confirmText) {
        this.confirmText = confirmText;
        return this;
    }

    //取消按钮的文字
    private String cancelText;

    /**
     * @description: 设置取消按钮文字
     * @author: 袁东华
     * created at 2016/11/9 14:20
     */
    public CustomChooseDialog setCancelText(String cancelText) {
        this.cancelText = cancelText;
        return this;
    }

    //是否显示全部按钮
    private int showAllBtn = -1;

    /**
     * @description: 设置是否显示全部按钮
     * @author: 袁东华
     * created at 2016/11/9 14:20
     */
    public CustomChooseDialog setShowAllBtn(int showAllBtn) {
        this.showAllBtn = showAllBtn;
        showOnlyOneBtn = View.GONE;
        return this;
    }

    //是否只显示一个按钮
    private int showOnlyOneBtn = -1;
    //只有一个按钮的文本
    private String onlyOneBtnText;

    /**
     * @description: 设置只显示一个按钮
     * @author: 袁东华
     * created at 2016/11/9 14:38
     */
    public CustomChooseDialog setShowOnlyOneBtn(int showOnlyOneBtn, String onlyOneBtnText) {
        this.showOnlyOneBtn = showOnlyOneBtn;
        this.onlyOneBtnText = onlyOneBtnText;
        showAllBtn = View.GONE;
        return this;
    }

    /**
     * @description: 隐藏所有按钮
     * @author: 袁东华
     * created at 2016/11/17 17:01
     */
    public CustomChooseDialog setHideAllBtn() {
        showOnlyOneBtn = View.GONE;
        showAllBtn = View.GONE;
        return this;
    }

    /**
     * @description: 点击对话框外面是否可以取消对话框
     * @author: 袁东华
     * created at 2016/11/17 17:24
     */
    public CustomChooseDialog setIsCancelable(boolean flag) {
        super.setCancelable(flag);
        return this;
    }

    @Override
    public void show() {
        super.show();
        setContent();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mCustomChooseDialog = null;
    }

    /**
     * @description: 设置数据
     * @author: 袁东华
     * created at 2016/11/9 14:30
     */
    private void setContent() {
        LogUtil.e("设置内容");
        //设置提示标题
        if (mTvtitle != null && !TextUtils.isEmpty(title)) {
            mTvtitle.setText(title);
        }
        //设置提示内容
        if (mTvmessage != null && !TextUtils.isEmpty(message)) {
            mTvmessage.setText(message);
        }
        //显示全部按钮
        if (bottomLine == null || mBottombtn == null) {
            mBottombtn = (LinearLayout) customView.findViewById(R.id.ll_bottom_button);
            bottomLine = (ImageView) customView.findViewById(R.id.img_dialogLine);
            LogUtil.e("全部按钮为空:");
        }
        if (showAllBtn != -1) {
            if (showAllBtn == View.VISIBLE) {
                bottomLine.setVisibility(View.VISIBLE);
                mBottombtn.setVisibility(View.VISIBLE);
                LogUtil.e("显示全部按钮");
            } else {
                bottomLine.setVisibility(View.GONE);
                mBottombtn.setVisibility(View.GONE);
                LogUtil.e("隐藏全部按钮");
            }

        } else {
            LogUtil.e("隐藏全部按钮");
//            bottomLine.setVisibility(View.GONE);
//            mBottombtn.setVisibility(View.GONE);
        }
        //设置确认按钮内容
        if (confirmButton != null && !TextUtils.isEmpty(confirmText)) {
            confirmButton.setText(confirmText);
        }
        //设置取消按钮内容
        if (cancelButton != null && !TextUtils.isEmpty(cancelText)) {
            cancelButton.setText(cancelText);
        }
        //设置是否只显示一个按钮
        if (mTvcenterPositive == null || bottomLine == null) {
            mTvcenterPositive = (TextView) customView.findViewById(R.id.tv_center_positive);
            bottomLine = (ImageView) customView.findViewById(R.id.img_dialogLine);
            LogUtil.e("单个按钮为空:");
        }
        if (!TextUtils.isEmpty(onlyOneBtnText) && showOnlyOneBtn != -1) {
            if (showOnlyOneBtn == View.VISIBLE) {
                mTvcenterPositive.setVisibility(View.VISIBLE);
                bottomLine.setVisibility(View.VISIBLE);
                LogUtil.e("显示一个按钮");
            } else {
                mTvcenterPositive.setVisibility(View.GONE);
                bottomLine.setVisibility(View.GONE);
                LogUtil.e("隐藏一个按钮");
            }
            mTvcenterPositive.setText(onlyOneBtnText);
        } else {
//            mTvcenterPositive.setVisibility(View.GONE);
            LogUtil.e("隐藏一个按钮");
        }
        if (showAllBtn == View.GONE && showOnlyOneBtn == View.GONE) {
            linear_dialogBtns.setVisibility(View.GONE);
        } else {
            linear_dialogBtns.setVisibility(View.VISIBLE);
        }

    }

    public interface CustomedDialogClickListener {
        void onConfirm(int action);

        void onCancel(int action);

        void onCenterConfirm(int action);

    }

}
