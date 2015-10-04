Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';  // this do not render the images!
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in create-request page!');

    
 // #######################			FIELD SETS 		########################## //
    
    var requestTypeItem = {
    		fieldLabel: 'REQUEST TYPE',
    		id: 'requestType',
    		name: 'requestType',
            xtype:'combo',
            store: new Ext.data.ArrayStore({
                fields: ['jobOrderTypeId', 'jobOrderTypeDescription'],
                data : [
	                ['1', 'Adjustment'],
	                ['2', 'Penalty']
                ]
            }),
            displayField:'jobOrderTypeDescription',
            valueField: 'jobOrderTypeId',
            queryMode: 'server',
            mode: 'local',
            typeAhead: true,
            triggerAction: 'all',
            lazyRender: true,
            emptyText: 'Select Type of Request',
            allowBlank: false,
            blankText: 'Request type should not be null',
            anchor: '100%'
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
    var remarksItem = {
    	fieldLabel: 'REMARKS',
        name: 'remarks',
        id: 'remarks',
        xtype: 'textfield',
        anchor: '100%'
    }
    
    
    // ^^^^^^^^^^ Research how to set the numberfield to 2 decimal places only
    // AMOUNT
    var amountItem = {
    	fieldLabel: 'AMOUNT',
        name: 'amount',
        id: 'amount',
        xtype: 'numberfield',
        anchor: '100%'
    }
    
    
    
    // POSTED DATE
    var postedDateItem = {
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
    }
    
    
  
  
    
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
        items: [requestTypeItem, requestorItem, remarksItem, amountItem, postedDateItem, requestorUserIdItem, requestorHouseIdItem] // change contents dynamically
    }

   
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'ADD ADJUSTMENT OR PENALTY',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        style: 'margin-top:30px',
        // bodyStyle:'padding:5px',
        labelWidth : 105,   // width nag label like 'REQUEST TYPE'
        width:450,
        height:240,
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
                fnSubmitRequest(createRequestFormPanel);
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
    
  
    
    // SUBMIT THE FORM
    function fnSubmitRequest(theForm)
    {
    	theForm.getForm().submit({
    		url: 'admin-create-adjustment-penalty.action',
            waitMsg:'Submitting request...',
            success: function(form, action) {
            	removeXButton(Ext.Msg.alert('Success', 'Successfully submitted adjustment or penalty.'));
                fnResetForm(theForm);
            },
            failure: function(form, action) {
            	removeXButton(Ext.Msg.alert('Warning', 'Submitting adjustment or penalty failed..'));
            }
        });
    }
    
    
    
    // reset form
    function fnResetForm(theForm)
    {
        theForm.getForm().reset();
        Ext.getCmp('createRequestSubmitBtn').setDisabled(false);
        Ext.getCmp('createRequestResetBtn').setDisabled(false);
    }
    
    
 	
    // render the Ext Js element in a div with id="login"
	createRequestFormPanel.render('my-tabs');
     
    
});