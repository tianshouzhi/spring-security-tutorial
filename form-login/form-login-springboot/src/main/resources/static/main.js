angular.module("AngularApp", ['ui.router'])
    .factory("AuthInterceptor", function ($rootScope, $q, $state) {
        return {
            responseError: function (response) {
                if (response.status == 401) {//当未登录时，访问受保护的资源时，服务端返回401
                    $state.go('login');
                }
                return $q.reject(response);
            }
        }
    })
    .config(function ($urlRouterProvider,$stateProvider,$httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');

        $urlRouterProvider.otherwise("/");
        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'app/login/login.html',
            controller: 'LoginController'
        }).state("home",{
            url:"/",
            controller:"HomeController",
            templateUrl:"app/home/home.html"
        });

    })
    .controller('HomeController', function ($rootScope, $scope,$http) {
        $http.get("/user").then(function () {
            $scope.user=user;
        })
    })
    .controller('LoginController', function ($rootScope, $scope,$http) {
        $scope.login = function (event) {
            event.preventDefault();
            $http.post("/login", credentials).then(function (response) {
                $rootScope.back();
            }, function () {
                $scope.loginError = true;
            })
     };
    });