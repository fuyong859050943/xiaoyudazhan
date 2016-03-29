package com.tanchiyu.object;

import java.util.Random;

import com.tanchiyu.baseobject.GameObject;

import android.content.res.Resources;
//import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

public class EnemyFish extends GameObject{
	protected int score;						 // 对象的分值
	protected int blood; 						 // 对象的当前血量
	protected int bloodVolume; 					 // 对象总的血量
//	protected boolean isExplosion;   			 // 判断是否爆炸
//	protected boolean isVisible;		 		 //	对象是否为可见状态
	/******************/
	protected int swingSpeed;					 //	摆动的速度
	protected int style;						 //	类型
	//前进的方向：0向上；1向右；2向下；3向左；4左上；5右上；6右下；7左下
	protected int forward = 1;
//	protected Thread play;						//摆动的线程
//	protected Thread swing;						//游动的线程
//	protected boolean tlife;							//线程是否运行的标志

	public EnemyFish(Resources resources)
	{
		super(resources);
	}
	//初始化数据
	public void initial(int sc, int bv, int sp, int st)
	{
		this.score = sc;
		this.bloodVolume = bv;
		this.speed = sp;
		this.style = st;
		this.swingSpeed = 40;

		Random ran = new Random();
		int f = ran.nextInt(2);
		if(f == 1)
		{
			this.forward = 1;
			this.object_x = -this.object_width;
			this.object_y = ran.nextInt((int)(this.screen_height - this.object_height));
		}else
		{
			this.forward = 3;
			this.object_x = this.screen_width;
			this.object_y = ran.nextInt((int)(this.screen_height - this.object_height));
		}
//		object_x = ran.nextInt((int)(screen_width - object_width));
//		this.tlife = true;
	}

	public void move()
	{
		int change = (int)(Math.random()*4);
		int cluck = (int)(Math.random()*100);
		if(cluck == 5)
		{
			this.forward = change;
		}
		if(this.isAlive)
		{
			if(this.object_x < -this.object_width || this.object_x > this.screen_width || this.object_y < -this.object_height || this.object_y > this.screen_height)
			{
				this.isAlive = false;
			}

			switch(this.forward)
			{
				case 0://上
					this.object_y -= this.speed;
					break;
				case 1://右
					this.rotate = true;
					this.object_x += this.speed;
					break;
				case 2://下
					this.object_y += this.speed;
					break;
				case 3://左
					this.rotate = false;
					this.object_x -= this.speed;
					break;
				case 4://左上
					this.rotate = false;
					this.object_x -= this.speed;
					this.object_y -= this.speed;
					break;
				case 5://右上
					this.rotate = true;
					this.object_x += this.speed;
					this.object_y -= this.speed;
					break;
				case 6://右下
					this.rotate = true;
					this.object_x += this.speed;
					this.object_y += this.speed;
					break;
				case 7://左下
					this.rotate = false;
					this.object_x -= this.speed;
					this.object_y += this.speed;
					break;
				default:
					break;
			}
		}
	}
	public PointF getFishMouse()
	{
		PointF p = null;
		if(this.isAlive)
		{
			if(!this.rotate)
			{
				p = new PointF(this.object_x, this.object_y+this.object_height/2);
			}else
			{
				p = new PointF(this.object_x+this.object_width, this.object_y+this.object_height/2);
			}
		}
		return p;
	}
	public RectF getCollideRect()
	{
		RectF rect = null;
		if(this.isAlive)
		{
			switch(this.forward)
			{
				case 0://上
					rect = new RectF(this.object_x, this.object_y - this.object_height/2, this.object_x + this.object_width, this.object_y);
					break;
				case 1://右
					rect = new RectF(this.object_x + this.object_width, this.object_y, this.object_x + this.object_width*3/2, this.object_y + this.object_height);
					break;
				case 2://下
					rect = new RectF(this.object_x, this.object_y + this.object_height, this.object_x + this.object_width, this.object_y + this.object_height*3/2);
					break;
				case 3://左
					rect = new RectF(this.object_x - this.object_width/2, this.object_y, this.object_x, this.object_y + this.object_height);
					break;
				default:
					break;
			}
		}
		return rect;
	}
	// 初始化图片资源

	//	public void initBitmap() {
//		// TODO Auto-generated method stub
//
//	}
//	// 对象的绘图函数
//	@Override
//	public void drawSelf(Canvas canvas) {
//		// TODO Auto-generated method stub
//		//判断敌机是否死亡状态
//
//	}
//	// 释放资源
//	@Override
//	public void release() {
//		// TODO Auto-generated method stub
//
//	}
//	// 对象的逻辑函数
//	public void logic() {
//		// TODO Auto-generated method stub
//		if (object_y < screen_height) {
//			object_y += speed;
//		}
//		else {
//			isAlive = false;
//		}
//		if(object_y + object_height > 0){
////			isVisible = true;
//		}
//		else{
////			isVisible = false;
//		}
//	}
	// 被攻击的逻辑函数
	public void attacked(int harm) {
		// TODO Auto-generated method stub
		blood -= harm;
		if (blood <= 0) {
//			isExplosion = true;
		}
	}
	// 检测碰撞
	public boolean isBeEate(MyFish mf) {
		if(!this.isAlive || !mf.isAlive())
			return false;
		RectF rect = getRect();
		PointF p = mf.getFishMouse();
		if(rect.contains(p.x, p.y) && mf.getObject_width()>= this.object_width && mf.getObject_height() >= this.object_height)
		{
			return true;
		}else
		{
			return false;
		}
//		// 矩形1位于矩形2的左侧
//		if (object_x <= obj.getObject_x()
//				&& object_x + object_width <= obj.getObject_x()) {
//			return false;
//		}
//		// 矩形1位于矩形2的右侧
//		else if (obj.getObject_x() <= object_x
//				&& obj.getObject_x() + obj.getObject_width() <= object_x) {
//			return false;
//		}
//		// 矩形1位于矩形2的上方
//		else if (object_y <= obj.getObject_y()
//				&& object_y + object_height <= obj.getObject_y()) {
//			return false;
//		}
//		// 矩形1位于矩形2的下方
//		else if (obj.getObject_y() <= object_y
//				&& obj.getObject_y() + obj.getObject_height() <= object_y) {
//			return false;
//		}

	}
	// 判断能否被检测碰撞
	public boolean isCanCollide() {
		// TODO Auto-generated method stub
//		return isAlive && !isExplosion && isVisible;
		return false;
	}
	//getter和setter方法
	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}
	public int getBlood() {
		// TODO Auto-generated method stub
		return blood;
	}
	public void setBlood(int blood) {
		// TODO Auto-generated method stub
		this.blood = blood;
	}
	public int getBloodVolume() {
		// TODO Auto-generated method stub
		return bloodVolume;
	}
	public void setBloodVolume(int bloodVolume) {
		// TODO Auto-generated method stub
		this.bloodVolume = bloodVolume;
	}
	//	public boolean isExplosion() {
//		// TODO Auto-generated method stub
//		return isExplosion;
//	}
	public int getSwingSpeed() {
		return swingSpeed;
	}
	public void setSwingSpeed(int swingSpeed) {
		this.swingSpeed = swingSpeed;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public int getForward() {
		return forward;
	}
	public void setForward(int forward) {
		this.forward = forward;
	}


}

