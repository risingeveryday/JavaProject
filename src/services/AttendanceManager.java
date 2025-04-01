package services;

import java.io.*;
import java.util.*;

public class AttendanceManager {
    private static final String INPUT_FILE = "input.csv";
    private static final String OUTPUT_FILE = "attendance.csv";
    private static final List<String> STUDENT_IDS = generateStudentIDs();
    private static final String SUBJECT = "Java"; // Change dynamically if needed
    private static final String MONTH = "01"; // Month for subject

    public void processAttendanceFromFile() {
        Map<String, Map<String, Integer>> attendanceMap = new LinkedHashMap<>();
        Set<String> dates = new LinkedHashSet<>();
        List<String> headers = new ArrayList<>(); // Store headers

        // Initialize map with student IDs and empty attendance records
        for (String studentId : STUDENT_IDS) {
            attendanceMap.put(studentId, new LinkedHashMap<>());
        }

        // Read input.csv
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            br.readLine(); // Skip subject line
            String headerLine = br.readLine(); // Read headers
            if (headerLine != null) {
                headers = Arrays.asList(headerLine.split(",")); // Store headers
                dates.addAll(headers.subList(1, headers.size())); // Extract day values as headers
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    String studentId = parts[0].trim();
                    Map<String, Integer> studentAttendance = attendanceMap.getOrDefault(studentId,
                            new LinkedHashMap<>());

                    for (int i = 1; i < parts.length; i++) {
                        String day = headers.get(i); // Store day directly
                        int present = Integer.parseInt(parts[i].trim());
                        studentAttendance.put(day, present);
                        dates.add(day);
                    }

                    attendanceMap.put(studentId, studentAttendance);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }

        // Write to attendance.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            bw.write(SUBJECT + "(" + MONTH + ")\n"); // First line: Subject with month

            // Write headers
            bw.write("StudentID");
            for (String day : dates) {
                bw.write("," + day);
            }
            bw.write("\n");

            // Write student attendance records
            for (String studentId : STUDENT_IDS) {
                bw.write(studentId);
                for (String day : dates) {
                    bw.write("," + attendanceMap.get(studentId).getOrDefault(day, 0));
                }
                bw.write("\n");
            }

            System.out.println("✅ Attendance processed successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }

    private static List<String> generateStudentIDs() {
        List<String> ids = new ArrayList<>();
        for (int i = 112315001; i <= 112315218; i++)
            ids.add(String.valueOf(i));
        for (int i = 112316001; i <= 112316056; i++)
            ids.add(String.valueOf(i));
        return ids;
    }
}
