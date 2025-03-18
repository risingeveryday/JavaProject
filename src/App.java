import models.AttendanceRecord;
import services.AttendanceManager;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
        AttendanceManager manager = new AttendanceManager();

        // Adding records
        manager.addAttendance(new AttendanceRecord("S001", "Math", LocalDate.now(), true));
        manager.addAttendance(new AttendanceRecord("S002", "Physics", LocalDate.now(), false));

        // Fetching records
        System.out.println("Attendance for S001:");
        for (AttendanceRecord record : manager.getAttendance("S001")) {
            System.out.println(record);
        }
    }
}
