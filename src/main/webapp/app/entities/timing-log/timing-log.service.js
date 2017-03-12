(function() {
    'use strict';
    angular
        .module('jhipstermongoApp')
        .factory('TimingLog', TimingLog);

    TimingLog.$inject = ['$resource'];

    function TimingLog ($resource) {
        var resourceUrl =  'api/timing-logs/:id';

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
