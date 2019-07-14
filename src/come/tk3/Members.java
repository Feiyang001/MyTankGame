package come.tk3;

import java.awt.Graphics;
import java.util.Vector;

class Bomb{
	int x;
	int y;
	int life=9;//炸弹的生命
	boolean isLive=true;
	public Bomb(int x,int y) {
		this.x=x;
		this.y=y;
	}
	//减少生命值
	public void lifeDown() {
		if (life>0) {
			life--;
		}else {
			this.isLive=false;
		}
	}
}

class Bullet implements Runnable{
	int x;
	int y;
	int direct;
	int speed=2;
	boolean isLive=true;
	
	public Bullet(int x,int y,int direct) {
		
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
	
	public void run() {
		
		while(true) {
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		switch(direct) {
		case 0:
			y-=speed;
			break;
		case 1:
			y+=speed;
			break;
		case 2:
			x-=speed;
			break;
		case 3:
			x+=speed;
			break;
		}
		//判断子弹是否碰到边界
		if(x<0||x>400||y<0||y>300) {
			this.isLive=false;
			break;
			
		}
	  }
	}

}


class Tank{
		int x=0;
		//坦克的方向，0表示向上，1表示向下，2表示向左，3表示向右
		int direct=0;
		
		//设置坦克的速度
		int speed=2;
		
		//设置坦克的颜色
		int color;
		
		
		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public int getSpeed() {
			return speed;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}

		public int getDirect() {
			return direct;
		}

		public void setDirect(int direct) {
			this.direct = direct;
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

		int y=0;
		
		public Tank(int x,int y) {
			this.x=x;
			this.y=y;
		}
		
}


class MyTank extends Tank{
	
	Vector<Bullet> bs=new Vector<Bullet>();
		Bullet b=null;
		
		public MyTank(int x,int y) {
			super(x,y);
			
		}
		//开火
		public void shotEnermy() {
	
			switch(this.direct){
			case 0:
				b=new Bullet(x+10,y,0);
				bs.add(b);
				break;
			case 1:
				b=new Bullet(x+10, y+30,1);
				bs.add(b);
				break;
			case 2:
				b=new Bullet(x, y+10,2);
				bs.add(b);
				break;
			case 3:
				b=new Bullet(x+30, y+10,3);
				bs.add(b);
				break;
			}
			
			Thread t=new Thread(b);
			t.start();
	}
		
		//坦克向上移动
	public void moveUp() {
		if(y>0) {
			y-=speed;
			}
		}
		public void moveDown() {
			if(y<270) {
			y+=speed;
			}
		}
		
		public void moveLeft(){
			if(x>0) {
			x-=speed;
			}
		}
		public void moveRight(){
			if(x<370) {
			x+=speed;
			}
		}
}
//敌人的坦克
class EnermyTank extends Tank implements Runnable{
	
	boolean isLive=true;
	int times=0;
	//定义一个向量，存放敌人坦克的子弹
	Vector<Bullet> bs=new Vector<Bullet>();
	
	public EnermyTank(int x, int y) {
		super(x, y);
		
	}
	@Override
	public void run() {
		while(true) {
			switch(this.direct) {
			case 0:
				for(int i=0;i<30;i++) {
					if(y>0) {
				y-=speed;
					}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
				break;
				
			case 1:
				for(int i=0;i<30;i++) {
					if(y<270) {
					y+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				
				break;
			case 2:
				for(int i=0;i<30;i++) {
					if(x>0) {
					x-=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
			
				break;
			case 3:
				for(int i=0;i<30;i++) {
					if(x<370) {
					x+=speed;
					}
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				
				break;
			}
			
			this.times++;
			if(times%2==0) {
				if(isLive) {
					if(bs.size()<5 ) {
						Bullet b=null;
						switch(direct) {
						case 0:
							b=new Bullet(x+10,y,0);
							bs.add(b);
							break;
						case 1:
							b=new Bullet(x+10, y+30,1);
							bs.add(b);
							break;
						case 2:
							b=new Bullet(x, y+10,2);
						bs.add(b);
							break;
						case 3:
							b=new Bullet(x+30, y+10,3);
							bs.add(b);
							break;
						
						}
						//启动子弹线程
						Thread t=new Thread(b);
						t.start();
						}
					}
				}
			
			
			//坦克随机产生新的方向
			this.direct=(int)(Math.random()*4);
			//判断敌人坦克是不是死亡
			if(this.isLive==false) {
				break;//坦克死亡后退出线程
			}
		
		}
	}
	
}






