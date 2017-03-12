(function() {
    'use strict';
    angular
        .module('jhipstermongoApp')
        .factory('OnOffLog', OnOffLog);

    OnOffLog.$inject = ['$resource'];

    function OnOffLog ($resource) {
        var resourceUrl =  'api/on-off-logs/:id';

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
