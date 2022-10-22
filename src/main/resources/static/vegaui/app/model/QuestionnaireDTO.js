/*
 * File: app/model/QuestionnaireDTO.js
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

Ext.define('VegaJhUI.model.QuestionnaireDTO', {
    extend: 'Ext.data.Model',

    requires: [
        'Ext.data.field.Integer',
        'Ext.data.field.String'
    ],

    fields: [
        {
            type: 'int',
            name: 'id'
        },
        {
            type: 'string',
            name: 'name'
        },
        {
            type: 'string',
            name: 'version'
        },
        {
            type: 'string',
            name: 'title'
        },
        {
            type: 'string',
            name: 'subTitle'
        },
        {
            type: 'string',
            name: 'notes'
        },
        {
            type: 'string',
            name: 'image'
        },
        {
            type: 'string',
            name: 'imageAlt'
        },
        {
            type: 'string',
            name: 'instructions'
        },
        {
            type: 'int',
            name: 'compilationTime'
        },
        {
            type: 'int',
            name: 'forcedTerminationTime'
        },
        {
            type: 'int',
            name: 'usedSeconds'
        },
        {
            type: 'int',
            name: 'status'
        },
        {
            type: 'string',
            name: 'xml'
        },
        {
            type: 'string',
            name: 'json'
        },
        {
            type: 'string',
            name: 'saveText'
        },
        {
            type: 'string',
            name: 'searchText'
        },
        {
            type: 'string',
            name: 'subjectToEvaluation'
        }
    ]
});