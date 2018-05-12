package controlrobot.main.com.controlrobot;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SendDataBluetooth sendDataBluetooth;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null){
            Toast.makeText(this,"Su Dispositivo no es compatible con Bluetooth",Toast.LENGTH_SHORT).show();
            finish();
        }

        if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,0);
        }else{
            sendDataBluetooth = new SendDataBluetooth(bluetoothAdapter);
        }
        //enableButtons(false);

        ((ImageButton)findViewById(R.id.buttonLeft)).setOnTouchListener(new OnTouchMoveListener(sendDataBluetooth));
        ((ImageButton)findViewById(R.id.buttonRight)).setOnTouchListener(new OnTouchMoveListener(sendDataBluetooth));
        ((ImageButton)findViewById(R.id.buttonAdvance)).setOnTouchListener(new OnTouchMoveListener(sendDataBluetooth));
        ((ImageButton)findViewById(R.id.buttonReverse)).setOnTouchListener(new OnTouchMoveListener(sendDataBluetooth));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                sendDataBluetooth = new SendDataBluetooth(bluetoothAdapter);
            }else{
                Toast.makeText(this,"La Aplicación necesita del servicio de Bluetooth",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(sendDataBluetooth != null){
            sendDataBluetooth.onClose();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.checkConection);
        item.setVisible(false);

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
            try {
                boolean resulRequest =  sendDataBluetooth.connectDivice();
                if(resulRequest){
                    Toast.makeText(MainActivity.this,"Modulo Enlazado",Toast.LENGTH_SHORT).show();
                    item.setTitle("Modulo Enlazado");
                    item.setEnabled(false);
                    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                    toolbar.getMenu().getItem(0).setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }

        if(id == R.id.checkConection){
            sendDataBluetooth.start();
            enableButtons(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void enableButtons(boolean enable){
        ((ImageButton)findViewById(R.id.buttonLeft)).setEnabled(enable);
        ((ImageButton)findViewById(R.id.buttonRight)).setEnabled(enable);
        ((ImageButton)findViewById(R.id.buttonAdvance)).setEnabled(enable);
        ((ImageButton)findViewById(R.id.buttonReverse)).setEnabled(enable);
    }
}
