/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JList;

/**
 *
 * @author HASSAN
 */
public class PrintJob  implements  Printable{
    
   private final Object PAGE_DATA;
    private final int px;
    private int py;
   private final Font font;

    public PrintJob(Object PAGE_DATA, int x, int y, Font font) {
        this.PAGE_DATA = PAGE_DATA;
        //make sure the x is not out of printable area.
        this.px = (x > 20 && x < 200 ? x : 20);
        //make sure the y is not out of printable area.
        this.py = (y > 20 ? y : 20 );
        this.font = font;
        
        
        
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        //check if page index is valid
     if(pageIndex > 0) return  NO_SUCH_PAGE;
     
     //get graphics2d
        Graphics2D   gd  =  (Graphics2D) graphics;
        
        //now translate screen x,y into paper x,y
        gd.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        //set drawing font
        gd.setFont(font);
        //then draw things onto this page 
       JList<String>   list  =  (JList<String>) PAGE_DATA;
        list.printAll(gd);
      
    return PAGE_EXISTS;    
    }
   
    
    
    
}
