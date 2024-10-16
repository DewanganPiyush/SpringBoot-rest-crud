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

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex){
        // create a student error response

        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception ex){

        StudentErrorResponse error = new StudentErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
