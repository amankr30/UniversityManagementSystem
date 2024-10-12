package university.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.jar.JarEntry;

public class StudentLeaveDetails extends JFrame implements ActionListener {

    Choice choicerollNo;
    JTable table;
    JButton search ,cancel,print;
    StudentLeaveDetails(){
        getContentPane().setBackground(new Color(250,172,206));

        JLabel heading = new JLabel("Search by Employee Id");
        heading.setBounds(20,20,150,20);
        add(heading);

        choicerollNo = new Choice();
        choicerollNo.setBounds(180,20,150,20);
        add(choicerollNo);

        try{
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from student");
            while (resultSet.next()){
                choicerollNo.add(resultSet.getString("rollNo"));
            }
        }catch (Exception E){
            E.printStackTrace();
        }

        table = new JTable();
        try{
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("select * from studentleave");
            table.setModel(DbUtils.resultSetToTableModel(resultSet));
        }catch (Exception e){
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0,100,900,600);
        add(scrollPane);

        search = new JButton("Search");
        search.setBounds(20,70,80,20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120,70,80,20);
        print.addActionListener(this);
        add(print);

        cancel = new JButton("Cancel");
        cancel.setBounds(220,70,80,20);
        cancel.addActionListener(this);
        add(cancel);

        setSize(900,700);
        setLocation(300,100);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==search){
            String q = "select * from studentleave where rollNo = '"+choicerollNo.getSelectedItem()+"' ";
            try {
                Conn c = new Conn();
                ResultSet resultSet = c.statement.getResultSet();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }catch (Exception E){
                E.printStackTrace();
            }

        } else if(e.getSource()==print) {
            try {
                table.print();
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else{
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentLeaveDetails();
    }
}