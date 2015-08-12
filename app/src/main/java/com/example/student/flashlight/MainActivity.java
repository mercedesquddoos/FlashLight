package com.example.student.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private Camera camera;
    Camera.Parameters camParam;
    ToggleButton tbFlashlight;
    private boolean isFlashOn;
    private boolean hasFlash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(hasFlash){
            getCamera();
        }
    }

    public void onFlashButtonClick(View v){
        tbFlashlight = (ToggleButton)v;
        if (isFlashOn) {
            turnOffCamera();
            tbFlashlight.setTextOff("Off");
        } else{
            turnOnCamera();
            tbFlashlight.setTextOn("On");
        }

    }

    private void turnOnCamera(){
        if(!isFlashOn){
            //Test that we have camParam and camera objects.
            if(camera == null || camParam == null){ getCamera();}

            camParam.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(camParam);
            camera.startPreview();
            isFlashOn = true;
        }
    }

    private void turnOffCamera(){
        if(isFlashOn){
            //Test that we have camParam and camera objects.
            if(camera == null || camParam == null){ getCamera();}

            camParam.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(camParam);
            camera.stopPreview();
            camera.release();
            camera = null;
            camParam = null;
            isFlashOn = false;
        }
    }

    private void getCamera(){
        if(camera == null) {
            try{
                camera = Camera.open();
                camParam = camera.getParameters();

            } catch(RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
