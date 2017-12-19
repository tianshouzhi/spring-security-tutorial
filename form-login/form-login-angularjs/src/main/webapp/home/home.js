angular.module('AngularApp')
    .config(function ($stateProvider) {
        $stateProvider.state("home",{
            url:"/",
            controller:"HomeController",
            templateUrl:"home/home.html"
        })
    }).controller("HomeController",function ($scope) {

})