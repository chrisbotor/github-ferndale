Ext.onReady(function(){

    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    var params = Ext.urlDecode(location.search.substring(1));
    
    var searchType = params.searchType;
    // alert('searchType: ' + searchType);
    
    
    // alert('on admin-get-requests.js!');

    // AMENITY BEAN
    var AmenityBean = Ext.data.Record.create([
                      {name: 'id', type: 'int'},
                      {name: 'houseId', type: 'int'},
                      {name: 'amenityId', type: 'int'},
                      {name: 'requestId', type: 'string'},
                      {name: 'address', type: 'string'},
                      {name: 'des', type: 'string'},
                      {name: 'requestedDate', type: 'string'},
                      {name: 'startTime', type: 'string'}, 
                      {name: 'endTime', type: 'string'},
                      {name: 'status', type: 'string'},
                      {name: 'basic_cost', type: 'float'},
                      {name: 'additional_cost', type: 'float'},
                      {name: 'total_cost', type: 'float'},
                      {name: 'uom', type: 'string'},
                      {name: 'requestor', type: 'string'},
                      {name: 'quantity', type: 'int'},
                      {name: 'regularPrice', type: 'float'},
                      {name: 'excessPrice', type: 'float'},
                      {name: 'maxRegular', type: 'int'},
                      {name: 'remarks', type: 'string'},
                      {name: 'otherCharges', type: 'float'}
     ]);
    
    
    // SERVICE BEAN
    var ServiceBean = Ext.data.Record.create([
                      {name: 'id', type: 'int'},
                      {name: 'requestId', type: 'string'},
                      {name: 'address', type: 'string'},
                      {name: 'houseId', type: 'int'},
                      {name: 'serviceId', type: 'int'},
                      {name: 'des', type: 'string'},
                      {name: 'preferredDate', type: 'string'},
                      {name: 'preferredTime', type: 'string'},
                      {name: 'confirmedDate', type: 'string'},
                      {name: 'status', type: 'string'},
                      {name: 'basic_cost', type: 'float'},
                      {name: 'additional_cost', type: 'float'},
                      {name: 'total_cost', type: 'float'},
                      {name: 'uom', type: 'string'},
                      {name: 'requestor', type: 'string'},
                      {name: 'regularPrice', type: 'float'},
                      {name: 'excessPrice', type: 'float'},
                      {name: 'quantity', type: 'int'},
                      {name: 'maxRegular', type: 'int'},
                      {name: 'remarks', type: 'string'},
                      {name: 'otherCharges', type: 'float'}
     ]);

 
    
 // ########################################################################   PROXY   #############################################################
    var proxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin-search-user-requests/amenities.action'
        }
    });

  /*  var srproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin-get-requests/services.action'
        }
    });*/
    
    

 // ########################################################################   READER   #############################################################
    var reader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    AmenityBean);

   /* var srreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'
    },
    ServiceBean);*/
    
    
   
    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    
 
  // ########################################################################   STORE   #############################################################

    // Typical Store collecting the Proxy, Reader and Writer together.
    var store = new Ext.data.Store({
        id: 'user',
        proxy: proxy,
        //writer: writer,
        reader: reader,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });


    
   /* var srstore = new Ext.data.Store({
        id: 'user',
        proxy: srproxy,
        reader: srreader,
        writer: writer,
        autoSave: false
    });*/
    
    
 
// ########################################################################  IMPORTANT: LOAD THE STORES   #############################################################

    //read the data from simple array
    store.load();
    // srstore.load();
   

    //var areditor = new Ext.ux.grid.RowEditor({
    //saveText: 'Update'
    //});

    var sreditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update'
    });
    
        
    
// ########################################################################   GRID   #############################################################
    var srgrid = new Ext.FormPanel({
        frame: true,
        title: 'SERVICE REQUEST',
        bodyPadding: 5,
        width: 900,
        id: 'service-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns
        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        items: [{
            columnWidth: 0.65,
            xtype: 'grid',
            id:'ars',
            //store: srstore,
            cls: 'x-panel-header',
            width: 400,
            height:480,
            title:'SERVICE REQUEST',
            
            columns: [
                {
                	header: "STATUS",
                	width:90,
                	sortable: true,
                	dataIndex:'status',
                	renderer:customRenderer
                },{
                	header: "ADDRESS",
                	width: 200,
                	sortable: true,
                	dataIndex: 'address'
                },{
                	header: "REQUESTOR",
                	width: 200,
                	sortable: true,
                	dataIndex: 'requestor'
                },{
                	header: "SERVICE",
                	width: 160,
                	sortable: true,
                	dataIndex: 'des'
                },{
                	header: "PREFFERED DATE",
                	width: 109,
                	sortable: true,
                	dataIndex: 'preferredDate'
                }
            ],
            listeners: {
                'rowclick':  function(grid, rowIndex, e)
                {
                	// set the values
                    var record = grid.getStore().getAt(rowIndex);
                    var id = record.get('id');
                    var requestId = record.get('requestId');
                    var status = record.get('status');
                    var address = record.get('address')
                    var requestor = record.get('requestor')
                    var service = record.get('des')
                    var preferredDate = record.get('preferredDate')
                    var preferredTime = record.get('preferredTime')
                    var confirmedDate = record.get('confirmedDate')
                    var quantity = record.get('quantity')
                    var basic_cost = record.get('basic_cost')
                    var additional_cost = record.get('additional_cost')
                    var total_cost = record.get('total_cost')
                    var uom = record.get('uom')
                    var regularPrice = record.get('regularPrice')
                    var excessPrice = record.get('excessPrice')
                    var maxRegular = record.get('maxRegular')
                    var remarks = record.get('remarks')
                    var otherCharges = record.get('otherCharges')
                    
                    // set value of components
                    Ext.getCmp('ags-id').setValue(id)
                    Ext.getCmp('ags-requestId').setValue(requestId)
                    Ext.getCmp('ags-address').setValue(address)
                    Ext.getCmp('ags-requestor').setValue(requestor)
                    Ext.getCmp('ags-status').setValue(status)
                    Ext.getCmp('ags-service').setValue(service)
                    Ext.getCmp('ags-quantity').setValue(quantity)
                    Ext.getCmp('ags-preferredDate').setValue(preferredDate)
                    Ext.getCmp('ags-preferredTime').setValue(preferredTime)
                    Ext.getCmp('ags-confirmedDate').setValue(confirmedDate)
                    Ext.getCmp('ags-basic_cost').setValue(basic_cost);
                    Ext.getCmp('ags-additional_cost').setValue(additional_cost)
                    Ext.getCmp('ags-total_cost').setValue(total_cost)
                    Ext.getCmp('ags-uom').setValue(uom)
                    Ext.getCmp('ags-regularPrice').setValue(regularPrice)
                    Ext.getCmp('ags-excessPrice').setValue(excessPrice)
                    Ext.getCmp('ags-maxRegular').setValue(maxRegular)
                    Ext.getCmp('ags-remarks').setValue(remarks)
                    Ext.getCmp('ags-otherCharges').setValue(otherCharges)
                    
                    
                    // ON CLICK VALIDATION
                    if(status == 'New')
                    {
                    	// alert('STATUS: ' + status);
                    	
                    	
                    	
                    	// hide if shown and used by other status
                    	Ext.getCmp('ags-quantity').hide();
                    	Ext.getCmp('ags-basic_cost').hide();
                    	Ext.getCmp('ags-preferredTime').hide();
                    	Ext.getCmp('ags-basic_cost').hide();
                        Ext.getCmp('ags-additional_cost').hide();
                        Ext.getCmp('ags-otherCharges').hide();
                        Ext.getCmp('ags-remarks').hide();
                        Ext.getCmp('ags-total_cost').hide();
                    }
                    
                    
                    if(status == 'Confirmed')
                    {
                    	// alert('STATUS: ' + status);
                    	
                    	Ext.getCmp('ags-quantity').hide();
                    	
                    	/*if(uom == 'hph')
                        {
                            Ext.getCmp('ags-quantity').show();
                            // Ext.getCmp('ags-preferredTime').show();
                        }*/
                    	
                       /* Ext.getCmp('ags-basic_cost').show();
                        Ext.getCmp('ags-additional_cost').show();
                        Ext.getCmp('ags-otherCharges').show();
                        Ext.getCmp('ags-remarks').show();
                        Ext.getCmp('ags-total_cost').show()*/
                    }
                    
                    /*if(uom == 'pc'){
                        Ext.getCmp('ags-quantity').show();
                        Ext.getCmp('ags-preferredTime').hide();
                    }
                    
                    
                    
                    if(uom == 'day'){
                        Ext.getCmp('ags-quantity').hide();
                        Ext.getCmp('ags-preferredTime').hide();
                        
                    }*/
                }
            }
        },{
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Service details', //racs
            height:485,
            padding: 20,
            
           /* defaults: {
                width:150,
                labelWidth: 150,
                labelAlign:'right'
            },*/
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'ID',
                name: 'id',
                id: 'ags-id',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'UOM',
                name: 'uom',
                id: 'ags-uom',
                anchor: '98%',
                hidden:true
            },/*{
                // has the hidden basic cost
                name: 'hiddenBasicCost',
                id: 'hiddenBasicCost',
                xtype: 'numberfield',
                hidden:true
            },*/
            {
                fieldLabel: 'MAX REGULAR',
                name: 'maxRegular',
                id: 'ags-maxRegular',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'REGULAR PRICE',
                name: 'regularPrice',
                id: 'ags-regularPrice',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'EXCESS PRICE',
                name: 'excessPrice',
                id: 'ags-excessPrice',
                anchor: '98%',
                hidden:true
            },
            {
                fieldLabel: 'STATUS',
                name: 'status',
                id:'ags-status',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Done', 'Done'],
                    ['Confirmed', 'Confirmed'],
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
                emptyText: 'Select status',
                allowBlank: false,
                blankText: 'Status should not be null',
                anchor: '98%',
                listeners:{
                    select: function(field, newValue, oldValue){
                        
                         var limit = Ext.getCmp('ags-maxRegular').getValue()
                         var qty = Ext.getCmp('ags-quantity').getValue()
                         var renTime = Ext.getCmp('ags-preferredTime').getValue()
                         
                         
                         // ON STATUS VALIDATION
                         if(field.value == "Confirmed")
                         {
                        	 // alert('STATUS: ' + field.value);
                        	 
                        	 // Ext.getCmp('ags-basic_cost').show();
                        	 
                        	 Ext.getCmp('ags-quantity').hide();
                        	 
                        	 // Ext.getCmp('ags-quantity').setReadOnly(true);
                        	 
                        	 
                        	 // var basicCostFromDB = Ext.getCmp('ags-basic_cost').getValue()
                        	 // alert('CHRIS basicCostFromDB: ' + basicCostFromDB);
                        	 
                        	 // Ext.getCmp('hiddenBasicCost').setValue(basicCostFromDB);
                        	 
                        	// alert('RACS basic cost: ' + Ext.getCmp('ags-basic_cost').getValue());
                        	
                        	// Ext.getCmp('ags-basic_cost').setValue(Ext.getCmp('hiddenBasicCost').getValue());
                        	
                            
                            
                            // alert('basi cost AFTER: ' + Ext.getCmp('ags-basic_cost').getValue());
                            
                            /*Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-total_cost').show();
                            */
                            // ECY
                            if(Ext.getCmp('ags-uom').getValue() == 'hph')
                            {
                               /* Ext.getCmp('ags-quantity').show();
                                Ext.getCmp('ags-additional_cost').hide();
                                
                                //var basic_cost_hph = 0;
                                
                                var basic_cost_hph = Ext.getCmp('ags-basic_cost').getValue();
                                
                                //hiddenBasicCost
                                
                                var additional_cost_hph = 0;
                                
                                if(renTime > limit){
                                    basic_cost_hph = Ext.getCmp('ags-regularPrice').getValue()
                                    var diff = renTime - limit;
                                    additional_cost_hph = Ext.getCmp('ags-excessPrice').getValue() * diff
                                }else{
                                    basic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * renTime * qty
                                    additional_cost_hph = 0;
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_hph)
                                Ext.getCmp('ags-additional_cost').setValue(additional_cost_hph)*/
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day')
                            {/*
                                var basic_cost_day = Ext.getCmp('ags-regularPrice').getValue()
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_day)
                                Ext.getCmp('ags-additional_cost').hide();
                             */   
                            }
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'pc')
                            {
                                /*var basic_cost_pc = 0;
                                var additional_cost_pc = 0;
                                if(qty > limit){
                                    basic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var diffs = qty - limit;
                                    additional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * diffs
                                }else{
                                    basic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    additional_cost_pc = 0;
                                }
                                Ext.getCmp('ags-basic_cost').setValue(basic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(additional_cost_pc)*/
                            }
                           
                         }
                         
                         
                         // DONE
                         if(field.value == "Done")
                         {
                        	 // alert('RACS STATUS: ' + field.value);
                        	 
                        	 Ext.getCmp('ags-quantity').show();
                        	 Ext.getCmp('ags-basic_cost').show();
                             Ext.getCmp('ags-additional_cost').show();
                             Ext.getCmp('ags-remarks').show();
                             Ext.getCmp('ags-otherCharges').show();
                             Ext.getCmp('ags-total_cost').show();
                        	 
                        	 if(Ext.getCmp('ags-uom').getValue() == 'pc')
                             {
                                 var dbasic_cost_pc = 0;
                                 var dadditional_cost_pc = 0;
                                 var total_cost_pc = 0.0;
                                 if(qty > limit){
                                     dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                     var ddiffs = qty - limit;
                                     dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                     total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                 }else{
                                     dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                     dadditional_cost_pc = 0;
                                     total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                 }
                                 
                                 Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                 Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                 Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                             }
                        	 
                        	
                        	 
                        	 if(Ext.getCmp('ags-uom').getValue() == 'day')
                             {
                        		 Ext.getCmp('ags-additional_cost').setValue(0);
                        		 Ext.getCmp('ags-additional_cost').hide();
                        		 
                        		 var basic_cost_day = Number(Ext.getCmp('ags-basic_cost').getValue()) * qty;
                        		 var other_charges_day = Number(Ext.getCmp('ags-otherCharges').getValue());
                        		 var total_cost_day = basic_cost_day + other_charges_day; 
                        		
                        		 Ext.getCmp('ags-total_cost').setValue(total_cost_day);
                             }
                        	 
                        	 
                            if(Ext.getCmp('ags-uom').getValue() == 'hph')
                            {
                            	Ext.getCmp('ags-additional_cost').setValue(0);
                            	Ext.getCmp('ags-additional_cost').hide();
                            	
                            	var basic_cost_hph = Number(Ext.getCmp('ags-basic_cost').getValue()) * qty;
                       		 	var other_charges_hph = Number(Ext.getCmp('ags-otherCharges').getValue());
                       		 	var total_cost_hph = basic_cost_hph + other_charges_hph; 
                       		
                       		 	Ext.getCmp('ags-total_cost').setValue(total_cost_hph);
                            	
                                /*Ext.getCmp('ags-quantity').show();
                                Ext.getCmp('ags-additional_cost').hide();
                                
                                var cbasic_cost_hph = 0.0;
                                var total_cost_hph = 0.0;
                                
                                cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * renTime * qty
                                    
                                
                                total_cost_hph = Number(Ext.getCmp('ags-basic_cost').getValue()) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                
                                // ======================= this is it =============================
                                // Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph);
                                
                                Ext.getCmp('ags-basic_cost').setValue(200);
                                
                                Ext.getCmp('ags-total_cost').setValue(total_cost_hph)*/
                            }
                            
                            
                            
                         }
                         
                         
                         
                         if(field.value == "Cancel")
                         {
                        	// Ext.getCmp('ags-basic_cost').hide();
                        	// Ext.getCmp('ags-additional_cost').hide();
                        	 
                            Ext.getCmp('ags-quantity').setValue(0)
                            Ext.getCmp('ags-basic_cost').setValue(0)
                            Ext.getCmp('ags-additional_cost').setValue(0)
                            Ext.getCmp('ags-otherCharges').setValue(0)
                            Ext.getCmp('ags-remarks').setValue("")
                            Ext.getCmp('ags-total_cost').setValue(0)
                            Ext.getCmp('ags-quantity').hide();
                            Ext.getCmp('ags-basic_cost').hide();
                            Ext.getCmp('ags-additional_cost').hide();
                            Ext.getCmp('ags-otherCharges').hide();
                            Ext.getCmp('ags-remarks').hide();
                            Ext.getCmp('ags-total_cost').hide();
                         }
                    }
                }
                
            },{
                fieldLabel: 'REQUEST ID',
                name: 'requestId',
                id: 'ags-requestId',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'ADDRESS',
                name: 'address',
                id: 'ags-address',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'REQUESTOR',
                name: 'requestor',
                id: 'ags-requestor',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'SERVICE',
                name: 'des',
                anchor: '98%',
                id: 'ags-service',
                readOnly:true
            },{
                fieldLabel: 'PREFFERED DATE',
                name: 'preferredDate',
                cls: 'cTextAlign',
                anchor: '98%',
                id: 'ags-preferredDate'
            },{
                fieldLabel: 'RENDERED TIME',
                name: 'preferredDate',
                cls: 'cTextAlign',
                anchor: '98%',
                id: 'ags-preferredTime',  // change the id
                hidden: true,
                listeners: {
                'render': function(c) {
                c.getEl().on('keyup', function() {
                            
                            var qtys = Ext.getCmp('ags-preferredTime').getValue()
                            var qty = Ext.getCmp('ags-quantity').getValue()
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-preferredTime').show();
                                Ext.getCmp('ags-quantity').show();
                                Ext.getCmp('ags-additional_cost').hide();
                                var cbasic_cost_hph = 0.0;
                                cbasic_cost_hph = Ext.getCmp('ags-regularPrice').getValue() * qtys * qty;
                                
                                // Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph) --> basic value should not be editable
                                
                                var total = cbasic_cost_hph + + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-total_cost').setValue(total)
                            }
                }, c);
                }
                }
            },{
                fieldLabel: 'CONFIRMED DATE',
                name: 'confirmedDate',
                cls: 'cTextAlign',
                id: 'ags-confirmedDate',
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                hidden: true,
                anchor: '98%',
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'QUANTITY/ NUM OF WORKERS',
                name: 'quantity',
                anchor: '98%',
                allowBlank: false,
                id: 'ags-quantity',
                hidden: true,
                listeners: {
                'render': function(c) {
                c.getEl().on('keyup', function() {
                			// set the rendered time to 1 hour
                			if (Ext.getCmp('ags-preferredTime').getValue() == 0)
                			{
                				Ext.getCmp('ags-preferredTime').setValue(1);
                			}
                			
                			var limit = Ext.getCmp('ags-maxRegular').getValue()
                            var qty = Ext.getCmp('ags-quantity').getValue()
                            var qtys = Ext.getCmp('ags-preferredTime').getValue()
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();

                            // SET VALUE ON KEYPRESS
                            if(Ext.getCmp('ags-uom').getValue() == 'hph'){
                                Ext.getCmp('ags-preferredTime').show();
                                Ext.getCmp('ags-quantity').show();
                                Ext.getCmp('ags-additional_cost').hide();
                                
                                var cbasic_cost_hph = Number(Ext.getCmp('ags-regularPrice').getValue()) * qtys * qty
                                
                                // Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph)  --> basic cost is not editable
                                
                                var total = cbasic_cost_hph + Number(Ext.getCmp('ags-otherCharges').getValue())
                                Ext.getCmp('ags-total_cost').setValue(total)
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day')
                            { // CHRIS
                            	Ext.getCmp('ags-additional_cost').hide();
                                
                                var ebasic_cost_day = Number(Ext.getCmp('ags-regularPrice').getValue()) * qty;
                                var cadditional_cost_day = Number(ebasic_cost_day) + Number(Ext.getCmp('ags-otherCharges').getValue());
                                Ext.getCmp('ags-total_cost').setValue(cadditional_cost_day);
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var dbasic_cost_pc = 0;
                                var dadditional_cost_pc = 0;
                                var total_cost_pc = 0.0;
                                if(qty > limit){
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var ddiffs = qty - limit;
                                    dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }else{
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    dadditional_cost_pc = 0;
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                            }
                }, c);
                }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'BASIC COST',
                name: 'basic_cost',
                anchor: '98%',
                readOnly:true,
                allowBlank: false,
                hidden: true, // ecy
                id: 'ags-basic_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'ADDITIONAL COST',
                name: 'additional_cost',
                anchor: '98%',
                readOnly:true,
                allowBlank: false,
                hidden: true,
                id: 'ags-additional_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'OTHER CHARGES',
                name: 'otherCharges',
                anchor: '98%',
                allowBlank: false,
                hidden: true,
                id: 'ags-otherCharges',
                listeners: {
                'render': function(c) {
                c.getEl().on('keyup', function() {
                            var limit = Number(Ext.getCmp('ags-maxRegular').getValue());
                            var numberOfHours = Number(Ext.getCmp('ags-preferredTime').getValue());
                            var qty = Number(Ext.getCmp('ags-quantity').getValue());
                            
                            Ext.getCmp('ags-basic_cost').show();
                            Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-total_cost').show();
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'hph')
                            {
                            	// alert('UOM: ' + Ext.getCmp('ags-uom').getValue());
                            	
                            	// Ext.getCmp('ags-preferredTime').show();
                                Ext.getCmp('ags-quantity').show();
                                Ext.getCmp('ags-additional_cost').hide();
                                     
                                var cbasic_cost_hph = 0.0;
                                
                                // alert('numberOfHours: ' + numberOfHours);
                                
                                if (numberOfHours > 0)
                                {
                                	cbasic_cost_hph = Number(Ext.getCmp('ags-regularPrice').getValue()) * numberOfHours * qty;
                                }
                                else
                                {
                                	cbasic_cost_hph = Number(Ext.getCmp('ags-regularPrice').getValue()) * qty;
                                }
                                
                                // Ext.getCmp('ags-basic_cost').setValue(cbasic_cost_hph) --> basic value should not be editable
                                     
                                var total = cbasic_cost_hph + Number(Ext.getCmp('ags-otherCharges').getValue());
                                
                               // alert('TOTAL: ' + total);
                                
                                Ext.getCmp('ags-total_cost').setValue(total);
                            }
                            
                            
                            if(Ext.getCmp('ags-uom').getValue() == 'day')
                            {
                                Ext.getCmp('ags-additional_cost').hide();
                                var ebasic_cost_day = Ext.getCmp('ags-regularPrice').getValue() * qty;
                                var cadditional_cost_day = Number(ebasic_cost_day) + Number(Ext.getCmp('ags-otherCharges').getValue());

                                // Ext.getCmp('ags-basic_cost').setValue(ebasic_cost_day)
                                Ext.getCmp('ags-total_cost').setValue(cadditional_cost_day);
                                
                            }
                            if(Ext.getCmp('ags-uom').getValue() == 'pc'){
                                var dbasic_cost_pc = 0;
                                var dadditional_cost_pc = 0;
                                var total_cost_pc = 0.0;
                                if(qty > limit){
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue()
                                    var ddiffs = qty - limit;
                                    dadditional_cost_pc = Ext.getCmp('ags-excessPrice').getValue() * ddiffs
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }else{
                                    dbasic_cost_pc = Ext.getCmp('ags-regularPrice').getValue() * qty
                                    dadditional_cost_pc = 0;
                                    total_cost_pc = Number(dbasic_cost_pc) + Number(dadditional_cost_pc) + Number(Ext.getCmp('ags-otherCharges').getValue())
                                }
                                
                                Ext.getCmp('ags-basic_cost').setValue(dbasic_cost_pc)
                                Ext.getCmp('ags-additional_cost').setValue(dadditional_cost_pc)
                                Ext.getCmp('ags-total_cost').setValue(total_cost_pc)
                            }
                }, c);
                }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'REMARKS',
                name: 'remarks',
                anchor: '98%',
                hidden: true,
                id: 'ags-remarks'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'TOTAL COST',
                name: 'total_cost',
                anchor: '98%',
                allowBlank: false,
                hidden: true,
                id: 'ags-total_cost',
                readOnly:true
            }], 
            buttons: [{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('service-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/update/services.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                                
                                var grid = Ext.getCmp('ars')
                                srstore.load();
                                fnResetForm(srgrid);
                            },
                            failure: function(form, action) {
                                //Ext.Msg.alert('Warning', 'Error in updating Service Request');
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                                
                                var grid = Ext.getCmp('ars')
                                fnResetForm(srgrid);
                            }
                        })
                    }
                }
            },{
                text: 'Reset',
                disabled: false,
                handler: function() {
                    fnResetForm(srgrid);
                }
            }]
        }]
    
    });
    
   
    var argrid = new Ext.FormPanel({
        frame: true,
        title: 'CREATE REQUEST',
        //bodyPadding: 20,
        padding: 2,
        width: 700,
        id: 'amenity-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns

        fieldDefaults: {
            //labelAlign: 'left',
            msgTarget: 'side'
        },
        
        items: [{
            columnWidth: 0.65,
            xtype: 'grid',
            cls: 'x-panel-header',
            id:'arg',
            store: store,
            //cls:'x-panel-blue',
            width: 400,
            height:459,
            title:'CREATE REQUEST',
            
            columns: [
            {
                header: "STATUS",
                width:90,
                sortable: true,
                dataIndex:'status',
                renderer:customRenderer
            }, /*{
                header: "REQUEST ID",
                width:100,
                sortable: true,
                dataIndex:'requestId'
            },
            {
                header: "ID",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'id'
            },
            {
                header: "HOUSE ID",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'houseId'
            },{
                header: "REGULAR PRICE",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'regularPrice'
            },{
                header: "EXCESS PRICE",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'excessPrice'
            },{
                header: "MAX REGULAR",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'maxRegular'
            },{
                header: "UOM",
                width: 170,
                hidden:true,
                sortable: true,
                dataIndex: 'uom'
            },*/
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
                header: "AMENITY",
                width: 160,
                sortable: true,
                dataIndex: 'des'
            },
            {
                header: "REQUESTED DATE",
                width: 106.9,
                sortable: true,
                dataIndex: 'requestedDate'
            }/*,
            {
                header: "START TIME",
                width: 150,
                sortable: true,
                dataIndex: 'startTime'
            },

            {
                header: "END TIME",
                width: 150,
                sortable: true,
                dataIndex: 'endTime'
            },
            {
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
                header: "REMARKS",
                width: 170,
                sortable: true,
                dataIndex: 'remarks'
            },{
                header: "TOTAL COST",
                width: 170,
                sortable: true,
                dataIndex: 'total_cost'
            }*/
            ],
            listeners: {
                'rowclick':  function(grid,rowIndex,e){
                    
                    var record = grid.getStore().getAt(rowIndex);
                    var id = record.get('id');
                    var requestId = record.get('requestId');
                    var status = record.get('status');
                    var address = record.get('address')
                    var requestor = record.get('requestor')
                    var amenity = record.get('des')
                    var requestedDate = record.get('requestedDate')
                    var startTime = record.get('startTime')
                    var endTime = record.get('endTime')
                    var quantity = record.get('quantity')
                    var basic_cost = record.get('basic_cost')
                    var additional_cost = record.get('additional_cost')
                    var total_cost = record.get('total_cost')
                    var uom = record.get('uom')
                    var regularPrice = record.get('regularPrice')
                    var excessPrice = record.get('excessPrice')
                    var maxRegular = record.get('maxRegular')
                    var otherCharges = record.get('otherCharges')
                    var remarks = record.get('remarks')
                    
                    Ext.getCmp('agf-id').setValue(id)
                    Ext.getCmp('agf-requestId').setValue(requestId)
                    Ext.getCmp('agf-address').setValue(address)
                    Ext.getCmp('agf-requestor').setValue(requestor)
                    Ext.getCmp('agf-status').setValue(status)
                    Ext.getCmp('agf-amenity').setValue(amenity)
                    Ext.getCmp('agf-quantity').setValue(quantity)
                    Ext.getCmp('agf-requestedDate').setValue(requestedDate)
                    Ext.getCmp('agf-startTime').setValue(startTime)
                    Ext.getCmp('agf-endTime').setValue(endTime)
                    Ext.getCmp('agf-basic_cost').setValue(basic_cost)
                    Ext.getCmp('agf-additional_cost').setValue(additional_cost)
                    Ext.getCmp('agf-total_cost').setValue(total_cost)
                    Ext.getCmp('agf-uom').setValue(uom)
                    Ext.getCmp('agf-regularPrice').setValue(regularPrice)
                    Ext.getCmp('agf-excessPrice').setValue(excessPrice)
                    Ext.getCmp('agf-maxRegular').setValue(maxRegular)
                    Ext.getCmp('agf-otherCharges').setValue(otherCharges)
                    Ext.getCmp('agf-remarks').setValue(remarks)
                   
                    if(status == 'Done'){
                         Ext.getCmp('agf-status').disable();
                    }else{
                        Ext.getCmp('agf-status').enable();
                    }
                    
                    //Ext.getCmp('agf-requestId').disable();
                    //Ext.getCmp('agf-address').disable();
                    //Ext.getCmp('agf-requestor').disable();
                    //Ext.getCmp('agf-amenity').disable();
                    //Ext.getCmp('agf-basic_cost').disable();
                    //Ext.getCmp('agf-additional_cost').disable();
                    //Ext.getCmp('agf-total_cost').disable();
                    
                    if(status == 'Booked' | status == 'Reserved' | status == 'Done'){
                        Ext.getCmp('agf-basic_cost').show();
                        Ext.getCmp('agf-additional_cost').show();
                        Ext.getCmp('agf-maxRegular').hide();
                        Ext.getCmp('agf-otherCharges').show();
                        Ext.getCmp('agf-remarks').show();
                        Ext.getCmp('agf-total_cost').show();
                    }else{
                        Ext.getCmp('agf-basic_cost').hide();
                        Ext.getCmp('agf-additional_cost').hide();
                        Ext.getCmp('agf-maxRegular').hide();
                        Ext.getCmp('agf-otherCharges').hide();
                        Ext.getCmp('agf-remarks').hide();
                        Ext.getCmp('agf-total_cost').hide();
                    }
                    
                    if(uom == 'hr'){
                        Ext.getCmp('agf-quantity').hide()
                        Ext.getCmp('agf-startTime').show()
                        Ext.getCmp('agf-endTime').show()
                    }
                    
                    if(uom == 'pc'){
                        Ext.getCmp('agf-quantity').show()
                        Ext.getCmp('agf-startTime').hide()
                        Ext.getCmp('agf-endTime').hide()
                    }
                    
                    if(uom == 'hph'){
                        Ext.getCmp('agf-quantity').show()
                        Ext.getCmp('agf-startTime').show()
                        Ext.getCmp('agf-endTime').show()
                    }
                    
                    if(uom == 'day'){
                        Ext.getCmp('agf-quantity').hide()
                        Ext.getCmp('agf-startTime').hide()
                        Ext.getCmp('agf-endTime').hide()
                    }
                }
            }
        },{
            columnWidth: 0.35,
           // margin: '10 0 0 0',
            padding: 20,
            xtype: 'fieldset',
            title:'Reservation details',
            height:470,
            
           /* defaults: {
                width:200,
                labelWidth: 200,
                labelAlign:'center'
            },*/
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'ID',
                name: 'id',
                id: 'agf-id',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'UOM',
                name: 'uom',
                id: 'agf-uom',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'MAX REGULAR',
                name: 'maxRegular',
                id: 'agf-maxRegular',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'REGULAR PRICE',
                name: 'regularPrice',
                id: 'agf-regularPrice',
                anchor: '98%',
                hidden:true
            },{
                fieldLabel: 'EXCESS PRICE',
                name: 'excessPrice',
                id: 'agf-excessPrice',
                anchor: '98%',
                hidden:true
            },
            {
                fieldLabel: 'STATUS',
                name: 'status',
                id: 'agf-status',
                xtype:'combo',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Reserved', 'Reserved'],
                    ['Booked', 'Booked'],
                    ['Cancel', 'Cancel'],
                    ['Done', 'Done'],
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
                //allowBlank: false,
                blankText: 'Status should not be null',
                anchor: '98%',
                listeners:{
                    select: function(field, newValue, oldValue){
                         var limit = Ext.getCmp('agf-maxRegular').getValue()
                         var qty = Ext.getCmp('agf-quantity').getValue()
                          
                         if(field.value == "Reserved" | field.value == "Booked"){
                            Ext.getCmp('agf-basic_cost').show();
                            Ext.getCmp('agf-additional_cost').show();
                            Ext.getCmp('agf-otherCharges').show();
                            Ext.getCmp('agf-remarks').show();
                            Ext.getCmp('agf-total_cost').show();
                            var variance = 0.0;
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'hr'){
                                
                                var start = new Date("December 25, 1995 "+Ext.getCmp('agf-startTime').getValue())
                                var end = new Date("December 25, 1995 "+Ext.getCmp('agf-endTime').getValue())
                                var diff = end.getHours() - start.getHours()
                                var basic_cost_hr = 0.0
                                var additional_cost_hr = 0.0
                                
                                if(limit <= 1){
                                    basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue() * diff
                                    
                                }else{
                                    if(diff > limit){
                                        basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                        variance = diff - limit;
                                        additional_cost_hr = Ext.getCmp('agf-excessPrice').getValue() * variance
                                    }else{
                                        basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                    } 
                                }
                               
                                Ext.getCmp('agf-basic_cost').setValue(basic_cost_hr)
                                Ext.getCmp('agf-additional_cost').setValue(additional_cost_hr)
                            }
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'pc'){
                                Ext.getCmp('agf-quantity').show();
                                var basic_cost_hph = 0.0;
                                var additional_cost_hph = 0.0;
                                
                                if(limit <= 1){
                                    basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue() * qty
                                }else{
                                    if(qty > limit){
                                        var diffHph = qty - limit
                                        basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                        additional_cost_hph = Ext.getCmp('agf-excessPrice').getValue() * diffHph
                                    }else if(qty <= limit){
                                        basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                    } 
                                }
                                
                                Ext.getCmp('agf-basic_cost').setValue(basic_cost_hph)
                                Ext.getCmp('agf-additional_cost').setValue(additional_cost_hph)
                            }
                            
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'day'){
                                var basic_cost_day = Ext.getCmp('agf-regularPrice').getValue()
                                Ext.getCmp('agf-basic_cost').setValue(basic_cost_day)
                                
                            }
                            if(Ext.getCmp('agf-uom').getValue() == 'hph'){
                                var basic_cost_pc = Ext.getCmp('agf-regularPrice').getValue() * Ext.getCmp('agf-quantity').getValue()
                                Ext.getCmp('agf-basic_cost').setValue(basic_cost_pc)
                            }
                           
                         }
                         
                         var total_cost = 0.0;
                         if(field.value == "Done"){
                            Ext.getCmp('agf-basic_cost').show();
                            Ext.getCmp('agf-additional_cost').show();
                            Ext.getCmp('agf-otherCharges').show();
                            Ext.getCmp('agf-remarks').show();
                            Ext.getCmp('agf-total_cost').show();
                            
                                var done_start = new Date("December 25, 1995 "+Ext.getCmp('agf-startTime').getValue())
                                var done_end = new Date("December 25, 1995 "+Ext.getCmp('agf-endTime').getValue())
                                var done_diff = done_end.getHours() - done_start.getHours()
                                var done_basic_cost_hr = 0.0
                                var done_additional_cost_hr = 0.0
                                
                                if(limit <= 1){
                                    done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue() * done_diff
                                }else{
                                    if(done_diff >= limit){
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                        variance = done_diff - limit;
                                        done_additional_cost_hr = Ext.getCmp('agf-excessPrice').getValue() * variance
                                    }else{
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                    } 
                                }
                                
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hr)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hr)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'day'){
                                var cbasic_cost_day = Ext.getCmp('agf-regularPrice').getValue()
                                Ext.getCmp('agf-basic_cost').setValue(cbasic_cost_day)
                                
                            }
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'pc'){
                                var done_basic_cost_hph = 0.0;
                                var done_additional_cost_hph = 0.0;
                                if(limit <= 1){
                                    done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue() * qty
                                }else{
                                    if(qty > limit){
                                        var done_diffHph = qty - limit
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                        done_additional_cost_hph = Ext.getCmp('agf-excessPrice').getValue() * done_diffHph
                                    }else if(qty <= limit){
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                    }
                                }
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hph)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hph)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            }
                           
                         }
                         
                         if(field.value == "Re-Scheduled" | field.value == "Cancel"){
                            Ext.getCmp('agf-quantity').setValue(0)
                            Ext.getCmp('agf-basic_cost').setValue(0)
                            Ext.getCmp('agf-additional_cost').setValue(0)
                            Ext.getCmp('agf-otherCharges').setValue(0)
                            Ext.getCmp('agf-remarks').setValue("")
                            Ext.getCmp('agf-total_cost').setValue(0)
                            Ext.getCmp('agf-basic_cost').hide();
                            Ext.getCmp('agf-additional_cost').hide();
                            Ext.getCmp('agf-otherCharges').hide();
                            Ext.getCmp('agf-remarks').hide();
                            Ext.getCmp('agf-total_cost').hide();
                         }
                    }
                }
                
            },{
                fieldLabel: 'REQUEST ID',
                name: 'requestId',
                id: 'agf-requestId',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'ADDRESS',
                name: 'address',
                id: 'agf-address',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'REQUESTOR',
                name: 'requestor',
                id: 'agf-requestor',
                anchor: '98%',
                readOnly:true
            },{
                fieldLabel: 'AMENITY',
                name: 'des',
                anchor: '98%',
                id: 'agf-amenity',
                readOnly:true
            },{
                fieldLabel: 'REQUESTED DATE',
                name: 'requestedDate',
                cls: 'cTextAlign', 
                readOnly:true,
                id: 'agf-requestedDate',
                anchor: '98%'
            },{
                xtype: 'timefield',
                cls: 'cTextAlign',
                fieldLabel: 'START TIME',
                name: 'startTime',
                anchor: '98%',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                id: 'agf-startTime'
            },{
                xtype: 'timefield',
                cls: 'cTextAlign',
                fieldLabel: 'END TIME',
                name: 'endTime',
                anchor: '98%',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                id: 'agf-endTime'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'QUANTITY',
                name: 'quantity',
                anchor: '98%',
                //allowBlank: false,
                id: 'agf-quantity',
                listeners:{
                     'render': function(c) {
                          c.getEl().on('keyup', function() {
                                var variance = 0.0;
                            var total_cost = 0.0;
                            var limit = Ext.getCmp('agf-maxRegular').getValue()
                            var qty = Ext.getCmp('agf-quantity').getValue()
                            Ext.getCmp('agf-basic_cost').show();
                            Ext.getCmp('agf-additional_cost').show();
                            Ext.getCmp('agf-otherCharges').show();
                            Ext.getCmp('agf-remarks').show();
                            Ext.getCmp('agf-total_cost').show();
                            
                                var done_start = new Date("December 25, 1995 "+Ext.getCmp('agf-startTime').getValue())
                                var done_end = new Date("December 25, 1995 "+Ext.getCmp('agf-endTime').getValue())
                                var done_diff = done_end.getHours() - done_start.getHours()
                                var done_basic_cost_hr = 0.0
                                var done_additional_cost_hr = 0.0
                                
                                if(limit <= 1){
                                    done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue() * done_diff
                                }else{
                                    if(done_diff >= limit){
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                        variance = done_diff - limit;
                                        done_additional_cost_hr = Ext.getCmp('agf-excessPrice').getValue() * variance
                                    }else{
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                    } 
                                }
                                
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hr)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hr)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'day'){
                                var cbasic_cost_day = Ext.getCmp('agf-regularPrice').getValue()
                                Ext.getCmp('agf-basic_cost').setValue(cbasic_cost_day)
                                
                            }
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'pc'){
                                var done_basic_cost_hph = 0.0;
                                var done_additional_cost_hph = 0.0;
                                if(limit <= 1){
                                    done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue() * qty
                                }else{
                                    if(qty > limit){
                                        var done_diffHph = qty - limit
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                        done_additional_cost_hph = Ext.getCmp('agf-excessPrice').getValue() * done_diffHph
                                    }else if(qty <= limit){
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                    }
                                }
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hph)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hph)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            }
                          }, c);     
                    }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'BASIC COST',
                name: 'basic_cost',
                anchor: '98%',
                readOnly:true,
                id: 'agf-basic_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'ADDITIONAL COST',
                name: 'additional_cost',
                anchor: '98%',
                readOnly:true,
                //allowBlank: false,
                id: 'agf-additional_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'OTHER CHARGES',
                name: 'otherCharges',
                anchor: '98%',
                //allowBlank: false,
                id: 'agf-otherCharges',
                listeners:{
                     'render': function(c) {
                          c.getEl().on('keyup', function() {
                                var variance = 0.0;
                            var total_cost = 0.0;
                            var limit = Ext.getCmp('agf-maxRegular').getValue()
                            var qty = Ext.getCmp('agf-quantity').getValue()
                            Ext.getCmp('agf-basic_cost').show();
                            Ext.getCmp('agf-additional_cost').show();
                            Ext.getCmp('agf-otherCharges').show();
                            Ext.getCmp('agf-remarks').show();
                            Ext.getCmp('agf-total_cost').show();
                            
                                var done_start = new Date("December 25, 1995 "+Ext.getCmp('agf-startTime').getValue())
                                var done_end = new Date("December 25, 1995 "+Ext.getCmp('agf-endTime').getValue())
                                var done_diff = done_end.getHours() - done_start.getHours()
                                var done_basic_cost_hr = 0.0
                                var done_additional_cost_hr = 0.0
                                
                                if(limit <= 1){
                                    done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue() * done_diff
                                }else{
                                    if(done_diff >= limit){
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                        variance = done_diff - limit;
                                        done_additional_cost_hr = Ext.getCmp('agf-excessPrice').getValue() * variance
                                    }else{
                                        done_basic_cost_hr = Ext.getCmp('agf-regularPrice').getValue()
                                    } 
                                }
                                
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hr)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hr)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'day'){
                                var cbasic_cost_day = Ext.getCmp('agf-regularPrice').getValue()
                                Ext.getCmp('agf-basic_cost').setValue(cbasic_cost_day)
                                
                            }
                            
                            if(Ext.getCmp('agf-uom').getValue() == 'pc'){
                                var done_basic_cost_hph = 0.0;
                                var done_additional_cost_hph = 0.0;
                                if(limit <= 1){
                                    done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue() * qty
                                }else{
                                    if(qty > limit){
                                        var done_diffHph = qty - limit
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                        done_additional_cost_hph = Ext.getCmp('agf-excessPrice').getValue() * done_diffHph
                                    }else if(qty <= limit){
                                        done_basic_cost_hph = Ext.getCmp('agf-regularPrice').getValue()
                                    }
                                }
                                Ext.getCmp('agf-basic_cost').setValue(done_basic_cost_hph)
                                Ext.getCmp('agf-additional_cost').setValue(done_additional_cost_hph)
                                total_cost = Number(Ext.getCmp('agf-basic_cost').getValue()) + Number(Ext.getCmp('agf-additional_cost').getValue()) + Number(Ext.getCmp('agf-otherCharges').getValue())
                                Ext.getCmp('agf-total_cost').setValue(total_cost)
                            }
                          }, c);     
                    }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'REMARKS',
                name: 'remarks',
                anchor: '98%',
                id: 'agf-remarks'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'TOTAL COST',
                name: 'total_cost',
                anchor: '98%',
                //allowBlank: false,
                id: 'agf-total_cost',
                readOnly:true
            }], 
            buttons: [{
                id: 'mf.btn.add',
                text: 'Submit',
                handler: function() {
                    //fnAdminAmenitiesUpdateForm(argrid);
                    var form = Ext.getCmp('amenity-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/update/amenities.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Amenities Request');
                                
                                var grid = Ext.getCmp('arg')
                                store.load();
                                fnResetForm(argrid);
                            },
                            failure: function(form, action) {
                                //Ext.Msg.alert('Warning', 'Error in updating Amenities Request');
                                Ext.Msg.alert('Success', 'Successfully updated Amenities Request');
                                
                                var grid = Ext.getCmp('arg')
                                store.load();
                                fnResetForm(argrid);
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
    
    //argrid.getSelectionModel().getSelections();
    
    
   
    /*var treeDivs = '<div id="amenity-tree-div"></div>' +
    '<div id="service-tree-div"></div>' +
    '<div id="employee-tree-div"></div>' +
    '<div id="vehicle-tree-div"></div>';*/

    var adminBorderPanel = new Ext.Panel({
        renderTo: 'my-tabs',
        width: 1200,
        height: 580,
        title: 'Portal Admin Menu',
        layout:'border',
        defaults: {
            collapsible: true,
            split: true,
            bodyStyle: 'padding:5px'
        },
        items: [/*{
            title: 'Alerts List',
            region:'west',
            margins: '5 0 0 0',
            cmargins: '5 5 0 0',
            width: 210,
            minSize: 100,
            maxSize: 250,
            html: treeDivs
        },*/{
            title: 'Main Content',
            collapsible: false,
            region:'center',
            margins: '5 0 0 0',
            xtype: 'tabpanel',
            activeTab: 0, // index or id
            // items: [argrid,srgrid,vgrid,eGrid]  // temporarily disabled vehicle and employee request, not part of the deliverable
            items: [srgrid] // RACS
            
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

function fnAdminAmenitiesUpdateForm(theForm)
{
    theForm.getForm().submit({
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
    // Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('argrid').getStore().load()
    Ext.getCmp('srgrid').getStore().load()
    Ext.getCmp('eGrid').getStore().load()
    Ext.getCmp('vgrid').getStore().load()
}