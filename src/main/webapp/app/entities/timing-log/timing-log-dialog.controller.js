(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('TimingLogDialogController', TimingLogDialogController);

    TimingLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimingLog'];

    function TimingLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TimingLog) {
        var vm = this;

        vm.timingLog = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.timingLog.id !== null) {
                TimingLog.update(vm.timingLog, onSaveSuccess, onSaveError);
            } else {
                TimingLog.save(vm.timingLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipstermongoApp:timingLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
