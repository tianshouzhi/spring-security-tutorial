angular.module('AngularApp')
    .config(function ($stateProvider) {
        $stateProvider.state("home",{
            url:"/",
            controller:"HomeController",
            templateUrl:"app/home/home.html"
        })
    }).controller("HomeController",function ($scope) {

})