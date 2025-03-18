package models;

import java.time.LocalDate;

public class AttendanceRecord {
    private String studentId;
    private String subject;
    private LocalDate date;
    private boolean present;

    public AttendanceRecord(String studentId, String subject, LocalDate date, boolean present) {
        this.studentId = studentId;
        this.subject = subject;
        this.date = date;
        this.present = present;
    }

    @Override
    public String toString() {
        return studentId + "," + subject + "," + date + "," + present;
    }

    public static AttendanceRecord fromString(String record) {
        String[] parts = record.split(",");
        return new AttendanceRecord(parts[0], parts[1], LocalDate.parse(parts[2]), Boolean.parseBoolean(parts[3]));
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }
}
