/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 *
 * @author HASSAN
 */
public class CommandsManager extends  AbstractAction{
private String  selection;
private final MyWindow  winFrame;
private final  JList<String> jList1;
private int fontSize;
private final JDialog  fontDialog;
private  FontDialogHandler dialogHandler;

        public CommandsManager(String selection, MyWindow  win) {
            super();
            this.selection = selection;
            winFrame =  win;
            jList1  = winFrame.jList1;
            fontSize = winFrame.fontSize; 
            fontDialog =  winFrame.fontDialog;
            
            
        }
       
        @Override
        public void actionPerformed(ActionEvent e) {  
            this.dialogHandler =  winFrame.dialogHandler;
            int currentFont  =  jList1.getFont().getSize();
            switch(selection){
                case "all":
                  winFrame.addSelectionToSysClapBoard(winFrame.getListEntries());
                    break;
                case "copy":
              winFrame.addSelectionToSysClapBoard(jList1.getSelectedValue());
                   break;
                case "print":
                    winFrame.print();
                    break;
                case "font+":
                
                fontSize = currentFont + 2; 
                if(fontSize  <=72){
                  jList1.setFont(new Font(jList1.getFont().getFamily(),jList1.getFont().getStyle(), fontSize));
                }
              break;
                case "font-":
                 fontSize = currentFont -2;
                   if(fontSize   >= 8){
                  jList1.setFont(new Font(jList1.getFont().getFamily(),jList1.getFont().getStyle(), fontSize));
                }
               break;
               
                case "fontDialog":
                dialogHandler.processInitialFontValues();
               dialogHandler.showDialog(winFrame.fontDialog, winFrame);
                break;
               case "colorDialog":
               winFrame.applyCurrentColorsToPanel();
               dialogHandler.showDialog(winFrame.colorDialog, winFrame);
              
                break;
                
                case "closeDialog":
                dialogHandler.dismissDialog(winFrame.fontDialog);
                break;
                 case "closeColorDialog":
                winFrame.cancelColorSelections();
                dialogHandler.dismissDialog(winFrame.colorDialog);
                break;
               
                
                case "resetColors":
                 winFrame.resetColorPanels();               
                break;
                case "applyFont":
                dialogHandler.dismissDialog(winFrame.fontDialog);                   
                winFrame.jList1.setFont(dialogHandler.deriveFont());
                break;
                
               case "applyColor":
                winFrame.applySelectedColors();
                 dialogHandler.dismissDialog(winFrame.colorDialog);   
                break;
                
                case "applySelectedColor":
                winFrame.applySelectedColors();                 
                break;
                
                  case "addFavoriteColor":
                winFrame.addFavoriteColor();
                 break;
                 
                  case "editFavoriteColor":
                winFrame.editFavoriteColor();
                 break;
                
              case "restoreDefaultColor":
                winFrame.resetDeafultColors();
                break;
                
                case "winClose":
                  winFrame.onWindowClose();
                break;
                
                  case "showHelp":    
                      
                  dialogHandler.showHelpWindows(winFrame.helpContent);
                break;
                
                   case "showFormat":    
                      
                  dialogHandler.showDialog(winFrame.formatDialog, winFrame);
                break;
            }//end switch 
           
        }//end method 


        
}   //end class  

