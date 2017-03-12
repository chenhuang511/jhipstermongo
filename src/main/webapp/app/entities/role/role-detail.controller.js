(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('RoleDetailController', RoleDetailController);

    RoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Role'];

    function RoleDetailController($scope, $rootScope, $stateParams, previousState, entity, Role) {
        var vm = this;

        vm.role = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:roleUpdate', function(event, result) {
            vm.role = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
