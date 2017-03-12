(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .controller('SwitcherDeleteController',SwitcherDeleteController);

    SwitcherDeleteController.$inject = ['$uibModalInstance', 'entity', 'Switcher'];

    function SwitcherDeleteController($uibModalInstance, entity, Switcher) {
        var vm = this;

        vm.switcher = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Switcher.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
