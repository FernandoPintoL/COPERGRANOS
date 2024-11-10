package com.tecnowebsa.copergranos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import CONSULTAS.Consulta;
import java.io.PrintStream;

public class CORPEGRANOSVIEW extends JFrame {
    private JTextArea textArea;
    private JButton startButton;
    private JButton stopButton;
    private Consulta manage;
    private Thread listenThread;
    private volatile boolean listening;

    public CORPEGRANOSVIEW() {
        /*JFrame frame = new JFrame("COPERGRANOS");
        textArea = new JTextArea(20,50);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/
        setTitle("COPERGRANOS");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Check Mails");
        stopButton = new JButton("Stop Checking");
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        add(buttonPanel, BorderLayout.SOUTH);
        //frame.add(buttonPanel, BorderLayout.SOUTH);

        // Redirige la salida estándar a la JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("INCIANDO EL PROCESO CORPEGRANOS");
                startListening();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopListening();
            }
        });

        try {
            manage = new Consulta();
        } catch (IOException ex) {
            textArea.append(ex.toString() + "\n");
            Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startListening() {
        if (listenThread == null || !listening) {
            listening = true;
            listenThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int cantidadMail = manage.getCantidadMails();
                        while (listening) {
                            int newCantidadEmail = manage.getCantidadMails();
                            if (cantidadMail != newCantidadEmail) {
                                textArea.append("Se encontro un nuevo Mensaje\n".toUpperCase());
                                cantidadMail = newCantidadEmail;
                                manage.newMensaje();
                            } else {
                                textArea.append("...\n");
                            }
                            Thread.sleep(5000);
                        }
                    } catch (IOException | InterruptedException ex) {
                        textArea.append(ex.toString() + "\n");
                        Logger.getLogger(Consulta.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            listenThread.start();
        }
    }

    private void stopListening() {
        listening = false;
        if (listenThread != null) {
            listenThread.interrupt();
        }
    }

    public static void main(String[] args) {
        //new CORPEGRANOSVIEW().setVisible(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CORPEGRANOSVIEW().setVisible(true);
            }
        });
    }
}
