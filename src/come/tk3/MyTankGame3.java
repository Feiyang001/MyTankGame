/*
 * 坦克可以发射多颗子弹
 * 击中目标死亡
 * 爆炸的效果
 */

package come.tk3;
import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class MyTankGame3 extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args) {
		MyTankGame3 mtg=new MyTankGame3();
	}
	
	public MyTankGame3() {
		mp=new MyPanel();
		//启动mypanel线程，再哪里创建的线程，在哪里启动
		Thread t=new Thread(mp);
		t.start();
		
		//注册监听
		this.addKeyListener(mp);
		
		
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(400, 300);
	    this.setTitle("坦克游戏");
	    //this.setLocation(x, y);
	    this.setVisible(true);
	    
	}

}
  
class MyPanel extends Panel implements KeyListener,Runnable{
	MyTank mt=null;
	
	Vector<EnermyTank> ets=new Vector<EnermyTank>();
	//定义一个炸弹集合
	Vector<Bomb> bombs=new Vector<Bomb>();
	int ensize=3;
	
	//定义图片,图片的切换组成一颗炸弹
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	public MyPanel() {
		
		mt=new MyTank(100,200);
//创建一辆敌人的坦克对象
		for(int i=0;i<ensize;i++) {
			EnermyTank et=new EnermyTank((i+1)*50,0);
			//et.setColor(1);
			et.setDirect(1);
			//启动敌人坦克
			Thread t=new Thread(et);
			t.start();
			//给敌人的坦克添加子弹
			Bullet b=new Bullet(et.x+10,et.y+30,1);
			et.bs.add(b);
			Thread t1=new Thread(b);
			t1.start();
			
		//加入集合	
			ets.add(et);
		}
		//初始化图片
		
		image1=Toolkit.getDefaultToolkit().getImage("/C:\\Users\\Administrator\\Desktop\\k1.png");
		image2=Toolkit.getDefaultToolkit().getImage("/C:\\Users\\Administrator\\Desktop\\k2.png");
		image3=Toolkit.getDefaultToolkit().getImage("/C:\\Users\\Administrator\\Desktop\\k3.png");
		
	}
	 
	//判断子弹是否击中 坦克
public void hitEnermyTank(Bullet b,EnermyTank et) {
		switch(et.direct) {
		case 0:
		case 1:
			if(b.x>et.x&&b.x<et.x+20&&b.y>et.y&&b.y<et.y+30) {
				b.isLive =false;
				et.isLive=false;
				//创建一颗炸弹
				Bomb bo=new Bomb(et.x,et.y);
				bombs.add(bo);
			
			}
		case 2:
		case 3:
			if(b.x>et.x&&b.x<et.x+30&&b.y>et.y&&b.y<et.y+20) {
				b.isLive =false;
				et.isLive=false;
				
				Bomb bo=new Bomb(et.x,et.y);
				bombs.add(bo);
				
			}
		}
	}

public void paint(Graphics g) {
	super.paint(g);
	
	g.fillRect(0, 0, 400, 300);//坦克的活动区域
	
	this.drawTank(mt.getX(), mt.getY(), g, this.mt.direct, 0);//实例化一个坦克
	
	//画出子弹
	for(int i=0;i<mt.bs.size();i++) {
		Bullet myBullet=mt.bs.get(i);
	if(myBullet!=null&&myBullet.isLive==true) {
		
		g.fillOval(myBullet.x, myBullet.y, 2, 2);
		}
		if(myBullet.isLive==false) {
	    mt.bs.remove(myBullet);
		}
	}
	//画出炸弹
	for(int i=0;i<bombs.size();i++) {
		Bomb bo=bombs.get(i);//取出炸弹
		if(bo.life>6) {
			g.drawImage(image1, bo.x, bo.y, 35, 35, this);
		}else if(bo.life>3) {
			g.drawImage(image2, bo.x, bo.y, 35, 35, this);
		}else {
			g.drawImage(image3, bo.x, bo.y, 35, 35, this);
		}
		   //减少生命值
		    bo.lifeDown();
		if(bo.life==0) {
			bombs.remove(bo);
	}
}
	
	//画坦克
	for(int i=0;i<ets.size();i++) {
		EnermyTank et=ets.get(i);
		if(et.isLive)
		{
		   this.drawTank(et.x, et.y, g, et.getDirect(), 1);
		   //画出敌人的坦克
		   for(int j=0;j<et.bs.size();j++) {
			   Bullet eb=et.bs.get(j);
			   if(eb.isLive) {
			   g.draw3DRect(eb.x, eb.y, 1, 1, false);
			   }else {
				   et.bs.remove(eb);
			   }
		   }
			}
		}	
}
	
public void drawTank(int x,int y,Graphics g,int direct,int type) {
			//判断什么类型的坦克
		switch(type){
			case 0:
				g.setColor(Color.YELLOW);
				break;
			case 1:
				g.setColor(Color.RED);
				break;	
				
			}
		switch(direct){
			case 0:
				
				g.fill3DRect(x, y, 5, 30, false);
				g.fill3DRect(x+15, y, 5, 30, false);
				g.fill3DRect(x+5, y+5, 10, 20, false);
				g.fillOval(x+5, y+10, 10, 10);
				g.drawLine(x+10, y+15,x+10, y);
				break;
				
			case 1:
				g.fill3DRect(x, y, 5, 30, false);
				g.fill3DRect(x+15, y, 5, 30, false);
				g.fill3DRect(x+5, y+5, 10, 20, false);
				g.fillOval(x+5, y+10, 10, 10);
				g.drawLine(x+10, y+15,x+10, y+30);
				break;
				
			case 2:
				g.fill3DRect(x, y, 30, 5, false);
				g.fill3DRect(x, y+15, 30, 5, false);
				g.fill3DRect(x+5, y+5, 20, 10, false);
				g.fillOval(x+10, y+5, 10, 10);
				g.drawLine(x+15, y+10,x, y+10);
				break;
			
			case 3:
				g.fill3DRect(x, y, 30, 5, false);
				g.fill3DRect(x, y+15, 30, 5, false);
				g.fill3DRect(x+5, y+5, 20, 10, false);
				g.fillOval(x+10, y+5, 10, 10);
				g.drawLine(x+15, y+10,x+30, y+10);
				break;	
			}
		}
		@Override
public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_S) {
				this.mt.setDirect(1);
				this.mt.moveDown();
				
			}else if(e.getKeyCode()==KeyEvent.VK_W) {
				this.mt.setDirect(0);
				this.mt.moveUp();
				
			}else if(e.getKeyCode()==KeyEvent.VK_A) {
				this.mt.setDirect(2);
				this.mt.moveLeft();
				 
			}else if(e.getKeyCode()==KeyEvent.VK_D) {
				this.mt.setDirect(3);
				this.mt.moveRight();
				//this.mt.shotEnermy();                               
			}
			if(e.getKeyCode()==KeyEvent.VK_J) {
				
				if(mt.bs.size()<=5) {
					mt.shotEnermy();
				}
			}
				
		this.repaint();//重新绘画窗口
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
			
			while(true) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
			e.printStackTrace();
			}
	//判断子弹是否击中坦克
			for(int i=0;i<mt.bs.size();i++) {
				Bullet myBullet=mt.bs.get(i);
				if(myBullet.isLive ) {
					for(int j=0;j<ets.size();j++) {
						EnermyTank et=ets.get(j);
						if(et.isLive) {
							this.hitEnermyTank(myBullet, et);
						}				
					}
				}		
			}	
			
			this.repaint();
			}
			
		}	
			
}













