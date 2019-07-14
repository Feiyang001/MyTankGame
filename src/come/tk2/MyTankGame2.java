//

package come.tk2;
import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class MyTankGame2 extends JFrame{
	
	MyPanel mp=null;
	public static void main(String[] args) {
		MyTankGame2 mtg=new MyTankGame2();
	}
	
	public MyTankGame2() {
		mp=new MyPanel();
		//����mypanel�̣߳������ﴴ�����̣߳�����������
		Thread t=new Thread(mp);
		t.start();
		
		//ע�����
		this.addKeyListener(mp);
		
		
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(400, 300);
	    this.setTitle("̹����Ϸ");
	    //this.setLocation(x, y);
	    this.setVisible(true);
	    
	}

}
  


class MyPanel extends Panel implements KeyListener,Runnable{
	MyTank mt=null;
	
	Vector<EnemyTank> ets=new Vector<EnemyTank>();
	int ensize=3;

	public MyPanel() {
		
		mt=new MyTank(100,200);
		//����һ�����˵�̹�˶���
		for(int i=0;i<ensize;i++) {
			EnemyTank et=new EnemyTank((i+1)*50,0);
			//et.setColor(1);
			et.setDirect(1);
		//���뼯��	
			ets.add(et);
		}
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 400, 300);//̹�˵Ļ����
		
		this.drawTank(mt.getX(), mt.getY(), g, this.mt.direct, 0);//ʵ����һ��̹��
		
		//�����ӵ�
		if(mt.b!=null&&mt.b.isLive==true) {
			g.fillOval(mt.b.x, mt.b.y, 2, 2);
		}
		
		for(int i=0;i<ets.size();i++) {
			   this.drawTank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getDirect(), 1);
		   }
	}
	
	
		public void drawTank(int x,int y,Graphics g,int direct,int type) {
			//�ж�ʲô���͵�̹��
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
				this.mt.shotEnermy();                               
			}
			if(e.getKeyCode()==KeyEvent.VK_J) {
				this.mt.shotEnermy();
			}
			this.repaint();//���»滭����
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
			this.repaint();
			}
			
		}	
			
}













