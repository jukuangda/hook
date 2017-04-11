package com.example.ju.hook;

import android.annotation.TargetApi;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView startTime;
    private TextView startXY;
    private TextView changeXY;
    private TextView endTime;
    private TextView endXY;
    private Button qtimeButton;
    private int start = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qtimeButton = (Button) findViewById(R.id.button);
        final TextView time = (TextView) findViewById(R.id.textView);
        startTime = (TextView) findViewById(R.id.startTime);
        startXY = (TextView) findViewById(R.id.startXY);
        changeXY = (TextView) findViewById(R.id.changeXY);
        endTime = (TextView) findViewById(R.id.endTime);
        endXY = (TextView) findViewById(R.id.endXY);
        qtimeButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                Point point = new Point();
                getWindowManager().getDefaultDisplay().getRealSize(point);
                DisplayMetrics dm = getResources().getDisplayMetrics();
                double x = Math.pow(point.x / dm.xdpi, 2);
                double y = Math.pow(point.y / dm.ydpi, 2);
                DecimalFormat df = new DecimalFormat("######0.00");
                String screenInches = df.format(Math.sqrt(x + y));
                time.setText("屏幕尺寸：" + screenInches + "\n" + "分辨率：" + dm.widthPixels + "*" + dm.heightPixels);
                start*=-1;
                if(start > 0)
                    qtimeButton.setText("停止捕捉");
                else
                    qtimeButton.setText("开始捕捉");
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss:SSS");
//获得触摸的坐标
        switch (event.getAction()) {
//触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                if (start > 0) {
                    startTime.setText("触摸开始时间：");
                    startXY.setText("开始坐标：");
                    changeXY.setText("划过坐标：");
                    endTime.setText("终止时间：");
                    endXY.setText("终止坐标：");
                    startTime.setText(startTime.getText() + " " + df.format(new Date()));
                    startXY.setText(startXY.getText() + " " + event.getX() + " , " + event.getY());
                }
                break;
//触摸并移动时刻
            case MotionEvent.ACTION_MOVE:
                if (start > 0) {
                    changeXY.setText(changeXY.getText() + "\n" + event.getX() + " , " + event.getY() + " "+df.format(new Date()));
                }
                break;
//终止触摸时刻
            case MotionEvent.ACTION_UP:
                if(start > 0) {
                    endTime.setText(endTime.getText() + " " + df.format(new Date()));
                    endXY.setText(endXY.getText() + " " + event.getX() + " , " + event.getY());
                }
                break;
        }
        return true;
    }


}
