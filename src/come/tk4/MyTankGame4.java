/*
 * 坦克可以发射多颗子弹
 * 击中目标死亡
 * 爆炸的效果
 *
 * 敌人击中我方坦克，我方坦克和敌方的子弹死亡
 * 防止敌方坦克重叠
 * 可以分关(做一个开始的panel，它是空的)
 * 可以暂停和继续
 * 记录成绩
 * 1、文件流记录成绩
 * 2、存盘退出游戏，可以保存退出时坦克的坐标，并打开的时候可以恢复
 * Java如何操作声音文件
 */

package come.tk4;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyTankGame4 extends JFrame implements ActionListener, KeyListener {

	MyPanel mp = null;
	MyStartPanel msp = null;
	JTextArea jta = null;
	JMenuBar jmb = null;
	JMenu jm1 = null, jm2 = null, jm3 = null, jm4 = null, jm5 = null;
	JMenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null, jmi6 = null;

	public static void main(String[] args) {
		MyTankGame4 mtg = new MyTankGame4();
	}

	public MyTankGame4() {

		msp = new MyStartPanel();
		this.add(msp);
		Thread t = new Thread(msp);
		t.start();

		jmb = new JMenuBar();
		jm4 = new JMenu("开始游戏（g）");
		jm4.setMnemonic('g');
		jmi1 = new JMenuItem("开始新游戏");
		jmi1.addActionListener(this);
		jmi1.setActionCommand("new");

		jmi2 = new JMenuItem("继续上局游戏");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("again");

		jmi3 = new JMenuItem("存档(s)");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("save");

		jmi4 = new JMenuItem("退出(e)");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("exit");

		jm4.add(jmi1);
		jm4.add(jmi2);
		jm4.add(jmi3);
		jm4.add(jmi4);
		jmb.add(jm4);
		this.setJMenuBar(jmb);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 440);
		this.setTitle("坦克游戏");
		// this.setLocation(x, y);
		this.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getActionCommand().equals("again")) {
			mp=new MyPanel("again");

			// 启动mypanel线程，再哪里创建的线程，在哪里启动
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);// 线删除开始面板
			this.add(mp);
			// 注册监听
			this.addKeyListener(mp);
			this.setVisible(true);

		} else if (arg0.getActionCommand().equals("exit")) {

			System.exit(0);

		} else if (arg0.getActionCommand().equals("save")) {

			Recorder r=new Recorder();
			r.setEts(mp.ets);
			r.saveEnermy();
			System.exit(0);

		} else if (arg0.getActionCommand().equals("new")) {

			mp = new MyPanel("new");
			// 启动mypanel线程，再哪里创建的线程，在哪里启动
			Thread t = new Thread(mp);
			this.remove(msp);// 线删除开始面板
			t.start();
			this.add(mp);
			// 注册监听
			this.addKeyListener(mp);
			this.setVisible(true);// 刷新，重新绘画新的面板

		}
	}


	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}

// 开始面板提示的作用
class MyStartPanel extends JPanel implements Runnable {

	int times = 0;

	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		if (times % 2 == 0) {
			// 提示信息
			g.setColor(Color.YELLOW);
			Font myFont = new Font("宋体", Font.BOLD, 30);
			g.setFont(myFont);
			g.drawString("stage:1", 150, 150);
		}
	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			times++;
			this.repaint();
		} // TODO Auto-generated method stub

	}
}

class MyPanel extends JPanel implements KeyListener, Runnable {
	MyTank mt = null;

	Vector<EnermyTank> ets = new Vector<EnermyTank>();
	Vector<Node> nodes = new Vector<Node>();
	// 定义一个炸弹集合
	Vector<Bomb> bombs = new Vector<Bomb>();
	int ensize=2;

	// 定义图片,图片的切换组成一颗炸弹
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	public MyPanel(String flag) {

		Recorder.getRecorder();

		mt = new MyTank(100, 200);

		// 创建一辆敌人的坦克对象
		if(flag.equals("new")){
			for (int i = 0; i < ensize; i++) {
				EnermyTank et = new EnermyTank((i + 1) * 50, 0);
				// et.setColor(1);
				et.setDirect(1);
				// 将mypanel的敌人坦克向量交给敌人坦克
				et.setEts(ets);

				// 启动敌人坦克
				Thread t = new Thread(et);
				t.start();
				// 给敌人的坦克添加子弹
				Bullet b = new Bullet(et.x + 10, et.y + 30, 1);
				et.bs.add(b);
				Thread t1 = new Thread(b);
				t1.start();

				// 加入集合
				ets.add(et);
			}
		}else {
			nodes=new Recorder().recoveryEnermy();
			System.out.println("数量是"+nodes.size());
			for (int i = 0; i <nodes.size()/3; i++) {
				Node node=nodes.get(i);
				EnermyTank et = new EnermyTank(node.x,node.y);
				// et.setColor(1);
				et.setDirect(node.direct);
				// 将mypanel的敌人坦克向量交给敌人坦克
				et.setEts(ets);

				// 启动敌人坦克
				Thread t = new Thread(et);
				t.start();
				// 给敌人的坦克添加子弹
				Bullet b = new Bullet(et.x + 10, et.y + 30, 1);
				et.bs.add(b);
				Thread t1 = new Thread(b);
				t1.start();

				// 加入集合
				ets.add(et);
			}
		}

		// 初始化图片
		try {
			image1 = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\k1.png"));
			image2 = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\k2.png"));
			image3 = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\k3.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PlaySound p=new PlaySound("D:\\tutu.wav");
		p.start();

	}

	// 判断我的子弹是否击中敌方的坦克
	public void hitEnermyTank() {

		// 判断子弹是否击中敌方坦克
		for (int i = 0; i < mt.bs.size(); i++) {
			Bullet mb = mt.bs.get(i);
			if (mb.isLive) {
				for (int j = 0; j < ets.size(); j++) {
					EnermyTank et = ets.get(j);
					if (et.isLive) {
						if (this.hitTank(mb, et)) {
							Recorder.reduceenNum();
							Recorder.addNum();
						}

					}
				}
			}
		}
	}

	// 判断我方坦克是否被敌方子弹击中
	public void hitMyTank() {

		for (int i = 0; i < ets.size(); i++) {// 取出敌方坦克，
			EnermyTank et = ets.get(i);

			for (int j = 0; j < et.bs.size(); j++) {// 取出敌方坦克的子弹
				Bullet eb = et.bs.get(j);
				if (mt.isLive) {
					if (this.hitTank(eb, mt)) {
						Recorder.reducemyLife();
					}
				}
			}
		}
	}

	// 判断子弹是否击中 坦克
	public boolean hitTank(Bullet b, Tank et) {

		boolean b2 = false;
		switch (et.direct) {
			case 0:
			case 1:
				if (b.x > et.x && b.x < et.x + 20 && b.y > et.y && b.y < et.y + 30) {
					b.isLive = false;
					et.isLive = false;
					b2 = true;
					// 创建一颗炸弹
					Bomb bo = new Bomb(et.x, et.y);
					bombs.add(bo);

				}
				break;
			case 2:
			case 3:
				if (b.x > et.x && b.x < et.x + 30 && b.y > et.y && b.y < et.y + 20) {
					b.isLive = false;
					et.isLive = false;
					b2 = true;
					Bomb bo = new Bomb(et.x, et.y);
					bombs.add(bo);

				}

				break;
		}
		return b2;
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.fillRect(0, 0, 400, 300);// 坦克的活动区域
		if (mt.isLive) {
			this.drawTank(mt.getX(), mt.getY(), g, this.mt.direct, 0);// 实例化一个坦克
		}
		// 画出子弹
		for (int i = 0; i < mt.bs.size(); i++) {
			Bullet myBullet = mt.bs.get(i);
			if (myBullet != null && myBullet.isLive == true) {

				g.fillOval(myBullet.x, myBullet.y, 2, 2);
			}
			if (myBullet.isLive == false) {
				mt.bs.remove(myBullet);
			}
		}
		// 画出炸弹
		for (int i = 0; i < bombs.size(); i++) {
			Bomb bo = bombs.get(i);// 取出炸弹
			if (bo.life > 6) {
				g.drawImage(image1, bo.x, bo.y, 35, 35, this);
			} else if (bo.life > 3) {
				g.drawImage(image2, bo.x, bo.y, 35, 35, this);
			} else {
				g.drawImage(image3, bo.x, bo.y, 35, 35, this);
			}
			// 减少生命值
			bo.lifeDown();
			if (bo.life == 0) {
				bombs.remove(bo);
			}
		}

		// 画坦克
		for (int i = 0; i < ets.size(); i++) {
			EnermyTank et = ets.get(i);
			if (et.isLive) {
				this.drawTank(et.x, et.y, g, et.getDirect(), 1);
				// 画出敌人的坦克
				for (int j = 0; j < et.bs.size(); j++) {
					Bullet eb = et.bs.get(j);
					if (eb.isLive) {
						g.draw3DRect(eb.x, eb.y, 1, 1, false);
					} else {
						et.bs.remove(eb);
					}
				}
			}
		}
		this.showInfo(g);

	}

	public void showInfo(Graphics g) {
		this.drawTank(80, 330, g, 0, 1);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getEnNum() + " ", 110, 350);

		this.drawTank(140, 330, g, 0, 0);
		g.setColor(Color.BLACK);
		g.drawString(Recorder.getMyLife() + " ", 175, 350);

		g.setColor(Color.black);
		g.drawString("消灭的敌方坦克数：", 420, 30);
		this.drawTank(420, 50, g, 0, 1);
		g.drawString(Recorder.getAllEnermy() + "", 450, 65);
	}

	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		// 判断什么类型的坦克
		switch (type) {
			case 0:
				g.setColor(Color.YELLOW);
				break;
			case 1:
				g.setColor(Color.RED);
				break;

		}
		switch (direct) {
			case 0:

				g.fill3DRect(x, y, 5, 30, false);
				g.fill3DRect(x + 15, y, 5, 30, false);
				g.fill3DRect(x + 5, y + 5, 10, 20, false);
				g.fillOval(x + 5, y + 10, 10, 10);
				g.drawLine(x + 10, y + 15, x + 10, y);
				break;

			case 1:
				g.fill3DRect(x, y, 5, 30, false);
				g.fill3DRect(x + 15, y, 5, 30, false);
				g.fill3DRect(x + 5, y + 5, 10, 20, false);
				g.fillOval(x + 5, y + 10, 10, 10);
				g.drawLine(x + 10, y + 15, x + 10, y + 30);
				break;

			case 2:
				g.fill3DRect(x, y, 30, 5, false);
				g.fill3DRect(x, y + 15, 30, 5, false);
				g.fill3DRect(x + 5, y + 5, 20, 10, false);
				g.fillOval(x + 10, y + 5, 10, 10);
				g.drawLine(x + 15, y + 10, x, y + 10);
				break;

			case 3:
				g.fill3DRect(x, y, 30, 5, false);
				g.fill3DRect(x, y + 15, 30, 5, false);
				g.fill3DRect(x + 5, y + 5, 20, 10, false);
				g.fillOval(x + 10, y + 5, 10, 10);
				g.drawLine(x + 15, y + 10, x + 30, y + 10);
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_S) {
			this.mt.setDirect(1);
			this.mt.moveDown();

		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			this.mt.setDirect(0);
			this.mt.moveUp();

		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			this.mt.setDirect(2);
			this.mt.moveLeft();

		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			this.mt.setDirect(3);
			this.mt.moveRight();
			// this.mt.shotEnermy();
		}
		if (e.getKeyCode() == KeyEvent.VK_J) {

			if (mt.bs.size() <= 5) {
				mt.shotEnermy();
			}
		}

		this.repaint();// 重新绘画窗口
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			this.hitEnermyTank();

			this.hitMyTank();

			this.repaint();
		}

	}

}
