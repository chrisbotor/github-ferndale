Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    //var hId = Ext.String.format(document.getElementById("houseIds").value);

    var Amenity = Ext.data.Record.create([
    {
        name: 'id'
    },
    {
        name: 'code',
        type: 'string'
    },
    {
        name: 'description',
        type: 'string'
    },
    {
        name: 'reg_value',
        type: 'float'
    },{
        name: 'excess_value',
        type: 'float'
    },{
        name: 'uom',
        type: 'string'
    },{
        name: 'max_regular',
        type: 'int'
    }]);


    var Services = Ext.data.Record.create([
    {
        name: 'id'
    },
    {
        name: 'code',
        type: 'string'
    },
    {
        name: 'description',
        type: 'string'
    },
    {
        name: 'reg_value',
        type: 'float'
    },{
        name: 'excess_value',
        type: 'float'
    },{
        name: 'uom',
        type: 'string'
    },{
        name: 'max_regular',
        type: 'int'
    }]);

    var OrSeries = Ext.data.Record.create([
        {
            name: 'id'
        },
        {
            name: 'startSeries',
            type: 'int'
        },{
            name: 'endSeries',
            type: 'int'
        },{
            name: 'current',
            type: 'int'
        }
        ]);


    var proxy = new Ext.data.HttpProxy({
        api: {
            read:'amenity/load.action',
            create : 'amenity/create.action',
            update: 'amenity/update.action',
            destroy: 'amenity/delete.action'
        }
    });

    var serproxy = new Ext.data.HttpProxy({
        api: {
            read:'services/view.action',
            create : 'services/create.action',
            update: 'services/update.action',
            destroy: 'services/delete.action'
        }
    });
    
    var orproxy = new Ext.data.HttpProxy({
        api: {
            read : 'loadOrSeries.action'
        }
    });


    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Amenity);

    var serreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Services);
    
    var orreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    OrSeries);


    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

     // The new DataWriter component.
    var serwriter = new Ext.data.JsonWriter({
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

    // Typical Store collecting the Proxy, Reader and Writer together.
    var serstore = new Ext.data.Store({
        id: 'user',
        proxy: serproxy,
        writer: serwriter,
        reader: serreader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
    var orstore = new Ext.data.Store({
        id: 'user',
        proxy: orproxy,
        reader: orreader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    store.load();
    serstore.load();
    orstore.load();

    //Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        //Ext.Msg.show({
            //title: 'ERROR',
            //msg: res.message,
            //icon: Ext.MessageBox.ERROR,
            //buttons: Ext.Msg.OK
        //});
    //});


    var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var sereditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
     var oreditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    // create grid
    var grid = new Ext.grid.GridPanel({ // RACS
        store: store,
        columns: [
        {
            header: "ID",
            width: 100,
            hidden:true,
            sortable: true,
            dataIndex: 'id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },
        {
            header: "CODE",
            width: 170,
            sortable: true,
            dataIndex: 'code',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "DESCRIPTION",
            width: 150,
            sortable: true,
            dataIndex: 'description',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "REGULAR VALUE",
            width: 150,
            sortable: true,
            dataIndex: 'reg_value',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "EXCESS VALUE",
            width: 150,
            sortable: true,
            dataIndex: 'excess_value',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "MAXIMUM REGULAR",
            width: 150,
            sortable: true,
            dataIndex: 'max_regular',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "UNIT OF MEASUREMENT",
            width:150,
            sortable: true,
            dataIndex:'uom',
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['pc', 'pc'],
                    ['hr', 'hr'],
                    ['hph', 'hph'],
                    ['day', 'day']
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
         viewConfig: {
            forceFit:true
        },
        // inline buttons
        plugins: [editor],
        width:400,
        height:300,
        frame:true,
        title:'AMENITY',
        tbar: [{
            iconCls: 'icon-user-add',
            text: 'Add Amenity',
            handler: function(){
                var e = new Amenity({
                    code:'',
                    description:'',
                    reg_value:'',
                    excess_value:'',
                    uom:''
                });
                editor.stopEditing();
                store.insert(0, e);
                grid.getView().refresh();
                grid.getSelectionModel().selectRow(0);
                editor.startEditing(0);
            }
        },{
            iconCls: 'icon-user-delete',
            text: 'Remove Amenity',
            handler: function(){
                editor.stopEditing();
                var s = grid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    store.remove(r);
                }
            }
        },{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                store.save();
            }
        }]
    //renderTo: document.body
    });


    // create grid
    var sergrid = new Ext.grid.GridPanel({
        store: serstore,
        columns: [
        {
            header: "ID",
            width: 100,
            hidden:true,
            sortable: true,
            dataIndex: 'id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },
        {
            header: "CODE",
            width: 170,
            sortable: true,
            dataIndex: 'code',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "DESCRIPTION",
            width: 150,
            sortable: true,
            dataIndex: 'description',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "REGULAR VALUE",
            width: 150,
            sortable: true,
            dataIndex: 'reg_value',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "EXCESS VALUE",
            width: 150,
            sortable: true,
            dataIndex: 'excess_value',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "MAXIMUM REGULAR",
            width: 150,
            sortable: true,
            dataIndex: 'max_regular',
            editor: {
                xtype: 'textfield'
            }
        },{
            header: "UNIT OF MEASUREMENT",
            width:150,
            sortable: true,
            dataIndex:'uom',
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['pc', 'pc'],
                    ['hr', 'hr'],
                    ['hph', 'hph'],
                    ['day', 'day']
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
         viewConfig: {
            forceFit:true
        },
        // inline buttons
        plugins: [sereditor],
        width:400,
        height:300,
        frame:true,
        title:'SERVICE',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-add',
            text: 'Add Service',
            handler: function(){
                var e = new Services({
                    code:'',
                    description:'',
                    value:''
                });
                sereditor.stopEditing();
                serstore.insert(0, e);
                sergrid.getView().refresh();
                sergrid.getSelectionModel().selectRow(0);
                sereditor.startEditing(0);
            }
        },{
            iconCls: 'icon-user-delete',
            text: 'Remove Service',
            handler: function(){
                sereditor.stopEditing();
                var s = sergrid.getSelectionModel().getSelections();
                for(var i = 0, r; r = s[i]; i++){
                    serstore.remove(r);
                }
            }
        },{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                serstore.save();
            }
        }]
    //renderTo: document.body
    });
    
     var orgrid = new Ext.grid.GridPanel({
        store: orstore,
        columns: [
        {
            header: "ID",
            width: 100,
            hidden:true,
            sortable: true,
            dataIndex: 'id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
        },
        {
            header: "START SERIES",
            width: 170,
            sortable: true,
            dataIndex: 'startSeries',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "END SERIES",
            width: 150,
            sortable: true,
            dataIndex: 'endSeries',
            editor: {
                xtype: 'textfield'
            }
        },

        {
            header: "CURRENT",
            width: 150,
            sortable: true,
            dataIndex: 'current',
            editor: {
                xtype: 'textfield'
            }
        }
        ],
        viewConfig: {
            forceFit:true
        },
        // inline buttons
        plugins: [oreditor],
        height: 500,
        width: 300,
        frame:true,
        cls:'x-panel-blue',
        title:'OR SERIES',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                orstore.save();
            }
        }]
    //renderTo: document.body
    });

    
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
	        title: 'Main Content',
	        collapsible: false,
	        region:'center',
	        margins: '5 0 0 0',
	        xtype: 'tabpanel',
	        activeTab: 0, // index or id
	         items: [grid,sergrid]
	    }]
	});
});