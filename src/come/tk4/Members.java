package come.tk4;

import java.awt.Graphics;
import java.awt.Transparency;
import java.util.Vector;
import java.io.*;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

class PlaySound extends Thread{
	private String filename;
	public PlaySound(String soundfile) {
		filename=soundfile;
	}
	public void run() {
		File sondFile=new File(filename);
		AudioInputStream ais=null;
		try {
			ais=AudioSystem.getAudioInputStream(sondFile);
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
			// TODO: handle exception
		}
		AudioFormat af=ais.getFormat();
		SourceDataLine sdl=null;
		DataLine.Info info=new DataLine.Info(SourceDataLine.class,af);
		
		try {
			sdl=(SourceDataLine) AudioSystem.getLine(info);
			sdl.open(af);
			
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
			// TODO: handle exception
		}
		sdl.start();
		int n=0;
		byte[] aData=new byte[1024];
		try {
			while(n!=-1) {
				n=ais.read(aData,0,aData.length);
				if(n>=0) {
					sdl.write(aData, 0, n);
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
			return;
			// TODO: handle exception
		}finally {
			sdl.drain();
			sdl.close();
		}
	}
}
class Node {
	int x;
	int y;
	int direct;
	public Node(int x,int y ,int direct) {
		this.x=x;
		this.y=y;
		this.direct=direct;
	}
}


class Recorder {
	private static int enNum = 20;
	private static int myLife = 3;
	private static int allEnermy = 0;

	public static int getAllEnermy() {
		return allEnermy;
	}

	public static void setAllEnermy(int allEnermy) {
		Recorder.allEnermy = allEnermy;
	}

	public static int getEnNum() {
		return enNum;
	}

	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}

	public static int getMyLife() {
		return myLife;
	}

	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}

	public static void reduceenNum() {
		enNum--;
	}

	public static void reducemyLife() {
		myLife--;
	}

	public static void addNum() {
		allEnermy++;
	}

	private static FileWriter fw = null;
	private static BufferedWriter bw = null;
	private  Vector<EnermyTank> ets=new Vector<EnermyTank>();
	
	public Vector<EnermyTank> getEts() {
		return ets;
	}
	

	public  void setEts(Vector<EnermyTank> ets) {
		this.ets = ets;
	}

	public void saveEnermy() {//保存敌人的数量和坐标，方向
		try {
			fw = new FileWriter("D:\\kk.txt");
			bw = new BufferedWriter(fw);

			bw.write(allEnermy + "\r\n");
			for(int i=0;i<ets.size();i++) {
				EnermyTank et=ets.get(i);
				if(et.isLive) {
					String recorder=et.x+" "+et.y+" "+et.direct+" ";
					bw.write(recorder + "\r\n");
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    static Vector<Node> nodes=new Vector<Node>();  
	
	public Vector<Node>  recoveryEnermy() {
		try {
			fr = new FileReader("D:\\kk.txt");
			br = new BufferedReader(fr);
			String n =" ";
			n=br.readLine();//先读第一行
			allEnermy = Integer.parseInt(n);
			while((n=br.readLine())!=null) {
				String []xyz=n.split(" "); 
				for(int i=0;i<xyz.length;i++) {
					Node node=new Node(Integer.parseInt(xyz[0]),Integer.parseInt(xyz[1]) , Integer.parseInt(xyz[2])); 
					nodes.add(node);
					
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {
			try {
				br.close();
				fr.close();

			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}
		return nodes;
		
	}

	public static void keepRecorder() {

		try {
			fw = new FileWriter("D:\\kk.txt");
			bw = new BufferedWriter(fw);

			bw.write(allEnermy + "\r\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static FileReader fr = null;
	private static BufferedReader br = null;

	public static void getRecorder() {
		try {
			fr = new FileReader("D:\\kk.txt");
			br = new BufferedReader(fr);
			String s = br.readLine();
			allEnermy = Integer.parseInt(s);

		} catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		} finally {
			try {
				br.close();
				fr.close();

			} catch (Exception e2) {
				e2.printStackTrace();

			}
		}
	}

}

class Bomb {
	int x;
	int y;
	int life = 9;// 炸弹的生命
	boolean isLive = true;

	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 减少生命值
	public void lifeDown() {
		if (life > 0) {
			life--;
		} else {
			this.isLive = false;
		}
	}
}

class Bullet implements Runnable {
	int x;
	int y;
	int direct;
	int speed = 2;
	boolean isLive = true;

	public Bullet(int x, int y, int direct) {

		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}

			switch (direct) {
			case 0:
				y -= speed;
				break;
			case 1:
				y += speed;
				break;
			case 2:
				x -= speed;
				break;
			case 3:
				x += speed;
				break;
			}
			// 判断子弹是否碰到边界
			if (x < 0 || x > 400 || y < 0 || y > 300) {
				this.isLive = false;
				break;

			}
		}
	}

}

class Tank {
	int x = 0;
	// 坦克的方向，0表示向上，1表示向下，2表示向左，3表示向右
	int direct = 0;

	// 设置坦克的速度
	int speed = 2;

	// 设置坦克的颜色
	int color;
	boolean isLive = true;

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

	int y = 0;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

}

class MyTank extends Tank {

	Vector<Bullet> bs = new Vector<Bullet>();
	Bullet b = null;

	public MyTank(int x, int y) {
		super(x, y);

	}

	// 开火
	public void shotEnermy() {

		switch (this.direct) {
		case 0:
			b = new Bullet(x + 10, y, 0);
			bs.add(b);
			break;
		case 1:
			b = new Bullet(x + 10, y + 30, 1);
			bs.add(b);
			break;
		case 2:
			b = new Bullet(x, y + 10, 2);
			bs.add(b);
			break;
		case 3:
			b = new Bullet(x + 30, y + 10, 3);
			bs.add(b);
			break;
		}

		Thread t = new Thread(b);
		t.start();
	}

	// 坦克向上移动
	public void moveUp() {
		if (y > 0) {
			y -= speed;
		}
	}

	public void moveDown() {
		if (y < 270) {
			y += speed;
		}
	}

	public void moveLeft() {
		if (x > 0) {
			x -= speed;
		}
	}

	public void moveRight() {
		if (x < 370) {
			x += speed;
		}
	}
}

// 敌人的坦克
class EnermyTank extends Tank implements Runnable {

	int times = 0;
	// 定义一个向量，存放敌人坦克的子弹
	Vector<Bullet> bs = new Vector<Bullet>();

	// 定义一个向量可以访问mypanel上所有的敌人坦克
	Vector<EnermyTank> ets = new Vector<EnermyTank>();

	public EnermyTank(int x, int y) {
		super(x, y);

	}

	// 的到mypanel的敌人坦克
	public void setEts(Vector<EnermyTank> vv) {
		this.ets = vv;
	}

	// 判读是否碰到敌方坦克
	public boolean isTouchOtherEnermyTank() {
		boolean b = false;

		switch (this.direct) {
		case 0:
			for (int i = 0; i < ets.size(); i++) {
				EnermyTank et = ets.get(i);
				if (et != this) {
					if (et.direct == 0 || et.direct == 1) {
						if (this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}

					}
				}
			}

			break;
		case 1:
			for (int i = 0; i < ets.size(); i++) {
				EnermyTank et = ets.get(i);
				if (et != this) {
					if (et.direct == 0 || et.direct == 1) {
						if (this.x >= et.x && this.x <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y + 30 >= et.y
								&& this.y + 30 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30 && this.y + 30 >= et.y && this.y + 30 <= et.y + 20) {
							return true;
						}
						if (this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y + 30 >= et.y
								&& this.y + 30 <= et.y + 20) {
							return true;
						}

					}
				}
			}
			break;

		case 2:
			for (int i = 0; i < ets.size(); i++) {
				EnermyTank et = ets.get(i);
				if (et != this) {
					if (et.direct == 0 || et.direct == 1) {
						if (this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x >= et.x && this.x <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if (this.x >= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x >= et.x && this.x <= et.x + 30 && this.y + 20 >= et.y && this.y + 20 <= et.y + 20) {
							return true;
						}

					}
				}
			}

			break;

		case 3:
			for (int i = 0; i < ets.size(); i++) {
				EnermyTank et = ets.get(i);
				if (et != this) {
					if (et.direct == 0 || et.direct == 1) {
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y + 20 >= et.y
								&& this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					if (et.direct == 2 || et.direct == 3) {
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if (this.x + 30 >= et.x && this.x + 30 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}

					}
				}
			}
			break;

		}

		return b;
	}

	@Override
	public void run() {
		while (true) {
			switch (this.direct) {
			case 0:
				for (int i = 0; i < 30; i++) {
					if (y > 0 && !this.isTouchOtherEnermyTank()) {
						y -= speed;
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
				for (int i = 0; i < 30; i++) {
					if (y < 270 && !this.isTouchOtherEnermyTank()) {
						y += speed;
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
				for (int i = 0; i < 30; i++) {
					if (x > 0 && !this.isTouchOtherEnermyTank()) {
						x -= speed;
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
				for (int i = 0; i < 30; i++) {
					if (x < 370 && !this.isTouchOtherEnermyTank()) {
						x += speed;
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
			if (times % 2 == 0) {
				if (isLive) {
					if (bs.size() < 5) {
						Bullet b = null;
						switch (direct) {
						case 0:
							b = new Bullet(x + 10, y, 0);
							bs.add(b);
							break;
						case 1:
							b = new Bullet(x + 10, y + 30, 1);
							bs.add(b);
							break;
						case 2:
							b = new Bullet(x, y + 10, 2);
							bs.add(b);
							break;
						case 3:
							b = new Bullet(x + 30, y + 10, 3);
							bs.add(b);
							break;

						}
						// 启动子弹线程
						Thread t = new Thread(b);
						t.start();
					}
				}
			}

			// 坦克随机产生新的方向
			this.direct = (int) (Math.random() * 4);
			// 判断敌人坦克是不是死亡
			if (this.isLive == false) {
				break;// 坦克死亡后退出线程
			}

		}
	}

}
