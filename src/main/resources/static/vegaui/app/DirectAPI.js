Ext.define('VegaJhUI.DirectAPI', {
    requires: ['Ext.direct.*', 'Ext.Ajax']
}, function() {
    Ext.Ajax.request({
        url: "http://localhost:8080/api.js?baseRouterUrl=http://localhost:8080&fullRouterUrl=true",
        async: false,
        success: function(xhr) {
            Ext.globalEval(xhr.responseText);
        },
        failure: function(xhr) {
            throw "Direct API load failed with error code " + xhr.status + ": " + xhr.statusText;
        }
    });
    Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});