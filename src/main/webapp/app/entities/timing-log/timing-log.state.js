(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('timing-log', {
            parent: 'entity',
            url: '/timing-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimingLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/timing-log/timing-logs.html',
                    controller: 'TimingLogController',
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
        .state('timing-log-detail', {
            parent: 'timing-log',
            url: '/timing-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TimingLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/timing-log/timing-log-detail.html',
                    controller: 'TimingLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TimingLog', function($stateParams, TimingLog) {
                    return TimingLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'timing-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('timing-log-detail.edit', {
            parent: 'timing-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timing-log/timing-log-dialog.html',
                    controller: 'TimingLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TimingLog', function(TimingLog) {
                            return TimingLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('timing-log.new', {
            parent: 'timing-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timing-log/timing-log-dialog.html',
                    controller: 'TimingLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                datetimeLog: null,
                                switcherId: null,
                                deviceId: null,
                                startTime: null,
                                duration: null,
                                uid: null,
                                command: null,
                                valueTiming: null,
                                valueLoop: null,
                                onofflogId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('timing-log', null, { reload: 'timing-log' });
                }, function() {
                    $state.go('timing-log');
                });
            }]
        })
        .state('timing-log.edit', {
            parent: 'timing-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timing-log/timing-log-dialog.html',
                    controller: 'TimingLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TimingLog', function(TimingLog) {
                            return TimingLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timing-log', null, { reload: 'timing-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('timing-log.delete', {
            parent: 'timing-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timing-log/timing-log-delete-dialog.html',
                    controller: 'TimingLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TimingLog', function(TimingLog) {
                            return TimingLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timing-log', null, { reload: 'timing-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
