Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';  // this do not render the images!
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in create-request page!');

	var currentURL = window.location.href;
	var userPage = false;
	var userHouseId = "";
	
	var uom = null;
	var code = null;
	
	
	// CHECKS IF THE PAGE IS A USER PAGE
	if (currentURL.indexOf("user-create-request.action") > -1)
	{
		userPage = true;
		// alert('userPage? ' + userPage);
		
		// this is the house id that came from the home owner landing page
	    var userParams = Ext.urlDecode(location.search.substring(1));
	    userHouseId = userParams.houseId;
	    // alert('userHouseId: ' + userHouseId);
	}
    
 

// #########################################################  FORM ITEMS  ###########################################################################
	
    var requestTypeItem = {
    		fieldLabel: 'REQUEST TYPE',
    		name: 'requestType',
    		id: 'requestType',
            xtype:'combo',
            store: new Ext.data.ArrayStore({
                fields: ['jobOrderTypeId', 'jobOrderTypeDescription'],
                data : [
	                ['1', 'Amenity'],
	                ['2', 'Service']
	            ]
            }),
            displayField:'jobOrderTypeDescription',
            valueField: 'jobOrderTypeId',
            queryMode: 'server',
            mode: 'local',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender: true,
            editable:true,
            emptyText: 'Select Type of Request',
            allowBlank: false,
            blankText: 'Status should not be null',
            anchor: '100%',
            listeners:{
            	select:  function(combo, record, index){
               
           			var jobOrderTypeId = combo.getValue();
           			// alert('JobOrderTypeId: ' + jobOrderTypeId);
           		
           			fnPopulateRequest(jobOrderTypeId);
           			
           			// if Request Type is 1 (Amenity), show amenity fields and hide service request fields
           			if (jobOrderTypeId == 1)
           			{
           				showStartEndDate();
           				showAmenityFields();
           			}
           			else if (jobOrderTypeId == 2)	// show service request fields, hide amenity fields
           			{
           				hideAmenityFields();
           			}
           		}
            }
    }
    
    
    
    
    
    // REQUEST
    var requestItem = {
    		 fieldLabel: 'REQUEST',
        	 name: 'userRequest',		// NOTE: name is used by the Java controller to get the field value, id is used by the ExtJs function getCmp(id)
        	 id: 'userRequest',
        	 xtype:'combo',
        	 store: jobOrderDefinitionStore,
             queryMode: 'server',
             displayField:'description',
             valueField: 'id',
             //cls: 'cTextAlign',
             mode: 'local',
             typeAhead: true,
             triggerAction: 'all',
             lazyRender: true,
             editable:true,
             emptyText: 'Select Request Type',
             //allowBlank: false,
             blankText: 'Request Type should not be null',
             anchor: '100%',
             listeners:{
                 select:  function(combo, record, index){
                 
                     uom = record.get('uom');	// ANDOT
                     code = record.get('code');
                     
                     var rqType = Ext.getCmp('requestType').getValue();
                     
                     // alert('uom: ' + uom);
                     // alert('requestType: ' + rqType);
                     // alert('code: ' + code);
                     
                     
                     if (rqType == 1)
                     {
                    	 var amenityId = record.get('id');
                    	 // alert("amenityId: " + amenityId);
                    	 
                    	 // set amenity id
                         Ext.getCmp('amenityIdID').setValue(amenityId);
                         Ext.getCmp('serviceIdID').setValue(0); // needed otherwise there will be binding error
                        
                    	 showAmenityFields(uom);
                     }
                     else if (rqType == 2)
                     {
                    	 var serviceId = record.get('id');
                    	 // alert("serviceId: " + serviceId);
                    	 
                    	 // set service id
                         Ext.getCmp('serviceIdID').setValue(serviceId);
                         Ext.getCmp('amenityIdID').setValue(0); // needed otherwise there will be binding error
                    	 
                         showServiceFields(uom, code);
                     }
                 }
             }
    }
    
    
    // REQUESTOR
    var requestorItem = {
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
            // allowBlank: false,
            allowBlank: true,
            blankText: 'Requestor should not be null',
            anchor: '100%',
            listeners:{
                select:  function(combo, record, index){
                	var userId = record.get('userId');
                    // alert('userId: ' + userId);
                    
                    Ext.getCmp('requestorUserId').setValue(userId);
                }
            }
    }
    
    
    // REQUESTED DATE		// RACS
    var requestedDateItem = {
    		fieldLabel: 'REQUESTED DATE',
            name: 'requestedDate',
            id: 'requestedDate',
            xtype: 'datefield',
            format: 'm/d/Y',
            // submitFormat: 'm/d/Y',
            submitFormat: 'Y/m/d',
            //yyyy-MM-dd
            allowBlank: false,
            editable:false,
            minValue: new Date(),
            anchor: '100%',
            blankText: 'Requested Date should not be null',
            listeners:{
            	select:  function(combo, record, index){
            		// alert('global value uom: ' + uom);
            		// alert('global value code: ' + code);
            		
            		var jobOrderTypeId = Ext.getCmp('requestType').getValue();
           			// alert('JobOrderTypeId: ' + jobOrderTypeId);
            		
            		if (Ext.getCmp('requestedDate').getValue() != null && Ext.getCmp('requestedDate').getValue() != ""
            			&& Ext.getCmp('quantityID').getValue() > 0)
            		{
            			var quantity = Ext.getCmp('quantityID').getValue()
            			// alert('QUANTITY: ' + quantity);
            			
            			var amenityOrServiceId = Ext.getCmp('userRequest').getValue();
                		// alert('amenityOrServiceId: ' + amenityOrServiceId);
            			
            			var param2 = quantity;
            			
            			// calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
            			
            			if (jobOrderTypeId == 1)	// Amenity request (this needs to be split into 2 because different implementation for the Amenity request)
            			{
            				if (uom == 'pc')
                			{
                				calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
                			}
            			}
            			else if (jobOrderTypeId == 2)	// Service request
            			{
            				calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
            			}
            		}
            	}
            }
    }
    
    
    
    // START TIME
    var startTime = {
    		fieldLabel: 'START TIME',
            name: 'startTime',
            id: 'startTimeID',
            xtype: 'timefield',
            format:'h:i A',	// CHRIS
            minValue: '00:00 AM',
            maxValue: '11:00 PM',
            increment: 60,
            anchor: '100%',
            listeners:{
            	select:  function(combo, record, index){
            		// VALIDATE TIME FIRST
            		var startTime = Ext.getCmp('startTimeID').getValue();
                	var endTime = Ext.getCmp('endTimeID').getValue();
                	
                	// alert('startTime: ' + startTime);
                	// alert('endTime: ' + endTime);
                	
            		if (endTime != null && endTime != "")
            		{
            			var validTime = validateTime(startTime, endTime);
            			
            			if (validTime)
                    	{
            				var startTimeDate = new Date("December 25, 1995 " + Ext.getCmp('startTimeID').getValue()); 
                        	var endTimeDate = new Date("December 25, 1995 " + Ext.getCmp('endTimeID').getValue()); 
                        	// alert('START TIME DATE: ' + startTimeDate);
                    		// alert('END TIME DATE: ' + endTimeDate);
                        	
                        	var jobOrderTypeId = Ext.getCmp('requestType').getValue();
                   			// alert('JobOrderTypeId: ' + jobOrderTypeId);
                   			
                   			var amenityOrServiceId = Ext.getCmp('userRequest').getValue();
                    		// alert('amenityOrServiceId: ' + amenityOrServiceId);
                    		
                    		// ADD CONDITIONS HERE TO GET THE HOURS OR PCS
                    		var param2 = null;
                    		
                    		var hoursUsed = endTimeDate.getHours() - startTimeDate.getHours();
                			// alert('hours used: ' + hoursUsed);
                			
                			param2 = hoursUsed;
                    		
                			// scan the form and get the computation
                			calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
                    	}
            			else
            			{
            				removeXButton(Ext.Msg.alert('WARNING ', 'Start time must be before End time'));
            				Ext.getCmp('fee').reset();
            			}
            		}
            	}
            }
    }
    
    
    // END TIME // RACS
    var endTime = {
    		fieldLabel: 'END TIME',
            name: 'endTime',
            id: 'endTimeID',
            xtype: 'timefield',
            format:'h:i A',
            minValue: '00:00 AM',
            maxValue: '11:00 PM',
            increment: 60,
            anchor: '100%',
            listeners:{
            	select:  function(combo, record, index){
            		// VALIDATE TIME FIRST
            		var startTime = Ext.getCmp('startTimeID').getValue();
                	var endTime = Ext.getCmp('endTimeID').getValue();
                	
                	// alert('startTime: ' + startTime);
                	// alert('endTime: ' + endTime);
                	
                	// if startTime and endTime are NOT NULL and not empty String, it means the request is for an Amenity request
                	// otherwise, it is for a Service request
                	if (startTime != null && startTime != "" && endTime != null && endTime != "")
                	{
                		var validTime = validateTime(startTime, endTime);
                    	
                    	if (validTime)
                    	{
                    		var startTimeDate = new Date("December 25, 1995 " + Ext.getCmp('startTimeID').getValue()); 
                        	var endTimeDate = new Date("December 25, 1995 " + Ext.getCmp('endTimeID').getValue()); 
                        	// alert('START TIME DATE: ' + startTimeDate);
                    		// alert('END TIME DATE: ' + endTimeDate);
                        	
                        	var jobOrderTypeId = Ext.getCmp('requestType').getValue();
                   			// alert('JobOrderTypeId: ' + jobOrderTypeId);
                   			
                   			var amenityOrServiceId = Ext.getCmp('userRequest').getValue();
                    		// alert('amenityOrServiceId: ' + amenityOrServiceId);
                    		
                    		// ADD CONDITIONS HERE TO GET THE HOURS OR PCS
                    		var param2 = null;
                    		
                    		var hoursUsed = endTimeDate.getHours() - startTimeDate.getHours();
                			// alert('hours used: ' + hoursUsed);
                			
                			param2 = hoursUsed;
                    		
                			// scan the form and get the computation
                			calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2);
                    	}
                    	else
                    	{
                    		removeXButton(Ext.Msg.alert('WARNING ', 'Start time must be before End time'));
                    		Ext.getCmp('fee').reset();
                    	}
                	}
                }
            }
    }
    
    
    // calculate the Amenity or Service Fee using a MySQL function in the database
    function calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2)
    {
       // alert('calculating the Amenity Fee...');
       // alert('jobOrderTypeId: ' + jobOrderTypeId);
       // alert('amenityOrServiceId: ' + amenityOrServiceId);
       // alert('param2: ' + param2);
       
       var ajaxURL = 'user/compute-amenity-or-service-fee.action?jobOrderTypeId=' + jobOrderTypeId + '&amenityOrServiceId=' + amenityOrServiceId + '&param2=' + param2;
       //alert('ajaxURL: ' + ajaxURL);
       
       // LOADING MESSAGE
       // Ext.Viewport.mask({ xtype: 'loadmask',message: 'Please wait computing the fee...' });
       
       var loadText = 'Calculating fee... Please wait';
       Ext.getBody().mask(loadText, 'loading');
       
       Ext.Ajax.request({
    	   url: ajaxURL,
		   method: 'GET',
		   success: function (response, request)
		   {
			   Ext.getBody().unmask();
               var jsonData = Ext.util.JSON.decode(response.responseText);
               var fee = jsonData.data;
               // alert('FEE: ' + fee);
               
               Ext.getCmp('fee').setValue('Php ' + fee.format(2));
               // Ext.getCmp('feeItem').setValue(fee);	// this is so that there will no longer be conversion in the controller before saving this value
               
               // alert('fee item: ' + Ext.getCmp('feeItem').getValue());
           },                                    
		   failure: function()
		   {
			   Ext.getBody().unmask();
			   console.log('Calculating total fee failed.');
			   removeXButton(Ext.Msg.alert('Warning', 'Calculating total fee failed.'));
		    }
		});
    }
    
    
   
    // AMENITY OR SERVICE FEE
    var fee = {
        fieldLabel: 'FEE',
        name: 'fee',
        id: 'fee',
        xtype: 'textfield',
        readOnly: true,
        editable:false,
        anchor: '100%'
    }
    
  
    // SERVICE
    // PREFERRED TIME
    var preferredTime = {
    		fieldLabel: 'PREFERRED TIME',
            name: 'preferredTime',
            id: 'preferredTimeID',
            xtype: 'timefield',
            minValue: '00:00 AM',
            maxValue: '11:00 PM',
            increment: 60,
            hidden:true,
            anchor: '100%'
    }
    
    
    
 //############################################## HIDDEN FIELDS ######################################################   
    // QUANTITY
    var quantity = {
    	fieldLabel: 'QUANTITY',
    	name: 'quantity',
    	id:'quantityID',
    	xtype: 'numberfield',
    	hidden:true,
        anchor: '100%',	// ECY
        listeners:{
            'render': function(c) {
                 c.getEl().on('keyup', function(){
                	 var quantity = Ext.getCmp('quantityID').getValue();
                	 // alert('quantity: ' + quantity);
                	 
                	 var jobOrderTypeId = Ext.getCmp('requestType').getValue();
                	 // alert('JobOrderTypeId: ' + jobOrderTypeId);
            			
                	 var amenityOrServiceId = Ext.getCmp('userRequest').getValue();
             		 // alert('amenityOrServiceId: ' + amenityOrServiceId);
             		 
             		 var param2 = quantity;
             		 
             		 calculateAmenityOrServiceFee(jobOrderTypeId, amenityOrServiceId, param2); // this is applicable for both Amenity and Service requests
               }, c);     
           }
       }
    }
    
    
    // STATUS
    var status = {
    	name: 'status',
    	xtype: 'textfield',
        value:'New',
        hidden:true,
        anchor: '100%'
    }
    
    
    // AMENITY ID
    var amenityIdItem = {
    	name: 'amenityId',
        id:'amenityIdID',
        xtype: 'numberfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    // SERVICE ID
    var serviceIdItem = {
    	name: 'serviceId',
        id:'serviceIdID',
        xtype: 'numberfield',
        hidden:true,
        anchor: '100%'
    }

    
    var remarksItem = {
        name: 'remarks',
        id:'remarksID',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    var payeeItem = {
    	name: 'payee',
        id:'payeeID',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
    }
      
    
    // REQUESTOR USER ID (Applicable to both Owner and Lessee)
    var requestorUserIdItem = {
    	name: 'requestorUserId',
        id:'requestorUserId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
    }
    
 // REQUESTOR USER ID (Applicable to both Owner and Lessee)
    var overlapItem = {
    	name: 'overlap',
        id:'overlap',
        xtype: 'textfield',
        readOnly: true,
        hidden:true,
        anchor: '100%'
    }
     
    
    // AMENITY OR SERVICE FEE ITEM
    /*var feeItem = {
    		name: 'feeItem',
            id: 'feeItem',
            xtype: 'numberfield',
            hidden:true,
            anchor: '100%'
       }*/
    
    
    
// #######################			REQUEST FORM 		########################## //
    var requestForm = {
        columnWidth: .98,
        id: 'requestFormId',
        layout: 'form',
        align:'center',
        items: [requestTypeItem, requestItem, requestorItem, requestedDateItem, startTime, endTime, quantity, status, fee, amenityIdItem, preferredTime,
                serviceIdItem, remarksItem, payeeItem, requestorUserIdItem, overlapItem] // change contents dynamically
	}

   
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'CREATE REQUEST',
        id: 'createRequestFormPanel',
        name: 'createRequestFormPanel',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        style: 'margin-top:30px',
        // bodyStyle:'padding:5px',
        labelWidth : 105,   // width nag label like 'REQUEST TYPE'
        width:450,
        height:260,
        padding: 5,
        labelAlign : 'center',
        items: [{
            items: [{
                align:'center',
                layout: 'column',
                items: [requestForm]
            }]
        }],
        buttons: [{
            id: 'createRequestSubmitBtn',
            text: 'Submit',
            handler: function() {
            	var date = Ext.getCmp('requestedDate').getValue();
            	var startTime = Ext.getCmp('startTimeID').getValue();
            	var endTime = Ext.getCmp('endTimeID').getValue();
            	
            	//var d = new Date();
            	date.format("d/m/Y");
            	
            	// Ext.Date.format(date, 'm/d/Y');
            	
            	// alert('FORMATTED DATE: ' + date);
            	
            	// alert('startTime: ' + startTime);
            	// alert('endTime: ' + endTime);
            	
            	// if startTime and endTime are NOT NULL and not empty String, it means the request is for an Amenity request
            	// otherwise, it is for a Service request
            	if (startTime != null && startTime != "" && endTime != null && endTime != "")	// THIS IS FOR AMENITY REQUEST
            	{
            		var validTime = validateTime(startTime, endTime);
            		
                	if (validTime)
                	{
                		// alert('TIME IS VALID!');
                		
                		validateDateTimeOverlap(date, startTime, endTime);
                	}
                	else
                	{
                		removeXButton(Ext.Msg.alert('WARNING: ', 'Start time must be before End time'));
                		return false;
                	}
            	}
            	else
            	{
            		// alert('Submitting SERVICE REQUEST!');										// THIS IS FOR SERVICE REQUEST
            		var serviceReqId = Ext.getCmp('userRequest').getValue();
            		var requestedDate = Ext.getCmp('requestedDate').getValue();
            		
            		// alert('serviceReqId: ' + serviceReqId);
            		// alert('requestedDate: ' + requestedDate);
            		
            		// VALIDATE SERVICE ID and DATE (only those that are Booked)
            		// checkServiceRequest(serviceId,date)
            		
            		validateServiceRequestAndDateOverlap(serviceReqId, requestedDate);
            		
            		// fnSubmitRequest(Ext.getCmp('createRequestFormPanel').getForm());	
            	}
            }
        },{
        	id: 'createRequestResetBtn',
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm();
            }
        }]
    });
    
  
    
    
    
    
    
    
    
    // VALIDATE TIME
    function validateTime(startTime, endTime)
    {
    	var from = Date.parseDate(Ext.getCmp('startTimeID').value, "h:i A");
    	var to = Date.parseDate(Ext.getCmp('endTimeID').value, "h:i A");
    	// alert('from: ' + from);
    	// alert('to: ' + to);
    	
    	if (to > from)
    	{
    		// alert("YES!");
    		return true;
    	}
    	else
    	{
    		// alert("NO!");
    		return false;
    	}
    }
    
    
    // VALIDATE DATE AND TIME OVERLAP
    function validateServiceRequestAndDateOverlap(serviceReqId, requestedDate) // ECY
    {
    	// alert("validating service request and date " + requestedDate);
    	
    	var ajaxURL = 'user/validate-service-request-and-date-overlap.action?serviceReqId=' + serviceReqId + '&requestedDate=' + requestedDate;
    	
    	var loadText = 'Submitting request...';
        Ext.getBody().mask(loadText, 'loading');
        
        Ext.Ajax.request({
     	   url: ajaxURL,
 		   method: 'GET',
 		   success: function (response, request)
 		   {
 			   // alert('YES!');
 			   
 			   Ext.getBody().unmask();
 			   
 			   var jsonData = Ext.util.JSON.decode(response.responseText);
               var withOverlap = jsonData.data;
               // alert('WITH OVERLAP? ' + withOverlap);
               
               if (withOverlap)
               {
            	   removeXButton(Ext.Msg.alert('WARNING ', 'Cannot submit request, requested service or date booked.'));
            	   // Ext.getCmp('overlap').setValue('true');
               }
               else
               {
            	   // submit form
            	   fnSubmitRequest(Ext.getCmp('createRequestFormPanel').getForm());
               }
                
                // Ext.getCmp('fee').setValue('Php ' + fee.format(2));
                // Ext.getCmp('feeItem').setValue(fee);	// this is so that there will no longer be conversion in the controller before saving this value
                
                // alert('fee item: ' + Ext.getCmp('feeItem').getValue());
            },                                    
 		   failure: function()
 		   {
 			   Ext.getBody().unmask();
 			   console.log('Submitting service request failed.');
 			   removeXButton(Ext.Msg.alert('Warning', 'Submitting service request failed.'));
 		    }
 		});
    	
    }
    
    
    
    // VALIDATE DATE AND TIME OVERLAP
    function validateDateTimeOverlap(date, startTime, endTime) // ECY
    {
    	// alert("validating date and time: " + date);
    	// alert("REQUEST TYPE: " + Ext.getCmp('requestType').getValue());
    	// alert("SELECTED AMENITY ID: " + Ext.getCmp('amenityIdID').getValue());
    	// alert("SELECTED SERVICE ID: " + Ext.getCmp('serviceIdID').getValue());
    	
    	var ajaxURL  = "";
    	
    	var requestType = Ext.getCmp('requestType').getValue();
    	var selectedAmenityId = Ext.getCmp('amenityIdID').getValue();
    	var selectedServiceId = Ext.getCmp('serviceIdID').getValue();
    	
    	// alert('RACS : ' + requestType);
    	
    	if (requestType != null && requestType != "")
    	{
    		// validate based on Request Type
   			if (requestType == 1)
   			{
   				ajaxURL = 'user/validate-date-and-time-overlap.action?requestType=' + requestType + '&requestId=' + selectedAmenityId + '&date=' + date + '&startTime=' + startTime + '&endTime=' + endTime;
   			}
   			else if (requestType == 2)
   			{
   				ajaxURL = 'user/validate-date-and-time-overlap.action?requestType=' + requestType + '&requestId=' + selectedServiceId + '&date=' + date + '&startTime=' + startTime + '&endTime=' + endTime; 
   			}
    	}
    	
    	
    	// startTime and endTime are empty String for Service requests
    	// var ajaxURL = 'user/validate-date-and-time-overlap.action?date=' + date + '&startTime=' + startTime + '&endTime=' + endTime; 
    	//var ajaxURL = 'user/validate-date-and-time-overlap.action?requestId=' + 'date=' + date + '&startTime=' + startTime + '&endTime=' + endTime; 
    	
    	
    	// var ajaxURL = 'user/validate-date-and-time-overlap.action?date=06/16/2015&startTime=1300&endTime=1700'; 
        // alert('ajaxURL: ' + ajaxURL);
        
        // LOADING MESSAGE
        // Ext.Viewport.mask({ xtype: 'loadmask',message: 'Please wait computing the fee...' });
        
        var loadText = 'Submitting request...';
        Ext.getBody().mask(loadText, 'loading');
        
        Ext.Ajax.request({
     	   url: ajaxURL,
 		   method: 'GET',
 		   success: function (response, request)
 		   {
 			   // alert('YES!');
 			   
 			   Ext.getBody().unmask();
 			   
 			   var jsonData = Ext.util.JSON.decode(response.responseText);
               var withOverlap = jsonData.data;
               // alert('WITH OVERLAP? ' + withOverlap);
               
               if (withOverlap)
               {
            	   removeXButton(Ext.Msg.alert('WARNING ', 'Cannot submit request, date or time is already booked.'));
            	   // Ext.getCmp('overlap').setValue('true');
               }
               else
               {
            	   // submit form
            	   fnSubmitRequest(Ext.getCmp('createRequestFormPanel').getForm());
               }
                
                // Ext.getCmp('fee').setValue('Php ' + fee.format(2));
                // Ext.getCmp('feeItem').setValue(fee);	// this is so that there will no longer be conversion in the controller before saving this value
                
                // alert('fee item: ' + Ext.getCmp('feeItem').getValue());
            },                                    
 		   failure: function()
 		   {
 			   Ext.getBody().unmask();
 			   console.log('Submitting service request failed.');
 			   removeXButton(Ext.Msg.alert('Warning', 'Submitting service request failed.'));
 		    }
 		});
    	
    }
    
    
    
    // SUBMIT THE FORM
    function fnSubmitRequest(theForm)
    {
    	// get the Combo box objects
    	var requestTypeCombo = Ext.getCmp('requestType');
    	var userRequestCombo = Ext.getCmp('userRequest');
    	var requestorCombo = Ext.getCmp('requestor');
    	
    	// get the items to be inserted to DB when creating requests 
    	var jobOrderTypeId = requestTypeCombo.getValue();
		var jobOrderDefinitionId = userRequestCombo.getValue();
		var houseId = requestorCombo.getValue();

    	// alert("requestType ID: " + jobOrderTypeId);
    	// alert("userRequest ID: " + jobOrderDefinitionId);
    	// alert("requestor House ID: " + houseId);
    	
    	
		// FOR ADMIN
    	var params = 'jobOrderTypeId=' + jobOrderTypeId + '&jobOrderDefinitionId=' + jobOrderDefinitionId + '&houseId=' + houseId;
		var submitUrl = 'admin/create-request.action?' + params;
    	
		// FOR USER
    	if (userPage)
    	{
    		params = 'jobOrderTypeId=' + jobOrderTypeId + '&jobOrderDefinitionId=' + jobOrderDefinitionId + '&houseId=' + userHouseId;
    		submitUrl = 'user/create-request.action?' + params;
    	}
    	
    	// alert('PARAMS: ' + params);
    	// alert('submitUrl: ' + submitUrl);
    	
    	var loadText = 'Verifying request... Please wait.';
        Ext.getBody().mask(loadText, 'loading');
    	
    	// SUBMIT
    	theForm.submit({
            //waitMsg:'Loading...',
            url: submitUrl,
            // waitMsg: 'loading...',
            success: function(form, action) {
            	
            	Ext.getBody().unmask();
            	removeXButton(Ext.Msg.alert('Success', 'Successfully created new request'));
            	
            	// reset the fields
            	fnResetForm();
            },
            failure: function(form, action) {
            	removeXButton(Ext.Msg.alert('Warning', 'The time and date have already been reserved.'));
            }
        });
    }
    
    
    
    // SHOW START and END DATE
    function showStartEndDate()
    {
    	Ext.getCmp('startTimeID').show();
    	Ext.getCmp('endTimeID').show();
    }
    
    
    // SHOW SERVICE FIELDS
    function showServiceFields(uom, code)
    {
    	// alert('uom: ' + uom);
    	// alert('code: ' + code);
    	
    	if(uom == 'hph')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('requestedDate').show();
            Ext.getCmp('requestedDate').reset();
            Ext.getCmp('quantityID').show();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    	
    	
    	if(uom == 'pc')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').show();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            Ext.getCmp('requestedDate').reset();
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    	
    	
    	if(uom == 'day' && code != 'other')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').show();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            Ext.getCmp('requestedDate').reset();
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    	
    	
    	if(uom == 'day' && code == 'other')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').hide();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            Ext.getCmp('requestedDate').reset();
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    	

    	if(uom == 'pc' && code == 'dlvry')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').hide();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    }
    
    
    
    function hideAmenityFields()
    {
    	// Ext.getCmp('requestedDate').hide();
    	Ext.getCmp('requestedDate').reset();
    	Ext.getCmp('fee').reset();
    	Ext.getCmp('startTimeID').hide();
    	Ext.getCmp('startTimeID').setValue("");
    	Ext.getCmp('endTimeID').hide();
    	Ext.getCmp('endTimeID').setValue("");
    	Ext.getCmp('quantityID').hide();
    	Ext.getCmp('quantityID').setValue(1);
    	hideRequestorIfUserPage();
    }
    
    
    function showAmenityFields(uom)
    {
    	// alert('uom: ' + uom);
    	
    	if(uom == undefined)	// it means no request type is selected yet
    	{
    		Ext.getCmp('requestedDate').reset();
    		Ext.getCmp('startTimeID').show();
            Ext.getCmp('endTimeID').show();
            Ext.getCmp('quantityID').hide();
            Ext.getCmp('quantityID').setValue(1);
            Ext.getCmp('fee').reset();
            hideRequestorIfUserPage();
        }
    	
    	
    	if(uom == 'hph')
    	{
    		Ext.getCmp('startTimeID').show();
            Ext.getCmp('endTimeID').show();
            Ext.getCmp('quantityID').show();
            Ext.getCmp('quantityID').setValue(1);
            hideRequestorIfUserPage();
        }
    	
    	if(uom == 'pc')		// CHRIS
        {
        	 Ext.getCmp('requestedDate').reset();
             Ext.getCmp('startTimeID').hide();
             Ext.getCmp('endTimeID').hide();
             Ext.getCmp('startTimeID').setValue("");
             Ext.getCmp('endTimeID').setValue("");
             Ext.getCmp('quantityID').show();
             Ext.getCmp('quantityID').setValue(1);
             Ext.getCmp('fee').reset();
             hideRequestorIfUserPage();
         }
         
         if(uom == 'hr')
         {
        	 Ext.getCmp('requestedDate').reset();
             Ext.getCmp('startTimeID').show();	
             Ext.getCmp('startTimeID').reset();
             Ext.getCmp('endTimeID').show();
             Ext.getCmp('endTimeID').reset();
             Ext.getCmp('quantityID').hide();
             Ext.getCmp('quantityID').setValue(1);
             Ext.getCmp('fee').reset();
             hideRequestorIfUserPage();
             
             // alert("QUANTITY: " + Ext.getCmp('quantityID').getValue());
         }
       
         if(uom == 'day')
         {
             Ext.getCmp('startTimeID').show();
             Ext.getCmp('endTimeID').show();
             Ext.getCmp('quantityID').hide();
             Ext.getCmp('quantityID').setValue(1);
             hideRequestorIfUserPage();
         }
    }
    
    
    // HIDE THE REQUESTOR FIELD IF USER PAGE
    function hideRequestorIfUserPage()
    {
    	if (userPage)
		{
			Ext.getCmp('requestor').hide();
		}
        else
        {
        	Ext.getCmp('requestor').setValue('');
            Ext.getCmp('requestor').show();
        }
    }
    
    
    // RESET FORM
    function fnResetForm()
    {
    	Ext.getCmp('requestType').reset();
    	Ext.getCmp('userRequest').reset();
    	Ext.getCmp('requestedDate').reset();
    	Ext.getCmp('startTimeID').reset();
    	Ext.getCmp('endTimeID').reset();
    	Ext.getCmp('quantityID').reset();
    	Ext.getCmp('requestor').reset();
    	Ext.getCmp('fee').reset();
    }
    
 	
    // render the Ext Js element in a div with id="login"
	createRequestFormPanel.render('my-tabs');
    
    
	// populate request combo box based on job order type selected
	function fnPopulateRequest(jobOrderTypeId)
	{
		var requestCombo = 	Ext.getCmp('userRequest');
		
		requestCombo.clearValue(); 
		
		// retrieve amenity/service request based on the jobOrderTypeId
		requestCombo.store.load({
			params: { jobOrderTypeId: jobOrderTypeId}
		})
	}
	
	
	// IF THIS IS A USER PAGE (NOT AN ADMIN PAGE), hide the REQUESTOR component
	hideRequestorIfUserPage();
	
	/*if (userPage)
	{

	Ext.getCmp('requestor').hide();

	}*/
     
    
});