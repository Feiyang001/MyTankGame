package come.tk1;


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
		
		public MyTank(int x,int y) {
			super(x,y);
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


