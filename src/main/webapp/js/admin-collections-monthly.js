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
                fieldLabel: 'COLLECTION MONTH',
                name: 'monthlyCollectionDate',
                id : 'monthlyCollectionDate',
                xtype: 'datefield',
                format: 'm/Y',
                submitFormat: 'm/Y',
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
        labelWidth : 120,
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
            	var monthlyCollectionDate = Ext.getCmp('monthlyCollectionDate').getValue().format('m/Y');
            	// alert('dailyCollectionDate: ' + dailyCollectionDate);
            	
            	window.open('download/monthly-collection-pdf.action?monthlyCollectionDate=' + monthlyCollectionDate, '_blank');
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
