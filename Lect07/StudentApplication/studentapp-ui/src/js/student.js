(function () {
    'use strict';

    angular.module("student-ui", ['ngResource', 'ng']);         //registered our angular js application (named as student-ui) as module

    function StudentService($resource) {
            return $resource('/api/v1/student/:id');     //service created that is = our backend controller (server side service)
        }

    angular.module('student-ui').factory('StudentService', ['$resource', StudentService]);  //registered the above created service in our application 'student-ui'

    //This controller will bind our student.html file with backed model
    //The below controller function is equivalent to class, we can define variables,
    function StudentController(StudentService) {
            var self = this;

            self.service = StudentService;      //StudentService receiving in constructor
            self.student = [];                  //created array of students
            self.name = '';                     //need to bind 'name' with backend, bcz we want to 'search by name' or 'search by name like'
            self.display = false;               //for performance improvement

            self.init = function () {
                self.search();                  //calling below function
            }

            self.search = function () {
                self.display = false;
                var parameters = {};            //created a variable
                if (self.name) {                //checking if user has input something into search field
                    parameters.name = self.name;    //added that input into parameters
                }
                else{
                    console.log('in else');
                    parameters.name = ''
                }
                self.service.get(parameters).$promise.then(function (response) {        //calling 'get' method of service by passing parameters, all post,delete methods exist
                    console.log('req. sent')
                    self.display = true;
                    self.student = response.content;
                });
            }

            self.init();    //auto call init method when controller loads, it will go to 'else' and will display all students
        }

        angular.module("student-ui").controller('StudentController', ['StudentService', StudentController]);    //registered the above controller in our application

}());