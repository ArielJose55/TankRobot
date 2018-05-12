package controlrobot.main.com.controlrobot;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Ariel Arnedo on 05/01/2016.
 */
public class SendDataBluetooth extends Thread implements RobotMove{

    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;

    private volatile byte[] sentMove = {0,0,0,0};
    private boolean checkConnection = false;

    public SendDataBluetooth(BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;
        bluetoothDevice = null;
    }

    public boolean connectDivice() throws IOException {
        Set<BluetoothDevice> bluetoothDevices = bluetoothAdapter.getBondedDevices();

        for(BluetoothDevice bluetoothDevice : bluetoothDevices){
            if(bluetoothDevice.getName().compareTo("HC-06") == 0){
                this.bluetoothDevice = bluetoothDevice;
                break;
            }
        }
        if(bluetoothDevice != null){
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
            outputStream = socket.getOutputStream();
            return true;
        }
        return false;
    }



    public void run(){

        while (true){

            try {

                outputStream.write(sentMove);
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClose(){
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void moveLeft(byte left) {
        sentMove[0] = left;
    }

    @Override
    public void moveRight(byte right) {
        sentMove[1] = right;
    }

    @Override
    public void moveAdvance(byte advance) {
        sentMove[2] = advance;
    }

    @Override
    public void moveReverse(byte reverse) {
        sentMove[3] = reverse;
    }
}
