Ext.onReady(function(){
    
    Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
   
    
    var searchMonthlyCollectionsBox = {
        columnWidth: .99,
        layout: 'form',
        align:'center',
        items: [{
            xtype: 'fieldset',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
	            {
	                fieldLabel: 'FROM COLLECTION DATE',
	                name: 'monthlyCollectionDateFrom',
	                id : 'monthlyCollectionDateFrom',
	                xtype: 'datefield',
	                format: 'm/d/Y',
	                submitFormat: 'm/d/Y',
	                maxValue: new Date(),
	                allowBlank: false,
	                editable:false,
	                anchor: '100%'
	            },
	            {
	                fieldLabel: 'TO COLLECTION DATE',
	                name: 'monthlyCollectionDateTo',
	                id : 'monthlyCollectionDateTo',
	                xtype: 'datefield',
	                format: 'm/d/Y',
	                submitFormat: 'm/d/Y',
	                maxValue: new Date(),
	                allowBlank: false,
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
    

    var searchMonthlyCollectionsForm = new Ext.FormPanel({
    	name: 'searchMonthlyCollectionsForm',
        id: 'searchMonthlyCollectionsForm',
        title: 'Search Monthly Collections',
        style: 'margin-top:40px',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 150,
        labelAlign : 'left',
        renderTo: 'my-tabs',
        width : 400,
        height : 150,
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [searchMonthlyCollectionsBox]
        }],
        buttons: [{
            text: 'Submit',
            handler: function()
            {
            	var monthlyCollectionDateFrom = Ext.getCmp('monthlyCollectionDateFrom').getValue().format('m/d/Y');
            	var monthlyCollectionDateTo = Ext.getCmp('monthlyCollectionDateTo').getValue().format('m/d/Y');
            	
            	// alert('monthlyCollectionDateFrom: ' + monthlyCollectionDateFrom);
            	// alert('monthlyCollectionDateTo: ' + monthlyCollectionDateTo);
            	
            	// alert('TO DO, need another parameter here para mag if na lang sa method sa controller...');
            	
            	window.open('download/monthly-collection-pdf.action?monthlyCollectionDateFrom=' + monthlyCollectionDateFrom 
            													+ '&monthlyCollectionDateTo=' + monthlyCollectionDateTo, '_blank');
            }
        },{
            text: 'Reset',
            disabled: false,
            handler: function() {
                fnResetForm(searchDailyCollectionsForm);
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
