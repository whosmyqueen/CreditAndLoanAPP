package com.zhph.commonlibrary.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.util.ArrayList;

/**
 * Created by liangwenke on 2016/10/26.
 */

public class TransverseTimeLintView extends View {
    private Paint p;
    private Context context;
    private RectF r;
    private int progress = 0;
    private int widthPixels;
    private int speed = 5;
    private boolean lockState = true;
    private int beginX = 20;
    private String currentNodeName;
    private int nodeSpacing = 0;
    private int countLength = 0;
    private ArrayList<String> nodes;
    private boolean falg = false;
    private Point point = new Point();
    private int screenHeight;
    private int[] location = null;
    private int beginColor = Color.parseColor("#F97607");
    private int endColor = Color.parseColor("#E8110F");
//    private boolean initState = true;

    public TransverseTimeLintView(Context context) {
        this(context, null);
    }


    public TransverseTimeLintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        r = new RectF();
        p = new Paint();
        ((Activity) context).getWindowManager().getDefaultDisplay().getSize(point);
        screenHeight = point.y - dip2px(context, 110);//减去底部高度
        location = new int[2];
        this.setMinimumHeight(dip2px(context, 60));
        beginColor = Color.parseColor("#F97607");
        endColor = Color.parseColor("#E8110F");
    }

    /**
     * dp与px转换关系
     * dp转换为px
     *
     * @param context
     * @param dp      * @return
     */
    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        // 四舍五入的操作
        return (int) (dp * density + 0.5);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2pid(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    public void setData(final View loadingView, final ViewGroup viewGroup, final ArrayList<String> nodes, final String currentNodeName) {
        this.currentNodeName = currentNodeName;
        this.nodes = nodes;
        viewGroup.removeAllViews();
        viewGroup.addView(TransverseTimeLintView.this);
        viewGroup.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                viewGroup.getLocationInWindow(location);
                if (location.length > 1 && location[1] > 0 && location[1] < screenHeight) {
                    widthPixels = viewGroup.getWidth() - viewGroup.getPaddingLeft() - viewGroup.getPaddingRight();
                    TransverseTimeLintView.this.setMinimumWidth(dip2px(context, widthPixels));
                    int index = 1;
                    if (nodes != null && nodes.size() > 0 && currentNodeName != null && !currentNodeName.isEmpty()) {
                        progress = 0;
                        nodeSpacing = px2pid(context, (widthPixels - dip2px(context, beginX) * 2) / nodes.size());
                        for (String node : nodes) {
                            if (currentNodeName.equals(node)) {
                                falg = true;
                                break;
                            } else {
                                ++index;
                            }
                        }
                    }
                    index = falg ? index : 0;
                    countLength = index * nodeSpacing;
                    loadingView.setVisibility(View.GONE);
                    TransverseTimeLintView.this.invalidate();
                    if (viewGroup.getViewTreeObserver().isAlive()) {
                        viewGroup.getViewTreeObserver().removeOnScrollChangedListener(this);
                    }
                }
            }

        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setAntiAlias(true);//去锯齿

        //画进度条背景
        p.setColor(Color.parseColor("#f7f7f7"));
        r.left = dip2px(context, beginX);
        r.right = widthPixels - dip2px(context, beginX);
        r.top = dip2px(context, 45);
        r.bottom = dip2px(context, 51);
        canvas.drawRoundRect(r, dip2px(context, 3), dip2px(context, 3), p);
        if (falg) {
            //画进度条
            RadialGradient lg = new RadialGradient(dip2px(context, beginX), dip2px(context, 45), dip2px(context, progress + beginX),
                    beginColor, endColor, Shader.TileMode.MIRROR);
            p.setStyle(Paint.Style.FILL);
            p.setStrokeWidth(dip2px(context, 1));
            p.setShader(lg);
            r.left = dip2px(context, beginX);
            r.right = dip2px(context, progress + beginX);
            r.top = dip2px(context, 45);
            r.bottom = dip2px(context, 51);
            canvas.drawRoundRect(r, dip2px(context, 3), dip2px(context, 3), p);
            p.setShader(null);
            p.setColor(Color.parseColor("#cccccc"));
            p.setTextSize(dip2px(context, 12));
            canvas.drawText(nodes.get(nodes.size() - 1), widthPixels - getStringWidth(nodes.get(nodes.size() - 1), p) - dip2px(context,
                    beginX), dip2px(context, 65), p);

            if (progress < countLength && lockState) {
                progress += speed;
                TransverseTimeLintView.this.invalidate();
            } else if (progress < nodeSpacing * nodes.size()) {
                //画三角
                p.setColor(Color.parseColor("#e60012"));
                Path path = new Path();
                path.moveTo(dip2px(context, progress + beginX - speed), dip2px(context, 35));
                path.lineTo(dip2px(context, progress + beginX - speed + 12), dip2px(context, 35));
                path.lineTo(dip2px(context, progress + beginX - speed + 6), dip2px(context, 35 + 6));
                path.close();
                canvas.drawPath(path, p);

                r.left = dip2px(context, progress + beginX - speed - 80 / 2 + 6);
                r.right = dip2px(context, (progress + beginX - speed - 80 / 2 + 6) + 80);
                r.top = dip2px(context, 35 - 20);
                r.bottom = dip2px(context, 35);
                canvas.drawRoundRect(r, dip2px(context, 10), dip2px(context, 10), p);

                p.setColor(Color.WHITE);
                p.setTextSize(dip2px(context, 12));
                canvas.drawText(currentNodeName, dip2px(context, (progress + beginX - speed - 80 / 2 + 6) + (80 - px2pid(context,
                        getStringWidth(currentNodeName, p))) / 2),
                        dip2px(context, 35 - 12 / 2), p);
                lockState = false;
            }
        }
    }


    public void setProgressColor() {
        beginColor = Color.parseColor("#00FFFFFF");
        endColor = Color.parseColor("#00FFFFFF");
    }

    /**
     * 获取文字宽度
     *
     * @param str
     * @param p
     * @return
     */
    private int getStringWidth(String str, Paint p) {
        return (int) p.measureText(str);
    }

    /**
     * 获取文字高度
     *
     * @param str
     * @param p
     * @return
     */
    private int getStringHeight(String str, Paint p) {
        Paint.FontMetrics fr = p.getFontMetrics();
        return (int) Math.ceil(fr.descent - fr.top) + 2;  //ceil() 函数向上舍入为最接近的整数。
    }

}
