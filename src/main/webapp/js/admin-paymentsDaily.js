Ext.onReady(function(){
    
    Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
   
    
    
    var services ={
        columnWidth: .65,
        layout: 'form',
        align:'center',
        items: [{
            xtype: 'fieldset',
            autoHeight: true,
            defaultType: 'textfield',
            items: [
            {
                fieldLabel: 'PAYMENT DATE',
                name: 'collectibleDate',
                cls: 'cTextAlign', 
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                allowBlank: false,
                id : 'collectibleDate',
                editable:false,
                anchor: '95%'
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
        title: 'Search Daily Payments',
        frame: true,
        cls: 'my-form-class',
        bodyStyle:'padding:5px',
        labelWidth : 200,
        labelAlign : 'right',
        renderTo: 'my-tabs',
        width : 800,
        height : 350,
        align:'center',
        items: [{
            align:'center',
            layout: 'column',
            items: [services]
        }],
        buttons: [{
            text: 'Submit',
            handler: function() {
                //fnServicesUpdateForm(serviceForm);
                var form = Ext.getCmp('service-form').getForm();
                if(form.isValid()){
                        form.submit({
                            
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                            },
                            failure: function(form, action) {
                                //window.location = 'admin-collectionsDailyDisplay.action';
                                var colId = Ext.getCmp('collectibleDate').getValue()
                                window.open("main/download/dailyPayments/pdf.action?collectibleDate="+colId+"");
                            }
                        })
                    }
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
