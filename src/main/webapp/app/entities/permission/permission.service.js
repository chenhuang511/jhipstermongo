(function() {
    'use strict';
    angular
        .module('jhipstermongoApp')
        .factory('Permission', Permission);

    Permission.$inject = ['$resource'];

    function Permission ($resource) {
        var resourceUrl =  'api/permissions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
