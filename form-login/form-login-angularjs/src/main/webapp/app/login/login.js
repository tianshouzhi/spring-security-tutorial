angular.module('AngularApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: 'app/login/login.html',
                controller: 'LoginController'
            });
    });

angular.module('AngularApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, AuthService) {
        $scope.login = function (event) {
            event.preventDefault();
            AuthService.auth({
                username: $scope.username,
                password: $scope.password,
            }).then(function () {
                $scope.authenticationError = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });