Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);
    var hId = 2;
    var Vehicle = Ext.data.Record.create([
    {
        name: 'id'
    },

    {
        name: 'houseId',
        type: 'int'
    },
    {
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

    
    var Employee = Ext.data.Record.create([
    {
        name: 'id'
    },

    {
        name: 'houseId',
        type: 'int'
    },
    {
        name: 'lastname',
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
        name: 'position',
        type: 'string'
    },{
        name: 'inhouse',
        type: 'string'
    },{
        name: 'status',
        type: 'string'
    }, {
        name: 'verified',
        type: 'string'
    }]);
    
    var vproxy = new Ext.data.HttpProxy({
        api: {
            read : 'vehicle/view.action',
            create : 'vehicle/create.action',
            update: 'vehicle/update.action',
            destroy: 'vehicle/delete.action'
        }
    });
    
    
    
    var eproxy = new Ext.data.HttpProxy({
        api: {
            read : 'employee/view.action',
            create : 'employee/create.action',
            update: 'employee/update.action',
            destroy: 'employee/delete.action'
        }
    });
    
    var vreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Vehicle);
    
    
    
    var ereader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    }, 
    Employee);

    // The new DataWriter component.
    var vwriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
   
    
     // The new DataWriter component.
    var ewriter = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    // Typical Store collecting the Proxy, Reader and Writer together.
    var vstore = new Ext.data.Store({
        id: 'user',
        proxy: vproxy,
        reader: vreader,
        writer: vwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
   
    
    // Typical Store collecting the Proxy, Reader and Writer together.
    var estore = new Ext.data.Store({
        id: 'user',
        proxy: eproxy,
        reader: ereader,
        writer: ewriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    vstore.load();
    estore.load();
    
    Ext.data.DataProxy.addListener('exception', function(vproxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });

    
    var veditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
   
     var eeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
    // create grid
    var vgrid = new Ext.grid.GridPanel({
        store: vstore,
        columns: [
        {
            header: "MODEL",
            width: 170,
            sortable: true,
            dataIndex: 'model',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },

        {
            header: "COLOR",
            width: 150,
            sortable: true,
            dataIndex: 'color',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },

        {
            header: "PLATE NUMBER",
            width: 150,
            sortable: true,
            dataIndex: 'plateNumber',
            editor: {
                xtype: 'textfield',
                allowBlank: false
            }
        },
        {
            header: "STICKER",
            width:100,
            sortable: true,
            dataIndex:'sticker',
            editor: {
                xtype: 'textfield',
                readOnly:true
            }
        },
        {
            header: "STATUS",
            width:100,
            sortable: true,
            dataIndex:'status',
            editor: {
                xtype: 'textfield',
                readOnly:true
            }
        },
        {
            header: "VERIFIED",
            width:100,
            sortable: true,
            dataIndex:'verified',
            editor: {
                xtype: 'textfield',
                readOnly:true
            }
        }
        ],
        plugins: [veditor],
        title: 'My Vehicle',
        height: 300,
        width:610,
        frame:true,
        tbar: [{
            iconCls: 'icon-user-add',
            text: 'Add Vehicle',
            handler: function(){
                var e = new Vehicle({
                    houseId:hId,
                    model: '',
                    color: '',
                    plateNumber: '',
                    sticker:'New',
                    status:'New',
                    verified:'New'
                });
                veditor.stopEditing();
                vstore.insert(0, e);
                vgrid.getView().refresh();
                vgrid.getSelectionModel().selectRow(0);
                veditor.startEditing(0);
            }
        },{
            iconCls: 'icon-user-delete',
            text: 'Remove Vehicle',
            handler: function(){
                veditor.stopEditing();
                var s = vgrid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    vstore.remove(r);
                }
            }
        },{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                vstore.save();
            }
        }]
    });


   
    //create employee grid
    var eGrid = new Ext.grid.GridPanel({
        store: estore,
        columns: [
        {
            header: "ID",
            width: 50,
            hidden:true,
            sortable: true,
            dataIndex: 'Id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },

        {
        header: "HOUSE ID",
        width: 50,
        hidden:true,
        sortable: true,
        dataIndex: 'houseId',
        editor: {
            xtype: 'textfield',
            readOnly: true
        }
    },

    {
        header: "LAST NAME",
        width: 100,
        sortable: true,
        dataIndex: 'lastname',
        editor: {
            xtype: 'textfield',
            allowBlank: false
        }
    },
{
    header: "FIRST NAME",
    width:100,
    sortable: true,
    dataIndex: 'firstname',
    editor: {
        xtype: 'textfield',
        allowBlank: false
    }
},
{
    header: "MIDDLE NAME",
    width:100,
    sortable: true,
    dataIndex: 'middlename',
    editor: {
        xtype: 'textfield',
        allowBlank: false
    }
},
{
    header: "POSITION",
    width:100,
    sortable: true,
    dataIndex: 'position',
    editor: {
        xtype: 'textfield',
        allowBlank: false
    }
},
{
    header: "IN HOUSE",
    width:100,
    sortable: true,
    dataIndex: 'inhouse',
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
        emptyText: 'Select action'
    }
},
{
    header: "STATUS",
    width:100,
    sortable: true,
    dataIndex: 'status',
    editor: {
        xtype:'combo',
        queryMode: 'server',
        store: new Ext.data.ArrayStore({
            fields: ['abbr', 'action'],
            data : [
            ['Active', 'Active'],
            ['In Active', 'In Active']
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
            header: "VERIFIED",
            width:100,
            sortable: true,
            dataIndex:'verified',
            editor: {
        xtype: 'textfield',
        allowBlank: true,
        readOnly:true
    }
        }
],
plugins: [eeditor],
title: 'My Employee',
height: 300,
width:610,
frame:true,
tbar: [{
    iconCls: 'icon-user-add',
    text: 'Add Employee',
    handler: function(){
        var e = new Employee({
            houseId:hId,
            lastname: '',
            firstname: '',
            middlename: '',
            position:'',
            inhouse:'',
            status:''
        });
        eeditor.stopEditing();
        estore.insert(0, e);
        eGrid.getView().refresh();
        eGrid.getSelectionModel().selectRow(0);
        eeditor.startEditing(0);
    }
}
,{
    iconCls: 'icon-user-delete',
    text: 'Remove Employee',
    handler: function(){
        eeditor.stopEditing();
        var s = eGrid.getSelectionModel().getSelections();
        for(var i = 0, r; r = s[i]; i++){
            estore.remove(r);
        }
    }
}
,{
    iconCls: 'icon-user-save',
    text: 'Save All Modifications',
    handler: function(){
        estore.save();
    }
}]
});

    
var myTabPanel = new Ext.TabPanel({
    title:"MY TAB",
    renderTo: 'my-tabs',
    fullScreen:true,
    activeItem: 0,
    items: [vgrid,eGrid]
});

});

