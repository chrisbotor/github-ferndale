Ext.onReady(function(){
    
    // Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    

// ####################################################################   BEAN   ############################################################### 
    // AMENITY
    var Amenity = Ext.data.Record.create([  //REMOVE THIS IT IS ALREADY IN THE load amenity requests js
	                  {name: 'id', type: 'int'},
	                  {name: 'code', type: 'string'},
	                  {name: 'description', type: 'string'},
	                  {name: 'reg_value', type: 'float'},
	                  {name: 'excess_value', type: 'float'},
	                  {name: 'uom', type: 'string'}
                  ]);


    // SERVICE
    var Services = Ext.data.Record.create([
	                   {name: 'id', type: 'int'},
	                   {name: 'code', type: 'string'},
	                   {name: 'description', type: 'string'},
	                   {name: 'reg_value', type: 'float'},
	                   {name: 'excess_value', type: 'float'},
	                   {name: 'uom', type: 'string'}
                   ]);
    
  
    
//####################################################################   PROXY   ###############################################################
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'amenities/view.action'
        }
    });
    
    
    var serproxy = new Ext.data.HttpProxy({
        api: {
            read : 'services/view.action'
        }
    });
 
    
    
    
// ####################################################################   READER   ###############################################################
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
    

    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    
    
    
// ####################################################################   STORE   ###############################################################
     var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        reader: reader,
        writer: writer,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });
    
     
    var serstore = new Ext.data.Store({
        id: 'user',
        proxy: serproxy,
        reader: serreader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

   

// ####################################################################   IMPORTANT: LOAD THE STORE!!!   ################################################
    store.load();
    serstore.load();
    
 
    
    
 // ####################################################################   PANEL ITEMS   ###############################################################
    
    var amenity = {
        columnWidth: .99,
        layout: 'form',
        align:'center',
        items: [{
            xtype: 'fieldset',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
            	fieldLabel: 'REQUESTOR',
        		name: 'requestor',
        		id: 'requestor',
        		xtype:'combo',
        		store: ownerHouseStore,
        		queryMode: 'server',
        		displayField:'ownerHouseLabel',
        		valueField: 'houseId',
        		mode: 'local',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender: true,
                emptyText: 'Select Requestor',
                allowBlank: false,
                blankText: 'Requestor should not be null',
                anchor: '100%',
                listeners:{
                	select:  function(combo, record, index){
                   
                		var requestorUserId = record.get('userId');
               			var requestorHouseId = combo.getValue();
               			
               			// alert('requestorUserId: ' + requestorUserId);
               			// alert('requestorHouseId: ' + requestorHouseId);
               			
               			Ext.getCmp('requestorUserId').setValue(requestorUserId);
               			Ext.getCmp('requestorHouseId').setValue(requestorHouseId);
               			// alert('RACS requestorUserId: ' + Ext.getCmp('requestorUserId').getValue());
               		}
                }
            },
            {
                fieldLabel: 'STATUS',
                name: 'status',
                id: 'status',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['New', 'New'],
                    ['Reserved', 'Reserved'],
                    ['Booked', 'Booked'],
                    ['Cancel', 'Cancel'],
                    ['Done', 'Done']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                //editable:false,
                emptyText: 'Select All',
                //allowBlank: false,
                blankText: 'Status should not be null',
                anchor: '100%'
            },
            {
            	fieldLabel: 'AMENITY',
            	name: 'amenityId',
            	id: 'amenityId',
            	store: store,
            	displayField:'description',
                valueField: 'id',
                xtype:'combo',
                cls: 'cTextAlign', 
                queryMode: 'server',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                //editable:false,
                emptyText: 'Select All',
                //allowBlank: false,
                //blankText: 'Amenity should not be null',
                anchor: '100%'/*,
                listeners:{
                	select:  function(combo, record, index){
                   
                		var amenityId = record.get('id');
               			// alert('amenityId: ' + amenityId);
               			
               			Ext.getCmp('amenityId').setValue(amenityId);
               			// alert('RACS amenityId: ' + Ext.getCmp('amenityId').getValue());
               		}
                }*/
            }
            ,{
                fieldLabel: 'FROM DATE',
                name: 'fromDate',
                id: 'fromDate',
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                emptyText: 'Any Date',
                //allowBlank: false,
                editable:false,
                anchor: '100%'
            },
            {
                fieldLabel: 'TO DATE',
                name: 'toDate',
                id: 'toDate',
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                emptyText: 'Any Date',
                //allowBlank: false,
                editable:false,
                anchor: '100%'
            },
            // #######################################################  AMENITY HIDDEN FIELDS   #######################################
            {
            	name: 'requestorUserId',
            	id:'requestorUserId',
            	xtype: 'numberfield',
            	value: 100,
            	hidden:true,
            	anchor: '100%'
            },{
            	name: 'requestorHouseId',
            	id:'requestorHouseId',
            	xtype: 'numberfield',
            	value: 100,
            	hidden:true,
            	anchor: '100%'
            }/*,{
				name: 'amenityId',
				id:'amenityId',
				xtype: 'numberfield',
				value: 100,
				hidden:true,
				anchor: '100%'
			}*/            
          ]
        }]
    }
    
   
    
// #######################################################  SEARCH SERVICE REQUESTS   #######################################
  
    var services = {
            columnWidth: .99,
            layout: 'form',
            align:'center',
            items: [{
                xtype: 'fieldset',
                autoHeight: true,
                defaultType: 'textfield',
                items: [
                {
                	fieldLabel: 'REQUESTOR',
            		name: 'serviceRequestor',
            		id: 'serviceRequestor',
            		xtype:'combo',
            		store: ownerHouseStore,
            		queryMode: 'server',
            		displayField:'ownerHouseLabel',
            		valueField: 'houseId',
            		mode: 'local',
                    typeAhead: true,
                    triggerAction: 'all',
                    lazyRender: true,
                    emptyText: 'Select Requestor',
                    allowBlank: false,
                    blankText: 'Requestor should not be null',
                    anchor: '100%',
                    listeners:{
                    	select:  function(combo, record, index){
                       
                    		var requestorUserId = record.get('userId');
                   			var requestorHouseId = combo.getValue();
                   			
                   			// alert('requestorUserId: ' + requestorUserId);
                   			// alert('requestorHouseId: ' + requestorHouseId);
                   			
                   			Ext.getCmp('requestorUserId').setValue(requestorUserId);
                   			Ext.getCmp('requestorHouseId').setValue(requestorHouseId);
                   			// alert('RACS requestorUserId: ' + Ext.getCmp('requestorUserId').getValue());
                   		}
                    }
                },
                {
                    fieldLabel: 'STATUS',
                    name: 'serviceStatus',
                    id: 'serviceStatus',
                    xtype:'combo',
                    queryMode: 'server',
                    store: new Ext.data.ArrayStore({
                        fields: ['abbr', 'action'],
                        data : [
                        ['New', 'New'],
                        ['Reserved', 'Reserved'],
                        ['Booked', 'Booked'],
                        ['Cancel', 'Cancel'],
                        ['Done', 'Done']
                        ]
                    }),
                    displayField:'action',
                    valueField: 'abbr',
                    mode: 'local',
                    typeAhead: false,
                    triggerAction: 'all',
                    lazyRender: true,
                    //editable:false,
                    emptyText: 'Select All',
                    //allowBlank: false,
                    blankText: 'Status should not be null',
                    anchor: '100%'
                },
                {
                	fieldLabel: 'SERVICE',
                	name: 'serviceId',
                	id: 'serviceId',
                	store: serstore,
                	displayField:'description',
                    valueField: 'id',
                    xtype:'combo',
                    cls: 'cTextAlign', 
                    queryMode: 'server',
                    mode: 'local',
                    typeAhead: false,
                    triggerAction: 'all',
                    lazyRender: true,
                    //editable:false,
                    emptyText: 'Select All',
                    //allowBlank: false,
                    //blankText: 'Amenity should not be null',
                    anchor: '100%'/*,
                    listeners:{
                    	select:  function(combo, record, index){
                       
                    		var amenityId = record.get('id');
                   			// alert('amenityId: ' + amenityId);
                   			
                   			Ext.getCmp('amenityId').setValue(amenityId);
                   			// alert('RACS amenityId: ' + Ext.getCmp('amenityId').getValue());
                   		}
                    }*/
                },
                {
                    fieldLabel: 'FROM DATE',
                    name: 'serviceFromDate',
                    id: 'serviceFromDate',
                    xtype: 'datefield',
                    format: 'm/d/Y',
                    submitFormat: 'm/d/Y',
                    emptyText: 'Any Date',
                    //allowBlank: false,
                    editable:false,
                    anchor: '100%'
                },
                {
                    fieldLabel: 'TO DATE',
                    name: 'serviceToDate',
                    id: 'serviceToDate',
                    xtype: 'datefield',
                    format: 'm/d/Y',
                    submitFormat: 'm/d/Y',
                    emptyText: 'Any Date',
                    //allowBlank: false,
                    editable:false,
                    anchor: '100%'
                }
            ]
         }]
     }
            
    
    
    function fnLoadForm(theForm)
    {
        //for the purpose of this tutorial, load 1 record.
        theForm.getForm().load();
    } 
    

    var amenityForm = new Ext.FormPanel({
        id: 'amenity-form',
        title: 'SEARCH AMENITY REQUEST',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 120,
        labelAlign : 'left',
        /*width : 800,
        height : 350,*/
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [amenity]
        }],
        buttons: [{
            text: 'Submit',
            handler: function() 
            {
            	var requestorUserId = Ext.getCmp('requestorUserId').getValue();
            	var requestorHouseId = Ext.getCmp('requestorHouseId').getValue();
            	
            	// alert('requestorUserId: ' + requestorUserId);
            	// alert('requestorHouseId: ' + requestorHouseId);
            	
            	var amenityId = Ext.getCmp('amenityId').getValue();
            	var status = Ext.getCmp('status').getValue();
            	
            	// FORMAT fromDate
            	if (Ext.getCmp('fromDate').getValue() != null && Ext.getCmp('fromDate').getValue() != "")
            	{
            		var fromDate = Ext.getCmp('fromDate').getValue().format('m/d/Y');
            	}
            	else
            	{
            		var fromDate = Ext.getCmp('fromDate').getValue();
            	}
            	
            	
            	// FORMAT toDate
            	if (Ext.getCmp('toDate').getValue() != null && Ext.getCmp('toDate').getValue() != "")
            	{
            		var toDate = Ext.getCmp('toDate').getValue().format('m/d/Y');
            	}
            	else
            	{
            		var toDate = Ext.getCmp('toDate').getValue();
            	}
            		
            	// SUBMIT	
            	window.location = 'admin-search-amenity-requests.action?requestorUserId=' + requestorUserId + '&requestorHouseId=' + requestorHouseId
                    	 			+ '&amenityId=' + amenityId + '&status=' + status + '&fromDate=' + fromDate + '&toDate=' + toDate;
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(amenityForm);
            }
        }]
    });
    
    var serviceForm = new Ext.FormPanel({
        id: 'service-form',
        title: 'SEARCH SERVICE REQUEST',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 120,
        labelAlign : 'left',
        width : 450,
        height : 350,
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [services]
            //items: []
        }],
        buttons: [{
            text: 'Submit',
            handler: function() 
            {
            	var requestorUserId = Ext.getCmp('requestorUserId').getValue();
            	var requestorHouseId = Ext.getCmp('requestorHouseId').getValue();
            	
            	// alert('requestorUserId: ' + requestorUserId);
            	// alert('requestorHouseId: ' + requestorHouseId);
            	
            	var serviceId = Ext.getCmp('serviceId').getValue(); // RACS
            	var serviceStatus = Ext.getCmp('serviceStatus').getValue();
        		// var serviceFromDate = Ext.getCmp('serviceFromDate').getValue();
        		// var serviceToDate = Ext.getCmp('serviceToDate').getValue();
        		
        		
            	// ECY
            	
               	// FORMAT fromDate
            	var fromDate = "";
            	
            	if (Ext.getCmp('serviceFromDate').getValue() != null && Ext.getCmp('serviceFromDate').getValue() != "")
            	{
            		fromDate = Ext.getCmp('serviceFromDate').getValue().format('m/d/Y');
            	}
            	else
            	{
            		fromDate = Ext.getCmp('serviceFromDate').getValue();
            	}
            	
            	
            	// FORMAT toDate
            	var toDate = "";
            	
            	if (Ext.getCmp('serviceToDate').getValue() != null && Ext.getCmp('serviceToDate').getValue() != "")
            	{
            		toDate = Ext.getCmp('serviceToDate').getValue().format('m/d/Y');
            	}
            	else
            	{
            		toDate = Ext.getCmp('serviceToDate').getValue();
            	}
            	
            	
            	
            	
            	// var serviceStatus = Ext.getCmp('serviceStatus').getValue();
        		// var serviceFromDate = Ext.getCmp('serviceFromDate').getValue();
        		// var serviceToDate = Ext.getCmp('serviceToDate').getValue();
        			
        		window.location = 'admin-search-service-requests.action?requestorUserId=' + requestorUserId + '&requestorHouseId=' + requestorHouseId
                	 					+ '&serviceId=' + serviceId + '&status=' + serviceStatus + '&fromDate=' + fromDate + '&toDate=' + toDate;
            	
            	
            	/*if (serviceId != null)
            	{	
            		// alert('serviceId: ' + serviceId);
            		
            		var serviceStatus = Ext.getCmp('serviceStatus').getValue();
            		var serviceFromDate = Ext.getCmp('serviceFromDate').getValue();
            		var serviceToDate = Ext.getCmp('serviceToDate').getValue();
            			
            		window.location = 'admin-search-service-requests.action?requestorUserId=' + requestorUserId + '&requestorHouseId=' + requestorHouseId
                    	 					+ '&serviceId=' + serviceId + '&status=' + serviceStatus + '&fromDate=' + serviceFromDate + '&toDate=' + serviceToDate;
            	}*/
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(amenityForm);
            }
        }]
    });
    
    
    var myTabPanel = new Ext.TabPanel({
        title:"MY TAB",
        style: 'margin-top:30px',
        width : 450,
        height : 260,
        align:'center',
        renderTo: 'my-tabs',
        activeItem: 0,
        // items: [amenityForm,serviceForm,vehicleForm,employeeForm]  // temporarily disabled vehicle and employee, not part of deliverable
        items: [amenityForm, serviceForm]
    });
});


// RESET FORM
function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
}
