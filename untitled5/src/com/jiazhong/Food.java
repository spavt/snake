package com.jiazhong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Food {
	private Point food;// 食物坐标
	private Random rand;// 随机数
	private Color color = Color.CYAN;// 食物颜色

	public Food() {
		rand = new Random();
		food = new Point();// 初始化食物坐标
		changeColor();// 随机颜色
		food.setLocation(randomPoint());// 随机坐标
	}

	// 产生一个食物
	public void createFood(Snake snake) {
		Point temPoint = randomPoint();// 随机坐标,tempPoint为食物坐标
		for (Point p : snake.getSnakeBody()) {// 判断食物是否与蛇重叠
			if (p.equals(temPoint)) {
				temPoint = randomPoint();// 重叠后重新产生坐标
			}
		}
		food.setLocation(temPoint);// 设置食物坐标
	}

	// 画出食物
	public void drawFood(Graphics g) {
		g.setColor(color);// 设置颜色
		g.fill3DRect(food.x * SizeConstants.SIZE, food.y * SizeConstants.SIZE, SizeConstants.SIZE, SizeConstants.SIZE,
			true);
	}

	// 得到食物位置
	public Point getFood() {
		return food;
	}

	// 改变颜色
	public void changeColor() {
		color = randomColor();
	}

	public Color getColor() {
		return color;
	}

	// 随机坐标
	private Point randomPoint() {
		int x = rand.nextInt(SizeConstants.WIDTH - 1) + 1;
		int y = rand.nextInt(SizeConstants.HEIGHT - 1) + 1;
		return new Point(x, y);
	}

	// 随机颜色
	private Color randomColor() {
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(235);
		return new Color(r, g, b);
	}

}
