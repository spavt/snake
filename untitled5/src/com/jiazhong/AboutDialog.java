package com.jiazhong;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog{
    private static Image bgImage;
    static{
        bgImage = Toolkit.getDefaultToolkit().getImage("src/images/about.jpg");
    }

    public AboutDialog(){
        this.setSize(799,598);
        //居中显示
        this.setLocationRelativeTo(null);
        //设置窗口不显示标题栏
        this.setVisible(true);
        //设置窗口为模式窗口
        this.setModal(true);
    }
    public void paint(Graphics g){
        g.drawImage(bgImage,0,0,799,598,this);
    }

}
