package com.example.demo.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public ResponseEntity<Map<String, List<Student>>> getAllStudent()
    {
        List<Student> student = studentService.getAllStudent();
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("content", student));

    }
    //creating a get mapping that retrieves the detail of a specific student
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id)
    {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }
    @GetMapping("/studentsByName")
    public ResponseEntity<Map<String, List<Student>>> getAllStudentsByName(@RequestParam(name="name") String name)
    {
        List<Student> student = studentService.getAllStudentsByName(name);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("content", student));
    }
    @GetMapping("/studentsByNameLike")
    public ResponseEntity<Map<String, List<Student>>> getAllStudentsByNameLike(@RequestParam(name="name") String name)
    {
        List<Student> student = studentService.getAllStudentsByNameLike(name);
        if (student.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("content", student));
    }

    //creating post mapping that post the student detail in the database
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student)
    {
        Student createdStudent = studentService.createStudent(student);
        if (createdStudent == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(createdStudent);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student newStudent)
    {
        Student oldStudent = studentService.updateStudent(id, newStudent);
        if (oldStudent != null) {
            return ResponseEntity.ok(oldStudent);
        }
        return ResponseEntity.notFound().build();
    }

    //creating a delete mapping that deletes a specific student
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") Long id)
    {
        boolean studentExistedAndDeleted = studentService.deleteStudent(id);
        if(studentExistedAndDeleted){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
