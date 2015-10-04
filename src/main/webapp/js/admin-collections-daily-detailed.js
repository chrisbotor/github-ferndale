Ext.onReady(function(){
    
    Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
   
    
    var searchDailyCollectionsBox = {
        columnWidth: .99,
        layout: 'form',
        align:'center',
        items: [{
            xtype: 'fieldset',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'COLLECTION DATE',
                name: 'dailyCollectionDate',
                id : 'dailyCollectionDate',
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
    

    var searchDailyCollectionsForm = new Ext.FormPanel({
    	name: 'searchDailyCollectionsForm',
        id: 'searchDailyCollectionsForm',
        title: 'Search Daily Collections',
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
            items: [searchDailyCollectionsBox]
        }],
        buttons: [{
            text: 'Submit',
            handler: function()
            {
            	var dailyCollectionDate = Ext.getCmp('dailyCollectionDate').getValue().format('m/d/Y');
            	// alert('dailyCollectionDate: ' + dailyCollectionDate);
            	
            	window.open('download/daily-collection-pdf.action?dailyCollectionDate=' + dailyCollectionDate + '&detailed=true', '_blank');
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
