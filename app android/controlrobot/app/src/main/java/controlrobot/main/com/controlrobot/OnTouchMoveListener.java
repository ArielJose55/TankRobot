package controlrobot.main.com.controlrobot;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Ariel Arnedo on 06/01/2016.
 */
public class OnTouchMoveListener implements View.OnTouchListener{

    private final SendDataBluetooth sendDataBluetooth;

    public OnTouchMoveListener(SendDataBluetooth sendDataBluetooth){
        this.sendDataBluetooth = sendDataBluetooth;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();

        switch (id){
            case R.id.buttonLeft:{
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendDataBluetooth.moveLeft(RobotMove.MOVE);
                    ((ImageButton)v).setImageResource(R.drawable.move_left_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendDataBluetooth.moveLeft(RobotMove.STOP);
                    ((ImageButton)v).setImageResource(R.drawable.move_left);
                }
                break;
            }
            case R.id.buttonRight:{
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendDataBluetooth.moveRight(RobotMove.MOVE);
                    ((ImageButton)v).setImageResource(R.drawable.move_right_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendDataBluetooth.moveRight(RobotMove.STOP);
                    ((ImageButton)v).setImageResource(R.drawable.move_right);
                }
                break;
            }
            case R.id.buttonAdvance:{
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendDataBluetooth.moveAdvance(RobotMove.MOVE);
                    ((ImageButton)v).setImageResource(R.drawable.move_advance_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendDataBluetooth.moveAdvance(RobotMove.STOP);
                    ((ImageButton)v).setImageResource(R.drawable.move_advance);
                }
                break;
            }
            case R.id.buttonReverse:{
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendDataBluetooth.moveReverse(RobotMove.MOVE);
                    ((ImageButton)v).setImageResource(R.drawable.move_reverse_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    sendDataBluetooth.moveReverse(RobotMove.STOP);
                    ((ImageButton)v).setImageResource(R.drawable.move_reverse);
                }
                break;
            }
        }
        return true;
    }
}
