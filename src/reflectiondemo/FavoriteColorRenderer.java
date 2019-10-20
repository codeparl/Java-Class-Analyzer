/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

/**
 *
 * @author HASSAN
 */
public class FavoriteColorRenderer  extends JPanel implements  ListCellRenderer<String>{
private MyWindow win;
   private HashMap<String, Color> colorMap;
 private JPanel  p; 
    public FavoriteColorRenderer(HashMap<String, Color> colorMap, MyWindow w) {
        super(new FlowLayout(FlowLayout.LEFT, 1, 1));
        this.colorMap = colorMap;
         win = w;
        color =  new JLabel();
        color.setPreferredSize(new Dimension(20,20));
        color.setOpaque(true);
        color.setBorder(new LineBorder(Color.BLACK, 1));
        name =  new JLabel();
        name.setPreferredSize(new Dimension(72,20));
        name.setOpaque(true);
        name.setForeground(Color.BLACK);    
        setOpaque(false);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
       p =  new JPanel(new GridLayout(1, 1, 2, 2));
        p.add(name);
        
        
    }
   
    
   private final  JLabel  color  ;
     private final   JLabel  name ;
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
    
        ((JScrollPane)  list.
                        getParent().
                        getParent()).
                        setVerticalScrollBarPolicy(JScrollPane
                  .VERTICAL_SCROLLBAR_ALWAYS);
          
        color.setBackground(colorMap.get(value)); 
         setPreferredSize(new Dimension(list.getSize().width,21));
        name.setText(value);
     
        if(isSelected)
             {
          name.setBackground(new Color(186,186,144));
         name.setForeground(Color.BLACK);
       
        
                
        }else{
         name.setBackground(Color.WHITE);
         name.setForeground(Color.BLACK);
        }
      
        add(color);
        add(name);  
       if(list.getModel().getSize() == 0)
           remove(color);
      
        
        
     return this;
    }
    
    
    
    
}
