Ext.onReady(function(){
    
    Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
       
    var services ={
        columnWidth: .99,
        layout: 'form',
        align:'center',
        items: [{
            xtype: 'fieldset',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
            	fieldLabel: 'PAYEE NAME',
            	name: 'requestor',
        		id: 'requestor',
        		xtype:'combo',
        		store: ownerHouseStore,
        		queryMode: 'server',
        		displayField:'ownerHouseLabel',
        		valueField: 'houseId',
        		// cls: 'cTextAlign', 
                mode: 'local',
                triggerAction: 'all',
                lazyRender: true,
                emptyText: 'Select Payee',
                allowBlank: false,
                blankText: 'Payee Name should not be null',
                anchor: '100%',
            	//hiddenName: 'id',
                //id: 'id',
                listeners: {
                    select:  function(combo, record, index){
                        // var id = record.get('id')
                    	
                    	// RACS
                    	var userId = record.get('userId');
                    	var houseId = record.get('houseId');
                    	var payeeName = record.get('firstname') +  " " + record.get('lastname'); 
                    	var payeeAddress = record.get('houseAddrNumber') +  " " + record.get('houseAddrStreet');
                    	                        
                        /*alert('userId: ' + userId);
                        alert('houseId: ' + houseId);
                        alert('payeeName: ' + payeeName);
                        alert('payeeAddress: ' + payeeAddress);
                        */
                        
                        // ASSIGN THE VALUES TO THE HIDDEN FIELDS
                    	Ext.getCmp('userId').setValue(userId);
                    	Ext.getCmp('houseId').setValue(houseId);
                    	Ext.getCmp('payeeName').setValue(payeeName);
                    	Ext.getCmp('payeeAddress').setValue(payeeAddress); // racs
                    	
                    
                    	
                        if(id == 1)
                        {
                            Ext.getCmp('modeOfPayment').setValue('Cash')
                            Ext.getCmp('modeOfPayment').hide();
                            Ext.getCmp('bank').hide()
                            Ext.getCmp('checkNum').hide()
                        }else
                        {
                            Ext.getCmp('modeOfPayment').show();
                        }
                    }
                }
            },{
                cls: 'cTextAlign',
                xtype:'combo',
                fieldLabel: 'MODE OF PAYMENT',
                name:'modeOfPayment',
                queryMode: 'server',
                store: new Ext.data.ArrayStore({
                    fields: ['abbr', 'action'],
                    data : [
                    ['Cash', 'Cash'],
                    ['Cheque', 'Cheque']
                    ]
                }),
                displayField:'action',
                valueField: 'abbr',
                id:'modeOfPayment',
                mode: 'local',
                typeAhead: true,
                triggerAction: 'all',
                lazyRender: true,
                editable:true,
                emptyText: 'Select action',
                allowBlank: false,
                blankText: 'Mode of Payment should not be null',
                anchor: '100%',
                listeners: 
                {
                    select:  function(combo, record, index)
                    {
                        var modeOfPayment = combo.getValue();
                        if(modeOfPayment == 'Cheque')
                        {
                            Ext.getCmp('bank').show()
                            Ext.getCmp('chequeNumber').show()
                        }else
                        {
                            Ext.getCmp('bank').hide()
                            Ext.getCmp('chequeNumber').hide()
                        }
                    }
                }
            },{
                cls: 'cTextAlign',
                xtype: 'textfield',
                fieldLabel: 'BANK',
                name: 'bank',
                anchor: '95%',
                hidden:true,
                id:'bank'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'CHEQUE NUMBER',
                name: 'chequeNumber',
                anchor: '95%',
                hidden:true,
                id:'chequeNumber'
            },
    // ###############################################  HIDDEN VALUES FOR THE FIELDS NEEDED BY SELECT IN AdminBillController ################################ 
            {
            	xtype: 'textfield',
                name: 'userId',
                id:'userId',
                hidden: true,
                anchor: '95%',
            },
            {
            	xtype: 'textfield',
                name: 'houseId',
                id:'houseId',
                hidden: true,
                anchor: '95%',
            },
            {
            	xtype: 'textfield',
                name: 'payeeName',
                id:'payeeName',
                hidden: true,
                anchor: '95%',
            },
            {
            	xtype: 'textfield',
                name: 'payeeAddress',
                id:'payeeAddress',
                hidden: true,
                anchor: '95%',
            }
           ]
        }]
    }
    
    
    
    function fnLoadForm(theForm)
    {
        //for the purpose of this tutorial, load 1 record.
        theForm.getForm().load();
    } 
    

    var serviceForm = new Ext.FormPanel({
        id: 'service-form',
        title: 'Search Payee',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:10px',
        style: 'margin-top:40px',
        labelWidth : 130,
        labelAlign : 'left',
        renderTo: 'my-tabs',
        width : 450,
        height : 200,
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [services]
        }],
        buttons: [{
            text: 'Submit',					
            handler: function() 
            {
            	// get the values
                var userId = Ext.getCmp('userId').getValue();
                var houseId = Ext.getCmp('houseId').getValue();
                var payeeName = Ext.getCmp('payeeName').getValue();
                var payeeAddress = Ext.getCmp('payeeAddress').getValue();
                var modeOfPayment = Ext.getCmp('modeOfPayment').getValue();
                var chequeNumber =  Ext.getCmp('chequeNumber').getValue();
                var bank =  Ext.getCmp('bank').getValue();
                
                
                if(userId == '')
                {
                    Ext.Msg.alert('Failed', 'Please select Payee');
                        return false;
                }
                
                if(modeOfPayment == '')
                {
                    Ext.Msg.alert('Failed', 'Please select mode of Payment');
                        return false;
                }
                
                if(modeOfPayment == 'Cheque')
                {
                    if(chequeNumber == '')
                    {
                        Ext.Msg.alert('Failed', 'Cheque Number should not be null');
                        return false;
                    }
                    
                    if(bank == '')
                    {
                        Ext.Msg.alert('Failed', 'Bank should not be null');
                        return false;
                    }
                }
                
                
                // SUBMIT	
            	window.location = 'admin-bill.action?userId=' + userId + '&houseId=' + houseId + '&payeeName=' + payeeName + '&payeeAddress=' + payeeAddress //racs
            						+ '&modeOfPayment=' + modeOfPayment + '&chequeNumber=' + chequeNumber + '&bank=' + bank;
                    	 					
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(serviceForm);
            }
        }]
    });
});

function fnResetForm(theForm)
{
    theForm.getForm().reset();
    Ext.getCmp('mf.btn.add').setDisabled(false);
    Ext.getCmp('mf.btn.reset').setDisabled(false);
} //end fnResetForm
