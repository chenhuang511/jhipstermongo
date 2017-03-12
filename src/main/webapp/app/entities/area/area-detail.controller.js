(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('AreaDetailController', AreaDetailController);

    AreaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Area'];

    function AreaDetailController($scope, $rootScope, $stateParams, previousState, entity, Area) {
        var vm = this;

        vm.area = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:areaUpdate', function(event, result) {
            vm.area = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
