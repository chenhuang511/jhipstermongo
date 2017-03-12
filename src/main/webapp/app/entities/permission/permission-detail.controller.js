(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('PermissionDetailController', PermissionDetailController);

    PermissionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Permission'];

    function PermissionDetailController($scope, $rootScope, $stateParams, previousState, entity, Permission) {
        var vm = this;

        vm.permission = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:permissionUpdate', function(event, result) {
            vm.permission = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
