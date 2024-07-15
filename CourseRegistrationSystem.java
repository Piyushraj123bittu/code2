import java.util.ArrayList;
import java.util.Scanner;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    String schedule;
    ArrayList<String> registeredStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.registeredStudents = new ArrayList<>();
    }

    public boolean registerStudent(String studentID) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(studentID);
            return true;
        }
        return false;
    }

    public void dropStudent(String studentID) {
        registeredStudents.remove(studentID);
    }

    public int getAvailableSlots() {
        return capacity - registeredStudents.size();
    }

    public String toString() {
        return courseCode + ": " + title + "\nDescription: " + description + "\nSchedule: " + schedule + 
            "\nCapacity: " + capacity + "\nAvailable Slots: " + getAvailableSlots();
    }
}

class Student {
    String studentID;
    String name;
    ArrayList<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.registerStudent(studentID)) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Registration failed. Course is full.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent(studentID);
            System.out.println("Successfully dropped " + course.title);
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public String toString() {
        return "Student ID: " + studentID + "\nName: " + name + "\nRegistered Courses: " + registeredCourses.size();
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Sample courses
        courses.add(new Course("CS101", "Introduction to Computer Science", "Basics of computer science.", 30, "MWF 9-10AM"));
        courses.add(new Course("MATH201", "Calculus I", "Differential and integral calculus.", 25, "TTh 11-12:30PM"));
        courses.add(new Course("ENG301", "English Literature", "Study of English literature.", 20, "MWF 2-3PM"));

        // Sample students
        students.add(new Student("S001", "Alice"));
        students.add(new Student("S002", "Bob"));
        students.add(new Student("S003", "Charlie"));

        while (running) {
            System.out.println("\nCourse Registration System Menu:");
            System.out.println("1. Display available courses");
            System.out.println("2. Register student for a course");
            System.out.println("3. Drop a course for a student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Courses:");
                    for (Course course : courses) {
                        System.out.println(course);
                        System.out.println();
                    }
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    String studentID = scanner.nextLine();
                    Student student = findStudent(students, studentID);
                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("Enter course code: ");
                    String courseCode = scanner.nextLine();
                    Course course = findCourse(courses, courseCode);
                    if (course == null) {
                        System.out.println("Course not found.");
                        break;
                    }
                    student.registerCourse(course);
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextLine();
                    student = findStudent(students, studentID);
                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }
                    System.out.print("Enter course code: ");
                    courseCode = scanner.nextLine();
                    course = findCourse(courses, courseCode);
                    if (course == null) {
                        System.out.println("Course not found.");
                        break;
                    }
                    student.dropCourse(course);
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    public static Student findStudent(ArrayList<Student> students, String studentID) {
        for (Student student : students) {
            if (student.studentID.equals(studentID)) {
                return student;
            }
        }
        return null;
    }

    public static Course findCourse(ArrayList<Course> courses, String courseCode) {
        for (Course course : courses) {
            if (course.courseCode.equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}