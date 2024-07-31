package com.jiazhong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Snake snake;
	private Food food;
	private Gear gear;
	private Timer timer;
	private int score; // 分数
	private int length = 3; // 蛇身长度
	private int speed; // 速度
	private GameFrame gameFrame; // 引用 GameFrame 实例
	private int gearMoveCounter = 0; // 齿轮移动计数器

	public GamePanel(GameFrame gameFrame) {
		this.gameFrame = gameFrame;
		snake = new Snake();
		food = new Food();
		speed = 200;
		timer = new Timer(speed, new SnakeRunListener()); // 设置定时器,控制蛇移动,速度,吃食物,碰撞检测

		setSize(SizeConstants.WIDTH * SizeConstants.SIZE, SizeConstants.HEIGHT * SizeConstants.SIZE); // 设置面板大小
		addKeyListener(new KeyControl()); // 添加键盘监听,控制蛇移动
	}

	// 开始游戏
	public void startGame() {
		score = 0;
		length = snake.getSnakeBody().size();
		if (gameFrame.isChallengeMode()) {
			gear = new Gear(); // 仅在挑战模式下初始化齿轮
			gear.randomGear(); // 随机生成齿轮
		} else {
			gear = null; // 非挑战模式下齿轮为空
		}
		timer.start(); // 启动定时器，蛇开始移动
	}

	// 暂停
	public void stop() {
		if (timer.isRunning()) { // 判断是否在运行，如果isRunning为true，则暂停
			timer.stop(); // 暂停
		} else {
			timer.start();
		}
	}
	public void superStop() {
		timer.stop();
	}

	// 重新开始
	public void againStart() {
		setSpeed(200);
		repaint();
		snake.againStart();
		startGame();
	}

	// 当吃到食物后产生新食物
	public void eatFood() {
		if (snake.isEatFood(food)) {
			addSpeed();
			timer.setDelay(speed);
			countScore();
			countLength();
			snake.eatFood();
			snake.setColor(food.getColor());
			food.changeColor();
			food.createFood(snake);
		}
	}

	// 游戏结束后的选择
	public void gameEnd() {
		if (!snake.getLife()) {
			int option = JOptionPane.showConfirmDialog(null, "是否继续?", "游戏已结束", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				setSpeed(200);
				timer.setDelay(speed);
				againStart();
			} else {
				System.exit(0);
			}
		}
	}

	// 速度增长比例
	public void addSpeed() {
		// 判断延迟是否大于25
		if (speed > 25) {
			speed = speed - 5;
		}
	}

	// 分数计算
	public void countScore() {
		score = score + 2 * (length - 2);
	}

	// 长度计算
	public void countLength() {
		length++;
	}

	public int getScore() {
		return score;
	}

	public int getLength() {
		return length;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); //双缓冲技术，防止闪烁
		for (int x = 0; x < SizeConstants.WIDTH; x++) {
			for (int y = 0; y < SizeConstants.HEIGHT; y++) {
				// 设置单元格填充颜色为白色
				g.setColor(new Color(242, 242, 242));
				// 绘制单元格
				g.fillRect(x * SizeConstants.SIZE, y * SizeConstants.SIZE, SizeConstants.SIZE, SizeConstants.SIZE);

				// 设置边框颜色为灰白色
				g.setColor(Color.white);
				// 绘制单元格边框
				g.drawRect(x * SizeConstants.SIZE, y * SizeConstants.SIZE, SizeConstants.SIZE, SizeConstants.SIZE);
			}
		}

		// 如果是挑战模式，绘制齿轮
		if (gameFrame.isChallengeMode() && gear != null) {
			g.drawImage(new ImageIcon("src/images/齿轮.jpg").getImage(), gear.getX() * SizeConstants.SIZE, gear.getY() * SizeConstants.SIZE, this);
		}

		// 绘制蛇和食物
		snake.drawSnake(g);
		food.drawFood(g);
		eatFood();
	}

	private class SnakeRunListener implements ActionListener { // 定时器监听，控制蛇移动
		@Override
		public void actionPerformed(ActionEvent e) {
			if (snake.getLife()) { // 判断蛇是否存活
				snake.snakeMove();
				if (gear != null) {
					gearMoveCounter++;
					if (gearMoveCounter >= 5) { // 每5次移动一次齿轮
						gear.move(); // 更新齿轮位置
						gearMoveCounter = 0;
					}
					if (snake.isMeetGear(gear)) { // 判断是否碰到齿轮
						System.out.println("碰到齿轮");
						snake.setLife(false); // 结束游戏
					}
				}
				gameEnd();
				repaint();
			}
		}
	}

	private class KeyControl extends KeyAdapter { // 键盘监听
		@Override
		public void keyPressed(KeyEvent e) { // 键盘按下事件
			int key = e.getKeyCode(); // 获取按下的键值
			if (gameFrame.isTwoPlayerMode()) {
				switch (key) {
					case KeyEvent.VK_UP:
						snake.changeDeriction(snake.UP);
						break;
					case KeyEvent.VK_DOWN:
						snake.changeDeriction(snake.DOWN);
						break;
					// 'a'键
					case KeyEvent.VK_A:
						snake.changeDeriction(snake.LEFT);
						break;
					// 'd'键
					case KeyEvent.VK_D:
						snake.changeDeriction(snake.RIGHT);
						break;
				}
			} else {
				switch (key) {
					// 加上‘w','a','s','d'键控制
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						snake.changeDeriction(snake.UP);
						break;
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						snake.changeDeriction(snake.DOWN);
						break;
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						snake.changeDeriction(snake.LEFT);
						break;
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						snake.changeDeriction(snake.RIGHT);
						break;
				}
			}
		}
	}
}
