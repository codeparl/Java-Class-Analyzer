/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reflectiondemo;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.XMLDecoder;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;




/**
 *
 * @author HASSAN
 */
public class MyWindow extends javax.swing.JFrame {

    /**
     * Creates new form MyWindow
     */
    private final JViewport  viewport;
    private final JTextArea   viewArea;
    private final JPanel portPanel;
    private final DefaultListModel<String> listModel;
    private ArrayList<String> jarFiles;
    private final Preferences  rootDir ;
    private final Preferences  rootDir2 ;
    protected final Preferences prefNode;
    protected final Preferences prefColorNode;
    private final JTextField comboEditorField;
    protected int fontSize;
    private final JPopupMenu  popupMenu;
    protected final FontDialogHandler dialogHandler;
    protected final ColorDialogHandler colorDialogHandler;
    protected  Properties  defaultProperties;
    protected  HashMap<String, Color> colorMap;
    protected  HashMap<String, Color> presetColorMap;
    protected  HashMap<String, Color> colorResetMap;
    protected  HashMap<String, Color> colorPresetMap;
    protected  HashMap<String, Color> colorFavoriteMap;
    protected  HashMap<JCheckBox, JLabel> labelMap;
    protected HelpContent  helpContent;
    DefaultComboBoxModel  comboBoxModel;
    private String browseDir =  System.getProperty("user.dir");
    protected  final FormatDialog formatDialog;
    
    
    
    public MyWindow(){
      viewport =  new JViewport();
      portPanel = new JPanel();
      viewArea  =  new JTextArea();
      listModel =  new DefaultListModel<>();
      jarFiles  =  new ArrayList<>();
      rootDir =  Preferences.userRoot();
      rootDir2 =  Preferences.userRoot();
      comboEditorField =  new JTextField();
      popupMenu=  new JPopupMenu();
      colorMap =  new HashMap<>();
      colorResetMap =  new HashMap<>();
      colorPresetMap =  new HashMap<>();
      colorFavoriteMap =  new HashMap<>();
      labelMap =  new HashMap<>();
      presetColorMap  =  new HashMap<>();
      defaultProperties =  new Properties();
      helpContent =  new HelpContent();
      helpContent.setLocationRelativeTo(this);
     comboEditorField.setColumns(34);
     comboBoxModel=  new DefaultComboBoxModel<>();
      entries =  new String[0];
     prefNode  =  rootDir.node("/com/reflectiondemo/reflection");
     prefColorNode = rootDir2.node("/com/reflectiondemo/reflection/colors");
     formatDialog =  new FormatDialog(this, true);
    initComponents();
   jComboBox2.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
    //even,even-fg,odd,odd-fg,bg-s,fg-s, bg
    Color[]  colors = {evenPane.getBackground(),evengfPane.getBackground(),
                       oddPane.getBackground(),oddgfPane.getBackground(),
                        bgselectPane.getBackground(),fgSelectPane.getBackground(),
                        bgPane.getBackground()};
    
   fontDialog.getRootPane().setDefaultButton(fontOkButton);
    colorDialogHandler =  new ColorDialogHandler(this);
    
    jList1.setCellRenderer(new MyCellRenderer(this, colors));
    
    dialogHandler = new FontDialogHandler(this);  
    
        System.out.println(this.getClass());
    
    
    setupFontDialog();
    jComboBox2.setModel(comboBoxModel);
    jComboBox2.setEditor(new ComboEditor(comboEditorField,jComboBox2));
            
        portPanel.setPreferredSize(new Dimension(getWidth()-30, 160));
        viewport.add(portPanel);
        viewport.setPreferredSize(portPanel.getPreferredSize());
        viewport.setBackground(Color.WHITE);
        portPanel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED),new EmptyBorder(5,5,10,5)));
        portPanel.add(viewArea);
        viewArea.setFont(jList1.getFont());
        viewArea.setEditable(false);
        viewArea.setBorder(new CompoundBorder(new LineBorder(Color.lightGray, 7, true), new EmptyBorder(5,5,2,10)));
        viewArea.setPreferredSize(new Dimension(getWidth()-150, 120));
        jScrollPane1.setColumnHeader(viewport);
         browseDir  = prefNode.get("dir", browseDir);
      
         confPopup();
         colorDialogHandler.addFocusListenerTo(redField,green,blue); 
         colorDialogHandler.setSliderValue(redField,green,blue); 
         colorDialogHandler.addDocFilterTo(redField,green,blue);         
  
    labelMap.put(oddchk, jLabel8);
    labelMap.put(oddfg, jLabel12);
    labelMap.put(evenchk, jLabel13);
    labelMap.put(evenfg, jLabel14);
    labelMap.put(bgchk, jLabel9);
    labelMap.put(selectchk, jLabel10);
    labelMap.put(selectfg, jLabel11);

    
    colorDialogHandler.hideAllLables(labelMap.values().toArray(new JLabel[0]));
    colorDialogHandler.hideLabelIcons();
    
   //Read jar-file-path from Preferences and fill values into an ArrayList
        readValues();
   loadPrefs(); 
    activateColorCheckBox();
     
     formatDialog.updatePreviewList(jList1);
     
       
      comboEditorField.getDocument().
          addDocumentListener(
       new DocumentListener() {
          @Override
          public void insertUpdate(DocumentEvent e) {
             
          SwingUtilities.invokeLater(() -> {  jButton1.setEnabled(true); });
         }

          @Override
          public void removeUpdate(DocumentEvent e) {
              if(e.getDocument().getLength() < 1)
              SwingUtilities.invokeLater(() -> {  jButton1.setEnabled(false); });
              
          }

          @Override
          public void changedUpdate(DocumentEvent e) {
          }
      });
 
      comboEditorField.setText("java.lang.Object");      
      printEntries(prefNode.get("comboSelectedValue2", "java.lang.Object"), 
      prefNode.get("comboSelectedValue1", "methods"));
 
 jComboBox1.setSelectedItem(prefNode.get("comboSelectedValue1", "methods"));
 jComboBox2.setSelectedItem(comboEditorField.getText());
 //read the jar-file-path list and extract all entries into a string array 
 //then upadate the comboBox with a new model holding these entries.
    new SwingWorker<Void, Void>(){
     @Override
     protected Void doInBackground() throws Exception {
         if(jarFiles.size() > 0)
              jarFiles.forEach((t) -> {        
              readJarFile(t);});
               return null;}
     
     @Override
          protected void done() {
         if(loadOption.isSelected())
             updateComboBox(entries);
              }}.execute();
   
colorDialogHandler.applyNamedColor(presetBox, presetColorMap);
colorDialogHandler.applyNamedColor(favoriteBox, colorFavoriteMap);




    }//end constcructor 
    
    
    

protected void resetDeafultColors() {

InputStream  file  =  getClass().getResourceAsStream("resource/defaults/defaultProps.xml");
 Color[]  colors =  new Color[7];
 
      try(XMLDecoder  decoder  =  new XMLDecoder(new BufferedInputStream(file))){
           for (int i = 0; i < colors.length; i++) {
              colors[i] = (Color) decoder.readObject();
    }
      } catch (Exception e) {
      }
 JPanel[]   panels =  {oddPane,oddgfPane,evenPane,evengfPane,bgPane,bgselectPane,fgSelectPane};   
 colorDialogHandler.setColorTo(colors, panels);

}

protected  void cancelColorSelections(){
colorMap.clear();
colorMap.putAll(colorResetMap);

}
protected   void resetColorPanels(){
 JPanel[]   panels =  {oddPane,oddgfPane,evenPane,evengfPane,bgPane,bgselectPane,fgSelectPane};   
 Color[] colors =  new Color[7];

 colors[0]  =  colorResetMap.get("odd");
 colors[1]  =  colorResetMap.get("odd-fg");
 colors[2]  =  colorResetMap.get("even");
 colors[3]  =  colorResetMap.get("even-fg");
 colors[4]  =  colorResetMap.get("bg");
 colors[5]  =  colorResetMap.get("bg-s");
 colors[6]  =  colorResetMap.get("fg-s");

 colorDialogHandler.setColorTo(colors, panels);    
 
}

 protected void  addFavoriteColor(){
 
     String name  =  JOptionPane.showInputDialog(colorDialog,"Name this color:");
     if(name  == null) return;
     if(colorFavoriteMap.containsKey(name)){
         colorDialogHandler.showColorMessage(name, colorFavoriteMap);
         return;
     }
     Color color  =  colorDialogHandler.addColorToFavorite(jSlider2,jSlider3,jSlider4);
     colorFavoriteMap.put(name, color);
     String[]  values  = colorFavoriteMap.keySet().toArray(new String[0]);
     updateComboBox(new FavoriteColorRenderer(colorFavoriteMap,this), favoriteBox, values);
     edtfBtn.setEnabled(true);
  
 }
 protected  void editFavoriteColor(){
  colorDialogHandler.editFavoriteColor(colorFavoriteMap,favoriteBox);
 
 }
  private void setupFontDialog(){
     fontList.setModel(dialogHandler.getSystemFontList());
    fontSizeList.setModel(dialogHandler.generateFontSizes());
    
    fontList.addListSelectionListener(dialogHandler.
            changeFontPreviewBySelection(fontList, "name"));
  
    fontStyleList.addListSelectionListener(dialogHandler.
            changeFontPreviewBySelection(fontStyleList, "style"));
    
    fontSizeList.addListSelectionListener(dialogHandler.
            changeFontPreviewBySelection(fontSizeList, "size"));
  
  }  
   
private void loadPrefs(){
storeOption.setSelected(prefNode.getBoolean("storeOption",false));
loadOption.setSelected(prefNode.getBoolean("loadOption",false));

Font  font  =  new Font(prefNode.get("fontFamily", "Tahoma"),
  prefNode.getInt("fontStyle", Font.PLAIN), prefNode.getInt("fontSize", 24));
  jList1.setFont(font);

 Color[] colors =  new Color[7];
 String[]  x  =  prefNode.get("odd","0").split(" ");
 Color c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
colors[0] = c;
colorMap.put("odd", c);
  
 x  =  prefNode.get("odd-fg","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colors[1] = c;
 colorMap.put("odd-fg", c);
 
  x  =  prefNode.get("even","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colors[2] = c;
 colorMap.put("even", c);
 
  x  =  prefNode.get("even-fg","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colors[3] = c;
 colorMap.put("even-fg", c);
 
  x  =  prefNode.get("bg","0").split(" ");
 Color c1  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colorMap.put("bg", c1);
 
  x  =  prefNode.get("bg-s","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
colors[4] = c;
colorMap.put("bg-s", c); 


  x  =  prefNode.get("fg-s","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colors[5] = c;
 colorMap.put("fg-s", c);
 
  x  =  prefNode.get("bg","0").split(" ");
 c  =  new Color(Integer.valueOf(x[0]),Integer.valueOf(x[1]),Integer.valueOf(x[2]));
 colors[6] = c;

 
jList1.setCellRenderer(new MyCellRenderer(this, colors));
  
laodFavoriteColors();
loadPresetColors();





}


private void laodFavoriteColors(){

   try {
         Arrays.asList(prefColorNode.keys()).forEach((t) -> {
                String  e[]  = prefColorNode.get(t, null).split(" ");
              
               Color  cl  =  new Color(Integer.valueOf(e[0]),Integer.valueOf(e[1]),Integer.valueOf(e[2]));
                colorFavoriteMap.put(t, cl);
                
            });     } catch (BackingStoreException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
       String v[]  = colorFavoriteMap.keySet().toArray(new String[0]);
       
    updateComboBox(new FavoriteColorRenderer(colorFavoriteMap, this), favoriteBox, v);
  
}

private void loadPresetColors(){
presetColorMap =  colorDialogHandler.readPresetColors("resource/defaults/presetColors.xml");
updateComboBox(new FavoriteColorRenderer(
 presetColorMap, this), presetBox, presetColorMap.keySet().toArray(new String[0]));

}

private void storePrefs(){
prefNode.putBoolean("storeOption", storeOption.isSelected());
prefNode.putBoolean("loadOption", loadOption.isSelected());
if(storeOption.isSelected())  
     jarFiles.forEach((t) -> {
     prefNode.put(t.substring(t.lastIndexOf(File.separator)+1), t);      
     });

prefNode.put("fontFamily", jList1.getFont().getFamily());
prefNode.putInt("fontStyle", jList1.getFont().getStyle());
prefNode.putInt("fontSize", jList1.getFont().getSize());

prefNode.put("dir", browseDir);

prefNode.put("comboSelectedValue1", (String) jComboBox1.getSelectedItem());
prefNode.put("comboSelectedValue2", comboEditorField.getText());

colorFavoriteMap.forEach((t, u) -> {
    String color  =  u.getRed()+" "+u.getGreen()+" "+u.getBlue();
    prefColorNode.put(t, color);
});

//presetColorMap.putAll(colorFavoriteMap);
//colorDialogHandler.savePresetColors(presetColorMap, "src/reflectiondemo/resource/defaults/presetColors.xml");

}
protected void onWindowClose(){
storePrefs();
System.exit(0);

}
 private void confPopup(){

 JMenuItem  copyItem = new JMenuItem(new CommandsManager("copy",this));
 copyItem.setText("Copy");
 JMenuItem  copyAllItem = new JMenuItem(new CommandsManager("all",this));
 copyAllItem.setText("Copy All");
 popupMenu.setPreferredSize(new Dimension(100,60));
 popupMenu.add(copyItem);
 popupMenu.add(copyAllItem);
 registerPopupMenuTo(jList1, popupMenu);
 
 
 listModel.addListDataListener(new ListDataListener() {
     @Override
     public void intervalAdded(ListDataEvent e) {
         copyAllMenu.setEnabled(true);
         copyAllBtn.setEnabled(true);
         printBtn.setEnabled(true);
         printmenu.setEnabled(true);
       
         
     }

     @Override
     public void intervalRemoved(ListDataEvent e) {
         
       if(listModel.getSize() == 0){
          copyAllMenu.setEnabled(false);
         copyAllBtn.setEnabled(false);
         printBtn.setEnabled(false);
         printmenu.setEnabled(false);
         copyBtn.setEnabled(false);
         copyMenu.setEnabled(false);
                 
         
         }
         
     }

     @Override
     public void contentsChanged(ListDataEvent e) {
     }
 });
 
 
     
  
 } 
private void  readValues(){
   try {
            for(int i = 0; i < prefNode.keys().length; ++i){
                String key  =prefNode.keys()[i];
                String value = prefNode.get(key, null);
                System.out.println(key+" = "+value);
                if(value.endsWith(".jar"))
                  jarFiles.add(value);
             
            }//end loop 
        
        } catch (BackingStoreException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
   
}
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fontDialog = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        fontList = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        fontStyleList = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        fontSizeList = new javax.swing.JList<>();
        jPanel8 = new javax.swing.JPanel();
        fontPreview = new javax.swing.JLabel();
        familyField = new javax.swing.JTextField();
        styleField = new javax.swing.JTextField();
        sizeField = new javax.swing.JTextField();
        cancelFontButton = new javax.swing.JButton();
        fontOkButton = new javax.swing.JButton();
        colorDialog = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edtfBtn = new javax.swing.JButton();
        presetBox = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        favoriteBox = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jSlider2 = new javax.swing.JSlider();
        jSlider3 = new javax.swing.JSlider();
        jSlider4 = new javax.swing.JSlider();
        jPanel18 = new javax.swing.JPanel();
        redField = new javax.swing.JTextField();
        green = new javax.swing.JTextField();
        blue = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        bgPane = new javax.swing.JPanel();
        oddPane = new javax.swing.JPanel();
        evenPane = new javax.swing.JPanel();
        bgselectPane = new javax.swing.JPanel();
        fgSelectPane = new javax.swing.JPanel();
        oddgfPane = new javax.swing.JPanel();
        evengfPane = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        oddchk = new javax.swing.JCheckBox();
        evenchk = new javax.swing.JCheckBox();
        selectchk = new javax.swing.JCheckBox();
        evenfg = new javax.swing.JCheckBox();
        bgchk = new javax.swing.JCheckBox();
        selectfg = new javax.swing.JCheckBox();
        oddfg = new javax.swing.JCheckBox();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        loadButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        total = new javax.swing.JLabel();
        loading = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        copyBtn = new javax.swing.JButton();
        copyAllBtn = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        clearBtn = new javax.swing.JButton();
        jToolBar4 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        printmenu = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        copyMenu = new javax.swing.JMenuItem();
        copyAllMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        clearMenu = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        loadOption = new javax.swing.JCheckBoxMenuItem();
        storeOption = new javax.swing.JCheckBoxMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        fontDialog.setTitle("Font Setup");
        fontDialog.setMinimumSize(new java.awt.Dimension(480, 520));
        fontDialog.setModal(true);
        fontDialog.setResizable(false);
        fontDialog.setSize(new java.awt.Dimension(480, 520));

        jPanel5.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder("Font Stetup"), javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(153, 153, 153))));
        jPanel5.setMaximumSize(new java.awt.Dimension(450, 441));

        jScrollPane2.setViewportView(fontList);

        fontStyleList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Plain", "Bold", "Italic", "Bold Italic" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(fontStyleList);

        fontSizeList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(fontSizeList);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Preview", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel8.setMaximumSize(new java.awt.Dimension(154, 175));
        jPanel8.setMinimumSize(new java.awt.Dimension(154, 175));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));

        fontPreview.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fontPreview.setText("Don't forget your mission");
        fontPreview.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        fontPreview.setMaximumSize(new java.awt.Dimension(154, 175));
        jPanel8.add(fontPreview);

        familyField.setEditable(false);

        styleField.setEditable(false);
        styleField.setText("Plain");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(familyField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(styleField, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sizeField)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(familyField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(styleField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {sizeField, styleField});

        cancelFontButton.setAction(new reflectiondemo.CommandsManager("closeDialog",this)
        );
        cancelFontButton.setText("Cancel");

        fontOkButton.setAction(new CommandsManager("applyFont",this)
        );
        fontOkButton.setText("Ok");

        javax.swing.GroupLayout fontDialogLayout = new javax.swing.GroupLayout(fontDialog.getContentPane());
        fontDialog.getContentPane().setLayout(fontDialogLayout);
        fontDialogLayout.setHorizontalGroup(
            fontDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fontDialogLayout.createSequentialGroup()
                .addGroup(fontDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fontDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(fontDialogLayout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(fontOkButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelFontButton)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        fontDialogLayout.setVerticalGroup(
            fontDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fontDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fontDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelFontButton)
                    .addComponent(fontOkButton))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        colorDialog.setTitle("Color Chooser");
        colorDialog.setModal(true);
        colorDialog.setResizable(false);
        colorDialog.setSize(new java.awt.Dimension(845, 690));
        colorDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                colorDialogWindowClosing(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true), javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(153, 153, 153))));

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 0), 2, true), "Named Colors"));
        jPanel11.setOpaque(false);

        jLabel7.setText("Favorite Colors");

        jLabel4.setText("Preset");

        edtfBtn.setAction(new CommandsManager("editFavoriteColor",this)
        );
        edtfBtn.setText("Edit Favorite");

        presetBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton9.setAction(new CommandsManager("addFavoriteColor",this)
        );
        jButton9.setText("Add to Favorite");

        favoriteBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(presetBox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(favoriteBox, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(edtfBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(presetBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9)
                    .addComponent(jLabel7)
                    .addComponent(edtfBtn)
                    .addComponent(favoriteBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 0), 1, true), "RGB Control"));
        jPanel12.setOpaque(false);

        jPanel16.setOpaque(false);
        jPanel16.setLayout(new java.awt.GridLayout(3, 1, 0, 3));

        jLabel3.setText("RED");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.add(jLabel3);

        jLabel5.setText("GREEN");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.add(jLabel5);

        jLabel6.setText("BLUE");
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel16.add(jLabel6);

        jPanel17.setOpaque(false);
        jPanel17.setLayout(new java.awt.GridLayout(3, 1, 0, 5));

        jSlider2.setMaximum(255);
        jSlider2.setValue(255);
        jSlider2.setOpaque(false);
        jPanel17.add(jSlider2);

        jSlider3.setMaximum(255);
        jSlider3.setValue(255);
        jSlider3.setOpaque(false);
        jPanel17.add(jSlider3);

        jSlider4.setMaximum(255);
        jSlider4.setValue(255);
        jSlider4.setOpaque(false);
        jPanel17.add(jSlider4);

        jPanel18.setOpaque(false);
        jPanel18.setLayout(new java.awt.GridLayout(3, 1, 0, 3));

        redField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        redField.setText("0");
        redField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(232, 232, 232))));
        jPanel18.add(redField);

        green.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        green.setText("0");
        green.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(232, 232, 232))));
        jPanel18.add(green);

        blue.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        blue.setText("0");
        blue.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)), javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(232, 232, 232))));
        jPanel18.add(blue);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 4, true), "Preview", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED))); // NOI18N

        bgPane.setBackground(java.awt.Color.white);
        bgPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout bgPaneLayout = new javax.swing.GroupLayout(bgPane);
        bgPane.setLayout(bgPaneLayout);
        bgPaneLayout.setHorizontalGroup(
            bgPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        bgPaneLayout.setVerticalGroup(
            bgPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        oddPane.setBackground(new java.awt.Color(236, 245, 248));
        oddPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout oddPaneLayout = new javax.swing.GroupLayout(oddPane);
        oddPane.setLayout(oddPaneLayout);
        oddPaneLayout.setHorizontalGroup(
            oddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        oddPaneLayout.setVerticalGroup(
            oddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        evenPane.setBackground(new java.awt.Color(250, 250, 250));
        evenPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout evenPaneLayout = new javax.swing.GroupLayout(evenPane);
        evenPane.setLayout(evenPaneLayout);
        evenPaneLayout.setHorizontalGroup(
            evenPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        evenPaneLayout.setVerticalGroup(
            evenPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        bgselectPane.setBackground(new java.awt.Color(117, 117, 117));
        bgselectPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));
        bgselectPane.setPreferredSize(new java.awt.Dimension(572, 37));

        javax.swing.GroupLayout bgselectPaneLayout = new javax.swing.GroupLayout(bgselectPane);
        bgselectPane.setLayout(bgselectPaneLayout);
        bgselectPaneLayout.setHorizontalGroup(
            bgselectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bgselectPaneLayout.setVerticalGroup(
            bgselectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        fgSelectPane.setBackground(java.awt.Color.yellow);
        fgSelectPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout fgSelectPaneLayout = new javax.swing.GroupLayout(fgSelectPane);
        fgSelectPane.setLayout(fgSelectPaneLayout);
        fgSelectPaneLayout.setHorizontalGroup(
            fgSelectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        fgSelectPaneLayout.setVerticalGroup(
            fgSelectPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        oddgfPane.setBackground(new java.awt.Color(0, 0, 0));
        oddgfPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout oddgfPaneLayout = new javax.swing.GroupLayout(oddgfPane);
        oddgfPane.setLayout(oddgfPaneLayout);
        oddgfPaneLayout.setHorizontalGroup(
            oddgfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        oddgfPaneLayout.setVerticalGroup(
            oddgfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        evengfPane.setBackground(java.awt.Color.blue);
        evengfPane.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 3, true));

        javax.swing.GroupLayout evengfPaneLayout = new javax.swing.GroupLayout(evengfPane);
        evengfPane.setLayout(evengfPaneLayout);
        evengfPaneLayout.setHorizontalGroup(
            evengfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        evengfPaneLayout.setVerticalGroup(
            evengfPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 31, Short.MAX_VALUE)
        );

        jFormattedTextField1.setText("jFormattedTextField1");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bgPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(evengfPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(oddgfPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bgselectPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fgSelectPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(evenPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(oddPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bgPane, evenPane, evengfPane, fgSelectPane, oddgfPane});

        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(oddPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(oddgfPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evenPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evengfPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bgPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bgselectPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(fgSelectPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel14Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bgPane, bgselectPane, evenPane, evengfPane, fgSelectPane, oddPane, oddgfPane});

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Apply To"));

        buttonGroup1.add(oddchk);
        oddchk.setText("Odd Color");
        oddchk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        oddchk.setOpaque(false);

        buttonGroup1.add(evenchk);
        evenchk.setText("Even");
        evenchk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        evenchk.setOpaque(false);

        buttonGroup1.add(selectchk);
        selectchk.setText("BG-Selection");
        selectchk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        selectchk.setOpaque(false);

        buttonGroup1.add(evenfg);
        evenfg.setText("FG-even");

        buttonGroup1.add(bgchk);
        bgchk.setText("Background");
        bgchk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bgchk.setOpaque(false);

        buttonGroup1.add(selectfg);
        selectfg.setText("FG-Selection");
        selectfg.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        selectfg.setOpaque(false);

        buttonGroup1.add(oddfg);
        oddfg.setText("FG-Odd");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(oddfg, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(oddchk)
                    .addComponent(bgchk)
                    .addComponent(selectfg)
                    .addComponent(evenchk)
                    .addComponent(evenfg)
                    .addComponent(selectchk)))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {bgchk, evenchk, evenfg, oddchk, oddfg, selectchk, selectfg});

        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(oddchk)
                .addGap(7, 7, 7)
                .addComponent(oddfg)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(evenchk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(evenfg, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(bgchk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectchk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(selectfg, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel15Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {bgchk, evenchk, evenfg, oddchk, oddfg, selectchk, selectfg});

        jPanel13.setLayout(new java.awt.GridLayout(7, 1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel8.setText("8");
        jPanel13.add(jLabel8);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel12.setText("9");
        jPanel13.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel13.setText("10");
        jPanel13.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel14.setText("11");
        jPanel13.add(jLabel14);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel9.setText("12");
        jLabel9.setPreferredSize(new java.awt.Dimension(15, 15));
        jPanel13.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel10.setText("13");
        jPanel13.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/right.png"))); // NOI18N
        jLabel11.setText("14");
        jPanel13.add(jLabel11);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel19.setLayout(new java.awt.GridLayout(1, 5, 10, 2));

        jButton5.setAction(new CommandsManager("applyColor",this
        )
    );
    jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButton5.setText("Ok");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });
    jPanel19.add(jButton5);

    jButton6.setAction(new CommandsManager("closeColorDialog",this)
    );
    jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButton6.setText("Cancel");
    jPanel19.add(jButton6);

    jButton8.setAction(new CommandsManager("restoreDefaultColor",this)
    );
    jButton8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButton8.setText("Default");
    jPanel19.add(jButton8);

    jButton7.setAction(new CommandsManager("resetColors",this
    )
    );
    jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jButton7.setText("Reset");
    jPanel19.add(jButton7);

    jButton10.setAction(new CommandsManager("applySelectedColor",this)
    );
    jButton10.setText("Apply");
    jPanel19.add(jButton10);

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(194, 194, 194))
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout colorDialogLayout = new javax.swing.GroupLayout(colorDialog.getContentPane());
    colorDialog.getContentPane().setLayout(colorDialogLayout);
    colorDialogLayout.setHorizontalGroup(
        colorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    colorDialogLayout.setVerticalGroup(
        colorDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(colorDialogLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Java Class Analyzer (Reflection)");
    setMinimumSize(new java.awt.Dimension(910, 541));
    setPreferredSize(resizeWindowToScreen());
    setSize(new java.awt.Dimension(882, 578));
    addComponentListener(new java.awt.event.ComponentAdapter() {
        public void componentResized(java.awt.event.ComponentEvent evt) {
            formComponentResized(evt);
        }
    });
    addWindowStateListener(new java.awt.event.WindowStateListener() {
        public void windowStateChanged(java.awt.event.WindowEvent evt) {
            formWindowStateChanged(evt);
        }
    });
    addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
        }
    });

    jLabel1.setText("Pick:");
    jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

    jComboBox1.setEditable(true);
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Constructors", "Methods", "Fields", "All Members", "All Annotations", "Annotaion" }));
    jComboBox1.setFocusable(false);

    jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
    jButton1.setForeground(new java.awt.Color(255, 0, 51));
    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/1rightarrow.png"))); // NOI18N
    jButton1.setText("Analyze");
    jButton1.setEnabled(false);
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jLabel2.setText("Enter the full class Name here:");
    jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

    jComboBox2.setEditable(true);

    loadButton.setText("Load");
    loadButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            loadButtonActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(loadButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(2, 2, 2)
            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(jLabel1))
                        .addComponent(jLabel2)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
    );

    jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jComboBox1, jComboBox2, jLabel1, jLabel2, loadButton});

    jPanel2.setBackground(new java.awt.Color(255, 255, 255));
    jPanel2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 102, 102), new java.awt.Color(153, 153, 153), new java.awt.Color(204, 204, 204), new java.awt.Color(0, 51, 51)), javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));

    jList1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
    jList1.setModel(listModel);
    jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            jList1ValueChanged(evt);
        }
    });
    jScrollPane1.setViewportView(jList1);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
            .addGap(0, 0, 0))
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4)));

    total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    total.setText("Nothing to show");

    loading.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createSequentialGroup()
            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(loading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(loading))
    );

    jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {loading, total});

    jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

    jToolBar1.setRollover(true);

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/OPEN.GIF"))); // NOI18N
    jButton2.setText("Load");
    jButton2.setFocusable(false);
    jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton2);

    printBtn.setAction(new reflectiondemo.CommandsManager("print",this)
    );
    printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/print.png"))); // NOI18N
    printBtn.setText("Print");
    printBtn.setEnabled(false);
    printBtn.setFocusable(false);
    printBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    printBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar1.add(printBtn);

    jToolBar2.setRollover(true);

    copyBtn.setAction(new reflectiondemo.CommandsManager("copy",this
    ));
    copyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/copy-icon.png"))); // NOI18N
    copyBtn.setText("Copy ");
    copyBtn.setEnabled(false);
    copyBtn.setFocusable(false);
    copyBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    copyBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar2.add(copyBtn);

    copyAllBtn.setAction(new reflectiondemo.CommandsManager("all",this));
    copyAllBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/copy-icon3.png"))); // NOI18N
    copyAllBtn.setText("Copy All");
    copyAllBtn.setEnabled(false);
    copyAllBtn.setFocusable(false);
    copyAllBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    copyAllBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar2.add(copyAllBtn);

    clearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/Copy-icon1.png"))); // NOI18N
    clearBtn.setText("Clear Entries");
    clearBtn.setEnabled(false);
    clearBtn.setFocusable(false);
    clearBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    clearBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    clearBtn.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            clearBtnActionPerformed(evt);
        }
    });
    jToolBar3.add(clearBtn);

    jButton3.setAction(new reflectiondemo.CommandsManager("font+",this)
    );
    jButton3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
    jButton3.setForeground(new java.awt.Color(0, 68, 91));
    jButton3.setText("A+");
    jButton3.setFocusable(false);
    jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar4.add(jButton3);

    jButton4.setAction(new reflectiondemo.CommandsManager("font-",this)
    );
    jButton4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
    jButton4.setForeground(new java.awt.Color(0, 68, 91));
    jButton4.setText("A-");
    jButton4.setFocusable(false);
    jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton4.setMargin(new java.awt.Insets(2, 2, 2, 2));
    jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jToolBar4.add(jButton4);

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jToolBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jToolBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    jMenu1.setText("File");

    jMenuItem1.setText("Load Jar Entities");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem1ActionPerformed(evt);
        }
    });
    jMenu1.add(jMenuItem1);

    jMenu6.setText("Save List As");

    jMenuItem9.setText("Text File");
    jMenu6.add(jMenuItem9);

    jMenuItem10.setText("XML File");
    jMenu6.add(jMenuItem10);

    jMenu1.add(jMenu6);

    printmenu.setAction(new reflectiondemo.CommandsManager("print", this)
    );
    printmenu.setText("Print");
    printmenu.setEnabled(false);
    jMenu1.add(printmenu);

    jMenuItem2.setAction(new CommandsManager("winClose",this)
    );
    jMenu1.add(jMenuItem2);

    jMenuBar1.add(jMenu1);

    jMenu2.setText("Edit");

    copyMenu.setAction(new reflectiondemo.CommandsManager("copy",this)
    );
    copyMenu.setText("Copy Selected Entry");
    copyMenu.setEnabled(false);
    copyMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            copyMenuActionPerformed(evt);
        }
    });
    jMenu2.add(copyMenu);

    copyAllMenu.setAction(new reflectiondemo.CommandsManager("all",this)
    );
    copyAllMenu.setText("Copy All  Entries");
    copyAllMenu.setEnabled(false);
    jMenu2.add(copyAllMenu);
    jMenu2.add(jSeparator1);

    clearMenu.setText("Clear  Entities");
    clearMenu.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            clearMenuActionPerformed(evt);
        }
    });
    jMenu2.add(clearMenu);

    jMenuBar1.add(jMenu2);

    jMenu5.setText("Format");

    jMenuItem3.setAction(new CommandsManager("showFormat",this)
    );
    jMenuItem3.setText("Format");
    jMenu5.add(jMenuItem3);

    jMenuBar1.add(jMenu5);

    jMenu3.setText("Settings");

    jMenuItem6.setAction(new reflectiondemo.CommandsManager("fontDialog",this)
    );
    jMenuItem6.setText("Change Font");
    jMenu3.add(jMenuItem6);

    loadOption.setSelected(true);
    loadOption.setText("Load Stored Entries");
    jMenu3.add(loadOption);

    storeOption.setSelected(true);
    storeOption.setText("Store Loaded  Entries");
    jMenu3.add(storeOption);

    jMenuItem8.setAction(new CommandsManager("colorDialog",this));
    jMenuItem8.setText("Change Colors");
    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jMenuItem8ActionPerformed(evt);
        }
    });
    jMenu3.add(jMenuItem8);

    jMenuBar1.add(jMenu3);

    jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/reflectiondemo/img/help.png"))); // NOI18N

    helpMenu.setAction(new CommandsManager("showHelp",this)
    );
    helpMenu.setText("Help Contents");
    jMenu4.add(helpMenu);

    jMenuItem4.setText("About This Program");
    jMenu4.add(jMenuItem4);

    jMenuItem5.setText("Keyboard Shortcuts");
    jMenu4.add(jMenuItem5);

    jMenuBar1.add(jMenu4);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(2, 2, 2)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(5, 5, 5)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

       String member  =  (String) jComboBox1.getSelectedItem();
       String className =  (String) jComboBox2.getSelectedItem();
      
        printEntries(className, member);
       
     
        
    }//GEN-LAST:event_jButton1ActionPerformed

private void printEntries(String className, String member  ){

 if(className == null) className  =  comboEditorField.getText();
      
           try {
               Anaylizer.analyzeClass(className, listModel, member, viewArea);
               total.setText("Total "+member+": "
                       +listModel.getSize());
            
           } catch (ClassNotFoundException ex) {
               
               JPanel   errorHandlerPane  =  new JPanel(new BorderLayout());
                   errorHandlerPane.setPreferredSize(new Dimension(600,400));
                  errorHandlerPane.setBorder(new CompoundBorder(new EmptyBorder(1,1,1,1),
                       new CompoundBorder(new MatteBorder(new Insets(10, 10, 10, 10),
                               Color.DARK_GRAY),
                               new BevelBorder(BevelBorder.RAISED)))); 
                   
               JList<String>  errorLog =  new JList<>();
               errorLog.setFont(total.getFont());
               total.setForeground(Color.black);
               errorLog.setBorder(new EmptyBorder(5,5,5,5));
               errorLog.setForeground(Color.red);
               errorLog.setModel(getErrorLines(errorLog, ex));
               
              
               JScrollPane  sp  = new JScrollPane(errorLog);
               errorHandlerPane.add(sp);
               total.setText("The class ["+className+"] is not found.");
               total.setForeground(Color.red);
               JOptionPane.showMessageDialog(this, errorHandlerPane,"Class Name Error",JOptionPane.PLAIN_MESSAGE);

           }
       

}    
    
    
   protected DefaultListModel<String> getErrorLines(JList<String> errorList,ClassNotFoundException x){
       
  DefaultListModel<String>  listModel =  new DefaultListModel<>();
       for (StackTraceElement object : x.getStackTrace()) 
           listModel.addElement(object.toString());
      
        return listModel;
   } 
  
 protected boolean addSelectionToSysClapBoard(String selection){
 
     StringSelection    data  =  new StringSelection(selection);
     Clipboard   clipboard    =  getToolkit().getSystemClipboard();
     clipboard .setContents(data, data);
     boolean added  =  false;
     
     try {
        String x  =  (String) clipboard.getData(DataFlavor.stringFlavor);
        if(x != null)  added =  true;
     } catch (UnsupportedFlavorException | IOException e) {
     }
 return added;
 
 }
         
 private void activateColorCheckBox(){

colorDialogHandler.changeSelectedColorBySlide(
oddPane, oddchk, jSlider2,jSlider3,jSlider4);

colorDialogHandler.changeSelectedColorBySlide(
oddgfPane,oddfg , jSlider2,jSlider3,jSlider4);

colorDialogHandler.changeSelectedColorBySlide(
evenPane, evenchk,jSlider2,jSlider3,jSlider4);
colorDialogHandler.changeSelectedColorBySlide(
evengfPane, evenfg, jSlider2,jSlider3,jSlider4);
colorDialogHandler.changeSelectedColorBySlide(bgPane, bgchk, jSlider2,jSlider3,jSlider4);
colorDialogHandler.changeSelectedColorBySlide(bgselectPane, selectchk, jSlider2,jSlider3,jSlider4);
colorDialogHandler.changeSelectedColorBySlide(fgSelectPane, selectfg, jSlider2,jSlider3,jSlider4);

 }
 
public void applyCurrentColorsToPanel (){

colorDialogHandler.applyCurrentColorsToPanel(colorMap,oddPane,oddgfPane,evenPane,evengfPane,bgPane,
bgselectPane,
fgSelectPane);

}
 
protected  void applySelectedColors(){

 colorDialogHandler.getSelectedColors(
colorMap,oddPane,oddgfPane,evenPane,evengfPane,bgPane,
bgselectPane,
fgSelectPane);

//even,even-fg,odd,odd-fg,bg-s,fg-s, bg 
Color[]  colors =  new Color[colorMap.size()];
colors[0] =  colorMap.get("even");
colors[1] =  colorMap.get("even-fg");
colors[2] =  colorMap.get("odd");
colors[3] =  colorMap.get("odd-fg");
colors[4] =  colorMap.get("bg-s");
colors[5] =  colorMap.get("fg-s");
colors[6] =  colorMap.get("bg");


colorMap.forEach((t, u) -> {
    String  color  =  u.getRed()+" "+u.getGreen()+" "+u.getBlue();
    prefNode.put(t, color);
});


jList1.setCellRenderer(new MyCellRenderer(this, colors));
jList1.repaint();



}
protected  void registerPopupMenuTo(Component invoker, JPopupMenu popupMenu){
  
  invoker.addMouseListener(new MouseAdapter() {
     @Override
     public void mousePressed(MouseEvent e) {
         checkTrigeredEvent(e);
     }

     @Override
     public void mouseReleased(MouseEvent e) {
         checkTrigeredEvent(e);
     }
     
     private void checkTrigeredEvent(MouseEvent e){
     if(e.isPopupTrigger()){
       popupMenu.show(invoker, e.getX(), e.getY());
     }
     
    if(invoker instanceof  JList){
        JList list  = (JList) invoker;
        DefaultListModel  m  =  (DefaultListModel) list.getModel();
        JMenuItem  mi  =  (JMenuItem) popupMenu.getComponent(1);
        JMenuItem  mi2  =  (JMenuItem) popupMenu.getComponent(0);
        if(m.isEmpty()){
          mi.setEnabled(false);
          mi2.setEnabled(false);
        }else {
            mi.setEnabled(true);
            mi2.setEnabled(true);
        }
    } 
     
     }
     
});
 
 
  
  
  }  
  
private Dimension resizeWindowToScreen(){

Dimension  scnSize  =  getToolkit().getScreenSize();
 
 //we want to make the size of our MyWindow 75% wide and 80% tall on the screen.
scnSize.width = (int) (scnSize.getWidth() / 100 * 75);
scnSize.height = (int) (scnSize.getHeight()/ 100 * 80);

 return scnSize;

}

   
    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
     
        
            
        switch(evt.getNewState()){
            case JFrame.MAXIMIZED_BOTH:
                viewArea.setPreferredSize(new Dimension(getWidth()-150, 140));
                break;
            case  0:
                viewArea.setPreferredSize(new Dimension(getWidth()-150, 140));
                break;
            
        
        }
        
        
    }//GEN-LAST:event_formWindowStateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
   
        try {
            loadFile();
        } catch (IOException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

protected  void loadFile() throws IOException{
      
    JFileChooser  fc  =  new JFileChooser();
    fc.setCurrentDirectory(new File(browseDir));
    
    int v =  fc.showOpenDialog(this);
    if(v  == JFileChooser.APPROVE_OPTION){
        File  f  =  fc.getSelectedFile();
        browseDir= f.getAbsolutePath();
        jarFiles.add(f.getAbsoluteFile().toString());   
        entries  = Anaylizer.collectJarEnities(f.getAbsoluteFile().toString());
        updateComboBox(entries);
       
    }
 }   
  String entries[];  
private void readJarFile(String f){
   try {
            entries  = Anaylizer.collectJarEnities(f);
           
        } catch (IOException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
 
}


    private void clearMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuActionPerformed
        clearEntries();
       loadButton.setEnabled(true);
    }//GEN-LAST:event_clearMenuActionPerformed

 protected  void clearEntries(){
   try {
            prefNode.clear();
        } catch (BackingStoreException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
   ( (DefaultComboBoxModel) jComboBox2.getModel()).removeAllElements();
   clearMenu.setEnabled(false);
   comboEditorField.setText(null);
  
  }  
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        
         viewArea.setPreferredSize(new Dimension(getWidth()-150, 140));
        
    }//GEN-LAST:event_formComponentResized
 
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
         copyMenu.setEnabled(true);
         copyBtn.setEnabled(true);
   
    }//GEN-LAST:event_jList1ValueChanged

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        clearEntries();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            loadFile();
        } catch (IOException ex) {
            Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void copyMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuActionPerformed
    }//GEN-LAST:event_copyMenuActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        onWindowClose();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed


if(entries != null)
    updateComboBox(entries);
 
    }//GEN-LAST:event_loadButtonActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void colorDialogWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_colorDialogWindowClosing
  cancelColorSelections();
        
    }//GEN-LAST:event_colorDialogWindowClosing
protected  void updateComboBox(ListCellRenderer<String> r, JComboBox<String>  c, String...data){
DefaultComboBoxModel<String> m  =  new DefaultComboBoxModel<>(data);
//c.setEditor(new ColorComboEditor());
if(r  != null)
  c.setRenderer(r);

c.setModel(m);
c.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxx");



}
private  void updateComboBox(String[] e){

String x  =  comboEditorField.getText();
    ArrayDeque<String>  q  =  new ArrayDeque<>(Arrays.asList(e));
    q.addFirst(x);
   // Set<String>  set    = new HashSet<>(q);
    TreeSet<String>  ts =  new TreeSet<>(q);
    e  =  ts.toArray(new String[0]);
    
DefaultComboBoxModel<String> m  =  new DefaultComboBoxModel<>(e);
jComboBox2.setModel(m);
jComboBox2.setSelectedItem(x);
}
protected String getListEntries(){
StringBuilder sb  =  new StringBuilder();
 for(int i = 0; i < listModel.getSize(); ++i){
  sb.append(listModel.getElementAt(i)).append("\n");
   }
return sb.toString();
}

protected void print(){
  PrinterJob   printerJob  =  PrinterJob.getPrinterJob();
  
  printerJob.setPrintable(new PrintJob(jList1, 20, 200,jList1.getFont()));
  PageFormat    pageFormat  =  printerJob.pageDialog(printerJob.defaultPage());
  pageFormat.setOrientation(PageFormat.LANDSCAPE);
  boolean printed;
  printed = printerJob.printDialog();
    
 if(printed)
     try {
         printerJob.print();
         System.out.println( "job printed: "+ printed );
  } catch (PrinterException ex) {
      Logger.getLogger(MyWindow.class.getName()).log(Level.SEVERE, null, ex);
  }

}


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
   
        /* Set the Nimbus look and feel */


//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            
//             //javax.swing.UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");    
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
            
UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                new MyWindow().setVisible(true);
            }
        });
        
      
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgPane;
    private javax.swing.JCheckBox bgchk;
    private javax.swing.JPanel bgselectPane;
    protected javax.swing.JTextField blue;
    private javax.swing.ButtonGroup buttonGroup1;
    protected javax.swing.JButton cancelFontButton;
    private javax.swing.JButton clearBtn;
    private javax.swing.JMenuItem clearMenu;
    protected javax.swing.JDialog colorDialog;
    private javax.swing.JButton copyAllBtn;
    private javax.swing.JMenuItem copyAllMenu;
    private javax.swing.JButton copyBtn;
    private javax.swing.JMenuItem copyMenu;
    protected javax.swing.JButton edtfBtn;
    private javax.swing.JPanel evenPane;
    private javax.swing.JCheckBox evenchk;
    private javax.swing.JCheckBox evenfg;
    private javax.swing.JPanel evengfPane;
    protected javax.swing.JTextField familyField;
    private javax.swing.JComboBox<String> favoriteBox;
    private javax.swing.JPanel fgSelectPane;
    protected javax.swing.JDialog fontDialog;
    protected javax.swing.JList<String> fontList;
    protected javax.swing.JButton fontOkButton;
    protected javax.swing.JLabel fontPreview;
    protected javax.swing.JList<String> fontSizeList;
    protected javax.swing.JList<String> fontStyleList;
    protected javax.swing.JTextField green;
    private javax.swing.JMenuItem helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    protected javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    protected javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    protected javax.swing.JSlider jSlider2;
    protected javax.swing.JSlider jSlider3;
    protected javax.swing.JSlider jSlider4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JToolBar jToolBar4;
    private javax.swing.JButton loadButton;
    private javax.swing.JCheckBoxMenuItem loadOption;
    private javax.swing.JLabel loading;
    private javax.swing.JPanel oddPane;
    private javax.swing.JCheckBox oddchk;
    private javax.swing.JCheckBox oddfg;
    private javax.swing.JPanel oddgfPane;
    private javax.swing.JComboBox<String> presetBox;
    private javax.swing.JButton printBtn;
    private javax.swing.JMenuItem printmenu;
    protected javax.swing.JTextField redField;
    private javax.swing.JCheckBox selectchk;
    private javax.swing.JCheckBox selectfg;
    protected javax.swing.JTextField sizeField;
    private javax.swing.JCheckBoxMenuItem storeOption;
    protected javax.swing.JTextField styleField;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}
