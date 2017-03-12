(function() {
    'use strict';
    angular
        .module('jhipstermongoApp')
        .factory('Switcher', Switcher);

    Switcher.$inject = ['$resource'];

    function Switcher ($resource) {
        var resourceUrl =  'api/switchers/:id';

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
