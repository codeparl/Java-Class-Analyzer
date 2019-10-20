/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author HASSAN
 */
public class MyCellRenderer extends  JLabel implements  ListCellRenderer<Object>{

    final JFrame winFrame;
    private Color[]  colors;
    public MyCellRenderer(JFrame winFrame, Color...c) {
        setOpaque(true);
        this.winFrame =  winFrame;
        colors = c;
    }
  

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    
        if(value == null) return this;
        
        list.setBackground(colors[6]);
  
        int count  =  list.getModel().getSize();
        String text  =  (String) value;
        setText(text);
        setFont(list.getFont());
        setBorder(new EmptyBorder(4,4,4,4));
        setAlignmentX(JLabel.LEFT);
        setAlignmentY(JLabel.CENTER_ALIGNMENT);
        
           if(isSelected){
            setBackground(colors[4]); //rgb(117,117,117)
            setForeground(colors[5]); //Color.YELLOW
//            String ttile  =  winFrame.getTitle();
//            String x  = getText()+" "+ttile;
//            ttile = x;
//            winFrame.setTitle(ttile);
       }  
           else{
            for(int i =  0; i < count; ++i ){
          if(index % 2 == 0){
              setBackground(colors[0]);   //rgb(236,245,248)
              setForeground(colors[1]);
          }
          else {
              setBackground(colors[2]); // rgb(250,250,250)
              setForeground(colors[3]); //Color.BLUE
          }
        
        }
        
        
        }
        
        
      
        
        
    return this; 


    }
    
    
    
   private Color rgb(int r, int g , int b){
   return new Color(r,g,b);
   } 
    
    
}
