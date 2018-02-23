/**
 * Created by tianshouzhi on 2017/12/21.
 */
angular.module("AngularApp", ['ui.router'])
    .run(function ($rootScope, $state) {
        //angular-ui-router版本使用0.3.1
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams, fromState, fromParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
        })
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            if (toState.name != 'login' && $rootScope.previousStateName) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            }
        })
    })
    .factory("AuthInterceptor", function ($rootScope, $q, $injector) {
        return {
            responseError: function (response) {
                if (response.status == 401) {//当未登录时，访问受保护的资源时，服务端返回401
                    var to = $rootScope.toState;
                    var params = $rootScope.toStateParams;
                    $rootScope.previousStateName = to;
                    $rootScope.previousStateNameParams = params;
                    var $state = $injector.get('$state');
                    $state.go('login');
                }
                return $q.reject(response);
            }
        }
    })
    .config(function ($urlRouterProvider, $stateProvider, $httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');
        $urlRouterProvider.otherwise("/");
        $stateProvider.state('login', {
            url: '/login',
            templateUrl: 'app/login/login.html',
            controller: 'LoginController'
        }).state("home", {
            url: "/",
            controller: "HomeController",
            templateUrl: "app/home/home.html"
        }).state("profile", {
            url: "/profile",
            controller: "ProfileController",
            templateUrl: "app/profile/profile.html"
        });
    })
    .controller('HomeController', function ($rootScope, $scope, $http) {
        $http.get("/user").then(function (response) {
            $scope.user = response.data;
        })
    })
    .controller('ProfileController', function ($rootScope, $scope, $http) {
        $http.get("/user").then(function (response) {
            $scope.user = response.data;
        })
    })
    .controller('LoginController', function ($rootScope, $scope, $http, $state) {
        $scope.login = function (event) {
            event.preventDefault();
            //不能使用json格式
            var data = "username=" + encodeURIComponent($scope.username) + "&password=" + $scope.password;

            $http.post("/login", data, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then(function (response) {
                if ("login" == $rootScope.previousStateName || null == $rootScope.previousStateName) {
                    $state.go("home");
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateNameParams);
                }
            }, function (response) {
                $scope.loginError = true;
            });
        };
    });