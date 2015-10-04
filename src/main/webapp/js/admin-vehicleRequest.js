Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    var VehicleBean = Ext.data.Record.create([
    {
        name: 'id'
    },

    {
        name: 'address',
        type: 'string'
    },
    {
        name: 'requestor',
        type: 'string'
    },{
        name: 'model',
        type: 'string'
    }, {
        name: 'color',
        type: 'string'
    }, {
        name: 'plateNumber',
        type: 'string'
    },{
        name: 'sticker',
        type: 'string'
    }, {
        name: 'status',
        type: 'string'
    }, {
        name: 'verified',
        type: 'string'
    }]);
    
    var vproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/vehicle/view.action'
        }
    });
    
    var vreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    VehicleBean);
    

    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    var vstore = new Ext.data.Store({
        id: 'user',
        proxy: vproxy,
        reader: vreader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    vstore.load();
    
    
    var veditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
    var vgrid = new Ext.FormPanel({
        frame: true,
        title: 'VEHICLE REQUEST',
        bodyPadding: 5,
        width: 700,
        id: 'vehicle-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns

        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        
        items:[{
            columnWidth: 0.65,
            xtype: 'grid',
            id:'vgrid',
            store: vstore,
            cls:'x-panel-blue',
            width: 400,
            height:400,
            title:'VEHICLE REQUEST',
            columns: [
             {
            header: "ID",
            width: 200,
            sortable: true,
            dataIndex: 'id',
            hidden:true
            },    
            {
            header: "STATUS",
            width:100,
            sortable: true,
            dataIndex:'status',
            renderer:customRenderer,
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['On Process', 'On Process'],
                    ['For Pick Up', 'For Pick Up'],
                    ['Done', 'Done'],
                    ['Cancelled', 'Cancelled']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                allowBlank:false,
                emptyText: 'Select action'
            }
        },    
        {
            header: "ADDRESS",
            width: 200,
            sortable: true,
            dataIndex: 'address'
        },{
            header: "REQUESTOR",
            width: 200,
            sortable: true,
            dataIndex: 'requestor'
        },
        {
            header: "MODEL",
            width: 170,
            sortable: true,
            dataIndex: 'model'
        },

        {
            header: "COLOR",
            width: 150,
            sortable: true,
            dataIndex: 'color'
        },

        {
            header: "PLATE NUMBER",
            width: 150,
            sortable: true,
            dataIndex: 'plateNumber'
        },
        {
            header: "STICKER",
            width:100,
            sortable: true,
            dataIndex:'sticker',
            editor: {
                xtype: 'textfield'
            }
        },
        {
            header: "VERIFIED",
            width:100,
            sortable: true,
            dataIndex:'verified',
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Yes', 'Yes'],
                    ['No', 'No']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                allowBlank:false,
                emptyText: 'Select action'
            }
        }
        ],
        listeners:{
              'rowclick':  function(grid,rowIndex,e){
                  var record = grid.getStore().getAt(rowIndex);
                  var id = record.get('id');
                  var status = record.get('status');
                  var address = record.get('address')
                  var requestor = record.get('requestor')
                  var model = record.get('model')
                  var color = record.get('color')
                  var plateNumber = record.get('plateNumber')
                  var verified = record.get('verified')
                  Ext.getCmp('veh-id').setValue(id)
                  Ext.getCmp('veh-address').setValue(address)
                  Ext.getCmp('veh-requestor').setValue(requestor)
                  Ext.getCmp('veh-status').setValue(status)
                  Ext.getCmp('veh-model').setValue(model)
                  Ext.getCmp('veh-color').setValue(color)
                  Ext.getCmp('veh-plateNumber').setValue(plateNumber)
                  Ext.getCmp('veh-verified').setValue(verified)
              }
        }
        },{
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Vehicle Sticker Request details',
            height:485,
            
            defaults: {
                width:150,
                labelWidth: 150,
                labelAlign:'center'
            },
            defaultType: 'textfield',
            items:[
                {
                fieldLabel: 'ID',
                name: 'id',
                id: 'veh-id',
                anchor: '85%',
                hidden:true
                },
                {
                fieldLabel: 'STATUS',
                name: 'status',
                xtype:'combo',
                id: 'veh-status',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['On Process', 'On Process'],
                    ['For Pick Up', 'For Pick Up'],
                    ['Done', 'Done'],
                    ['Cancelled', 'Cancelled']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                allowBlank:false,
                emptyText: 'Select action',
                anchor: '85%'
                },
                {
                fieldLabel: 'ADDRESS',
                name: 'address',
                id: 'veh-address',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'REQUESTOR',
                name: 'requestor',
                id: 'veh-requestor',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'MODEL',
                name: 'model',
                id: 'veh-model',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'COLOR',
                name: 'color',
                id: 'veh-color',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'PLATE NUMBER',
                name: 'plateNumber',
                id: 'veh-plateNumber',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'VERIFIED',
                name: 'verified',
                id: 'veh-verified',
                anchor: '85%',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Yes', 'Yes'],
                    ['No', 'No']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                editable:false,
                allowBlank:false,
                emptyText: 'Select action'
                }
            ],
            buttons:[{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('vehicle-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/vehicle/update.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Vehicle Sticker Request');
                                
                                vstore.load();
                                fnResetForm(vgrid);
                            },
                            failure: function(form, action) {
                                Ext.Msg.alert('Warning', 'Error in updating Vehicle Sticker Request');
                            }
                        })
                    }
                }
            },{
                text: 'Reset',
                disabled: false,
                handler: function() {
                    fnResetForm(vgrid);
                }
            }]
        }]
    })
    
    
   
    var treeDivs = '<div id="amenity-tree-div"></div>' +
    '<div id="service-tree-div"></div>' +
    '<div id="employee-tree-div"></div>' +
    '<div id="vehicle-tree-div"></div>';

    var adminBorderPanel = new Ext.Panel({
        renderTo: 'my-tabs',
        width: 1200,
            height: 530,
        title: 'Portal Admin Menu',
        layout:'border',
        defaults: {
            collapsible: true,
            split: true,
            bodyStyle: 'padding:15px'
        },
        items: [{
            title: 'Alerts List',
            region:'west',
            margins: '5 0 0 0',
            cmargins: '5 5 0 0',
            width: 300,
            minSize: 100,
            maxSize: 250,
            html: treeDivs
        },{
            title: 'Main Content',
            collapsible: false,
            region:'center',
            margins: '5 0 0 0',
            xtype: 'tabpanel',
            activeTab: 0, // index or id
            //items: [argrid,srgrid,vgrid,eGrid],
            items: [vgrid]
        }]
    });
    
    

    //Amenity Tree
    var amenityTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
        loader : new Ext.tree.TreeLoader({
            url : 'alert/amenity/view.action'
            
        }),
        root : {
            text : 'Amenity Requests',
            id : 'data',
            cls: 'folder',
            expanded : true
        },
        renderTo: 'amenity-tree-div'
    });

    //Service Tree
    var serviceTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
        loader : new Ext.tree.TreeLoader({
            url : 'alert/service/view.action'
        }),
        root : {
            text : 'Service Requests',
            id : 'data',
            cls: 'folder',
            expanded : true
        },
        renderTo: 'service-tree-div'
    });

    //Employee Tree
    var employeeTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
        loader : new Ext.tree.TreeLoader({
            url : 'alert/employee/view.action'
        }),
        root : {
            text : 'Employee Updates',
            id : 'data',
            cls: 'folder',
            expanded : true
        },
        listeners:{
             "click": function(node){
                 //alert(node)
             }
        },
        renderTo: 'employee-tree-div'
    });

    //Vehicle Tree
    var vehicleTree = new Ext.tree.TreePanel({
        xtype : 'treepanel',
        autoScroll : true,
        loader : new Ext.tree.TreeLoader({
            url : 'alert/vehicle/view.action'
        }),
        root : {
            text : 'Vehicle Updates',
            id : 'data',
            cls: 'folder',
            expanded : true
        },
        renderTo: 'vehicle-tree-div'
    });

});

function customRenderer(value, metaData, record, rowIndex, colIndex, store) {
    var opValue = value;
    
    if (value === "New") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "On Process") {
        metaData.css = 'orangeUnderlinedText';
    }else if (value === "For Pick Up") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Reserved") {
        metaData.css = 'blueUnderlinedText';
    }else if (value === "Booked") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Cancelled") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Confirmed") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "Re-Scheduled") {
        metaData.css = 'orangeUnderlinedText';
    }else if (value === "Decline") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Done") {
        metaData.css = 'greenUnderlinedText';
    }else if (value === "ReIssue") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Renew") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Sold") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Change Request") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Update Profile") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "End of Contract") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Cancel") {
        metaData.css = 'redUnderlinedText';
    }else if (value === "Re-Schedule") {
        metaData.css = 'redUnderlinedText';
    }
    return opValue;
}

function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('vgrid').getStore().load()
}