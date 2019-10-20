/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

/**
 *
 * @author HASSAN
 */
public class Anaylizer {

    /**
     * @param args the command line arguments
     */
 
    
    
    
    
    public static void analyzeClass(String className,  DefaultListModel<String>  display,String member,JTextArea  viewPort) throws ClassNotFoundException   {
  
      Class<?> c  =  Class.forName(className);
        System.out.println(c);
       switch(member){
           case "Constructors":
               Constructor<?>[]    constructors =  c.getConstructors();
               printDetail(c,constructors,display,member,viewPort);
               break;
           case "Methods":
                Method methods[]  =  c.getMethods();
                printDetail(c,methods,display,member,viewPort);
               break;
           case "Fields":
             Field fields[]  =  c.getFields();
             printDetail(c,fields,display,member,viewPort); 
               break;
       case "All Annotations":
             Annotation[] annotations = c.getAnnotations();
             printDetail(c,annotations,display,member,viewPort); 
               break;
      
             case "All Members":
              Constructor<?>[]    constructors1 =  c.getConstructors();      
             Field fields1[]  =  c.getFields();
             Method methods1[]  =  c.getMethods();
             List<Object>  list =  new ArrayList<>();
             list.addAll(Arrays.asList(constructors1));
               list.addAll(Arrays.asList(methods1));
                list.addAll(Arrays.asList(fields1));
                Object[] o  =  list.toArray(new Object[0]);
              printAllDetail(c,display,member,viewPort,o); 
             
               break;   
               
       }//end switch  
        
      
    
            
    }//end method 
    
    
  private   static void printDetail(Class<?> c,Object[] a,  
      DefaultListModel<String> display, String member, JTextArea  viewPort){
      StringBuilder  sb  =  new StringBuilder();
     String className  =  c.toString();
     className = className.substring(className.indexOf(" "));
     
      sb.append("Analyzing ").append(member).append("\n")
    .append(computeLineDivider(viewPort))
              .append("\n").append(member).append(" of:").append(className).append("\n");
    viewPort.setText(null);
    viewPort.append(sb.toString());
    
     display.clear();
    for(Object o : a){
        display.addElement(o.toString());
       
    }
    
    
      
    }
 
  
  
 private   static void printAllDetail(Class<?> c, 
          DefaultListModel<String> display, String member, JTextArea  viewPort,Object...a){
      StringBuilder  sb  =  new StringBuilder();
     String className  =  c.toString();
     className = className.substring(className.indexOf(" "));
     
      sb.append("Analyzing ").append(member).append("\n")
    .append(computeLineDivider(viewPort))
              .append("\n").append(member).append(" of:").append(className).append("\n");
    viewPort.setText(null);
    viewPort.append(sb.toString());
    
     display.clear();
    for(Object o : a){
        display.addElement(o.toString());
       
    }
    
    
      
    }
 
  
  
  
  static int comSize;  
private static String computeLineDivider(JTextArea  ta){
    
 comSize =  (int) ta.getPreferredSize().getWidth();
 ta.addComponentListener(new ComponentAdapter() {
    
     @Override
     public void componentResized(ComponentEvent e) {
        comSize = (int) ta.getPreferredSize().getWidth();
     }    
});
String line = "";
 for(int i = 1; i  <= comSize; ++i){
   line += "=";
 }
return line;
}    
  
  public static  String[] collectJarEnities(String jar) throws IOException{

  StringBuilder  sb  =  new StringBuilder();
  ArrayList<String>    list  =  readZipFiles(jar);
  String  dir  =  new File(System.getProperty("user.dir")).getAbsoluteFile().toString();
 for (String string : list) {
      
        String x  = string;
        if(x.endsWith(".class")) {     
         x  =  x.substring(dir.length()+1);
         x  =  x.substring(0, x.length()-6);
         x  = x.replaceAll("\\\\", ".");
         sb.append(x).append("\n");

         
        }
        

      }
  
  
  return sb.toString().split("\n");
  }
  
  
      private static ArrayList<String> readZipFiles(String zipFile) throws IOException{
    ArrayList<String>   list  =  new ArrayList<>();
          
      ZipFile  sf   =  new ZipFile(new File(zipFile));
      
        Enumeration e =  sf.entries();
        
        while(e.hasMoreElements()){
            ZipEntry  ze = (ZipEntry) e.nextElement();
            list.add(new File(ze.getName()).getAbsolutePath());
            
           
        }
        
        
    sf.close();
    return list;
    }

  
  
  
}//end class 
