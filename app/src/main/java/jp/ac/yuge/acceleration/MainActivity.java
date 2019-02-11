package jp.ac.yuge.acceleration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    //
    private static final String TAG = "MainActivity";

    //
    private SensorManager mSensorManager;
    //
    private Sensor mSensor;
    //
    private Handler mHandler;

    //
    private TextView mTextViewAnnotationX;
    private TextView mTextViewAnnotationY;
    private TextView mTextViewAnnotationZ;
    private TextView mTextViewAnnotationMaxX;
    private TextView mTextViewAnnotationMaxY;
    private TextView mTextViewAnnotationMaxZ;

    //
    private float mAccelerationMaxX;
    private float mAccelerationMaxY;
    private float mAccelerationMaxZ;

    @SuppressLint("HandlerLeak") //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        //
        mTextViewAnnotationX = (TextView)findViewById(R.id.acceleration_x);
        mTextViewAnnotationY = (TextView)findViewById(R.id.acceleration_y);
        mTextViewAnnotationZ = (TextView)findViewById(R.id.acceleration_z);
        mTextViewAnnotationMaxX = (TextView)findViewById(R.id.acceleration_max_x);
        mTextViewAnnotationMaxY = (TextView)findViewById(R.id.acceleration_max_y);
        mTextViewAnnotationMaxZ = (TextView)findViewById(R.id.acceleration_max_z);

        //
        mAccelerationMaxX = 0;
        mAccelerationMaxY = 0;
        mAccelerationMaxZ = 0;

        //
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Acceleration acceleration = (Acceleration)msg.obj;
                //
                if (Math.abs(acceleration.getX()) > Math.abs(mAccelerationMaxX)) {
                    mAccelerationMaxX = acceleration.getX();
                }
                if (Math.abs(acceleration.getY()) > Math.abs(mAccelerationMaxY)) {
                    mAccelerationMaxY = acceleration.getY();
                }
                if (Math.abs(acceleration.getZ()) > Math.abs(mAccelerationMaxY)) {
                    mAccelerationMaxZ = acceleration.getZ();
                }

                //
                mTextViewAnnotationX.setText("x: " + acceleration.getX());
                mTextViewAnnotationY.setText("y: " + acceleration.getY());
                mTextViewAnnotationZ.setText("z: " + acceleration.getZ());
                //
                mTextViewAnnotationMaxX.setText("max x: " + mAccelerationMaxX);
                mTextViewAnnotationMaxY.setText("max y: " + mAccelerationMaxY);
                mTextViewAnnotationMaxZ.setText("max z: " + mAccelerationMaxZ);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSensor != null) {
            //
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            //
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSensor != null) {
            //
            mSensorManager.unregisterListener(this);

        } else {
            //
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "acceleration x: " + event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);

        //
        Acceleration acceleration = new Acceleration(event.values[0], event.values[1], event.values[2]);
        //
        mHandler.sendMessage(Message.obtain(mHandler, 0, acceleration));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }

    //
    private class Acceleration {
        private float x;
        private float y;
        private float z;

        Acceleration(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        float getX() { return this.x; }
        float getY() { return this.y; }
        float getZ() { return this.z; }
    }
}
