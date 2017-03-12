(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('OnOffLogDetailController', OnOffLogDetailController);

    OnOffLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OnOffLog'];

    function OnOffLogDetailController($scope, $rootScope, $stateParams, previousState, entity, OnOffLog) {
        var vm = this;

        vm.onOffLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipstermongoApp:onOffLogUpdate', function(event, result) {
            vm.onOffLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
