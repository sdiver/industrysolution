/*
*AUTHOR:MT
*DATE:2017-1-14
*REVISER:
*LAST CHANGE:
*/

var app = angular.module('customermanager', ['ui.router','ngAnimate', 'ngSanitize', 'ui.bootstrap','ngFileUpload']);

app.config(['$stateProvider','$urlRouterProvider', function($stateProvider,$urlRouterProvider){        
    $stateProvider.state('login',{
        url: '/login',
        cache:false,
        templateUrl: 'app/templates/login.html'
    })
    .state("workspace",{
        url:"/workspace",
        abstract:true,
        templateUrl:"app/templates/workspace.html"
    })
    .state("workspace.solutionmanager_1_0", {
        url: "/solutionmanager_1_0",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_1_0/solutionmanager_1_0.html"
            }
        }
    })
    .state("workspace.solutionmanager_1_1", {
        url: "/solutionmanager_1_1",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_1_1/solutionmanager_1_1.html"
            }
        }
    })
    .state("workspace.solutionmanager_1_2", {
        url: "/solutionmanager_1_2",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_1_2/solutionmanager_1_2.html"
            }
        }
    })
    .state("workspace.solutionmanager_1_3", {
        url: "/solutionmanager_1_3",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_1_3/solutionmanager_1_3.html"
            }
        }
    })
    .state("workspace.solutionmanager_2_0", {
        url: "/solutionmanager_2_0",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_2_0/solutionmanager_2_0.html"
            }
        }
    })
    .state("workspace.solutionmanager_2_1", {
        url: "/solutionmanager_2_1",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_2_1/solutionmanager_2_1.html"
            }
        }
    })
    .state("workspace.solutionmanager_2_2", {
        url: "/solutionmanager_2_2",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_2_2/solutionmanager_2_2.html"
            }
        }
    })
    .state("workspace.solutionmanager_2_3", {
        url: "/solutionmanager_2_3",
        views: {
            "content":{
                templateUrl:"app/pages/solutionmanager_2_3/solutionmanager_2_3.html"
            }
        }
    })
    .state("workspace.setting", {
        url: "/setting",
        views: {
            "content":{
                templateUrl:"app/templates/setting.html"
            }
        }
    });

    $urlRouterProvider.otherwise("/login");
}]);