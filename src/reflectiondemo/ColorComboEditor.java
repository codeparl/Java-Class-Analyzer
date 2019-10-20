/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxEditor;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

/**
 *
 * @author HASSAN
 */
public class ColorComboEditor extends  JTextField implements  ComboBoxEditor{

    public ColorComboEditor() {
        
        setBackground(Color.BLACK);
        
       // System.out.println(getText());
    }
    
    
 
    @Override
    public Component getEditorComponent() {
      return  this;
    }

    @Override
    public void setItem(Object anObject) {
        if(anObject != null)
            this.setText(anObject.toString());
    }

    @Override
    public Object getItem() {
        return this.getText();
    }

    @Override
    public void selectAll() {
        select(0, getText().length());
    }
    

    @Override
    public void addActionListener(ActionListener l) {
    }

    @Override
    public void removeActionListener(ActionListener l) {
    }   
    
    
    
}
