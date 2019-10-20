/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author HASSAN
 */
public class ComboEditor extends  JPanel implements  ComboBoxEditor{
  
   private JTextField  textField;

    public ComboEditor(JTextField field, JComboBox<String> box) {
        this.textField = field;
        JLabel  l  =  new JLabel("Entries");
        l.setBorder(new EmptyBorder(2,2,2,2));
       setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JPanel  p  =  new JPanel(new GridLayout(1, 1,3,2));
        p.add(textField);
        add(l);
        add(p);
        setPreferredSize(box.getPreferredSize());
       
    }

  
   
   
   
    @Override
    public Component getEditorComponent() {
      return  this;
    }

    @Override
    public void setItem(Object anObject) {
        if(anObject != null)
            this.textField.setText(anObject.toString());
    }

    @Override
    public Object getItem() {
        return this.textField.getText();
    }

    @Override
    public void selectAll() {
        textField.selectAll();
    }

    @Override
    public void addActionListener(ActionListener l) {
       textField. addActionListener(l);
        
    }

    @Override
    public void removeActionListener(ActionListener l) {
        textField.removeActionListener(l);
    }
    
    
    
}
