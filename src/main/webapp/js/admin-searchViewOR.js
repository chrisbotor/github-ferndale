Ext.onReady(function(){
    
    Ext.QuickTips.init();
   
    
    Ext.BLANK_IMAGE_URL = '/homeportal/ext-3.4.0/resources/images/default/s.gif';
    
    var UserBean = Ext.data.Record.create([
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'requestor',
        type: 'string'
    },
    ])
    
    var userproxy = new Ext.data.HttpProxy({
        api: {
            read : 'admin/getRequestor.action'
        }
    });
    
    
    var userreader = new Ext.data.JsonReader({
        totalProperty: 'total',
        successProperty: 'success',
        idProperty: 'id',
        root: 'data',
        messageProperty: 'message'  // <-- New "messageProperty" meta-data
    },
    UserBean);

    // The new DataWriter component.
    var writer = new Ext.data.JsonWriter({
        encode: true,
        writeAllFields: true
    });
    
    
    var userstore = new Ext.data.Store({
        id: 'user',
        proxy: userproxy,
        reader: userreader,
        //writer: serwriter,  // <-- plug a DataWriter into the store just as you would a Reader
        autoSave: false // <-- false would delay executing create, update, destroy requests until specifically told to do so with some [save] buton.
    });

    userstore.load();

    Ext.data.DataProxy.addListener('exception', function(proxy, type, action, options, res) {
        Ext.Msg.show({
            title: 'ERROR',
            msg: res.message,
            icon: Ext.MessageBox.ERROR,
            buttons: Ext.Msg.OK
        });
    });
    
    
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
                xtype:'combo',
                cls: 'cTextAlign', 
                queryMode: 'server',
                store: userstore,
                fieldLabel: 'PAYEE',
                displayField:'requestor',
                hiddenName: 'id',
                valueField: 'id',
                mode: 'local',
                typeAhead: false,
                triggerAction: 'all',
                lazyRender: true,
                //editable:false,
                emptyText: 'Select action',
                id:'requestor',
                blankText: 'Payee should not be null',
                anchor: '95%'     
            },{
                fieldLabel: 'OFFICIAL RECEIPT DATE',
                name: 'orDate',
                id: 'orDate',
                cls: 'cTextAlign', 
                xtype: 'datefield',
                format: 'm/d/Y',
                submitFormat: 'm/d/Y',
                //allowBlank: false,
                editable:false,
                anchor: '95%',
                blankText: 'Official Receipt Date should not be null'
            },{
                cls: 'cTextAlign',
                xtype: 'numberfield',
                fieldLabel: 'OFFICIAL RECEIPT NUMBER',
                name: 'orNumber',
                id: 'orNumber',
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
        title: 'Search Official Receipt',
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
                var orNumber = Ext.getCmp('orNumber').getValue()
                var orDate = Ext.getCmp('orDate').getValue()
                var requestor = Ext.getCmp('requestor').getValue()
                
                if(orNumber == '' && orDate == '' && requestor == '')
                {
                    Ext.Msg.alert('Failed', 'Please input atleast in one parameter');
                        return false;
                }
                
                var form = Ext.getCmp('service-form').getForm();
                if(form.isValid()){
                        form.submit({
                            url: 'admin-ORHistory.action',
                            success: function(form, action) {
                                Ext.Msg.alert('Success', 'Successfully updated Service Request');
                                
                            },
                            failure: function(form, action) {
                                window.location = 'admin-ORHistory.action';
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
