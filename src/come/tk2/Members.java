package come.tk2;

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
	
		Bullet b=null;
		
		public MyTank(int x,int y) {
			super(x,y);
			
		}
		//开火
		public void shotEnermy() {
			
			
			switch(this.direct){
			case 0:
				b=new Bullet(x+10,y,0);
				break;
			case 1:
				b=new Bullet(x+10, y+30,1);
				break;
			case 2:
				b=new Bullet(x, y+10,2);
				break;
			case 3:
				b=new Bullet(x+30, y+10,3);
				break;
			}
			
			Thread t=new Thread(b);
			t.start();
	}
		
		//坦克向上移动
	public void moveUp() {
			y-=speed;
		}
		public void moveDown() {
			y+=speed;
		}
		
		public void moveLeft(){
			x-=speed;
		}
		public void moveRight(){
			x+=speed;
		}
}
//敌人的坦克
class EnemyTank extends Tank{
	
	public EnemyTank(int x, int y) {
		super(x, y);
		
	}
	 
}


