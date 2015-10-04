Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';  // this do not render the images!
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in create-request page!');

   // jobOrderDefinitionStore.load();
	
	var currentURL = window.location.href;
	var userPage = false;
	var userHouseId = "";


	if (currentURL.indexOf("user-create-request.action") > -1)

	{
		userPage = true;
		alert('userPage? ' + userPage);
		
		// this is the house id that came from the home owner landing page
	    var userParams = Ext.urlDecode(location.search.substring(1));
	    userHouseId = userParams.houseId;
	    
	    alert('userHouseId: ' + userHouseId);
	}
    
 
    
 // #######################			FIELD SETS 		########################## //
    
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
           			
           			// validate based on Request Type
           			if (jobOrderTypeId == 1)
           			{
           				showStartEndDate();
           				showAmenityFields();
           			}
           			else if (jobOrderTypeId == 2)
           			{
           				// Ext.getCmp('startTimeID').hide();
           				
           				hideAmenityFields();
           			}
           		}
            }
    }
    
    
    
    var requestItem = {
    		 fieldLabel: 'REQUEST',
        	 name: 'userRequest',		// NOTE: name is used by the Java controller to get the field value, id is used by the Extjs function getCmp(id)
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
                 
                     var uom = record.get('uom');
                     
                     var code = record.get('code');
                     
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
    
    
    // REQUESTED DATE
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
            blankText: 'Requested Date should not be null'
    }
    
    
    // START TIME
    var startTime = {
    		fieldLabel: 'START TIME',
            name: 'startTime',
            id: 'startTimeID',
            xtype: 'timefield',
            format:'h:i A',
            minValue: '00:00 AM',
            maxValue: '11:00 PM',
            increment: 60,
            anchor: '100%'
    }
    
    
    // END TIME
    var endTime = {
    		fieldLabel: 'END TIME',
            name: 'endTime',
            id: 'endTimeID',
            xtype: 'timefield',
            format:'h:i A',
            minValue: '00:00 AM',
            maxValue: '11:00 PM',
            increment: 60,
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
        anchor: '100%'
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
    
    
    
// #######################			REQUEST FORM 		########################## //
    var requestForm = {
        columnWidth: .98,
        id: 'requestFormId',
        layout: 'form',
        align:'center',
        items: [requestTypeItem, requestItem, requestorItem, requestedDateItem, startTime, endTime, quantity, status, amenityIdItem, preferredTime,
                serviceIdItem, remarksItem, payeeItem, requestorUserIdItem] // change contents dynamically
        // items: [requestTypeItem, requestorItem, requestedDateItem]
	}

   
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'CREATE REQUEST',
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
            	var startTime = Ext.getCmp('startTimeID').getValue();
            	var endTime = Ext.getCmp('endTimeID').getValue();
            	
            	alert('startTime: ' + startTime);
            	alert('endTime: ' + endTime);
            	
            	
            	// if startTime and endTime are NOT NULL and not empty String, it means the request is for an Amenity request
            	// otherwise, it is for a Service request
            	if (startTime != null && startTime != "" && endTime != null && endTime != "")
            	{
            		var validTime = validateTime(startTime, endTime);
                	
                	if (validTime)
                	{
                		fnSubmitRequest(createRequestFormPanel);
                	}
                	else
                	{
                		removeXButton(Ext.Msg.alert('WARNING: ', 'Start time must be before End time'));
                		
                		return false;
                	}
            	}
            	else
            	{
            		fnSubmitRequest(createRequestFormPanel);
            	}
            	
            	
            	/*var validTime = validateTime(startTime, endTime);
            	
            	if (validTime)
            	{
            		fnSubmitRequest(createRequestFormPanel);
            	}
            	else
            	{
            		removeXButton(Ext.Msg.alert('WARNING: ', 'Start time must be before End time'));
            		
            		// alert("Start time must be before End time!");
            		
            		return false;
            	}*/
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
    
  
    
    // submit the form
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
    	
    	alert('ECY PARAMS: ' + params);
    	alert('RACS submitUrl: ' + submitUrl);
    	
    	// return false; RACS REMOVE THIS
    	
        //if(theForm.isValid()){
        theForm.getForm().submit({
            //waitMsg:'Loading...',
            url: submitUrl,
            waitMsg: 'loading...',
            success: function(form, action) {
            	removeXButton(Ext.Msg.alert('Success', 'Successfully created new request'));
            	
            	// reset the fields
            	fnResetForm();
            },
            failure: function(form, action) {
            	removeXButton(Ext.Msg.alert('Warning', 'The time and date have already been reserved.'));
            }
        });
    //}
    }
    
    
    // validate time
    function validateTime(startTime, endTime)
    {
    	var from = Date.parseDate(Ext.getCmp('startTimeID').value, "h:i A");
    	var to = Date.parseDate(Ext.getCmp('endTimeID').value, "h:i A");
    	
    	alert('from: ' + from);
    	alert('to: ' + to);
    	
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
    
    
    
    function showStartEndDate()
    {
    	Ext.getCmp('startTimeID').show();
    	Ext.getCmp('endTimeID').show();
    }
    
    
    function showServiceFields(uom, code)
    {
    	if(uom == 'hph'){
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('requestedDate').show();
            Ext.getCmp('quantityID').hide();
            Ext.getCmp('quantityID').setValue(1);
            // Ext.getCmp('requestor').setValue('');
            // Ext.getCmp('requestor').show();
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            
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
    	
    	
    	if(uom == 'pc'){
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').show();
            Ext.getCmp('quantityID').setValue(1);
            // Ext.getCmp('requestor').setValue('');
            // Ext.getCmp('requestor').show();
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            
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
    	
    	
    	if(uom == 'day' && code != 'other')
    	{
            Ext.getCmp('preferredTimeID').hide();
            Ext.getCmp('quantityID').hide();
            Ext.getCmp('quantityID').setValue(1);
            // Ext.getCmp('requestor').setValue('');
            // Ext.getCmp('requestor').show();
            Ext.getCmp('remarksID').setValue('');
            Ext.getCmp('remarksID').hide();
            Ext.getCmp('payeeID').setValue('');
            Ext.getCmp('payeeID').hide();
            
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
    }
    
    
    
    function hideAmenityFields()
    {
    	// Ext.getCmp('requestedDate').hide();
    	// Ext.getCmp('requestedDate').reset();
    	Ext.getCmp('startTimeID').hide();
    	Ext.getCmp('startTimeID').setValue("");
    	Ext.getCmp('endTimeID').hide();
    	Ext.getCmp('endTimeID').setValue("");
    	Ext.getCmp('quantityID').hide();
    	Ext.getCmp('quantityID').setValue(1);
    	
    	if (userPage)
		{ 
			Ext.getCmp('requestor').hide();
		}
    }
    
    
    function showAmenityFields(uom)
    {
    	if (userPage)
		{ 
			Ext.getCmp('requestor').hide();
		}
    	
    	if(uom == 'hph'){
             Ext.getCmp('startTimeID').show();
             Ext.getCmp('endTimeID').show();
             Ext.getCmp('quantityID').show();
             Ext.getCmp('quantityID').setValue(1);
         }
       
         if(uom == 'pc'){
             Ext.getCmp('startTimeID').hide();
             Ext.getCmp('endTimeID').hide();
             Ext.getCmp('startTimeID').setValue("");
             Ext.getCmp('endTimeID').setValue("");
             Ext.getCmp('quantityID').show();
             Ext.getCmp('quantityID').setValue(1);
         }
         
         if(uom == 'hr'){
             Ext.getCmp('startTimeID').show();
             Ext.getCmp('endTimeID').show();
             Ext.getCmp('quantityID').hide();
             Ext.getCmp('quantityID').setValue(1);
             
             // alert("QUANTITY: " + Ext.getCmp('quantityID').getValue());
         }
       
         if(uom == 'day'){
             Ext.getCmp('startTimeID').show();
             Ext.getCmp('endTimeID').show();
             Ext.getCmp('quantityID').hide();
             Ext.getCmp('quantityID').setValue(1);
         }
    }
    
    
    // reset form
    function fnResetForm()
    {
    	Ext.getCmp('requestType').reset();
    	Ext.getCmp('userRequest').reset();
    	Ext.getCmp('requestedDate').reset();
    	Ext.getCmp('startTimeID').reset();
    	Ext.getCmp('endTimeID').reset();
    	Ext.getCmp('quantityID').reset();
    	Ext.getCmp('requestor').reset();
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

	if (userPage)
	{

	Ext.getCmp('requestor').hide();

	}
     
    
});