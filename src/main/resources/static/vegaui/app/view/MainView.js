/*
 * File: app/view/MainView.js
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

Ext.define('VegaJhUI.view.MainView', {
    extend: 'Ext.container.Viewport',
    alias: 'widget.mainview',

    requires: [
        'VegaJhUI.view.MainViewViewModel',
        'Ext.menu.Menu',
        'Ext.menu.Item',
        'Ext.form.Label',
        'Ext.grid.Panel',
        'Ext.grid.column.Number',
        'Ext.grid.filters.filter.String',
        'Ext.view.Table',
        'Ext.grid.filters.Filters'
    ],

    viewModel: {
        type: 'mainview'
    },
    itemId: 'mainView',
    layout: 'border',
    defaultListenerScope: true,

    items: [
        {
            xtype: 'panel',
            region: 'west',
            split: true,
            itemId: 'menuPanel',
            width: 150,
            title: 'Menu',
            items: [
                {
                    xtype: 'menu',
                    floating: false,
                    itemId: 'menu',
                    items: [
                        {
                            xtype: 'menuitem',
                            itemId: 'home',
                            text: 'Home',
                            focusable: true
                        },
                        {
                            xtype: 'menuitem',
                            itemId: 'about',
                            text: 'About Us',
                            focusable: true
                        },
                        {
                            xtype: 'menuitem',
                            itemId: 'contact',
                            text: 'Contact us',
                            focusable: true
                        },
                        {
                            xtype: 'menuitem',
                            itemId: 'questionari',
                            text: 'Questionari',
                            focusable: true
                        }
                    ],
                    listeners: {
                        click: 'onMenuClick'
                    }
                }
            ]
        },
        {
            xtype: 'panel',
            region: 'center',
            itemId: 'contentPanel',
            layout: 'card',
            items: [
                {
                    xtype: 'panel',
                    itemId: 'homePanel',
                    title: 'Home',
                    layout: {
                        type: 'vbox',
                        align: 'center',
                        pack: 'center'
                    },
                    items: [
                        {
                            xtype: 'label',
                            text: 'Home View'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'aboutPanel',
                    title: 'About Us',
                    layout: {
                        type: 'vbox',
                        align: 'center',
                        pack: 'center'
                    },
                    items: [
                        {
                            xtype: 'label',
                            text: 'About Us View'
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    itemId: 'contactPanel',
                    title: 'Contact Us',
                    layout: {
                        type: 'vbox',
                        align: 'center',
                        pack: 'center'
                    },
                    items: [
                        {
                            xtype: 'label',
                            text: 'Contact Us View'
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    itemId: 'questionariPanel',
                    title: 'Questionari',
                    autoLoad: true,
                    store: 'QuestionnaireDirectStore',
                    columns: [
                        {
                            xtype: 'numbercolumn',
                            dataIndex: 'id',
                            text: 'Id'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: 'Name',
                            filter: {
                                type: 'string'
                            }
                        }
                    ],
                    plugins: [
                        {
                            ptype: 'gridfilters'
                        }
                    ]
                }
            ]
        }
    ],

    onMenuClick: function(menu, item, e, eOpts) {
        location.hash = item.itemId;
    }

});