package fr.umlv.jphoto.cela.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;

/**
 * Draw an rule with ms units
 * 
 * @author laurent barbisan
 */
public class JTimeLineRuler extends JComponent{
    /**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 * Default Height Size
	 */
	public static final int SIZE = 22;
	
	/**
	 * increments between units
	 */
    private int increment;
    
    /**
     * Pixel/units
     */
    private int units;
    
    /**
     * Model links to JTimeLineRuler
     */
    private TableColumnModel model;

    /**
     * Constructor
     * @param model model link to JTimeLineRuler
     */
    public JTimeLineRuler(TableColumnModel  model) {
        this.model = model;
    	createListener(model);
        setPreferredSize(new Dimension (model.getTotalColumnWidth(),SIZE));
        increment = 100;
        units = 1;
    }

    /**
     * Draw the rule
     */
    protected void paintComponent(Graphics g) {
        Rectangle drawHere = g.getClipBounds();

        // Fill clipping area with dirty brown/orange.
		g.setColor(new Color(230, 163, 4));
        g.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

        // Do the ruler labels in a small font that's black.
        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(Color.black);

        // Some vars we need.
        int end = 0;
        int start = 0;
        int tickLength = 0;
        String text = null;

            start = (drawHere.x / increment) * increment;
            end = (((drawHere.x + drawHere.width) / increment) + 1)
                  * increment;

        // Make a special case of 0 to display the number
        // within the rule and draw a units label.
        if (start == 0) {
            text = Integer.toString(0) + (" ms");
            tickLength = 10;

                g.drawLine(0, SIZE-1, 0, SIZE-tickLength-1);
                g.drawString(text, 2, 9);
           text = null;
            start = increment;
        }

        // ticks and labels
        for (int i = start; i < end; i += increment) {
            if (i % units == 0)  {
                tickLength = 10;
                text = Integer.toString(i*10);
            } else {
                tickLength = 7;
                text = null;
            }

            if (tickLength != 0) {
                    g.drawLine(i, SIZE-1, i, SIZE-tickLength-1);
                    if (text != null)
                        g.drawString(text, i-6, 9);
             
            }
        }
    }

	/**
	 * Create listener for redraw line when element are added to
	 *  List
	 * @param model
	 */
	private void createListener(final TableColumnModel model )
	{
		model.addColumnModelListener(new TableColumnModelListener()
				{

					public void columnAdded(TableColumnModelEvent e) {
						setPreferredSize(new Dimension(((TableColumnModel)e.getSource()).getTotalColumnWidth(), SIZE));
						revalidate();
					}

					public void columnRemoved(TableColumnModelEvent e) {
						setPreferredSize(new Dimension(((TableColumnModel)e.getSource()).getTotalColumnWidth(), SIZE));
						revalidate();
					}

					public void columnMoved(TableColumnModelEvent e) {
						setPreferredSize(new Dimension(((TableColumnModel)e.getSource()).getTotalColumnWidth(), SIZE));
						revalidate();
					}

					public void columnMarginChanged(ChangeEvent e) {
						setPreferredSize(new Dimension(((TableColumnModel)e.getSource()).getTotalColumnWidth(), SIZE));
						revalidate();
					}

					public void columnSelectionChanged(ListSelectionEvent e) {
					}

				});
        		
	}
}
