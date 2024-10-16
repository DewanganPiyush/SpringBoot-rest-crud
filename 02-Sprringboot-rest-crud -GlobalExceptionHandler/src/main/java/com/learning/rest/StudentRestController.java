package com.learning.rest;

import com.learning.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> theStudents;

    @PostConstruct
    public void loadData(){
        theStudents = new ArrayList<Student>();

        theStudents.add(new Student("Aman","Gupta"));
        theStudents.add(new Student("Loki","Srivastav"));
        theStudents.add(new Student("Charan","Singh"));
    }

    // multiple students
    @GetMapping("/student")
    public List<Student> getStudents() {



         return theStudents;
    }

    //Single students
    @GetMapping("/student/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // exception handling

        if(studentId > theStudents.size() || studentId < 0){
            throw new StudentNotFoundException("Student id " + studentId + " not found");
        }

        return theStudents.get(studentId);
    }





}
