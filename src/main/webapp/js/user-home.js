Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    // this is the house id that came from the home owner landing page
    var params = Ext.urlDecode(location.search.substring(1));
    
    var houseId = params.houseId;
    var houseNum = params.houseNum;
    var address = params.address;
    var delinquent = params.del;
    
    var decodedAddress = decodeURIComponent(address.replace(/\+/g, '%20') );
    var houseListLink = document.getElementById('houseListLink');
    
    
    // alert('RACS NUMBER OF HOUSES: ' + houseNum);
    
    // set the Owner's Address
    var ownerAddress = document.getElementById('ownerAddress')
    ownerAddress.innerHTML = decodedAddress;
    ownerAddress.style.color = "green";
    ownerAddress.style.fontWeight = 'bold';
    
    
    // display the link to house list
    if (houseNum > 1)
    {
    	houseListLink.style.display = 'block';
    }
    
     

 // ###################################################      SET THE ATTRIBUTE OF THE HREFS     ######################################################################
    var homeLink =  document.getElementById('homeLink').getAttribute('href');
    
    var createRequestLink =  document.getElementById('createRequestLink').getAttribute('href');
    var billingViewLink = document.getElementById('billingViewLink').getAttribute('href');
    var billingStatementLink = document.getElementById('billingStatementLink').getAttribute('href');
    var viewORHistoryLink = document.getElementById('viewORHistoryLink').getAttribute('href');
    var updateProfileLink = document.getElementById('updateProfileLink').getAttribute('href');
    
    
    homeLink = homeLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    createRequestLink = createRequestLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    billingViewLink = billingViewLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    billingStatementLink = billingStatementLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    viewORHistoryLink = viewORHistoryLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    updateProfileLink = updateProfileLink + '?houseId=' + houseId + '&address=' + address + '&houseNum=' + houseNum + '&del=' + delinquent;
    
    
    // SET ATTRIBUTES
    document.getElementById('homeLink').href = homeLink;
    document.getElementById('createRequestLink').href = createRequestLink;
    document.getElementById('billingViewLink').href = billingViewLink;
    document.getElementById('billingStatementLink').href = billingStatementLink;
    document.getElementById('viewORHistoryLink').href = viewORHistoryLink;
    document.getElementById('updateProfileLink').href = updateProfileLink;
    
    document.getElementById('createRequestLink').onclick = function()
    { 
    	// if home owner is DELINQUENT, DO NOT ALLOW TO CREATE REQUEST
        if (delinquent == 'T')
        {
        	alert('You have an outstanding balance. Please contact the homeowners ADMIN.');
        	return false;
        }
        else
        {
        	return true;
        }
    }

    
   // alert('homeLink: ' + document.getElementById("homeLink").getAttribute("href"));
    
    
    
   // var hiddenInput = Ext.get('hiddenHouseId').text;
   // alert("hiddenInput: " + hiddenInput);
    
    
// ###################################################      BEANS     ######################################################################
    var AmenityBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'des',
        type: 'string'
    },
    {
        name: 'requestedDate',
        type: 'string'
    },{
        name: 'requestId',
        type: 'string'
    },
    {
        name: 'startTime',
        type: 'string'
    }, {
        name: 'endTime',
        type: 'string'
    },{
        name: 'status',
        type: 'string'
    },{
        name: 'quantity',
        type: 'int'
    },{
        name: 'uom',
        type: 'string'
    }]);

    var ServiceBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'des',
        type: 'string'
    },
    {
        name: 'requestId',
        type: 'string'
    },
    {
        name: 'preferredDate',
        type: 'string'
    },{
        name: 'preferredTime',
        type: 'string'
    }, {
        name: 'confirmedDate',
        type: 'string'
    }, {
        name: 'status',
        type: 'string'
    }, {
        name: 'quantity',
        type: 'int'
    },{
        name: 'uom',
        type: 'string'
    }]);
    
    
    
    var AdjustmentBean = Ext.data.Record.create([
	    { name: 'id', type: 'int'},
	    { name: 'postedDate', type: 'string'},
	    { name: 'remarks', type: 'string'},
	    { name: 'amount', type: 'int'}
    ]);

    
    var Vehicle = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
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


    var Employee = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },{
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


    
// ###################################################      PROXY     ######################################################################
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'home/amenities.action?houseId=' + houseId,
            update: 'home/update/amenities.action?houseId=' + houseId
        }
    });

    var srproxy = new Ext.data.HttpProxy({
        api: {
            read : 'home/services.action?houseId=' + houseId,
            update: 'home/update/services.action?houseId=' + houseId
        }
    });

    
    var adjustmentproxy = new Ext.data.HttpProxy({
        api: {
            read : 'home/adjustments.action?houseId=' + houseId/*,
            update: 'home/update/services.action?houseId=' + houseId*/
        }
    });
    

    var vproxy = new Ext.data.HttpProxy({
        api: {
            read : 'home/vehicle/view.action?houseId=' + houseId,
            update: 'home/update/vehicle.action?houseId=' + houseId
        }
    });

    var eproxy = new Ext.data.HttpProxy({
        api: {
            read : 'home/employee/view.action?houseId=' + houseId,
            update: 'home/employee/update.action?houseId=' + houseId
        }
    });

    
    
    
// ###################################################      READER     ######################################################################
    var vreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    Vehicle);


    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    AmenityBean);

    var srreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    ServiceBean);
    
    var adjustmentreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    AdjustmentBean);
    

    var ereader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    Employee);

    
    
// ###################################################      WRITER     ######################################################################
    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });

    
    
// ###################################################      STORE     ######################################################################
    // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        writer: writer,
        reader: reader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });


    var srstore = new Ext.data.Store({
        id: 'user',
        proxy: srproxy,
        reader: srreader,
        writer: writer,
        autoSave: false
    });
    
    
    // ADJUSTMENTS
    var adjustmentstore = new Ext.data.Store({
        id: 'user',
        proxy: adjustmentproxy,
        reader: adjustmentreader,
        writer: writer,
        autoSave: false
    });
    

    var vstore = new Ext.data.Store({
        id: 'user',
        proxy: vproxy,
        reader: vreader,
        writer: writer,  
        autoSave: false
    });

    var estore = new Ext.data.Store({
        id: 'user',
        proxy: eproxy,
        reader: ereader,
        writer: writer,
        autoSave: false
    });

    

// ###################################################     IMPORTANT: LOAD STORES     ######################################################################
    //read the data from simple array
    store.load();
    srstore.load();
    adjustmentstore.load();
    vstore.load();
    estore.load();

    
    
    
 // ###################################################      LISTENER     ######################################################################
    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });


    
    
 // ###################################################      GRID ROW EDITOR     ######################################################################
    var areditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var sreditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    var veditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });


    var eeditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });

    
    
// ###################################################      GRID PANEL     ######################################################################
    // create grid (this grid for vehicle is currently not in use, as of 19FEB2015)
    var vgrid = new Ext.grid.GridPanel({
        store: vstore,
        id:'vgrid',
        columns: [
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
                    ['ReIssue', 'ReIssue'],
                    ['Renew', 'Renew'],
                    ['Sold', 'Sold'],
                    ['Cancel', 'Cancel']
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
            header: "ID",
            width: 170,
            hidden:true,
            sortable: true,
            dataIndex: 'id'
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
            width: 120,
            sortable: true,
            dataIndex: 'plateNumber'
        },
        {
            header: "STICKER",
            width:100,
            sortable: true,
            dataIndex:'sticker'
        },
        {
            header: "VERIFIED",
            width:100,
            sortable: true,
            dataIndex:'verified'
        }
        ],
        viewConfig: {
            forceFit:true
        },
        plugins: [veditor],
        title: 'VIEW STICKER REQUEST FOR VEHICLE',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300,
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                vstore.save();
                vstore.load();
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Refresh Vehicle Sticker Request',
            handler: function(){
                Ext.getCmp('vgrid').getStore().load()
            }
        }]
    });

    // create grid (for amenities requests)
    var argrid = new Ext.grid.GridPanel({
        store: store,
        id:'argrid',
        columns: [
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
                    ['Cancel', 'Cancel'],
                    ['Change Request', 'Change Request']
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
        }, {
            header: "ID",
            width: 170,
            hidden:true,
            sortable: true,
            dataIndex: 'id'
        },
        {
            header: "REQUEST ID",
            width: 170,
            hidden:false,
            sortable: true,
            dataIndex: 'requestId'
        },{
            header: "UOM",
            width: 170,
            hidden:true,
            sortable: true,
            dataIndex: 'uom'
        },
        {
            header: "AMENITY",
            width: 170,
            sortable: true,
            dataIndex: 'des'
        },
        {
            header: "REQUESTED DATE", // RACS
            width: 150,
            sortable: true,
            xtype: 'datecolumn',
            dataIndex: 'requestedDate',
            editor:{
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                minValue: new Date(),
                allowBlank: false,
                blankText: 'Requested Date should not be null',
                editable:false
            }
        },
        {
            header: "START TIME",
            width: 150,
            sortable: true,
            dataIndex: 'startTime',
            editor:{
                xtype: 'timefield',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                emptyText: 'Select action'
            }
        },

        {
            header: "END TIME",
            width: 150,
            sortable: true,
            dataIndex: 'endTime',
            editor:{
                xtype: 'timefield',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                emptyText: 'Select action'
            }
        },{
            header: "QUANTITY",
            width: 170,
            sortable: true,
            dataIndex: 'quantity',
            editor:{
                xtype: 'numberfield'
            }
        }
        ],
        
        viewConfig: {
            forceFit:true
        },

        // inline buttons
        plugins: [areditor],
        cls:'x-panel-blue',
        width:400,
        height:300,
        frame:true,
        title:'VIEW RESERVATION REQUEST',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                store.save();
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Refresh Amenity Request',
            handler: function(){
                Ext.getCmp('argrid').getStore().load()
            }
        }]
    //renderTo: document.body Ext.getCmp('argrid').getStore().load()
    });
    

    // GRID FOR SERVICE REQUESTS
    var srgrid = new Ext.grid.GridPanel({
        store: srstore,
        id:'srgrid',
        columns: [
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
                    ['Cancel', 'Cancel'],
                    ['Re-Schedule', 'Re-Schedule']
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
        },  {
            header: "REQUEST ID",
            width: 170,
            hidden:false,
            sortable: true,
            dataIndex: 'requestId'
        },   
        {
            header: "ID",
            width: 170,
            hidden:true,
            sortable: true,
            dataIndex: 'id'
        },{
            header: "UOM",
            width: 170,
            hidden:true,
            sortable: true,
            dataIndex: 'uom'
        },
        {
            header: "SERVICE",
            width: 170,
            sortable: true,
            dataIndex: 'des'
        },

        {
            header: "PREFERRED DATE", // CHRIS
            width: 150,
            sortable: true,
            xtype: 'datecolumn',
            dataIndex: 'preferredDate',
            editor:{
	            xtype: 'datefield',
	            format: 'm/d/Y',
	            submitFormat: 'm/d/Y',
	            minValue: new Date(),
	            allowBlank: false,
	            blankText: 'Requested Date should not be null',
	            editable:false
	        }
        },

        {
            header: "PREFERRED TIME",
            width: 150,
            sortable: true,
            dataIndex: 'preferredTime',
            editor:{
                xtype: 'timefield',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                emptyText: 'Select action'
            }
        },{
            header: "QUANTITY",
            width: 170,
            sortable: true,
            dataIndex: 'quantity',
            editor:{
                xtype: 'numberfield'
            }
        },
        {
            header: "CONFIRMED DATE",
            width: 170,
            sortable: true,
            dataIndex: 'confirmedDate'
        }
        ],

        viewConfig: {
            forceFit:true
        },

        // inline buttons
        plugins: [sreditor],
        cls:'x-panel-blue',
        width:400,
        height:300,
        frame:true,
        title:'VIEW SERVICE REQUEST',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                srstore.save();
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Refresh Service Request',
            handler: function(){
                Ext.getCmp('srgrid').getStore().load()
            }
        }]
    //renderTo: document.body
    });

    
    // GRID FOR ADJUSTMENT
    var adjustmentgrid = new Ext.grid.GridPanel({
        store: adjustmentstore,
        id:'adjustmentgrid',
        columns: [
		{
		    header: "POSTED DATE",
		    width: 150,
		    sortable: true,
		    dataIndex: 'postedDate'
		},
		{
            header: "REMARKS",
            width: 170,
            sortable: true,
            dataIndex: 'remarks'
        },
        {
            header: "AMOUNT",
            width: 170,
            sortable: true,
            dataIndex: 'amount'/*,
            editor:{
                xtype: 'numberfield'
            }*/
        }],

        viewConfig: {
            forceFit:true
        },

        // inline buttons
        // plugins: [sreditor],
        cls:'x-panel-blue',
        width:400,
        height:300,
        frame:true,
        title:'VIEW ADJUSTMENTS',
        iconCls:'icon-grid',
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
               // srstore.save();
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Refresh Service Request',
            handler: function(){
                Ext.getCmp('adjustmentgrid').getStore().load()
            }
        }]
    });
    
    
    //create employee grid (this grid for employee is currently not in use, as of 19FEB2015)
    var eGrid = new Ext.grid.GridPanel({
        store: estore,
        id:'eGrid',
        columns: [
        {
            header: "STATUS",
            width:100,
            sortable: true,
            dataIndex: 'status',
            renderer:customRenderer,
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Update Profile', 'Update Profile'],    
                    ['Renew', 'Renew'],
                    ['End of Contract', 'End of Contract'],
                    ['Cancel', 'Cancel']
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
            dataIndex: 'Id',
            editor: {
                xtype: 'textfield',
                readOnly: true
            }
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
            dataIndex: 'civilStatus',
            editor: {
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Single', 'Single'],
                    ['Married', 'Married']
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
            dataIndex: 'enddate',
            editor:{
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                editable:false
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
        viewConfig: {
            forceFit:true
        },
        plugins: [eeditor],
        title: 'VIEW EMPLOYEE AUTHORIZATION REQUEST',
        frame:true,
        cls:'x-panel-blue',
        width:400,
        height:300,
        tbar: [{
            iconCls: 'icon-user-save',
            text: 'Save All Modifications',
            handler: function(){
                estore.save();
            }
        },{
            iconCls: 'icon-user-add',
            text: 'Refresh Employee Authorization Request',
            handler: function(){
                Ext.getCmp('eGrid').getStore().load()
            }
        }]
    });


    
    
    
// ###################################################      MAIN PANEL     ######################################################################

    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        renderTo: 'my-tabs',
        width: 1230,
        height: 445,
        activeItem: 0,
        // items: [argrid,srgrid,vgrid,eGrid]
        // items: [argrid,srgrid,adjustmentgrid]
        items: [argrid,srgrid] // removed View Adjustments requested on 20MAR2015
    });

});




// ###################################################      UTILITY FUNCTIONS     ######################################################################
function renderTopic(value){
    return String.format(
        '<b>\n\
                <a style="color:red" href="index.action?t='+value+'">{0}</a>\n\
                </b>',
        value);
}
 



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
    }else if (value === "Cancel") {
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