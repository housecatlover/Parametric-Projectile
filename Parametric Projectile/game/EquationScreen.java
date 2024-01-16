package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class EquationScreen extends JFrame implements ActionListener {
  private static final int PREF_WIDTH = 400;
  private static final int PREF_HEIGHT = 50;
  private final ParametricProjectile game;
  private final EquationEditor editor;
  private final ArrayList<EquationRow> rows;
  private final JLabel current;

  public EquationScreen(EquationEditor editor, ParametricProjectile game) {
    this.game = game;
    this.rows = new ArrayList<>();
    this.editor = editor;


    this.setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT * 2));
    this.setLayout(new FlowLayout());

    JButton exit = new JButton("Return to game");
    exit.addActionListener(this);
    this.add(exit);

    current = new JLabel();
    this.add(current);
    update();

    this.pack();
  }


  public void resetTypes() {
    rows.clear();
  }

  public void addType(EquationEditor.Type type) {
    EquationRow temp = new EquationRow(type, editor, this);
    rows.add(temp);
    this.add(temp);
    this.setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT * (2 + rows.size())));
    this.pack();
  }

  public void update() {
    EquationEditor.Type selected = editor.getType();
    String output = selected == null ? "None" : selected.toString();
    current.setText("Selected: " + output);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    game.run();
  }
}