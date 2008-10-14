package fr.umlv.jphoto.cela.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.Timer;

import fr.umlv.jphoto.cela.data.PSlide;
import fr.umlv.jphoto.cela.data.Slide;
import fr.umlv.jphoto.cela.data.SlideShow;



/**
 * Show a slideshow.
 * JSlideShow is a JCOmponent with the method paintComponent wich is
 * redefine.
 */
public class JSlideShow extends JComponent implements Runnable{

    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Thread where is run JSlideShow
	 */
    private Thread thread;
    
    /**
     * Slides show to draw
     */
    private SlideShow slides;
    
    /**
     * Iterator on slideshow
     */
    private Iterator iSlide;
    
    /**
     * Current Showed Slide
     */
    private Slide slide;
    
    /**
     * Current Time for transition
     */
    private long time;
    
    /**
     * Start Time before 1970 if the transition
     */
    private long startTime;
    
    /**
     * Dimension for the screen
     */
    private Dimension screenSize;

    /**
     * Run a new Slide Show
     * @param slides slide show to run
     */
    public JSlideShow(SlideShow slides)
    {
    	super(); 
    	time = 0;
    	this.slides = slides;
    	
    	iSlide = slides.iterator();
    	slide = (Slide)iSlide.next();
    	
    	slide.setAnimationDimension(screenSize);
    	slide.getTransition().init(slide);
    	      	
    }
    
    /**
     * Start the thread
     * @param screenSize size of screen
     */
    public void start(Dimension screenSize) {
        
    	thread = new Thread(this);
        thread.setPriority(Thread.NORM_PRIORITY);
        this.screenSize = screenSize;
        /* Start the first Counter */
        startTime =System.currentTimeMillis();
        thread.start();
        
    }

    /**
     * Stop the thread
     *
     */
    public synchronized void stop() {
        thread = null;
    }


    /**
     * Launch the slide show
     */
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
        	paintImmediately(new Rectangle(0,0, (int)getSize().getWidth(), (int)getSize().getHeight()));
        }
        thread = null;
    }

	/**
	 * Show the ask Slide.
	 */
	protected void paintComponent(Graphics g) {
		
        Graphics2D g2 = (Graphics2D)g;
        /* Retrouve la frame à afficher */
        time = System.currentTimeMillis() - startTime;
        /* Affiche la frame */
        slide.getTransition().draw(time, g2);
        
        /* Changement de slide ? */
        if(time>slide.getDuration()){
        	if(!iSlide.hasNext()){
        		return;
        	}
        	/* Initialize the next slide */
        	slide.getTransition().dispose();
        	slide = (Slide)iSlide.next();
        	slide.setAnimationDimension(getSize());
        	slide.getTransition().init(slide);
        	startTime = System.currentTimeMillis();
        	time=0;
        }     
	}
	
	/**
	 * Launch a JSlide Show
	 * @param device device use to render the full screen
	 * @param slides slides to draw
	 */
	public static void Launch(JSlideShow slides) {
		
	    	/* JFrame utilisé pour le Rendu */
			final JFrame mainFrame;
			
	    	//grab the environment
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			//grab the graphic Device
			GraphicsDevice device = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			
			mainFrame = new JFrame(gc);
			mainFrame.setUndecorated(true);
			mainFrame.setIgnoreRepaint(true);
			mainFrame.setLayout(new BorderLayout());
			
			final JToolBar toolbar = new  JToolBar("PlayerBar");
			final Timer timer = new Timer(2000,new ActionListener()
			        {
                        public void actionPerformed(ActionEvent e) {
                            toolbar.setVisible(false);
                           
                        }
			        });
			
			toolbar.add(new JButton("Play"));
			toolbar.add(new JButton("Stop"));
			toolbar.add(new JButton("Pause"));
			toolbar.setVisible(false);
			
			/* Mise à l'ecran */
			device.setFullScreenWindow(mainFrame);
						
			KeyListener keylistener =  new KeyListener()
			{
				
				public void keyTyped(KeyEvent e) {
				}
				
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					
					if(code == KeyEvent.VK_ESCAPE){
						//	Key pressed is the Escape key. Hide this Dialog.
						mainFrame.setVisible(false);
						mainFrame.dispose();
					}
				}
				
				public void keyReleased(KeyEvent e) {
				}
			};
			MouseListener mouselistener = new MouseListener()
			{

				public void mouseClicked(MouseEvent e) {
					mainFrame.setVisible(false);
					mainFrame.dispose();
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
				
			};
			MouseMotionListener motion = new MouseMotionListener()
			{

                public void mouseDragged(MouseEvent e) {
                }

                public void mouseMoved(MouseEvent e) {
                	if(timer.isRunning()==false)
                	{
                		toolbar.setVisible(true);
                		timer.start();
                		timer.setRepeats(false);
                	}
                }
			    
			};
		
			mainFrame.getContentPane().add(toolbar, BorderLayout.PAGE_START);
			mainFrame.getContentPane().add(slides, BorderLayout.CENTER);
			mainFrame.addKeyListener(keylistener);
			mainFrame.addMouseListener(mouselistener);
			mainFrame.addMouseMotionListener(motion);
			mainFrame.setVisible(true);
			
			slides.start(mainFrame.getSize());
		}

}
