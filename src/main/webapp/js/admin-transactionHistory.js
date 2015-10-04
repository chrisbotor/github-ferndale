Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    var TransactionBean = Ext.data.Record.create([
    {
        name: 'job_order_id',
        type: 'int'
    },{
        name: 'referenceNumber',
        type: 'string'
    },{
        name: 'order_type_id',
        type: 'int'
    },{
        name: 'req_id',
        type: 'int'
    },{
        name: 'userId',
        type: 'int'
    },{
        name: 'requestId',
        type: 'string'
    },{
        name: 'description',
        type: 'string'
    },{
        name: 'requestedDate',
        type: 'string'
    },{
        name: 'postedDate',
        type: 'string'
    },{
        name: 'quantity',
        type: 'int'
    },{
        name: 'basic_cost',
        type: 'float'
    },{
        name: 'additional_cost',
        type: 'float'
    },{
        name: 'otherCharges',
        type: 'float'
    },{
        name: 'total_cost',
        type: 'float'
    }]);

    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/viewTransaction.action'
        }
    });

    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
   TransactionBean);

   
    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

    // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        writer: writer,
        reader: reader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    store.load();
    

    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.responseText,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });


    //var areditor = new Ext.ux.grid.RowEditor({
    //saveText: 'Update'
    //});
   
    var argrid = new Ext.FormPanel({
        frame: true,
        title: 'TRANSACTION HISTORY',
        bodyPadding: 5,
        width: 700,
        id: 'transaction-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns

        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        
        items: [{
            columnWidth: 0.65,
            xtype: 'grid',
            id:'txn',
            store: store,
            cls:'x-panel-blue',
            width: 400,
            height:400,
            title:'TRANSACTION HISTORY',
            
            columns: [
            {
                header: "JOB ORDER ID",
                width:100,
                sortable: true,
                hidden:true,
                dataIndex:'job_order_id'
            },{
                header: "ORDER TYPE ID",
                width:100,
                sortable: true,
                hidden:true,
                dataIndex:'order_type_id'
            },{
                header: "ID",
                width:100,
                sortable: true,
                hidden:true,
                dataIndex:'req_id'
            },{
                header: "USER ID",
                width:100,
                sortable: true,
                hidden:true,
                dataIndex:'userId'
            },{
                header: "REFERENCE NUMBER",
                width:100,
                sortable: true,
                dataIndex:'referenceNumber'
            }, {
                header: "REQUEST ID",
                width:100,
                sortable: true,
                dataIndex:'requestId'
            },
            {
                header: "DESCRIPTION",
                width: 200,
                sortable: true,
                dataIndex: 'description'
            },{
                header: "REQUESTED DATE",
                width: 170,
                sortable: true,
                dataIndex: 'requestedDate'
            },{
                header: "POSTED DATE",
                width: 150,
                sortable: true,
                dataIndex: 'postedDate'
            },{
                header: "QUANTITY",
                width: 170,
                sortable: true,
                dataIndex: 'quantity'
            },
            {
                header: "BASIC COST",
                width: 170,
                sortable: true,
                dataIndex: 'basic_cost'
            }, {
                header: "ADDITIONAL COST",
                width: 170,
                sortable: true,
                dataIndex: 'additional_cost'
            },{
                header: "OTHER CHARGES",
                width: 170,
                sortable: true,
                dataIndex: 'otherCharges'
            },{
                header: "TOTAL COST",
                width: 170,
                sortable: true,
                dataIndex: 'total_cost'
            }
            ],
            listeners: {
                'rowclick':  function(grid,rowIndex,e){
                    
                    var record = grid.getStore().getAt(rowIndex);
                    var job_order_id = record.get('job_order_id');
                    var order_type_id = record.get('order_type_id');
                    var id = record.get('req_id');
                    var userId = record.get('userId');
                    var referenceNumber = record.get('referenceNumber');
                    var requestId = record.get('requestId');
                    var description = record.get('description')
                    var requestedDate = record.get('requestedDate')
                    var postedDate = record.get('postedDate')
                    var quantity = record.get('quantity')
                    var basic_cost = record.get('basic_cost')
                    var additional_cost = record.get('additional_cost')
                    var otherCharges = record.get('otherCharges')
                    var total_cost = record.get('total_cost')
                    
                    Ext.getCmp('txn-job_order_id').setValue(job_order_id)
                    Ext.getCmp('txn-order_type_id').setValue(order_type_id)
                    Ext.getCmp('txn-id').setValue(id)
                    Ext.getCmp('txn-referenceNumber').setValue(referenceNumber)
                    Ext.getCmp('txn-requestId').setValue(requestId)
                    Ext.getCmp('txn-description').setValue(description)
                    Ext.getCmp('txn-userId').setValue(userId)
                    Ext.getCmp('txn-quantity').setValue(quantity)
                    Ext.getCmp('txn-requestedDate').setValue(requestedDate)
                    Ext.getCmp('txn-postedDate').setValue(postedDate)
                    Ext.getCmp('txn-basic_cost').setValue(basic_cost)
                    Ext.getCmp('txn-additional_cost').setValue(additional_cost)
                    Ext.getCmp('txn-otherCharges').setValue(otherCharges)
                    Ext.getCmp('txn-total_cost').setValue(total_cost)
                }
            }
        },
        {
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Transaction History',
            height:450,
            
            defaults: {
                width:200,
                labelWidth: 200,
                labelAlign:'center'
            },
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'JOB ORDER ID',
                name: 'job_order_id',
                id: 'txn-job_order_id',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'ORDER TYPE ID',
                name: 'order_type_id',
                id: 'txn-order_type_id',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'ID',
                name: 'req_id',
                id: 'txn-id',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'USER ID',
                name: 'userId',
                id: 'txn-userId',
                anchor: '85%',
                hidden:true
            },{
                fieldLabel: 'REFERENCE NUMBER',
                name: 'referenceNumber',
                id: 'txn-referenceNumber',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'REQUEST ID',
                name: 'requestId',
                id: 'txn-requestId',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'DESCRIPTION',
                name: 'description',
                id: 'txn-description',
                anchor: '85%',
                readOnly:true
            },{
                fieldLabel: 'REQUESTED DATE',
                name: 'requestedDate',
                cls: 'cTextAlign', 
                readOnly:true,
                id: 'txn-requestedDate',
                anchor: '85%'
            },{
                fieldLabel: 'POSTED DATE',
                name: 'postedDate',
                cls: 'cTextAlign',
                id: 'txn-postedDate',
                anchor: '85%'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'QUANTITY',
                name: 'quantity',
                anchor: '85%',
                allowBlank: false,
                id: 'txn-quantity'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'BASIC COST',
                name: 'basic_cost',
                anchor: '85%',
                allowBlank: false,
                id: 'txn-basic_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'ADDITIONAL COST',
                name: 'additional_cost',
                anchor: '85%',
                allowBlank: false,
                id: 'txn-additional_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'OTHER CHARGES',
                name: 'otherCharges',
                anchor: '85%',
                allowBlank: false,
                id: 'txn-otherCharges'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'TOTAL COST',
                name: 'total_cost',
                anchor: '85%',
                allowBlank: false,
                id: 'txn-total_cost'
            }], 
            buttons: [{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('transaction-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/update/amenities.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully Transaction History');
                                
                                var grid = Ext.getCmp('arg')
                                store.load();
                                fnResetForm(argrid);
                            },
                            failure: function(form, action) {
                                Ext.Msg.alert('Warning', 'Error in updating Transaction History');
                            }
                        })
                    }
                }
            },{
                text: 'Reset',
                disabled: false,
                handler: function() {
                    fnResetForm(argrid);
                }
            }]
        }]
    
    });
    
   
    var treeDivs = '<div id="amenity-tree-div"></div>' +
    '<div id="service-tree-div"></div>' +
    '<div id="employee-tree-div"></div>' +
    '<div id="vehicle-tree-div"></div>';

    var adminBorderPanel = new Ext.Panel({
        renderTo: 'my-tabs',
        width: 1340,
        height: 560,
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
            items: [argrid]
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



function fnAdminAmenitiesUpdateForm(theForm)
{
    //if(theForm.isValid()){
    theForm.getForm().submit({
        //waitMsg:'Loading...',
        //update: 'admin/update/amenities.action' "/admin/update/amenities.action
        url: 'admin/update/amenities.action',
        
        waitMsg: 'loading...',
        success: function(form, action) {
            Ext.Msg.alert('Success', 'Successfully updated Amenities Request');
        },
        failure: function(form, action) {
            Ext.Msg.alert('Warning', form.name);
        }
    });
} 

function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('argrid').getStore().load()
}