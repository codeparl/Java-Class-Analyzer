/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import com.jtattoo.border.JTBorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author HASSAN
 */
public class ColorDialogHandler {
    
       private final MyWindow  myWindow;
    private final JDialog  dialog;

    public ColorDialogHandler(MyWindow myWindow) {
        this.myWindow = myWindow;
         this.dialog = myWindow.colorDialog;
    }
  
    private void changePaneColor(JPanel p, int colorElement,String element){

    Color   original  =  p.getBackground();
  
    Color  newColor  =  null;
   switch(element){
       case "r":
       newColor =new Color(colorElement, original.getGreen(),original.getBlue()); 
       myWindow.redField.setText(colorElement+"");
       break;
       
        case "g":
       newColor =new Color(original.getRed(),colorElement, original.getBlue()); 
       myWindow.green.setText(colorElement+"");
       break;
       
        case "b":
       newColor =new Color(original.getRed(),original.getGreen(),colorElement); 
       myWindow.blue.setText(colorElement+"");
       break;
   }
p.setBackground(newColor);

}
    
    public  void applyCurrentColorsToPanel(HashMap<String, Color>map,JPanel...p){
  
    p[0].setBackground(map.get("odd"));
    p[1].setBackground(map.get("odd-fg"));
    p[2].setBackground(map.get("even"));
    p[3].setBackground(map.get("even-fg"));
    p[4].setBackground(map.get("bg"));
    p[5].setBackground(map.get("bg-s"));
    p[6].setBackground(map.get("fg-s"));
    
    Arrays.asList(p).forEach((t) -> {
        t.addPropertyChangeListener((evt) -> {            
            if(evt.getPropertyName().equals("background")){
          Color  c  =  t.getBackground();
           myWindow.jSlider2.setValue(c.getRed());
           myWindow.jSlider3.setValue(c.getGreen());
           myWindow.jSlider4.setValue(c.getBlue());
           }
        });
    });
    
    myWindow.colorResetMap.putAll(map);
    map.clear();
    
    
}
    
    public void setSliderValue(JTextField...field){

field[0].getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        
     SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider2, e);
   });
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
               SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider2, e);
   });

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
});
field[1].getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider3, e);
   });
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider3, e);
   });
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
});
field[2].getDocument().addDocumentListener(new DocumentListener() {
    @Override
    public void insertUpdate(DocumentEvent e) {
        
         SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider4, e);
   });
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
       update(myWindow.jSlider4, e);
   });
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
});
            
  
}
private void update(JSlider  s, DocumentEvent e){
 
 try {
    String  txt  =  e.getDocument().getText(0,e.getDocument().getLength()).trim();
   if(txt == null || !txt.matches("\\d+")) return;
   SwingUtilities.invokeLater(() -> {
       s.setValue(Integer.valueOf(txt));
   });
     
        } catch (BadLocationException ex) {
            Logger.getLogger(FontDialogHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public static class   DocFilter extends DocumentFilter{

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
              String text  = fb.getDocument().getText(0,fb.getDocument().getLength() );
              text += string;
                    if( text.matches("\\d+") && (fb.getDocument().getLength()+string.length()) <= 3  
                          
                            ){

                          super.insertString(fb, offset, string, attr); 
                    
                    }
         
        }//end method

     
        @Override
public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
       String txt  = fb.getDocument().getText(0,fb.getDocument().getLength() );
       txt += text;
           if ( txt.matches("\\d+") &&   (fb.getDocument().getLength() + text.length()
             - length) <= 3  
                 
                   )
   
            super.replace(fb, offset, length, text, attrs);
        
        }
   
}

public void addFocusListenerTo(JTextField...field){
 
    for (JTextField f : field) {
       f.addFocusListener(new FocusAdapter() {
           @Override
           public void focusLost(FocusEvent e) {
               if(f.getText().length() == 0 || f.getText().equals(""))
                   f.setText("0");
           }
           
});
    }
    

}
public  void getSelectedColors(HashMap<String, Color> map ,JPanel...p){
    map.put("odd",p[0].getBackground());
    map.put("odd-fg",p[1].getBackground());
    map.put("even",p[2].getBackground());
    map.put("even-fg",p[3].getBackground());
    map.put("bg",p[4].getBackground());
    map.put("bg-s",p[5].getBackground());
    map.put("fg-s",p[6].getBackground());
    


}


private void change(JSlider slider, JPanel preview, JCheckBox chk, String x ){
   if(chk.isSelected()){
    changePaneColor(preview, slider.getValue(), x);
          
 }

}


public  void  changeSelectedColorBySlide(JPanel  preview, JCheckBox  chk,JSlider...slider){
chk.addActionListener(activateCheckButtons(chk, preview, slider));
slider[0].addChangeListener((e) ->{change(slider[0], preview, chk,"r");} );
slider[1].addChangeListener((e) ->{change(slider[1], preview, chk,"g");} );
slider[2].addChangeListener((e) ->{change(slider[2], preview, chk,"b");} ); 
}


public ActionListener  activateCheckButtons(JCheckBox chk, JPanel p, JSlider...sl ){

return (e) -> {
    if(chk.isSelected()){
 
           Color   c  =  p.getBackground();
           sl[0].setValue(c.getRed());
           sl[1].setValue(c.getGreen());
           sl[2].setValue(c.getBlue());
           

 }
    };
}


public  Color  addColorToFavorite(JSlider...sl){

 int r  =  sl[0].getValue();
 int g  =  sl[1].getValue();
 int b  =  sl[2].getValue();
 Color  c  =  new Color(r,g,b);
    
 return   c;
}



public void addDocFilterTo(JTextField...field){
 
    for (JTextField f : field) {
       AbstractDocument  doc  = (AbstractDocument) f.getDocument();
       doc.setDocumentFilter(new DocFilter());
      
    }

}


public  void setColorTo(Color[] colors,JPanel...p){

   p[0].setBackground(colors[0]);
    p[1].setBackground(colors[1]);
     p[2].setBackground(colors[2]);
      p[3].setBackground(colors[3]);
       p[4].setBackground(colors[4]);
        p[5].setBackground(colors[5]);
         p[6].setBackground(colors[6]);

}


public  void  editFavoriteColor(HashMap<String, Color> colorMap, JComboBox<String> colors) {
ColorEidtPane   eidtPane = new ColorEidtPane();  
String selectedColor  = (String) colors.getSelectedItem();
String name   = eidtPane.showInputDialog(dialog,selectedColor);
 


if(eidtPane.renameButton.isSelected()){
if(colorMap.containsKey(selectedColor)){
    if(colorMap.containsKey(name)){
    showColorMessage(name, colorMap);
    return;
}
       ( (DefaultComboBoxModel<String>)   colors.getModel()).removeAllElements();
       Color  c  =  colorMap.remove(selectedColor);
      colorMap.put(name, c);
      
      myWindow.updateComboBox(new FavoriteColorRenderer(colorMap, myWindow),
               colors, colorMap.keySet().toArray(new String[0]));
      colors.setSelectedItem(name);
   }
}//end if 



if(eidtPane.removeAll.isSelected()){
    ( (DefaultComboBoxModel<String>)   colors.getModel()).removeAllElements();
    removeItemFromPrefs(colorMap, null);
        colorMap.clear();      
     myWindow.updateComboBox(new FavoriteColorRenderer(colorMap, myWindow),
               colors, colorMap.keySet().toArray(new String[0]));
     myWindow.edtfBtn.setEnabled(false);
}//end if 



if(eidtPane.remove.isSelected()){
   if(colorMap.containsKey(name)){
       ( (DefaultComboBoxModel<String>)   colors.getModel()).removeAllElements();
         colorMap.remove(name);
          removeItemFromPrefs(colorMap, name);
     myWindow.updateComboBox(new FavoriteColorRenderer(colorMap, myWindow),
               colors, colorMap.keySet().toArray(new String[0]));
     if(colorMap.isEmpty()) myWindow.edtfBtn.setEnabled(false);
  }//end if
}//end if


    



}//end method 

public void showColorMessage(String colorName, HashMap<String, Color> colorMap){

 JPanel p  = new JPanel();
 p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
 p.setBorder(new TitledBorder(new LineBorder(Color.DARK_GRAY), "Color Already exist",
 TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
 
 JLabel  l  =  new JLabel("<html><p>This color already exist in the list.</p>"
         + "<p>Please rename to different name.</p><html>");
 l.setHorizontalAlignment(JLabel.LEFT);
JLabel  color  = new JLabel();
color.setPreferredSize(new Dimension(20,20));
color.setOpaque(true);
color.setBackground(colorMap.get(colorName));

JLabel  name  =  new JLabel(colorName);
JPanel  p2  = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 3));

JPanel  c = new JPanel(new GridLayout(1, 2));
c.add(color);
c.add(name);
p2.add(c);

p.add(Box.createVerticalStrut(5));
 p.add(l);
 p.add(Box.createVerticalStrut(5));
  p.add(p2);
  p.add(Box.createVerticalStrut(5));
 JOptionPane.showMessageDialog(myWindow.colorDialog, p,"Color Exist",JOptionPane.WARNING_MESSAGE);
 
 

}


public void removeItemFromPrefs(HashMap<String, Color> map, String key ){

    if(key == null)
map.forEach((t, u) -> {
    myWindow.prefColorNode.remove(t);
});
    else myWindow.prefColorNode.remove(key);
        

}

public void hideLabelIcons(JLabel label, String opt){
    Icon  icon  =  new ImageIcon(getClass().getResource("img/right.png"));
      switch(opt){
          case "show":
             label.setIcon(icon);
             label.setHorizontalTextPosition(JLabel.LEFT);
             label.setIconTextGap(label.getText().length()+5);
              break;
          case "hide":
              label.setIcon(null);
              break;
              
      
      }
    

}

public void hideAllLables(JLabel...label){
   
    for (JLabel jLabel : label) {
        hideLabelIcons(jLabel, "hide");
        jLabel.setText("");
        
    }


}

public void hideLabelIcons(){

myWindow.labelMap.forEach((t, u) -> {
    
    t.addChangeListener((e) -> {
         if(t.isSelected()){
        hideLabelIcons(u,"show");
       }else {hideLabelIcons(u,"hide");
         }
        
    });
   
});


}

public  void applyNamedColor(JComboBox<String> comboBox, HashMap<String, Color> map){

   comboBox.addItemListener((e) -> {
       String key  =  (String) comboBox.getSelectedItem();
       if(map.containsKey(key)){
       Color  c  =  map.get(key);
       int r =  c.getRed();
       int g  =  c.getGreen();
       int b  =  c.getBlue();
       
       myWindow.jSlider2.setValue(r);
       myWindow.jSlider3.setValue(g);
       myWindow.jSlider4.setValue(b);
    
       }//end if 
   });
    
    


}

public  void savePresetColors(HashMap<String, Color>  colors, String file){

try(XMLEncoder   encoder  =  new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)))){
encoder.writeObject(colors);
    
}catch(Exception ex){ex.printStackTrace();}
    

}

public  HashMap<String , Color> readPresetColors(String file){
  
 HashMap<String , Color>   map  =  null;
 
 try(XMLDecoder  decoder =  new XMLDecoder(new BufferedInputStream(getClass().getResourceAsStream(file)))){
 map =  (HashMap<String, Color>) decoder.readObject();
 
 }catch(Exception e){e.printStackTrace();}
return map;
}
}//end class 