(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('OnOffLogDeleteController',OnOffLogDeleteController);

    OnOffLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'OnOffLog'];

    function OnOffLogDeleteController($uibModalInstance, entity, OnOffLog) {
        var vm = this;

        vm.onOffLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OnOffLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
