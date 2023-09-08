package com.example.demo.core;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.text.Style;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    private final Logger logger= LoggerFactory.getLogger(getClass());

    //getting all student records
    public List<Student> getAllStudent()
    {
        //this.createTestData();
        List<Student> students = new ArrayList<Student>();
        studentRepository.findAll().forEach(student -> students.add(student));
        return students;
    }

    private void createTestData(){
        for(long i=0; i<3; i++){
            Student student=new Student();
            student.setId(i);
            student.setName("name: "+i);
            student.setAge(12);
            studentRepository.save(student);
        }
    }

    //getting a specific record
    public Student getStudentById(Long id)
    {
        return studentRepository.findById(id).orElse(null);
    }
    public Student createStudent(Student student)
    {
        if (student.getId() != null && studentRepository.existsById(student.getId())) {
            logger.warn("Student already exist&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            return null;
            //logger.warn("after null");
        }
        logger.warn("save to repo");
        return studentRepository.save(student);
    }
    public Student updateStudent(Long id, Student newStudent){
        Optional<Student> oldStudent=studentRepository.findById(id);
        if(oldStudent.isPresent()){
            Student updatedStudent=oldStudent.get();
            updatedStudent.setName(newStudent.getName());
            updatedStudent.setAge(newStudent.getAge());
            return studentRepository.save(updatedStudent);
        }
        return null;
    }

    //deleting a specific record
    public boolean deleteStudent(Long id)
    {
        if(!studentRepository.existsById(id)){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}
