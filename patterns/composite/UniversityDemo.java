package main.java.org.example.patterns.composite;

import java.util.*;

interface Member {
    void showDetails();
}

class Professor implements Member {
    private String name;
    Professor(String n) { this.name = n; }
    public void showDetails() {
        System.out.println(" Professor: " + name);
    }
}

class Student implements Member {
    private String name;
    Student(String n) { this.name = n; }
    public void showDetails() {
        System.out.println(" Student: " + name);
    }
}

class Department implements Member {
    private String name;
    private List<Member> members = new ArrayList<>();
    Department(String n) { this.name = n; }
    public void add(Member m) { members.add(m); }
    public void showDetails() {
        System.out.println(" Department: " + name);
        for (Member m : members) {
            m.showDetails();
        }
    }
}

public class UniversityDemo {
    public static void main(String[] args) {
        Professor p1 = new Professor("Dr. Smith");
        Student s1 = new Student("Alice");

        Department cs = new Department("Computer Science");
        cs.add(p1);
        cs.add(s1);

        Department uni = new Department("University");
        uni.add(cs);

        uni.showDetails();
    }
}
