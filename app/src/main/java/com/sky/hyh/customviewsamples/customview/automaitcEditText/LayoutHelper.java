package com.sky.hyh.customviewsamples.customview.automaitcEditText;

import android.graphics.Paint;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.TextView;
import com.sky.hyh.customviewsamples.span.spandata.CustomTextSpanData;
import java.util.List;

/**
 * Created by hyh on 2019/3/6 16:41
 * E-Mail Address：fjnuhyh122@gmail.com
 */
public class LayoutHelper {
    private TextView mHost;
    private float mFontSize;
    private int mLayoutWidth;
    private TextSizeAdjustHelper mTextSizeAdjustHelper;

    public LayoutHelper(TextView host,float fontSize) {
        mHost = host;
        mFontSize = fontSize;
        mTextSizeAdjustHelper = new TextSizeAdjustHelper(this);
    }

    public void setLayoutWidth(int layoutWidth) {
        mLayoutWidth = layoutWidth;
    }

    public float getFontSize() {
        return mFontSize;
    }

    /**
     * 这里构建一个假的Layout，用于辅助计算，不是TextView关联的Layout
     * @param text
     * @return
     */
    protected Layout buildFakeLayout(CharSequence text){
        TextPaint paint = copyPaint();
        DynamicLayout dynamicLayout = new DynamicLayout(text,paint,mLayoutWidth,mHost.getLayout().getAlignment(),mHost.getLayout().getSpacingMultiplier(),mHost.getLayout().getSpacingAdd(),mHost.getIncludeFontPadding());
        return dynamicLayout;
    }

    protected TextPaint copyPaint(){
        TextPaint paint = new TextPaint(mHost.getPaint());
        paint.setTextSize(mFontSize);
        return paint;
    }

    protected float getMatchWidthFontSize(String lineText,int width){
        Paint paint = copyPaint();
        float textWidth = paint.measureText(lineText);
        //按照宽比缩放字体
        float textSize = mLayoutWidth / textWidth * mFontSize;
        paint.setTextSize(textSize);
        //缩放字体后还得检查行宽度是否大于最大文本宽度，如果大于则还需要调小字体
        textSize = mTextSizeAdjustHelper.calculateMatchWidthSize(paint,lineText,width);
        return textSize;
    }

    protected void claculateMatchHeightFontSize(String text,
        List<CustomTextSpanData> customTextSpanDataList, int height){
        mTextSizeAdjustHelper.calculateMatchHeightSize(text,customTextSpanDataList,height);
    }

    protected float calculateMaxLengthLineWidth(String lineText, float fontSize){
        Paint paint = copyPaint();
        float width = paint.measureText(lineText);
        float rate = mLayoutWidth / width;
        paint.setTextSize(fontSize);
        float textWidth = paint.measureText(lineText);
        float maxWidth = textWidth * rate;
        return maxWidth;
    }
}