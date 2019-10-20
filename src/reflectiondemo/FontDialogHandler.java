/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author HASSAN
 */
public class FontDialogHandler {
    
    
    private final MyWindow  myWindow;
    private final JDialog  dialog;

    public FontDialogHandler(MyWindow w) {
        this.myWindow = w;
        this.dialog = myWindow.fontDialog;
        
    }
    
    
 public void dismissDialog(JDialog d){
     d.setVisible(false);
 }   
    
 public void showDialog(JDialog  d, Component parent){
 d.setLocationRelativeTo(parent);
 d.setVisible(true);
 
 
 }   
    
public DefaultListModel<String> getSystemFontList(){
DefaultListModel<String> model  =  new DefaultListModel<>();

    GraphicsEnvironment   ge  =  GraphicsEnvironment.getLocalGraphicsEnvironment();
    String[] fonts  =  ge.getAvailableFontFamilyNames();
    for(String f : fonts)
        model.addElement(f);

return  model;
}    

public  DefaultListModel<String> generateFontSizes(){
DefaultListModel<String> model  =  new DefaultListModel<>();
for(int i = 8; i <= 72; i += 2){
    model.addElement(String.valueOf(i));
}
return model;
}

public void processInitialFontValues(){
myWindow.familyField.setText(myWindow.jList1.getFont().getFamily());
myWindow.fontList.setSelectedValue(myWindow.jList1.getFont().getFamily(), true);
initFontStyleValue();
initFontSizeValues();
myWindow.fontPreview.setFont(deriveFont());

 
}

private void initFontStyleValue(){

    
    switch(myWindow.jList1.getFont().getStyle()){
        case Font.BOLD:
       myWindow.fontStyleList.setSelectedValue("Bold", true);   
         break;
         case  Font.PLAIN:
           myWindow.fontStyleList.setSelectedValue("Plain", true);   
             break;
               case Font.ITALIC:
             myWindow.fontStyleList.setSelectedValue("Italic", true);  
             break;
             
         case Font.BOLD | Font.ITALIC:
             myWindow.fontStyleList.setSelectedValue("Bold Italic", true);  
             break;
    
    }
myWindow.styleField.setText(myWindow.fontStyleList.getSelectedValue());
}

private void initFontSizeValues() {
     
        int font  = myWindow.jList1.getFont().getSize();
        myWindow.fontSizeList.setSelectedValue(String.valueOf(font), true);
        myWindow.sizeField.setText(myWindow.fontSizeList.getSelectedValue());
    }

public Font deriveFont(){

   Font  font  =  null;
   String family  =  myWindow.familyField.getText();
   String style  = myWindow.styleField.getText();
   int size  =  Integer.valueOf(myWindow.sizeField.getText());
   int fontStyle =  deriveFontStyle(style);
 
    font  =  new Font(family, fontStyle, size);
    return font;
    

}

private int deriveFontStyle(String size){
 int fontStyle = -1;
   switch(size){
       case "Plain":
       fontStyle =  Font.PLAIN;
       break;
   case "Bold":
       fontStyle =  Font.BOLD;
       break;
       
       case "Italic":
       fontStyle =  Font.ITALIC;
       break;
       case "Bold Italic":
       fontStyle =  Font.BOLD | Font.ITALIC;
       break;
   }
return fontStyle;
}


public  ListSelectionListener changeFontPreviewBySelection(JList<String> list, String fontElement){

return  (ListSelectionEvent e) -> {
    String element = null;
    Font previewFont  =  myWindow.fontPreview.getFont();
    switch(fontElement){
       case "name":
      element = list.getSelectedValue();
      myWindow.familyField.setText(element);  
      myWindow.fontPreview.setFont(new Font(element,previewFont.getStyle(),previewFont.getSize()));  
      
      break;
      case "style":
      element = list.getSelectedValue();
      myWindow.styleField.setText(element);  
      myWindow.fontPreview.setFont(new Font(previewFont.getFamily(),deriveFontStyle(element),previewFont.getSize()));
          
      break;
      
         case "size":
        element = list.getSelectedValue();
        myWindow.sizeField.setText(element);
        myWindow.fontPreview.setFont(new Font(previewFont.getFamily(),previewFont.getStyle(),Integer.valueOf(element)));      
      break;
      
    }
    
};


}


public  void showHelpWindows(JFrame win){

    win.setVisible(true);
     


}




}
