/*
 * File: app/store/QuestionnaireDirectStore.js
 *
 * This file was generated by Sencha Architect version 4.3.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 7.6.x Classic library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 7.6.x Classic. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('VegaJhUI.store.QuestionnaireDirectStore', {
    extend: 'Ext.data.Store',

    requires: [
        'VegaJhUI.model.QuestionnaireDTO',
        'Ext.data.proxy.Direct',
        'VegaJhUI.DirectAPI',
        'Ext.data.reader.Json'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'QuestionnaireDirectStore',
            autoLoad: true,
            model: 'VegaJhUI.model.QuestionnaireDTO',
            proxy: {
                type: 'direct',
                api: {
                    read: 'questionnaireDirect.loadAllQuestionnaires'
                },
                reader: {
                    type: 'json',
                    messageProperty: 'Errore nella lettura dei Questionari',
                    rootProperty: 'records',
                    listeners: {
                        exception: {
                            fn: me.onJsonException,
                            scope: me
                        }
                    }
                },
                listeners: {
                    exception: {
                        fn: me.onDirectException,
                        scope: me
                    }
                }
            }
        }, cfg)]);
    },

    onJsonException: function(reader, response, error, eOpts) {
        var me = this;
        Ext.MessageBox.alert('Errore nell\'accesso alla tabella Questionnaire', JSON.parse(response.responseText).message,
            function () {
                me.rejectChanges();
            }, me);
    },

    onDirectException: function(proxy, response, operation, eOpts) {
        var me = this;
        Ext.MessageBox.alert('Errore nell\'accesso alla tabella Questionnaire', operation.getError(),
            function () {
                me.rejectChanges();
        }, me);
    }

});