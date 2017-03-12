(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('TimingLogDeleteController',TimingLogDeleteController);

    TimingLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'TimingLog'];

    function TimingLogDeleteController($uibModalInstance, entity, TimingLog) {
        var vm = this;

        vm.timingLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TimingLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
