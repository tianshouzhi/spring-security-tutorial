/**
 * Created by tianshouzhi on 2017/12/19.
 */
angular.module('AngularApp')
    .factory("AuthService",function ($state,$rootScope) {
        $rootScope.isAuthenticated=false;
        return{
            auth:function () {
                if($rootScope.isAuthenticated){
                    return true;
                }else{
                    $state.go("login");
                    return false;
                }
            }
        }
    })
