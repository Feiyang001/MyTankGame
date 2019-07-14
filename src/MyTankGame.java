import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class MyTankGame extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args) {
		MyTankGame mtg=new MyTankGame();
	}
	
	public MyTankGame() {
		mp=new MyPanel();
		
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

class Tank{
	int x=0;
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
}

class MyPanel extends Panel implements KeyListener{
	MyTank mt=null;
	public MyPanel() {
		mt=new MyTank(10,10);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);//坦克的活动区域
		
		this.drawTank(mt.getX(), mt.getY(), g, 0, 1);//实例化一个坦克
	
	}
		
		public void drawTank(int x,int y,Graphics g,int direct,int type) {
			//判断什么类型的坦克
			switch(type){
				case 0:
				g.setColor(Color.BLUE);
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
			}
		}
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				mt.y++;
			}else if(e.getKeyCode()==KeyEvent.VK_UP) {
				mt.y--;
			}else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				mt.x--;
			}else if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				mt.x+=3;//控制小球的移速
			}
			this.repaint();
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}	
			
}













