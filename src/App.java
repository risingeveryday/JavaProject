import services.AttendanceManager;

public class App {
    public static void main(String[] args) {
        AttendanceManager manager = new AttendanceManager();
        manager.processAttendanceFromFile();
    }
}
