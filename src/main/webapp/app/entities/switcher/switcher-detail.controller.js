(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('SwitcherDetailController', SwitcherDetailController);

    SwitcherDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Switcher'];

    function SwitcherDetailController($scope, $rootScope, $stateParams, previousState, entity, Switcher) {
        var vm = this;

        vm.switcher = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:switcherUpdate', function(event, result) {
            vm.switcher = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
