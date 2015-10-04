Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    var OrSeries = Ext.data.Record.create([
        {
            name: 'id',
            type: 'int'
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
    
    var orproxy = new Ext.data.HttpProxy({
        api: {
            read : 'loadOrSeries.action',
            update: 'updateOrSeries.action'
        }
    });
    
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
   
    var orstore = new Ext.data.Store({
        id: 'user',
        proxy: orproxy,
        reader: orreader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    orstore.load();

    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });
    
     var oreditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    // create grid
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
                xtype: 'numberfield',
                readOnly: true
            }
        },
        {
            header: "START SERIES",
            width: 170,
            sortable: true,
            dataIndex: 'startSeries',
            editor: {
                xtype: 'numberfield'
            }
        },

        {
            header: "END SERIES",
            width: 150,
            sortable: true,
            dataIndex: 'endSeries',
            editor: {
                xtype: 'numberfield'
            }
        },

        {
            header: "CURRENT",
            width: 150,
            sortable: true,
            dataIndex: 'current'
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
	         items: [orgrid]
	    }]
	});

});