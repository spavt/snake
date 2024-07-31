package com.jiazhong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame {
	// 游戏区域的宽度和高度
	private static final int GAME_WIDTH = 535;
	private static final int GAME_HEIGHT = 565;

	private GamePanel gPanel; // 游戏面板
	private JMenuBar jBar; // 菜单栏
	private JMenuItem[] jItems; // 菜单项
	private JLabel scoreLabel; // 用于显示得分的标签
	private boolean isTwoPlayerMode = false; // 双人模式标志
	private boolean isChallengeMode = false; // 挑战模式标志
	private Gear gear; // 齿轮
	public GameFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLayout(null);
		setLocationRelativeTo(null); // 窗口居中显示
		setUndecorated(true); // 去掉窗口的边框

		jBar = new JMenuBar(); // 创建菜单栏
		JMenu difficultyMenu = new JMenu("难度"); // 创建难度菜单
		JMenu modeMenu = new JMenu("模式"); // 创建模式菜单

		jItems = new JMenuItem[9]; // 创建菜单项数组

		jItems[0] = new JMenuItem("重新开始");
		jItems[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0)); // 设置快捷键
		jItems[1] = new JMenuItem("暂停/开始");
		jItems[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0)); // 设置快捷键
		jItems[2] = new JRadioButtonMenuItem("低");
		jItems[3] = new JRadioButtonMenuItem("中");
		jItems[4] = new JRadioButtonMenuItem("高");
		jItems[5] = new JMenuItem("退出");
		jItems[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		jItems[6] = new JRadioButtonMenuItem("单人模式");
		jItems[7] = new JRadioButtonMenuItem("双人模式");
		jItems[8] = new JMenuItem("挑战");

		// 设置菜单项的首选大小和最大大小
		jItems[0].setPreferredSize(new Dimension(215, 40)); // 开始
		jItems[1].setPreferredSize(new Dimension(215, 40)); // 暂停/开始
		jItems[2].setPreferredSize(new Dimension(75, 40)); // 低
		jItems[3].setPreferredSize(new Dimension(75, 40)); // 中
		jItems[4].setPreferredSize(new Dimension(75, 40)); // 高
		jItems[5].setPreferredSize(new Dimension(160, 40)); // 退出
		jItems[6].setPreferredSize(new Dimension(160, 40)); // 单人模式
		jItems[7].setPreferredSize(new Dimension(160, 40)); // 双人模式
		jItems[8].setPreferredSize(new Dimension(110, 40)); // 挑战
		// 默认选中“低”菜单项
		jItems[2].setSelected(true);
		// 默认选中“单人模式”菜单项
		jItems[6].setSelected(true);

		// 创建并添加难度菜单项
		ButtonGroup difficultyGroup = new ButtonGroup();
		difficultyGroup.add(jItems[2]);
		difficultyGroup.add(jItems[3]);
		difficultyGroup.add(jItems[4]);

		difficultyMenu.add(jItems[2]);
		difficultyMenu.add(jItems[3]);
		difficultyMenu.add(jItems[4]);

		// 创建并添加模式菜单项
		ButtonGroup modeGroup = new ButtonGroup(); // 创建模式按钮组
		modeGroup.add(jItems[6]);
		modeGroup.add(jItems[7]);

		modeMenu.add(jItems[6]);
		modeMenu.add(jItems[7]);

		jBar.add(jItems[0]); // jItems[0]是开始游戏的菜单项
		jBar.add(jItems[1]); // jItems[1]是暂停/开始游戏的菜单项
		jBar.add(difficultyMenu);
		jBar.add(modeMenu); // 添加模式菜单
		jBar.add(jItems[8]); // 添加挑战菜单项
		jBar.add(jItems[5]); // jItems[5]是退出游戏的菜单项

		for (int i = 0; i < jItems.length; i++) {
			jItems[i].addActionListener(new MyAction()); // 为所有菜单项添加事件监听器，处理菜单项的单击事件
		}

		// 创建并添加用于显示得分的标签
		scoreLabel = new JLabel("得分: 0"); // 创建得分标签
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT); // 设置标签中的文本右对齐
		jBar.add(Box.createHorizontalGlue()); // 添加弹性空间以将得分标签推到右侧
		jBar.add(scoreLabel); // 添加得分标签

		setJMenuBar(jBar); // 设置菜单栏

		gPanel = new GamePanel(this);
		gPanel.setBounds(0, 0, GAME_WIDTH, GAME_HEIGHT); // 设置游戏面板的位置和大小
		add(gPanel);
		addWindowListener(new WindowAdapter() { // 为窗口添加窗口事件监听器
			@Override
			public void windowClosing(WindowEvent e) {
				// 弹出对话框，询问用户是否退出游戏
				int result = JOptionPane.showConfirmDialog(null, "是否退出游戏？", "退出游戏", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		setVisible(true);
		gPanel.requestFocus();
		showScore();
	}

	private void showScore() {
		Timer timer = new Timer(100, e -> {
			scoreLabel.setText("得分: " + gPanel.getScore() + " 长度: " + gPanel.getLength() + " 速度: " + (300 - gPanel.getSpeed()));
		});
		timer.start();
	}

	private class MyAction implements ActionListener { // 菜单项的事件监听器
		@Override
		public void actionPerformed(ActionEvent e) { // 处理菜单项的单击事件
			String s = e.getActionCommand(); // 获取事件源的名称
			switch (s) {
				case "重新开始":
					gPanel.againStart();
					break;
				case "暂停/开始":
					gPanel.stop();
					break;
				case "低":
					gPanel.setSpeed(200);
					break;
				case "中":
					gPanel.setSpeed(150);
					break;
				case "高":
					gPanel.setSpeed(100);
					break;
				case "挑战":
					//暂停
					gPanel.superStop();
					//先判断是否已经是挑战模式,如果是，询问是否退出挑战模式
					if (isChallengeMode) {
						int result = JOptionPane.showConfirmDialog(null, "是否退出挑战模式？", "退出挑战", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							isChallengeMode = false;
							gPanel.setSpeed(200);
							gear=null;
							//重新开始
							gPanel.againStart();
//							gPanel.stop();
						}
						break;
					}
					int result = JOptionPane.showConfirmDialog(null, "是否挑战？", "挑战", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						isChallengeMode = true;
						gPanel.setSpeed(100);
						gPanel.stop();
					}
//					isChallengeMode = true;
//					gPanel.setSpeed(110);
					break;
				case "退出":
					//暂停
					gPanel.superStop();
					// 弹出对话框，询问用户是否退出游戏
					 result = JOptionPane.showConfirmDialog(null, "是否退出游戏？", "退出游戏", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION)
						System.exit(0);
					else {
						//继续
						gPanel.stop();
					}
					break;
				case "单人模式":
					isTwoPlayerMode = false; // 设置为单人模式
					break;
				case "双人模式":
					isTwoPlayerMode = true; // 设置为双人模式
					break;
				default:
					break;
			}
		}
	}

	public boolean isTwoPlayerMode() {
		return isTwoPlayerMode;
	}

	public boolean isChallengeMode() {
		return isChallengeMode;
	}
}
