package com.jiazhong;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * 音乐播放器类
 */
public class Sound {
    private static File bgSoundFile; // 背景音乐文件
    private static File missileSoundFile; // 撞击音效文件
    private Clip missileSoundClip; // 撞击音效播放器
    private Clip bgSoundClip; // 背景音乐播放器
    private static File eatileSoundFile; // 吃食物音效文件
    private Clip eatileSoundClip; // 吃食物音效播放器

    // 加载声音文件
    static {
        // 指定音频文件路径
        bgSoundFile = new File("src/audio/贪吃蛇.wav");
        // 指定音频文件路径
        missileSoundFile = new File("src/audio/PenZ.wav");
        // 指定音频文件路径
        eatileSoundFile = new File("src/audio/Eat.wav");
    }

    public Clip getBgSoundClip() {
        return this.bgSoundClip;
    }

    /**
     * 获得背景音乐播放器对象
     *
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void playBgMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // 获取音频输入流
        AudioInputStream bgSoundStream = AudioSystem.getAudioInputStream(bgSoundFile);

        // 获取Clip对象
        this.bgSoundClip = AudioSystem.getClip();
        // 开发音乐流
        this.bgSoundClip.open(bgSoundStream);
        // 开始播放音乐
        this.bgSoundClip.start();
        // 循环播放
        this.bgSoundClip.loop(-1);
    }

    /**
     * 播放撞击音效
     *
     * @return
     */
    public void playMissileSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // 获取音频输入流
        AudioInputStream missileSoundStream = AudioSystem.getAudioInputStream(missileSoundFile);

        // 获取Clip对象
        this.missileSoundClip = AudioSystem.getClip();
        // 开发音乐流
        this.missileSoundClip.open(missileSoundStream);
        // 开始播放音乐
        this.missileSoundClip.start();
    }

    /**
     * 播放吃食物音效
     *
     * @return
     */
    public void playEatileSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // 获取音频输入流
        AudioInputStream eatileSoundStream = AudioSystem.getAudioInputStream(eatileSoundFile);

        // 获取Clip对象
        this.eatileSoundClip = AudioSystem.getClip();
        // 开发音乐流
        this.eatileSoundClip.open(eatileSoundStream);
        // 开始播放音效
        this.eatileSoundClip.start();
    }

    /**
     * 停止背景音乐播放
     */
    public void stop() {
        if (this.bgSoundClip != null) {
            this.bgSoundClip.stop();
            this.bgSoundClip = null;
        }
    }

    /**
     * 停止吃食物音效播放
     */
    public void stopEatileSound() {
        if (this.eatileSoundClip != null) {
            this.eatileSoundClip.stop();
            this.eatileSoundClip = null;
        }
    }

}
