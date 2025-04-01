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
        // Check if input file exists
        File inputFile = new File(INPUT_FILE);
        if (!inputFile.exists()) {
            System.err.println("Input file does not exist: " + INPUT_FILE);
            return;
        }

        Map<String, Map<String, Integer>> attendanceMap = new LinkedHashMap<>();
        Set<String> dates = new LinkedHashSet<>();
        List<String> headers = new ArrayList<>();
        Set<String> inputStudentIds = new HashSet<>();

        // Initialize attendance map with all student IDs
        for (String studentId : STUDENT_IDS) {
            attendanceMap.put(studentId, new LinkedHashMap<>());
        }

        // Read input.csv
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            br.readLine(); // Skip subject line
            String headerLine = br.readLine(); // Read headers
            if (headerLine == null) {
                System.err.println("Error: Input file is missing header line.");
                return;
            }
            headers = Arrays.asList(headerLine.split(","));
            if (headers.size() < 2) {
                System.err.println("Warning: No attendance dates found in header.");
            } else {
                dates.addAll(headers.subList(1, headers.size()));
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) {
                    System.err.println("Warning: Malformed line in input file: " + line);
                    continue;
                }
                String studentId = parts[0].trim();
                inputStudentIds.add(studentId);
                Map<String, Integer> studentAttendance = attendanceMap.getOrDefault(studentId, new LinkedHashMap<>());

                // Process attendance data up to the number of headers
                for (int i = 1; i < headers.size(); i++) {
                    String day = headers.get(i);
                    if (i < parts.length) {
                        try {
                            int present = Integer.parseInt(parts[i].trim());
                            studentAttendance.put(day, present);
                        } catch (NumberFormatException e) {
                            System.err.println("Warning: Invalid attendance value '" + parts[i].trim() +
                                    "' for student " + studentId + " on day " + day);
                            studentAttendance.put(day, 0); // Default to 0
                        }
                    } else {
                        // Fewer columns than headers, assume 0
                        studentAttendance.put(day, 0);
                    }
                }
                attendanceMap.put(studentId, studentAttendance);

                // Warn if line has mismatched column count
                if (parts.length != headers.size()) {
                    System.err.println("Warning: Line for student " + studentId + " has " +
                            (parts.length - 1) + " attendance values, but header has " +
                            (headers.size() - 1) + " dates.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Check for unexpected student IDs in input file
        for (String studentId : inputStudentIds) {
            if (!STUDENT_IDS.contains(studentId)) {
                System.err.println("Warning: Student ID " + studentId +
                        " in input file is not in the predefined list.");
            }
        }

        // Write to attendance.csv
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            bw.write(SUBJECT + "(" + MONTH + ")\n"); // Write subject line

            // Write headers
            bw.write("StudentID");
            if (dates.isEmpty()) {
                System.err.println("Warning: No attendance dates to write.");
            }
            for (String day : dates) {
                bw.write("," + day);
            }
            bw.write("\n");

            // Write attendance records
            for (String studentId : STUDENT_IDS) {
                bw.write(studentId);
                Map<String, Integer> studentAttendance = attendanceMap.get(studentId);
                for (String day : dates) {
                    bw.write("," + studentAttendance.getOrDefault(day, 0));
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
        for (int i = 112315001; i <= 112315218; i++) {
            ids.add(String.valueOf(i));
        }
        for (int i = 112316001; i <= 112316056; i++) {
            ids.add(String.valueOf(i));
        }
        return ids;
    }
}