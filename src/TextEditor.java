import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TextEditor {

	private JFrame frame;
	private JTextArea textArea;
	private File openFile;
	private final String TITLE = "Text Editor";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					TextEditor window = new TextEditor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TextEditor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle(TITLE);
		frame.setBounds(100, 100, 599, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Arial", Font.PLAIN, 13));
		frame.getContentPane().add(textArea, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				open();
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				create();
			}
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		mnFile.add(mntmClose);
		
	}
	
	private void open() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Select a Text File to Open");
			chooser.showOpenDialog(null);
			
			openFile = chooser.getSelectedFile();
			if(openFile != null && !openFile.exists()){
				JOptionPane.showMessageDialog(null, "Failed to Open, File does not exist","Error", JOptionPane.ERROR_MESSAGE);
				openFile = null;
				return;
			}
			
			Scanner reader = new Scanner(openFile);
			String contents = "";
			while(reader.hasNextLine()){
				contents += reader.nextLine() + "\n";
			}
			reader.close();
			textArea.setText(contents);
			
			frame.setTitle(TITLE + "- " + openFile.getName());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void create() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Save New File");
			chooser.showSaveDialog(null);
				
			openFile = chooser.getSelectedFile();
			save();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void save() {
		try {
			if(openFile == null){
				JOptionPane.showMessageDialog(null, "Failed to Save File, No File was Selected", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String contents = textArea.getText();
			Formatter form = new Formatter(openFile);
			form.format("%s", contents);
			form.close();
			
			frame.setTitle(TITLE + "- " + openFile.getName());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void close() {
		if(openFile == null){
			JOptionPane.showMessageDialog(null, "Failed to Close File, No File was Selected", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			int input = JOptionPane.showConfirmDialog(null, "Do you want to save before closing?", "Wait", JOptionPane.YES_NO_OPTION);
			
			if(input == JOptionPane.YES_OPTION){
				save();
			}
			textArea.setText("");
			openFile = null;
			frame.setTitle(TITLE);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
