// SimpleEditor.java
// An example showing several DefaultEditorKit features. This class is designed
// to be easily extended for additional functionality.
//
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.JOptionPane;

public class SimpleEdit extends JFrame {

  private Action openAction = new OpenAction();
  private Action saveAction = new SaveAction();
  private Action InfoAction = new InfoAction();

  private JTextComponent textComp;
  private Hashtable actionHash = new Hashtable();

  public static void main(String[] args) {
    SimpleEdit editor = new SimpleEdit();
    editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    editor.setVisible(true);
  }

  // Create an editor.
  public SimpleEdit() {
    super("SimpleEdit");
    textComp = createTextComponent();
    makeActionsPretty();

    Container content = getContentPane();
    content.add(textComp, BorderLayout.CENTER);
    content.add(createToolBar(), BorderLayout.NORTH);
    setJMenuBar(createMenuBar());
    setSize(320, 240);
  }

  // Create the JTextComponent subclass.
  protected JTextComponent createTextComponent() {
    JTextArea ta = new JTextArea();
    ta.setLineWrap(true);
    return ta;
  }

  // Add icons and friendly names to actions we care about.
  protected void makeActionsPretty() {
    Action a;
    a = textComp.getActionMap().get(DefaultEditorKit.cutAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("icons/cut.gif"));
    a.putValue(Action.NAME, "Cut");

    a = textComp.getActionMap().get(DefaultEditorKit.copyAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("icons/copy.gif"));
    a.putValue(Action.NAME, "Copy");

    a = textComp.getActionMap().get(DefaultEditorKit.pasteAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("icons/paste.gif"));
    a.putValue(Action.NAME, "Paste");

    a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);
    a.putValue(Action.NAME, "Select All");
  }

  // Create a simple JToolBar with some buttons.
  protected JToolBar createToolBar() {
    JToolBar bar = new JToolBar();

    // Add simple actions for opening & saving.
    bar.add(getInfoAction()).setText("");
    bar.add(getOpenAction()).setText("");
    bar.add(getSaveAction()).setText("");
   
    bar.addSeparator();

    // Add cut/copy/paste buttons.
    bar.add(textComp.getActionMap().get(DefaultEditorKit.cutAction)).setText("");
    bar.add(textComp.getActionMap().get(
              DefaultEditorKit.copyAction)).setText("");
    bar.add(textComp.getActionMap().get(
              DefaultEditorKit.pasteAction)).setText("");
    return bar;
  }

  // Create a JMenuBar with file & edit menus.
  protected JMenuBar createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu simpleedit = new JMenu("SimpleEdit");
    JMenu file = new JMenu("Datei");
    JMenu edit = new JMenu("Bearbeitet");
    menubar.add(simpleedit);
    menubar.add(file);
    menubar.add(edit);

    simpleedit.add(getInfoAction());
    simpleedit.add(new ExitAction());
    file.add(getOpenAction());
    file.add(getSaveAction());
    edit.add(textComp.getActionMap().get(DefaultEditorKit.cutAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.copyAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction));
    edit.add(textComp.getActionMap().get(DefaultEditorKit.selectAllAction));
    return menubar;
  }
  protected JMenuBar applicationBar() {
	  JMenuBar infobar = new JMenuBar();
	  JMenu SimpleEdit = new JMenu("Info");
	return infobar;
	  }

  // Subclass can override to use a different open action.
  protected Action getOpenAction() { return openAction; }

  // Subclass can override to use a different save action.
  protected Action getSaveAction() { return saveAction; }
  
  protected Action getInfoAction() { return InfoAction; }
  protected JTextComponent getTextComponent() { return textComp; }

  // ********** ACTION INNER CLASSES ********** //

  
  

  public class InfoAction extends AbstractAction {
	    public InfoAction() {
	    	super("Info", new ImageIcon("info.png")); 
	    }
	    public void actionPerformed(ActionEvent e) {
	    	JOptionPane.showMessageDialog(textComp, "SimpleEdit 0.0.1 (C) Dierk-Bent Piening");
	        
	    }
	}

  
  
  
  
  
  // A very simple exit action
  public class ExitAction extends AbstractAction {
    public ExitAction() { 
    	super("Exit", new ImageIcon("exit.png")); 
    	
    	}
    public void actionPerformed(ActionEvent ev) { System.exit(0); }
  }

  // An action that opens an existing file
  class OpenAction extends AbstractAction {
    public OpenAction() { 
      super("Open", new ImageIcon("open.png")); 
    }  
    

    // Query user for a filename and attempt to open and read the file into the  // text component.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showOpenDialog(SimpleEdit.this) !=
          JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileReader reader = null;
      try {
        reader = new FileReader(file);
        textComp.read(reader, null);
      }
      catch (IOException ex) {
        JOptionPane.showMessageDialog(SimpleEdit.this,
        "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
      finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException x) {}
        }
      }
    }
  }

  // An action that saves the document to a file
  class SaveAction extends AbstractAction {
    public SaveAction() {
      super("Save", new ImageIcon("save.png"));
    }

    // Query user for a filename and attempt to open and write the text
    // component’s content to the file.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showSaveDialog(SimpleEdit.this) !=
          JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileWriter writer = null;
      try {
        writer = new FileWriter(file);
        textComp.write(writer);
      }
      catch (IOException ex) {
        JOptionPane.showMessageDialog(SimpleEdit.this,
        "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
      }
      finally {
        if (writer != null) {
          try {
            writer.close();
          } catch (IOException x) {}
        }
      }
    }
  }


  }

