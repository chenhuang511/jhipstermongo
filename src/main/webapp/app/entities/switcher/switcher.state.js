(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('switcher', {
            parent: 'entity',
            url: '/switcher?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Switchers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/switcher/switchers.html',
                    controller: 'SwitcherController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('switcher-detail', {
            parent: 'switcher',
            url: '/switcher/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Switcher'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/switcher/switcher-detail.html',
                    controller: 'SwitcherDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Switcher', function($stateParams, Switcher) {
                    return Switcher.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'switcher',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('switcher-detail.edit', {
            parent: 'switcher-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/switcher/switcher-dialog.html',
                    controller: 'SwitcherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Switcher', function(Switcher) {
                            return Switcher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('switcher.new', {
            parent: 'switcher',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/switcher/switcher-dialog.html',
                    controller: 'SwitcherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                deviceId: null,
                                name: null,
                                number: null,
                                status: null,
                                counter: null,
                                countTiming: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('switcher', null, { reload: 'switcher' });
                }, function() {
                    $state.go('switcher');
                });
            }]
        })
        .state('switcher.edit', {
            parent: 'switcher',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/switcher/switcher-dialog.html',
                    controller: 'SwitcherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Switcher', function(Switcher) {
                            return Switcher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('switcher', null, { reload: 'switcher' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('switcher.delete', {
            parent: 'switcher',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/switcher/switcher-delete-dialog.html',
                    controller: 'SwitcherDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Switcher', function(Switcher) {
                            return Switcher.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('switcher', null, { reload: 'switcher' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
