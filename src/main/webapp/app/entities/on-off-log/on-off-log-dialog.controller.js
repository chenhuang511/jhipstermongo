(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('OnOffLogDialogController', OnOffLogDialogController);

    OnOffLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OnOffLog'];

    function OnOffLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OnOffLog) {
        var vm = this;

        vm.onOffLog = entity;
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
            if (vm.onOffLog.id !== null) {
                OnOffLog.update(vm.onOffLog, onSaveSuccess, onSaveError);
            } else {
                OnOffLog.save(vm.onOffLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipstermongoApp:onOffLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
