(function() {
    'use strict';

    angular
        .module('jhipstermongoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('on-off-log', {
            parent: 'entity',
            url: '/on-off-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OnOffLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/on-off-log/on-off-logs.html',
                    controller: 'OnOffLogController',
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
        .state('on-off-log-detail', {
            parent: 'on-off-log',
            url: '/on-off-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OnOffLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/on-off-log/on-off-log-detail.html',
                    controller: 'OnOffLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OnOffLog', function($stateParams, OnOffLog) {
                    return OnOffLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'on-off-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('on-off-log-detail.edit', {
            parent: 'on-off-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/on-off-log/on-off-log-dialog.html',
                    controller: 'OnOffLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OnOffLog', function(OnOffLog) {
                            return OnOffLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('on-off-log.new', {
            parent: 'on-off-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/on-off-log/on-off-log-dialog.html',
                    controller: 'OnOffLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                areaId: null,
                                deviceId: null,
                                switchId: null,
                                customerId: null,
                                datetimeLog: null,
                                startTime: null,
                                finishTime: null,
                                uid: null,
                                status: null,
                                type: null,
                                command: null,
                                addInfo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('on-off-log', null, { reload: 'on-off-log' });
                }, function() {
                    $state.go('on-off-log');
                });
            }]
        })
        .state('on-off-log.edit', {
            parent: 'on-off-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/on-off-log/on-off-log-dialog.html',
                    controller: 'OnOffLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OnOffLog', function(OnOffLog) {
                            return OnOffLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('on-off-log', null, { reload: 'on-off-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('on-off-log.delete', {
            parent: 'on-off-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/on-off-log/on-off-log-delete-dialog.html',
                    controller: 'OnOffLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OnOffLog', function(OnOffLog) {
                            return OnOffLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('on-off-log', null, { reload: 'on-off-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
