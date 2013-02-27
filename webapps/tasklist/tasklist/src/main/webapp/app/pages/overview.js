"use strict";

define(["angular"], function(angular) {

  var module = angular.module("tasklist.pages");

  var Controller = function($scope, $location, EngineApi) {

    var allTasks = {
      "mytasks": [
        { id: 1, name: "ASD", process: "asdf", created: "sadf", due: "asdsd"}],
      "unassigned": [
        { id: 1, name: "ASD", process: "asdf", created: "sadf", due: "asdsd"},
        { id: 1, name: "erer", process: "tzz", created: "sadf", due: "asdsd"},
        { id: 1, name: "ASD", process: "werw", created: "sadf", due: "asdsd"}],
      "colleague-klaus": [],
      "group-my-group": [
        { id: 1, name: "asd", process: "werwe", created: "sadf", due: "asdsd"},
        { id: 1, name: "g", process: "asdwerewrf", created: "sadf", due: "asdsd"}],
      "group-other-group": []
    };

    function loadTasks(filter, search) {
      EngineApi.getTasklist();

      $scope.taskList.tasks = allTasks[filter + (search ? "-" + search : "")];
      $scope.taskList.view = { filter: filter, search: search };
      $scope.taskList.selection = [];
    }

    $scope.$watch(function() { return $location.search(); }, function(newValue) {
      loadTasks(newValue.filter || "mytasks", newValue.search);
    });

    $scope.startTask = function(task) {

    };

    $scope.claimTask = function(task) {

    };

    $scope.delegateTask = function(task) {

    };

    $scope.isSelected = function(task) {
      return $scope.taskList.selection.indexOf(task) != -1;
    };

    $scope.selectAllTasks = function() {

      $scope.deselectAllTasks();

      var selection = $scope.taskList.selection,
          tasks = $scope.taskList.tasks;

      angular.forEach(tasks, function(task) {
        selection.push(task);
      });
    };

    $scope.deselectAllTasks = function() {
      return $scope.taskList.selection = [];
    };

    $scope.taskList = {
      tasks: [],
      selection: []
    };
  };

  Controller.$inject = ["$scope", "$location", "EngineApi"];

  var RouteConfig = function($routeProvider) {
    $routeProvider.when("/overview", {
      templateUrl: "pages/overview.html",
      controller: Controller
    });
  };

  RouteConfig.$inject = [ "$routeProvider"];

  module
    .config(RouteConfig)
    .controller("OverviewController", Controller);
});