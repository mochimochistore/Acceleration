package jp.ac.kagawanct.acceleration;

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
    // ログ出力用タグ名
    private static final String TAG = "MainActivity";

    // センサーマネージャ
    private SensorManager mSensorManager;
    // 加速度センサーを取り扱うオブジェクト
    private Sensor mSensor;
    // UI への処理実行を管理するハンドラ
    private Handler mHandler;

    // センサー計測結果を表示する TextView オブジェクト
    private TextView mTextViewAnnotationX;
    private TextView mTextViewAnnotationY;
    private TextView mTextViewAnnotationZ;
    private TextView mTextViewAnnotationMaxX;
    private TextView mTextViewAnnotationMaxY;
    private TextView mTextViewAnnotationMaxZ;

    // 加速度最大値を保持するフィールド
    private float mAccelerationMaxX;
    private float mAccelerationMaxY;
    private float mAccelerationMaxZ;

    @SuppressLint("HandlerLeak") // Handler のメモリリーク警告を表示させないための設定
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: フィールドの初期化と、センサー計測値の更新時に呼び出されるハンドルメソッドの定義
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mSensor != null) {
            // 加速度センサーの計測を開始する
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            // 加速度センサーが利用できない場合
            Log.w(TAG, "Acceleration sensor is not available.");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mSensor != null) {
            // 加速度センサーの計測を解除する
            mSensorManager.unregisterListener(this);

        } else {
            // 加速度センサーが利用できない場合
            Log.w(TAG, "Acceleration sensor is not available.");
        }
    }

    // SensorEventListener インタフェースが提供するメソッドの実装
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "acceleration x: " + event.values[0] + ", y: " + event.values[1] + ", z: " + event.values[2]);

        // TODO: 取得した加速度計測値を画面に描画
    }

    // SensorEventListener インタフェースが提供するメソッドの実装
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 何もしない
    }

    // 加速度センサーの計測値を一時的に保持するためのクラス
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
