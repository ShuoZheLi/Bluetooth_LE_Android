package com.example.bluetry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String msg = "Android : ";
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
            .getDefaultAdapter();
    private BluetoothDevice targetDevice;
    private BluetoothLeScanner bluetoothLeScanner;

    /** 当活动第一次被创建时调用 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(msg, "The onCreate() event");
        if(mBluetoothAdapter == null){
            Toast.makeText(this, "不支持蓝牙", Toast.LENGTH_SHORT).show();
        }else{
            enableBT();
        }
    }

    // method enable Bluetooth
    protected void enableBT(){
        // case: bluetooth already enabled
        if(mBluetoothAdapter.isEnabled()){
            Log.d(msg, "蓝牙已经提前开启");
            Toast.makeText(this, "蓝牙已经提前开启", Toast.LENGTH_SHORT).show();
        }else{
            // case: bluetooth not enabled yet
            Toast.makeText(this, "正在开启蓝牙", Toast.LENGTH_SHORT).show();
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
        }
        // start scanning
        Log.d(msg, "蓝牙开始扫描");
        scanLeDevice();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "没有蓝牙权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    // setup and start scan BT_LE device
    private void scanLeDevice() {
        // ask user for COARSE_LOCATION Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
        }
        Log.d(msg, "蓝牙正在扫描");
        // setup scanner then start scan
        bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        bluetoothLeScanner.startScan(callback);
    }

    private ScanCallback callback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            List<ParcelUuid> uuids = result.getScanRecord().getServiceUuids();
            //Log.d(msg, "扫描的设备:"+result.getDevice().getName());
            if(uuids!=null){
                Log.d(msg, "扫描的设备:"+uuids.get(0));
                Log.d(msg, "扫描的设备:"+uuids.get(1));
                Log.d(msg, "扫描的设备:"+result.getDevice().getName());
                Log.d(msg, "扫描的强度:"+result.getRssi());
            }
//            Log.d(msg, "扫描的设备:"+uuids);
//            Log.d(msg, "扫描的强度:"+result.getRssi());
//            if(){
//
//            }
            super.onScanResult(callbackType, result);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };



    /** 当活动即将可见时调用 */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** 当活动可见时调用 */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** 当其他活动获得焦点时调用 */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** 当活动不再可见时调用 */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** 当活动将被销毁时调用 */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }
}
