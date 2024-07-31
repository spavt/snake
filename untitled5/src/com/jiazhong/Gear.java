package com.jiazhong;

public class Gear {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int direction; // 方向：0 - 静止，1 - 向右，-1 - 向左
    private int type;

    public Gear(int x, int y, int width, int height, int speed, int direction, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.direction = direction;
        this.type = type;
    }

    public Gear() {
        randomGear();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    // 随机位置生成齿轮
    public void randomGear() {
        this.x = (int)((Math.random() * SizeConstants.WIDTH / 3) + (Math.random() * SizeConstants.WIDTH / 3));
        this.y = (int)((Math.random() * SizeConstants.HEIGHT / 3) + (Math.random() * SizeConstants.HEIGHT / 3));
        this.width = SizeConstants.SIZE;
        this.height = SizeConstants.SIZE;
        this.speed = 1; // 将齿轮移动速度设置为较小值，例如1像素
        this.direction = (Math.random() > 0.5) ? 1 : -1; // 随机方向
        this.type = (int)(Math.random() * 2); // 随机类型
    }

    public void move() {
        if (direction == 1) {
            x += speed;
            if (x >= SizeConstants.WIDTH) {
                x = 0; // 回到左边
            }
        } else if (direction == -1) {
            x -= speed;
            if (x < 0) {
                x = SizeConstants.WIDTH; // 回到右边
            }
        }
    }
}
