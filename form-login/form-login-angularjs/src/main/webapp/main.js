angular.module("AngularApp",['ui.router'])
    .config(function ($urlRouterProvider) {
        $urlRouterProvider.otherwise("/");
}).run(function ($rootScope) {
    $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams,AuthService){
        AuthService.auth();
    });
})