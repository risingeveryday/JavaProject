package services;

import models.AttendanceRecord;
import java.io.*;
import java.util.*;

public class AttendanceManager {
    private static final String FILE_PATH = "attendance.txt";

    public void addAttendance(AttendanceRecord record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(record.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<AttendanceRecord> getAttendance(String studentId) {
        List<AttendanceRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                AttendanceRecord record = AttendanceRecord.fromString(line);
                if (record.getStudentId().equals(studentId)) {
                    records.add(record);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }
}
