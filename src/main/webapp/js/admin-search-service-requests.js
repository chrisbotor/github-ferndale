

// alert('on admin-search-service-requests.js!');

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
            store: srstore,
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
                    
                    // alert('UOM: ' + uom);
                    
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
                    
                    
                    // load the STATUS combo box on the Reservation Details based on the current status
                    // fnPopulateStatusSelectBox(status);
                    
                }
            }
        },{
            columnWidth: 0.35,
            margin: '10 0 0 0',
            xtype: 'fieldset',
            title:'Service details',
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
	                    ['Confirmed', 'Confirmed'], // CHRIS
	                    ['Done', 'Done'],
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
                        	 // alert('basicCostFromDB: ' + basicCostFromDB);
                        	 
                        	 // Ext.getCmp('hiddenBasicCost').setValue(basicCostFromDB);
                        	 
                        	// alert('basic cost: ' + Ext.getCmp('ags-basic_cost').getValue());
                        	
                        	// Ext.getCmp('ags-basic_cost').setValue(Ext.getCmp('hiddenBasicCost').getValue());
                        	
                            
                            
                            // alert('basic cost AFTER: ' + Ext.getCmp('ags-basic_cost').getValue());
                            
                            /*Ext.getCmp('ags-additional_cost').show();
                            Ext.getCmp('ags-otherCharges').show();
                            Ext.getCmp('ags-remarks').show();
                            Ext.getCmp('ags-total_cost').show();
                            */
                            
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
                        	 // alert('STATUS: ' + field.value);
                        	 
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
                        	 
                        	 
                        	 // alert(Ext.getCmp('ags-uom').getValue());
                        	 
                            if(Ext.getCmp('ags-uom').getValue() == 'hph')
                            {
                            	// alert(Ext.getCmp('ags-uom').getValue()); // NOT CALLED DURING SEARCH
                            	// alert('QUANTITY: ' + qty); // NOT CALLED DURING SEARCH
                            	
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
                        	 
                            // Ext.getCmp('ags-quantity').setValue(0)
                        	// Ext.getCmp('ags-basic_cost').setValue(0)
                        	//  Ext.getCmp('ags-additional_cost').setValue(0)
                        	//  Ext.getCmp('ags-otherCharges').setValue(0)
                        	//  Ext.getCmp('ags-remarks').setValue("")
                            // Ext.getCmp('ags-total_cost').setValue(0)
                            
                        	 
                        	 Ext.getCmp('ags-quantity').hide();
                            Ext.getCmp('ags-basic_cost').hide();
                            Ext.getCmp('ags-additional_cost').hide();
                            Ext.getCmp('ags-otherCharges').hide();
                            Ext.getCmp('ags-remarks').show();
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
                            {
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
                hidden: true,
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
                                //window.location = 'admin-request.action';
                                var grid = Ext.getCmp('ars')
                                srstore.load();
                                fnResetForm(srgrid);
                                
                                // location.reload(true);
                            },
                            failure: function(form, action) {
                                //Ext.Msg.alert('Warning', 'Error in updating Service Request');
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                                //window.location = 'admin-request.action';
                                var grid = Ext.getCmp('ars');
                                srstore.load();
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
    
   
      
    
    /*
     * Default is to show both tabs (Amenity and Service Requests) in the home page.
     * There is a logic in both admin-search-amenity-requests.js and admin-search-service-requests.js to display only the respective tabs
     * based on which search service is calling it
     * Ex: Display only the Amenity Requests tab if the admin is searching for amenity requests. Same is true for Service Requests.
     * */
    /*var adminBorderPanel = new Ext.Panel({
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
        items: [{
            title: 'Main Content',
            collapsible: false,
            region:'center',
            margins: '5 0 0 0',
            xtype: 'tabpanel',
            activeTab: 0, // index or id
            // items: [argrid,srgrid,vgrid,eGrid]  // temporarily disabled vehicle and employee request, not part of the deliverable
            items: [argrid, srgrid]
            
        }]
    });*/
    
    
    if (callingPage == 'admin-search-service-requests.action')
    {
    	// MAIN PANEL that holds the grid
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
            items: [{
                title: 'Main Content',
                collapsible: false,
                region:'center',
                margins: '5 0 0 0',
                xtype: 'tabpanel',
                activeTab: 0, // index or id            
                items: [srgrid]
            }]
        });
    }
    if (callingPage == 'admin-home.action')
    {
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
            items: [{
                title: 'Main Content',
                collapsible: false,
                region:'center',
                margins: '5 0 0 0',
                xtype: 'tabpanel',
                activeTab: 0, // index or id
                // items: [argrid,srgrid,vgrid,eGrid]  // temporarily disabled vehicle and employee request, not part of the deliverable
                items: [argrid, srgrid]
                
            }]
        });
    }
    
    
// });




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
        	removeXButton(Ext.Msg.alert('Success', 'Successfully updated Service Request'));
        },
        failure: function(form, action) {
        	removeXButton(Ext.Msg.alert('Warning', form.name));
        }
    });
}


function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    // Ext.getCmp('mf.btn.reset').setDisabled(false);
} 

function loadStore()
{
    Ext.getCmp('argrid').getStore().load()
    Ext.getCmp('srgrid').getStore().load()
    Ext.getCmp('eGrid').getStore().load()
    Ext.getCmp('vgrid').getStore().load()
}

//populate request combo box based on the request current status
function fnPopulateStatusSelectBox(currentStatus) // this is a work in progress
{
	// alert('Setting values for STATUS...');
	
	var statusCombo = 	Ext.getCmp('ags-status');
	
	statusCombo.clearValue(); 
	
	
	var newStore = new Ext.data.ArrayStore({
        fields: ['abbr', 'action'],
        data : [
                ['Racs', 'Racs'], // ECY
                ['Chris', 'Chris'],
                ['Ecy', 'Ecy']
            ]
    });
	
	
	// alert("newStore data:  " + newStore.data.items);
	
	
	// retrieve amenity/service request based on the jobOrderTypeId
	statusCombo.store.loadData(newStore.data.items);
	
	// statusCombo.store.load(newStore.data.items);
	
	/*statusCombo.store.reload({
		 data : [
	                ['Racs', 'Racs'], // ECY
	                ['Chris', 'Chris'],
	                ['Ecy', 'Ecy']
	            ]
	})*/

	
	 
	
	
}