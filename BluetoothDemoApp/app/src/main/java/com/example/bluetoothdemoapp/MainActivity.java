package com.example.bluetoothdemoapp;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView bluetoothInfoTextView, pairedDevicesTextView;
    Button onButton, offButton, pairedButton, discoverButton;
    ImageView bluetoothImageView;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothInfoTextView = findViewById(R.id.bluetoothInfoTextView);
        pairedDevicesTextView = findViewById(R.id.pairedDevicesTextView);
        onButton = findViewById(R.id.onButton);
        offButton = findViewById(R.id.offButton);
        pairedButton = findViewById(R.id.pairedButton);
        discoverButton = findViewById(R.id.discoverButton);
        bluetoothImageView = findViewById(R.id.bluetoothImageView);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();



        if (bluetoothAdapter == null) {
            bluetoothInfoTextView.setText("Bluetooth not available");
            bluetoothImageView.setImageResource(R.drawable.ic_action_off);
            setButtonsIfUnavailable();
            return;
        }

        if (bluetoothAdapter.isEnabled())
            bluetoothImageView.setImageResource(R.drawable.ic_action_on);
        else
            bluetoothImageView.setImageResource(R.drawable.ic_action_off);

        bluetoothInfoTextView.setText("Bluetooth available");

        setButtonsIfAvailable();
    }

    private void setButtonsIfUnavailable()
    {
        onButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Bluetooth unavailable");
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Bluetooth unavailable");
            }
        });

        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Bluetooth unavailable");
            }
        });

        pairedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMessage("Bluetooth unavailable");
            }
        });
    }

    private void setButtonsIfAvailable()
    {
        onButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, 0);

                    return;
                }
                showToastMessage("Bluetooth is already on");
            }
        });

        offButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (bluetoothAdapter.isEnabled()) {
                    bluetoothAdapter.disable();
                    bluetoothImageView.setImageResource(R.drawable.ic_action_off);
                    return;
                }
                showToastMessage("Bluetooth is already off");
            }
        });

        discoverButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if(!bluetoothAdapter.isDiscovering()) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent,1);

                    return;
                }
            }
        });

        pairedButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if(bluetoothAdapter.isEnabled())
                {
                    pairedDevicesTextView.setText("Paired Devices List:");
                    Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                    for(BluetoothDevice device : pairedDevices) {
                        pairedDevicesTextView.append("\nName: " + device.getName() + "\n" + device);
                    }
                    return;
                }

                showToastMessage("Turn on Bluetooth first");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent intent) {
        if (requestCode == 0 && resultCode == RESULT_OK){
                    bluetoothImageView.setImageResource(R.drawable.ic_action_on);
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void showToastMessage(String input) {
        Toast.makeText(this,input,Toast.LENGTH_LONG).show();
    }
}