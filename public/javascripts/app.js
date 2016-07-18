var app = angular.module("et", ["ngResource", "ngRoute", "ngTable"]);

app.config(function($routeProvider, $locationProvider) {
	$routeProvider
		.when("/", {
			redirectTo: "/students"
		})
		.when("/students", {
			templateUrl: "/assets/html/student.html",
			controller: "StudentController"
		})
		.otherwise({
			redirectTo: "/students"
		});
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});
});