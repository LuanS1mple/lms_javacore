package org.luans1mple.lmscore.controller.ulties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.awt.GridLayout;
import java.util.concurrent.SynchronousQueue;

public class Ulti<T> {
    public void showList(List<T> all){
        for (int i = 0; i < all.size(); i++) {
            System.out.println(all.get(i).toString());
        }
    }
    public static String generateClassCode(){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid.substring(0, 8);
    }
    public static LocalDateTime getDatetimeByUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        // Date picker setup
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Hôm nay");
        p.put("text.month", "Tháng");
        p.put("text.year", "Năm");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new JFormattedTextField.AbstractFormatter() {
            private final String pattern = "dd-MM-yyyy";
            private final SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            @Override
            public Object stringToValue(String text) throws java.text.ParseException {
                return sdf.parseObject(text);
            }

            @Override
            public String valueToString(Object value) {
                if (value != null) {
                    Calendar cal = (Calendar) value;
                    return sdf.format(cal.getTime());
                }
                return "";
            }
        });

        // Hour and minute selection
        JComboBox<Integer> hourBox = new JComboBox<>();
        JComboBox<Integer> minuteBox = new JComboBox<>();
        for (int i = 0; i < 24; i++) hourBox.addItem(i);
        for (int i = 0; i < 60; i++) minuteBox.addItem(i);

        // Add components to panel
        panel.add(new JLabel("Chọn ngày:"));
        panel.add(datePicker);
        panel.add(new JLabel("Chọn giờ:"));
        panel.add(hourBox);
        panel.add(new JLabel("Chọn phút:"));
        panel.add(minuteBox);

        // 🔥 Tạo một JFrame ẩn làm chủ để đảm bảo hộp thoại hiển thị trong CLI
        JFrame owner = new JFrame();
        owner.setUndecorated(true);
        owner.setAlwaysOnTop(true);
        owner.setSize(1, 1);
        owner.setLocationRelativeTo(null);
        owner.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        owner.setVisible(true); // Phải hiển thị để làm owner hợp lệ

        // Hiển thị hộp thoại
        int result = JOptionPane.showConfirmDialog(owner, panel, "Chọn ngày & giờ", JOptionPane.OK_CANCEL_OPTION);
        owner.dispose(); // Dọn sau khi xong

        // Trả về kết quả nếu chọn OK
        if (result == JOptionPane.OK_OPTION) {
            Date selectedDate = (Date) datePicker.getModel().getValue();
            if (selectedDate == null) return null;

            int hour = (int) hourBox.getSelectedItem();
            int minute = (int) minuteBox.getSelectedItem();

            return selectedDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .atTime(hour, minute);
        }

        return null; // Nếu bấm Cancel
    }


    public static void main(String[] args) {
        LocalDateTime date = Ulti.getDatetimeByUI();
        System.out.println(date);
    }
}
