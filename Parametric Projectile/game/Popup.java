package game;

import java.awt.*;

import javax.swing.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

//Modified from FormattedTextFieldDemo by Oracle
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


public class Popup extends JPanel implements PropertyChangeListener {
    private JFormattedTextField[] fields; //the array of each field's associated Object
    private JLabel[] labels; //object holding information for fields
    private String[] labelNames; //the array of each field's name
    private double[] values; //the array of starting values, either double or int
//    private static NumberFormat murica; //If I allowed commas or needed to localize
    private int size;
    /**
     * Creates a popup window to text input from a user, with any number of fields.
     * Each field only accepts numbers, using the startingValue's type.
     *
     * @param labelNames    the name of each field, in order.
     * @param startingValue the initial value of each field, in order. Subtype is maintained.
     * @throws IllegalArgumentException if the lengths of lists are different
     */
    public Popup(String[] labelNames, double[] startingValue) {
        super(new BorderLayout());
        this.size = labelNames.length;
        if (size != startingValue.length) throw new IllegalArgumentException();
        //Would be setting up formats
        this.labelNames = labelNames;
        this.values = startingValue;

        labels = new JLabel[size];
        fields = new JFormattedTextField[size];
        for (int i = 0; i < size; i++) {
            labels[i] = new JLabel(labelNames[i]);
            fields[i] = new JFormattedTextField(startingValue[i]);
            fields[i].setColumns(10);
            fields[i].addPropertyChangeListener("value", this);
        }

        JPanel labelPane = new JPanel(new GridLayout(0,1));
        JPanel fieldPane = new JPanel(new GridLayout(0,1));
        for (int i = 0; i < size; i++) {
            labelPane.add(labels[i]);
            fieldPane.add(fields[i]);
        }

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }

    /** Called when a field's "value" property changes. */
    public void propertyChange(PropertyChangeEvent e) {
        Object source = e.getSource();
        for (int i = 0; i < size; i++){
            if (source == fields[i]) {
                values[i] = ((Number)fields[i].getValue()).doubleValue();
            }
        }
    }

    public void ask(){
        Popup popup = this;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI(popup);
            }
        });
    }
    public double[] getValues() {
        return values;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI(Popup popup) {
        //Create and set up the window.
        JFrame frame = new JFrame("Equation Values Entry");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //TODO what does this do

        //Add contents to the window.
        frame.add(popup);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}