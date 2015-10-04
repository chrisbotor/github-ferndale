Ext.onReady(function(){

    // Ext.BLANK_IMAGE_URL = '../ext-3.4.0/resources/images/default/s.gif';  // this do not render the images!
	
	Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';

    // alert('in admin-assoc-dues-yearly page!');

	// GLOBAL VARIABLES
	var totalAnnualDue = 0;
	var discountedAmount = 0;
	var netAmountToPay = 0;
	var discountString = "";
	
	    
 // #######################			FIELD SETS 		########################## //
    
    // REQUESTOR
    var requestorItem = {
    		fieldLabel: 'PAYEE NAME',
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
                	var houseId = combo.getValue();
                	// alert('userId: ' + userId);
                    // alert('houseId: ' + houseId);
                    
                    // calculate the total assoc due
                    fnCalculateTotalAssocDue(userId, houseId);
                    
                    Ext.getCmp('requestorUserId').setValue(userId);
                    Ext.getCmp('requestorHouseId').setValue(houseId);
                }
            }
    }
    
    
    // TOTAL AMOUNT DUE
    var totalAmountDueItem = {
        fieldLabel: 'TOTAL DUE',
        name: 'totalAmountDue',
        id: 'totalAmountDue',
        xtype: 'textfield',
        readOnly: true,
        editable:false,
        // style: 'font-color: grey; background-image:none;', style it so it can be shown as non-editable
        anchor: '100%'
   }
    
    
    // DISCOUNT
    var discountItem = {
    	fieldLabel: 'DISCOUNT (in %)',
        name: 'discount',
        id: 'discount',
        xtype: 'numberfield',
        anchor: '100%',
        listeners:{
            'render': function(c) {
                 c.getEl().on('keyup', function(){
                	 
                	 var discount = this.getValue();
                	 // alert('DISCOUNT: ' + discount);
                	 // alert('totalAnnualDue: ' + totalAnnualDue);
                	 
                	 fnCalculateDiscountedAmount(totalAnnualDue, discount);
                	 
                	 fnCalculateNetAmountToPay(totalAnnualDue, discountedAmount);
                	 Ext.getCmp('amount').setValue(netAmountToPay);
                	 
                	 // alert('TOTAL ANNUAL: ' + totalAnnualDue);
                	 // alert('TOTAL DISCOUNT: ' + (totalAnnualDue - netAmountToPay));
                	 // alert('DISCOUNT %: ' + discount);
                	 
                	 var concatenatedRemarks = totalAnnualDue.format(2).toString() + ' less ' + (totalAnnualDue - netAmountToPay).format(2).toString() 
                	 							+ ' (' + discount + '% discount) ';
                	 Ext.getCmp('concatenatedRemarks').setValue(concatenatedRemarks);
                	 
               }, c);     
           }
       }
    }
    
    // ^^^^^^^^^^ Research how to set the numberfield to 2 decimal places only (DONE, use format function below)
    // DISCOUNTED AMOUNT
    var discountedAmountItem = {
    	fieldLabel: 'DISCOUNTED AMOUNT',
        name: 'discountedAmount',
        id: 'discountedAmount',
        xtype: 'textfield',
        readOnly: true,
        editable:false,
        anchor: '100%'
    }
    
    
    // NEW AMOUNT
    var newAmountItem = {
    	fieldLabel: 'NEW AMOUNT TO PAY',
        name: 'newAmount',
        id: 'newAmount',
        xtype: 'textfield',
        readOnly: true,
        editable:false,
        anchor: '100%'
    }
    
    
    // REMARKS
    var remarksItem = {
    	fieldLabel: 'REMARKS',
        name: 'remarks',
        id: 'remarks',
        xtype: 'textfield',
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
    
    
    // PAID AMOUNT
    var amountItem = {
    	name: 'amount',
        id:'amount',
        xtype: 'numberfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    // RATE
    var rateItem = {
    	name: 'rate',
        id:'rate',
        xtype: 'numberfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    // LOT AREA
    var lotAreaItem = {
    	name: 'lotArea',
        id:'lotArea',
        xtype: 'numberfield',
        hidden:true,
        anchor: '100%'
    }
    
    
    // CONCATENATED REMARKS
    var concatenatedRemarksItem = {
    	name: 'concatenatedRemarks',
        id:'concatenatedRemarks',
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
        items: [requestorItem, totalAmountDueItem, discountItem, discountedAmountItem, newAmountItem, remarksItem, 
                requestorUserIdItem, requestorHouseIdItem, amountItem, rateItem, lotAreaItem, postedDateItem, concatenatedRemarksItem] // change contents dynamically
    }

   
 // FORM PANEL
    var createRequestFormPanel = new Ext.FormPanel({
        title: 'ASSOCIATION DUES - YEARLY',
        frame: true,
        autoScroll:true,
        cls: 'my-form-class',
        style: 'margin-top:30px',
        // bodyStyle:'padding:5px',
        labelWidth : 105,   // width nag label like 'REQUEST TYPE'
        width:450,
        height:300,
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
    		url: 'admin-pay-assoc-dues-yearly.action',
            waitMsg:'Submitting payment...',
            success: function(form, action) {
                Ext.Msg.alert('Success', 'Successfully submitted new amount to pay.');
                fnResetForm(theForm);
            },
            failure: function(form, action) {
                Ext.Msg.alert('Warning', 'Payment for annual association dues failed.');
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
    
    
    // calculate the total annual assoc due
    function fnCalculateTotalAssocDue(userId, houseId)
    {
       // alert('calculating total assoc due...');
       // alert('userId: ' + userId);
       // alert('houseId: ' + houseId);
       
       var ajaxURL = 'admin-get-total-assoc-due.action?userId=' + userId + '&houseId=' + houseId;
       // alert('ajaxURL: ' + ajaxURL);
       
       Ext.Ajax.request({
    	   url: ajaxURL,
		   method: 'GET',
		   success: function (response, request)
		   			{
               			var jsonData = Ext.util.JSON.decode(response.responseText);
               			// var lotArea = jsonData.data.lot_area;
               			var lotArea = jsonData.lotArea;
               			var rate = jsonData.rate
               			
               			totalAnnualDue = (lotArea * rate) * 4;
               			// alert('totalAnnualDue: ' + totalAnnualDue);
               			
               			var formattedTotalDue = totalAnnualDue.format(2);
               			// alert('formattedTotalDue: ' + formattedTotalDue);
               			
               			Ext.getCmp('totalAmountDue').setValue(formattedTotalDue);
               			Ext.getCmp('rate').setValue(rate);
               			Ext.getCmp('lotArea').setValue(lotArea);
           },                                    
		   failure: function()
		    		 {
		    			console.log('Calculating total amount due failed.');
		    		 }
		});
    }
    
    

    // calculate the Discounted Amount
    function fnCalculateDiscountedAmount(totalAnnualDue, discount)
    {
    	discountedAmount = totalAnnualDue * (discount / 100);
    	//alert('discountedAmount: ' + discountedAmount);
    	
    	var formattedDiscountedAmount = discountedAmount.format(2);
		// alert('formattedDiscountedAmount: ' + formattedDiscountedAmount);
		
		// set the value	
		Ext.getCmp('discountedAmount').setValue(formattedDiscountedAmount);
    }
    
    
    
    
    // calculate the Discounted Amount
    function fnCalculateNetAmountToPay(totalAnnualDue, discountedAmount)
    {
    	netAmountToPay = totalAnnualDue - discountedAmount;
    	// alert('netAmountToPay: ' + netAmountToPay);
    	
    	var formattedNetAmountToPay = netAmountToPay.format(2);
		// alert('formattedNetAmountToPay: ' + formattedNetAmountToPay);
		
		// set the value	
		Ext.getCmp('newAmount').setValue(formattedNetAmountToPay);
    }
    
    
    
    // TO FORMAT MONEY (formatting is now moved to common-util.js)
    /*Number.prototype.format = function(n, x) {
        var re = '\\d(?=(\\d{' + (x || 3) + '})+' + (n > 0 ? '\\.' : '$') + ')';
        return this.toFixed(Math.max(0, ~~n)).replace(new RegExp(re, 'g'), '$&,');
    };*/
 	
    // render the Ext Js element in a div with id="login"
	createRequestFormPanel.render('my-tabs');
     
    
});