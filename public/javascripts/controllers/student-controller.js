app.controller("StudentController", ($scope, $http, $resource, $routeParams, NgTableParams) => {

	var Student = $resource("/data/student", {}, { get: { isArray: true } });

	$scope.students = [];

	$scope.currentStudentID = null;
	$scope.studentEdit = null;

	$scope.studentTable = new NgTableParams(
		{
			page: 1,
			count: 0
		},
		{
			total: 0,
			/*counts: [5, 10, 15, 20],*/
			getData: params => $scope.students = Student.get()
		}
	);

	$scope.upsertStudent = row => {
		$http.put("/data/student", row).success(() => {
			$scope.studentTable.reload();
			$scope.studentEdit = null;
		});
	};

	$scope.newStudent = () => {
		$scope.students.push({});
		$scope.studentEdit = $scope.students.length - 1;
	};

});