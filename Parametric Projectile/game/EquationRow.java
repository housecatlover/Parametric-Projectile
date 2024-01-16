package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EquationRow extends JPanel implements ActionListener {
  private final JLabel label;
  private final JButton button;
  private final EquationEditor editor;
  private final EquationRow next;
  private final EquationScreen screen;
  private final EquationEditor.Type type;
  private JTextField field;

  EquationRow(EquationEditor.Type type, EquationEditor editor, EquationScreen screen) {
    this.editor = editor;
    this.screen = screen;
    this.type = type;
    label = switch (type) {
      case YEQUALS -> new JLabel("y =");
      case POLAR -> new JLabel("r =");
      case PARAMETRIC -> new JLabel("x = ");
    };

    next = type == EquationEditor.Type.PARAMETRIC ?
            new EquationRow(EquationEditor.Type.YEQUALS, editor, screen) : null;

    button = new JButton("commit");
    button.addActionListener(this);

    field = new JTextField("2x + 1");
    field.setColumns(25);

    this.add(button);
    this.add(label);
    this.add(field);
    if (type == EquationEditor.Type.PARAMETRIC) {
      this.add(next);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    editor.setEquation(type, field.getText());
    screen.update();
  }
}