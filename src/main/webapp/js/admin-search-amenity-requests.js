/**
 * Author: Racquel A. Botor
 * Date: 2014-08-14
 * This javascript is used for calculating the total amount due once the status of the request is changed.
 *  
 * */

    // alert('on admin-search-amenity-requests.js!');
    
    // GOT THIS VALUE FROM admin-load-amenity-requests.js
    // alert('GOT the callingPage: ' + callingPage);
    
       
// ########################################################################   GRID   #################################################################
   
    var argrid = new Ext.FormPanel({
        frame: true,
        title: 'AMENITY REQUEST',
        //bodyPadding: 20,
        padding: 2,
        width: 700,
        id: 'amenity-form',
        layout: 'column',    // Specifies that the items will now be arranged in columns
        fieldDefaults: {
            msgTarget: 'side'
        },
        items: [
        {
            columnWidth: 0.65,
            xtype: 'grid',
            cls: 'x-panel-header',
            id:'arg',
            store: arstore,
            //cls:'x-panel-blue',
            width: 400,
            height:500,
            title:'AMENITY REQUEST',
            
            columns: [
            {
                header: "STATUS",
                width:90,
                sortable: true,
                dataIndex:'status',
                renderer:customRenderer
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
            }],
            listeners:
            {
                'rowclick':  function(grid,rowIndex,e)
                {
	                // If a record in the grid is clicked, the right-side box will be populated with details
                	// alert("YES I'M CLICKED!");
	                var record = grid.getStore().getAt(rowIndex);
	                var status = record.get('status');
	                var uom = record.get('uom');
	                	
	                fnSetFieldValues(record);
	                fnShowAndHideFieldValues(status, uom);
	                fnSetQuantityBasedOnUOM(uom);
                }
            }
        },
        {
        	columnWidth: 0.35,
            padding: 20,
            xtype: 'fieldset',
            title:'Reservation details',
            height:512,
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
                fieldLabel: 'REGULAR PRICE',
                name: 'regularPrice',
                id: 'agf-regularPrice',
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
                    data : 
                    [
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
                listeners:
                {
                    select: function(field, newValue, oldValue)
                    {
                         var qty = Ext.getCmp('agf-quantity').getValue();
                         var status = field.value;
                         var uom = Ext.getCmp('agf-uom').getValue();
                          
                         // alert("SELECTED: " + field.value);
                         
                         // RACS
                         if(status == 'Reserved' | status == 'Booked' | status == 'Done')
                         {
                            fnShowAndHideFieldValues(status, uom);
                            fnSetHoursUsed();                           
                    		fnSetTotalCost(status, uom);
                        }
                         
                         if(status == 'Cancel')
                         {
                        	 fnSetValuesForCancel();
                         }
                    }
                }
                
            },{
                fieldLabel: 'REQUEST ID',
                name: 'requestId',
                id: 'agf-requestId',
                anchor: '98%',
                readOnly:true
            },
            {
                fieldLabel: 'ADDRESS',
                name: 'address',
                id: 'agf-address',
                anchor: '98%',
                readOnly:true
            },/*{
                fieldLabel: 'REQUESTOR',  // This is disabled to be able to add HOURS USED field
                name: 'requestor',
                id: 'agf-requestor',
                anchor: '98%',
                readOnly:true
            },*/{
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
                // editable:false, // NOTE: THIS IS NOT WORKING! TIME IS STILL EDITABLE!
                id: 'agf-startTime',
                listeners:{
                	select:  function(combo, record, index){
                		
                		var startTime = new Date("December 25, 1995 " + combo.getValue());
                		// alert('START TIME: ' + startTime);
                		
                		fnSetHoursUsed();
                		
                		// get the total cost based on status and uom
                		var status = Ext.getCmp('agf-status').getValue();
                		// alert('status: ' + status);
                		var uom = Ext.getCmp('agf-uom').getValue();
                		// alert('uom: ' + uom);
                		fnSetTotalCost(status, uom);
                	}
                }
            },{
                xtype: 'timefield',
                cls: 'cTextAlign',
                fieldLabel: 'END TIME',
                name: 'endTime',
                anchor: '98%',
                minValue: '6:00 AM',
                maxValue: '10:00 PM',
                increment: 60,
                // editable:false, // NOTE: THIS IS NOT WORKING! TIME IS STILL EDITABLE!
                id: 'agf-endTime',
                listeners:{
                	select:  function(combo, record, index){
                   
                		var endTime = new Date("December 25, 1995 " + combo.getValue());
                		// alert('END TIME: ' + endTime);
                		
                		fnSetHoursUsed();
                		
                		// get the total cost based on status and uom
                		var status = Ext.getCmp('agf-status').getValue();
                		// alert('status: ' + status);
                		var uom = Ext.getCmp('agf-uom').getValue();
                		// alert('uom: ' + uom);
                		fnSetTotalCost(status, uom);
                	}
                }
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
                          c.getEl().on('keyup', function(){
                        	  
                        	  var status = Ext.getCmp('agf-status').getValue();
                              var uom = Ext.getCmp('agf-uom').getValue();
                               
                              fnSetTotalCost(status, uom);
                              
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
                fieldLabel: 'MAX REGULAR',
                name: 'maxRegular',
                id: 'agf-maxRegular',
                anchor: '98%',
                hidden:true
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                // fieldLabel: 'COST PER ADDITIONAL HR',
                fieldLabel: 'COST PER ADDITIONAL',
                name: 'additional_cost',
                anchor: '98%',
                readOnly:true,
                //allowBlank: false,
                id: 'agf-additional_cost'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'HOURS USED',
                name: 'hours_used',
                anchor: '98%',
                readOnly:true,
                id: 'agf-hours_used'
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
                        	  	// get the total cost based on status and uom
                      			var status = Ext.getCmp('agf-status').getValue();
                      			// alert('status: ' + status);
                      			var uom = Ext.getCmp('agf-uom').getValue();
                      			// alert('uom: ' + uom);
                      		             
                      			 // this is only applicable if the cost is per hour. should create a method that takes uom and compute based on the uom.
                      			fnSetTotalCost(status, uom);
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
                handler: function()
                {
                    var form = Ext.getCmp('amenity-form').getForm();
                    if(form.isValid()){
                        form.submit({
                            url: 'admin/update/amenities.action',
                            waitMsg: 'loading...',
                            success: function(form, action) {
                            	removeXButton(Ext.Msg.alert('Success', 'Successfully updated Amenities Request'));
                                //window.location = 'admin-request.action';
                                var grid = Ext.getCmp('arg');
                                arstore.load();
                                fnResetForm(argrid);
                            },
                            failure: function(form, action) {
                                //Ext.Msg.alert('Warning', 'Error in updating Amenities Request');
                            	removeXButton(Ext.Msg.alert('Success', 'Successfully updated Amenities Request'));
                                //window.location = 'admin-request.action';
                                var grid = Ext.getCmp('arg')
                                arstore.load();
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
    
  
    
    

// ########################################  CUSTOM FUNCTIONS  ##############################################

// set the field values in the Registration Details panel
function fnSetFieldValues(record)
{
    var id = record.get('id');
    var requestId = record.get('requestId');
    var status = record.get('status');
    var address = record.get('address');
    // var requestor = record.get('requestor');
    var amenity = record.get('des');
    var requestedDate = record.get('requestedDate');
    var startTime = record.get('startTime');
    var endTime = record.get('endTime');
    var quantity = record.get('quantity');
    var basic_cost = record.get('basic_cost');
    var total_cost = record.get('total_cost');
    var uom = record.get('uom');
    var regularPrice = record.get('regularPrice');
    var maxRegular = record.get('maxRegular');
    var otherCharges = record.get('otherCharges');
    var remarks = record.get('remarks');
    var excessPrice = record.get('excessPrice');
    // alert('===== MY requestId: ' + id);
    
    Ext.getCmp('agf-id').setValue(id);
    Ext.getCmp('agf-requestId').setValue(requestId);
    Ext.getCmp('agf-address').setValue(address);
    // Ext.getCmp('agf-requestor').setValue(requestor);
    Ext.getCmp('agf-status').setValue(status);
    Ext.getCmp('agf-amenity').setValue(amenity);
    Ext.getCmp('agf-quantity').setValue(quantity);
    Ext.getCmp('agf-requestedDate').setValue(requestedDate);
    Ext.getCmp('agf-startTime').setValue(startTime);
    Ext.getCmp('agf-endTime').setValue(endTime);
    Ext.getCmp('agf-basic_cost').setValue(basic_cost);
    Ext.getCmp('agf-additional_cost').setValue(excessPrice);
    Ext.getCmp('agf-total_cost').setValue(total_cost);
    Ext.getCmp('agf-uom').setValue(uom);
    Ext.getCmp('agf-regularPrice').setValue(regularPrice);
    Ext.getCmp('agf-maxRegular').setValue(maxRegular);
    Ext.getCmp('agf-otherCharges').setValue(otherCharges);
    Ext.getCmp('agf-remarks').setValue(remarks);
}




// SHOW and HIDE the fields in the Reservation Details panel based on the status and uom of the clicked request in the AMENITY REQUEST grid
function fnShowAndHideFieldValues(status, uom)
{
	// alert('Showing and hiding field values based on STATUS: ' + status);
	
	if(uom == 'hr')
	{
		if(status == 'Done')
	    {
	        Ext.getCmp('agf-basic_cost').show();
	        Ext.getCmp('agf-additional_cost').show();
	        Ext.getCmp('agf-otherCharges').show();
	        Ext.getCmp('agf-total_cost').show();
	        Ext.getCmp('agf-maxRegular').hide();
	        Ext.getCmp('agf-remarks').show();
	        Ext.getCmp('agf-hours_used').show();
	    }
	    else
	    {   
	    	// if(status == 'Booked' | status == 'Reserved'), execute the code below
	        Ext.getCmp('agf-basic_cost').hide();
	        Ext.getCmp('agf-additional_cost').hide();
	        Ext.getCmp('agf-otherCharges').hide();
	        Ext.getCmp('agf-total_cost').hide();
	        Ext.getCmp('agf-maxRegular').hide();
	        Ext.getCmp('agf-hours_used').hide();
	        Ext.getCmp('agf-remarks').show();
	        
	        
	        // hide the REMARKS for NEW requests
	        if (status == 'New') 
	        {
	        	 Ext.getCmp('agf-remarks').hide();
	        }
	    }
	}
	else if(uom == 'pc')
	{
		if(status == 'Done')
	    {
	        Ext.getCmp('agf-basic_cost').show();
	        Ext.getCmp('agf-additional_cost').show();
	        Ext.getCmp('agf-otherCharges').show();
	        Ext.getCmp('agf-total_cost').show();
	        Ext.getCmp('agf-remarks').show();
	        Ext.getCmp('agf-hours_used').hide();
	        Ext.getCmp('agf-maxRegular').hide();
	    }
	    else
	    {   
	    	// if(status == 'Booked' | status == 'Reserved'), execute the code below
	        Ext.getCmp('agf-basic_cost').hide();
	        Ext.getCmp('agf-additional_cost').hide();
	        Ext.getCmp('agf-otherCharges').hide();
	        Ext.getCmp('agf-total_cost').hide();
	        Ext.getCmp('agf-maxRegular').hide();
	        Ext.getCmp('agf-hours_used').hide();
	        Ext.getCmp('agf-remarks').show();
	        
	        
	        // hide the REMARKS for NEW requests
	        if (status == 'New') 
	        {
	        	 Ext.getCmp('agf-remarks').hide();
	        }
	    }
		
	}
}




// Set quantity in the Reservation Details panel based on UOM
function fnSetQuantityBasedOnUOM(uom)
{
	// alert('Setting quantity based on UOM: ' + uom);
	
	if(uom == 'hr')
    {
        Ext.getCmp('agf-quantity').hide();
        Ext.getCmp('agf-startTime').show();
        Ext.getCmp('agf-endTime').show();
    }
    
    if(uom == 'pc')
    {
        Ext.getCmp('agf-quantity').show();
        Ext.getCmp('agf-startTime').hide();
        Ext.getCmp('agf-endTime').hide();
    }
    
    if(uom == 'hph')
    {
        Ext.getCmp('agf-quantity').show();
        Ext.getCmp('agf-startTime').show();
        Ext.getCmp('agf-endTime').show();
    }
    
    if(uom == 'day')
    {
        Ext.getCmp('agf-quantity').hide();
        Ext.getCmp('agf-startTime').hide();
        Ext.getCmp('agf-endTime').hide();
    }
}






/**
 * Set the Total cost
 * */
function fnSetTotalCost(status, uom) //  ECY
{
	// alert("=== RACS Setting Total Cost...");
	
	var limit = Ext.getCmp('agf-maxRegular').getValue();
	// alert("limit: " + limit);
	
	var basic_cost_per_hr = Ext.getCmp('agf-basic_cost').getValue();
	// alert("basic_cost_per_hr: " + basic_cost_per_hr);
	
	var cost_per_additional_hr = Ext.getCmp('agf-additional_cost').getValue();
	// alert("cost_per_additional_hr: " + cost_per_additional_hr);
	
	var hours_used = Ext.getCmp('agf-hours_used').getValue();
    // alert("ECY hours_used: " + hours_used);
	
	var quantity = Ext.getCmp('agf-quantity').getValue();
    // alert("quantity: " + quantity);
    
    var other_charges = Ext.getCmp('agf-otherCharges').getValue();
    // alert("other_charges: " + other_charges);
    

    // COMPUTE BASED ON THE UOM
    if (uom == 'hr')
    {
    	fnSetCostPerHour(basic_cost_per_hr, cost_per_additional_hr, limit, hours_used, other_charges);
    }
    else if (uom == 'pc')
    {
    	fnSetCostPerPc(basic_cost_per_hr, cost_per_additional_hr, limit, quantity, other_charges);
    }
    else if(uom == 'day')
	{
		fnSetCostPerDay();
	}
    else if(uom == 'hph')
	{
		fnSetCostPerHph(basic_cost_per_hr, cost_per_additional_hr, limit, other_charges);
	}
    
}




// Set Basic cost and other charges based on per hour rates
function fnSetCostPerHour(basic_cost_per_hr, cost_per_additional_hr, limit, hours_used, other_charges)
{
	// alert('Setting cost based on per hour...');
	
	var total_cost = 0.0;
	var excess_hour = 0;
	
	if (cost_per_additional_hr > 0)
	{
		// compute with excess value, get the number of excess hours used
		if (hours_used > limit)
	    {
			// alert('HOURS USED > THAN THE LIMIT');
	    	excess_hour = hours_used - limit;
	    	// alert("excess_hour: " + excess_hour);
	    	
	    	// display the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').show();
	    	
	    	total_cost = (basic_cost_per_hr) + (cost_per_additional_hr * excess_hour) + other_charges;
	    }
	    else
	    {
	    	// hide the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').hide();
	    	total_cost = basic_cost_per_hr + other_charges;
	    }
	}
	else
	{
		// alert('COMPUTING FOR FIX RATE... ');
		
		// compute Fix Rate
		total_cost = basic_cost_per_hr * hours_used + other_charges;
	}
	
	// set the total cost value
	Ext.getCmp('agf-total_cost').setValue(total_cost);
}





/**
 * Set Basic cost and other charges based on per pc rates
 * **/
function fnSetCostPerPc(basic_cost_per_hr, cost_per_additional_hr, limit, quantity, other_charges)
{
	// alert('Setting cost based on per pc...');
	
	var total_cost = 0.0;
	var excess_price = 0;
	
	if (cost_per_additional_hr > 0)
	{
		// compute with excess value, get the number of excess hours used
		if (quantity > limit)
	    {
			// alert('HOURS USED > THAN THE LIMIT');
			excess_price = quantity - limit;
	    	// alert("excess_price: " + excess_price);
	    	
	    	// display the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').show();
	    	
	    	total_cost = (basic_cost_per_hr) + (cost_per_additional_hr * excess_price) + other_charges;
	    }
	    else
	    {
	    	// hide the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').hide();
	    	total_cost = basic_cost_per_hr + other_charges;
	    }
	}
	else
	{
		// alert('COMPUTING FOR FIX RATE... ');
		
		// compute Fix Rate
		total_cost = basic_cost_per_hr * quantity + other_charges;
	}
	
	// set the total cost value
	Ext.getCmp('agf-total_cost').setValue(total_cost);
}





//Set Basic cost and other charges based on per day rates
function fnSetCostPerDay()
{
	var basic_cost_day = Ext.getCmp('agf-regularPrice').getValue();
	Ext.getCmp('agf-basic_cost').setValue(basic_cost_day);
}




//Set Basic cost and other charges based on per hph rates
function fnSetCostPerHph(basic_cost_per_hr, cost_per_additional_hr, limit, other_charges)
{
	// alert('Setting cost based on hph...');
	
	/*var total_cost = 0.0;
	var excess_price = 0;
	
	if (limit == 0)
	{
		total_cost = (basic_cost_per_hr) + (cost_per_additional_hr * excess_price) + other_charges;
	}
	
	
	if (cost_per_additional_hr > 0)
	{
		// compute with excess value, get the number of excess hours used
		if (quantity > limit)
	    {
			// alert('HOURS USED > THAN THE LIMIT');
			excess_price = quantity - limit;
	    	// alert("excess_price: " + excess_price);
	    	
	    	// display the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').show();
	    	
	    	total_cost = (basic_cost_per_hr) + (cost_per_additional_hr * excess_price) + other_charges;
	    }
	    else
	    {
	    	// hide the COST PER ADDITIONAL field
	    	Ext.getCmp('agf-additional_cost').hide();
	    	total_cost = basic_cost_per_hr + other_charges;
	    }
	}
	else
	{
		alert('COMPUTING FOR FIX RATE... ');
		
		// compute Fix Rate
		total_cost = basic_cost_per_hr * quantity + other_charges;
	}
	
	// set the total cost value
	Ext.getCmp('agf-total_cost').setValue(total_cost);
	
	
	var basic_cost_pc = Ext.getCmp('agf-regularPrice').getValue() * Ext.getCmp('agf-quantity').getValue();
    Ext.getCmp('agf-basic_cost').setValue(basic_cost_pc);*/
}


/**
 * Set the number of Hours used when either start time or end time changes
 * */
function fnSetHoursUsed()
{
	// alert("Setting hours used...");
	
	var startTime = new Date("December 25, 1995 " + Ext.getCmp('agf-startTime').getValue());
	// alert('START TIME: ' + startTime);
	
	var endTime = new Date("December 25, 1995 " + Ext.getCmp('agf-endTime').getValue())
	// alert('END TIME: ' + endTime);
	 	
	var hrsUsed = endTime.getHours() - startTime.getHours();
	// alert('hours used: ' + hrsUsed);
	
	Ext.getCmp('agf-hours_used').setValue(hrsUsed);
}




// Set values for Cancelled requests
function fnSetValuesForCancel()
{
	Ext.getCmp('agf-basic_cost').hide();
	Ext.getCmp('agf-additional_cost').hide();
	Ext.getCmp('agf-otherCharges').hide();
	Ext.getCmp('agf-remarks').show();
	Ext.getCmp('agf-total_cost').hide();
}




function customRenderer(value, metaData, record, rowIndex, colIndex, store)
{
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
    }
    return opValue;
}

function fnAdminAmenitiesUpdateForm(theForm)
{
    theForm.getForm().submit({
        url: 'admin/update/amenities.action',
        waitMsg: 'loading...',
        success: function(form, action) {
            Ext.Msg.alert('Success', 'Successfully updated Amenity Request');
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



/**
 * RENDERS THE RESULTS IN A TABPANEL, THE DISPLAYED TAB IS BASED ON WHETHER THE PAGE IS CALLED BY THE HOME PAGE OR BY THE SEARCH AMENITY SERVICE
 * 
 * Default is to show both tabs (Amenity and Service Requests) in the home page.
 * There is a logic in both admin-search-amenity-requests.js and admin-search-service-requests.js to display only the respective tabs
 * based on which search service is calling it
 * Ex: Display only the Amenity Requests tab if the admin is searching for amenity requests. Same is true for Service Requests.
 * */
if (callingPage == 'admin-search-amenity-requests.action')
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
            items: [argrid]
        }]
    });
}




// populate request combo box based on job order type selected
/*function fnPopulateRequest(callingPage)
{
	var requestCombo = 	Ext.getCmp('userRequest');
	
	requestCombo.clearValue(); 
	
	// retrieve amenity/service request based on the jobOrderTypeId
	requestCombo.store.load({
		params: { jobOrderTypeId: jobOrderTypeId}
	})
}*/