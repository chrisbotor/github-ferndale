Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';


    var EmployeeBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },{
        name: 'address',
        type: 'string'
    },{
        name: 'lastname',
        type: 'string'
    },{
        name: 'requestor',
        type: 'string'
    },
    {
        name: 'firstname',
        type: 'string'
    },
    {
        name: 'middlename',
        type: 'string'
    },
    {
        name: 'civilStatus',
        type: 'string'
    },
    {
        name: 'position',
        type: 'string'
    },{
        name: 'inhouse',
        type: 'string'
    },{
        name: 'birthdate',
        type: 'string'
    },{
        name: 'startdate',
        type: 'string'
    },{
        name: 'enddate',
        type: 'string'
    },{
        name: 'status',
        type: 'string'
    }, {
        name: 'verified',
        type: 'string'
    }]);
    
    var eproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/employee/view.action'
        }
    });
    
    var ereader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    EmployeeBean);


    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    var estore = new Ext.data.Store({
        id: 'user',
        proxy: eproxy,
        reader: ereader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    
    estore.load();
    
    var eeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
    var eGrid = new Ext.FormPanel({
        frame: true,
        title: 'EMPLOYEE REQUEST',
        bodyPadding: 5,
        width: 700,
        id: 'employee-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns

        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        items:[
        {
            columnWidth: 0.65,
            xtype: 'grid',
            id:'egrid',
            store: estore,
            cls:'x-panel-blue',
            width: 400,
            height:400,
            title:'EMPLOYEE REQUEST',
            columns: [
            {
                header: "STATUS",
                width:100,
                renderer:customRenderer,
                sortable: true,
                dataIndex: 'status',
            
                editor: {
                    xtype:'combo',
                    queryMode: 'server',
                    store: new Ext.data.ArrayStore({
                        fields: ['abbr', 'action'],
                        data : [
                        ['On Process', 'On Process'],    
                        ['Done', 'Done'],
                        ['Decline', 'Decline'],
                        ['Cancelled', 'Cancelled']
                        ]
                    }),
                    displayField:'action',
                    valueField: 'abbr',
                    mode: 'local',
                    editable:false,
                    typeAhead: false,
                    triggerAction: 'all',
                    lazyRender: true,
                    emptyText: 'Select action'
                }
            },
            {
                header: "ID",
                width: 50,
                hidden:true,
                sortable: true,
                dataIndex: 'id',
                editor: {
                    xtype: 'textfield',
                    readOnly: true
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
                header: "LAST NAME",
                width: 100,
                sortable: true,
                dataIndex: 'lastname'
            },
            {
                header: "FIRST NAME",
                width:100,
                sortable: true,
                dataIndex: 'firstname'
            },
            {
                header: "MIDDLE NAME",
                width:100,
                sortable: true,
                dataIndex: 'middlename'
            },{
                header: "BIRTH DATE",
                width: 150,
                sortable: true,
                xtype: 'datecolumn',
                dataIndex: 'birthdate'
            },{
                header: "CIVIL STATUS",
                width:100,
                sortable: true,
                dataIndex: 'civilStatus'
            },
            {
                header: "POSITION",
                width:100,
                sortable: true,
                dataIndex: 'position'
            },
            {
                header: "IN HOUSE",
                width:100,
                sortable: true,
                dataIndex: 'inhouse'
            },{
                header: "START DATE",
                width: 150,
                sortable: true,
                xtype: 'datecolumn',
                dataIndex: 'startdate'
            },{
                header: "END DATE",
                width: 150,
                sortable: true,
                xtype: 'datecolumn',
                dataIndex: 'enddate'
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
                    emptyText: 'Select action',
                    allowBlank: false,
                    blankText: 'Verified should not be null'
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
                    var lastName = record.get('lastname')
                    var firstName = record.get('firstname')
                    var middleName = record.get('middlename')
                    var birthDate = record.get('birthdate')
                    var civilStatus = record.get('civilStatus')
                    var position = record.get('position')
                    var inHouse = record.get('inhouse')
                    var startDate = record.get('startdate')
                    var endDate = record.get('enddate')
                    var verified = record.get('verified')
                    
                    Ext.getCmp('emp-id').setValue(id)
                    Ext.getCmp('emp-address').setValue(address)
                    Ext.getCmp('emp-requestor').setValue(requestor)
                    Ext.getCmp('emp-status').setValue(status)
                    Ext.getCmp('emp-lastName').setValue(lastName)
                    Ext.getCmp('emp-firstName').setValue(firstName)
                    Ext.getCmp('emp-middleName').setValue(middleName)
                    Ext.getCmp('emp-birthDate').setValue(birthDate)
                    Ext.getCmp('emp-civilStatus').setValue(civilStatus)
                    Ext.getCmp('emp-position').setValue(position)
                    Ext.getCmp('emp-inHouse').setValue(inHouse)
                    Ext.getCmp('emp-startDate').setValue(startDate)
                    Ext.getCmp('emp-endDate').setValue(endDate)
                    Ext.getCmp('emp-verified').setValue(verified)
                }
            }
        },
        {
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Employee Request details',
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
                id: 'emp-id',
                anchor: '85%',
                hidden:true
                },
                {
                fieldLabel: 'STATUS',
                name: 'status',
                xtype:'combo',
                id: 'emp-status',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['On Process', 'On Process'],    
                    ['Done', 'Done'],
                    ['Decline', 'Decline']
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
                id: 'emp-address',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'REQUESTOR',
                name: 'requestor',
                id: 'emp-requestor',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'LASTNAME',
                name: 'lastname',
                id: 'emp-lastName',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'FIRSTNAME',
                name: 'firstname',
                id: 'emp-firstName',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'MIDDLENAME',
                name: 'middlename',
                id: 'emp-middleName',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'BIRTHDATE',
                name: 'birthdate',
                id: 'emp-birthDate',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'CIVIL STATUS',
                name: 'civilStatus',
                id: 'emp-civilStatus',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'POSITION',
                name: 'position',
                id: 'emp-position',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'IN HOUSE',
                name: 'inhouse',
                id: 'emp-inHouse',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'START DATE',
                name: 'startdate',
                id: 'emp-startDate',
                anchor: '85%',
                readOnly:true
                },
                 {
                fieldLabel: 'END DATE',
                name: 'enddate',
                id: 'emp-endDate',
                anchor: '85%',
                readOnly:true
                },
                {
                fieldLabel: 'VERIFIED',
                name: 'verified',
                id: 'emp-verified',
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
            ],buttons:[{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('employee-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/employee/update.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Employee Authorization Request');
                                estore.load();
                                fnResetForm(eGrid);
                            },
                            failure: function(form, action) {
                                Ext.Msg.alert('Warning', 'Error in updating Employee Authorization Request');
                            }
                        })
                    }
                }
            },{
                text: 'Reset',
                disabled: false,
                handler: function() {
                    fnResetForm(eGrid);
                }
            }]
        }
        ]
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
            items: [eGrid]
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

function fnAdminAmenitiesUpdateForm(theForm)
{
    //if(theForm.isValid()){
    theForm.getForm().submit({
        //waitMsg:'Loading...',
        //update: 'admin/update/amenities.action' "/admin/update/amenities.action
        url: 'admin/update/amenities.action',
        
        waitMsg: 'loading...',
        success: function(form, action) {
        	removeXButton(Ext.Msg.alert('Success', 'Successfully updated Employee Request'));
        },
        failure: function(form, action) {
            Ext.Msg.alert('Warning', form.name);
        }
    });
//}
} //end fnUpdateForm


function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('eGrid').getStore().load()
}