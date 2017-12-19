angular.module("AngularApp",['ui.router'])
    .factory("AuthInterceptor",function ($rootScope, $q, AuthService, $state) {
        return {
            responseError: function(response) {
                if (response.status == 401){
                    var to = $rootScope.toState;
                    var params = $rootScope.toStateParams;
                    $rootScope.previousStateName = to;
                    $rootScope.previousStateNameParams = params;
                    $state.go('login');
                }
                return $q.reject(response);
            }
        }
    })
    .config(function ($urlRouterProvider,$httpProvider) {
        $urlRouterProvider.otherwise("/")
        $httpProvider.interceptors.push('AuthInterceptor');
    })
    .run(function ($rootScope,AuthService) {
        //Angular 监听路由变化 http://blog.csdn.net/JunBo_Song/article/details/52127485

        //如果想直接跳转到
        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            AuthService.auth();
    });
});