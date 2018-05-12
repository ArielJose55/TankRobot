package controlrobot.main.com.controlrobot;

/**
 * Created by Ariel Arnedo on 06/01/2016.
 */
public interface RobotMove {
    public byte MOVE = 1;
    public byte STOP = 0;

    public void moveLeft(byte left);
    public void moveRight(byte right);
    public void moveAdvance(byte advance);
    public void moveReverse(byte reverse);
}
