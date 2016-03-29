package com.tanchiyu.object;

import java.util.Random;

import com.tanchiyu.R;
import com.tanchiyu.baseobject.GameObject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;

public class MyFish extends GameObject{

	private int lifeCount;				//初始生命值
	private int score;					//分数
	private int level;					//等级
	private int swingSpeed;				//摆动的速度
	private int speedX;					//x速度
	private int speedY;					//y速度
	private int forward;				//方向
	private boolean isSuper;			//是否超级无敌
	private Bitmap sphoto;				//图片
	private Bitmap slphoto;				//缩放的图片
	private Bitmap srphoto;				//翻转缩放之后的图片
	private int	superTime;				//无敌时间

	public MyFish(Resources resources) {
		super(resources);
		// TODO Auto-generated constructor stub
		this.score = 0;
		this.forward = 3;
		this.isSuper = true;
		this.superTime = 40;
		this.level = 1;
		this.lifeCount = 10;
		this.rotate = false;
		this.speed = 8;
		this.speedX = 8;
		this.speedY = 8;
		this.swingSpeed = 40;
	}
	//初始化数据
	public void initial()
	{
		this.forward = 3;
		this.object_x = (screen_width + this.object_width)/2;
		this.object_y = (screen_height + this.object_height)/2;
	}
	// 初始化图片资源

	public void initBitmap(float sca) {

		this.scale = sca;
		this.colCount = 5;
		this.rowCount = 2;
		photo = BitmapFactory.decodeResource(resources, R.drawable.player0);
		sphoto = BitmapFactory.decodeResource(resources, R.drawable.player1);
		object_width = photo.getWidth()/this.colCount * this.scale;			//获得每一帧位图的宽
		object_height = photo.getHeight()/this.rowCount * this.scale;		//获得每一帧位图的高

		Matrix matrixL = new Matrix();
		matrixL.postScale(this.scale, this.scale);
		this.lphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixL, true);
		this.slphoto = Bitmap.createBitmap(sphoto, 0, 0, sphoto.getWidth(), sphoto.getHeight(), matrixL, true);

		Matrix matrixR = new Matrix();
		matrixR.setScale(-1, 1);
		matrixR.postTranslate(photo.getWidth(), 0);
		matrixR.postScale(this.scale, this.scale);
		//matrixR.postRotate(180);

		this.rphoto = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrixR, true);
		this.srphoto = Bitmap.createBitmap(sphoto, 0, 0, sphoto.getWidth(), sphoto.getHeight(), matrixR, true);
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
	// 对象的绘图方法
	public void drawSelf(Canvas canvas)
	{
		if(isAlive)
		{
			if(!isSuper){
				if(!rotate)
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(lphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					canvas.restore();
				}else
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					//canvas.translate(0, object_height);
					//canvas.scale(1f, -1f);
					//canvas.rotate(180);
					canvas.drawBitmap(rphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					//canvas.scale(1f, -1f);
					canvas.restore();
				}
			}else
			{
				if(!rotate && (superTime%2 == 0))
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(lphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					canvas.restore();
				}else if(rotate && (superTime%2 == 0))
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					//canvas.translate(0, object_height);
					//canvas.scale(1f, -1f);
					//canvas.rotate(180);
					canvas.drawBitmap(rphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					//canvas.scale(1f, -1f);
					canvas.restore();
				}else if(!rotate && (superTime%2 == 1))
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(slphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					canvas.restore();
				}else if(rotate && (superTime%2 == 1))
				{
					canvas.save();
					canvas.clipRect(object_x,object_y,object_x + object_width,object_y + object_height);
					canvas.drawBitmap(srphoto, object_x - (currentFrame%colCount)*object_width, object_y - (currentFrame/colCount)*object_height,paint);
					canvas.restore();
				}
				superTime--;
				if(superTime==0)
				{
					this.isSuper = false;
				}
			}

		}
	}
	// 检测碰撞
	public boolean isBeEate(EnemyFish nf) {
		if(!this.isAlive || !nf.isAlive() || isSuper)
			return false;
		RectF rect = this.getRect();
		PointF p = nf.getFishMouse();
		if(rect.contains(p.x, p.y) && nf.getObject_width()> this.object_width && nf.getObject_height() >this.object_height)
		{
			this.lifeCount--;
			this.isSuper = true;
			this.superTime = 40;
			return true;
		}else
		{
			return false;
		}
	}
	// 释放资源
	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		if(!sphoto.isRecycled()){
			sphoto.recycle();
		}
		if(!slphoto.isRecycled()){
			slphoto.recycle();
		}
		if(!srphoto.isRecycled()){
			srphoto.recycle();
		}
	}

	public int getLifeCount() {
		return lifeCount;
	}

	public void setLifeCount(int lifeCount) {
		this.lifeCount = lifeCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSwingSpeed() {
		return swingSpeed;
	}

	public void setSwingSpeed(int swingSpeed) {
		this.swingSpeed = swingSpeed;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public int getForward() {
		return forward;
	}

	public void setForward(int forward) {
		this.forward = forward;
	}

	public boolean isSuper() {
		return isSuper;
	}

	public void setSuper(boolean isSuper) {
		this.isSuper = isSuper;
	}

	public Bitmap getSphoto() {
		return sphoto;
	}

	public void setSphoto(Bitmap sphoto) {
		this.sphoto = sphoto;
	}

	public Bitmap getSlphoto() {
		return slphoto;
	}

	public void setSlphoto() {
		this.object_width = photo.getWidth()/this.colCount * this.scale;
		this.object_height = photo.getHeight()/this.rowCount * this.scale;
		Matrix matrixL = new Matrix();
		matrixL.postScale(this.scale, this.scale);
		this.slphoto = Bitmap.createBitmap(sphoto, 0, 0, sphoto.getWidth(), sphoto.getHeight(), matrixL, true);
	}

	public Bitmap getSrphoto() {
		return srphoto;
	}

	public void setSrphoto(Bitmap srphoto) {
		this.object_width = photo.getWidth()/this.colCount * this.scale;
		this.object_height = photo.getHeight()/this.rowCount * this.scale;
		Matrix matrixR = new Matrix();
		matrixR.setScale(-1, 1);
		matrixR.postTranslate(photo.getWidth(), 0);
		matrixR.postScale(this.scale, this.scale);
		//matrixR.postRotate(180);			
		this.srphoto = Bitmap.createBitmap(sphoto, 0, 0, sphoto.getWidth(), sphoto.getHeight(), matrixR, true);
	}

}
