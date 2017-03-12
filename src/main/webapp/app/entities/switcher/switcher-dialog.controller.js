(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('SwitcherDialogController', SwitcherDialogController);

    SwitcherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Switcher'];

    function SwitcherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Switcher) {
        var vm = this;

        vm.switcher = entity;
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
            if (vm.switcher.id !== null) {
                Switcher.update(vm.switcher, onSaveSuccess, onSaveError);
            } else {
                Switcher.save(vm.switcher, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipstermongoApp:switcherUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
