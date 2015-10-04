Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    var Rates = Ext.data.Record.create(
    [
        {	name: 'id', type: 'int'	},
        {	name: 'code', type: 'string' },
        {	name: 'name', type: 'string' },
        {	name: 'amount', type: 'double' },
        {	name: 'startDate', type: 'string' },
        {	name: 'endDate', type: 'string' }
     ]);
    
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'rates/view.action',
            update: 'rates/update.action'
        }
    });
    
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    Rates);


    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
   
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    //read the data from simple array
    store.load();

    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });
    
     var editor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    // create grid
     var grid = new Ext.grid.GridPanel({
        store: store,
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
            header: "CODE",
            width: 170,
            sortable: true,
            dataIndex: 'code'
        },

        {
            header: "NAME",
            width: 150,
            sortable: true,
            dataIndex: 'name'
        },

        {
            header: "AMOUNT",
            width: 150,
            sortable: true,
            dataIndex: 'amount',
            editor: {
                xtype: 'numberfield',
                allowBlank: false,
                blankText: 'Amount should not be null'
            }
        },
        {
            header: "START DATE",
            width: 150,
            sortable: true,
            xtype: 'datecolumn',
            dataIndex: 'startDate',
            editor:{
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y'
            }
        },{
            header: "END DATE",
            width: 150,
            sortable: true,
            xtype: 'datecolumn',
            dataIndex: 'endDate',
            editor:{
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y'
            }
        }
        ],
        viewConfig: {
            forceFit:true
        },
        // inline buttons
        plugins: [editor],
        height: 500,
        width: 300,
        frame:true,
        cls:'x-panel-blue',
        title:'RATES',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                store.save();
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
	         items: [grid]
	    }]
	});
});