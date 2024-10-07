import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Course class
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    // constructor
    public Course(String courseCode, String title, String description, int capacity, String schedule, int enrolledStudents) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    // getter 
    public String getCourseCode() {
        return courseCode;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getCapacity() {
        return capacity;
    }
    public String getSchedule() {
        return schedule;
    }
    public int getEnrolledStudents() {
        return enrolledStudents;
    }
    public boolean isFull() {
        return enrolledStudents >= capacity;
    }

    // Enroll a student
    public boolean enrollStudent() {
        if(!isFull()) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    // Drop a student
    public boolean dropStudent() {
        if(enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }
}

// Student class
class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    // getter
    public String getStudentId() {
        return studentId;
    }
    public String getName() {
        return name;
    }
    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    // Register for a course
    public boolean registerCourse(Course course) {
        if(!course.isFull() && !registeredCourses.contains(course)) {
            registeredCourses.add(course);
            return course.enrollStudent();
        }
        return false;
    }

    // Drop a course
    public boolean  dropCourse(Course course) {
        if(registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            return course.dropStudent();
        }
        return false;
    }
}

// Main class
public class StudentCourseRegistrationSystem {
    private static Map<String, Course> courseDatabase = new HashMap<>();
    private static Map<String, Student> studentDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        courseDatabase.put("CS101" , new Course("CS101", "Intro to CS", "Basics of CS Concepts", 15, "Mon-Wed 10-11 AM", 0));
        courseDatabase.put("MA102" , new Course("MA102", "Calculus II", "Intro to Calculas", 20, "Tue-Thu 8-10 AM", 0));
        courseDatabase.put("CS102" , new Course("CS102", "Data Structures", "Intro to DS like arrays, strings", 30, "Mon-Thu 3-5 PM", 0));
        courseDatabase.put("CS103" , new Course("CS103", "Algorithms", "Study of Algo Design & Study", 17, "Tue-Thu 1-2 PM", 0));
        courseDatabase.put("DB201" , new Course("DB201", "Database Systems", "Intro to RDBMS & SQL", 25, "Thu-Sat 6-8 PM", 0));
        courseDatabase.put("OS202" , new Course("OS202", "Operating Systems", "Concepts of Process,File & Memory", 20, "Fri 1-4 PM", 0));    
        
        studentDatabase.put("CS017", new Student("CS017", "Alice"));
        studentDatabase.put("CS018", new Student("CS018", "Bob"));

        while (true) { 
            System.out.println("\n--- Student Course Registration System ---");
            System.out.println("1. List Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Choose a option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice) {
                case 1:
                    listCourses();
                    break;
                case 2:
                    registerCourse(sc);
                    break;
                case 3:
                    dropCourse(sc);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");            
            }
        }
    }

    // List all available courses
    private static void listCourses() {
        System.out.println("\nAvailable Courses:");
        for(Course course : courseDatabase.values()) {
            System.out.println(course.getCourseCode() + ": " + course.getTitle() + " (" +
            course.getEnrolledStudents() + "/" + course.getCapacity() + " slots filled)");
            System.out.println("  Schedule: " + course.getSchedule());
            System.out.println("  Description: " + course.getDescription());
        }
    }

    // Register a student for a course
    private static void registerCourse(Scanner sc) {
        System.out.print("\nEnter Student ID: ");
        String studentId = sc.nextLine();
        Student student = studentDatabase.get(studentId);

        if(student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();
        Course course = courseDatabase.get(courseCode);

        if(course == null) {
            System.out.println("Course not found.");
            return;
        }

        if(student.registerCourse(course)) {
            System.out.println("Successfully registed for "+ course.getTitle());
        } else {
            System.out.println("Registration failed. Course may be full or already registered");
        }
    }

    // Drop a Course
    private static void dropCourse(Scanner sc) {
        System.out.print("\nEnter Student ID: ");
        String studentID = sc.nextLine();
        Student student = studentDatabase.get(studentID);

        if(student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Enter Course Code: ");
        String courseCode = sc.nextLine();
        Course course = courseDatabase.get(courseCode);

        if(course == null) {
            System.out.println("Course not found.");
            return;
        }

        if(student.dropCourse(course)) {
            System.out.println("Successfully dropped " + course.getTitle());
        } else {
            System.out.println("Drop failed. You may not be registered for this course.");
        }
    }
}