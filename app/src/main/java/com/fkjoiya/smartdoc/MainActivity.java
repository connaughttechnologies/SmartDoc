package com.fkjoiya.smartdoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2{

    CameraBridgeViewBase cameraBridgeViewBase;
    BaseLoaderCallback baseLoaderCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraBridgeViewBase = (JavaCameraView) findViewById(R.id.HelloOpenCvView);
        //mCameraBridgeViewBase.setVisibility(SurfaceView.VISIBLE);
        cameraBridgeViewBase.setCvCameraViewListener(this);

        baseLoaderCallback = new BaseLoaderCallback(MainActivity.this) {
            @Override
            public void onManagerConnected(int status) {
                super.onManagerConnected(status);
                switch (status){
                    case BaseLoaderCallback.SUCCESS:
                    cameraBridgeViewBase.enableView();
                    break;
                    default:
                        super.onManagerConnected(status);
                        break;
                }
            }
        };
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
         Mat matFrame = inputFrame.rgba();
         return matFrame;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()){
            Toast.makeText(this, "Problem Occurred", Toast.LENGTH_SHORT).show();
        }else {
            baseLoaderCallback.onManagerConnected(baseLoaderCallback.SUCCESS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (cameraBridgeViewBase != null){
            cameraBridgeViewBase.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraBridgeViewBase != null){
            cameraBridgeViewBase.disableView();
        }
    }
}