

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;


public class SegTreeViz extends Game {

	int a[];
	int n = 6;
	int mxe=0;
	int calBuild=0;
	int pinf=1000000000,ninf=-1000000000;
	static int r=0;
	int gridSize = 80, gridLen = 0;
    public static Display display;
	

	Node t[];
	int animSpeed = 500;

	public SegTreeViz(String title, int width, int height, int FPS) {
		super(title, width, height, FPS,display=new Display(title,width,height));
		r=0;
		this.gridLen = width / gridSize;

        t = new Node[4 * n];
		a = new int[]{1, 3, -2, 8, -7, 10, 2, 22};
		
		new Thread()
		{
			public void run()
			{
				try {
					Thread.sleep(animSpeed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
   
				
				
				display.bSum.addActionListener(new ActionListener(){    

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMax.setBackground(Color.white);
						display.bMin.setBackground(Color.white);
						display.bXor.setBackground(Color.white);
						display.bSum.setBackground(Color.magenta);
						calBuild=0;
						build(a, 1, 0, n - 1, 1, 1,0);
						
					}    
					});
				
					
				display.bXor.addActionListener(new ActionListener(){    

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMax.setBackground(Color.white);
						display.bMin.setBackground(Color.white);
						display.bSum.setBackground(Color.white);
						display.bXor.setBackground(Color.blue);
						calBuild=1;
						build(a, 1, 0, n - 1, 1, 1,1);
						
					}    
					});
					
				display.bMax.addActionListener(new ActionListener(){    

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMin.setBackground(Color.white);
						display.bXor.setBackground(Color.white);
						display.bSum.setBackground(Color.white);
						display.bMax.setBackground(Color.yellow);
						calBuild=2;
						build(a, 1, 0, n - 1, 1, 1,2);
						
					}    
					}); 

				display.bMin.addActionListener(new ActionListener(){    

					@Override
					public void actionPerformed(ActionEvent e) {
						getArray();
						display.qr.setText("");
						display.bqr.setBackground(Color.white);
						display.bMax.setBackground(Color.white);
						display.bXor.setBackground(Color.white);
						display.bSum.setBackground(Color.white);
						display.bMin.setBackground(Color.cyan);
						calBuild=3;
						build(a, 1, 0, n - 1, 1, 1,3);
						
					}    
					});

					display.bqr.addActionListener(new ActionListener(){    

						@Override
						public void actionPerformed(ActionEvent e) {
							display.qr.setText("");
							display.bqr.setBackground(Color.pink);
							getQuery();
						}    
						}); 
				

			}
		}.start();
	}

	int max(int a,int b)
	{
		if(a>b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}

	int min(int a,int b)
	{
		if(a<b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}

	void getArray()
	{
		String lines = display.t1.getText(); 
		String[] strs = lines.trim().split(" ");	
		// System.out.println(strs.length);
		
			if(strs.length>0)
			{
				n=strs.length;
				a=new int[n];	
				t = new Node[4 * n];
				for (int i = 0; i < strs.length; i++) {
					a[i] = Integer.parseInt(strs[i]);
				}
			}
	}

	void getQuery()
	{
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(int i=0;i<4*n;i++)
		{
			if(this.t[i]!=null)
			t[i].ons=0;
		}

		String linesl = display.tl.getText(); 
		String linesr = display.tr.getText(); 
		int l=Integer.parseInt(linesl);
		int r=Integer.parseInt(linesr);
		// System.out.println(l);
		// System.out.println(r);
		int ans=Querry(1, 0, n-1, l, r,calBuild);
		String s=Integer.toString(ans);
		display.qr.setText("Answer = " +s);
	}


	void build(int a[], int v, int tl, int tr, int x, int y,int id) {
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t[v] = new Node(0, tl + x, tr + x + 1, y);
		if (tl == tr) {
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			t[v].val = a[tl];
			t[v].set = true;
		} else {
			int tm = (tl + tr) / 2;
			build(a, v*2, tl, tm, x, y + 2,id);
			build(a, v*2+1, tm+1, tr, x, y + 2,id);
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(id==0) // sum
			{
				t[v].val = t[v*2].val + t[v*2+1].val;
			}
			else if(id==1)  // xor
			{
				t[v].val = t[v*2].val ^ t[v*2+1].val;
			}
			else if(id==2)  // max
			{
				t[v].val = max(t[v*2].val , t[v*2+1].val);
			}
			else if(id==3) // min
			{
				t[v].val = min(t[v*2].val , t[v*2+1].val);
			}
			t[v].c1 = t[2*v].mid;
			t[v].c2 = t[2*v + 1].mid;
			t[v].set = true;
		}
	}


	int Querry(int v, int tl, int tr, int qs, int qe,int id) {
        t[v].ons=1;

		if(tr<qs || tl>qe)
		{
			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

            t[v].ons=0;

			try {
				Thread.sleep(animSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(id==0 || id==1)
			{
				return 0;
			}
			else if(id==2)
			{
				return ninf;
			}

			return pinf;
		}

		

		if(tl>=qs && tr<=qe)
		{
			return t[v].val;
		}

		int tm = (tl + tr) / 2;
		int cans=0;
		if(id==0) // sum
		{
			try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			cans=(Querry(2*v,tl,tm,qs,qe,id)+Querry(2*v+1,tm+1,tr,qs,qe,id));
		}
		else if(id==1)  // xor
		{
			try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			cans=(Querry(2*v,tl,tm,qs,qe,id)^Querry(2*v+1,tm+1,tr,qs,qe,id));
		}
		else if(id==2)  // max
		{
			try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			cans=max(Querry(2*v,tl,tm,qs,qe,id),Querry(2*v+1,tm+1,tr,qs,qe,id));
		}
		else if(id==3) // min
		{
			try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			cans=min(Querry(2*v,tl,tm,qs,qe,id),Querry(2*v+1,tm+1,tr,qs,qe,id));
		}

		t[v].ons=0;
		try {
			Thread.sleep(animSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return cans;
	}


	

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g, Graphics2D g2) {
		g.setColor(new Color(49, 49, 49));
		g.fillRect(0, 0, width, height);
		for (int i = 0; i < 4 * n; i++)
		{
			if (t[i] != null)
				t[i].render(g2,t[i].ons);
		}
	}

	class Node {
		int val;
		int l, r, y;
		float mid;
		float c1, c2;
		boolean set = false;
		Font f;
		int k=0;
		int ons=0;
		Node(int val, int l, int r, int y) {
			this.val = val;
			this.l = l;
			this.r = r;
			this.y = y;
			mid = ((float)l + r) / 2f;
			c1 = c2 = mid;
			f = new Font("Serif", Font.PLAIN, 34);
		}

		void render(Graphics2D g,int cons)
		{
			g.setColor(new Color(81, 172, 51));
			g.setStroke(new BasicStroke(4));
			
			g.drawOval(l * gridSize - 5, y * gridSize + 50, (r - l) * gridSize - 20, gridSize );
		
			if(cons==1)
			{
				g.setColor(Color.magenta);
				g.setStroke(new BasicStroke(4));

				g.drawOval(l * gridSize - 5, y * gridSize + 50, (r - l) * gridSize - 20, gridSize );
				g.setColor(new Color(81, 172, 51));
				
			}
			// g.drawRect(10 , 10, 50, 60);

			if (set)
			{

				String toDraw = Integer.toString(val);
				FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
				int w = (int)f.getStringBounds(toDraw, frc).getWidth();
				int h = (int)f.getStringBounds(toDraw, frc).getHeight();
				g.setFont(f);
				g.drawString(toDraw, (int)(mid * gridSize) - w - 5, y * gridSize + gridSize / 2 + h +10);
			}
			

			if (c1 != mid) {
				g.drawLine((int)(mid * gridSize)-10, (y + 1) * gridSize + 50, (int)(c1 * gridSize) - 20, (y + 2) * gridSize + 47);
			}
			if (c2 != mid) {
				g.drawLine((int)(mid * gridSize)-10, (y + 1) * gridSize + 50, (int)(c2 * gridSize) - 20, (y + 2) * gridSize + 47);
			}
			
		}

	}
}
