package com.company;

import java.awt.*;


public class Main {
    public static void main(String[] args)
    {
            EventQueue.invokeLater(() ->{
                MyComponent frame = new MyComponent();
                frame.setTitle("Euler");
                frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        }


    }

