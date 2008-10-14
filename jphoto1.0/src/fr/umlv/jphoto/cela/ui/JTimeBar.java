package fr.umlv.jphoto.cela.ui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import fr.umlv.jphoto.cela.model.AdapterSlideShowTableModel;
import fr.umlv.jphoto.cela.model.JphotoAdaptaterListModel;
import fr.umlv.jphoto.cela.model.SlideShowAdapterTableColumnModel;
import fr.umlv.jphoto.cela.ui.renderer.HeaderSlideShowRenderer;
import fr.umlv.jphoto.cela.ui.renderer.SlideShowListCellRender;
import fr.umlv.jphoto.cela.ui.renderer.SlideShowTableRenderer;
import fr.umlv.jphoto.cela.ui.transfert.ListPictureTransferHandler;

/**
 * This class is used to manage timeline.
 * <br>We extends it from JScrollPane because JTimeBar should be a JComponent, to 
 * simplify coding we used a JScrollPane instead of JComponent.
 * The JTimeBar is built with a JScrollPane, the JScrollPane Store:
 * The JTable or the JList Depends of the ask TimeLine Type.
 * @author laurent Barbisan
 */
public class JTimeBar extends JScrollPane {
	
    /**
     * Enum use to slect the type of TimeLine
     * <br>
     * TIMELINE_SIMPLE : Simple time line
     * <br>
     * TIMELINE_ADVANCED : Advanced time line
     */
    public enum TypeTimeLineBar {
		TIMELINE_SIMPLE { String value() { return "TimeLine Simple"; }},
		TIMELINE_ADVANCED { String value() { return "TimeLine Avancé";}};
		
		abstract String value();
	}
    
    private static JTimeBar timebar = null;
	
    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	/** 
	 *  Horizontal Rule 
	 */
	final private JTimeLineRuler columnView;
	
	/**
	 *  JTable representation of Slide Show
	 */
	private JSlideTable timeline = new JSlideTable();
	
	/**
	 *	JList representation of Slide Show
	 */
	private JList jlist = new JList();
	
	/**
	 *  base model for Slide show
	 */
	private JphotoAdaptaterListModel listModel = new JphotoAdaptaterListModel();
	
	
	/**
	 *  Jpanel for inserted JComponent
	 */
	private JPanel listPanel;
	private JPanel tablePanel;
	
	public static JTimeBar getJTimeBar() {
		if(timebar == null)
			timebar = new JTimeBar();
		return timebar;
	}


	/**
	 * Constructor
	 */
	private JTimeBar()
	{
		listPanel = new JPanel();
		tablePanel = new JPanel();
		
		this.setPreferredSize(new Dimension(400,50));
		
		/* Configure JList */
		jlist.setModel(this.listModel);
		jlist.setTransferHandler(new ListPictureTransferHandler());
		jlist.setCellRenderer(new SlideShowListCellRender());
		jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		jlist.setVisibleRowCount(1);
		
		/* Configure JTable */
		timeline.setDefaultRenderer(Object.class , new SlideShowTableRenderer());
		timeline.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		timeline.setAutoCreateColumnsFromModel(false);
		timeline.getTableHeader().setDefaultRenderer(new HeaderSlideShowRenderer() );
		timeline.getTableHeader().setSize(10,40 );
		timeline.setColumnModel(new SlideShowAdapterTableColumnModel(listModel));
		timeline.setModel(new AdapterSlideShowTableModel(timeline.getColumnModel()));
		timeline.setDragEnabled(true);
	

		//Create the row and column headers	
		columnView = new JTimeLineRuler(timeline.getColumnModel());

		//Set up the layer for je JScroll pane.
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		tablePanel.setLayout(gbl);
		gbc.fill = GridBagConstraints.VERTICAL; 
		gbc.weighty = 0; gbc.weightx = 1;
		gbc.gridwidth= GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.WEST;
		tablePanel.add(timeline, gbc);
		gbc.weighty = 1;
		tablePanel.add(timeline.getTableHeader(), gbc);
		
		listPanel = new JPanel(new BorderLayout());
		listPanel.add(jlist,BorderLayout.CENTER);
		
		// Set up the componment for JScrollPane
		this.setColumnHeaderView(listPanel);
		this.setColumnHeaderView(columnView);
		this.setViewportView(listPanel);
		
	}
	

	/**
	 * @return Returns the listModel.
	 */
	public JphotoAdaptaterListModel getListModel() {
		return listModel;
	}
	/**
	 * @param listModel The listModel to set.
	 */
	public void setListModel(JphotoAdaptaterListModel listModel) {
		this.listModel = listModel;
	}
	/**
	 * @return Returns the jlist.
	 */
	public JList getJlist() {
		return jlist;
	}
	/**
	 * @param jlist The jlist to set.
	 */
	public void setJlist(JList jlist) {
		this.jlist = jlist;
	}
	/**
	 * @return Returns the timeline.
	 */
	public JSlideTable getTimeline() {
		return timeline;
	}
	/**
	 * @param timeline The timeline to set.
	 */
	public void setTimeline(JSlideTable timeline) {
		this.timeline = timeline;
	}
	
	/**
	 * Set the Type of TimeLine<br>
	 * TIMELINE_SIMPLE : simple time line<br>
	 * TIMELINE_ADVANCED : advanced time line<br>
	 * @param type
	 */
	public void SetTimeLineType(TypeTimeLineBar type)
	{

			if(type.compareTo(TypeTimeLineBar.TIMELINE_SIMPLE) == 0) {
			    this.setViewportView(listPanel);
			} else if(type.equals(TypeTimeLineBar.TIMELINE_ADVANCED)) {
			    this.setViewportView(tablePanel);
			}
			
	}
	
	/**
	 * Get
	 * @return
	 */
	public SlideShowAdapterTableColumnModel getColumnModel()
	{
	    return (SlideShowAdapterTableColumnModel)timeline.getColumnModel();
	}
	
    class ActionJTimeBar extends AbstractAction {

        private JTimeBar timebar;
        private TypeTimeLineBar type;
        /**
         * @param viewtype
         */
        public ActionJTimeBar(JTimeBar timebar,TypeTimeLineBar type) {
            super(type.value());
            this.timebar = timebar;
            this.type = type;
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
           timebar.SetTimeLineType(type);
        }
        
    }
}


