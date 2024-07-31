package com.jiazhong;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

public class Game extends JFrame {
    private static final Image bgImage = Toolkit.getDefaultToolkit().getImage("src/images/贪吃蛇.jpg");//加载背景图片
    private static final int WIDTH = 800;//
    private static final int HEIGHT = 450;
    private static final int MENU_X_START = 337;
    private static final int MENU_X_END = 460;
    private static final int START_Y_START = 262;
    private static final int START_Y_END = 299;
    private static final int ABOUT_Y_START = 323;
    private static final int ABOUT_Y_END = 362;
    private static final int EXIT_Y_START = 388;
    private static final int EXIT_Y_END = 425;
    private static final int MUSIC_X_START = 8;
    private static final int MUSIC_X_END = 68;
    private static final int MUSIC_Y_START = 387;
    private static final int MUSIC_Y_END = 437;

    private final Sound sound = new Sound();

    public Game() {
        initGUI();
    }

    private void initGUI() {
        setTitle("贪吃蛇");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            sound.playBgMusic();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        setVisible(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {//鼠标点击事件
                handleMouseClick(event);//处理鼠标点击事件,点击开始游戏，关于，退出游戏
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {//鼠标移动事件
            @Override
            public void mouseMoved(MouseEvent event) {
                handleMouseMove(event);
            }
        });
    }

    private void handleMouseClick(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        if (isWithinBounds(x, y, MENU_X_START, MENU_X_END, START_Y_START, START_Y_END)) {
            dispose();
            new GameFrame();
        } else if (isWithinBounds(x, y, MENU_X_START, MENU_X_END, ABOUT_Y_START, ABOUT_Y_END)) {
            new AboutDialog();
        } else if (isWithinBounds(x, y, MENU_X_START, MENU_X_END, EXIT_Y_START, EXIT_Y_END)) {
            int num = JOptionPane.showConfirmDialog(this, "确认退出游戏吗", "退出提示", JOptionPane.CANCEL_OPTION);
            if (num == 0) {
                System.exit(0);
            }
        } else if (isWithinBounds(x, y, MUSIC_X_START, MUSIC_X_END, MUSIC_Y_START, MUSIC_Y_END)) {
            toggleMusic();
        }
    }

    private void handleMouseMove(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        if (isWithinBounds(x, y, MENU_X_START, MENU_X_END, START_Y_START, START_Y_END) ||
                isWithinBounds(x, y, MENU_X_START, MENU_X_END, ABOUT_Y_START, ABOUT_Y_END) ||
                isWithinBounds(x, y, MENU_X_START, MENU_X_END, EXIT_Y_START, EXIT_Y_END) ||
                isWithinBounds(x, y, MUSIC_X_START, MUSIC_X_END, MUSIC_Y_START, MUSIC_Y_END)) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private boolean isWithinBounds(int x, int y, int xStart, int xEnd, int yStart, int yEnd) {
        return x >= xStart && x <= xEnd && y >= yStart && y <= yEnd;
    }

    private void toggleMusic() {
        if (sound.getBgSoundClip() != null) {
            sound.stop();
        } else {
            try {
                sound.playBgMusic();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bgImage, 0, 0, this);
    }

    public static void main(String[] args) {
        new Game();
    }
}
