package sk.stuba.fei.uim.oop;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerInput extends KeyAdapter {
    private Window window;
    private Player player;

    public PlayerInput(Window window) {
        this.window = window;
        this.player = window.getPlayer();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP) player.moveUp();
        if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft();
        if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN) player.moveDown();
        if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight();

        window.getCml().setMousePointer(new Point(player.getPX(), player.getPY()));
        window.getCanvas().setGuidePoint(window.getCml().getMousePointer());
        window.repaint();
    }
}