package com.company;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

class MyComponent extends JFrame
{
    private boolean flag = false;
    private double y;
    private double x;
    private String formula;
    MyComponent()
    {
        JPanel panel = new JPanel();
        JButton button = new JButton("Paint");
        button.setLocation(20, 400);
        JTextField textField = new JTextField(10);
        JTextField textX = new JTextField(2);
        JTextField textY = new JTextField(2);
        JLabel labelFunction = new JLabel("y' = ", JLabel.RIGHT);
        JLabel labelY = new JLabel("y(",JLabel.RIGHT);
        JLabel labelX = new JLabel(") = ", JLabel.RIGHT);

        button.addActionListener(e -> {
            flag = true;
            x = Double.parseDouble(textX.getText().trim());
            y = Double.parseDouble(textY.getText().trim());
            formula = textField.getText();
            repaint();
        });

        panel.add(labelFunction);
        panel.add(textField);
        panel.add(labelY);
        panel.add(textX);
        panel.add(labelX);
        panel.add(textY);
        panel.add(button);
        add(panel, BorderLayout.NORTH);
        add(new DrawComponent());
        pack();
    }

    public class DrawComponent extends JComponent
    {
        private static final int DEFAULT_WIDTH = 600;
        private static final int DEFAULT_HEIGHT = 600;

        private final double h = 0.1;
        private final int n = 10;

        public void paintComponent(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            Font f = new Font("Serif", Font.BOLD, 24);
            g2.setFont(f);

            g2.draw(new Line2D.Double(0, 500, 600, 500));
            g2.draw(new Line2D.Double(80, 20, 100, 0));
            g2.draw(new Line2D.Double(100, 0, 120, 20));
            g2.draw(new Line2D.Double(100, 0, 100, 600));
            g2.draw(new Line2D.Double(580, 480, 600, 500));
            g2.draw(new Line2D.Double(600, 500, 580, 520));

            Ellipse2D circle = new Ellipse2D.Double();
            circle.setFrameFromCenter(100, 500, 105, 505);
            g2.fill(circle);
            g2.draw(circle);
            circle.setFrameFromCenter(100, 300, 105, 305);
            g2.fill(circle);
            g2.draw(circle);
            circle.setFrameFromCenter(300, 500, 305, 505);
            g2.fill(circle);
            g2.draw(circle);

            String Y = "Y";
            String X = "X";
            String O = "O";
            String one = "1";
            g2.drawString(Y, 60, 20);
            g2.drawString(X, 560, 540);
            g2.drawString(O, 80, 520);
            g2.drawString(one, 80, 300);
            g2.drawString(one, 300, 520);

            if (flag)
            {
                ScriptEngineManager manager = new ScriptEngineManager();
                ScriptEngine engine = manager.getEngineByName("js");
                try
                {
                    for (int i = 0; i < n; i++)
                    {
                        Object result = engine.eval(Transfer(formula));
                        double x1 = x;
                        double y1 = y;
                        y += h * (double) result;
                        x += h;
                        g2.setPaint(Color.RED);
                        g2.draw(new Line2D.Double(200 * x1 + 100, 500 - y1 * 200, 200 * x + 100, 500 - 200 * y));
                    }
                    flag = false;
                } catch (ScriptException e)
                {
                    e.printStackTrace();
                }
            }
        }

        String Transfer(String formula)
        {
            char[] a = formula.toCharArray();
            String[] stringa = new String[a.length];
            for (int i = 0; i < a.length; i++)
            {
                switch (a[i])
                {
                    case 'x':
                        stringa[i] = String.valueOf(x);
                        break;
                    case 'y':
                        stringa[i] = String.valueOf(y);
                        break;
                    default:
                        stringa[i] = String.copyValueOf(new char[]{a[i]});
                        break;
                }
            }
            return join(stringa);
        }

        private String join(String[] strings)
        {
            StringBuilder sb = new StringBuilder();
            for (String string : strings)
            {
                sb.append(string);
            }
            return sb.toString();
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }

    }
}
