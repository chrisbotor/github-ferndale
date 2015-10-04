Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';  // this do not render the images!
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in admin-search-or-to-cancel page!');

    
 // #######################			FIELD SETS 		########################## //
    
    var requestTypeItem = {
    		fieldLabel: 'SEARCH TYPE',
    		id: 'requestType',
    		name: 'requestType',
            xtype:'combo',
            store: new Ext.data.ArrayStore({
                fields: ['searchTypeId', 'searchTypeDescription'],
                data : [
	                ['searchByOR', 'Search by OR number'],
	                ['searchByRequestor', 'Search by requestor']
                ]
            }),
            displayField:'searchTypeDescription',
            valueField: 'searchTypeId',
            queryMode: 'server',
            mode: 'local',
            typeAhead: false,
            triggerAction: 'all',
            lazyRender: true,
            editable:false,
            emptyText: 'Select Type of Search',
            //allowBlank: false,
            blankText: 'Search type should not be null',
            anchor: '100%',
            listeners:
            {
                select: function(field, newValue, oldValue)
                {
                     var searchType = field.value;
                     // alert("SELECTED: " + searchType);
                     
                     fnShowAndHideFieldValues(searchType);
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
            allowBlank: false,
            blankText: 'Requestor should not be null',
            hidden:true,
            anchor: '100%',
            listeners:{
                select:  function(combo, record, index)
                {
                	var userId = record.get('userId');
                    // alert('userId: ' + userId);
                    
                    var houseId = combo.getValue();
                    // alert('houseId: ' + houseId);
                    
                    Ext.getCmp('requestorUserId').setValue(userId);
                    Ext.getCmp('requestorHouseId').setValue(houseId);
                }
            }
    }
    
    
    // REMARKS
   /* var remarksItem = {
    	fieldLabel: 'REMARKS',
        name: 'remarks',
        id: 'remarks',
        xtype: 'textfield',
        anchor: '100%'
    }*/
    
    
    // ^^^^^^^^^^ Research how to set the numberfield to 2 decimal places only
    // OR NUMBER
    var orNumberItem = {
    	fieldLabel: 'OR NUMBER',
        name: 'orNumber',
        id: 'orNumber',
        xtype: 'numberfield',
        anchor: '100%',
        hidden:true
    }
    
    
    
    // POSTED DATE
    /*var postedDateItem = {
    		fieldLabel: 'POSTED DATE',
            name: 'postedDate',
            id: 'postedDate',
            xtype: 'datefield',
            format: 'm/d/Y',
            submitFormat: 'Y/m/d',
            allowBlank: false,
            editable:false,
            minValue: new Date(),
            anchor: '100%',
            blankText: 'Posted Date should not be null'
    }*/
    
    
  
  
    
 //############################################## HIDDEN FIELDS ######################################################    
    
    // REQUESTOR USER ID (Applicable to both Owner and Lessee)
    var requestorUserIdItem = {
    	name: 'requestorUserId',
        id:'requestorUserId',
        xtype: 'textfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    // REQUESTOR HOUSE ID (Applicable to both Owner and Lessee)
    var requestorHouseIdItem = {
    	name: 'requestorHouseId',
        id:'requestorHouseId',
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
        items: [requestTypeItem, requestorItem, orNumberItem, requestorUserIdItem, requestorHouseIdItem] // change contents dynamically
    }

   
    
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'SEARCH FOR OFFICIAL RECEIPT',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        style: 'margin-top:30px',
        // bodyStyle:'padding:5px',
        labelWidth : 105,   // width nag label like 'REQUEST TYPE'
        width:450,
        height : 200,
        padding: 15,
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
            handler: function() 
            {
            	var requestorUserId = Ext.getCmp('requestorUserId').getValue();
            	// alert("requestorUserId: " + requestorUserId);
            	
            	var requestorHouseId = Ext.getCmp('requestorHouseId').getValue();
            	// alert("requestorHouseId: " + requestorHouseId);
            	
            	var orNumber = Ext.getCmp('orNumber').getValue();
            	
            	// SUBMIT THE FORM
            	window.location = 'admin-display-or-to-cancel.action?requestorUserId=' + requestorUserId + '&requestorHouseId=' + requestorHouseId
				+ '&orNumber=' + orNumber;
            }
        },{
        	id: 'createRequestResetBtn',
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(createRequestFormPanel);
            }
        }]
    });
    
  
    
    
    // reset form
    function fnResetForm(theForm)
    {
        theForm.getForm().reset();
        Ext.getCmp('createRequestSubmitBtn').setDisabled(false);
        Ext.getCmp('createRequestResetBtn').setDisabled(false);
    }
    
    
 	
    // render the Ext Js element in a div with id="login"
	createRequestFormPanel.render('my-tabs');
	
	
	
	
	
// ########################################  CUSTOM FUNCTIONS  ##############################################
	
/**
 * SHOW and HIDE the fields in the Search for Official Receipt tab based on the search type selected
 * */
function fnShowAndHideFieldValues(searchType)
{
	// alert('Showing and hiding field values based on SEARCH TYPE: ' + searchType);
	
	if (searchType == 'searchByOR')
    {
   	 	// alert('searching by OR...');
   	 	Ext.getCmp('orNumber').show();
   	 	Ext.getCmp('requestor').hide();
   	 	
   	 	fnClearFieldValues('requestor');
   }
   else if (searchType == 'searchByRequestor')
   {
	   // alert('searching by requestor...');
	   Ext.getCmp('requestor').show();
	   Ext.getCmp('orNumber').hide();
	   
	   fnClearFieldValues('orNumber');
   }
}

/**
 *  Clears field of the previously entered values
 * */
function fnClearFieldValues(fieldId)
{
	Ext.getCmp(fieldId).reset();	
}
     
    
});