package com.jiazhong;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.LinkedList;

public class Snake {
	private LinkedList<Point> snakeBody;// 蛇身
	private Point snakeTail;// 蛇尾
	private final Sound sound = new Sound();// 音效
	// 方向
	final int UP = -1;
	final int DOWN = 1;
	final int LEFT = -2;
	final int RIGHT = 2;

	private boolean life;// 生命标记
	private int oldDirection;// 原始方向
	private int newDirection;// 新方向（按键后的方向）
	private Color color;// 蛇身颜色

	public Snake() {
		init();
	}

	// 初始化
	public void init() {
		snakeBody = new LinkedList<>();
		snakeTail = new Point();
		life = true;
		oldDirection = newDirection = RIGHT;
		color = Color.YELLOW;
		snakeBody.add(new Point(4, 5));
		snakeBody.add(new Point(5, 5));
		snakeBody.add(new Point(6, 5));
	}

	// 改变方向
	public void changeDeriction(int d) {
		newDirection = d;
	}

	// 蛇移动
	public void snakeMove() {
		if ((oldDirection + newDirection) != 0) {// 判断是否反方向
			oldDirection = newDirection;
		}

		snakeTail = snakeBody.removeLast();// 蛇尾

		int x = snakeBody.getFirst().x;
		int y = snakeBody.getFirst().y;
		switch (oldDirection) {// 移动方向
			case UP:
				y--;
				isDied();
				break;
			case DOWN:
				y++;
				isDied();
				break;
			case LEFT:
				x--;
				isDied();
				break;
			case RIGHT:
				x++;
				isDied();
				break;
		}
		snakeBody.addFirst(new Point(x, y));
	}

	// 吃食物
	public void eatFood() {
		snakeBody.addLast(snakeTail);
	}

	// 判断蛇是否吃到食物
	public boolean isEatFood(Food food) {
		if (snakeBody.getFirst().equals(food.getFood())) {
			try {
				sound.playEatileSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	// 判断蛇是否吃到身体
	public boolean isEatBody() {
		for (int i = 1; i < snakeBody.size(); i++) {
			if (snakeBody.get(i).equals(snakeBody.getFirst())) {
				return true;
			}
		}
		return false;
	}

	// 判断是否碰到墙壁
	public boolean isMeetWall() {
		if (snakeBody.getFirst().x < 0 || snakeBody.getFirst().x > SizeConstants.WIDTH - 1 || snakeBody.getFirst().y < 0
				|| snakeBody.getFirst().y > SizeConstants.HEIGHT - 1) {
			try {
				sound.playMissileSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}

			return true;
		}
		return false;
	}

	// 判断是否碰到齿轮
	public boolean isMeetGear(Gear gear) {
		if (snakeBody.getFirst().x >=gear.getX() &&snakeBody.getFirst().x <=gear.getX()+3&& snakeBody.getFirst().y<= gear.getY()+3&&snakeBody.getFirst().y>=gear.getY()) {
			try {
				sound.playMissileSound();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	// 画蛇身
	public void drawSnake(Graphics g) {
		g.setColor(color);
		for (Point p : snakeBody) {
			g.fill3DRect(p.x * SizeConstants.SIZE, p.y * SizeConstants.SIZE, SizeConstants.SIZE, SizeConstants.SIZE, true);
		}
	}

	// 重新开始
	public void againStart() {
		snakeBody.clear();
		init();
	}

	// 判断蛇是否死亡
	public void isDied() {
		if (isEatBody() || isMeetWall()) {
			life = false;
		}
	}

	public LinkedList<Point> getSnakeBody() {
		return snakeBody;
	}

	public boolean getLife() {
		return life;
	}

	public void setLife(boolean life) {
		this.life = life;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
