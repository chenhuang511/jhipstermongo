(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('TimingLogDetailController', TimingLogDetailController);

    TimingLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TimingLog'];

    function TimingLogDetailController($scope, $rootScope, $stateParams, previousState, entity, TimingLog) {
        var vm = this;

        vm.timingLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:timingLogUpdate', function(event, result) {
            vm.timingLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
