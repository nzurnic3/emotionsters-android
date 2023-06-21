package rs.urosdragojevic.emotionsters_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


public class Bubble extends androidx.appcompat.widget.AppCompatTextView {

    private Canvas canvas;
    private Paint drawPaint;
    private Paint drawPaint1;
    private TextPaint textPaint;

    String text = "";


    public Bubble(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;

        Drawable circle = ContextCompat.getDrawable(this.getContext(), R.drawable.bubble_shape);
        Drawable t = ContextCompat.getDrawable(this.getContext(), R.drawable.triangle_shape);

        int horizontalInset = (circle.getIntrinsicWidth() - t.getIntrinsicWidth()) / 2;

        LayerDrawable finalDrawable = new LayerDrawable(new Drawable[] {circle, t});
        finalDrawable.setLayerInset(0, 0, 0, 0, circle.getIntrinsicHeight());
        finalDrawable.setLayerInset(1, horizontalInset, t.getIntrinsicHeight(), horizontalInset, 0);

        circle.setBounds(100,100,100,100);

        circle.draw(canvas);
    }
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.FILL);

        drawPaint1 = new Paint();
        drawPaint1.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint1.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setTextSize(16 * getResources().getDisplayMetrics().density);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }
}
