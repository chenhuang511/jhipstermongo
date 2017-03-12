(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('AreaDialogController', AreaDialogController);

    AreaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Area'];

    function AreaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Area) {
        var vm = this;

        vm.area = entity;
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
            if (vm.area.id !== null) {
                Area.update(vm.area, onSaveSuccess, onSaveError);
            } else {
                Area.save(vm.area, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipstermongoApp:areaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
