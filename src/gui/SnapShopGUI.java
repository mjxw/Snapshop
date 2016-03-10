/*
 * TCSS 305 - Winter 2016
 * Assignment 3 - Easy Street
 */

package gui;

import filters.EdgeDetectFilter;
import filters.EdgeHighlightFilter;
import filters.FlipHorizontalFilter;
import filters.FlipVerticalFilter;
import filters.GrayscaleFilter;
import filters.SharpenFilter;
import filters.SoftenFilter;
import image.PixelImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the GUI for SnapShop. Images can be opened and saved. 
 * A client can click on buttons to make modifications via filters to an 
 * opened image.
 * 
 * @author Matthew Wu
 * @version 1.5
 */
public class SnapShopGUI extends JFrame {
    
    /**
     * The number of rows for the upper portion of a gridlayout.
     */
    public static final int UPPER_GRID_ROW_NUM = 7; 
    
    /**
     * The number of rows for the lower portion of a gridlayout.
     */
    public static final int LOWER_GRID_ROW_NUM = 3; 
    
    /**
     * A number reference for each button. 
     */
    public static final int ZERO = 0;
    
    /**
     * A number reference for each button. 
     */
    public static final int ONE = 1;
    
    /**
     * A number reference for each button. 
     */
    public static final int TWO = 2;
    
    /**
     * A number reference for each button. 
     */
    public static final int THREE = 3;
    
    /**
     * A number reference for each button. 
     */
    public static final int FOUR = 4;
    
    /**
     * A number reference for each button. 
     */
    public static final int FIVE = 5;
    
    /**
     * A number reference for each button. 
     */
    public static final int SIX = 6;
    
    /**
     * A number reference for each button. 
     */
    public static final int SEVEN = 7;
    
    /**
     * A number reference for each button. 
     */
    public static final int EIGHT = 8;
    
    /**
     * A number reference for each button. 
     */
    public static final int NINE = 9;
    
    /**
     * A number reference for each button. 
     */
    public static final int TEN = 10;
   
    /**
     * A generated serial version UID for object Serialization.
     */
    private static final long serialVersionUID = -2144739668936849562L;
    
    /**
     * An edge detect filter object.
     */
    private final EdgeDetectFilter myEdgeDetectFilter = new EdgeDetectFilter(); 
    
    /**
     * An edge highlight filter object.
     */
    private final EdgeHighlightFilter myEdgeHighlightFilter = new EdgeHighlightFilter();
    
    /**
     * A flip horizontal filter object.
     */
    private final FlipHorizontalFilter myFlipHorizontalFilter = new FlipHorizontalFilter();
    
    /**
     * A flip vertical filter object.
     */
    private final FlipVerticalFilter myFlipVerticalFilter = new FlipVerticalFilter();
    
    /**
     * A grayscale filter object.
     */
    private final GrayscaleFilter myGrayscaleFilter = new GrayscaleFilter();
    
    /**
     * A sharpen filter object.
     */
    private final SharpenFilter mySharpenFilter = new SharpenFilter();
    
    /**
     * A soften filter object.
     */
    private final SoftenFilter mySoftenFilter = new SoftenFilter();
    
    /**
     * A JFrame for later use.
     */
    private JFrame myFrame;
    
    /**
     * An array of JButtons for later use.
     */
    private JButton[] myButtonsCollection;
    
    /**
     * Storing the file a user selected for global access.
     */
    private File mySelectedFile;
    
    /**
     * A JFileChooser for later use.
     */
    private JFileChooser myFileChooser;
    
    /**
     * A boolean flag for whether or not buttons should be on or off.
     */
    private boolean myOnOffSwitch;
    
    /**
     * A JLabel for later use.
     */
    private JLabel myLabel;
    
    /**
     * Storing the image a user selects.
     */
    private PixelImage myImage;


    
    /**
     * This method initializes field variables and makes calls 
     * to set up the panels and the frames for the snapshop.
     */
    public void start() {   
        myFrame = new JFrame("TCSS 305 SnapShop");
        myButtonsCollection = new JButton[TEN];
        myFileChooser = new JFileChooser();
        myLabel = new JLabel();
        myOnOffSwitch = false;
        myImage = null;
        setupPanels(); // setup panels first
        setupFrame(); // launch frame after panels are set
    }
    
    /**
     * This method sets up the frame so that it is centered, visible, 
     * exits on close, and packed to size.
     */
    private void setupFrame() {
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * This method sets up three primary panels.
     * An overall panel that contains two other panels:
     * the panel for buttons and the panel for the image.
     */
    private void setupPanels() {
        final JPanel overallPanel = new JPanel();
        overallPanel.setLayout(new BorderLayout());
        myFrame.add(overallPanel, BorderLayout.CENTER);
        
        // Create panel for buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        overallPanel.add(buttonPanel, BorderLayout.WEST);
        generateButtonsCollection();
        addButtons(buttonPanel);
        
        // Create panel for image
        final JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        overallPanel.add(imagePanel, BorderLayout.CENTER);
        imagePanel.add(myLabel, BorderLayout.NORTH);
    }
    
    /**
     * This method creates buttons and stores them in an array.
     */
    private void generateButtonsCollection() {
        myButtonsCollection[ZERO] = createEdgeDetectButton();
        myButtonsCollection[ONE] = createEdgeHighlightButton();
        myButtonsCollection[TWO] = createFlipHorizontalButton();
        myButtonsCollection[THREE] = createFlipVerticalButton();
        myButtonsCollection[FOUR] = createGrayscaleButton();
        myButtonsCollection[FIVE] = createSharpenButton();
        myButtonsCollection[SIX] = createSoftenButton();
        myButtonsCollection[SEVEN] = createOpenButton();
        myButtonsCollection[EIGHT] = createSaveButton();
        myButtonsCollection[NINE] = createCloseImageButton(); 
    }
    
    /**
     * This method is for toggling the buttons on/off.
     */
    private void toggleButtons() {
        for (int i = 0; i <= NINE; i++) {
            if (myOnOffSwitch) {
                myButtonsCollection[i].setEnabled(true);
            } else {
                if (i != SEVEN) {
                    myButtonsCollection[i].setEnabled(false);
                }
            }
        }
    }
    
    /**
     * This method adds all buttons to their respectful panels.
     * 
     * @param theButtonPanel is the panel for buttons.
     */
    private void addButtons(final JPanel theButtonPanel) {
        final JPanel upperButtonGridPanel = new JPanel(new GridLayout(UPPER_GRID_ROW_NUM, 1));
        final JPanel lowerButtonGridPanel = new JPanel(new GridLayout(LOWER_GRID_ROW_NUM, 1));
        theButtonPanel.add(upperButtonGridPanel, BorderLayout.NORTH);
        theButtonPanel.add(lowerButtonGridPanel, BorderLayout.SOUTH);
        
        for (int i = 0; i <= NINE; i++) {
            if (i <= SIX) {
                upperButtonGridPanel.add(myButtonsCollection[i]);
            } else {
                lowerButtonGridPanel.add(myButtonsCollection[i]);
            }
        }
    }
    
    /**
     * This method creates an edge detect button with the appropriate listeners to trigger 
     * the desired action. 
     * 
     * @return Returns the button.
     */
    private JButton createEdgeDetectButton() {
        final JButton edgeDetectButton = new JButton(myEdgeDetectFilter.getDescription());
        edgeDetectButton.setEnabled(false);
        edgeDetectButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myEdgeDetectFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        });
        return edgeDetectButton;
    }
    
    /**
     * This method creates an edge highlight button with the appropriate listeners to trigger 
     * the desired action. This image highlights the edges of the image.
     * 
     * @return Returns the button.
     */
    private JButton createEdgeHighlightButton() {
        final JButton edgeHighlightButton = 
                        new JButton(myEdgeHighlightFilter.getDescription());
        edgeHighlightButton.setEnabled(false);
        edgeHighlightButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myEdgeHighlightFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        });
        return edgeHighlightButton;
    }
    
    /**
     * This method creates a flip horizontal button with the appropriate listeners to trigger
     * the desired action. This button flips the image horizontally.
     * 
     * @return Returns the button.
     */
    private JButton createFlipHorizontalButton() {
        final JButton flipHorizontalButton = 
                        new JButton(myFlipHorizontalFilter.getDescription());
        flipHorizontalButton.setEnabled(false);
        flipHorizontalButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFlipHorizontalFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        });
        return flipHorizontalButton;
    }
    
    /**
     * This method creates a flip vertical button with the appropriate listeners to trigger
     * the desired action. This button flips the image vertically.
     * 
     * @return Returns the button.
     */
    private JButton createFlipVerticalButton() {
        final JButton flipVerticalButton = new JButton(myFlipVerticalFilter.getDescription());
        flipVerticalButton.setEnabled(false);
        flipVerticalButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFlipVerticalFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        });
        return flipVerticalButton;
    }
    
    /**
     * This method creates a gray scale button with the appropriate listeners to trigger 
     * the desired action. This button does a grayscale for the image.
     * 
     * @return Returns the button.
     */
    private JButton createGrayscaleButton() {
        final JButton grayscaleButton = new JButton(myGrayscaleFilter.getDescription());
        grayscaleButton.setEnabled(false);
        grayscaleButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myGrayscaleFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        }); 
        return grayscaleButton;
    }
    
    /**
     * This method creates a sharpen button with the appropriate listeners to trigger 
     * the desired action. This button sharpens the image.
     * 
     * @return Returns the button.
     */
    private JButton createSharpenButton() {
        final JButton sharpenButton = new JButton(mySharpenFilter.getDescription());
        sharpenButton.setEnabled(false);
        sharpenButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                mySharpenFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        });
        
        return sharpenButton;
    }
    
    /**
     * This method creates a soften button with the appropriate listeners to trigger 
     * the desired action. This button softens the image.
     * 
     * @return Returns the button.
     */
    private JButton createSoftenButton() {
        final JButton softenButton = new JButton(mySoftenFilter.getDescription());
        softenButton.setEnabled(false);
        softenButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                mySoftenFilter.filter(myImage);
                myLabel.setIcon(new ImageIcon(myImage));
            }
        }); 
        return softenButton;
    }
    
    /**
     * This method creates an open button with the appropriate listeners to trigger 
     * the desired action. This button is used to open files.
     * 
     * @return Returns the button.
     */
    private JButton createOpenButton() {
        final JButton openButton = new JButton("Open...");  
        openButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                final int result = myFileChooser.showOpenDialog(getParent());
                if (result == JFileChooser.APPROVE_OPTION) {
                    mySelectedFile = myFileChooser.getSelectedFile();
                    loadImage(myLabel, mySelectedFile);
                    myOnOffSwitch = true;
                    toggleButtons();
                    setupFrame();
                    final Dimension currentSize = myFrame.getBounds().getSize();
                    myFrame.setMinimumSize(currentSize);
                }
            }
        });
        return openButton;
    }
    
    /**
     * This method loads a selected image file.
     * 
     * @param theLabel is the label that contains the icon to be set.
     * @param theImageFile is the image file for loading.
     */
    private void loadImage(final JLabel theLabel, final File theImageFile) {
        try {
            myImage = PixelImage.load(theImageFile);
            toggleButtons();
        } catch (final IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(myFrame, 
                                          "The selcted file did not contain an image!", 
                                          "Error.", JOptionPane.ERROR_MESSAGE);
        }        
        theLabel.setIcon(new ImageIcon(myImage));
    }
    
    /**
     * This method creates a save button with the appropriate listeners to trigger 
     * the desired action. This button is used for saving an image. 
     * 
     * @return Returns the button.
     */
    private JButton createSaveButton() {
        final JButton saveButton = new JButton("Save as...");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
           
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                final int result = myFileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    final File selectedFile = myFileChooser.getSelectedFile();
                    saveImage(selectedFile);
                    
                }
            }
        }); 
     
        return saveButton;
    }
    
    /**
     * This method saves an image file.
     * 
     * @param theImageFile is the image file that is to be saved. 
     */
    private void saveImage(final File theImageFile) {
        try {
            myImage.save(theImageFile);
        } catch (final IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(myFrame, 
                                          "The selcted file is not an image!", 
                                          "Error!", JOptionPane.ERROR_MESSAGE);
        }        
    }
    
    /**
     * This method creates a close button with the appropriate listeners to trigger 
     * the desired action. This button closes the image.
     * 
     * @return Returns the button.
     */
    private JButton createCloseImageButton() {
        final JButton closeImageButton = new JButton("Close Image");
        closeImageButton.setEnabled(false);
        closeImageButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myLabel.setIcon(null);
                myOnOffSwitch = false;
                toggleButtons();
                setupFrame();
            }
        });
        return closeImageButton;
    }

}